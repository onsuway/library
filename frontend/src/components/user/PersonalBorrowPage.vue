<template>
    <div style="padding: 30px">
        <el-tabs v-model="activeName" class="demo-tabs">
            <el-tab-pane label="当前借阅" name="borrowing">
                <div style="display: flex">
                    <div style="flex: 1"></div>
                    <el-button type="primary" plain @click="handleBatchExtend(selectedRowIds)">批量续借</el-button>
                    <el-button type="success" plain @click="handleBatchReturn(selectedRowIds)">批量归还</el-button>
                </div>
                <el-table
                        size="large"
                        @selection-change="handleSelectionChange"
                        :data="borrowingBookList"
                        :cell-style="{'text-align':'center'}"
                        :header-cell-style="{'text-align':'center'}"
                >
                    <el-table-column type="selection"/>
                    <el-table-column prop="title" label="书名"/>
                    <el-table-column prop="author" label="作者"/>
                    <el-table-column prop="borrow_time" label="借阅时间"/>
                    <el-table-column prop="due_time" label="应还时间"/>
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
                    <el-button type="danger" plain style="margin-right: 10px">批量删除记录</el-button>
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
                <!-- TODO 待完成查询数据绑定历史借阅表格 -->
                <el-table
                        size="large"
                        :cell-style="{'text-align':'center'}"
                        :header-cell-style="{'text-align':'center'}"
                >
                    <el-table-column type="selection"/>
                    <el-table-column prop="bid" label="封面图"/>
                    <el-table-column prop="bid" label="书名"/>
                    <el-table-column prop="bid" label="作者"/>
                    <el-table-column prop="bid" label="借阅时间"/>
                    <el-table-column prop="bid" label="归还时间"/>
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

// 多选框选中的书籍
const selectedRows = ref([]);
// 多选框选中的书籍ids
const selectedRowIds = ref([]);

//多选框选择每次改变后执行
const handleSelectionChange = (val) => {
    // 设置选中的数据
    selectedRows.value = val;
    // 设置选中的数据ids
    selectedRowIds.value = val.map((item) => item.borrow_id);
};

const borrowingBookList = ref([])

const borrowedBookList = ref([])

const freshBorrowingBook = () => {
    get('/api/borrow/get-user-borrowing/' + userStore.userInfo.id,
        (message) => {
            borrowingBookList.value = message
        })
}

const freshBorrowedBook = () => {

}

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        //post
        ElMessage.success('搜索成功')
    }
}

const handleExtendBorrow = (row) => {
    post('/api/borrow/extend/' + row.borrow_id, {
        borrow_id: row.borrow_id
    }, () => {
        ElMessage.success("成功续借《" + row.title + "》三天")
        freshBorrowingBook()
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleReturnBorrow = (row) => {
    post('/api/borrow/return/' + row.borrow_id, {
        borrow_id: row.borrow_id
    }, () => {
        ElMessage.success("成功归还《" + row.title + "》")
        freshBorrowingBook()
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleBatchExtend = (selectedRowIds) => {
    if (selectedRowIds.length === 0) {
        ElMessage.warning('选中不能为空！')
    } else {
        ElMessageBox.confirm(
            '确定将这些借阅延长三天吗，这不可撤销！',
            '延长确认',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            post('/api/borrow/extend/' + selectedRowIds, selectedRowIds, (message) => {
                ElMessage.success(message)
                freshBorrowingBook()
            }, (message) => {
                ElMessage.warning(message)
            })
        }).catch(() => {
            ElMessage.info('你取消了批量延长')
        })
    }

}

const handleBatchReturn = (selectedRowIds) => {
    if (selectedRowIds.length === 0) {
        ElMessage.warning('选中不能为空！')
    } else {
        ElMessageBox.confirm(
            '确定将这些借阅归还吗，这不可撤销！',
            '归还确认',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            post('/api/borrow/return/' + selectedRowIds, selectedRowIds, (message) => {
                ElMessage.success(message)
                freshBorrowingBook()
            }, (message) => {
                ElMessage.warning(message)
            })
        }).catch(() => {
            ElMessage.info('你取消了批量归还')
        })
    }
}

freshBorrowingBook()
</script>

<style scoped>

</style>
