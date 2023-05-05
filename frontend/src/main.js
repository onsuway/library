import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import axios from "axios";

import 'element-plus/dist/index.css'

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080'
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

app.use(pinia)

app.use(router)

app.mount('#app')
