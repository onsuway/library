# 线上图书馆系统

---

springboot + vue 包含管理员后端系统/读者借阅系统前后台的线上图书馆

[GitHub仓库地址](https://github.com/syp1016620694/library)

登录模块基于 [青空の霞光（白马大专讲师）](https://space.bilibili.com/37737161) 的登录验证开发


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

## 欢迎界面（通用

### 登录

![](https://i.imgloc.com/2023/05/21/V4tFnQ.jpeg)

### 注册

![](https://i.imgloc.com/2023/05/21/V4tdsC.jpeg)

### 忘记密码

![](https://i.imgloc.com/2023/05/21/V4tGcE.jpeg)


## 管理员端

### 主页

![](https://i.imgloc.com/2023/05/21/V4tccU.jpeg)

### 书籍管理

![](https://i.imgloc.com/2023/05/21/V4tNxk.jpeg)

### 借阅管理

![](https://i.imgloc.com/2023/05/21/V4tIlx.jpeg)

### 用户管理

![](https://i.imgloc.com/2023/05/21/V4trLL.jpeg)

### 个人设置

#### 修改密码

![](https://i.imgloc.com/2023/05/21/V4tjQa.jpeg)




## 用户端
### 主页

![](https://i.imgloc.com/2023/05/21/V4tzMH.jpeg)

### 热门借阅

![](https://i.imgloc.com/2023/05/21/V4tgF5.jpeg)

### 个人借阅

#### 个人信息

![](https://i.imgloc.com/2023/05/21/V4tX8y.jpeg)

#### 当前借阅

![](https://i.imgloc.com/2023/05/21/V4tmov.jpeg)

#### 历史借阅

![](https://i.imgloc.com/2023/05/21/V4tBf3.jpeg)

#### 修改密码

![](https://i.imgloc.com/2023/05/21/V4texF.jpeg)

### 书籍详情页

![](https://i.imgloc.com/2023/05/21/V4tEwZ.jpeg)

### 书籍搜索列表

![](https://i.imgloc.com/2023/05/21/V4txY8.jpeg)	

