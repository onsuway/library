<template>
    <el-container class="background-container">
        <el-container class="header-selection">
            <div class="type-row">
                <div style="font-size: 18px; font-weight: bold;flex: 1">分类：</div>
                <div style="flex: 14">
                    <el-radio-group v-model="typeRadio" @change="handleTypeRadioChange()">
                        <el-radio-button label="all">全部</el-radio-button>
                        <el-radio-button
                                v-for="type in typeList"
                                :label="type.type_id"
                        >
                            {{ type.type_name }}
                        </el-radio-button>
                    </el-radio-group>
                </div>
            </div>
            <div class="statistics-time-row">
                <div style="font-size: 18px; font-weight: bold;flex: 1">统计范围：</div>
                <div style="flex: 14">
                    <el-radio-group v-model="timeRadio" @change="handleTimeRadioChange()">
                        <el-radio-button label="all">全部</el-radio-button>
                        <el-radio-button label="week">最近一周</el-radio-button>
                        <el-radio-button label="month">最近一月</el-radio-button>
                        <el-radio-button label="year">最近一年</el-radio-button>
                    </el-radio-group>
                </div>
            </div>
        </el-container>

        <el-container class="table-container">
            <el-table
                    :data="bookLeaderboardList"
                    size="large"
                    :cell-style="{'text-align':'center'}"
                    :header-cell-style="{'text-align':'center'}"
                    height="600"
                    stripe
            >
                <el-table-column label="序号" type="index" width="80"/>
                <el-table-column label="书名" prop="title" width="400">
                    <template #default="scope">
                        <el-link
                                type="primary"
                                @click="router.push(`/user/bookDetails/${scope.row.book_id}`)"
                        >
                            {{ scope.row.title }}
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column label="作者" prop="author"/>
                <el-table-column label="可借数量" prop="nums"/>
                <el-table-column label="借阅次数" prop="borrow_count"/>
                <el-table-column/>
            </el-table>
        </el-container>
    </el-container>
</template>

<script setup>
import {ref} from "vue";
import {get, post} from "@/net";
import router from "@/router";

const typeRadio = ref('all')

const timeRadio = ref('all')

const typeList = ref([])

const bookLeaderboardList = ref([])

const freshBookList = () => {
    post('/api/borrow/get-hot-borrow-leaderboard', {
        type_id: typeRadio.value,
        time_range: timeRadio.value
    }, (message) => {
        bookLeaderboardList.value = message
    })
}

const freshTypeList = () => {
    get('/api/book/get-all-type', (message) => {
        typeList.value = message
    })
}

const handleTypeRadioChange = () => {
    freshBookList()
}

const handleTimeRadioChange = () => {
    freshBookList()
}


freshTypeList()
freshBookList()
</script>

<style scoped>
.background-container {
    flex-direction: column;
    align-items: center;
    background-color: #f5f5f5;
    height: 93.5vh;
}

.header-selection {
    flex-direction: column;
    background-color: #ffffff;
    width: 1600px;
    padding: 20px;
    margin-top: 20px;
    box-shadow: 0 3px 4px rgba(0, 0, 0, .15);
    flex: 1;
}

.type-row {
    display: flex;
    align-items: center;
}

.statistics-time-row {
    display: flex;
    align-items: center;
    margin-top: 20px;
}

.table-container {
    background-color: #ffffff;
    width: 1600px;
    margin-top: 30px;
    padding: 30px;
    margin-bottom: 30px;
    flex: 8;
}
</style>
