<template>
  <div
    ref="trainingManageViewContainerRef"
    class="training-manage-container h-full min-h-52 rounded-xl bg-base-100"
  >
    <div
      class="training-manage-header flex h-12 items-center justify-between border-b-2 border-solid border-inherit"
    >
      <p class="ml-3 text-lg font-semibold">训员管理</p>
      <el-button :loading="loadingStatus" class="mr-3" type="primary" @click="handleTableRefresh"
        >刷新
      </el-button>
    </div>
    <div class="training-manage-content rounded-3xl px-1.5 pt-3">
      <el-table
        id="table"
        v-loading="isLoading"
        :data="traineeTableData"
        :default-sort="{ prop: 'submitTime', order: 'descending' }"
        :height="tableHeight"
        class="rounded-lg"
        highlight-current-row
        stripe
      >
        <el-table-column align="center" label="学号" min-width="90px" prop="uid" />
        <el-table-column align="center" label="班级" min-width="95px" prop="studentClass" />
        <el-table-column align="center" label="姓名" min-width="70px" prop="name" />
        <el-table-column align="center" label="周报状态" min-width="80px">
          <template v-slot="scope">
            <el-tag :type="scope.row.weeklyState === 0 ? 'danger' : 'success'">
              {{ scope.row.clockInState === 0 ? '未提交' : '已提交' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="打卡状态" min-width="80px">
          <template v-slot="scope">
            <el-tag :type="scope.row.clockInState === 0 ? 'danger' : 'success'">
              {{ scope.row.clockInState === 0 ? '未打卡' : '已打卡' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="在训状态" width="100px">
          <template v-slot="scope">
            <el-select
              v-model="scope.row.state"
              placeholder="Select"
              @change="handleUpdatePersonnelState(scope.row.uid, scope.row.state)"
            >
              <el-option
                v-for="item in personnelState"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div ref="tablePaginationRef" class="training-manage-pagination h-14 p-3">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :layout="tableLayout"
        :page-sizes="[5, 10, 15, 20]"
        :pager-count="maxPagerNumber"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getTrainee, updatePersonnelState } from '@/server'
import { usePublicStore } from '@/stores'
import type { PersonnelStateReqForm, TraineeTableDataReq } from '@/util/typeUtils'
import { storeToRefs } from 'pinia'
import { onMounted, reactive, ref, watch } from 'vue'

const { windowWidth, windowHeight } = storeToRefs(usePublicStore())

const trainingManageViewContainerRef = ref<any>()

// 训员表格数据
const traineeTableData = ref<Array<TraineeTableDataReq>>()

// 人员状态表单
const personnelStateForm = reactive<PersonnelStateReqForm>({
  uid: undefined,
  personnelState: undefined
})

// 人员状态选项
const personnelState = reactive([
  {
    value: 0,
    label: '退出'
  },
  {
    value: 1,
    label: '在训'
  },
  {
    value: 2,
    label: '毕业'
  }
])

// 刷新状态
const loadingStatus = ref<boolean>(false)
// 表格高度
const tableHeight = ref<number>(0)
// 是否在加载
const isLoading = ref<boolean>(false)
// 总记录数
const total = ref<number>(0)
// 当前页码
const currentPage = ref<number>(1)
// 一页显示多少条
const pageSize = ref<number>(windowWidth.value <= 768 ? 10 : 15)
// 最大显示多少页
const maxPagerNumber = ref<number>(windowWidth.value <= 768 ? 5 : 9)
// 分页组件
const tableLayout = ref<string>(
  windowWidth.value <= 768 ? 'prev, pager, next' : 'total, sizes, prev, pager, next'
)

onMounted(() => {
  tableHeight.value = trainingManageViewContainerRef.value.clientHeight - 118
  handleGetTraineeTableData(currentPage.value, pageSize.value)
})
watch([windowWidth, windowHeight], () => {
  tableHeight.value = trainingManageViewContainerRef.value.clientHeight - 118
  if (windowWidth.value <= 768) {
    pageSize.value = 10
    maxPagerNumber.value = 5
    tableLayout.value = 'prev, pager, next'
  } else {
    pageSize.value = 15
    maxPagerNumber.value = 9
    tableLayout.value = 'total, sizes, prev, pager, next'
  }
})

// 处理表格刷新
const handleTableRefresh = () => {
  loadingStatus.value = true
  setTimeout(() => {
    loadingStatus.value = false
  }, 1000)
  handleGetTraineeTableData(currentPage.value, pageSize.value)
}
// 处理获取个人负责人员数据
const handleGetTraineeTableData = (currentPageValue: number, pageSizeValue: number) => {
  isLoading.value = true
  getTrainee(currentPageValue, pageSizeValue).then((res) => {
    console.debug('个人负责人员数据', res)
    isLoading.value = false
    traineeTableData.value = res.tableData as Array<TraineeTableDataReq>
    total.value = res.total
    pageSize.value = res.pageSize
  })
}
// 处理更新人员在训状态
const handleUpdatePersonnelState = (uid: number, personnelState: number) => {
  personnelStateForm.uid = uid
  personnelStateForm.personnelState = personnelState
  updatePersonnelState(personnelStateForm).then(() => {
    handleGetTraineeTableData(currentPage.value, pageSize.value)
  })
}
// 处理每页记录数改变
const handleSizeChange = (pageSize: number) => {
  handleGetTraineeTableData(currentPage.value, pageSize)
}
// 处理页码改变
const handleCurrentChange = (currentPage: number) => {
  handleGetTraineeTableData(currentPage, pageSize.value)
}
</script>
