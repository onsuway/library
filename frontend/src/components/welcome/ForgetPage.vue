<template>
    <div>
        <div style="margin: 30px 20px;">
            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="验证邮箱"/>
                <el-step title="重设密码"/>
            </el-steps>
        </div>
        <transition name="el-fade-in-linear" mode="out-in">
            <div style="text-align: center; margin: 0 20px; height: 100%;" v-if="active === 0">
                <div style="margin-top: 80px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 14px; color: grey">请验证需要重置密码账户的邮箱地址</div>
                </div>
                <div style="margin-top: 50px">
                    <el-form
                            :model="form"
                            :rules="rules"
                            @validate="onValidate"
                            ref="formRef"
                    >
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
                                <el-col :span="18">
                                    <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                                        <template #prefix>
                                            <el-icon>
                                                <EditPen/>
                                            </el-icon>
                                        </template>
                                    </el-input>
                                </el-col>
                                <el-col :span="6">
                                    <el-button @click="validateEmail()" type="success"
                                               :disabled="!isEmailValid || coldTime > 0">
                                        {{ coldTime > 0 ? '请' + coldTime + '秒后再试' : "获取验证码" }}
                                    </el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-form>
                </div>
                <div style="margin-top: 70px">
                    <el-button @click="startReset()" style="width: 270px" type="danger" plain>开始重置密码</el-button>
                </div>
            </div>
        </transition>
        <transition name="el-fade-in-linear" mode="out-in">
            <div style="text-align: center; margin: 0 20px; height: 100%;" v-if="active === 1">
                <div style="margin-top: 80px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 14px; color: grey">请填写新密码</div>
                </div>
                <div style="margin-top: 50px">
                    <el-form
                            :model="form"
                            :rules="rules"
                            @validate="onValidate"
                            ref="formRef"
                    >
                        <el-form-item prop="password">
                            <el-input v-model="form.password" :maxlength="16" type="password" placeholder="新密码">
                                <template #prefix>
                                    <el-icon>
                                        <Lock/>
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="password_repeat">
                            <el-input v-model="form.password_repeat" :maxlength="16" type="password"
                                      placeholder="重复新密码">
                                <template #prefix>
                                    <el-icon>
                                        <Lock/>
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <div style="margin-top: 70px">
                    <el-button @click="doReset()" style="width: 270px" type="danger" plain>立即重置密码</el-button>
                </div>
            </div>
        </transition>

    </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";
import {post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

//邮箱地址是否验证通过
const isEmailValid = ref(false)

const active = ref(0)

const formRef = ref()

const coldTime = ref(0)

const form = reactive({
    email: '',
    code: '',
    password: '',
    password_repeat: '',
})

const onValidate = (prop, isValid) => {
    if (prop === 'email') {
        isEmailValid.value = isValid
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
    email: [
        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
        {type: 'email', message: '请输入合法的邮箱地址', trigger: ['blur', 'change']}
    ],
    code: [
        {required: true, message: '请输入获取的验证码', trigger: 'blur'},
    ],
    password: [
        {validator: validatePassword, trigger: ['blur', 'change']},
        {min: 6, max: 16, message: '密码的长度必须在6-16个字符', trigger: ['blur', 'change']},
    ],
    password_repeat: [
        {validator: validatePasswordRepeat, trigger: ['blur', 'change']},
    ],
}


const validateEmail = () => {
    coldTime.value = 60
    post('/api/auth/valid-reset-email', {
        email: form.email
    }, (message) => {
        ElMessage.success(message)
        setInterval(() => coldTime.value--, 1000)
    },(message) => {
        ElMessage.warning(message)
        coldTime.value = 0
    })
}

const startReset = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {
            post('/api/auth/start-reset', {
                email: form.email,
                code: form.code,
            }, () => {
                active.value++
            })
        } else {
            ElMessage.warning('请填写邮箱地址和验证码')
        }
    })
}

const doReset = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {
            post('/api/auth/do-reset', {
                password: form.password
            }, (message) => {
                ElMessage.success(message);
                router.push('/')
            })
        } else {
            ElMessage.warning('请填写新密码')
        }
    })
}

</script>

<style scoped>

</style>