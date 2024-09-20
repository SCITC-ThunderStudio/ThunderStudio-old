<template>
  <el-dialog v-model="isUploadDialogVisible" :width="dialogWidth" destroy-on-close>
    <template #header>
      <div class="flex items-center gap-10">
        <div class="h-14 min-h-14 w-14 min-w-14 rounded-full bg-blue-200 text-center">
          <UploadFilled class="h-full w-full p-3 text-blue-600" />
        </div>
        <div>
          <p class="text-lg font-bold">周报上传</p>
          <p class="text-xs text-gray-500">仅支持word文档，大小限制为10MB，优先上传文件</p>
        </div>
      </div>
    </template>
    <div class="dialog-content">
      <div class="flex h-20 justify-between">
        <el-upload
          v-model:file-list="weeklyFile"
          :auto-upload="false"
          accept=".doc,.docx,.xml,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document"
          class="w-full"
        >
          <el-button type="success">选择文件</el-button>
        </el-upload>
        <el-input v-model="weeklyDataForm.weeklyTitle" class="max-h-8" placeholder="标题" />
      </div>
      <div>
        <el-input
          v-model="weeklyDataForm.weeklyContent"
          :maxlength="200"
          :rows="10"
          :show-word-limit="true"
          placeholder="内容"
          resize="none"
          type="textarea"
        />
      </div>
    </div>
    <template #footer>
      <el-button @click="isUploadDialogVisible = false">取消</el-button>
      <el-button :loading="isLoading" type="primary" @click="handleWeeklySubmit">上传</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { uploadWeeklyContent, uploadWeeklyFile } from '@/server'
import { usePublicStore } from '@/stores'
import type { weeklyDataReqForm } from '@/util/typeUtils'
import { ElMessage } from 'element-plus'
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { UploadFilled } from '@element-plus/icons-vue'

const emit = defineEmits(['onUpdateWeeklyTableData'])

const { windowWidth } = storeToRefs(usePublicStore())

// 周报表单
const weeklyDataForm = reactive<weeklyDataReqForm>({
  weeklyTitle: '',
  weeklyContent: ''
})

// 周报文件
const weeklyFile = ref<any>([])

// 是否显示上传对话框
const isUploadDialogVisible = ref<boolean>(false)
// 上传对话框宽度
const dialogWidth = ref<string>(windowWidth.value <= 768 ? '90%' : '40%')
// 是否在上传中
const isLoading = ref<boolean>(false)

watch(windowWidth, () => {
  if (windowWidth.value <= 768) {
    dialogWidth.value = '90%'
  } else {
    dialogWidth.value = '40%'
  }
})

// 处理周报上传
const handleWeeklySubmit = () => {
  if (
    weeklyFile.value.length === 0 &&
    weeklyDataForm.weeklyTitle === '' &&
    weeklyDataForm.weeklyContent === ''
  ) {
    ElMessage.error('请上传文件或填写内容!')
    return
  }
  if (weeklyFile.value.length !== 0) {
    if (weeklyFile.value[0].size / 1024 / 1024 > 10) {
      ElMessage.warning('文件过大')
      return
    }
    isLoading.value = true
    uploadWeeklyFile(weeklyFile.value[0].raw).then((res) => {
      isUploadDialogVisible.value = !res
      if (res) {
        weeklyDataForm.weeklyTitle = ''
        weeklyDataForm.weeklyContent = ''
        weeklyFile.value = []
        emit('onUpdateWeeklyTableData')
      }
      isLoading.value = false
    })
  } else {
    isLoading.value = true
    uploadWeeklyContent(weeklyDataForm).then((res) => {
      isUploadDialogVisible.value = !res
      if (res) {
        weeklyDataForm.weeklyTitle = ''
        weeklyDataForm.weeklyContent = ''
        weeklyFile.value = []
        emit('onUpdateWeeklyTableData')
      }
      isLoading.value = false
    })
  }
}

defineExpose({
  isUploadDialogVisible
})
</script>
