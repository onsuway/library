<template>
    <div style="padding: 30px">
        <el-tabs v-model="activeName" class="demo-tabs">
            <el-tab-pane label="当前借阅" name="borrowing">
                <el-table
                        size="large"
                        :data="borrowingBookList"
                        :cell-style="{'text-align':'center'}"
                        :header-cell-style="{'text-align':'center'}"
                        empty-text="还没有借阅过书籍呢，快去看一看叭"
                >
                    <el-table-column prop="title" label="书名"/>
                    <el-table-column prop="author" label="作者"/>
                    <el-table-column prop="borrow_time" label="借阅时间"/>
                    <el-table-column prop="due_time" label="最晚归还时间"/>
                    <el-table-column label="操作">
                        <template #default="{row}">
                            <el-popconfirm
                                title="确定延长三天借阅时间吗？"
                                width="220"
                                confirm-button-text="确定"
                                cancel-button-text="取消"
                                @confirm="handleExtendBorrow(row)"
                            >
                                <template #reference>
                                    <el-button type="primary">续借</el-button>
                                </template>
                            </el-popconfirm>
                            <el-popconfirm
                                title="确定归还该书籍吗？"
                                width="220"
                                confirm-button-text="确定"
                                cancel-button-text="取消"
                                @confirm="handleReturnBorrow(row)"
                            >
                                <template #reference>
                                    <el-button type="success">归还</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="历史借阅" name="historyBorrow">
                <div style="display: flex">
                    <div style="flex: 1;font-size: 15px;color: grey;margin-left: 10px">
                        注：正在借阅中的书籍不在此表中
                    </div>
                    <el-button style="margin-right: 10px" type="primary" @click="resetBorrowedBook()" plain>重置</el-button>
                    <el-input style="flex: 0.5" v-model="searchInputText" placeholder="按书名或作者搜索">
                        <template #prepend>
                            <el-select
                                    v-model="searchInputSelected"
                                    placeholder="书名/作者"
                                    style="width: 75px">
                                <el-option label="书名" value="title"/>
                                <el-option label="作者" value="author"/>
                            </el-select>
                        </template>
                        <template #append>
                            <el-button :icon="Search" @click="handleSearch()"/>
                        </template>
                    </el-input>
                </div>
                <el-table
                        size="large"
                        :data="borrowedBookList"
                        :cell-style="{'text-align':'center'}"
                        :header-cell-style="{'text-align':'center'}"
                        height="650"
                        empty-text="还没有借阅过书籍呢，快去看一看叭"
                >
                    <el-table-column prop="title" label="书名"/>
                    <el-table-column prop="author" label="作者"/>
                    <el-table-column prop="borrow_time" label="借阅时间"/>
                    <el-table-column prop="actual_time" label="归还时间"/>
                </el-table>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script setup>
import {ref} from "vue";
import {Search} from "@element-plus/icons-vue"
import {ElMessage, ElMessageBox} from "element-plus";
import {get, post} from "@/net";
import {useUserStore} from "@/stores";

const userStore = useUserStore()

const activeName = ref('borrowing')

//搜索框选择的搜索类型
const searchInputSelected = ref('title')
//搜索框的输入文本
const searchInputText = ref('')

const borrowingBookList = ref([])

const borrowedBookList = ref([])

const freshBorrowingBook = () => {
    get('/api/borrow/get-user-borrowing/' + userStore.userInfo.id,
        (message) => {
            borrowingBookList.value = message
        })
}

const freshBorrowedBook = () => {
    get('/api/borrow/get-user-borrowed/' + userStore.userInfo.id,
        (message) => {
            borrowedBookList.value = message
        })
}

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        //post
        post('/api/borrow/search-borrowed-with-account', {
            search_type: searchInputSelected.value,
            search_value: searchInputText.value,
            account_id: userStore.userInfo.id
        }, (message) => {
            borrowedBookList.value = message
        })
    }
}

const handleExtendBorrow = (row) => {
    post('/api/borrow/user-single-extend/' + row.borrow_id, {
        borrow_id: row.borrow_id
    }, () => {
        ElMessage.success("成功续借《" + row.title + "》三天")
        freshBorrowingBook()
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleReturnBorrow = (row) => {
    post('/api/borrow/user-single-return', {
        borrow_id: row.borrow_id,
        account_id: userStore.userInfo.id
    }, () => {
        ElMessage.success("成功归还《" + row.title + "》")
        freshBorrowingBook()
    }, (message) => {
        ElMessage.warning(message)
        freshBorrowingBook()
    })
}

const resetBorrowedBook = () => {
    searchInputSelected.value = 'title'
    searchInputText.value = ''
    freshBorrowedBook()
}




freshBorrowingBook()
freshBorrowedBook()
</script>

<style scoped>

</style>
