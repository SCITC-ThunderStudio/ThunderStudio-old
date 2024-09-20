<template>
  <el-dialog v-model="isDialogVisible" :width="dialogWidth">
    <el-upload
      ref="uploadRef"
      v-model:file-list="fileList"
      :auto-upload="false"
      :drag="true"
      :limit="1"
      :on-exceed="handleExceed"
      accept=".xls,.xlsx, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
    >
      <el-icon class="el-icon--upload">
        <upload-filled />
      </el-icon>
      <div class="el-upload__text">拖动或 <em>选择</em></div>
      <template #tip>
        <div class="el-upload__tip">仅支持Excel文件,大小限制为10MB</div>
      </template>
    </el-upload>
    <template #footer>
      <span>
        <el-button @click="isDialogVisible = false">取消</el-button>
        <el-button :loading="isLoading" type="primary" @click="handleUpload">上传</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { usePublicStore } from '@/stores'
import { ref, watch } from 'vue'
import { UploadFilled } from '@element-plus/icons-vue'
import {
  ElMessage,
  genFileId,
  type UploadInstance,
  type UploadProps,
  type UploadRawFile,
  type UploadUserFile
} from 'element-plus'
import { uploadTrainingListFile } from '@/server/module/admin'

const { windowWidth } = storeToRefs(usePublicStore())

const uploadRef = ref<UploadInstance>()

// 训练营名单文件
const fileList = ref<Array<UploadUserFile>>([])

// 是否显示上传对话框
const isDialogVisible = ref<boolean>(false)
// 对话框宽度
const dialogWidth = ref<string>(windowWidth.value <= 768 ? '90%' : '40%')
// 是否在上传
const isLoading = ref<boolean>(false)

watch(windowWidth, (value) => {
  if (value <= 768) {
    dialogWidth.value = '90%'
  } else {
    dialogWidth.value = '40%'
  }
})

// 处理文件上传限制
const handleExceed: UploadProps['onExceed'] = (files) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadRef.value!.handleStart(file)
}

// 处理训练营名单上传
const handleUpload = () => {
  if (fileList.value.length <= 0) {
    ElMessage.error('请上传训练营名单')
    return
  } else if (fileList.value[0].raw.size / 1024 / 1024 > 10) {
    ElMessage.error('文件大小超过限制,请重新上传')
    return
  } else {
    isLoading.value = true
    uploadTrainingListFile(fileList.value[0].raw).then((res) => {
      if (res) {
        uploadRef.value!.clearFiles()
        isDialogVisible.value = false
      }
      isLoading.value = false
    })
  }
}

defineExpose({
  isDialogVisible
})
</script>

<style scoped></style>
