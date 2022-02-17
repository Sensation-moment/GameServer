一个简易游戏服务器框架

- 项目框架
  - JDK 11
  - Maven 3.8.2
  - SpringBoot 2
  - MyBatisPlus 
  - MySQL 5.7
  
- 实现的功能

  - 各种实体类的定义，各种操作消息类，英雄道具实体类以及模板、玩家缓存类型

  - 服务端与客户端消息码的定义与实现
  - 对可能出现的各种异常的定义、捕获以及统一管理
  - 使用MyBatisPlus实现实体类的映射，实现持久层
  - 玩家的账号注册和登录
  - 加入属性，实现玩家对应英雄的等级、血量、攻击力、防御力、星级等属性
  - 加入对英雄的操作，英雄的创建、属性初始化、升星、升级等功能
  - 加入背包道具系统，删除使用消耗道具、查看道具等功能的实现
  - 定时进行持久化，将缓存中的玩家数据持久化到数据库保存起来