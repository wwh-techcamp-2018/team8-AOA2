insert into user (uuid, name, email, phone_number) values ('903950726', 'Junho Hong', 'brenden0730@gmail.com', '010-1111-1111');
--insert into store (user_id, ADDRESS, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name) values (1,'ADDRESS', 'DETAI', 'DESC', '/', '예제', '1234512345', '12345', 'SERVICE DESC', 'TEST_STORE');

insert into store (ADDRESS, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name, time_to_close) values ('ADDRESS', 'DETAI', 'DESC', '/', '예제', '1234512345', '12345', 'SERVICE DESC', 'TEST_STORE', CURRENT_TIMESTAMP());

insert into user (id, email, name, phone_number, uuid) values (null, 'urbanscenery@gmail.com', '김연태', '01043334441', '904502908');

insert into store (id, address, address_detail, description, imgurl, owner_name, phone_number, post_code, service_description, store_name, time_to_close, user_id) values (null, '경기 안양시 동안구 엘에스로116번길 56 (호계동, 안양호계푸르지오)', '110동 501호', '안녕 나는 코딩하느라 힘들어 여기서 마카롱을 사준다면 내가좀 나아질것 같구나 ', '/uploads/test.png', '김연태', '01043334441', '14118', '연태가 운영하는 맛있는 마카롱가게', '연태가게', CURRENT_TIMESTAMP(), 2)
