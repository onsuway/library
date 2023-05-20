<template>
    <div class="user-layout">
        <el-backtop :right="100" :bottom="100" />
        <el-container style="flex-direction: column">
            <el-affix>
                <el-header class="content-header">
                    <div style="flex: 1">
                        <el-image
                            src="https://image.itbaima.net/images/90/image-20230514153349528.png"
                            @click="router.push('/user/home')"
                            class="logo"
                        />
                    </div>
                    <div style="flex: 0.7">
                        <el-menu
                            mode="horizontal"
                            background-color="#66b1ff"
                            text-color="white"
                            active-text-color="black"
                            router
                            :default-active="router.currentRoute.value.fullPath"
                        >
                            <el-menu-item index="/user/home">书籍检索</el-menu-item>
                            <el-menu-item index="/user/hotBorrow">热门借阅</el-menu-item>
                            <el-menu-item index="/user/me/myBorrow">个人借阅</el-menu-item>
                        </el-menu>
                    </div>
                    <div style="text-align: right;margin: 15px 10px 0 0; line-height: 15px">
                        <div style="font-weight: bold;font-size: 18px">{{ userStore.userInfo.username }}</div>
                        <div style="color: grey;font-size: 14px;">{{ userStore.userInfo.email }}</div>
                    </div>
                    <div style="margin-top: 8px">
                        <el-dropdown>
                            <el-avatar
                                class="user-avatar"
                                src="https://image.itbaima.net/images/90/image-2023042612172367.jpeg"
                            />
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item :icon="User" @click="router.push('/user/me/myInfo')">个人信息
                                    </el-dropdown-item>
                                    <el-dropdown-item :icon="SwitchButton" divided @click="logout">退出登录
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                </el-header>
            </el-affix>
            <el-main style="padding: 0">
                <router-view :key="router.currentRoute.value.fullPath"/>
            </el-main>
        </el-container>
    </div>
</template>

<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserStore} from "@/stores";
import {
    SwitchButton,
    User
} from "@element-plus/icons-vue";


const userStore = useUserStore();

const logout = () => {
    get('/api/auth/logout', (message) => {
        ElMessage.success(message)
        userStore.logout()
        router.push('/')
    })
}
</script>

<style>
.user-avatar:hover {
    cursor: pointer;
}

.logo:hover {
    cursor: pointer;
}

.content-header {
    background-color: #66b1ff;
    padding: 0 10px;
    display: flex;
    height: 63px;
}


</style>
