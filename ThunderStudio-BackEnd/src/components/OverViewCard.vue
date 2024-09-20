<template>
  <div class="overview-card-container flex gap-4 max-lg:grid max-lg:grid-cols-2 max-md:block">
    <div
      class="card-box relative flex h-20 w-1/4 items-center justify-between rounded-xl bg-base-100 pl-2 max-lg:w-full max-md:mb-4"
    >
      <div class="card-icon h-16 w-16 rounded-xl bg-blue-400">
        <UserFilled class="h-full w-full p-3 text-blue-100" />
      </div>
      <div class="card-content">
        <div class="stat-value">{{ overViewData.totalCount }}</div>
        <div class="stat-title">总训人员</div>
      </div>
      <div class="h-16 w-2.5 rounded-s-xl bg-blue-400"></div>
    </div>
    <div
      class="card-box relative flex h-20 w-1/4 items-center justify-between rounded-xl bg-base-100 pl-2 max-lg:w-full max-md:mb-4"
    >
      <div class="card-icon h-16 w-16 rounded-xl bg-green-400">
        <Checked class="h-full w-full p-3 text-green-100" />
      </div>
      <div class="card-content">
        <div class="stat-value">{{ overViewData.inTrainingCount }}</div>
        <div class="stat-title">在训人员</div>
      </div>
      <div class="h-16 w-2.5 rounded-s-xl bg-green-400"></div>
    </div>
    <div
      class="card-box relative flex h-20 w-1/4 items-center justify-between rounded-xl bg-base-100 pl-2 max-lg:w-full max-md:mb-4"
    >
      <div class="card-icon h-16 w-16 rounded-xl bg-orange-400">
        <List class="h-full w-full p-3 text-orange-100" />
      </div>
      <div class="card-content">
        <div class="stat-value">{{ overViewData.graduatedCount }}</div>
        <div class="stat-title">毕业人员</div>
      </div>
      <div class="h-16 w-2.5 rounded-s-xl bg-orange-400"></div>
    </div>
    <div
      class="card-box relative flex h-20 w-1/4 items-center justify-between rounded-xl bg-base-100 pl-2 max-lg:w-full max-md:mb-4"
    >
      <div class="card-icon h-16 w-16 rounded-xl bg-red-400">
        <Failed class="h-full w-full p-3 text-red-100" />
      </div>
      <div class="card-content">
        <div class="stat-value">{{ overViewData.withdrawalCount }}</div>
        <div class="stat-title">退训人员</div>
      </div>
      <div class="h-16 w-2.5 rounded-s-xl bg-red-400"></div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { getOverview } from '@/server'
import type { overViewDataReq } from '@/util/typeUtils'
import { Checked, Failed, List, UserFilled } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue'

// 概览数据
const overViewData = ref<overViewDataReq>({
  totalCount: 0,
  inTrainingCount: 0,
  graduatedCount: 0,
  withdrawalCount: 0
})

onMounted(() => {
  getOverview().then((res) => {
    if (res !== null) {
      overViewData.value = res
    }
  })
})
</script>
