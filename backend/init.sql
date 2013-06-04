# setting up the database

insert into core_forest (user_id, level, points) values (1, 1, 0);
insert into core_forest (user_id, level, points) values (2, 1, 0);
insert into core_forest (user_id, level, points) values (3, 1, 0);
insert into core_forest (user_id, level, points) values (4, 1, 0);

insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 2);
insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 3);
insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 4);
insert into core_forest_friends (from_forest_id, to_forest_id) values (2, 3);
insert into core_forest_friends (from_forest_id, to_forest_id) values (2, 4);

insert into core_itemtype (name) values ('Normal');
insert into core_itemtype (name) values ('Special');

insert into core_itemcategory (name) values ('Animals');
insert into core_itemcategory (name) values ('Clothes');
insert into core_itemcategory (name) values ('Others');

insert into core_item (name, description, image, costs, min_level, item_type_id, category_id, allowed_category_id) values ('Tie', 'The tie is nice!', '', 10, 1, 1, 2, 1);
insert into core_item (name, description, image, costs, min_level, item_type_id, category_id, allowed_category_id) values ('Gordon', '..the frog!', '', 30, 3, 2, 1, -1);

insert into core_forest_items (forest_id, item_id) values (1, 2);
insert into core_forest_items (forest_id, item_id) values (2, 2);

