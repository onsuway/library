# 线上图书馆系统

---

springboot + vue 包含管理员后端系统/读者借阅系统前后台的线上图书馆

> 这个项目是本人第一个练手的小项目 
  - 前端页面主要模仿自henu图书馆官网 
  - 登录注册模块基于 [青空の霞光（白马大专讲师）](https://space.bilibili.com/37737161) 的登录验证开发
  - 后端接口完全由自己怎么想怎么来
  - 时间大概了写了一周（一周只要没课都在写这个小项目） 
  - 写的不好请见谅🙂


## 技术栈
### 后端
- SpringBoot 3
- Spring Security
- Redis(基于Session的权限校验)
- MySQL + Mybatis(没用plus 纯写SQL语句
### 前端
- vue3、router、pinia
- vite
- Element-Plus
- axios

---

## 功能

### 管理员
- 书籍增删改查
- 借阅的强制归还/强制续借(无视是否逾期/是否续借过)
- 用户信用积分重置/更改用户角色(student/teacher)
- 修改密码

### 读者
- 书籍的借阅/归还
  - 借阅时需满足
    1. 用户信用积分 > 0
    2. student正在借阅数量 < 3 || teacher正在借阅数量 < 5
    3. 是否借阅过这本书
    4. 这本书库存 > 0
  - 归还时检查是否逾期，逾期则信用积分-1
- 热门借阅榜单
  - 可选分类
  - 可选榜单统计范围（全部/周/月/年
- 个人正在借阅/历史借阅
- 修改密码
