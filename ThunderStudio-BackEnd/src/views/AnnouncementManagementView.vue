<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { usePublicStore } from '@/stores'
import type { AnnouncementTableDataReq } from '@/util/typeUtils'
import { deleteAnnouncement, getAnnouncementAllList } from '@/server'
import { Bell } from '@element-plus/icons-vue'
import NoticeDialog from '@/components/NoticeDialog.vue'

const { windowWidth, windowHeight } = storeToRefs(usePublicStore())

const AnnouncementManagementViewRef = ref<any>(null)
const NoticeDialogRef = ref<any>(null)

// 公告表格数据
const tableData = ref<Array<AnnouncementTableDataReq>>([])

// 表格高度
const tableHeight = ref<number>(0)
// 表格是否在加载
const isLoading = ref<boolean>(false)
// 分页组件
const tableLayout = ref<string>(
  windowWidth.value <= 768 ? 'prev, pager, next' : 'total, prev, pager, next'
)
// 总记录数
const total = ref<number>(0)
// 一页显示多少条
const pageSize = ref<number>(windowWidth.value <= 768 ? 10 : 15)
// 当前页码
const pageNumber = ref<number>(1)
// 最大显示多少页
const maxPagerNumber = ref<number>(windowWidth.value <= 768 ? 5 : 9)

const deleteDialogVisible = ref<boolean>(false)

const tmp = ref<number>(0)

onMounted(() => {
  tableHeight.value = AnnouncementManagementViewRef.value.clientHeight - 42 - 2 - 40
  handleGetTableData()
})

watch([windowWidth, windowHeight], () => {
  tableHeight.value = AnnouncementManagementViewRef.value.clientHeight - 42 - 2 - 40
  if (windowWidth.value <= 768) {
    tableLayout.value = 'prev, pager, next'
    pageSize.value = 10
    maxPagerNumber.value = 5
  } else {
    tableLayout.value = 'total, prev, pager, next'
    pageSize.value = 15
    maxPagerNumber.value = 9
  }
})

const handleGetTableData = (
  pageSizeValue: number = pageSize.value,
  pageNumberValue: number = pageNumber.value
) => {
  isLoading.value = true
  getAnnouncementAllList(pageNumberValue, pageSizeValue).then((res) => {
    if (res !== null) {
      tableData.value = res.tableData as Array<AnnouncementTableDataReq>
      total.value = res.total
      pageNumber.value = res.pageNumber
    }
    isLoading.value = false
  })
}

// 打开公告对话框
const openNoticeDialog = () => {
  NoticeDialogRef.value.isShowDialog = true
}

const handleDelete = () => {
  deleteAnnouncement(tmp.value).then((res) => {
    deleteDialogVisible.value = !res
    if (res) {
      handleGetTableData()
    }
  })
}
</script>

<template>
  <div
    class="announcement-management-view-container h-full rounded-lg bg-base-100"
    ref="AnnouncementManagementViewRef"
  >
    <div
      class="announcement-management-view-header flex h-10 items-center justify-between border-b-2 border-solid border-inherit px-2"
    >
      <h1 class="text-lg font-semibold">公告管理</h1>
      <div>
        <el-button type="primary" :icon="Bell" @click="openNoticeDialog">发布公告</el-button>
      </div>
    </div>
    <div class="personnel-manage-view-main px-2 pt-1">
      <el-table
        v-loading="isLoading"
        :data="tableData"
        :height="tableHeight"
        highlight-current-row
        stripe
      >
        <el-table-column align="center" label="创建人" width="auto" prop="name" />
        <el-table-column align="center" label="标题" width="auto" prop="title" />
        <el-table-column align="center" label="内容" width="auto" prop="content" />
        <el-table-column align="center" label="创建时间" width="auto" prop="creationTime" />
        <el-table-column align="center" label="操作" min-width="105px">
          <template v-slot="scope">
            <el-button
              circle
              icon="Delete"
              type="danger"
              @click="
                () => {
                  deleteDialogVisible = true
                  tmp = scope.row.announcementId
                }
              "
            />
            <!--            <el-button-->
            <!--              circle-->
            <!--              icon="Edit"-->
            <!--              type="primary"-->
            <!--              @click="-->
            <!--                () => {-->
            <!--                  PersonnelManageUserInfoDialogRef.init(scope.row)-->
            <!--                  PersonnelManageUserInfoDialogRef.isDialogVisible = true-->
            <!--                }-->
            <!--              "-->
            <!--            />-->
          </template>
        </el-table-column>
      </el-table>

      <el-dialog v-model="deleteDialogVisible" title="Warning" width="500" align-center>
        <span>是否删除公告？</span>
        <template #footer>
          <div class="dialog-footer">
            <el-button
              @click="
                () => {
                  deleteDialogVisible = false
                }
              "
              >取消
            </el-button>
            <el-button type="primary" @click="handleDelete()">确定</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
    <div class="personnel-manage-view-pagination h-10 py-1 md:pl-2">
      <el-pagination
        v-model:current-page="pageNumber"
        v-model:page-size="pageSize"
        :layout="tableLayout"
        :page-sizes="[10, 15, 20, 30, 40]"
        :pager-count="maxPagerNumber"
        :total="total"
        @current-change="handleGetTableData(undefined, pageNumber)"
      />
    </div>
  </div>
  <NoticeDialog ref="NoticeDialogRef" @RefreshTable="handleGetTableData(pageSize, 1)" />
</template>

<style scoped></style>
