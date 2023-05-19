<template>
    <div style="padding: 30px">
        <el-tabs v-model="activeName" class="demo-tabs">
            <el-tab-pane label="当前借阅" name="borrowing">
                <div style="display: flex">
                    <div style="flex: 1"></div>
                    <el-button type="primary" plain>批量续借</el-button>
                    <el-button type="primary" plain>批量归还</el-button>
                </div>
                <!-- TODO 待完成查询数据绑定当前借阅表格 -->
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
                    <el-table-column prop="bid" label="应还时间"/>
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
import {ElMessage} from "element-plus";
import {post} from "@/net";

const activeName = ref('borrowing')

//搜索框选择的搜索类型
const searchInputSelected = ref('title')

//搜索框的输入文本
const searchInputText = ref('')

const handleSearch = () => {
    if (searchInputText.value === '' || searchInputSelected.value === '') {
        ElMessage.warning('搜索类型和搜索输入均不允许为空')
    } else {
        //post
        ElMessage.success('搜索成功')
    }
}
</script>

<style scoped>

</style>
