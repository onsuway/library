<template>
    <div style="height: 100vh;">
        <div class="search">
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
    </div>

</template>

<script setup>
import {Search} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";
import {ref} from "vue";
import router from "@/router";

//搜索框选择的搜索类型
const searchInputSelected = ref('')

//搜索框的输入文本
const searchInputText = ref('')

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

</script>

<style scoped>
.search-main {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(128, 128, 128, 0.4);
    width: 700px;
    height: 100px;
    position: absolute;
    top: 140px;
    left: 33%;
}

.search-input {
    width: 600px;
    margin: auto;
}
</style>
