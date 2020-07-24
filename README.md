# fast-boot管理系统后端项目

## 项目介绍
**fast-boot-backend**是**fast-boot**前后台端分离项目的后端项目，基于 Spring Boot 、Spring Security、Mybatis Plus搭建。
**fastboot**是一款前后端分离的快速开发脚手架，采用Mybatis Plus的AutoGenerator代码生成器，可以快速生成前后端代码，快速提高开发效率，减少重复代码的编写。

## 演示地址
[http://122.51.105.16:3000/](http://122.51.105.16:3000/)
账号：admin
密码：admin123

druid数据库监控
账号: admin
密码: admin

## 技术选型
- Spring Boot
- Spring Security
- Mybatis Plus
- Hibernator-Validator
- minio
- druid
- knife4j
- lombok
- mapstruct
- redis
- jwt
- lombok
- easypoi
- easy-captcha

### 功能介绍
- 用户管理: 提供系统用户的管理
- 角色管理: 分配角色的菜单权限，可根据部门设置角色的数据权限
- 菜单管理: 配置多级菜单，结合前端的动态路由管理
- 部门管理: 管理公司部门、子部门
- 岗位管理: 管理各个部门的职位
- 字典管理: 维护一些常用固定的数据，如：状态、性别
- 登录日志: 记录系统用户的登录日志
- 操作日志: 记录系统用户的操作日志
- 在线用户: 在线用户管理，可强制线下
- 接口文档: 可在线调试接口，方便开发测试
- 数据库监控: 监控系统数据库状态，查询慢sql
- 代码生成: 采用AutoGenerator代码生成器，减少重复代码开发

## 本地运行
```
# 克隆项目
git clone https://github.com/DreamChan/fast-boot-backend.git

# 创建数据库本地数据库fast-boot,  运行sql/fast-boot.sql文件

# 修改配置
将 application-dev.yml 文件中的 mysql、redis、minio连接地址和账号等修改为自己的本地开发环境连接地址和账号

# 启动运行项目
FastBootApplication
```

## 演示图

![](https://p.pstatp.com/origin/13817000014479c2ff9be)

![](https://p.pstatp.com/origin/ff9300027d9db4a80d06)

![](https://p.pstatp.com/origin/fe7100020dd2b11e2914)

![](https://p.pstatp.com/origin/ff3b000226e6636c3ec3)


