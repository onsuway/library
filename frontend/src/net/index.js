import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserStore} from "@/stores";

const defaultError = () => ElMessage.error('发生了一个错误，请联系管理员')
const defaultFailure = (message) => ElMessage.warning(message)

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.success){
            success(data.message, data.status)
        } else {
            failure(data.message, data.status)
        }
    }).catch(error)
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        withCredentials: true
    }).then(({data}) => {
        if (data.success){
            success(data.message, data.status)
        } else {
            failure(data.message, data.status)
        }
    }).catch(error)
}

router.beforeEach((to,from,next) => {
    const userStore = useUserStore()
    if (userStore.auth.user != null && to.name.startsWith('welcome-')){
        next('/index')
    }else if (userStore.auth.user == null && to.fullPath.startsWith('/index')){
        next('/')
    }else if (to.matched.length === 0){
        next('/index')
    }else {
        next()
    }
})



export { post, get }
