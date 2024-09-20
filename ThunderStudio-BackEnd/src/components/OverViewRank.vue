<template>
  <div class="rank-container rounded-xl bg-base-100">
    <div class="rank-header flex h-32 items-center justify-between">
      <div class="h-24 w-5 rounded-e-xl bg-blue-500 max-md:hidden"></div>
      <div
        class="flex h-28 w-3/4 items-center justify-around rounded-xl bg-red-400 max-md:m-3 max-md:w-full"
      >
        <div class="text-center text-base-100">
          <p class="text-lg font-semibold">Rank</p>
          <p class="text-base font-medium">{{ userInfoStore.ranking }}</p>
        </div>
        <div class="text-center">
          <div class="avatar">
            <div class="h-16 rounded-full max-md:h-14">
              <img :src="userInfoStore.avatarPath" />
            </div>
          </div>
          <p class="text-base font-medium text-base-100">{{ userInfoStore.name }}</p>
        </div>
        <div class="text-center text-base-100">
          <p class="text-lg font-semibold">Number</p>
          <p class="text-base font-medium">{{ userInfoStore.questionNumber }}</p>
        </div>
      </div>
      <div class="h-24 w-5 rounded-s-xl bg-yellow-500 max-md:hidden"></div>
    </div>
    <div class="rank-content p-3">
      <el-table :data="rankData" stripe class="rounded-xl" height="400px">
        <el-table-column type="index" min-width="55px" label="排名" align="center" />
        <el-table-column prop="uid" min-width="90px" label="学号" align="center" />
        <el-table-column prop="studentClass" min-width="100px" label="班级" align="center" />
        <el-table-column prop="name" width="auto" label="姓名" align="center" />
        <el-table-column prop="questionNumber" width="auto" label="做题数" align="center" />
      </el-table>
    </div>
  </div>
</template>
<script setup lang="ts">
import { getRank } from '@/server'
import { useUserInfoStore } from '@/stores'
import type { rankTableDataReq } from '@/util/typeUtils'
import { onMounted, ref } from 'vue'

const userInfoStore = useUserInfoStore().user

// 排名数据
const rankData = ref<Array<rankTableDataReq>>([
  {
    uid: 0,
    studentClass: '',
    name: '',
    questionNumber: 0
  }
])

onMounted(() => {
  getRank().then((res) => {
    if (res !== null) {
      rankData.value = res
    }
  })
})
</script>
