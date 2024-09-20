<template>
  <el-dialog v-model="isDialogVisible" :width="dialogWidth" title="用户信息">
    <el-form :model="dataForm" class="grid grid-cols-2 gap-4">
      <el-form-item label="学号" prop="uid">
        <el-input v-model="dataForm.uid" :disabled="true" />
      </el-form-item>
      <el-form-item label="班级" prop="studentClass">
        <el-input v-model="dataForm.studentClass" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="dataForm.sex">
          <el-option key="男" value="男" label="男"></el-option>
          <el-option key="女" value="女" label="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="dataForm.name" />
      </el-form-item>
      <el-form-item label="权限" prop="authority">
        <el-select v-model="dataForm.authority">
          <el-option :key="0" :value="0" label="管理员"></el-option>
          <el-option :key="1" :value="1" label="负责人"></el-option>
          <el-option :key="2" :value="2" label="训员"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="dataForm.state">
          <el-option :key="0" :value="0" label="毕业"></el-option>
          <el-option :key="1" :value="1" label="在训"></el-option>
          <el-option :key="2" :value="2" label="退训"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="isDialogVisible = false">取消</el-button>
        <el-button :loading="isLoading" type="primary" @click="handleUpdate">更新</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { usePublicStore } from '@/stores'
import type { UpdateUserInfoReqFormAdmin } from '@/util/typeUtils'
import { updateUserInfo } from '@/server/module/admin'

const { windowWidth } = storeToRefs(usePublicStore())

// 用户信息更新表单
const dataForm = reactive<UpdateUserInfoReqFormAdmin>({
  uid: undefined,
  studentClass: '',
  sex: 'props.UserInfo.sex',
  name: '',
  authority: undefined,
  state: undefined
})

// 是否显示用户信息对话框
const isDialogVisible = ref<boolean>(false)
// 对话框宽度
const dialogWidth = ref<string>(windowWidth.value <= 768 ? '90%' : '40%')
// 是否在上传
const isLoading = ref<boolean>(false)

// 处理用户信息更新
const handleUpdate = () => {
  updateUserInfo(dataForm).then((res) => {
    isDialogVisible.value = !res
  })
}

// 初始化
const init = (userInfo: UpdateUserInfoReqFormAdmin) => {
  dataForm.uid = userInfo.uid
  dataForm.studentClass = userInfo.studentClass
  dataForm.sex = userInfo.sex
  dataForm.name = userInfo.name
  dataForm.authority = userInfo.authority
  dataForm.state = userInfo.state
}

defineExpose({
  isDialogVisible,
  init
})
</script>

<style scoped>
.el-form-item {
  margin-bottom: 0;
}
</style>
