<template>
  <div class="user-info-container flex rounded-xl bg-base-100"></div>
</template>

<script setup lang="ts">
import { useUserInfoStore } from '@/stores'

const userInfoStore = useUserInfoStore().user

const avatarUrl = import.meta.env.VITE_AVATAR_URL + userInfoStore.avatarPath
</script>
