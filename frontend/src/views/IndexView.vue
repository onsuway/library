<template>
    <div>欢迎进入！</div>
    <div>
        <el-button @click="logout()" type="danger" plain>退出登录</el-button>
    </div>
</template>

<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserStore} from "@/stores";

const userStore = useUserStore();

const logout = () => {
    get('/api/auth/logout', (message) => {
        ElMessage.success(message)
        userStore.auth.user = null
        router.push('/')
    })
}
</script>

<style scoped>

</style>