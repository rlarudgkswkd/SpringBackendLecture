// ========================================
// 메뉴 관리 시스템 - Spring Security 연동 CSR
// ========================================

const API_BASE_URL = '/api';

// 전역 변수
let currentUser = null;
let accessToken = null; // JWT 액세스 토큰(메모리 보관)

// ========================================
// 1. 애플리케이션 초기화
// ========================================

document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

async function initializeApp() {
    try {
        // 쿠키 기반 CSRF: 초기 토큰 생성을 위해 한 번 요청
        await loadInitialCsrfToken();

        // 새로고침 진입 시: 리프레시 토큰으로 액세스 토큰 갱신 시도(쿠키 기반)
        await tryRefreshByCookie();

        // 현재 사용자 확인 (토큰이 있으면 서버에 질의 가능)
        await checkCurrentUser();
        
        // 이벤트 리스너 설정
        setupEventListeners();
        
    } catch (error) {
        console.error('애플리케이션 초기화 실패:', error);
        showLoginScreen();
    }
}

// ========================================
// 2. CSRF 토큰 관리
// ========================================

async function loadCsrfToken(context = '기본') {
    try {
        console.log(`[${context}] CSRF 토큰 로드 시작`);
        console.log(`[${context}] 현재 쿠키 (토큰 요청 전):`, document.cookie);
        
        // 쿠키 기반 CSRF: 토큰 생성을 위해 API 호출 (응답값은 사용하지 않음)
        const response = await fetch(`${API_BASE_URL}/auth/csrf-token`);
        if (response.ok) {
            console.log(`[${context}] CSRF 토큰 요청 성공`);
            console.log(`[${context}] 현재 쿠키 (토큰 요청 후):`, document.cookie);
            
            // 토큰이 제대로 생성되었는지 확인
            const token = getCsrfTokenFromCookie();
            if (token) {
                console.log(`[${context}] ✅ CSRF 토큰 정상 확인:`, token.substring(0, 20) + '...');
            } else {
                console.error(`[${context}] ❌ CSRF 토큰 쿠키가 생성되지 않았습니다`);
            }
        } else {
            console.error(`[${context}] CSRF 토큰 요청 실패:`, response.status, response.statusText);
        }
    } catch (error) {
        console.error(`[${context}] CSRF 토큰 생성 트리거 실패:`, error);
    }
}

// 기존 함수명 호환성을 위한 래퍼
async function loadInitialCsrfToken() {
    return await loadCsrfToken('초기화');
}

function getCsrfTokenFromCookie() {
    // 쿠키 기반 CSRF: XSRF-TOKEN 쿠키에서 토큰 읽기
    const cookies = document.cookie.split(';');
    
    for (let cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'XSRF-TOKEN') {
            return value;
        }
    }
    
    console.warn('[CSRF] ❌ XSRF-TOKEN 쿠키를 찾을 수 없습니다');
    return null;
}

// 리프레시 시도 (쿠키의 REFRESH-TOKEN 사용)
async function tryRefreshByCookie() {
    try {
        const csrf = getCsrfTokenFromCookie();
        const headers = {};
        if (csrf) headers['X-XSRF-TOKEN'] = csrf;
        const resp = await fetch(`${API_BASE_URL}/auth/refresh`, {
            method: 'POST',
            headers,
            credentials: 'same-origin'
        });
        if (resp.ok) {
            const jwt = await resp.json();
            accessToken = jwt.accessToken;
            currentUser = jwt.user;
            return true;
        }
        return false;
    } catch (e) {
        console.error('[REFRESH] 실패:', e);
        return false;
    }
}

// ========================================
// 3. API 호출 헬퍼
// ========================================

async function apiCall(url, options = {}) {
    const headers = {
        ...options.headers
    };

    // JWT 액세스 토큰 추가
    if (accessToken) {
        headers['Authorization'] = `Bearer ${accessToken}`;
    }

    // CSRF 토큰 추가 (POST, PUT, DELETE 요청시)
    if (options.method && ['POST', 'PUT', 'DELETE'].includes(options.method)) {
        let token = getCsrfTokenFromCookie();
        if (!token) {
            console.warn('[API] CSRF 토큰이 없어 갱신을 시도합니다');
            await loadCsrfToken('API 직전 갱신');
            token = getCsrfTokenFromCookie();
        }
        if (token) {
            headers['X-XSRF-TOKEN'] = token;  // 쿠키 기반 CSRF: X-XSRF-TOKEN 헤더
        } else {
            console.error('[API] CSRF 토큰을 찾을 수 없습니다');
        }
    }

    // JSON 요청일 때 Content-Type 설정
    if (options.body && typeof options.body === 'string') {
        headers['Content-Type'] = 'application/json';
    }

    const exec = () => fetch(url, {
        ...options,
        headers,
        credentials: 'same-origin' // 세션 쿠키 포함
    });

    let response = await exec();
    if (response.status === 401) {
        console.log('[API] 401 발생 → 리프레시 시도');
        const refreshed = await tryRefreshByCookie();
        if (refreshed) {
            // 토큰 갱신 후 원 요청 재시도
            if (accessToken) headers['Authorization'] = `Bearer ${accessToken}`;
            response = await exec();
        }
        if (response.status === 401) {
            // 여전히 실패 → 사용자에게 알림 및 로그아웃 상태로 전환
            await loadCsrfToken('401 에러 후');
            currentUser = null;
            accessToken = null;
            showNotification('세션이 만료되었습니다. 다시 로그인해주세요.', 'error');
            showMainScreen();
            return null;
        }
    }
    return response;
}

// ========================================
// 4. 인증 관리
// ========================================

async function checkCurrentUser() {
    if (!accessToken || !currentUser) {
        currentUser = null;
    }
    showMainScreen();
}

async function login(username, password) {
    try {
        // Form 기반 로그인: FormData 사용
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        // CSRF 토큰을 헤더로 전송
        const csrfToken = getCsrfTokenFromCookie();
        const headers = {};
        if (csrfToken) {
            headers['X-XSRF-TOKEN'] = csrfToken;
            console.log('[로그인] ✅ CSRF 토큰 헤더 추가');
        } else {
            console.error('[로그인] ❌ CSRF 토큰을 찾을 수 없습니다!');
        }

        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: headers,
            body: formData,
            credentials: 'same-origin'
        });

        if (response.ok) {
            const jwt = await response.json();
            // JwtDto { user, accessToken }
            currentUser = jwt.user;
            accessToken = jwt.accessToken;

            // 로그인 성공 후 CSRF 토큰 재로드 (쿠키 최신화 목적)
            await loadCsrfToken('로그인 후');

            showMainScreen();
            showNotification(`${currentUser.username}님, 환영합니다!`, 'success');
            return true;
        } else {
            const error = await response.json();
            showNotification(error.message || '로그인에 실패했습니다.', 'error');
            return false;
        }
    } catch (error) {
        console.error('로그인 실패:', error);
        showNotification('로그인 중 오류가 발생했습니다.', 'error');
        return false;
    }
}

async function logout() {
    try {
        console.log('[로그아웃] 로그아웃 요청 시작');

        // CSRF 토큰이 없으면 먼저 갱신
        if (!getCsrfTokenFromCookie()) {
            await loadCsrfToken('logout 직전 갱신');
        }

        const response = await apiCall(`${API_BASE_URL}/auth/logout`, {
            method: 'POST'
        });

        if (response && response.ok) {
            currentUser = null;
            accessToken = null;

            // 로그아웃 성공 후 CSRF 토큰 재로드 (새 세션 대비)
            await loadCsrfToken('로그아웃 후');

            showMainScreen(); // 로그아웃 후 메인 화면 유지 (미인증 상태)
            showNotification('로그아웃되었습니다.', 'info');
        } else {
            showNotification('로그아웃에 실패했습니다.', 'error');
        }
    } catch (error) {
        console.error('로그아웃 실패:', error);
        showNotification('로그아웃 중 오류가 발생했습니다.', 'error');
    }
}

async function signup(username, email, password) {
    try {
        const response = await apiCall(`${API_BASE_URL}/users`, {
            method: 'POST',
            body: JSON.stringify({
                username,
                email,
                password
            })
        });

        if (response && response.ok) {
            showNotification('회원가입이 완료되었습니다. 로그인해주세요.', 'success');
            showLogin();
            return true;
        } else {
            showNotification('회원가입에 실패했습니다.', 'error');
            return false;
        }
    } catch (error) {
        console.error('회원가입 실패:', error);
        showNotification('회원가입 중 오류가 발생했습니다.', 'error');
        return false;
    }
}

// ========================================
// 5. 화면 전환
// ========================================

function showLoginScreen() {
    document.getElementById('loginScreen').style.display = 'block';
    document.getElementById('mainScreen').style.display = 'none';
    document.getElementById('navbar').style.display = 'none';
}

function showMainScreen() {
    document.getElementById('loginScreen').style.display = 'none';
    document.getElementById('mainScreen').style.display = 'block';
    document.getElementById('navbar').style.display = 'block';
    
    // 사용자 정보 표시
    updateUserInfo();
    
    // 권한에 따른 UI 조정
    updateUIByRole();
    
    // 데이터 로드
    loadCategories();
    loadMenus();
}

function updateUserInfo() {
    const userInfo = document.getElementById('userInfo');
    const loginBtn = document.getElementById('loginBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    
    if (currentUser) {
        // 인증된 사용자: 사용자 정보 표시 + 로그아웃 버튼
        const roleText = currentUser.role === 'ADMIN' ? '관리자' : '사용자';
        userInfo.textContent = `${currentUser.username} (${roleText})`;
        loginBtn.style.display = 'none';
        logoutBtn.style.display = 'inline-block';
    } else {
        // 미인증 사용자: 환영 메시지 + 로그인 버튼
        userInfo.textContent = '환영합니다! 로그인하시면 더 많은 기능을 이용하실 수 있습니다.';
        loginBtn.style.display = 'inline-block';
        logoutBtn.style.display = 'none';
    }
}

function updateUIByRole() {
    const addMenuBtn = document.getElementById('addMenuBtn');
    const userManagementBtn = document.getElementById('userManagementBtn');
    
    if (currentUser && currentUser.role === 'ADMIN') {
        // 관리자: 모든 관리 기능 표시
        addMenuBtn.style.display = 'inline-block';
        userManagementBtn.style.display = 'inline-block';
    } else {
        // 일반 사용자 또는 미인증 사용자: 관리 기능 숨김
        addMenuBtn.style.display = 'none';
        userManagementBtn.style.display = 'none';
    }
}

function openLoginModal() {
    // 네비게이션 바의 로그인 버튼 클릭 시 호출
    showLoginScreen();
}

function showLogin() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('signupForm').style.display = 'none';
    document.querySelector('.tab-btn.active').classList.remove('active');
    document.querySelector('.tab-btn').classList.add('active');
}

function showSignup() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('signupForm').style.display = 'block';
    document.querySelector('.tab-btn.active').classList.remove('active');
    document.querySelectorAll('.tab-btn')[1].classList.add('active');
}

// ========================================
// 6. 이벤트 리스너 설정
// ========================================

function setupEventListeners() {
    // 로그인 폼
    document.getElementById('loginForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        const username = document.getElementById('loginUsername').value;
        const password = document.getElementById('loginPassword').value;
        await login(username, password);
    });

    // 회원가입 폼
    document.getElementById('signupForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        const username = document.getElementById('signupUsername').value;
        const email = document.getElementById('signupEmail').value;
        const password = document.getElementById('signupPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            showNotification('비밀번호가 일치하지 않습니다.', 'error');
            return;
        }

        await signup(username, email, password);
    });

    // 메뉴 등록 폼
    document.getElementById('addMenuForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        const formData = new FormData(this);
        await addMenu(formData);
    });

    // 모달 외부 클릭 시 닫기
    window.onclick = function(event) {
        const modals = document.querySelectorAll('.modal');
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    }
}

// ========================================
// 7. 메뉴 관리
// ========================================

async function loadCategories() {
    try {
        // 임시 카테고리 데이터 (백엔드에 카테고리 API가 구현되면 수정 필요)
        const categories = [
            { categoryCode: 1, categoryName: '식사' },
            { categoryCode: 2, categoryName: '디저트' },
            { categoryCode: 3, categoryName: '음료' }
        ];
        
        const categorySelect = document.getElementById('categoryCode');
        categorySelect.innerHTML = '<option value="">카테고리를 선택하세요</option>';
        
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.categoryCode;
            option.textContent = category.categoryName;
            categorySelect.appendChild(option);
        });
    } catch (error) {
        console.error('카테고리 로드 실패:', error);
    }
}

async function loadMenus() {
    const container = document.getElementById('menuContainer');
    
    try {
        const response = await apiCall(`${API_BASE_URL}/menus`);
        if (response && response.ok) {
            const menus = await response.json();
            
            if (menus.length === 0) {
                container.innerHTML = '<div class="error">등록된 메뉴가 없습니다.</div>';
                return;
            }
            
            const menuGrid = document.createElement('div');
            menuGrid.className = 'menu-grid';
            
            menus.forEach(menu => {
                const menuCard = createMenuCard(menu);
                menuGrid.appendChild(menuCard);
            });
            
            container.innerHTML = '';
            container.appendChild(menuGrid);
        }
    } catch (error) {
        console.error('메뉴 로드 실패:', error);
        container.innerHTML = '<div class="error">메뉴를 불러오는데 실패했습니다.</div>';
    }
}

function createMenuCard(menu) {
    const card = document.createElement('div');
    card.className = 'menu-card';
    
    const imageUrl = menu.menuImageUrl 
        ? `${API_BASE_URL}/images/${menu.menuImageUrl}` 
        : '';
    
    const formatPrice = (price) => {
        return new Intl.NumberFormat('ko-KR').format(price);
    };

    const isAdmin = currentUser && currentUser.role === 'ADMIN';
    
    card.innerHTML = `
        <div onclick="openMenuDetailModal(${menu.menuCode})" style="cursor: pointer;">
            ${imageUrl 
                ? `<img src="${imageUrl}" alt="${menu.menuName}" class="menu-image" onerror="this.style.display='none'">` 
                : '<div class="menu-image">이미지 없음</div>'
            }
            <div class="menu-content">
                <div class="menu-category">${menu.category ? menu.category.categoryName : '미분류'}</div>
                <div class="menu-title">${menu.menuName}</div>
                <div class="menu-price">${formatPrice(menu.menuPrice)}원</div>
                <div class="menu-description">${menu.menuDescription || '설명이 없습니다.'}</div>
                <div class="menu-stock">재고: ${menu.menuStock}개</div>
            </div>
        </div>
        ${isAdmin ? `
        <div class="menu-content" style="padding-top: 0;">
            <button class="btn btn-danger delete-btn" onclick="event.stopPropagation(); deleteMenu(${menu.menuCode})">
                🗑️ 삭제
            </button>
        </div>
        ` : ''}
    `;
    
    return card;
}

async function addMenu(formData) {
    try {
        const response = await apiCall(`${API_BASE_URL}/menus`, {
            method: 'POST',
            body: formData
        });
        
        if (response && response.ok) {
            showNotification('메뉴가 성공적으로 등록되었습니다.', 'success');
            closeAddMenuModal();
            loadMenus();
        } else {
            showNotification('메뉴 등록에 실패했습니다.', 'error');
        }
    } catch (error) {
        console.error('메뉴 등록 실패:', error);
        showNotification('메뉴 등록 중 오류가 발생했습니다.', 'error');
    }
}

async function deleteMenu(menuCode) {
    if (!confirm('정말로 이 메뉴를 삭제하시겠습니까?')) {
        return;
    }
    
    try {
        const response = await apiCall(`${API_BASE_URL}/menus/${menuCode}`, {
            method: 'DELETE'
        });
        
        if (response && response.ok) {
            showNotification('메뉴가 성공적으로 삭제되었습니다.', 'success');
            loadMenus();
        } else {
            showNotification('메뉴 삭제에 실패했습니다.', 'error');
        }
    } catch (error) {
        console.error('메뉴 삭제 실패:', error);
        showNotification('메뉴 삭제 중 오류가 발생했습니다.', 'error');
    }
}

async function openMenuDetailModal(menuCode) {
    try {
        const response = await apiCall(`${API_BASE_URL}/menus/${menuCode}`);
        if (response && response.ok) {
            const menu = await response.json();
            displayMenuDetail(menu);
            document.getElementById('menuDetailModal').style.display = 'block';
        } else {
            showNotification('로그인이 필요합니다.', 'error');
        }
    } catch (error) {
        console.error('메뉴 상세 정보 로드 실패:', error);
        showNotification('메뉴 정보를 불러오는데 실패했습니다.', 'error');
    }
}

function displayMenuDetail(menu) {
    const imageUrl = menu.menuImageUrl 
        ? `${API_BASE_URL}/images/${menu.menuImageUrl}` 
        : '';
    
    const formatPrice = (price) => {
        return new Intl.NumberFormat('ko-KR').format(price);
    };

    const isAdmin = currentUser && currentUser.role === 'ADMIN';
    
    const detailContent = document.getElementById('menuDetailContent');
    detailContent.innerHTML = `
        <div style="text-align: center; margin-bottom: 25px;">
            ${imageUrl 
                ? `<img src="${imageUrl}" alt="${menu.menuName}" style="max-width: 100%; max-height: 300px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1);" onerror="this.style.display='none'">` 
                : '<div style="width: 100%; height: 200px; background: #f0f0f0; display: flex; align-items: center; justify-content: center; color: #999; border-radius: 10px;">이미지 없음</div>'
            }
        </div>
        <div style="margin-bottom: 20px;">
            <div style="display: inline-block; background: #e3f2fd; color: #1976d2; padding: 6px 16px; border-radius: 20px; font-size: 0.9rem; font-weight: 600; margin-bottom: 15px;">
                ${menu.category ? menu.category.categoryName : '미분류'}
            </div>
            <h3 style="font-size: 1.8rem; color: #2c3e50; margin-bottom: 10px;">${menu.menuName}</h3>
            <div style="font-size: 1.5rem; font-weight: 800; color: #667eea; margin-bottom: 15px;">${formatPrice(menu.menuPrice)}원</div>
            <p style="color: #666; line-height: 1.6; margin-bottom: 15px;">${menu.menuDescription || '설명이 없습니다.'}</p>
            <div style="color: #4caf50; font-weight: 600; margin-bottom: 20px;">재고: ${menu.menuStock}개</div>
        </div>
        ${isAdmin ? `
        <div style="display: flex; gap: 10px; justify-content: center;">
            <button class="btn btn-danger" onclick="confirmDeleteMenu(${menu.menuCode})">
                🗑️ 메뉴 삭제
            </button>
        </div>
        ` : ''}
    `;
}

async function confirmDeleteMenu(menuCode) {
    if (!confirm('정말로 이 메뉴를 삭제하시겠습니까?')) {
        return;
    }
    
    try {
        const response = await apiCall(`${API_BASE_URL}/menus/${menuCode}`, {
            method: 'DELETE'
        });
        
        if (response && response.ok) {
            showNotification('메뉴가 성공적으로 삭제되었습니다.', 'success');
            closeMenuDetailModal();
            loadMenus();
        } else {
            showNotification('메뉴 삭제에 실패했습니다.', 'error');
        }
    } catch (error) {
        console.error('메뉴 삭제 실패:', error);
        showNotification('메뉴 삭제 중 오류가 발생했습니다.', 'error');
    }
}

// ========================================
// 8. 모달 관리
// ========================================

function openAddMenuModal() {
    document.getElementById('addMenuModal').style.display = 'block';
}

function closeAddMenuModal() {
    document.getElementById('addMenuModal').style.display = 'none';
    document.getElementById('addMenuForm').reset();
}

function closeMenuDetailModal() {
    document.getElementById('menuDetailModal').style.display = 'none';
}

function openUserManagementModal() {
    document.getElementById('userManagementModal').style.display = 'block';
    loadUserManagement();
}

function closeUserManagementModal() {
    document.getElementById('userManagementModal').style.display = 'none';
}

async function loadUserManagement() {
    const content = document.getElementById('userManagementContent');
    content.innerHTML = `
        <div class="user-management">
            <h3>사용자 권한 수정</h3>
            <div class="form-group">
                <label for="roleUserId">사용자 ID:</label>
                <input type="number" id="roleUserId" placeholder="권한을 수정할 사용자 ID 입력">
            </div>
            <div class="form-group">
                <label for="newRole">새로운 권한:</label>
                <select id="newRole">
                    <option value="USER">USER (일반 사용자)</option>
                    <option value="ADMIN">ADMIN (관리자)</option>
                </select>
            </div>
            <button type="button" class="btn btn-primary" onclick="updateUserRole()">권한 수정</button>
            <div style="margin-top: 20px;">
                <p><strong>참고:</strong> 권한 수정은 ADMIN 권한이 필요합니다.</p>
                <p>사용자 ID는 개발자 도구의 Network 탭이나 데이터베이스에서 확인할 수 있습니다.</p>
            </div>
        </div>
    `;
}

async function updateUserRole() {
    const userId = document.getElementById('roleUserId').value;
    const newRole = document.getElementById('newRole').value;
    
    if (!userId) {
        showNotification('사용자 ID를 입력해주세요.', 'error');
        return;
    }
    
    try {
        console.log('[권한 수정] 요청 시작 - 사용자 ID:', userId, '새 권한:', newRole);
        
        const response = await apiCall(`${API_BASE_URL}/auth/role`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: parseInt(userId),
                newRole: newRole
            })
        });

        if (response.ok) {
            const updatedUser = await response.json();
            showNotification(`사용자 권한이 ${newRole}로 변경되었습니다.`, 'success');
            console.log('[권한 수정] 성공:', updatedUser);
            
            // 입력 필드 초기화
            document.getElementById('roleUserId').value = '';
            document.getElementById('newRole').value = 'USER';
        } else {
            const errorText = await response.text();
            console.error('[권한 수정] 실패:', response.status, errorText);
            
            if (response.status === 403) {
                showNotification('권한이 없습니다. ADMIN 권한이 필요합니다.', 'error');
            } else if (response.status === 404) {
                showNotification('해당 사용자를 찾을 수 없습니다.', 'error');
            } else {
                showNotification('권한 수정에 실패했습니다.', 'error');
            }
        }
    } catch (error) {
        console.error('[권한 수정] 오류:', error);
        showNotification('권한 수정 중 오류가 발생했습니다.', 'error');
    }
}

// ========================================
// 9. 알림 시스템
// ========================================

function showNotification(message, type = 'info') {
    const container = document.getElementById('notificationContainer');
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    container.appendChild(notification);
    
    // 애니메이션
    setTimeout(() => {
        notification.classList.add('show');
    }, 100);
    
    // 자동 제거
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            container.removeChild(notification);
        }, 300);
    }, 3000);
} 