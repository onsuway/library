<template>
    <div class="background">
        <div class="content-container margin-auto">
            <div class="book-info">
                <div class="book-detail">
                    <h2 style="margin-left: 20px">{{ currentBook.title }}</h2>
                    <div class="book-cover-image">
                        <el-image
                                style="width: 100%; height: 100%;"
                                :src="currentBook.cover_url"
                                fit="fill"
                        />
                    </div>
                    <div style="margin-left: 15px">
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【作者】</el-col>
                            <el-col :span="12">{{ currentBook.author }}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【类型】</el-col>
                            <el-col :span="12">{{ currentBook.type_name }}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【剩余可借】</el-col>
                            <el-col :span="12">{{ currentBook.nums }}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【简介】</el-col>
                            <el-col :span="12">{{ currentBook.desc }}</el-col>
                        </el-row>
                    </div>

                    <div v-if="currentBook.nums === 0" class="book-nums-0">
                        <el-link :icon="Promotion" @click="ElMessage.success('管理员已经在全速补货啦')">
                            没库存啦？提醒管理员
                        </el-link>
                    </div>
                    <div class="borrow-button">
                        <el-button
                                :disabled="currentBook.nums === 0"
                                style="width: 100px"
                                type="primary"
                                plain
                                @click="handleBorrow(currentBook.bid)">
                            借阅
                        </el-button>
                    </div>
                </div>
                <div class="same-type-book">
                    <div style="font-size: 18px;font-weight: bold">同类型书籍推荐</div>
                    <el-divider/>
                    <div style="display: flex">
                        <div class="type-book-item" v-for="book in sameTypeBookList">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;cursor: pointer"
                                          :src="book.cover_url"
                                          @click="clickRouterPush(book.bid)"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link @click="clickRouterPush(book.bid)" type="primary" style="font-size: 17px">
                                        {{ book.title }}
                                    </el-link>
                                    <div style="font-size: 14px;margin-top: 5px;color: #aba7a7">{{ book.author }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="relative-info">
                <div class="link">
                    <div style="font-size: 18px;font-weight: bold">相关资源链接</div>
                    <el-divider/>
                    <div style="display: flex;flex-wrap: wrap">
                        <el-image
                                style="height: 40px;width: 60px;cursor: pointer"
                                @click="searchTaoBaoLink(currentBook.title)"
                                src="https://i.328888.xyz/2023/05/18/VVTbcU.png"
                                fit="fill"
                        />
                        <el-image
                                style="height: 40px;width: 60px;cursor: pointer"
                                @click="searchJDLink(currentBook.title)"
                                src="https://i.328888.xyz/2023/05/18/VVTrF5.png"
                                fit="fill"
                        />
                        <el-image
                                style="height: 40px;width: 60px;cursor: pointer"
                                src="https://i.328888.xyz/2023/05/18/VVTStN.png"
                                @click="searchDangDangLink(currentBook.title)"
                                fit="cover"
                        />
                        <el-image
                                style="height: 40px;width: 60px;cursor: pointer"
                                src="https://i.328888.xyz/2023/05/18/VVTN8y.png"
                                @click="searchDouBanLink(currentBook.title)"
                                fit="fill"
                        />
                    </div>

                </div>
                <div class="same-author-book">
                    <div style="font-size: 18px;font-weight: bold">作者其他著作</div>
                    <el-divider/>
                    <div class="author-book-item" v-for="book in sameAuthorBookList">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;cursor: pointer"
                                      :src="book.cover_url"
                                      @click="clickRouterPush(book.bid)"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link
                                    type="primary"
                                    style="font-size: 20px"
                                    @click="clickRouterPush(book.bid)"
                            >
                                {{ book.title }}
                            </el-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {useRoute} from "vue-router";
import {ref} from "vue";
import {post} from "@/net";
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";
import {useUserStore} from "@/stores";
import {Promotion} from "@element-plus/icons-vue";

const route = useRoute()

const userStore = useUserStore()

//传递过来的书籍ID
const book_id = ref(route.params.book_id)

const currentBook = ref({
    bid: 0,
    title: '',
    author: '',
    desc: '',
    type_name: '',
    nums: 0,
    cover_url: '',
})

const sameTypeBookList = ref([])
const sameAuthorBookList = ref([])

const freshCurrentBookDetail = () => {
    post('/api/book/get-by-id', {
        book_id: book_id.value
    }, (message) => {
        currentBook.value = message
    })
}

const freshSameTypeBookList = () => {
    post('/api/book/get-same-type-book', {
        book_id: book_id.value
    }, (message) => {
        sameTypeBookList.value = message
    })
}

const freshSameAuthorBookList = () => {
    post('/api/book/get-same-author-book', {
        book_id: book_id.value
    }, (message) => {
        sameAuthorBookList.value = message
    })
}

const clickRouterPush = (bid) => {
    router.push({path: `/user/bookDetails/${bid}`});
    freshPage()
}

const freshPage = () => {
    freshCurrentBookDetail()
    freshSameTypeBookList()
    freshSameAuthorBookList()
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
                freshPage()
            }, (message) => {
                ElMessage.warning(message)
            })
        })
        .catch(() => {
            ElMessage.info('你取消了借阅')
        })
}

const searchTaoBaoLink = (title) => {
    window.open('https://s.taobao.com/search?q=' + title, '_blank')
}

const searchJDLink = (title) => {
    window.open('https://search.jd.com/Search?keyword=' + title, '_blank')
}

const searchDangDangLink = (title) => {
    window.open('https://search.dangdang.com/?act=input&key=' + title, '_blank')
}

const searchDouBanLink = (title) => {
    window.open('https://www.douban.com/search?q=' + title, '_blank')
}

freshPage()

</script>

<style scoped>
.background {
    background-color: #f5f5f5;
    padding: 20px;
    height: 100vh;
}

.content-container {
    display: flex;
    width: 1200px;
    height: 750px;
    background-color: #f5f5f5;
}

.margin-auto {
    margin: 0 auto;
}

.book-info {
    display: flex;
    flex-direction: column;
    background-color: #f5f5f5;
    margin-right: 15px;
    flex: 3;
}

.book-detail {
    position: relative;
    background-color: #ffffff;
    flex: 2.5;
    margin-bottom: 15px;
}

.book-cover-image {
    position: absolute;
    right: 10px;
    top: 15px;
    width: 230px;
    height: 300px;
}

.book-nums-0 {
    position: absolute;
    right: 40px;
    top: 370px;
}

.borrow-button {
    position: absolute;
    right: 60px;
    top: 340px;
}

.same-type-book {
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    flex: 2;
    padding: 10px;
}

.type-book-item {
    margin-left: 20px;
}

.relative-info {
    display: flex;
    flex-direction: column;
    background-color: #f5f5f5;
    flex: 1;
}

.link {
    display: flex;
    flex-direction: column;
    padding: 20px;
    background-color: #ffffff;
    flex: 1;
    margin-bottom: 15px;
}

.same-author-book {
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    flex: 3;
    padding: 15px;
}

.author-book-item {
    display: flex;
    padding: 5px;
}

.author-book-info {
    margin-top: 10px;
    margin-left: 20px;
}


.info-row {
    margin-bottom: 20px;
}

</style>
