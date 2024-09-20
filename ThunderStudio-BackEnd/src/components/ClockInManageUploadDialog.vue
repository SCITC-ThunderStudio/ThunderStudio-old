<template>
  <el-dialog v-model="isUploadDialogVisible" :width="dialogWidth" destroy-on-close>
    <template #header>
      <div class="flex items-center gap-10">
        <div class="h-14 min-h-14 w-14 min-w-14 rounded-full bg-blue-200 text-center">
          <UploadFilled class="h-full w-full p-3.5 text-blue-600" />
        </div>
        <div>
          <p class="text-lg font-bold">打卡上传</p>
          <p class="text-xs text-gray-500">仅支持JPG/PNG格式，大小限制为10MB</p>
        </div>
      </div>
    </template>
    <el-form
      ref="clockInFormRef"
      :model="clockInDataForm"
      :rules="clockInDataFormRules"
      class="dialog-content flex justify-center gap-5"
    >
      <div class="dialog-content-left w-1/3">
        <el-form-item prop="number">
          <el-input v-model="clockInDataForm.number" placeholder="做题数" />
        </el-form-item>
        <el-upload
          :auto-upload="false"
          :class="{ uploadHide: isUploadVisible }"
          :file-list="imgFile"
          :on-change="handleFileChange"
          :on-preview="handlePicturePreview"
          :on-remove="
            () => {
              imgFile = []
            }
          "
          accept=".jpg,.png"
          list-type="picture-card"
        >
          <el-icon>
            <Plus />
          </el-icon>
        </el-upload>
      </div>
      <div class="dialog-content-right w-2/3">
        <el-input
          v-model="clockInDataForm.logs"
          :maxlength="100"
          :show-word-limit="true"
          placeholder="日志"
          resize="none"
          rows="9"
          type="textarea"
        />
      </div>
    </el-form>
    <template #footer>
      <el-button @click="isUploadDialogVisible = false">取消</el-button>
      <el-button
        :loading="isLoading"
        type="primary"
        @click="handleClockInDataFormSubmit(clockInFormRef)"
        >上传
      </el-button>
    </template>
    <el-dialog v-model="isDialogVisible" :width="dialogWidth">
      <img :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
  </el-dialog>
</template>

<script lang="ts" setup>
import { updateClockIn, uploadClockIn } from '@/server'
import { usePublicStore } from '@/stores'
import type { clockInReqForm, clockInUpdateReqForm } from '@/util/typeUtils'
import { ElMessage, type FormInstance, type FormRules, type UploadProps } from 'element-plus'
import { storeToRefs } from 'pinia'
import { computed, reactive, ref, watch } from 'vue'
import { Plus, UploadFilled } from '@element-plus/icons-vue'

const emit = defineEmits(['onUpdateClockInTableData'])

const { windowWidth } = storeToRefs(usePublicStore())

const clockInFormRef = ref<FormInstance>()

// 打卡数据表单
const clockInDataForm = reactive<clockInReqForm>({
  pictureFile: undefined,
  number: undefined,
  logs: ''
})
// 打卡记录id
const recordsId = ref<number>()
// 是否显示上传对话框
const isUploadDialogVisible = ref<boolean>(false)
// 上传对话框宽度
const dialogWidth = ref<string>(windowWidth.value <= 768 ? '90%' : '40%')
// 文件列表
const imgFile = ref<any>([])
// 是否显示预览图片窗口
const isDialogVisible = ref<boolean>(false)
// 图片链接
const dialogImageUrl = ref<string>('')
// 是否在上传中
const isLoading = ref<boolean>(false)
// 上传选项选项
const uploadOption = ref<number>(0)

watch(windowWidth, () => {
  if (windowWidth.value <= 768) {
    dialogWidth.value = '90%'
  } else {
    dialogWidth.value = '40%'
  }
})

watch(uploadOption, () => {
  if (uploadOption.value === 0) {
    clockInDataForm.pictureFile = undefined
    clockInDataForm.number = undefined
    clockInDataForm.logs = undefined
    imgFile.value = []
  }
})

// 处理文件变更
const handleFileChange = (file: File, fileList: []) => {
  imgFile.value = fileList.splice(-1)
}

// 处理图片预览
const handlePicturePreview: UploadProps['onPreview'] = (uploadFile) => {
  if (uploadFile.url === undefined) {
    dialogImageUrl.value = uploadFile.toString()
  } else {
    dialogImageUrl.value = uploadFile.url!
  }
  isDialogVisible.value = true
}

// 限制只显示一个图片
const isUploadVisible = computed({
  get() {
    return imgFile.value.length >= 1
  },
  set() {}
})

// 处理打卡记录更新
const handleClockInDataUpdate = (clockInUpdateDataForm: clockInUpdateReqForm, option: number) => {
  const uploadUserFileL: object = {
    name: clockInUpdateDataForm.pictureFile.name,
    percentage: 0,
    raw: clockInUpdateDataForm.pictureFile,
    size: clockInUpdateDataForm.pictureFile.size,
    status: 'ready',
    uid: Math.floor(1000000000000 + Math.random() * 9000000000000),
    url: URL.createObjectURL(clockInUpdateDataForm.pictureFile)
  }
  recordsId.value = clockInUpdateDataForm.recordsId
  imgFile.value[0] = uploadUserFileL
  clockInDataForm.number = clockInUpdateDataForm.number
  clockInDataForm.logs = clockInUpdateDataForm.logs
  uploadOption.value = option
  isUploadDialogVisible.value = true
}

// 处理打卡记录上传
const handleClockInDataFormSubmit = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate().then((valid) => {
    if (valid) {
      if (imgFile.value.length === 0) {
        ElMessage.warning('请上传文件')
        return false
      }
      if (imgFile.value[0].size / 1024 / 1024 > 10) {
        ElMessage.warning('文件过大')
        return false
      }
      clockInDataForm.pictureFile = imgFile.value[0].raw
      isLoading.value = true
      switch (uploadOption.value) {
        case 0:
          uploadClockIn(
            clockInDataForm.pictureFile,
            clockInDataForm.number,
            clockInDataForm.logs
          ).then((res) => {
            isLoading.value = false
            if (res) {
              isUploadDialogVisible.value = false
              formEl.resetFields()
              clockInDataForm.pictureFile = undefined
              clockInDataForm.logs = ''
              imgFile.value = []
              emit('onUpdateClockInTableData')
            }
          })
          break
        case 1:
          updateClockIn(
            recordsId.value,
            clockInDataForm.pictureFile,
            clockInDataForm.number,
            clockInDataForm.logs
          ).then((res) => {
            isLoading.value = false
            if (res) {
              isUploadDialogVisible.value = false
              formEl.resetFields()
              clockInDataForm.pictureFile = undefined
              clockInDataForm.logs = ''
              imgFile.value = []
              emit('onUpdateClockInTableData')
            }
          })
      }
    } else {
      return false
    }
  })
}

const clockInDataFormRules = reactive<FormRules<typeof clockInDataForm>>({
  number: [
    { required: true, message: '请输入数字', trigger: 'blur' },
    { pattern: /^[1-9]\d*$/, message: '值为数字,且大于0', trigger: 'blur' }
  ]
})

defineExpose({
  uploadOption,
  isUploadDialogVisible,
  handleClockInDataUpdate
})
</script>

<style scoped>
:deep(.dialog-content-left .el-upload-list),
:deep(.dialog-content-left .el-upload-list .el-upload) {
  width: 100%;
}

:deep(.dialog-content-left .el-upload-list .is-ready) {
  width: 100%;
  margin-right: 0;
}

:deep(.uploadHide .el-upload-list--picture-card .el-upload--picture-card) {
  display: none;
}
</style>
