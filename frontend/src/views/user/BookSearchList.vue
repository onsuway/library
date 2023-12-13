<template>
    <div>
        <div style="background-color: #f0f3f5; height: 120px;">
            <div class="header margin-auto">
                <div class="search">
                    <el-input
                            size="large"
                            v-model="searchInputText"
                            placeholder="按书名或作者搜索书籍">
                        <template #prepend>
                            <el-select v-model="searchInputSelected" size="large" placeholder="书名/作者"
                                       style="width: 115px">
                                <el-option label="书名" value="title"/>
                                <el-option label="作者" value="author"/>
                            </el-select>
                        </template>
                        <template #append>
                            <el-button :icon="Search" @click="handleSearch()"/>
                        </template>
                    </el-input>
                </div>
            </div>
        </div>

        <div class="content margin-auto">
            <div class="main margin-auto">
                <div class="main-aside">
                    <div style="font-size: 20px;color: grey;margin-top: 15px">筛选类别</div>
                    <el-checkbox-group
                            style="display: flex;flex-direction: column;margin-left: 40px"
                            v-for="type in typeList"
                            v-model="checkBoxList"
                            :key="type.type_id"
                            @change="handleCheckBoxChange()"
                    >
                        <el-checkbox :label="type.type_name" checked/>
                    </el-checkbox-group>
                </div>
                <div class="main-list">
                    <div class="main-list-header">
                        <span style="margin-left: 13px;font-size: 15px;color: grey;flex: 1">
                            总共{{ bookSearchList.length }}条
                        </span>
                    </div>

                    <div class="book-list">
                        <div
                                class="book-card"
                                v-for="(book,index) in bookSearchList"
                                :key="book.bid"
                        >
                            <div style="font-weight: bold;font-size: 20px">{{ index + 1 + '. ' + book.title }}</div>
                            <div style="display: flex; margin: 10px">
                                <div style="flex: 1; margin-top: 15px">
                                    <el-image
                                            style="width: 100%;height: 180px;"
                                            fit="fill"
                                            :src="book.cover_url"/>
                                </div>
                                <div style="flex: 6;">
                                    <ul>
                                        <li>
                                            <span style="font-style: italic;font-size: 15px">作者：</span>
                                            {{ book.author }}
                                        </li>
                                        <li>
                                            <span style="font-style: italic;font-size: 15px">类别：</span>
                                            {{ book.type_name }}
                                        </li>
                                        <li>
                                            <span style="font-style: italic;font-size: 15px">简介：</span>
                                            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ book.desc }}</div>
                                        </li>
                                    </ul>
                                </div>

                            </div>
                            <div class="book-nums">可借({{ book.nums }})</div>
                            <div class="book-operation">
                                <el-button
                                        type="primary"
                                        :disabled="book.nums === 0"
                                        @click="handleBorrow(book.bid)">
                                    借阅
                                </el-button>
                            </div>
                            <div v-if="book.nums === 0" class="book-nums-0">
                                <el-link :icon="Promotion" @click="ElMessage.success('管理员已经在全速补货啦')">
                                    没库存啦？提醒管理员
                                </el-link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {Promotion, Search} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {get, post} from "@/net";
import {useCheckBoxStore, useUserStore} from "@/stores";
import router from "@/router";

onMounted(() => {
    checkBoxStore.setCheckBoxList(typeNameList.value)
    freshBookSearchList()


    console.log(userStore.userInfo.credit)
})

const route = useRoute()
const userStore = useUserStore()
const checkBoxStore = useCheckBoxStore()


//从主页搜索框传过来的搜索类型和搜索文本
const searchInputSelected = ref(route.query.type)

const searchInputText = ref(route.query.value)

const bookSearchList = ref([])

const typeList = ref([])

const checkBoxList = ref(checkBoxStore.getCheckBox)

const typeNameList = ref([])


get('/api/book/get-all-type', (message) => {
    typeList.value = message
    typeNameList.value = typeList.value.map(type => type.type_name)
})


const freshBookSearchList = () => {
    post('/api/book/search', {
        search_type: searchInputSelected.value,
        search_value: searchInputText.value
    }, (message) => {

        if (checkBoxStore.getCheckBox.length === 0) {
            checkBoxStore.setCheckBoxList(typeNameList.value)
            freshBookSearchList()
        }

        bookSearchList.value = message.filter(book =>
            checkBoxList.value.indexOf(book.type_name) !== -1
        )
    })
}

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        freshBookSearchList()
        router.push({
            path: '/user/bookSearchList',
            query: {
                type: searchInputSelected.value,
                value: searchInputText.value,
            },
        })
    }
}

const handleBorrow = (bid) => {
    ElMessageBox.confirm(
        '确定借阅吗？',
        '借阅确认',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'success',
        }
    )
        .then(() => {
            post('/api/borrow/user-borrow', {
                bid: bid,
                account_id: userStore.userInfo.id
            }, () => {
                ElMessage.success('成功借阅')
                freshBookSearchList()
            }, (message) => {
                ElMessage.warning(message)
            })
        })
        .catch(() => {
            ElMessage.info('你取消了借阅')
        })
}

const handleCheckBoxChange = () => {
    checkBoxStore.setCheckBoxList(checkBoxList.value)
    freshBookSearchList()
}

</script>

<style scoped>
.margin-auto {
    margin: 0 auto;
}

.header {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100px;
    background-color: #fafafa;
    box-shadow: 0 2px 15px 0 rgba(58, 88, 104, .2)
}

.search {
    width: 600px;
}

.content {
    padding: 20px;
}

.main {
    display: flex;
    width: 1200px;
}

.main-aside {
    text-align: center;
    flex: 1;

}

.main-list {
    flex: 4;
}

.main-list-header {
    height: 40px;
    line-height: 40px;
    display: flex;
    border-bottom: solid 1px #e0e0e0;
}

.book-list {
    display: flex;
    flex-direction: column;
}

.book-list .book-card {
    position: relative;
    padding: 20px;
    height: 240px;
    border-bottom: solid 1px #e0e0e0;
}

.book-card:hover {
    box-shadow: 0 2px 15px 0 rgba(58, 88, 104, .2)
}

li {
    list-style: none;
}

.book-nums {
    background-color: rgba(135, 206, 250, .3);
    position: absolute;
    width: 70px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 15px;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
    right: 15px;
    top: 0;
}

.book-operation {
    position: absolute;
    right: 30px;
    top: 50px;
}

.book-nums-0 {
    position: absolute;
    top: 0;
    right: 100px;
}
</style>
