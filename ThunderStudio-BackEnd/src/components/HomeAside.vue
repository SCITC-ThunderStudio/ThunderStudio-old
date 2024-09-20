<template>
  <el-menu
    :collapse="isCollapse"
    :default-active="activeIndex"
    class="el-menu-container bg-gray-500"
    router
  >
    <el-menu-item>
      <img alt="lightning-bolt" class="h-9" src="../assets/images/logo.png" />
      <div v-if="!isCollapse" class="logo-name text-base font-bold">
        <span class="text-blue-500">雷霆</span>工作室
      </div>
    </el-menu-item>
    <el-menu-item
      v-for="item in routerListStore.routerList"
      :key="item.id"
      :index="`/home/${item.path}`"
    >
      <el-icon>
        <component :is="item.meta.icon"></component>
      </el-icon>
      <template #title>
        <span>{{ item.meta.title }}</span>
      </template>
    </el-menu-item>
    <el-menu-item class="mt-12" @click="handleLoginOut">
      <el-icon>
        <SwitchButton />
      </el-icon>
      <template #title>退出登陆</template>
    </el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup>
import { SwitchButton } from '@element-plus/icons-vue'
import { usePublicStore, useRouterListStore } from '@/stores'
import { ref, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { ElMessageBox } from 'element-plus'
import { loginOut } from '@/server/module/public'

const routerListStore = useRouterListStore()

// 是否关闭侧边栏
const { isCollapse, windowWidth } = storeToRefs(usePublicStore())
// 当前路由菜单高亮
const activeIndex = ref<string>('/home/overView')

watch(windowWidth, (newValue) => {
  if (newValue <= 1024) {
    isCollapse.value = true
  }
  if (newValue > 1024) {
    isCollapse.value = false
  }
})

// 处理登出
const handleLoginOut = () => {
  ElMessageBox.confirm('是否退出登陆?', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      loginOut()
    })
    .catch(() => {})
}

defineExpose({
  isCollapse
})
</script>

<style scoped>
.el-menu-container:not(.el-menu--collapse) {
  width: 150px;
}

.el-menu--vertical:not(.el-menu--collapse):not(.el-menu--popup-container) .el-menu-item {
  padding-left: 15px;
}

.el-menu--vertical:not(.el-menu--collapse):not(.el-menu--popup-container)
  .el-menu-item:nth-child(1) {
  padding-left: 0;
}

.el-menu {
  padding: 5px;
}

.el-menu-container .el-menu-item {
  border-radius: var(--rounded-btn, 0.5rem /* 8px */);
  padding: 0 15px;
}

.el-menu-container .el-menu-item:hover {
  background-color: var(--fallback-bc, oklch(var(--bc) / 0.2));
  border-radius: var(--rounded-btn, 0.5rem /* 8px */);
}

.el-menu-container .el-menu-item:nth-child(1) {
  align-items: center;
  display: flex;
  justify-content: center;
  padding: 0;
}

.el-menu-container .el-menu-item:nth-child(1):hover {
  background-color: transparent;
  border-radius: 0;
}

.el-menu--collapse {
  padding: 5px;
}

.el-menu--collapse .logo-name {
  display: none;
}

:deep(.el-menu-container .el-menu--collapse .el-menu-item .el-menu-tooltip__trigger) {
  padding: 10px;
}
</style>
