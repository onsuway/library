<template>
    <div class="background">
        <div class="content-container margin-auto">
            <div class="book-info">
                <div class="book-detail">
                    <h2 style="margin-left: 20px">{{book.title}}</h2>
                    <div class="book-cover-image">
                        <el-image
                                style="width: 100%; height: 100%;"
                                :src="book.cover_url"
                                fit="fill"
                        />
                    </div>
                    <div style="margin-left: 15px">
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【作者】</el-col>
                            <el-col :span="12">{{book.author}}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <!-- TODO 待在book数据库里添加出版社字段 -->
                            <el-col :span="4" style="color: #a6a9ad">【出版社】</el-col>
                            <el-col :span="12">人民教育出版社</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【类型】</el-col>
                            <el-col :span="12">{{book.type_name}}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【剩余可借】</el-col>
                            <el-col :span="12">{{book.nums}}</el-col>
                        </el-row>
                        <el-row class="info-row">
                            <el-col :span="4" style="color: #a6a9ad">【简介】</el-col>
                            <el-col :span="12">{{book.desc}}</el-col>
                        </el-row>
                    </div>
                </div>
                <div class="same-type-book">
                    <div style="font-size: 18px;font-weight: bold">同类型书籍推荐</div>
                    <el-divider />
                    <div style="display: flex">
                        <!-- TODO 待实现从数据库中查询相同类型的书籍 -->
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                        <div class="type-book-item">
                            <div style="width: 120px;height: 160px">
                                <el-image style="width: 100%;height: 100%;"
                                          src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                          fit="fill"/>
                                <div style="margin-left: 5px">
                                    <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                                    <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">莫言</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="relative-info">
                <div class="link">
                    <div style="font-size: 18px;font-weight: bold">相关资源链接</div>
                    <el-divider />
                    <div style="display: flex;flex-wrap: wrap">
                        <!-- TODO 待实现点击图片直接跳转对应链接搜索 -->
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTbcU.png"
                            fit="fill"
                        />
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTrF5.png"
                            fit="fill"
                        />
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTStN.png"
                            fit="cover"
                        />
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTIf3.png"
                            fit="fill"
                        />
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTN8y.png"
                            fit="fill"
                        />
                        <el-image
                            style="height: 40px;width: 60px"
                            src="https://i.328888.xyz/2023/05/18/VVTjov.png"
                            fit="fill"
                        />
                    </div>

                </div>
                <div class="same-author-book">
                    <div style="font-size: 18px;font-weight: bold">作者其他著作</div>
                    <el-divider />
                    <!-- TODO 待实现从数据库中查询相同作者的书籍 -->
                    <div class="author-book-item">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;"
                                      src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                            <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">人民教育出版社</div>
                        </div>
                    </div>
                    <div class="author-book-item">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;"
                                      src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                            <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">人民教育出版社</div>
                        </div>
                    </div>
                    <div class="author-book-item">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;"
                                      src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                            <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">人民教育出版社</div>
                        </div>
                    </div>
                    <div class="author-book-item">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;"
                                      src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                            <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">人民教育出版社</div>
                        </div>
                    </div>
                    <div class="author-book-item">
                        <div style="width: 90px;height: 127px">
                            <el-image style="width: 100%;height: 100%;"
                                      src="https://i.328888.xyz/2023/05/16/Vi2Z8q.jpeg"
                                      fit="fill"/>
                        </div>
                        <div class="author-book-info">
                            <el-link type="primary" style="font-size: 20px">红高粱</el-link>
                            <div style="font-size: 15px;margin-top: 5px;color: #aba7a7">人民教育出版社</div>
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

const route = useRoute()

//传递过来的书籍ID
const book_id = ref(route.params.book_id)

const book = ref({
    bid: 0,
    title: '',
    author: '',
    desc: '',
    type_name: '',
    nums: 0,
    cover_url: '',
})

const freshBookDetail = () => {
    post('/api/book/get-by-id', {
        book_id: book_id.value
    }, (message) => {
        book.value = message
    })
}


freshBookDetail()

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
    height: 700px;
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
