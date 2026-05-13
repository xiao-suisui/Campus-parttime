<template>
  <view class="content">
    <!-- 顶部横幅 -->
    <view class="banner">
      <view class="banner-text">
        <text class="banner-title">校园兼职平台</text>
        <text class="banner-subtitle">找到适合你的兼职工作</text>
      </view>
    </view>

    <!-- 快捷入口 -->
    <view class="quick-entry">
      <view class="entry-item" @click="goPage('/pages/work/index')">
        <view class="entry-icon" style="background-color: #667eea;">
          <uni-icons type="list" size="24" color="#fff"></uni-icons>
        </view>
        <text class="entry-text">岗位大厅</text>
      </view>
      <view class="entry-item" @click="goPage('/pages/student/application/list')">
        <view class="entry-icon" style="background-color: #ff9f43;">
          <uni-icons type="paperplane-filled" size="24" color="#fff"></uni-icons>
        </view>
        <text class="entry-text">我的投递</text>
      </view>
      <view class="entry-item" @click="goPage('/pages/student/collection/list')">
        <view class="entry-icon" style="background-color: #ff4757;">
          <uni-icons type="heart-filled" size="24" color="#fff"></uni-icons>
        </view>
        <text class="entry-text">我的收藏</text>
      </view>
      <view class="entry-item" @click="goPage('/pages/student/resume/index')">
        <view class="entry-icon" style="background-color: #2ed573;">
          <uni-icons type="compose" size="24" color="#fff"></uni-icons>
        </view>
        <text class="entry-text">我的简历</text>
      </view>
    </view>

    <!-- 推荐岗位 -->
    <view v-if="recommendList.length > 0" class="section">
      <view class="section-header">
        <text class="section-title">为你推荐</text>
        <text class="section-more" @click="goPage('/pages/work/index')">查看更多</text>
      </view>
      <view class="recommend-list">
        <view v-for="item in recommendList" :key="item.postId" class="recommend-item" @click="goDetail(item.postId)">
          <view class="rec-info">
            <text class="rec-name">{{ item.postName }}</text>
            <text class="rec-addr">{{ item.workAddress }}</text>
          </view>
          <text class="rec-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ formatSalaryUnit(item.salaryUnit) }}</text>
        </view>
      </view>
    </view>

    <!-- 最新岗位 -->
    <view class="section">
      <view class="section-header">
        <text class="section-title">最新岗位</text>
        <text class="section-more" @click="goPage('/pages/work/index')">查看更多</text>
      </view>
      <view class="post-list">
        <view v-for="item in postList" :key="item.postId" class="post-item" @click="goDetail(item.postId)">
          <view class="post-info">
            <text class="post-name">{{ item.postName }}</text>
            <view class="post-tags">
              <uni-tag :text="formatWorkType(item.workType)" type="primary" size="small" />
              <uni-tag :text="formatLocationType(item.workLocationType)" type="success" size="small" />
            </view>
          </view>
          <text class="post-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ formatSalaryUnit(item.salaryUnit) }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { listPost, getRecommendPosts } from '@/api/student/post'

export default {
  data() {
    return {
      recommendList: [],
      postList: []
    }
  },
  onLoad() {
    this.loadData()
  },
  onPullDownRefresh() {
    this.loadData()
  },
  methods: {
    formatSalaryUnit(unit) {
      const map = { '1': '日结', '2': '周结', '3': '月结' }
      return map[unit] || unit || ''
    },
    formatWorkType(type) {
      const map = { '1': '短期', '2': '长期', '3': '实习' }
      return map[type] || type || ''
    },
    formatLocationType(type) {
      const map = { '1': '校内', '2': '校外', '3': '远程' }
      return map[type] || type || ''
    },
    loadData() {
      Promise.all([
        getRecommendPosts(4),
        listPost({ pageNum: 1, pageSize: 5 })
      ]).then(([recRes, postRes]) => {
        this.recommendList = recRes.data || []
        this.postList = postRes.rows || []
      }).finally(() => {
        uni.stopPullDownRefresh()
      })
    },
    goPage(url) {
      uni.navigateTo({ url: url })
    },
    goDetail(postId) {
      uni.navigateTo({ url: '/pages/student/post/detail?id=' + postId })
    }
  }
}
</script>

<style scoped>
.content {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60rpx 40rpx 80rpx;
  color: #fff;
}

.banner-title {
  font-size: 44rpx;
  font-weight: bold;
  display: block;
  margin-bottom: 12rpx;
}

.banner-subtitle {
  font-size: 28rpx;
  opacity: 0.8;
}

.quick-entry {
  display: flex;
  justify-content: space-around;
  background-color: #fff;
  margin: -40rpx 20rpx 20rpx;
  border-radius: 16rpx;
  padding: 30rpx 10rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
}

.entry-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.entry-icon {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12rpx;
}

.entry-text {
  font-size: 24rpx;
  color: #333;
}

.section {
  background-color: #fff;
  margin: 20rpx;
  border-radius: 16rpx;
  padding: 30rpx;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.section-more {
  font-size: 24rpx;
  color: #667eea;
}

.recommend-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.recommend-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx;
  background-color: #f8f9ff;
  border-radius: 12rpx;
}

.rec-info {
  flex: 1;
}

.rec-name {
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 8rpx;
}

.rec-addr {
  font-size: 24rpx;
  color: #999;
}

.rec-salary {
  font-size: 28rpx;
  color: #ff6b00;
  font-weight: bold;
  margin-left: 20rpx;
}

.post-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.post-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx;
  background-color: #f8f9ff;
  border-radius: 12rpx;
}

.post-info {
  flex: 1;
}

.post-name {
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 8rpx;
}

.post-tags {
  display: flex;
  gap: 8rpx;
}

.post-salary {
  font-size: 28rpx;
  color: #ff6b00;
  font-weight: bold;
  margin-left: 20rpx;
}
</style>
