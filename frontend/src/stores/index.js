import {defineStore} from "pinia";
import {get} from "@/net";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        isLogin: false,
        userInfo: {
            id: '',
            username: '',
            email: '',
            role: '',
            borrowing_nums: 0,
            credit: 0,
        }
    }),
    getters: {
        getUserInfo: (state) => state.userInfo,

    },

    actions: {
        login(message) {
            this.isLogin = true
            this.userInfo = message
        },
        logout() {
            this.isLogin = false
            this.userInfo = {
                id: '',
                username: '',
                email: '',
                role: '',
                borrowing_nums: 0,
                credit: 0,
            }
        },
        updateUserInfo() {
            get('/api/user/me', (message) => {
                this.login(message)
            }, () => {
                this.logout()
            })
        }
    },
    persist: {
        storage: sessionStorage,
    },
})
