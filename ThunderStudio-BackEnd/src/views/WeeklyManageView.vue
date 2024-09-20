<template>
  <div
    ref="weeklyManageContainerRef"
    class="weekly-manage-container h-full min-h-52 rounded-xl bg-base-100"
  >
    <div
      class="weekly-manage-header flex h-12 items-center justify-between border-b-2 border-solid border-inherit"
    >
      <el-button
        class="ml-3"
        type="primary"
        @click="weeklyManageUploadDialogRef.isUploadDialogVisible = true"
        >文件上传
      </el-button>
      <el-button :loading="loadingStatus" class="mr-3" type="primary" @click="handleTableRefresh"
        >刷新
      </el-button>
    </div>
    <div class="weekly-manage-content px-1.5 pt-3">
      <el-table
        v-loading="isLoading"
        :data="weeklyTableData"
        :height="tableHeight"
        class="rounded-lg"
        highlight-current-row
        stripe
      >
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="文件名"
          prop="fileName"
          width="auto"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="提交时间"
          min-width="85px"
          prop="submitTime"
        />
        <el-table-column
          align="center"
          label="评论"
          prop="evaluate"
          show-overflow-tooltip
          width="auto"
        />
        <el-table-column align="center" label="预览" width="60px">
          <template v-slot="scope">
            <el-button
              :icon="View"
              circle
              type="success"
              @click="handleFileView(scope.row.filePath)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="60px">
          <template v-slot="scope">
            <el-button
              :icon="Delete"
              circle
              type="danger"
              @click="handleWeeklyDelete(scope.row.weeklyId)"
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="weekly-manage-pagination h-14 p-3">
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
    <WeeklyManageUploadDialog
      ref="weeklyManageUploadDialogRef"
      @onUpdateWeeklyTableData="handleGetWeeklyTableData(currentPage, pageSize)"
    />
  </div>
</template>

<script lang="ts" setup>
import WeeklyManageUploadDialog from '@/components/WeeklyManageUploadDialog.vue'
import { deleteWeekly, getWeeklyList } from '@/server'
import { usePublicStore } from '@/stores'
import type { WeeklyTableDataReq } from '@/util/typeUtils'
import { Delete, View } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import { storeToRefs } from 'pinia'
import { onMounted, ref, watch } from 'vue'

const { windowWidth, windowHeight } = storeToRefs(usePublicStore())

const weeklyManageContainerRef = ref<any>()
const weeklyManageUploadDialogRef = ref<any>()

// 周报记录表格数据
const weeklyTableData = ref<Array<WeeklyTableDataReq>>([])

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
  tableHeight.value = weeklyManageContainerRef.value.clientHeight - 118
  handleGetWeeklyTableData(currentPage.value, pageSize.value)
})

watch([windowWidth, windowHeight], () => {
  tableHeight.value = weeklyManageContainerRef.value.clientHeight - 118
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
  handleGetWeeklyTableData(currentPage.value, pageSize.value)
}

// 处理获取周报表格数据
const handleGetWeeklyTableData = (currentPageValue: number, pageSizeValue: number) => {
  isLoading.value = true
  getWeeklyList(currentPageValue, pageSizeValue).then((res) => {
    console.debug('个人周报记录数据:', res)
    total.value = res.total
    weeklyTableData.value = res.tableData as Array<WeeklyTableDataReq>
    isLoading.value = false
  })
}

// 处理周报文件预览
const handleFileView = (filePath: string) => {
  const KKUrl = import.meta.env.VITE_KK_FILE_VIEW_URL + encodeURIComponent(window.btoa(filePath))
  window.open(KKUrl)
}

// 处理周报记录删除
const handleWeeklyDelete = (weeklyId: number) => {
  ElMessageBox.confirm('是否删除此周报记录?', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteWeekly(weeklyId).then((res) => {
        if (res) {
          handleGetWeeklyTableData(currentPage.value, pageSize.value)
        }
      })
    })
    .catch(() => {})
}

// 处理每页记录数改变
const handleSizeChange = (pageSize: number) => {
  handleGetWeeklyTableData(currentPage.value, pageSize)
}

// 处理页码改变
const handleCurrentChange = (currentPage: number) => {
  handleGetWeeklyTableData(currentPage, pageSize.value)
}
</script>
