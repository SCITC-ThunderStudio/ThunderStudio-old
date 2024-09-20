<template>
  <div class="rank-container rounded-xl bg-base-100 max-md:block max-md:w-full">
    <div class="rank-header flex h-2/6 items-center">
      <div class="h-4/6 w-5 flex-none rounded-e-xl bg-indigo-400 max-md:h-20"></div>
      <div
        class="mx-5 flex h-4/5 flex-auto items-center justify-around rounded-xl bg-blue-400 max-md:h-24"
      >
        <div class="pt-5">
          <p class="mb-2 text-2xl font-medium tracking-wider text-slate-100">Rank</p>
          <p class="text-center text-lg text-slate-100">
            {{ userInfoStore.ranking }}
          </p>
        </div>
        <div>
          <div class="avatar pt-3">
            <div class="h-20 w-20 rounded-full">
              <img :src="avatarUrl" alt="avatar" />
            </div>
          </div>
          <p class="text-center text-lg text-slate-100">{{ userInfoStore.name }}</p>
        </div>
        <div class="pt-5">
          <p class="mb-2 text-2xl font-medium tracking-wider text-slate-100">Number</p>
          <p class="text-center text-lg text-slate-100">{{ userInfoStore.questionNumber }}</p>
        </div>
      </div>
      <div class="h-4/6 w-5 flex-none rounded-s-xl bg-yellow-400 max-md:h-20"></div>
    </div>
    <div class="px-4 pt-4">
      <el-table :data="rankData" stripe class="rounded-lg">
        <el-table-column type="index" min-width="52%" label="排名" align="center" />
        <el-table-column prop="uid" width="auto" label="学号" align="center" />
        <el-table-column prop="studentClass" width="auto" label="班级" align="center" />
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

// 头像链接
const avatarUrl = import.meta.env.VITE_AVATAR_URL + userInfoStore.avatarPath
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
