<template>
  <div class="clock-in-overview-container h-full">
    <div class="clock-in-top flex gap-4 max-md:block">
      <div class="clock-in-top-left w-1/2 max-md:w-full">
        <ClockInOverViewTableF />
      </div>
      <div class="clock-in-top-right w-1/2 max-md:mt-4 max-md:w-full">
        <ClockInOverViewEcharts />
      </div>
    </div>
    <div class="clock-in-bottom pt-4">
      <ClockInOverViewTable />
    </div>
  </div>
</template>

<script setup lang="ts">
import ClockInOverViewEcharts from '@/components/ClockInOverViewEcharts.vue'
import ClockInOverViewTable from '@/components/ClockInOverViewTable.vue'
import ClockInOverViewTableF from '@/components/ClockInOverViewTableF.vue'
</script>

<style scoped></style>
