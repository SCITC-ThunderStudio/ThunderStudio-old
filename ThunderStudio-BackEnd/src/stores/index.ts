import { createPinia } from 'pinia'
import PersistedState from 'pinia-plugin-persistedstate'

const pinia = createPinia() // 初始化 Pinia
pinia.use(PersistedState) // 激活 Pinia 插件,数据持久化

export * from './module/publicStore'
export * from './module/userInfoStore'
export * from './module/routeListStore'

export default pinia
