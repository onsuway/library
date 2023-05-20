<template>
    <div style="height: 100vh;">
        <el-container style="height: 100%;">
            <el-aside width="200px" class="content-aside" :style="{width: hideMenu? '0' : '250px'}">
                <div style="height: 60px;line-height: 60px;text-align: center;transition: 0.3s;font-weight: bold; font-size: 20px"
                     :style="{opacity: hideMenu? '0' : '1'}">
                    Henu图书馆管理端
                </div>
                <el-menu
                        style="height: calc(100% - 60px);border: none"
                        router
                        unique-opened
                        :default-active="router.currentRoute.value.fullPath"
                >
                    <el-menu-item index="/admin/home">
                        <el-icon>
                            <HomeFilled/>
                        </el-icon>
                        <span>主页</span>
                    </el-menu-item>
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon>
                                <MessageBox/>
                            </el-icon>
                            <span>管理页面</span>
                        </template>
                        <el-menu-item index="/admin/book">
                            <el-icon>
                                <Collection/>
                            </el-icon>
                            <span>书籍管理</span>

                        </el-menu-item>
                        <el-menu-item index="/admin/borrow">
                            <el-icon>
                                <List/>
                            </el-icon>
                            <span>借阅管理</span>
                        </el-menu-item>
                        <el-menu-item index="/admin/userAdmin">
                            <el-icon>
                                <UserFilled/>
                            </el-icon>
                            <span>用户管理</span>
                        </el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="2">
                        <template #title>
                            <el-icon>
                                <Setting/>
                            </el-icon>
                            <span>个人设置</span>
                        </template>
                        <el-menu-item index="/admin/me">
                            个人信息
                        </el-menu-item>
                        <el-menu-item index="/admin/changePassword">
                            修改密码
                        </el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header class="content-header">
                    <div style="flex: 1">
                        <el-button style="margin-top: 8px;font-size: 20px;width: 20px"
                                   :icon="hideMenu ? Expand : Fold" text @click="hideMenu = !hideMenu"/>
                    </div>

                    <div style="text-align: right;margin: 13px 10px 0 0; line-height: 14px">
                        <div style="font-weight: bold;font-size: 18px">{{ userStore.userInfo.username }}</div>
                        <div style="color: grey;font-size: 14px;">{{ userStore.userInfo.email }}</div>
                    </div>
                    <div style="margin-top: 5px">
                        <el-dropdown>
                            <el-avatar
                                    class="user-avatar"
                                    src="https://image.itbaima.net/images/90/image-2023042612172367.jpeg"
                            />
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item :icon="User">个人信息</el-dropdown-item>
                                    <el-dropdown-item :icon="SwitchButton" divided @click="logout">退出登录
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                </el-header>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserStore} from "@/stores";
import {
    Collection,
    Expand, Fold, HomeFilled, List, MessageBox,
    Setting,
    SwitchButton,
    User, UserFilled
} from "@element-plus/icons-vue";
import {ref} from "vue";

const hideMenu = ref(false)

const userStore = useUserStore();

const logout = () => {
    get('/api/auth/logout', (message) => {
        ElMessage.success(message)
        userStore.logout()
        router.push('/')
    })
}
</script>

<style scoped>
.user-avatar:hover {
    cursor: pointer;
}

.content-header {
    padding: 0 10px;
    display: flex;
    height: 50px;
    border-bottom: solid 1px #e0e0e0;
}


.content-aside {
    width: 250px;
    border-right: solid 1px #e0e0e0;
    transition: 0.4s;
}

</style>
