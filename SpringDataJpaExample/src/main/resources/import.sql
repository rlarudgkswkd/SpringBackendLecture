INSERT INTO categories (name, created_at) VALUES ('찌개류', NOW());
INSERT INTO categories (name, created_at) VALUES ('면류', NOW());
INSERT INTO categories (name, created_at) VALUES ('밥류', NOW());

INSERT INTO menus (menu_name, price, created_at, category_id) VALUES ('김치찌개', 9000, NOW(), 1);
INSERT INTO menus (menu_name, price, created_at, category_id) VALUES ('된장찌개', 8500, NOW(), 1);
INSERT INTO menus (menu_name, price, created_at, category_id) VALUES ('라면', 4500, NOW(), 2);
INSERT INTO menus (menu_name, price, created_at, category_id) VALUES ('비빔밥', 9500, NOW(), 3);