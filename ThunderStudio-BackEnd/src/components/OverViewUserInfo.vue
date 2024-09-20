<template>
  <div class="overview-user-info-container flex rounded-xl bg-base-100" style="height: 216px">
    <div class="overview-user-info-left w-3/12 rounded-l-xl bg-indigo-500 max-xl:w-2/5">
      <div class="flex h-3/5 items-center justify-center rounded-full">
        <img :src="userInfoStore.avatarPath" alt="avatar" class="h-20 w-20 rounded-full" />
      </div>
      <div class="text-center">
        <p class="h mb-2 text-base font-bold tracking-wider text-base-100">
          {{ userInfoStore.authority }}
        </p>
        <p class="text-xl font-bold tracking-wider text-base-100">{{ userInfoStore.name }}</p>
      </div>
    </div>
    <div class="overview-user-right w-9/12 pl-4 pt-4 max-xl:w-3/5">
      <div class="h-2/5">
        <h1 class="text-xl font-semibold">个人信息</h1>
        <p class="text-lg">{{ userInfoStore.uid }}</p>
        <p class="text-lg">{{ userInfoStore.studentClass }}</p>
      </div>
      <div>
        <h1 class="text-xl font-semibold">邮箱:</h1>
        <div class="h-1/5 overflow-auto">
          <span class="overflow-scroll text-lg">{{ userInfoStore.email }}</span>
        </div>
      </div>
      <div>
        <h1 class="h-1/5 text-xl font-semibold">入训时间:</h1>
        <div class="text-lg">{{ userInfoStore.joinDate }}</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserInfoStore } from '@/stores'

const userInfoStore = useUserInfoStore().user
</script>
