-- setting up the database
-- be sure to register users if they don't exist in the database



insert into core_itemtype (name) values ('Normal');
insert into core_itemtype (name) values ('Special');

insert into core_itemcategory (name) values ('Animals');
insert into core_itemcategory (name) values ('Clothes');
insert into core_itemcategory (name) values ('Others');

insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Fichte', 'Dies ist eine Fichte.
    Zum Erlangen
    muss man mindestens
    eine Waldgröße von 85 m² erreicht haben.', 100, 17, 0, 0, "standard", "plants", 1);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Laubbaum', 'Dies ist ein Laubbaum.
    Es ist ein Startgegenstand.', 15, 1, 0, 0, "standard", "plants", 2);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Gordon der Frosch', 'SPEZIALGEGENSTAND!
    Dieser Gegenstand
    ist nicht zu kaufen!
    Man erlangt ihn fuer
    herrausragendes Fahren!', 0, 0, 0, 1, "special", "animals", 3);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Busch', 'Dies ist ein Busch.
    Er ist ein Startgegenstand.', 95, 1, 0, 0, "standard", "plants", 4);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Blume', 'Dies ist eine Tuple.
    Zum Erlangen
    muss man mindestens
    eine Waldgröße von 130 m² erreicht haben.', 20, 26, 0, 0, "standard", "plants", 5);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Vogel', 'Dies ist ein Vogel.
    Zum Erlangen
    muss man mindestens
    eine Waldgröße von 130 m² erreicht haben.', 20, 26, 0, 0, "standard", "animals", 6);
insert into core_item (name, description, price, level, amount, moveable, type, category, imageId) values ('Kleid', 'Dies ist ein Kleid.
    Zum Erlangen
    muss man mindestens
    eine Waldgröße von 130 m² erreicht haben.', 35, 26, 0, 0, "standard", "clothes", 7);

insert into core_forest (user_id, level, points, levelProgessPoints, pointProgress) values (1, 17, 80, 89, 90.0);
insert into core_forest (user_id, level, points, levelProgessPoints, pointProgress) values (2, 17, 80, 89, 90.1);
insert into core_forest (user_id, level, points, levelProgessPoints, pointProgress) values (3, 17, 80, 89, 90.2);
insert into core_forest (user_id, level, points, levelProgessPoints, pointProgress) values (4, 17, 80, 89, 90.3);

insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 2);
insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 3);
insert into core_forest_friends (from_forest_id, to_forest_id) values (1, 4);
insert into core_forest_friends (from_forest_id, to_forest_id) values (2, 3);
insert into core_forest_friends (from_forest_id, to_forest_id) values (2, 4);

insert into core_statistic (timestamp, gainedPoints, consumptions, dataInterval, tripConsumption) values (datetime('now','localtime'), 20, "1.0,7.2,8.3,8.4,8.4", 10, 8.5);
insert into core_statistic (timestamp, gainedPoints, consumptions, dataInterval, tripConsumption) values (datetime('now','localtime'), 15, "1.0,7.2,8.3,8.4,8.4", 10, 8.5);
insert into core_statistic (timestamp, gainedPoints, consumptions, dataInterval, tripConsumption) values (datetime('now','localtime'), 12, "1.0,7.2,8.3,8.4,8.4", 10, 8.5);
insert into core_statistic (timestamp, gainedPoints, consumptions, dataInterval, tripConsumption) values (datetime('now','localtime'), 48, "1.0,7.2,8.3,8.4,8.4", 10, 8.5);

insert into core_forest_statistics (forest_id, statistic_id) values (1, 1);
insert into core_forest_statistics (forest_id, statistic_id) values (1, 2);
insert into core_forest_statistics (forest_id, statistic_id) values (1, 3);
insert into core_forest_statistics (forest_id, statistic_id) values (1, 4);

insert into core_userforestitem (item_id, tileX, tileY, offsetX, offsetY) values (1, 0, 0, 0.5, 0.5);
insert into core_userforestitem (item_id, tileX, tileY, offsetX, offsetY) values (1, 1, 0, 0.5, 0.5);
insert into core_userforestitem (item_id, tileX, tileY, offsetX, offsetY) values (2, 2, 0, 0.5, 0.5);
insert into core_userforestitem (item_id, tileX, tileY, offsetX, offsetY) values (3, 0, 1, 0.5, 0.5);
insert into core_userforestitem (item_id, tileX, tileY, offsetX, offsetY) values (4, 1, 1, 0.5, 0.5);

insert into core_forest_userforestitems (forest_id, userforestitem_id) values (1, 1);
insert into core_forest_userforestitems (forest_id, userforestitem_id) values (1, 2);
insert into core_forest_userforestitems (forest_id, userforestitem_id) values (1, 3);
insert into core_forest_userforestitems (forest_id, userforestitem_id) values (1, 4);
insert into core_forest_userforestitems (forest_id, userforestitem_id) values (1, 5);