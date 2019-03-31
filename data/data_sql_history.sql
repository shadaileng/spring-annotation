-- 表：user
CREATE TABLE user (
    id       INTEGER       PRIMARY KEY AUTOINCREMENT,
    username VARCHAR (128) NOT NULL,
    password VARCHAR (128) NOT NULL,
    email    VARCHAR (128) UNIQUE
                           NOT NULL,
    phone    CHAR (13)     NOT NULL,
    created  DATE          NOT NULL,
    updated  DATE          NOT NULL
);

CREATE TABLE `content` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `category_id` INTEGER,
  `title` varchar(200) NOT NULL,
  `sub_title` varchar(100) NOT NULL,
  `title_desc` varchar(500) NOT NULL,
  `url` varchar(500) NOT NULL,
  `pic` varchar(300) NOT NULL,
  `content` text,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL
);

insert  into `content`(`id`,`category_id`,`title`,`sub_title`,`title_desc`,`url`,`pic`,`content`,`created`,`updated`) values 

(28,89,'标题1','标题1','标题1','http://www.jd.com','http://localhost:9000/images/2015/07/27/1437979301511057.jpg','标题1','2015-07-27 14:41:57','2015-07-27 14:41:57'),

(29,89,'ad2','ad2','ad2','http://www.baidu.com','http://localhost:9000/images/2015/07/27/1437979349040954.jpg','ad2','2015-07-27 14:42:36','2015-07-27 14:42:36'),

(30,89,'ad3','ad3','ad3','http://www.sina.com.cn','http://localhost:9000/images/2015/07/27/1437979377450366.jpg','','2015-07-27 14:42:58','2015-07-27 14:42:58'),

(31,89,'ad4','ad4','ad4','ad4','http://localhost:9000/images/2015/07/27/1437979392186756.jpg','ad4','2015-07-27 14:43:15','2015-07-27 14:43:15');

select c.*, cc.name as category_name from content c left outer join content_category cc on c.category_id = cc.id;
SELECT c.*, cc.name as `contentCategory.name` FROM content as c LEFT OUTER JOIN content_category cc on c.category_id = cc.id  LIMIT ? OFFSET ?;
SELECT c.*, cc.name as `contentCategory.name`, cc.id as `contentCategory.id` FROM content as c LEFT OUTER JOIN content_category cc on c.category_id = cc.id WHERE ( c.id = 28 );

----------------------------------------------
CREATE TABLE content_category (
  id INTEGER PRIMARY KEY AUTOINCREMENT, 
  parent_id INTEGER NOT NULL, 
  name varchar (50) NOT NULL, 
  status int (1) NOT NULL DEFAULT '1', 
  sort_order int (4) NOT NULL, 
  id_parent tinyint (1) DEFAULT '1' NOT NULL, 
  created datetime NOT NULL, 
  updated datetime NOT NULL
);

insert  into `content_category`(`id`,`parent_id`,`name`,`status`,`sort_order`,`is_parent`,`created`,`updated`) values 

(30,0,'LeeShop',1,1,1,'2015-04-03 16:51:38','2015-04-03 16:51:40'),

(86,30,'首页',1,1,1,'2015-06-07 15:36:07','2015-06-07 15:36:07'),

(87,30,'列表页面',1,1,1,'2015-06-07 15:36:16','2015-06-07 15:36:16'),

(88,30,'详细页面',1,1,1,'2015-06-07 15:36:27','2015-06-07 15:36:27'),

(89,86,'大广告',1,1,0,'2015-06-07 15:36:38','2015-06-07 15:36:38'),

(90,86,'小广告',1,1,0,'2015-06-07 15:36:45','2015-06-07 15:36:45'),

(91,86,'商城快报',1,1,0,'2015-06-07 15:36:55','2015-06-07 15:36:55'),

(92,87,'边栏广告',1,1,0,'2015-06-07 15:37:07','2015-06-07 15:37:07'),

(93,87,'页头广告',1,1,0,'2015-06-07 15:37:17','2015-06-07 15:37:17'),

(94,87,'页脚广告',1,1,0,'2015-06-07 15:37:31','2015-06-07 15:37:31'),

(95,88,'边栏广告',1,1,0,'2015-06-07 15:37:56','2015-06-07 15:37:56'),

(96,86,'中广告',1,1,1,'2015-07-25 18:58:52','2015-07-25 18:58:52'),

(97,96,'中广告1',1,1,0,'2015-07-25 18:59:43','2015-07-25 18:59:43');

SELECT a.*,ifnull(b.id, '0'), ifnull(b.name, '/') FROM CONTENT_CATEGORY a left outer join CONTENT_CATEGORY b on a.parent_id = b.id order by a.parent_id, a.`sort_order`, a.`is_parent`;