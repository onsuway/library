<template>
    <div class="avatar-title">
        <el-avatar :size="150" src="https://i.imgloc.com/2023/04/01/i2lTJw.jpeg"/>
        <div style="font-size: 20px;margin-top: 10px;font-weight: bold">student</div>
    </div>
    <div class="description">
        <el-descriptions
                :column="3"
                size="large"
                border
        >
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon class="desc-icon">
                            <user/>
                        </el-icon>
                        用户名
                    </div>
                </template>
                {{userStore.userInfo.username}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon class="desc-icon">
                            <Message/>
                        </el-icon>
                        邮箱
                    </div>
                </template>
                {{userStore.userInfo.email}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon class="desc-icon">
                            <Avatar/>
                        </el-icon>
                        用户类型
                    </div>
                </template>
                <el-tag size="small">student</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon class="desc-icon">
                            <star-filled/>
                        </el-icon>
                        信用积分
                    </div>
                </template>
                {{userStore.userInfo.credit}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon class="desc-icon">
                            <Memo/>
                        </el-icon>
                        借阅中书籍数量
                    </div>
                </template>
                {{userStore.userInfo.borrowing_nums}}
            </el-descriptions-item>
        </el-descriptions>
    </div>

</template>

<script setup>
import {
    Avatar,
    Memo,
    Message,
    StarFilled,
    User
} from "@element-plus/icons-vue";
import {useUserStore} from "@/stores";

const userStore = useUserStore()

</script>

<style scoped>
.avatar-title {
    margin-top: 30px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.description {
    padding: 50px;
    margin-top: 20px;
}

.description .cell-item {
    font-size: 17px;
}

.desc-icon {
    vertical-align: middle;
}
</style>
