-- members
insert into members (id, email, name) values (1, 'a@test.com', '회원A');
insert into members (id, email, name) values (2, 'b@test.com', '회원B');
insert into members (id, email, name) values (3, 'c@test.com', '회원C');
insert into members (id, email, name) values (4, 'd@test.com', '회원D');

-- stamps (member_id unique)
insert into stamps (id, member_id, stamp_count) values (1, 1, 0);
insert into stamps (id, member_id, stamp_count) values (2, 2, 3);
insert into stamps (id, member_id, stamp_count) values (3, 3, 7);
insert into stamps (id, member_id, stamp_count) values (4, 4, 12);

-- coffees
insert into coffees (id, name, price) values (1, '아메리카노', 4500);
insert into coffees (id, name, price) values (2, '라떼', 5000);
insert into coffees (id, name, price) values (3, '바닐라라떼', 5500);
insert into coffees (id, name, price) values (4, '콜드브루', 6000);
insert into coffees (id, name, price) values (5, '카라멜마끼아또', 5800);
insert into coffees (id, name, price) values (6, '카푸치노', 5200);
insert into coffees (id, name, price) values (7, '에스프레소', 4000);
insert into coffees (id, name, price) values (8, '디카페인 아메리카노', 4800);