<template>
    <div style="text-align: center; margin: 0 20px">
        <div style="margin-top: 150px">
            <div style="font-size: 25px;font-weight: bold">登录</div>
            <div style="font-size: 14px; color: grey">在进入系统之前请先输入用户名和密码进行登录</div>
        </div>
        <div style="margin-top: 50px">
            <el-input v-model="form.username" type="text" placeholder="用户名/邮箱">
                <template #prefix>
                    <el-icon>
                        <User/>
                    </el-icon>
                </template>
            </el-input>
        </div>
        <div style="margin-top: 10px">
            <el-input v-model="form.password" type="password" placeholder="密码">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
        </div>
        <el-row style="margin-top: 5px">
            <el-col :span="12" style="text-align: left">
                <el-checkbox v-model="form.remember" label="记住我"/>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <el-link @click="router.push('/forget')">忘记密码？</el-link>
            </el-col>
        </el-row>
        <div style="margin-top: 30px">
            <el-button @click="login()" style="width: 270px" type="success" plain>立即登录</el-button>
        </div>

        <el-divider>
            <span style="color: grey; font-size: 13px">没有账号</span>
        </el-divider>
        <div>
            <el-button @click="router.push('/register')" style="width: 270px" type="warning" plain>注册账号</el-button>
        </div>
    </div>
</template>

<script setup>
import {Lock, User} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import router from "@/router";
import {useUserStore} from "@/stores";

const userStore = useUserStore()

const form = reactive({
    username: '',
    password: '',
    remember: false
})

const login = () => {
    if (!form.username || !form.password){
        ElMessage.warning('用户名和密码不能为空')
    }else {
        post('/api/auth/login', {
            username: form.username,
            password: form.password,
            remember: form.remember
        }, (message) => {
            ElMessage.success(message)
            get('/api/user/me', (message) => {
                userStore.login(message)
                router.push('/' + message.role)
            },() => {
                userStore.logout()
            })
        })
    }
}

</script>

<style scoped>

</style>
