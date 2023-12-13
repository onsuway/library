<template>
    <el-form
        label-position="right"
        label-width="100px"
        :model="formChangePassword"
        style="max-width: 460px"
        ref="formRef"
        :rules="rules"
        class="changePwd-form"
    >
        <el-form-item label="原密码" prop="current_password">
            <el-input v-model="formChangePassword.current_password" type="password"/>
        </el-form-item>
        <el-form-item label="新密码" prop="new_password">
            <el-input v-model="formChangePassword.new_password" type="password"/>
        </el-form-item>
        <el-form-item label="重复新密码" prop="new_password_repeat">
            <el-input v-model="formChangePassword.new_password_repeat" type="password"/>
        </el-form-item>
        <el-form-item>
            <el-button plain style="margin-left: 100px; margin-top: 20px" type="primary" @click="submitNewPassword()">
                确认更改
            </el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import {reactive, ref} from "vue";
import {post} from "@/net";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores";
import router from "@/router";

const userStore = useUserStore()

const formRef = ref()

const formChangePassword = reactive({
    current_password: '',
    new_password: '',
    new_password_repeat: '',
})

//验证密码合法性
const validatePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else if (/\s+/.test(value)) {
        callback(new Error('密码不能包含空格'))
    } else {
        callback()
    }
}

//验证新密码不能和原有密码一样
const validateNewPassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else if (/\s+/.test(value)) {
        callback(new Error('密码不能包含空格'))
    } else if (value === formChangePassword.current_password) {
        callback(new Error('新密码不能和原密码一样'))
    } else {
        callback()
    }
}

//验证两次输入密码必须一致
const validatePasswordRepeat = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入新密码'))
    } else if (value !== formChangePassword.new_password) {
        callback(new Error('两次输入的新密码不一致'))
    } else {
        callback()
    }
}

const rules = {
    current_password: [
        {validator: validatePassword, trigger: ['blur', 'change']},
        {min: 6, max: 16, message: '密码的长度在6-16个字符', trigger: ['blur', 'change']},
    ],
    new_password: [
        {validator: validateNewPassword, trigger: ['blur', 'change']},
        {min: 6, max: 16, message: '密码的长度必须在6-16个字符', trigger: ['blur', 'change']},
    ],
    new_password_repeat: [
        {validator: validatePasswordRepeat, trigger: ['blur', 'change']},
    ],
}

const submitNewPassword = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {
            post('/api/user/reset-password', {
                account_id: userStore.userInfo.id,
                current_password: formChangePassword.current_password,
                new_password: formChangePassword.new_password,
            }, (message) => {
                ElMessage.success(message)
                router.push('/user/home')
            }, (message) => {
                ElMessage.warning(message)
            })
        } else {
            ElMessage.warning('请完整填写信息')
        }
    })
}
</script>
<style scoped>
.changePwd-form {
    margin: 100px auto;
}
</style>
