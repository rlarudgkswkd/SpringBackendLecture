-- -----------------------
-- Categories
-- -----------------------
INSERT INTO categories (id, name, created_at) VALUES (1, '찌개류', now());
INSERT INTO categories (id, name, created_at) VALUES (2, '면류', now());
INSERT INTO categories (id, name, created_at) VALUES (3, '밥류', now());
INSERT INTO categories (id, name, created_at) VALUES (4, '음료', now());
INSERT INTO categories (id, name, created_at) VALUES (5, '디저트', now());

-- -----------------------
-- Menus (120 rows)
-- id는 IDENTITY라 원래 생략 가능하지만, import.sql에서는 명시하는 편이 안정적임(학습용)
-- category_id는 categories.id를 참조
-- -----------------------

-- 찌개류 (1) 24개
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (1, '김치찌개-1', 7000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (2, '김치찌개-2', 8000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (3, '된장찌개-1', 6500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (4, '된장찌개-2', 7500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (5, '순두부찌개-1', 8000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (6, '순두부찌개-2', 9000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (7, '부대찌개-1', 9500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (8, '부대찌개-2', 10500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (9, '청국장-1', 8500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (10, '청국장-2', 9500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (11, '동태찌개-1', 9000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (12, '동태찌개-2', 10000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (13, '고추장찌개-1', 7500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (14, '고추장찌개-2', 8500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (15, '참치김치찌개-1', 9000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (16, '참치김치찌개-2', 10000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (17, '콩비지찌개-1', 8000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (18, '콩비지찌개-2', 9000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (19, '차돌된장찌개-1', 10000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (20, '차돌된장찌개-2', 11000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (21, '짜글이-1', 9000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (22, '짜글이-2', 10000, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (23, '해물순두부-1', 9500, now(), 1);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (24, '해물순두부-2', 10500, now(), 1);

-- 면류 (2) 24개
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (25, '라면-1', 4500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (26, '라면-2', 5000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (27, '우동-1', 6500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (28, '우동-2', 7000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (29, '쫄면-1', 7000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (30, '쫄면-2', 7500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (31, '비빔국수-1', 6500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (32, '비빔국수-2', 7000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (33, '잔치국수-1', 6000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (34, '잔치국수-2', 6500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (35, '쌀국수-1', 9000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (36, '쌀국수-2', 9500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (37, '냉면-1', 10000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (38, '냉면-2', 11000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (39, '짜장면-1', 8000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (40, '짜장면-2', 8500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (41, '짬뽕-1', 9000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (42, '짬뽕-2', 9500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (43, '파스타-1', 12000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (44, '파스타-2', 13000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (45, '토마토파스타-1', 13500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (46, '토마토파스타-2', 14500, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (47, '크림파스타-1', 14000, now(), 2);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (48, '크림파스타-2', 15000, now(), 2);

-- 밥류 (3) 24개
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (49, '김치볶음밥-1', 8000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (50, '김치볶음밥-2', 8500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (51, '제육덮밥-1', 9500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (52, '제육덮밥-2', 10000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (53, '돈까스정식-1', 11000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (54, '돈까스정식-2', 12000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (55, '불고기덮밥-1', 10500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (56, '불고기덮밥-2', 11500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (57, '비빔밥-1', 9000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (58, '비빔밥-2', 9500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (59, '카레라이스-1', 8500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (60, '카레라이스-2', 9000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (61, '오므라이스-1', 9500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (62, '오므라이스-2', 10000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (63, '스테이크덮밥-1', 15000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (64, '스테이크덮밥-2', 16000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (65, '연어덮밥-1', 14000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (66, '연어덮밥-2', 15000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (67, '참치덮밥-1', 12000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (68, '참치덮밥-2', 13000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (69, '새우볶음밥-1', 9000, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (70, '새우볶음밥-2', 9500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (71, '김밥-1', 4500, now(), 3);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (72, '김밥-2', 5000, now(), 3);

-- 음료 (4) 24개
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (73, '아메리카노-1', 3500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (74, '아메리카노-2', 4000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (75, '카페라떼-1', 4500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (76, '카페라떼-2', 5000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (77, '바닐라라떼-1', 5200, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (78, '바닐라라떼-2', 5700, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (79, '레몬에이드-1', 5500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (80, '레몬에이드-2', 6000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (81, '자몽에이드-1', 5800, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (82, '자몽에이드-2', 6300, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (83, '딸기스무디-1', 6500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (84, '딸기스무디-2', 7000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (85, '망고스무디-1', 6500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (86, '망고스무디-2', 7000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (87, '초코라떼-1', 5000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (88, '초코라떼-2', 5500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (89, '녹차라떼-1', 5200, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (90, '녹차라떼-2', 5700, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (91, '밀크티-1', 6000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (92, '밀크티-2', 6500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (93, '콜라-1', 2000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (94, '콜라-2', 2500, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (95, '사이다-1', 2000, now(), 4);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (96, '사이다-2', 2500, now(), 4);

-- 디저트 (5) 24개
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (97, '치즈케이크-1', 6500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (98, '치즈케이크-2', 7000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (99, '초코케이크-1', 6500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (100, '초코케이크-2', 7000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (101, '딸기케이크-1', 7000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (102, '딸기케이크-2', 7500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (103, '티라미수-1', 6800, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (104, '티라미수-2', 7300, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (105, '쿠키-1', 2500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (106, '쿠키-2', 3000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (107, '마카롱-1', 3000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (108, '마카롱-2', 3500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (109, '푸딩-1', 4000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (110, '푸딩-2', 4500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (111, '아이스크림-1', 3500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (112, '아이스크림-2', 4000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (113, '와플-1', 7000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (114, '와플-2', 7500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (115, '팬케이크-1', 8000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (116, '팬케이크-2', 8500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (117, '브라우니-1', 6000, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (118, '브라우니-2', 6500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (119, '도넛-1', 2500, now(), 5);
INSERT INTO menus (id, menu_name, price, created_at, category_id) VALUES (120, '도넛-2', 3000, now(), 5);