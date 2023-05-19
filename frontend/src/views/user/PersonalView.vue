<template>
<div class="background">
    <div class="content-container margin-auto">
        <div class="menu-aside">
            <el-menu
                popper-effect="light"
                style="border: none"
                router
                :default-active="router.currentRoute.value.fullPath"
            >
                <el-menu-item index="/user/me/myInfo" class="menu-font">个人信息</el-menu-item>
                <el-menu-item index="/user/me/myBorrow" class="menu-font">个人借阅</el-menu-item>
                <el-menu-item index="/user/me/changePwd" class="menu-font">修改密码</el-menu-item>
            </el-menu>
        </div>
        <div class="main-content">
            <router-view/>
        </div>
    </div>
</div>
</template>

<script setup>

import router from "@/router";
</script>

<style scoped>
.background {
    background-color: #f5f5f5;
    padding: 20px;
    height: 89.5vh;
}

.margin-auto {
    margin: 0 auto;
}

.content-container {
    display: flex;
    width: 1200px;
    height: 800px;
    background-color: #f5f5f5;
}

.menu-aside {
    flex: 1;
    padding: 10px;
    margin-right: 20px;
    background-color: #ffffff;
}

.menu-font {
    font-size: 17px;
}

.main-content {
    flex: 7;
    background-color: #ffffff;
}

/* 左侧菜单选中的样式 */
.el-menu-item.is-active {
    background-color: var(--el-menu-hover-bg-color);
}

</style>
