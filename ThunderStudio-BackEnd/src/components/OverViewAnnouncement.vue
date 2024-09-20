<template>
  <div class="announcement-container h-80 rounded-xl bg-base-100">
    <div class="announcement-header flex h-10 items-center border-b-2 border-solid border-inherit">
      <p class="announcement-title ml-2 text-lg font-semibold">公 告</p>
    </div>
    <div class="announcement-content overflow-auto px-2 pt-2">
      <el-timeline style="height: 260px">
        <el-timeline-item
          v-for="(item, index) in announcementData"
          :timestamp="item.creationTime"
          placement="top"
          :key="index"
        >
          <el-card>
            <h1 class="text-lg font-semibold">{{ item.title }}</h1>
            <p class="text-base">{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getAnnouncement } from '@/server'
import type { announcementDataReq } from '@/util/typeUtils'
import { onMounted, ref } from 'vue'

// 公告数据
const announcementData = ref<Array<announcementDataReq>>([])

onMounted(() => {
  getAnnouncement().then((res) => {
    if (res !== null) {
      console.debug('公告数据', res)
      announcementData.value = res
    }
  })
})
</script>
