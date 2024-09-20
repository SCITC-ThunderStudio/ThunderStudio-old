<template>
  <div class="clock-in-overview-table-container rounded-xl bg-base-100">
    <div
      class="clock-in-overview-table-title flex h-12 items-center justify-between border-b-2 border-solid border-inherit px-2 max-md:h-10"
    >
      <el-tooltip content="提醒" placement="right-start">
        <el-button
          icon="Bell"
          :size="buttonSize"
          @click="handleRemind"
          circle
          type="success"
        ></el-button>
      </el-tooltip>
      <h1 class="text-lg font-semibold max-md:text-base">打卡记录</h1>
      <el-radio-group v-model="radioValue" :size="radioSize" @change="handleGetTableData">
        <el-radio-button label="今日" :value="0" />
        <el-radio-button label="全部" :value="1" />
      </el-radio-group>
    </div>
    <div class="clock-in-overview-table h-80 px-2 pt-2">
      <el-table height="312px" highlight-current-row stripe v-loading="isLoading" :data="tableData">
        <el-table-column align="center" label="学号" min-width="90px" prop="uid" />
        <el-table-column align="center" label="班级" min-width="95px" prop="studentClass" />
        <el-table-column align="center" label="姓名" min-width="70px" prop="name" />
        <el-table-column align="center" label="做题数" min-width="90px" prop="number" sortable />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="日志"
          prop="logs"
          width="auto"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="提交时间"
          min-width="105px"
          prop="submitTime"
          sortable
        />
        <el-table-column
          :show-overflow-tooltip="true"
          align="center"
          label="更新时间"
          min-width="85px"
          prop="updateTime"
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
      </el-table>
    </div>
    <div class="clock-in-overview-pagination py-1 pl-4">
      <el-pagination
        v-model:current-page="currentPage"
        :layout="tableLayout"
        :pager-count="maxPagerNumber"
        :total="total"
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
  </div>
</template>

<script setup lang="ts">
import { getClockInRecord } from '@/server/module/admin'
import { usePublicStore } from '@/stores'
import type { ClockInRecordTableDataReq } from '@/util/typeUtils'
import { ElMessageBox } from 'element-plus'
import { storeToRefs } from 'pinia'
import { onMounted, ref, watch } from 'vue'

const { windowWidth } = storeToRefs(usePublicStore())

// 表格数据
const tableData = ref<Array<ClockInRecordTableDataReq>>()

// 选项值
const radioValue = ref<number>(0)
// 选项大小
const radioSize = ref<string>(windowWidth.value <= 768 ? 'small' : 'default')
// 按钮大小
const buttonSize = ref<string>(windowWidth.value <= 768 ? 'small' : 'default')
// 是否在加载
const isLoading = ref<boolean>(false)
// 图片链接
const pictureUrl = ref<string>('')
// 总记录数
const total = ref<number>(0)
// 当前页码
const currentPage = ref<number>(1)
// 一页显示多少条
const pageSize = ref<number>(10)
// 最大显示多少页
const maxPagerNumber = ref<number>(windowWidth.value <= 768 ? 5 : 9)
// 分页组件
const tableLayout = ref<string>(
  windowWidth.value <= 768 ? 'prev, pager, next' : 'total, prev, pager, next'
)
// 图片预览对话框是否可见
const isPicturePreviewDialogVisible = ref<boolean>(false)
// 图片预览对话框宽度
const picturePreviewDialogWidth = ref<string>(windowWidth.value <= 768 ? '85%' : '60%')

watch(windowWidth, () => {
  if (windowWidth.value <= 768) {
    radioSize.value = 'small'
    buttonSize.value = 'small'
    maxPagerNumber.value = 5
    tableLayout.value = 'prev, pager, next'
    picturePreviewDialogWidth.value = '85%'
  } else {
    radioSize.value = 'default'
    buttonSize.value = 'default'
    maxPagerNumber.value = 9
    tableLayout.value = 'total, prev, pager, next'
    picturePreviewDialogWidth.value = '60%'
  }
})

onMounted(() => {
  handleGetTableData(radioValue.value, currentPage.value, pageSize.value)
})

// 处理获取表格数据
const handleGetTableData = (radioValue: number, currentPage: number, pageSize: number) => {
  isLoading.value = true
  getClockInRecord(radioValue, currentPage, pageSize).then((res) => {
    console.debug('打卡记录数据', res.tableData)
    tableData.value = res.tableData as Array<ClockInRecordTableDataReq>
    total.value = res.total
    total.value = res.pageSize
    isLoading.value = false
  })
}

// 处理提醒
const handleRemind = () => {
  ElMessageBox.confirm('是否提醒未打卡人员?', '再次确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {})
    .catch(() => {})
}

// 处理图片预览
const handlePicturePreview = (url: string) => {
  pictureUrl.value = url
  isPicturePreviewDialogVisible.value = true
}

// 处理页码改变
const handleCurrentChange = (currentPage: number) => {
  handleGetTableData(radioValue.value, currentPage, pageSize.value)
}
</script>

<style scoped></style>
