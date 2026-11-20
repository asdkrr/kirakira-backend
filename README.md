#kirakira-server
本网站仿b站后端建造

技术栈：SpringBoot 2.7.18 / JDK 8 / MyBatis-Plus / MySQL 8 / JWT
## 本地启动
1. 建库：mysql -uroot -p < src/main/resources/sql/schema-mysql.sql
2. 建账号：CREATE USER 'example'@'localhost' IDENTIFIED BY 'answer';
   GRANT ALL PRIVILEGES ON example.* TO 'example'@'localhost';
3. 改 application.yml 里的数据库密码为你创建数据库设置的
4. mvnw spring-boot:run
5. 访问 http://localhost:7070/home/videos

## 接口列表
| 方法 | 路径 | 说明 | 需要登录 |
| --- | --- | --- | --- |
| GET | /home/videos | 视频列表（分页） | 否 |
| GET | /video/getone?vid= | 视频详情 | 否 |
| GET | /danmu/get?vid= | 弹幕列表 | 否 |
| POST | /danmu/send | 发弹幕 | 是 |
| GET | /search/video?keyword= | 搜视频 | 否 |
| GET | /search/user?keyword= | 搜用户 | 否 |
| GET | /space/user?uid= | 空间信息 | 否 |
| GET | /space/videos?uid= | 空间投稿 | 否 |
| POST | /user/register | 注册 | 否 |
| POST | /user/login | 登录 | 否 |
| GET | /user/info | 当前用户 | 是 |
| GET | /comment/get?vid= | 评论（占位，返回空） | 否 |

## 测试账号

| 账号 | 密码 | 说明 |
| --- | --- | --- |
| admin | 123456 | 管理员，种子数据自动创建 |