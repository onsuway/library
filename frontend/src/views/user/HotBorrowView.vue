<template>
    <el-container class="background-container">
        <el-container class="header-selection">
            <div class="type-row">
                <div style="font-size: 18px; font-weight: bold;flex: 1">分类：</div>
                <div style="flex: 14">
                    <!-- TODO 待查询绑定类别
                     有个问题是：如果类型过多该如何显示 -->
                    <el-radio-group v-model="typeRadio">
                        <el-radio-button label="all">全部</el-radio-button>
                        <el-radio-button label="chinese">语文</el-radio-button>
                        <el-radio-button label="math">数学</el-radio-button>
                        <el-radio-button label="english">英语</el-radio-button>
                    </el-radio-group>
                </div>
            </div>
            <div class="statistics-time-row">
                <div style="font-size: 18px; font-weight: bold;flex: 1">统计范围：</div>
                <div style="flex: 14">
                    <!-- TODO 待绑定 -->
                    <el-radio-group v-model="timeRadio">
                        <el-radio-button label="all">全部</el-radio-button>
                        <el-radio-button label="week">最近一周</el-radio-button>
                        <el-radio-button label="month">最近一月</el-radio-button>
                        <el-radio-button label="year">最近一年</el-radio-button>
                    </el-radio-group>
                </div>
            </div>
        </el-container>

        <el-container class="table-container">
            <!-- TODO 待绑定数据库信息
                计划将之前主页用的HotBorrowBook类增加一些新的信息并绑定在这里
             -->
            <el-table
                    :data="bookLeaderboardList"
                    size="large"
                    :cell-style="{'text-align':'center'}"
                    :header-cell-style="{'text-align':'center'}"
                    height="600"
                    stripe
            >
                <el-table-column label="序号" type="index" width="80"/>
                <el-table-column label="书名" prop="title" width="400"/>
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

const typeRadio = ref('all')

const timeRadio = ref('all')

const bookLeaderboardList = ref([
    {
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    }, {
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    }, {
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    }, {
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    },{
        title: '测试1',
        author: '刘慈欣',
        nums: 66,
        borrow_count: 22,
    }
])
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
