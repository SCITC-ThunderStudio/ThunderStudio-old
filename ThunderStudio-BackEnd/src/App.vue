<template>
  <RouterView />
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import { onBeforeUnmount, onMounted } from 'vue'
import { isDarkModeInSystem, unWatchDarkMode, watchDarkMode } from '@/util/themeUtils'
import { unWatchWindowSize, watchWindowSize } from '@/util/windowSizeUtils'

onMounted(() => {
  isDarkModeInSystem()
  watchDarkMode()
  watchWindowSize()
})

onBeforeUnmount(() => {
  unWatchDarkMode()
  unWatchWindowSize()
})
</script>
