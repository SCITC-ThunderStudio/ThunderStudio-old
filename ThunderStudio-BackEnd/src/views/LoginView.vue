<template>
  <el-container class="bg-base-100">
    <el-header>
      <LoginHeader @openLogInWin="openLogInWin" />
    </el-header>
    <el-main>
      <LoginMain />
      <LoginWin ref="LoginWinRef" />
    </el-main>
    <el-footer>
      <LoginFooter />
    </el-footer>
  </el-container>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import LoginHeader from '@/components/LoginHeader.vue'
import LoginMain from '@/components/LoginMain.vue'
import LoginWin from '@/components/LoginWin.vue'
import LoginFooter from '@/components/LoginFooter.vue'

const LoginWinRef = ref<any>(null)

// 打开登陆窗口
const openLogInWin = () => {
  LoginWinRef.value.isHideLogInWin = false
}
</script>

<style scoped>
.el-container {
  height: 100%;
}
</style>
