insert into user (uuid, name, email, phone_number) values ('903950726', 'Junho Hong', 'brenden0730@gmail.com', '010-1111-1111');
--insert into store (user_id, ADDRESS, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name) values (1,'ADDRESS', 'DETAI', 'DESC', '/', '예제', '1234512345', '12345', 'SERVICE DESC', 'TEST_STORE');

insert into store (ADDRESS, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name, time_to_close) values ('ADDRESS', 'DETAI', 'DESC', '/', '예제', '1234512345', '12345', 'SERVICE DESC', 'TEST_STORE', CURRENT_TIMESTAMP());

insert into menu (last_used, deleted, description, image_url, name, price, store_id) values (true, false, '메뉴설명1','/uploads/1534936936513-스크린샷 2018-07-12 오후 5.18.56.png', '이름1', 1000, 1);
insert into menu (last_used, deleted, description, image_url, name, price, store_id) values (false, false, '메뉴설명2','/uploads/1534936936513-스크린샷 2018-07-12 오후 5.18.56.png', '이름2', 1000, 1);