package org.example.Head08_SpringBeansUnderstandAndUse.topic03_DependencyInjectionUnderstanding.example02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* @Service : @Component의 세분화 어노테이션의 한 종류로 Service 계층에서 사용한다. */
@Service("bookServiceSetter")
public class BookService {

    // final을 사용할 수 없음 - 가변 필드
    private BookDAO bookDAO;

    // 기본 생성자가 필요함
    public BookService() {
        System.out.println("BookService 기본 생성자 호출");
        // 이 시점에서 bookDAO는 null
    }

    /* BookDAO 타입의 bean 객체를 setter에 자동으로 주입해준다.
     *
     * Setter 주입의 특징:
     * 1. 객체 생성 후 주입되므로 선택적 의존성 처리 가능 ->[의미]있을수도 있고 없을수도 있다!
     * 2. 런타임에 의존성 변경 가능 (권장하지 않음)
     * 3. 순환 참조가 런타임에 발견됨
     */
    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        System.out.println("setBookDAO 호출 - BookDAO 주입");
        this.bookDAO = bookDAO;
    }

    // 선택적 의존성 예시 - required = false
    @Autowired(required = false)
    public void setCacheService(CacheService cacheService) {
        // CacheService가 없어도 오류 발생하지 않음
        if (cacheService != null) {
            System.out.println("캐시 서비스 활성화");
        }
    }

    public List<BookDTO> selectAllBooks(){
        // NPE(Null Pointer Exception) 방지를 위한 null 체크 필요
        if (bookDAO == null) {
            throw new IllegalStateException("BookDAO가 주입되지 않았습니다");
        }
        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequence) {
        if (bookDAO == null) {
            throw new IllegalStateException("BookDAO가 주입되지 않았습니다");
        }
        return bookDAO.selectOneBook(sequence);
    }
}
