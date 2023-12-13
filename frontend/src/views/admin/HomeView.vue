<template>
    <div>
        <div style="height: 40vh;width: 100%;">
            <el-image
                    style="width: 100%; height: 100%;"
                    src="https://image.itbaima.net/images/90/image-20230514118051790.jpeg"
                    fit="cover"/>
        </div>
        <div class="welcome-slogan">
            <div style="font-size: 35px;font-weight: bold">
                欢迎登录图书馆管理员端<br/>
                你可以在这里管理图书馆相关业务
            </div>
        </div>
    </div>

    <div style="margin-top: 120px">
        <el-row :gutter="40">
            <el-col :span="6">
                <el-statistic
                        class="card"
                        :value="userCount"
                        :value-style="valueCss"
                >
                    <template #prefix>
                        <el-icon size="25px">
                            <UserFilled/>
                        </el-icon>
                    </template>
                    <template #title>
                        <div style="align-items: center;padding: 15px;font-size: 20px">
                            读者人数
                        </div>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="bookUniqueCount" class="card" :value-style="valueCss">
                    <template #prefix>
                        <el-icon size="25px">
                            <Notebook/>
                        </el-icon>
                    </template>
                    <template #title>
                        <div style="align-items: center;padding: 15px;font-size: 20px">
                            书籍种数
                        </div>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="typeCount" class="card" :value-style="valueCss">
                    <template #prefix>
                        <el-icon size="25px">
                            <Collection/>
                        </el-icon>
                    </template>
                    <template #title>
                        <div style="align-items: center;padding: 15px;font-size: 20px">
                            分类种数
                        </div>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="bookTotalCount" class="card" :value-style="valueCss">
                    <template #prefix>
                        <el-icon size="25px">
                            <Files/>
                        </el-icon>
                    </template>
                    <template #title>
                        <div style="align-items: center;padding: 15px;font-size: 20px">
                            馆藏总数
                        </div>
                    </template>
                </el-statistic>
            </el-col>
        </el-row>
    </div>


</template>

<script setup>
import {ref} from "vue";
import {get} from "@/net";
import {Collection, Files, Notebook, UserFilled} from "@element-plus/icons-vue";

const userCount = ref(0)
const bookUniqueCount = ref(0)
const bookTotalCount = ref(0)
const typeCount = ref(0)

const valueCss = {
    'font-size': '30px',
}

const freshData = () => {
    get('/api/user/user-count', (message) => {
        userCount.value = message
    })

    get('/api/book/book-unique-count', (message) => {
        bookUniqueCount.value = message
    })

    get('/api/book/book-total-count', (message) => {
        bookTotalCount.value = message
    })

    get('/api/book/type-count', (message) => {
        typeCount.value = message
    })
}
freshData()

</script>

<style scoped>
.card {
    background-color: #66b1ff;
    border-radius: 40px;
    height: 100px;
}

.welcome-slogan {
    position: absolute;
    top: 130px;
    left: 530px;
    color: white;
    text-shadow: 0 0 10px black;
}

.el-col {
    text-align: center;
}
</style>
