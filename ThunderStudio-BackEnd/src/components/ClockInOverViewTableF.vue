<template>
  <div class="clock-in-overview-table-container rounded-xl bg-base-100">
    <div
      class="clock-in-overview-table-title flex h-12 items-center justify-between border-b-2 border-solid border-inherit px-2 max-md:h-10"
    >
      <h1 class="text-lg font-semibold max-md:text-base">未打卡人员</h1>
      <el-radio-group v-model="radioValue" :size="radioSize" @change="handleGetTableData">
        <el-radio-button label="今日" :value="0" />
        <el-radio-button label="昨日" :value="1" />
      </el-radio-group>
    </div>
    <div class="clock-in-overview-table h-64 px-2 pt-2">
      <el-table
        :data="clockInTableFData"
        height="240px"
        v-loading="isLoading"
        :header-cell-style="{
          background: 'var(--fallback-b1,oklch(var(--b1)/var(--tw-bg-opacity)))'
        }"
        :row-style="{ background: 'var(--fallback-b1,oklch(var(--b1)/var(--tw-bg-opacity)))' }"
      >
        <el-table-column prop="uid" label="学号" min-width="90px" align="center" />
        <el-table-column prop="studentClass" label="班级" min-width="95px" align="center" />
        <el-table-column prop="name" label="姓名" width="auto" align="center" />
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getClockInListF } from '@/server/module/admin'
import { usePublicStore } from '@/stores'
import type { clockInTableFDataReq } from '@/util/typeUtils'
import { storeToRefs } from 'pinia'
import { onMounted, ref, watch } from 'vue'

const { windowWidth } = storeToRefs(usePublicStore())

// 未打卡人员表格数据
const clockInTableFData = ref<Array<clockInTableFDataReq>>([])

// 选项值
const radioValue = ref<number>(0)
// 选项大小
const radioSize = ref<string>(windowWidth.value <= 768 ? 'small' : 'default')
// 是否在加载
const isLoading = ref<boolean>(false)

onMounted(() => {
  handleGetTableData()
})

// 处理获取表格数据
const handleGetTableData = () => {
  isLoading.value = true
  getClockInListF(radioValue.value).then((res) => {
    if (res !== null) {
      console.debug('未打卡人员名单数据', res)
      clockInTableFData.value = res
    }
    isLoading.value = false
  })
}

watch(windowWidth, (value) => {
  if (value <= 768) {
    radioSize.value = 'small'
  } else {
    radioSize.value = 'default'
  }
})
</script>

<style scoped></style>
