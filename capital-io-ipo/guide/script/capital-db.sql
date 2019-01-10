-- # 查看字符集
SHOW VARIABLES LIKE 'character_set_%';

-- # 列出MYSQL支持的所有字符集
SHOW CHARACTER SET;

-- # 查看mysql 中用户信息
SELECT DISTINCT CONCAT('User: ''',user,'''@''',host,''';') AS query FROM mysql.user;

-- # 查看最大连接数
show variables like '%max_connections%';

-- # 重新设置
set global max_connections=1000

show status like 'Threads%';

-- # TimeZone CST
select curtime();
select now();
show variables like "%time_zone%";

-- # > BASE
-- # 创建数据库
CREATE DATABASE capital_pre_ipo;

-- # 创建用户
CREATE USER capital_pre_ipo@'%' IDENTIFIED BY 'capital_pre_ipo_pw';

-- # 授权
GRANT ALL PRIVILEGES ON capital_pre_ipo.* TO capital_pre_ipo@'%';