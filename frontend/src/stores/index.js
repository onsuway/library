import {defineStore} from "pinia";
import {reactive} from "vue";

export const useUserStore = defineStore('userStore',() => {
    const auth = reactive({
        user: null
    })

    return { auth }
})