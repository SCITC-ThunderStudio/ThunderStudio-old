<template>
  <div class="overview-container">
    <div class="overview-card-box">
      <OverViewCard />
    </div>
    <div class="overview-bottom flex gap-4 pt-4 max-md:block">
      <div class="rank-box w-3/5 max-md:w-full">
        <OverViewRank />
      </div>
      <div class="w-2/5 max-md:mt-4 max-md:w-full">
        <div class="userinfo-box"><OverViewUserInfo /></div>
        <div class="announcement-box pt-4">
          <OverViewAnnouncement />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import OverViewAnnouncement from '@/components/OverViewAnnouncement.vue'
import OverViewCard from '@/components/OverViewCard.vue'
import OverViewRank from '@/components/OverViewRank.vue'
import OverViewUserInfo from '@/components/OverViewUserInfo.vue'
</script>
