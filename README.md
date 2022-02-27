## 项目介绍

这是一个采用前后端分离开发的项目，前端采用安卓开发、后端采用 Spring boot + Mybatis 开发。

**2021/2022冬季学期 上海大学数据库大作业**


## 项目部署

1. 建立数据库
```shell
mysqld -u username -p password DataBaseName < student.sql
```
2. maven打包jar
```shell
java -jar student.jar
```
3. 安装Android端应用
```
adb install student.apk
```
## 项目展示


1. 登陆界面

<img src="http://flopsyyan-typora.oss-cn-beijing.aliyuncs.com/img/login.jpeg" alt="login" style="zoom:30%;" />

2. admin 主界面

<img src="http://flopsyyan-typora.oss-cn-beijing.aliyuncs.com/img/admin.jpeg" alt="admin" style="zoom:50%;" />

4. 学生端

<img src="http://flopsyyan-typora.oss-cn-beijing.aliyuncs.com/img/student.jpeg" alt="student" style="zoom:50%;" />

5. 教师端

<img src="http://flopsyyan-typora.oss-cn-beijing.aliyuncs.com/img/teacher.jpeg" alt="teacher" style="zoom:50%;" />

## 实现的功能

1. admin
   实现对教师、学生、 课程的 CRUD，实现对数据库的完全控制
2. teacher 
   实现查询我开设的课程, 以及选择我课程的学生信息，以及对学生成绩的登记、修改
3. student
   实现选课退课的功能，实现成绩查询的功能

# 数据库设计

![image-20220227125737060](http://flopsyyan-typora.oss-cn-beijing.aliyuncs.com/img/image-20220227125737060.png)

