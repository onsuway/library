<template>
    <div>
        <div style="margin: 20px 600px">
            <el-input size="large" v-model="searchInputText" placeholder="按用户名搜索用户">
                <template #append>
                    <el-button :icon="Search" @click="handleSearch()"/>
                </template>
            </el-input>
        </div>
        <el-table
                :data="userList"
                size="large"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
        >
            <el-table-column prop="username" label="用户名"/>
            <el-table-column prop="role" label="用户角色"/>
            <el-table-column prop="email" label="用户邮箱"/>
            <el-table-column prop="borrowing_nums" label="正借阅数量"/>
            <el-table-column prop="credit" label="信用积分"/>
            <el-table-column label="操作" align="right" width="200">
                <template #default="{row}">
                    <el-dropdown>
                        <el-button type="primary">
                            <el-icon>
                                <Tools/>
                            </el-icon>
                            <el-icon class="el-icon--right">
                                <arrow-down/>
                            </el-icon>
                        </el-button>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="handleDetailBorrow(row)">查看正在借阅</el-dropdown-item>
                                <!-- TODO 更改用户角色 student/teacher -->
                                <el-dropdown-item>更改用户角色</el-dropdown-item>
                                <el-dropdown-item @click="handleResetCredit(row)">重置信用积分</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog v-model="dialogTableVisible" :title="'用户' + currentUser + '借阅中书籍'">
            <el-table
                    :data="userBorrowingList"
                    :cell-style="{'text-align':'center'}"
                    :header-cell-style="{'text-align':'center'}"
            >
                <el-table-column prop="borrow_id" label="借阅订单号" align="center" width="120"/>
                <el-table-column prop="title" label="书籍"/>
                <el-table-column prop="borrow_time" label="借阅时间" sortable/>
                <el-table-column prop="due_time" label="最晚归还时间" sortable/>
                <el-table-column label="操作" align="right" header-align="center">
                    <template #default="{row}">
                        <el-popconfirm
                                title="确定延长三天借阅时间吗？"
                                width="220"
                                confirm-button-text="确定"
                                cancel-button-text="取消"
                                @confirm="handleExtendBorrow(row)"
                        >
                            <template #reference>
                                <el-button type="primary">延长借阅</el-button>
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
                                <el-button type="success">确认归还</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script setup>
import {get, post} from "@/net";
import {ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {ArrowDown, Search, Tools} from "@element-plus/icons-vue";

const userList = ref([])
const userBorrowingList = ref([])
const dialogTableVisible = ref(false)
const currentUser = ref('')

//搜索框的输入文本
const searchInputText = ref('')

const freshUserList = () => {
    get('/api/user/get-all', (message) => {
        userList.value = message
    }, (message) => {
        ElMessage.warning(message)
    })
}

const getBorrowByUser = (account_id) => {
    get('/api/borrow/get-user-borrowing/' + account_id, (message) => {
        userBorrowingList.value = message
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleDetailBorrow = (row) => {
    getBorrowByUser(row.id)
    dialogTableVisible.value = true
    currentUser.value = row.username
}

const handleExtendBorrow = (row) => {
    post('/api/borrow/extend/' + row.borrow_id, row.borrow_id, () => {
        ElMessage.success("成功延长" + currentUser.value + "借阅《" + row.title + "》三天")
        dialogTableVisible.value = false
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleReturnBorrow = (row) => {
    post('/api/borrow/admin-batch-return/' + row.borrow_id, row.borrow_id, () => {
        ElMessage.success("成功归还" + currentUser.value + "借阅《" + row.title + "》")
        dialogTableVisible.value = false
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleResetCredit = (row) => {
    ElMessageBox.confirm(
        '确定重置该用户信用积分吗？这不可撤销',
        '重置积分确认',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            post('/api/user/reset-credit', {
                id: row.id
            }, (message) => {
                ElMessage.success(message)
                freshUserList()
            }, (message) => {
                ElMessage.warning(message)
            })
        })
        .catch(() => {
            ElMessage.info("取消了重置信用积分")
        })
}

const handleSearch = () => {
    if (searchInputText.value === ''){
        ElMessage.warning('搜索不允许为空')
    }else {
        post('/api/user/search', {
            text: searchInputText.value
        }, (message) => {
            userList.value = message
        }, (message) => {
            ElMessage.warning(message)
        })
    }
}

freshUserList()
</script>

<style scoped>

</style>
