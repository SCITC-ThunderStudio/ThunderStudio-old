<template>
  <div class="weekly-overview-table-container rounded-lg bg-base-100">
    <div
      class="weekly-overview-table-title flex h-10 items-center justify-between border-b-2 border-solid border-inherit px-2"
    >
      <el-tooltip content="提醒" placement="right-start">
        <el-button icon="Bell" @click="handleRemind" circle></el-button>
      </el-tooltip>
      <h1 class="text-lg font-semibold">周报记录</h1>
      <div class="hidden items-center max-md:flex">
        <el-dropdown trigger="click">
          <span class="flex items-center">
            <el-icon size="20px"><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleGetTableData(0, currentPage)">本周</el-dropdown-item>
              <el-dropdown-item @click="handleGetTableData(1, currentPage)">全部</el-dropdown-item>
              <el-dropdown-item @click="handleTableRefresh" :disabled="isRefresh">
                刷新
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="flex items-center max-md:hidden">
        <el-radio-group v-model="radio" @change="handleGetTableData(radio, currentPage)">
          <el-radio-button label="本周" :value="0" />
          <el-radio-button label="全部" :value="1" />
        </el-radio-group>
        <el-button :loading="isRefresh" class="ml-2" type="primary" @click="handleTableRefresh">
          刷新
        </el-button>
      </div>
    </div>
    <div>
      <div class="weekly-overview-table h-96 px-2 pt-1">
        <el-table
          height="380px"
          highlight-current-row
          stripe
          v-loading="isLoading"
          :data="tableData"
        >
          <el-table-column align="center" label="学号" min-width="90px" prop="uid" />
          <el-table-column align="center" label="班级" min-width="95px" prop="studentClass" />
          <el-table-column align="center" label="姓名" min-width="70px" prop="name" />
          <el-table-column
            align="center"
            label="文件名"
            min-width="90px"
            prop="fileName"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="提交时间"
            min-width="105px"
            prop="submitTime"
            :sortable="radio ? false : true"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="评价"
            prop="evaluate"
            width="auto"
          />
          <el-table-column align="center" label="操作" min-width="100px">
            <template v-slot="scope">
              <el-button
                :icon="Edit"
                circle
                type="primary"
                @click="
                  () => {
                    evaluateForm.weeklyId = scope.row.weeklyId
                    evaluateForm.evaluateContent = scope.row.evaluate
                    isDialogVisible = true
                  }
                "
              />
              <el-button
                :icon="View"
                circle
                type="success"
                @click="handleFileView(scope.row.filePath)"
              />
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="weekly-overview-pagination pl-2 max-md:pl-1">
        <el-pagination
          v-model:current-page="currentPage"
          :layout="tableLayout"
          :pager-count="maxPagerNumber"
          :total="total"
          @current-change="handleGetTableData(radio, currentPage)"
        />
      </div>
    </div>
    <el-dialog v-model="isDialogVisible" title="评价周报" :width="dialogWidth">
      <el-input
        v-model="evaluateForm.evaluateContent"
        maxlength="100"
        rows="2"
        :show-word-limit="true"
        type="textarea"
        resize="none"
      />
      <template #footer>
        <div>
          <el-button @click="isDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleEvaluate" :loading="isSubmit">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { evaluateWeekly, getWeeklyRecord } from '@/server/module/admin'
import { usePublicStore } from '@/stores'
import type { EvaluateWeeklyForm, weeklyRecordTableDataReq } from '@/util/typeUtils'
import { Edit, View } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import { storeToRefs } from 'pinia'
import { onMounted, reactive, ref, watch } from 'vue'

const { windowWidth } = storeToRefs(usePublicStore())

// 周报记录表格数据
const tableData = ref<Array<weeklyRecordTableDataReq>>()
// 评价周报表单
const evaluateForm = reactive<EvaluateWeeklyForm>({
  weeklyId: 0,
  evaluateContent: ''
})

// 选项
const radio = ref<number>(0)
// 是否在刷新
const isRefresh = ref<boolean>(false)
// 表格是否在加载
const isLoading = ref<boolean>(false)
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
// 评价对话框是否可见
const isDialogVisible = ref<boolean>(false)
// 评价对话框宽度
const dialogWidth = ref<string>(windowWidth.value <= 758 ? '90%' : '50%')
// 周报评价是否在提交
const isSubmit = ref<boolean>(false)

onMounted(() => {
  handleGetTableData()
})
watch(windowWidth, (value) => {
  if (value <= 768) {
    maxPagerNumber.value = 5
    tableLayout.value = 'prev, pager, next'
    dialogWidth.value = '90%'
  } else {
    maxPagerNumber.value = 9
    tableLayout.value = 'total, prev, pager, next'
    dialogWidth.value = '50%'
  }
})

// 处理提醒
const handleRemind = () => {
  ElMessageBox.confirm('是否提醒未提交周报人员?', '再次确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {})
    .catch(() => {})
}
// 处理表格刷新
const handleTableRefresh = () => {
  isRefresh.value = true
  setTimeout(() => {
    isRefresh.value = false
  }, 500)
  handleGetTableData(radio.value, currentPage.value)
}
// 处理获取周报记录表格数据
const handleGetTableData = (
  radioValue: number = radio.value,
  currentPageValue: number = currentPage.value,
  pageSizeValue: number = pageSize.value
) => {
  isLoading.value = true
  getWeeklyRecord(radioValue, currentPageValue, pageSizeValue).then((res) => {
    if (res !== null) {
      console.debug('周报记录表格数据', res)
      tableData.value = res.tableData as Array<weeklyRecordTableDataReq>
      total.value = res.total
      currentPage.value = res.currentPage
    }
    isLoading.value = false
  })
}
// 处理周报评价
const handleEvaluate = () => {
  isSubmit.value = true
  evaluateWeekly(evaluateForm).then((res) => {
    isDialogVisible.value = !res
    isSubmit.value = false
    if (res) {
      handleGetTableData(radio.value, currentPage.value)
    }
  })
}
// 处理周报文件预览
const handleFileView = (filePath: string) => {
  const KKUrl = import.meta.env.VITE_KK_FILE_VIEW_URL + encodeURIComponent(window.btoa(filePath))
  window.open(KKUrl)
}
</script>

<style scoped></style>
