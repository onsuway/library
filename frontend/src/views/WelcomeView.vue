<template>
    <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
        <div style="flex: 1; ">
            <el-image style="width: 100%; height: 100%;" fit="cover"
                      src="https://image.itbaima.net/images/90/image-20230419236235204.jpeg"/>
        </div>
        <div class="welcome-title">

            <div style="font-size: 35px;font-weight: bold">We are glad to have you here,</div>
            <div style="font-size: 21px;margin-top: 10px">and truly wish that you can benefit from being here.</div>
        </div>
        <div style="width: 400px; background-color:white;z-index: 1">
            <router-view v-slot="{ Component }">
                <!-- 过渡动画 淡入淡出 -->
                <transition name="el-fade-in-linear" mode="out-in">
                    <component :is="Component" style="height: 100%;" />
                </transition>
            </router-view>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title {
    position: absolute;
    bottom: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
</style>