<template>
  <div class="weekly-overview-table-f-container rounded-lg bg-base-100">
    <div
      class="weekly-overview-table-f-title flex h-10 items-center justify-between border-b-2 border-solid border-inherit px-2"
    >
      <h1 class="text-lg font-semibold">未提交周报人员</h1>
      <div class="hidden items-center max-md:flex">
        <el-dropdown trigger="click">
          <span class="flex items-center">
            <el-icon size="20px"><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleGetTableData(0)">本周</el-dropdown-item>
              <el-dropdown-item @click="handleGetTableData(1)">上周</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="flex justify-center max-md:hidden">
        <el-radio-group v-model="radio" @change="handleGetTableData(radio)">
          <el-radio-button label="本周" :value="0" />
          <el-radio-button label="上周" :value="1" />
        </el-radio-group>
      </div>
    </div>
    <div class="weekly-overview-table-f-table h-64 px-2 pt-1">
      <el-table :data="tableData" height="248px" v-loading="isLoading">
        <el-table-column prop="uid" label="学号" min-width="90px" align="center" />
        <el-table-column prop="studentClass" label="班级" min-width="95px" align="center" />
        <el-table-column prop="name" label="姓名" width="auto" align="center" />
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getWeeklyListF } from '@/server/module/admin'
import type { weeklyTableFDataReq } from '@/util/typeUtils'
import { onMounted, ref } from 'vue'

// 未提交周报人员表格数据
const tableData = ref<Array<weeklyTableFDataReq>>()

// 选项值
const radio = ref<number>(0)
// 表格是否在加载
const isLoading = ref<boolean>(false)

onMounted(() => {
  handleGetTableData()
})

// 处理获取表格数据
const handleGetTableData = (radioValue: number = radio.value) => {
  isLoading.value = true
  getWeeklyListF(radioValue).then((res) => {
    if (res !== null) {
      console.debug('未提交周报人员名单', res)
      tableData.value = res
    }
    isLoading.value = false
  })
}
</script>

<style scoped></style>
