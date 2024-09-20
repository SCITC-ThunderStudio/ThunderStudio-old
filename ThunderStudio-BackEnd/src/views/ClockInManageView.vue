<template>
  <div
    ref="clockInManageContainerRef"
    class="clock-in-manage-container h-full rounded-xl bg-base-100"
  >
    <div
      class="clock-in-manage-header flex h-12 items-center justify-between border-b-2 border-solid border-inherit"
    >
      <el-button
        class="ml-3"
        type="primary"
        @click="
          (ClockInManageUploadDialogRef.isUploadDialogVisible = true),
            (ClockInManageUploadDialogRef.uploadOption = 0)
        "
        >今日打卡
      </el-button>
      <el-button :loading="loadingStatus" class="mr-3" type="primary" @click="handleTableRefresh"
        >刷新
      </el-button>
    </div>
    <div class="clock-in-manage-content px-1.5 pt-3">
      <el-table
        v-loading="isLoading"
        :data="clockInTableData"
        :height="tableHeight"
        class="rounded-lg"
        highlight-current-row
        stripe
      >
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="提交时间"
          min-width="85px"
          prop="submitTime"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="更新时间"
          min-width="85px"
          prop="updateTime"
        />
        <el-table-column align="center" label="做题数" min-width="70px" prop="number" />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="日志"
          prop="logs"
          width="auto"
        />
        <el-table-column align="center" label="图片" width="100px">
          <template v-slot="scope">
            <div class="flex items-center">
              <el-image
                :src="scope.row.picturePath"
                @click="handlePicturePreview(scope.row.picturePath)"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" min-width="90px">
          <template v-slot="scope">
            <el-button
              :icon="Delete"
              circle
              type="danger"
              @click="handleClockInDelete(scope.row.recordsId)"
            />
            <el-button
              :icon="Edit"
              circle
              type="primary"
              @click="
                handleClockInUpdate(
                  scope.row.recordsId,
                  scope.row.picturePath,
                  scope.row.number,
                  scope.row.logs
                )
              "
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="clock-in-manage-pagination h-14 p-3">
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
    <el-dialog
      v-model="isPicturePreviewDialogVisible"
      :width="picturePreviewDialogWidth"
      destroy-on-close
      title="图片预览"
    >
      <img :src="pictureUrl" alt="Preview Image" class="picture-preview" />
    </el-dialog>
    <ClockInManageUploadDialog
      ref="ClockInManageUploadDialogRef"
      @onUpdateClockInTableData="handleGetClockInTableData(currentPage, pageSize)"
    />
  </div>
</template>

<script lang="ts" setup>
import ClockInManageUploadDialog from '@/components/ClockInManageUploadDialog.vue'
import { deleteClockIn, getClockInList } from '@/server'
import { usePublicStore } from '@/stores'
import { pictureUrlToFileObject } from '@/util/commonUtils'
import type { ClockInTableDataReq, clockInUpdateReqForm } from '@/util/typeUtils'
import { Delete, Edit } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import { storeToRefs } from 'pinia'
import { onMounted, reactive, ref, watch } from 'vue'

const { windowWidth, windowHeight } = storeToRefs(usePublicStore())

const clockInManageContainerRef = ref<any>()
const ClockInManageUploadDialogRef = ref<any>()

// 打卡记录表格数据
const clockInTableData = ref<Array<ClockInTableDataReq>>([])
// 打卡记录更新数据表单
const clockInUpdateForm = reactive<clockInUpdateReqForm>({
  recordsId: undefined,
  pictureFile: undefined,
  number: undefined,
  logs: ''
})

// 刷新状态
const loadingStatus = ref<boolean>(false)
// 表格高度
const tableHeight = ref<number>(0)
// 是否在加载
const isLoading = ref<boolean>(false)
// 图片链接
const pictureUrl = ref<string>('')
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
// 图片预览对话框是否可见
const isPicturePreviewDialogVisible = ref<boolean>(false)
// 图片预览对话框宽度
const picturePreviewDialogWidth = ref<string>(windowWidth.value <= 768 ? '85%' : '60%')

onMounted(() => {
  tableHeight.value = clockInManageContainerRef.value.clientHeight - 118
  handleGetClockInTableData(currentPage.value, pageSize.value)
})

watch([windowWidth, windowHeight], () => {
  tableHeight.value = clockInManageContainerRef.value.clientHeight - 118
  if (windowWidth.value <= 768) {
    picturePreviewDialogWidth.value = '85%'
    pageSize.value = 10
    maxPagerNumber.value = 5
    tableLayout.value = 'prev, pager, next'
  } else {
    picturePreviewDialogWidth.value = '60%'
    pageSize.value = 15
    maxPagerNumber.value = 9
    tableLayout.value = 'total, sizes, prev, pager, next'
  }
})

// 处理获取打卡记录
const handleGetClockInTableData = (currentPageValue: number, pageSizeValue: number) => {
  isLoading.value = true
  getClockInList(currentPageValue, pageSizeValue).then((res) => {
    console.debug('打卡记录数据:', res)
    total.value = res.total
    clockInTableData.value = res.tableData as Array<ClockInTableDataReq>
    isLoading.value = false
  })
}

// 处理表格刷新
const handleTableRefresh = () => {
  handleGetClockInTableData(currentPage.value, pageSize.value)
}

// 处理图片预览
const handlePicturePreview = (url: string) => {
  pictureUrl.value = url
  isPicturePreviewDialogVisible.value = true
}

// 处理打卡记录删除
const handleClockInDelete = (recordsId: number) => {
  ElMessageBox.confirm('是否删除此记录?', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteClockIn(recordsId).then((res) => {
        if (res) {
          handleGetClockInTableData(currentPage.value, pageSize.value)
        }
      })
    })
    .catch(() => {})
}

// 处理打卡记录更新
const handleClockInUpdate = (
  recordsId: number,
  pictureUrl: string,
  number: number,
  logs: string
) => {
  pictureUrl = pictureUrl.slice(pictureUrl.indexOf('/', 8) + 1, pictureUrl.length)
  pictureUrlToFileObject(pictureUrl).then((res) => {
    if (res !== null) {
      clockInUpdateForm.recordsId = recordsId
      clockInUpdateForm.pictureFile = res
      clockInUpdateForm.number = number
      clockInUpdateForm.logs = logs
      ClockInManageUploadDialogRef.value.handleClockInDataUpdate(clockInUpdateForm, 1)
    }
  })
}

// 处理每页记录数改变
const handleSizeChange = (pageSize: number) => {
  handleGetClockInTableData(currentPage.value, pageSize)
}

// 处理页码改变
const handleCurrentChange = (currentPage: number) => {
  handleGetClockInTableData(currentPage, pageSize.value)
}
</script>
