<template>
    <div style="display: flex;">
        <div>
            <el-radio-group v-model="radio" @change="handleRadioChange">
                <el-radio-button label="unreturned">所有未归还</el-radio-button>
                <el-radio-button label="willOverdue">即将逾期</el-radio-button>
                <el-radio-button label="overdue">已逾期</el-radio-button>
            </el-radio-group>
        </div>
        <div style="margin: auto 20px">
            <el-button type="primary" @click="handleBatchExtend(selectedRowIds)">批量延长</el-button>
            <el-button type="success" @click="handleBatchReturn(selectedRowIds)">批量归还</el-button>
        </div>
        <div style="margin: auto 50px">
            <el-tag type="warning">即将逾期</el-tag>
            <el-tag type="danger">已经逾期</el-tag>
        </div>
        <div style="flex: 1;margin: 0 300px">
            <el-input size="large" v-model="searchInputText" placeholder="按用户名或书名搜索借阅订单">
                <template #prepend>
                    <el-select
                            v-model="searchInputSelected"
                            size="large"
                            placeholder="用户名/书名"
                            style="width: 125px">
                        <el-option label="用户名" value="username"/>
                        <el-option label="书名" value="title"/>
                    </el-select>
                </template>
                <template #append>
                    <el-button :icon="Search" @click="handleSearch()"/>
                </template>
            </el-input>
        </div>
    </div>

    <div style="margin-top: 20px">
        <el-table
                @selection-change="handleSelectionChange"
                :data="borrowList"
                style="width: 100%"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :row-class-name="tableRowClassName"
                :default-sort="{ prop: 'due_time', order: 'descending' }"
                size="large"
        >
            <el-table-column type="selection" width="55"/>
            <el-table-column prop="borrow_id" label="借阅订单号" align="center" width="120"/>
            <el-table-column prop="title" label="书籍"/>
            <el-table-column prop="username" label="用户"/>
            <el-table-column prop="borrow_time" label="借阅时间" sortable/>
            <el-table-column prop="due_time" label="最晚归还时间" sortable/>
            <el-table-column label="操作" align="right" header-align="center">
                <template #default="{row}">
                    <el-popconfirm
                            title="确定延长七天借阅时间吗？"
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
    </div>
</template>

<script setup>

import {ref} from "vue";
import {deleted, get, post} from "@/net";
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from "@element-plus/icons-vue";

// 表格展示的列表
const borrowList = ref([])

const radio = ref('unreturned')

//当前时间
const now = new Date()

//搜索框选择的搜索类型
const searchInputSelected = ref('')

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


//表格的响应式class类名
const tableRowClassName = ({row}) => {

    const due_time = new Date(row.due_time)

    if (row.del_flag === 0) {
        if (due_time < now) {
            return 'danger-row'
        } else if (due_time - now <= 24 * 60 * 60 * 1000) {
            return 'warning-row'
        } else return ''
    }

}

const freshUnreturnedBorrowList = () => {
    get('/api/borrow/get-unreturned', (message) => {
        borrowList.value = message
    }, (message) => {
        ElMessage.warning(message)
    })
}

const freshWillOverdueBorrowLit = () => {
    get('/api/borrow/get-willOverdue', (message) => {
        borrowList.value = message
    }, (message) => {
        ElMessage.warning(message)
    })
}

const freshOverdueBorrowList = () => {
    get('/api/borrow/get-overdue', (message) => {
        borrowList.value = message
    }, (message) => {
        ElMessage.warning(message)
    })
}

freshUnreturnedBorrowList()


const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        //post
        post('/api/borrow/search', {
            search_type: searchInputSelected.value,
            search_value: searchInputText.value
        }, (message) => {
            borrowList.value = message
        })
    }
}

const handleRadioChange = (value) => {
    if (value === 'unreturned') freshUnreturnedBorrowList()
    if (value === 'willOverdue') freshWillOverdueBorrowLit()
    if (value === 'overdue') freshOverdueBorrowList()
}


const handleExtendBorrow = (row) => {
    post('/api/borrow/extend/' + row.borrow_id, row.borrow_id, () => {
        ElMessage.success("成功延长" + row.username + "借阅《" + row.title + "》三天")
        handleRadioChange(radio.value)
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleReturnBorrow = (row) => {
    post('/api/borrow/return/' + row.borrow_id, row.borrow_id, () => {
        ElMessage.success("成功归还" + row.username + "借阅《" + row.title + "》")
        handleRadioChange(radio.value)
    }, (message) => {
        ElMessage.warning(message)
    })
}

const handleBatchExtend = (selectedRowIds) => {
    if (selectedRowIds.length === 0) {
        ElMessage.warning('选中不能为空！')
    } else {
        ElMessageBox.confirm(
            '确定将这些借阅延长七天吗，这不可撤销！',
            '延长确认',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            post('/api/borrow/extend/' + selectedRowIds, selectedRowIds, (message) => {
                ElMessage.success(message)
                handleRadioChange(radio.value)
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
                handleRadioChange(radio.value)
            }, (message) => {
                ElMessage.warning(message)
            })
        }).catch(() => {
            ElMessage.info('你取消了批量归还')
        })
    }
}

</script>

<style>
.el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
}

.el-table .danger-row {
    --el-table-tr-bg-color: var(--el-color-danger-light-9);
}
</style>
