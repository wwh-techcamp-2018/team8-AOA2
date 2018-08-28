insert into user (uuid, name, email, phone_number) values ('111111111', 'Junho Hong', 'brenden0730@gmail.com', '010-1111-1111');
insert into user (uuid, name, email, phone_number) values ('907579591', '윤미현', 'brenden0730@gmail.com', '010-2222-2222');

--insert into user (uuid, name, email, phone_number) values ('907126317', '김연태', 'urbanscenery@gmail.com', '01043444441');
--insert into store (user_id, ADDRESS, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name) values (1,'ADDRESS', 'DETAI', 'DESC', '/', '예제', '1234512345', '12345', 'SERVICE DESC', 'TEST_STORE');

insert into store ( address, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name, user_id) values ('경기 안양시 동안구 엘에스로116번길 56 (호계동, 안양호계푸르지오)', '101동 301호', '안녕 나는 코딩하느라 힘들어 여기서 마카롱을 사준다면 내가좀 나아질것 같구나 ', '/uploads/test.png', '김연태', '01043444441', '14118', '연태가 운영하는 맛있는 마카롱가게', '연태가게', 1);
insert into store ( address, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name, user_id) values ('경기 안양시 동안구 엘에스로116번길 56 (호계동, 안양호계푸르지오)', '101동 301호', '안녕 나는 코딩하느라 힘들어 여기서 마카롱을 사준다면 내가좀 나아질것 같구나 ', '/uploads/test.png', '윤미현', '01033333333', '14118', '미현이가 운영하는 맛있는 마카롱가게', '미현가게', 2);


insert into menu (deleted, description, image_url, last_used, max_count, personal_max_count, name, price, store_id) values (false, '메뉴 설명', '/uploads/1535013404637-스크린샷 2018-08-09 오후 3.31.15.png', true, 3, 3, '메뉴1', 1000, 1);

insert into menu (deleted, description, image_url, last_used, max_count, personal_max_count, name, price, store_id) values (false, '메뉴 설명', '/uploads/1535013404637-스크린샷 2018-08-09 오후 3.31.15.png', true, 3, 3, '메뉴2', 1000, 1);

insert into menu (deleted, description, image_url, last_used, max_count, personal_max_count, name, price, store_id) values (false, '메뉴 설명', '/uploads/1535013404637-스크린샷 2018-08-09 오후 3.31.15.png', false, 3, 3, '메뉴3', 1000, 1);


insert into reservation (available_count, max_count, personal_max_count, open_date, menu_id, store_id) values (3, 3, 3, '2018-08-26', 1, 1);
insert into reservation (available_count, max_count, personal_max_count, open_date, menu_id, store_id) values (3, 3, 3, '2018-08-26', 2, 1);

insert into order_table (CREATED_DATE, NAME, PHONE_NUMBER, IS_PICKEDUP, ORDER_TOTAL_PRICE, PICKUP_TIME, STORE_ID) values ('2018-08-26', '홍준호', '010-1111-1111', false, 2000,'2018-08-27 11:00:00+03', 1);
insert into order_table (CREATED_DATE, NAME, PHONE_NUMBER, IS_PICKEDUP, ORDER_TOTAL_PRICE, PICKUP_TIME, STORE_ID) values ('2018-08-26', '이성근', '010-2222-2222', true, 2000,'2018-08-27 15:00:00+03', 1);

insert into order_item (ITEM_COUNT, ITEM_TOTAL_PRICE, ORDER_ID, RESERVATION_ID) values (1, 1000, 1, 1);
insert into order_item (ITEM_COUNT, ITEM_TOTAL_PRICE, ORDER_ID, RESERVATION_ID) values (1, 1000, 1, 2);

insert into order_item (ITEM_COUNT, ITEM_TOTAL_PRICE, ORDER_ID, RESERVATION_ID) values (1, 1000, 2, 1);
insert into order_item (ITEM_COUNT, ITEM_TOTAL_PRICE, ORDER_ID, RESERVATION_ID) values (1, 1000, 2, 2);