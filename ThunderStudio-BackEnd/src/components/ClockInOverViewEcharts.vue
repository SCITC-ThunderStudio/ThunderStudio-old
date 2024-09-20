<template>
  <div class="clock-in-overview-echarts-container rounded-lg bg-base-100">
    <div
      class="clock-in-overview-echarts-title flex h-12 items-center border-b-2 border-solid border-inherit pl-2 max-md:h-10"
    >
      <h1 class="text-lg font-semibold max-md:text-base">做题概览</h1>
    </div>
    <div class="clock-in-overview-content h-64">
      <v-chart :option="option" autoresize />
    </div>
  </div>
</template>

<script setup lang="ts">
import { getQuestionQuantityDistribution } from '@/server/module/admin'
import { BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent } from 'echarts/components'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { onMounted, ref } from 'vue'
import VChart from 'vue-echarts'

use([TooltipComponent, GridComponent, BarChart, CanvasRenderer])

const option = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  grid: {
    left: '4%',
    right: '8%',
    bottom: '4%',
    containLabel: true
  },
  xAxis: [
    {
      name: '题量',
      nameLocation: 'end',
      type: 'category',
      data: [],
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
  yAxis: [
    {
      name: '人数',
      nameLocation: 'end',
      type: 'value'
    }
  ],
  series: [
    {
      name: '人数',
      type: 'bar',
      barWidth: '50%',
      data: []
    }
  ]
})

onMounted(() => {
  getQuestionQuantityDistribution().then((res) => {
    if (res !== null) {
      console.debug('题量概览数据', res)
      option.value.xAxis[0].data = res.number
      option.value.series[0].data = res.totalRecords
    }
  })
})
</script>

<style scoped></style>
