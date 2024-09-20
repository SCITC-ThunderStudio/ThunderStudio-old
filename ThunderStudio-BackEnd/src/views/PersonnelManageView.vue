<template>
  <div
    ref="personnelManageViewRef"
    class="personnel-manage-view-container h-full rounded-lg bg-base-100"
  >
    <div
      class="personnel-manage-view-header flex h-10 items-center justify-between border-b-2 border-solid border-inherit px-2"
    >
      <h1 class="text-lg font-semibold">人员管理</h1>
      <div class="flex md:hidden">
        <el-dropdown trigger="click">
          <span class="flex">
            <el-icon size="20px">
              <ArrowDown />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                icon="Upload"
                @click="PersonnelManageUploadDialogRef.isDialogVisible = true"
                >上传名单
              </el-dropdown-item>
              <el-dropdown-item icon="Download" @click="handleDownload">下载模板</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="max-md:hidden">
        <el-tooltip content="上传训练营名单" placement="top">
          <el-button
            :icon="Upload"
            type="primary"
            @click="PersonnelManageUploadDialogRef.isDialogVisible = true"
            >上传
          </el-button>
        </el-tooltip>
        <el-tooltip content="下载训练营名单模版" placement="top">
          <el-button :icon="Download" type="success" @click="handleDownload">下载</el-button>
        </el-tooltip>
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
        <el-table-column align="center" label="学号" min-width="85px" prop="uid" />
        <el-table-column align="center" label="班级" min-width="100px" prop="studentClass" />
        <el-table-column align="center" label="性别" min-width="55px" prop="sex" />
        <el-table-column align="center" label="姓名" min-width="70px" prop="name" />
        <el-table-column align="center" label="负责人" min-width="70px" prop="principal" />
        <el-table-column align="center" label="做题数" prop="questionNumber" width="auto" />
        <el-table-column align="center" label="权限" min-width="80px">
          <template v-slot="scope">
            <el-tag
              :type="
                scope.row.authority === 0
                  ? 'primary'
                  : scope.row.authority === 1
                    ? 'warning'
                    : 'success'
              "
            >
              {{
                scope.row.authority === 0 ? '管理员' : scope.row.authority === 1 ? '负责人' : '训员'
              }}
            </el-tag>
          </template>
        </el-table-column>
        <!--        <el-table-column label="权限" min-width="110px">-->
        <!--          <template v-slot="scope">-->
        <!--            <el-select-->
        <!--              v-model="scope.row.authority"-->
        <!--              @change="handleChangeAuthority(scope.row.uid, scope.row.authority)"-->
        <!--            >-->
        <!--              <el-option :key="0" :value="0" label="管理员"></el-option>-->
        <!--              <el-option :key="1" :value="1" label="学生"></el-option>-->
        <!--              <el-option :key="2" :value="2" label="负责人"></el-option>-->
        <!--            </el-select>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column align="center" label="状态" min-width="70px">
          <template v-slot="scope">
            <el-tag
              :type="
                scope.row.state === 0 ? 'danger' : scope.row.state === 1 ? 'success' : 'warning'
              "
            >
              {{ scope.row.state === 0 ? '退出' : scope.row.state === 1 ? '在训' : '毕业' }}
            </el-tag>
          </template>
        </el-table-column>
        <!--        <el-table-column label="状态" min-width="100px" width="auto">-->
        <!--          <template v-slot="scope">-->
        <!--            <el-select-->
        <!--              v-model="scope.row.state"-->
        <!--              @change="handleChangeState(scope.row.studentId, scope.row.state)"-->
        <!--            >-->
        <!--              <el-option :key="0" :value="0" label="毕业"></el-option>-->
        <!--              <el-option :key="1" :value="1" label="在训"></el-option>-->
        <!--              <el-option :key="2" :value="2" label="退训"></el-option>-->
        <!--            </el-select>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column align="center" label="操作" min-width="105px">
          <template v-slot="scope">
            <el-button circle icon="Delete" type="danger" @click="handleDelete(scope.row.uid)" />
            <el-button
              circle
              icon="Edit"
              type="primary"
              @click="
                () => {
                  PersonnelManageUserInfoDialogRef.init(scope.row)
                  PersonnelManageUserInfoDialogRef.isDialogVisible = true
                }
              "
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="personnel-manage-view-pagination h-10 py-1 md:pl-2">
      <el-pagination
        v-model:current-page="pageNumber"
        v-model:page-size="pageSize"
        :layout="tableLayout"
        :page-sizes="[10, 15, 20, 30, 40]"
        :pager-count="maxPagerNumber"
        :total="total"
        @size-change="handleGetTableData(pageSize, undefined)"
        @current-change="handleGetTableData(undefined, pageNumber)"
      />
    </div>
    <PersonnelManageUploadDialog ref="PersonnelManageUploadDialogRef" />
    <PersonnelManageUserInfoDialog ref="PersonnelManageUserInfoDialogRef" />
  </div>
</template>

<script lang="ts" setup>
import { deleteUser, downloadTrainingListFile, getPersonnelList } from '@/server/module/admin'
import { usePublicStore } from '@/stores'
import type { PersonnelTableDataReq } from '@/util/typeUtils'
import { ArrowDown, Download, Upload } from '@element-plus/icons-vue'
import { storeToRefs } from 'pinia'
import { onMounted, ref, watch } from 'vue'
import PersonnelManageUploadDialog from '@/components/PersonnelManageUploadDialog.vue'
import PersonnelManageUserInfoDialog from '@/components/PersonnelManageUserInfoDialog.vue'

const { windowWidth, windowHeight } = storeToRefs(usePublicStore())

const personnelManageViewRef = ref<any>()
const PersonnelManageUploadDialogRef = ref<any>()
const PersonnelManageUserInfoDialogRef = ref<any>()

// 人员表格数据
const tableData = ref<Array<PersonnelTableDataReq>>([])

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

onMounted(() => {
  tableHeight.value = personnelManageViewRef.value.clientHeight - 42 - 2 - 40
  handleGetTableData()
})

watch([windowWidth, windowHeight], () => {
  tableHeight.value = personnelManageViewRef.value.clientHeight - 42 - 2 - 40
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

// 处理训练营名单模版下载
const handleDownload = () => {
  downloadTrainingListFile()
}

/// 处理获取人员表格数据
const handleGetTableData = (
  pageSizeValue: number = pageSize.value,
  pageNumberValue: number = pageNumber.value
) => {
  console.log(pageNumberValue, pageSizeValue)
  isLoading.value = true
  getPersonnelList(pageSizeValue, pageNumberValue).then((res) => {
    if (res !== null) {
      console.debug('人员表格数据', res.tableData)
      tableData.value = res.tableData as Array<PersonnelTableDataReq>
      total.value = res.total
      // pageSize.value = res.pageSize
      pageNumber.value = res.pageNumber
    }
    isLoading.value = false
  })
}

// 处理人员删除
const handleDelete = (uid: number) => {
  deleteUser(uid).then((res) => {})
}

const currentChange = (currentNumber: number) => {
  console.log(pageSize.value, currentNumber)
  handleGetTableData(pageSize.value, currentNumber)
}
</script>

<style scoped></style>
