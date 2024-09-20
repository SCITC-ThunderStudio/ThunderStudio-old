import './assets/css/normalize.css'
import './assets/css/tailwind.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import pinia from '@/stores'

import App from './App.vue'
import router from './router'
import './router/permission'
import { initRouter } from '@/router/permission'
import { initKey } from '@/util/commonUtils'

const app = createApp(App)

app.use(ElementPlus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(pinia)

initRouter().then(() => {
  app.use(router)
  initKey().then(() => {
    app.mount('#app')
  })
})
