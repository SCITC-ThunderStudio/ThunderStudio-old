<template>
  <el-container class="home-container h-full">
    <el-aside width="collapse">
      <HomeAside />
    </el-aside>
    <el-container>
      <el-header>
        <HomeHeader />
      </el-header>
      <el-main class="h-full bg-base-300">
        <RouterView />
      </el-main>
      <el-footer>
        <HomeFooter />
      </el-footer>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import HomeHeader from '@/components/HomeHeader.vue'
import HomeAside from '@/components/HomeAside.vue'
import HomeFooter from '@/components/HomeFooter.vue'
</script>

<style scoped>
.home-container .el-header {
  padding: 0;
  height: 4%;
  min-height: 64px;
}

.home-container .el-aside {
  box-shadow: 2px 0 8px #1d23290d;
}

.home-container .el-aside ul {
  border-right: none;
  height: 100%;
}

:deep(.el-aside .el-menu--collapse .el-menu-item .el-menu-tooltip__trigger) {
  padding: 0 15px;
}

.home-container .el-footer {
  height: 4%;
  min-height: 52px;
}
</style>
