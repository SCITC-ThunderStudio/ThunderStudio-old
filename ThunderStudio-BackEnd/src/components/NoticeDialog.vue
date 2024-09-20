<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { AnnouncementFormReq } from '@/util/typeUtils'
import { ElMessage } from 'element-plus'
import { announcement } from '@/server'
import { useUserInfoStore } from '@/stores'

const userInfoStore = useUserInfoStore()

// 是否显示对话框
const isShowDialog = ref<boolean>(false)

const emit = defineEmits(['RefreshTable'])

const AnnouncementForm = reactive<AnnouncementFormReq>({
  uid: undefined,
  title: '',
  content: ''
})

const onSubmit = () => {
  if (AnnouncementForm.title === '' || AnnouncementForm.content === '') {
    ElMessage.warning('内容不能为空!')
  } else {
    announcement(userInfoStore.user.uid, AnnouncementForm.title, AnnouncementForm.content).then(
      (res) => {
        isShowDialog.value = !res
        emit('RefreshTable')
      }
    )
  }
}

defineExpose({
  isShowDialog
})
</script>

<template>
  <el-dialog v-model="isShowDialog" title="编辑公告" width="500">
    <div class="grid gap-4">
      <div>
        <el-input v-model="AnnouncementForm.title" placeholder="标题" />
      </div>
      <div>
        <el-input
          v-model="AnnouncementForm.content"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          placeholder="内容"
        />
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button
          @click="
            () => {
              isShowDialog = false
            }
          "
          >取消
        </el-button>
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>
