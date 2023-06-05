<template>
  <!-- 表格上方一排 按钮 + 搜索框 -->
    <div style="display: flex;margin-bottom: 20px">
        <div style="margin-right: 5px;margin-top: 5px">
            <el-button type="danger" @click="deleteSelectedBooks(selectedRowIds)">批量删除</el-button>
            <el-button type="primary" @click="handleAddBook()">添加书籍</el-button>
        </div>
        <div style="margin: 10px 10px">
            <el-tag type="warning">库存不足</el-tag>
            <el-tag type="danger">库存为0</el-tag>
        </div>
        <div style="flex: 1; margin: 0 auto">
            <el-input size="large" v-model="searchInputText" placeholder="按书名或作者搜索书籍">
                <template #prepend>
                    <el-select v-model="searchInputSelected" size="large" placeholder="书名/作者" style="width: 115px">
                        <el-option label="书名" value="title"/>
                        <el-option label="作者" value="author"/>
                    </el-select>
                </template>
                <template #append>
                    <el-button :icon="Search" @click="handleSearch()"/>
                </template>
            </el-input>
        </div>

        <div style="margin-left: 5px;margin-top: 5px">
            <!-- 表格上方点此查看所有书籍类别按钮 -->
            <el-popover
                    placement="top-start"
                    :width="280"
                    trigger="click"
            >
                <template #reference>
                    <el-button class="m-2" type="success">点此查看所有书籍类别</el-button>
                </template>
                <!-- 书籍类别信息表格 -->
                <el-table
                        :data="typeList"
                        height="200"
                        width="100%"
                        :cell-style="{'text-align':'center'}"
                        :header-cell-style="{'text-align':'center'}"
                >
                    <el-table-column prop="type_name" label="类型"/>
                    <el-table-column fixed="right" label="操作">
                        <template #default="{row}">
                            <el-popconfirm
                                    title="确定删除吗？该类别的书籍将会被置空"
                                    width="220"
                                    confirm-button-text="确定"
                                    cancel-button-text="取消"
                                    @confirm="handleDeleteType(row)"
                            >
                                <template #reference>
                                    <el-button type="danger">删除</el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
            </el-popover>
            <el-button type="primary" @click="handleAddType()">添加书籍类别</el-button>
        </div>

        <!--  点击表格上方的添加书籍按钮按钮打开的对话框表单  -->
        <el-dialog
                v-model="addBookDialogVisible"
                title="新增书籍信息"
                width="27%"
                align-center
                center
        >
            <el-form
                    label-width="100px"
                    :model="bookForm"
                    style="max-width: 460px"
                    label-position="right"
                    ref="formRef"
                    :rules="rules"
            >
                <el-form-item label="书名" prop="title">
                    <el-input v-model="bookForm.title"/>
                </el-form-item>
                <el-form-item label="作者" prop="author">
                    <el-input v-model="bookForm.author"/>
                </el-form-item>
                <el-form-item label="简介" prop="desc">
                    <el-input v-model="bookForm.desc" type="textarea" :rows="5"/>
                </el-form-item>
                <el-form-item label="类型" prop="type_name">
                    <el-select v-model="bookForm.type_name" class="m-2" placeholder="书籍类型" size="default">
                        <el-option
                                v-for="item in typeList"
                                :value="item.type_name"
                                :key="item.type_id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="库存" prop="nums">
                    <el-input-number v-model="bookForm.nums" :min="0"/>
                </el-form-item>
                <el-form-item label="封面图URL" prop="cover_url">
                    <el-input v-model="bookForm.cover_url" placeholder="没有可为空"/>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="addBookDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addBookSubmit()">添加</el-button>
              </span>
            </template>
        </el-dialog>
    </div>

  <!-- 书籍信息表格 -->
    <div>
        <el-table
                @selection-change="handleSelectionChange"
                :data="bookList"
                height="79vh"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                size="large"
                :cell-style="{'text-align':'center'}"
                :header-cell-style="{'text-align':'center'}"
                :tooltip-options="tooltipOption"
        >
            <el-table-column type="selection" width="55"/>
            <el-table-column prop="bid" label="ID" width="100"/>
            <el-table-column label="封面图" width="180">
                <template #default="scope">
                    <div style="height: 120px;width: 90px; margin: 0 auto">
                        <el-image :src="scope.row.cover_url">
                            <template #placeholder>
                                <div class="image-slot"><span class="dot"></span></div>
                            </template>
                        </el-image>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="书名" width="200"/>
            <el-table-column prop="author" label="作者" width="200"/>
            <el-table-column prop="desc" label="简介" show-overflow-tooltip/>
            <el-table-column
                    prop="type_name"
                    label="类型"
                    width="200"
                    :filters="filterTypeList"
                    :filter-method="filterHandler"
                    filter-placement="bottom"
            />
            <el-table-column prop="nums" label="库存" width="100"/>
            <el-table-column label="操作" align="right" width="200">
                <template #default="{row}">
                    <el-button type="primary" @click="handleEdit(row)">编辑</el-button>
                    <el-button type="danger" @click="handleDeleteBook(row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--  点击每行书籍的编辑按钮打开的对话框表单  -->
        <el-dialog
                v-model="editBookDialogVisible"
                title="编辑书籍信息"
                width="30%"
                align-center
                center
        >
            <el-form
                    label-width="100px"
                    :model="bookForm"
                    style="max-width: 460px"
                    label-position="right"
                    ref="formRef"
                    :rules="rules"
            >
                <el-form-item label="书名" prop="title">
                    <el-input v-model="bookForm.title"/>
                </el-form-item>
                <el-form-item label="作者" prop="author">
                    <el-input v-model="bookForm.author"/>
                </el-form-item>
                <el-form-item label="简介" prop="desc">
                    <el-input v-model="bookForm.desc" type="textarea" :rows="5"/>
                </el-form-item>
                <el-form-item label="类型" prop="type_name">
                    <el-select v-model="bookForm.type_name" class="m-2" placeholder="书籍类型" size="default">
                        <el-option
                                v-for="item in typeList"
                                :value="item.type_name"
                                :key="item.type_id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="库存" prop="nums">
                    <el-input-number v-model="bookForm.nums" :min="0"/>
                </el-form-item>
                <el-form-item label="封面图URL" prop="cover_url">
                    <el-input v-model="bookForm.cover_url" placeholder="没有可为空"/>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="editBookDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="editSubmit()">保存</el-button>
              </span>
            </template>
        </el-dialog>

    </div>
</template>

<script setup>
import {deleted, get, post} from "@/net";
import {ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {useUserStore} from "@/stores";
import router from "@/router";
import {Search} from "@element-plus/icons-vue";

const userStore = useUserStore()

//编辑/添加书籍信息表单的可见性
const editBookDialogVisible = ref(false)
const addBookDialogVisible = ref(false)

//搜索框选择的搜索类型
const searchInputSelected = ref('')

//搜索框的输入文本
const searchInputText = ref('')

// 多选框选中的书籍
const selectedRows = ref([]);
// 多选框选中的书籍ids
const selectedRowIds = ref([]);

//书籍表格根据类型筛选的列表
const filterTypeList = ref([])

//表单的ref 用于添加校验规则
const formRef = ref()

//书籍表格中“简介”的tooltip样式
const tooltipOption = ref({
    'popperClass': 'table-tooltip',
})

//编辑书籍内容 和 添加新书籍 的表单内容规则限制
const rules = {
    title: [
        {required: true, message: '书名不能为空', trigger: 'blur'},
    ],
    author: [
        {required: true, message: '作者不能为空', trigger: 'blur'},
    ],
    type_name: [
        {required: true, message: '类型不能为空，如果无对应类别，请先添加类别', trigger: ['blur', 'change']},
    ],
    desc: [
        {max: 300, message: '简介最长300字符', trigger: 'blur'}
    ]
}

//表格的响应式class类名
const tableRowClassName = ({row}) => {
    if (row.nums === 0) {
        return 'danger-row'
    } else if (row.nums <= 10) {
        return 'warning-row'
    }
    return ''
}

//所有的书籍列表
const bookList = ref([
    {
        bid: 0,
        title: '',
        author: '',
        desc: '',
        type_name: '',
        nums: 0,
        cover_url: '',
    }
])

//所有的书籍类型列表
const typeList = ref([
    {
        type_id: 0,
        type_name: '',
    }
])

//待向后端提交的书籍信息（用于编辑/添加书籍
const bookForm = ref({
    bid: 0,
    title: '',
    author: '',
    desc: '',
    type_name: '',
    nums: 0,
    cover_url: '',
})

//刷新书籍列表
const freshBookList = () => {
    get('/api/book/get-all', (message) => {
        bookList.value = message
        bookList.value.sort((a, b) => a.nums - b.nums)
    }, (message) => {
        ElMessage.warning(message)
        if (!userStore.isLogin) router.push('/')
    })
}

//刷新类型列表
const freshTypeList = () => {
    get('/api/book/get-all-type', (message) => {
        typeList.value = message
        if (typeList.value.length !== filterTypeList.value.length) {
            filterTypeList.value = []
            typeList.value.forEach((item) => {
                filterTypeList.value.push({
                    text: item.type_name,
                    value: item.type_name,
                })
            })
        }
    }, (message) => {
        ElMessage.warning(message)
    })

}

//页面更新后刷新
freshTypeList()
freshBookList()

//多选框选择每次改变后执行
const handleSelectionChange = (val) => {
    // 设置选中的数据
    selectedRows.value = val;
    // 设置选中的数据ids
    selectedRowIds.value = val.map((item) => item.bid);
};

//每行书籍的删除操作
const handleDeleteBook = (row) => {
    const index = bookList.value.indexOf(row)
    if (index !== -1) {
        //确认框
        ElMessageBox.confirm(
            '《' + row.title + '》将会彻底删除，这不可撤销！',
            '删除警告',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            deleted('/api/book/delete-book/' + row.bid, row.bid, () => {
                ElMessage.success('成功删除了《' + row.title + '》')
                freshBookList()
            }, (message) => {
                ElMessage.warning(message)
            })
        }).catch(() => {
            ElMessage.info('你取消了删除')
        })
    } else {
        ElMessage.warning('出现了内部错误，请联系管理员')
    }
}

const handleDeleteType = (row) => {
    deleted('/api/book/delete-type/' + row.type_id, row.type_id, (message) => {
        ElMessage.success(message + row.type_name)
        freshTypeList()
        freshBookList()
    }, (message) => {
        ElMessage.warning(message)
    })
}


//每行书籍的编辑操作
const handleEdit = (row) => {
    editBookDialogVisible.value = true
    bookForm.value.bid = row.bid
    bookForm.value.title = row.title
    bookForm.value.author = row.author
    bookForm.value.desc = row.desc
    bookForm.value.type_name = row.type_name
    bookForm.value.nums = row.nums
    bookForm.value.cover_url = row.cover_url
}

//编辑书籍信息的表单保存操作
const editSubmit = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {
            editBookDialogVisible.value = false
            post('/api/book/edit', bookForm.value, (message) => {
                ElMessage.success(message)
                freshBookList()
            })
        } else {
            ElMessage.warning('请完整填写书籍内容')
        }
    })
}


//批量删除操作
const deleteSelectedBooks = (selectedRowIds) => {
    if (selectedRowIds.length === 0) {
        ElMessage.warning('删除不能为空！')
    } else {
        //确认框 确认后执行then里的删除请求 取消则执行catch
        ElMessageBox.confirm(
            '你选中的书籍将会彻底删除，这不可撤销！',
            '删除警告',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(() => {
            deleted('/api/book/delete-book/' + selectedRowIds, selectedRowIds, (message) => {
                ElMessage.success(message)
                freshBookList()
            }, (message) => {
                ElMessage.warning(message)
            })
        }).catch(() => {
            ElMessage.info('你取消了批量删除')
        })
    }
}


const handleAddBook = () => {
    addBookDialogVisible.value = true
    // 添加书籍的时候不能自己输入书号，也不用给后端传输书号 直接数据库自增生成
    // 但编辑书籍的时候需要给后端传输书号 以在数据库找到要编辑的书籍
    // bookForm.value.bid = 0
    bookForm.value.title = ''
    bookForm.value.author = ''
    bookForm.value.desc = ''
    bookForm.value.type_name = ''
    bookForm.value.nums = 0
    bookForm.value.cover_url = ''
}


const addBookSubmit = () => {
    formRef.value.validate((isValid) => {
        if (isValid) {
            addBookDialogVisible.value = false
            post('/api/book/add', bookForm.value, (message) => {
                ElMessage.success(message)
                freshBookList()
            })
        } else {
            ElMessage.warning('请完整填写书籍内容')
        }
    })
}

const handleAddType = () => {
    ElMessageBox.prompt('请输入要添加的类别名称', '添加类别', {
        confirmButtonText: '添加',
        cancelButtonText: '取消',
        inputPattern: /^[\u4e00-\u9fa5]+$/,
        inputErrorMessage: '书籍类别只能是中文',
        inputPlaceholder: "类别名称后无需加‘类’"
    })
        .then(({value}) => {
            post('/api/book/add-type', {
                type_name: value
            }, (message) => {
                ElMessage.success(message + value);
                freshTypeList()
            }, (message) => {
                ElMessage.warning(message)
            })
        })
        .catch(() => {
            ElMessage.info('取消添加')
        })
}

const filterHandler = (value, row, column) => {
    const property = column['property']
    return row[property] === value
}

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        //post
        post('/api/book/search', {
            search_type: searchInputSelected.value,
            search_value: searchInputText.value
        }, (message) => {
            bookList.value = message
        })
    }
}

</script>

<style>
/* 书籍表格中根据库存数量设置背景颜色 */
.el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
}

.el-table .danger-row {
    --el-table-tr-bg-color: var(--el-color-danger-light-9);
}

/* 书籍表格中“简介”tooltip的样式 */
.table-tooltip {
    width: 300px;
}
</style>
