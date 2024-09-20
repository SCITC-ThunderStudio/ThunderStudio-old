<template>
  <div class="weekly-overview-container h-full">
    <div class="weekly-overview-top flex gap-4 max-md:block">
      <div class="weekly-overview-top-left w-1/2 max-md:w-full">
        <WeeklyOverviewTableF />
      </div>
      <div class="weekly-overview-top-right w-1/2 max-md:mt-4 max-md:w-full"></div>
    </div>
    <div class="weekly-overview-bottom pt-4">
      <WeeklyOverviewTable />
    </div>
  </div>
</template>

<script setup lang="ts">
import WeeklyOverviewTable from '@/components/WeeklyOverviewTable.vue'
import WeeklyOverviewTableF from '@/components/WeeklyOverviewTableF.vue'
</script>

<style scoped></style>
