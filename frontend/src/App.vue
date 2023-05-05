<script setup>
import { RouterView } from 'vue-router'
</script>
<template>
    <div id="app">
        <router-view/>
    </div>

</template>

<style scoped>

</style>
<!--<script setup>-->
<!--import {get} from "@/net";-->
<!--import {useUserStore} from "@/stores";-->
<!--import router from "@/router";-->

<!--const userStore = useUserStore()-->

<!--if (userStore.auth.user == null) {-->
<!--    get('/api/user/me', (message) => {-->
<!--        userStore.auth.user = message-->
<!--        router.push('/index')-->
<!--    }, () => {-->
<!--        userStore.auth.user = null-->
<!--    })-->
<!--}-->

<!--</script>-->
