import {defineStore} from "pinia";
import {get} from "@/net";
import {ElMessage} from "element-plus";

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
    persist: true

    // const isLogin = ref(false)
    //
    // const user = ref({
    //     id: '',
    //     username: '',
    //     email: '',
    //     role: ''
    // })
    //
    // function setLoginStatus(LoginStatus){
    //     if (LoginStatus){
    //         isLogin.value = true
    //     }else {
    //         isLogin.value = !isLogin.value
    //     }
    // }
    //
    // function setUser(message) {
    //     user.id = message.id
    //     user.username = message.username
    //     user.email = message.email
    //     user.role = message.role
    // }
    //
    // function clearUser() {
    //     this.id = ''
    //     this.name = ''
    //     this.email = ''
    //     this.role = ''
    // }
    //
    // return {isLogin, user, setLoginStatus ,setUser, clearUser}
})
