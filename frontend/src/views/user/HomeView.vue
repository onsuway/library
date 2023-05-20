<template>
    <div class="main">
        <div style="position: relative">
            <div style="height: 300px;width: 100%;">
                <el-image
                        style="width: 100%; height: 100%;"
                        src="https://i.328888.xyz/2023/05/14/VZtUya.jpeg"
                        fit="cover"
                />
            </div>
            <div class="search-main">
                <div class="search-input">
                    <el-input size="large" v-model="searchInputText" placeholder="按书名或作者搜索书籍">
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

        <div class="hot-book-container margin-auto">
            <div class="book-container-title">
                <span class="title-left">热读推荐</span>
                <div class="title-right" @click="router.push('/user/hotBorrow')">查看全部</div>
            </div>
            <div class="hot-book" >
                <div class="hot-book-item" v-for="book in hotBookList">
                    <div
                        style="width: 180px;height: 240px; cursor: pointer"
                        @click="router.push(`/user/bookDetails/${book.book_id}`)"
                    >
                        <el-image  style="width: 100%;height: 100%;" :src="book.cover_url"/>
                    </div>
                    <div class="hot-book-info">
                        <el-link type="primary" class="hot-book-title" @click="router.push(`/user/bookDetails/${book.book_id}`)">{{book.title}}</el-link>
                        <div class="hot-book-popularity">
                            总借阅次数：<span>{{book.borrow_count}}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="new-book-container margin-auto">
            <div class="book-container-title">
                <span class="title-left">新书速递</span>
            </div>

            <div class="new-book">
                <div class="new-book-item" v-for="book in newBookList" @click="router.push(`/user/bookDetails/${book.book_id}`)">
                    <div style="width: 90px;height: 127px">
                        <el-image style="width: 100%;height: 100%;" :src="book.cover_url"/>
                    </div>
                    <div class="new-book-info">
                        <div class="new-book-title">
                            <div>{{book.title}}</div>
                            <div class="new-book-desc">{{book.author}}</div>
                        </div>
                        <div class="new-book-desc">
                            <div>{{book.create_day}}上架</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="recommend-buy-container margin-auto">
            <div class="book-container-title">
                <span class="title-left">资源荐购</span>
            </div>
            <div class="recommend-buy">
                <div class="recommend-button" @click="handleRecommend">
                    资源荐购
                    <el-icon size="25" style="margin-left: 10px">
                        <Right/>
                    </el-icon>
                </div>
                <div class="recommend-slogan">
                    你推荐，我买书
                </div>
            </div>

        </div>

        <div class="footer">

        </div>
    </div>

</template>

<script setup>
import {Right, Search} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {onMounted, ref} from "vue";
import router from "@/router";
import {get} from "@/net";

//搜索框选择的搜索类型
const searchInputSelected = ref('title')

//搜索框的输入文本
const searchInputText = ref('')

//热门书籍
const hotBookList = ref([])

//新书推荐
const newBookList = ref([])

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        router.push({
            path: '/user/bookSearchList',
            query: {
                type: searchInputSelected.value,
                value: searchInputText.value,
            },
        })
    }
}

const freshHotBookList = () => {
    get('/api/borrow/get-hot-borrow-book', (message) => {
        hotBookList.value = message
    })
}

const freshNewBookList = () => {
    get('/api/book/get-new-create', (message) => {
        newBookList.value = message
    })
}

const handleRecommend = () => {
    ElMessage.info('推荐')
}

onMounted(() => {
    freshHotBookList()
    freshNewBookList()
})
</script>

<style scoped>

.margin-auto {
    margin: 0 auto;
}

.main {
    background-color: #f5f5f5;
}

.search-main {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(128, 128, 128, 0.4);
    width: 700px;
    height: 100px;
    position: absolute;
    top: 100px;
    left: 33%;
}

.search-input {
    width: 600px;
    margin: auto;
}

.book-container-title {
    display: flex;
    height: 50px;
}

.title-left {
    flex: 1;
    font-size: 24px;
    font-weight: 400;
    text-align: left;
    color: #000;
}

.title-right {
    color: #999;
    cursor: pointer;
}

.hot-book-container {
    width: 1240px;
    margin-top: 35px;
}

.hot-book {
    display: flex;
    background-color: #fff;
    padding: 24px;
    height: 320px;
    box-shadow: 0 3px 4px rgba(0, 0, 0, .2);
    border-radius: 4px;
}

.hot-book-item {
    margin-top: 20px;
    margin-right: 70px;
}

.hot-book-title {
    margin-top: 5px;
    font-size: 18px;
}

.hot-book-popularity {
    margin-top: 5px;
    font-size: 13px;
    color: #999;
}

.new-book-container {
    width: 1240px;
    margin-top: 35px;
}

.new-book {
    display: flex;
    flex-wrap: wrap;
    background-color: #fff;
    padding: 24px;
    height: 320px;
    box-shadow: 0 3px 4px rgba(0, 0, 0, .2);
    border-radius: 4px;
}

.new-book-item {
    padding: 14px;
    display: flex;
}

.new-book-item:hover {
    background-color: #f5f5f5;
    cursor: pointer;
}

.new-book-info {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-top: -4px;
    margin-bottom: 5px;
    width: 170px;
}

.new-book-title {
    flex: 1;
    font-size: 18px;
}

.new-book-desc {
    font-size: 14px;
    color: #999;
}

.recommend-buy-container {
    width: 1240px;
    margin-top: 35px;
}

.recommend-buy {
    background: #fff url("https://i.328888.xyz/2023/05/16/ViOfLk.jpeg") no-repeat bottom;
    height: 240px;
    box-shadow: 0 3px 4px rgba(0, 0, 0, .2);
    border-radius: 4px;
    position: relative;
}

.recommend-button {
    display: flex;
    position: absolute;
    top: 80px;
    left: 100px;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    width: 250px;
    height: 75px;
    background-color: #05a081;
    color: white;
    font-size: 22px;
}
.recommend-slogan {
    position: absolute;
    top: 100px;
    left: 400px;
    color: white;
    font-size: 30px;
}


.footer {
    height: 50px;
}

</style>
