-- delete from file
Create table if not exists person(
    id INTEGER primary key AUTOINCREMENT,
    name varchar(200) not null,
    gender tinyint(1) not null
);

insert into person(name, gender) values('shadaleng', 1);
insert into person(name, gender) values('cy', 0);
insert into person(name, gender) values('cxl', 0);

select * from person;
-------------------------------------------
Create table user (
    id INTEGER primary key AUTOINCREMENT,
    username varchar(128) not null,
    password varchar(128) not null,
    email varchar(128) unique
);
insert into user(username, password, email) values ('qpf', 'qpf', 'qpf@qq.com');
insert into user(username, password, email) values ('sdl', 'sdl', 'sdl@qq.com');
insert into user(username, password, email) values ('cy', 'cy', 'cy@qq.com');
insert into user(username, password, email) values ('cxl', 'cxl', 'cxl@qq.com');

SELECT * FROM USER LIMIT 5 OFFSET 0;
SELECT * FROM USER where username like '%'||'c'||'%' LIMIT 5 OFFSET 0;
--------------------------------------------------------------------
Create table role (
    id INTEGER primary key AUTOINCREMENT,
    name varchar(128) not null
);
insert into role(name) values('PM - 项目经理'), ('SE - 软件工程师'), ('PG - 程序员'), ('TL - 组长'), ('GL - 组长'), ('QA - 品质保证'), ('QC - 品质控制'), ('SA - 软件架构师'), ('CMO / CMS - 配置管理员');
select * from role;
SELECT * FROM ROLE  WHERE name like '%'||''||'%' LIMIT ? OFFSET ?;
-------------------------------------------------------------------------------
Create table user_role(
    id INTEGER primary key AUTOINCREMENT,
    userid INTEGER not null,
    roleid INTEGER not null    
);
insert into user_role(userid, roleid) values(1, 3), (1, 4), (2, 5), (3, 5), (4, 6);
select * from user_role;
SELECT * FROM USER_ROLE WHERE userid = 1;
SELECT roleid FROM USER_ROLE WHERE  userid = 4;
----------------------------------------------------------------------------------
Create table permission(
    id INTEGER primary key AUTOINCREMENT,
    pid INTEGER not null,
    name VARCHAR(128) not null,
    url VARCHAR(256) not null,
    icon VARCHAR(128) not null
);
insert into permission(pid, name, url, icon) values
(0, '控制面板', '/user/index', '<ion-icon name="speedometer"></ion-icon>'), 
(1, '权限管理', '-', '<ion-icon name="list"></ion-icon>'), 
(2, '用户维护', '/user/list', '<ion-icon name="man"></ion-icon>'), 
(2, '角色维护', '/role/list', '<ion-icon name="ribbon"></ion-icon>'), 
(2, '许可维护', '/permission/list', '<ion-icon name="ribbon"></ion-icon>'), 
(1, '业务审核', '-', '<ion-icon name="checkmark-circle"></ion-icon>'), 
(6, '实名认证审核', '-', '<ion-icon name="checkmark-cirle-outline"></ion-icon>'), 
(6, '广告审核', '-', '<ion-icon name="checkmark-cirle-outline"></ion-icon>'), 
(6, '项目维护', '-', '<ion-icon name="checkmark-cirle-outline"></ion-icon>'), 
(1, '业务管理', '-', '<ion-icon name="checkmark-cirle-outline"></ion-icon>'), 
(9, '资质维护', '-', '<ion-icon name="images"></ion-icon>'), 
(9, '分类管理', '-', '<ion-icon name="keypad"></ion-icon>'), 
(9, '流程管理', '-', '<ion-icon name="shuffle"></ion-icon>'), 
(9, '广告管理', '-', '<ion-icon name="desktop"></ion-icon>'), 
(9, '消息模板', '-', '<ion-icon name="chatbubbles"></ion-icon>'), 
(9, '项目分类', '-', '<ion-icon name="folder-open"></ion-icon>'), 
(9, '项目标签', '-', '<ion-icon name="star"></ion-icon>');
Create table role_permission(
    id INTEGER primary key AUTOINCREMENT,
    roleid INTEGER not null,
    permissionid INTEGER not null
);
select * from permission;
----------------------------------