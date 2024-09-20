<template>
  <div :class="{ 'show-popup': !isHideLogInWin }" class="login-form-box-container">
    <div class="blur-bg-overlay" />
    <!-- 表单框 -->
    <div :class="{ 'show-signup': isToggle }" class="form-popup bg-base-100">
      <button
        class="close-btn btn btn-circle btn-ghost btn-xs"
        @click="
          () => {
            isHideLogInWin = true
            isToggle = false
          }
        "
      >
        <svg
          class="h-6 w-6 md:h-4 md:w-4"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M6 18L18 6M6 6l12 12"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
          />
        </svg>
      </button>
      <button
        :class="{ hidden: !isToggle }"
        class="return-btn btn btn-circle btn-ghost btn-xs flex text-center"
        @click="
          () => {
            isToggle = false
          }
        "
      >
        <ArrowLeft class="h-6 w-6 md:h-4 md:w-4" />
      </button>
      <!-- 登录框 -->
      <div class="form-box login">
        <div class="form-details m-2 hidden rounded-2xl bg-blue-100 px-10 md:flex">
          <img alt="SVG" class="svg" src="../assets/images/decorativePattern.svg" />
          <div class="mb-2.5 w-full text-xl text-emerald-300">Thunder Studio</div>
          <h2 class="mb-2.5 w-full text-sm font-bold">雷霆工作室</h2>
          <p class="text-base text-indigo-200">将来的你，一定会感谢现在奋斗的自己!</p>
        </div>
        <div class="form-content w-full p-11">
          <h1 class="mb-5 w-full py-1 text-center text-2xl font-bold uppercase text-blue-500">
            Account Login
          </h1>
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginFormRules"
            hide-required-asterisk
          >
            <el-form-item prop="studentId">
              <label class="mb-1.5 font-medium leading-4 text-gray-400">账号</label>
              <el-input
                v-model="loginForm.uid"
                :disabled="isHideLogInWin"
                placeholder="账号为学号"
                prefix-icon="User"
                type="text"
              />
            </el-form-item>
            <el-form-item prop="password">
              <label class="mb-1.5 font-medium leading-4 text-gray-400">密码</label>
              <el-input
                v-model="loginForm.password"
                :disabled="isHideLogInWin"
                clearable
                placeholder="请输入密码"
                prefix-icon="Lock"
                show-password
                type="password"
              />
            </el-form-item>
            <el-form-item>
              <el-row class="w-full">
                <el-col :span="12">
                  <el-switch
                    v-model="loginForm.isRememberMe"
                    :active-value="1"
                    :inactive-value="0"
                    active-text="记住我"
                  />
                </el-col>
                <el-col :span="12" class="text-end text-blue-500">
                  <a href="#" @click="isToggle = !isToggle">忘记密码?</a>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button :loading="isLoading" type="primary" @click="handleLogin(loginFormRef)"
                >登陆
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!-- 登录框 -->

      <!-- 找回密码框 -->
      <div class="form-box signup">
        <div class="form-details m-2 hidden rounded-2xl bg-blue-100 px-10 md:flex">
          <img alt="SVG" class="svg" src="../assets/images/decorativePattern.svg" />
          <div class="mb-2.5 w-full text-xl text-emerald-300">Thunder Studio</div>
          <h2 class="mb-2.5 w-full text-sm font-bold">雷霆工作室</h2>
          <p class="text-base text-indigo-200">将来的你，一定会感谢现在奋斗的自己!</p>
        </div>
        <div class="form-content w-full p-11">
          <h1 class="mb-5 w-full py-1 text-center text-2xl font-bold uppercase text-blue-500">
            Retrieve Password
          </h1>
          <el-form
            ref="updatePasswordRef"
            :model="updatePasswordFormF"
            :rules="updatePasswordFormFRules"
          >
            <el-form-item prop="studentId">
              <el-input
                v-model="updatePasswordFormF.uid"
                placeholder="请输入学号"
                prefix-icon="User"
                type="text"
              />
            </el-form-item>
            <el-form-item prop="email">
              <el-input
                v-model="updatePasswordFormF.email"
                placeholder="仅支持QQ邮箱"
                prefix-icon="Message"
                type="text"
              />
            </el-form-item>
            <el-form-item prop="captcha">
              <el-input
                v-model="updatePasswordFormF.captcha"
                placeholder="请输入验证码"
                prefix-icon="Comment"
                type="text"
              />
              <el-button
                :disabled="isDisabled"
                class="CaptchaBtnText"
                type="primary"
                @click="handleCaptcha(updatePasswordRef)"
                >{{ btnText }}
              </el-button>
            </el-form-item>
            <el-form-item prop="newPassword">
              <el-input
                v-model="updatePasswordFormF.newPassword"
                clearable
                placeholder="新密码"
                prefix-icon="Lock"
                show-password
                type="password"
              />
            </el-form-item>
            <el-form-item>
              <el-button
                :loading="isLoading"
                type="primary"
                @click="handleUpdatePassword(updatePasswordRef)"
                >提交
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!-- 找回密码框 -->
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { LoginReqForm, UpdatePasswordReqFormF } from '@/util/typeUtils'
import { ArrowLeft } from '@element-plus/icons-vue'
import { getCaptcha, login, updatePasswordF } from '@/server/module/public'
import { getRouter, getUserInfo } from '@/server/module/user'
import router from '@/router'
import { usePublicStore } from '@/stores'
import { addRouterList } from '@/router/permission'

const publicStore = usePublicStore()

const loginFormRef = ref<FormInstance>()
const updatePasswordRef = ref<FormInstance>()

// 是否隐藏登陆框
const isHideLogInWin = ref<boolean>(true)
// 是否切换登陆框
const isToggle = ref<boolean>(false)
// 验证码按钮文字
const btnText = ref<string | number>('发送验证码')
// 是否在加载中
const isLoading = ref<boolean>(false)
// 验证码图片链接
const VerificationCodeUrl = ref<string>('')
// 验证码发送按钮是否禁用
const isDisabled = ref<boolean>(false)

// 登陆表单
const loginForm = reactive<LoginReqForm>({
  uid: '',
  password: '',
  isRememberMe: 0
})

// 更新密码表单
const updatePasswordFormF = reactive<UpdatePasswordReqFormF>({
  uid: '',
  email: '',
  captcha: '',
  newPassword: ''
})

// 挂载前检查验证码冷却时间
onMounted(() => {
  if (publicStore.captchaTime < 60) {
    let timer = setInterval(() => {
      publicStore.captchaTime--
      btnText.value = `${publicStore.captchaTime}s后重新发送`
      isDisabled.value = true
      if (publicStore.captchaTime === 0) {
        isDisabled.value = false
        btnText.value = '重新发送'
        publicStore.captchaTime = 60
        clearInterval(timer)
      }
    }, 1000)
  }
})

// 处理登陆
const handleLogin = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid: any) => {
    if (valid) {
      isLoading.value = true
      login(loginForm).then((res) => {
        isHideLogInWin.value = res
        if (res) {
          formEl.resetFields()
          getUserInfo().then((res) => {
            if (res) {
              getRouter().then((res) => {
                if (res) {
                  addRouterList().then(() => {
                    router.push('/home/overView')
                  })
                }
              })
            }
          })
        }
        isLoading.value = false
      })
    } else {
      return false
    }
  })
}

// 处理获取验证码
const handleCaptcha = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validateField(['studentId', 'email']).then((valid) => {
    if (valid) {
      let timer = setInterval(() => {
        publicStore.captchaTime--
        btnText.value = `${publicStore.captchaTime}s后重新发送`
        isDisabled.value = true
        if (publicStore.captchaTime === 0) {
          isDisabled.value = false
          btnText.value = '重新发送'
          publicStore.captchaTime = 60
          clearInterval(timer)
        }
      }, 1000)
      setTimeout(() => {
        getCaptcha(updatePasswordFormF.uid as number, updatePasswordFormF.email)
      }, 1001)
    } else {
      return false
    }
  })
}

// 处理更新密码
const handleUpdatePassword = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validateField(['studentId', 'captcha', 'newPassword'], (valid) => {
    if (valid) {
      isLoading.value = true
      updatePasswordF(updatePasswordFormF).then((res) => {
        isLoading.value = false
        isToggle.value = !res
        if (res) {
          formEl.resetFields()
        }
      })
    }
  })
}

const loginFormRules = reactive<FormRules<typeof loginForm>>({
  uid: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 8, max: 8, message: '学号位数错误', trigger: 'blur' },
    { pattern: /^\d{8}$/, message: '学号为数字', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$/,
      message: '密码格式错误,必须包含大小写字母和数字的组合,不能使用特殊字符,长度在8-15之间',
      trigger: 'blur'
    }
  ]
})

const updatePasswordFormFRules = reactive<FormRules<typeof updatePasswordFormF>>({
  uid: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 8, max: 8, message: '学号位数为8位', trigger: 'blur' },
    { pattern: /^\d{8}$/, message: '学号为数字', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      pattern: /[1-9][0-9]{4,}@qq.com/,
      message: '邮箱格式错误,仅支持QQ邮箱,且前缀为数字',
      trigger: 'blur'
    }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9]{6}$/, message: '验证码为6位', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$/,
      message: '密码格式错误,必须包含大小写字母和数字的组合,不能使用特殊字符,长度在8-15之间',
      trigger: 'blur'
    }
  ]
})

defineExpose({
  isHideLogInWin
})
</script>

<style scoped>
.login-form-box-container .blur-bg-overlay {
  -webkit-backdrop-filter: blur(5px);
  backdrop-filter: blur(5px);
  height: 100%;
  left: 0;
  opacity: 0;
  pointer-events: none;
  position: fixed;
  top: 0;
  transition: 0.1s ease;
  width: 100%;
  z-index: 2;
}

.show-popup .blur-bg-overlay {
  opacity: 1;
  pointer-events: auto;
}

.login-form-box-container .form-popup {
  border-radius: 16px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.1);
  height: 410px;
  left: 50%;
  max-width: 720px;
  opacity: 0;
  pointer-events: none;
  position: fixed;
  top: 50%;
  transform: translate(-50%, -70%);
  width: 95%;
  z-index: 3;
}

.show-popup .form-popup {
  opacity: 1;
  pointer-events: auto;
  transform: translate(-50%, -50%);
  transition:
    transform 0.3s ease,
    opacity 0.1s;
}

.form-popup .close-btn {
  position: absolute;
  right: 10px;
  top: 10px;
}

.form-popup .return-btn {
  left: 50%;
  position: absolute;
  top: 10px;
}

.form-popup .form-box {
  display: flex;
  height: 100%;
}

.form-box .form-details {
  align-items: center;
  color: #fff;
  flex-direction: column;
  justify-content: center;
  max-width: 330px;
  position: relative;
  width: 100%;
}

[data-theme='dark'] .bg-blue-100 {
  background: #69b4ff; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #69b4ff,
    #0085ff,
    #006fff
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #69b4ff,
    #0085ff,
    #006fff
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}

[data-theme='light'] .bg-blue-100 {
  background: #69b4ff; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #69b4ff,
    #0085ff,
    #006fff
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #69b4ff,
    #0085ff,
    #006fff
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}

.form-details .svg {
  position: absolute;
  right: 0;
  top: 0;
}

:deep(.login .form-content .el-form-item:nth-child(1), .el-form-item:nth-child(2)) {
  margin-bottom: 16px;
}

:deep(.login .form-content .el-form-item:nth-child(3)) {
  margin-bottom: 24px;
}

:deep(.login .form-content .el-form-item:nth-child(4) .el-button) {
  width: 100%;
}

:deep(.signup .form-content .el-form-item) {
  margin-bottom: 24px;
}

:deep(.signup .form-content .el-form-item:nth-child(3) .el-form-item__content) {
  justify-content: space-between;
}

:deep(.signup .form-content .el-form-item:nth-child(3) .el-input) {
  width: 63%;
}

:deep(.signup .form-content .el-form-item:nth-child(3) .el-button) {
  width: 35%;
}

:deep(.signup .form-content .el-form-item:nth-child(5) .el-button) {
  width: 100%;
}

.form-popup .signup,
.form-popup.show-signup .login {
  display: none;
}

.form-popup.show-signup .signup {
  display: flex;
}

@media (max-width: 768px) {
  .form-popup .return-btn {
    left: 10px;
    position: absolute;
    top: 10px;
  }

  .form-box .form-content {
    padding: 30px 20px;
  }

  :deep(.form-content .el-form-item .el-form-item__content .el-input) {
    height: 40px;
  }

  :deep(.form-content .el-form-item .el-form-item__content .el-button) {
    height: 40px;
  }
}
</style>
