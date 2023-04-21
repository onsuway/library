<template>
    <div style="text-align: center; margin: 0 20px">
        <div style="margin-top: 100px">
            <div style="font-size: 25px;font-weight: bold">注册新用户</div>
            <div style="font-size: 14px; color: grey">欢迎注册，请在下方填写相关信息</div>
        </div>
        <div style="margin-top: 50px">
            <el-form
                    :model="form"
                    :rules="rules"
                    @validate="onValidate"
                    ref="formRef"
            >
                <el-form-item prop="username">
                    <el-input v-model="form.username" type="text" placeholder="用户名">
                        <template #prefix>
                            <el-icon>
                                <User/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="form.password" type="password" placeholder="密码">
                        <template #prefix>
                            <el-icon>
                                <Lock/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password_repeat">
                    <el-input v-model="form.password_repeat" type="password" placeholder="重复密码">
                        <template #prefix>
                            <el-icon>
                                <Lock/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input v-model="form.email" type="email" placeholder="邮箱地址">
                        <template #prefix>
                            <el-icon>
                                <Message/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row :gutter="8">
                        <el-col :span="17">
                            <el-input v-model="form.code" type="text" placeholder="请输入验证码">
                                <template #prefix>
                                    <el-icon>
                                        <EditPen/>
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-button @click="validateEmail()" type="success" :disabled="!isEmailValid">获取验证码</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin-top: 80px">
            <el-button @click="register()" style="width: 270px" type="warning" plain>立即注册</el-button>
        </div>
        <div style="margin-top: 20px;">
            <span style="font-size: 15px;color: grey">已有账号？</span>
            <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
        </div>
    </div>
</template>

<script setup>
import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const formRef = ref()

const form = reactive({
    username: '',
    password: '',
    password_repeat: '',
    email: '',
    code: '' //验证码
})

//验证用户名合法性
const validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入用户名'))
    } else if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(value)) {
        callback(new Error('用户名不能包含特殊字符，只能是中英文'))
    } else {
        callback()
    }
}

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
//验证两次输入密码必须一致
const validatePasswordRepeat = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== form.password) {
        callback(new Error('两次输入的密码不一致'))
    } else {
        callback()
    }
}


const rules = {
    username: [
        {validator: validateUsername, trigger: ['blur', 'change']},
        {min: 2, max: 8, message: '用户名的长度必须在2-8个字符', trigger: ['blur', 'change']},
    ],
    password: [
        {validator: validatePassword, trigger: ['blur', 'change']},
        {min: 6, max: 16, message: '密码的长度必须在6-16个字符', trigger: ['blur', 'change']},
    ],
    password_repeat: [
        {validator: validatePasswordRepeat, trigger: ['blur', 'change']},
    ],
    email: [
        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
        {type: 'email', message: '请输入合法的邮箱地址', trigger: ['blur', 'change']}
    ],
    code: [
        {required: true, message: '请输入获取的验证码', trigger: 'blur'},
    ]
}

//邮箱地址是否验证通过
const isEmailValid = ref(false)

const onValidate = (prop, isValid) => {
    if (prop === 'email') {
        isEmailValid.value = isValid
    }
}

const register = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {

        }else{
            ElMessage.warning('请完整填写注册信息')
        }
    })
}

const validateEmail = () => {
    post('/api/auth/valid-email', {
        email: form.email
    },(message) => {
        ElMessage.success(message)
    })

}
</script>

<style scoped>

</style>