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


## 项目说明
### 代码生成
项目采用Mybatis Plus的AutoGenerator代码生成器，可以生成前后端代码，降低重复代码编写。
- 修改`GeneratorMain`中的常量参数，然后直接运行`main`方法，即可在对应的目录中生成相关代码
- 在数据库中运行生成的`insertmenu.sql`文件, 向菜单中添加相应的菜单资源
- 在前端项目工程中`/src/api`中新建一个`模块名`的文件夹，将生成的js文件拷贝之中，将`表名/index.vue`拷贝到`/src/view`中
- 删除前端代码和生成的sql文件


| 配置参数   | 说明 | 备注 |
| :- | :- | :-  |
| baseProjectPath       | 生成文件所在项目路径     |     |
| srcJavaPath           | 文件存放路径     |     |
| basePackage           | 基础包路径     |     |
| authorName            | 代码作者名称    |     |
| driverName            | 数据库驱动     |     |
| url                   | 数据库连接地址     |     |
| username              | 数据库用户名     |     |
| password              | 数据库密码     |     |
| fileOverride          | 是否覆盖文件     |     |
| prefix                | 表前缀名称     |     |
| modulesName           | 模块名称     |     |
| tables                | 要生成的表名     |     |

注意: 代码生成是直接对数据库表直接读取，没有对字段进行细化区分，生成的前端代码与实际业务需求不符，需要自己手动修改相应页面显示数据与表单类型，来实现自己的实际业务需求。

### 项目结构说明
```
cn.dreamchan
|----common                  //基础包
    |----base                //封装基础类
    |----biz                 //封装返回数据
    |----constant            //常量
    |----enums               //枚举
    |----exception           //自定义异常
    |----tree                //树形结构
    |----utils               //工具类
|----component
    |----aop                 //注解处理
    |----converter           //自定义转换器
    |----event               //事件
    |----handler             //处理器
    |----listener            //监听器
    |----redis               //redis服务类
    |----security            //配置
|----config                  //组件使用配置
|----generate                //代码生成
|----modules                 //模块
    |----cms                 //测试模块，可参照此模块编写
        |----controller      //控制器
        |----mapper          //mybatis mapper 接口
            |----xml         //mapper 接口对应的xml文件
        |----pojo            //对象
            |----dto         //传输对象
            |----entity      //数据库实体
            |----vo          //前端展示页面实体
        |----service         //service 接口
            |----mapstruct   //实体映射服务
            |----impl        //service 接口实现
    |----login               //登录模块
    |----monitor             //监控模块
    |----oss                 //对象存储模块
    |----system              //系统管理模块
|----FastBootApplication     //启动类
```


## 演示图

![](https://p.pstatp.com/origin/13817000014479c2ff9be)

![](https://p.pstatp.com/origin/ff9300027d9db4a80d06)

![](https://p.pstatp.com/origin/fe7100020dd2b11e2914)

![](https://p.pstatp.com/origin/ff3b000226e6636c3ec3)


