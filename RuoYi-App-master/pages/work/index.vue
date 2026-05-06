<template>
  <view class="work-container">
    <!-- 搜索栏 -->
    <view class="search-bar">
      <uni-search-bar v-model="keyword" placeholder="搜索岗位" @confirm="onSearch" />
    </view>

    <!-- 推荐区域 -->
    <view v-if="recommendList.length > 0" class="recommend-section">
      <view class="section-title">为你推荐</view>
      <scroll-view scroll-x class="recommend-scroll">
        <view v-for="item in recommendList" :key="item.postId" class="recommend-card" @click="goDetail(item.postId)">
          <view class="recommend-name">{{ item.postName }}</view>
          <view class="recommend-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ item.salaryUnit }}</view>
          <view class="recommend-addr">{{ item.workAddress }}</view>
        </view>
      </scroll-view>
    </view>

    <!-- 功能入口 -->
    <view class="section-title">功能入口</view>
    <view class="grid-body">
      <uni-grid :column="4" :showBorder="false" @change="changeGrid">
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="list" size="30" color="#667eea"></uni-icons>
            <text class="text">岗位大厅</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="paperplane-filled" size="30" color="#667eea"></uni-icons>
            <text class="text">投递管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="heart-filled" size="30" color="#ff4757"></uni-icons>
            <text class="text">我的收藏</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="chat-filled" size="30" color="#ff9f43"></uni-icons>
            <text class="text">互评管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="person-filled" size="30" color="#667eea"></uni-icons>
            <text class="text">个人信息</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box">
            <uni-icons type="compose" size="30" color="#667eea"></uni-icons>
            <text class="text">我的简历</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>

    <!-- 最新岗位 -->
    <view class="section-title">最新岗位</view>
    <view class="post-list">
      <view v-for="item in postList" :key="item.postId" class="post-card" @click="goDetail(item.postId)">
        <view class="post-header">
          <text class="post-name">{{ item.postName }}</text>
          <text class="post-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ item.salaryUnit }}</text>
        </view>
        <view class="post-footer">
          <view class="post-tags">
            <uni-tag :text="item.workType" type="primary" size="small" />
            <uni-tag :text="item.workLocationType" type="success" size="small" />
          </view>
          <text class="post-addr">{{ item.workAddress }}</text>
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
      keyword: '',
      recommendList: [],
      postList: []
    }
  },
  onLoad() {
    this.loadRecommend()
    this.loadPosts()
  },
  onPullDownRefresh() {
    this.loadRecommend()
    this.loadPosts()
  },
  methods: {
    loadRecommend() {
      getRecommendPosts(6).then(res => {
        this.recommendList = res.data || []
      })
    },
    loadPosts() {
      listPost({ pageNum: 1, pageSize: 10 }).then(res => {
        this.postList = res.rows || []
      }).finally(() => {
        uni.stopPullDownRefresh()
      })
    },
    onSearch() {
      if (this.keyword.trim()) {
        uni.navigateTo({ url: '/pages/work/index?keyword=' + this.keyword })
      }
    },
    changeGrid(e) {
      const routes = [
        '/pages/work/index',
        '/pages/student/application/list',
        '/pages/student/collection/list',
        '/pages/student/evaluation/list',
        '/pages/student/info/index',
        '/pages/student/resume/index'
      ]
      const index = e.detail.index
      if (index === 0) {
        // 岗位大厅 - 当前页面，不跳转
        return
      }
      uni.navigateTo({ url: routes[index] })
    },
    goDetail(postId) {
      uni.navigateTo({ url: '/pages/student/post/detail?id=' + postId })
    }
  }
}
</script>

<style lang="scss" scoped>
/* #ifndef APP-NVUE */
page {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  background-color: #f5f5f5;
  min-height: 100%;
  height: auto;
}

view {
  font-size: 14px;
  line-height: inherit;
}
/* #endif */

.search-bar {
  background-color: #fff;
  padding: 10rpx 20rpx;
}

.recommend-section {
  background-color: #fff;
  margin-top: 20rpx;
  padding: 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  padding: 20rpx 20rpx 10rpx;
}

.recommend-scroll {
  white-space: nowrap;
}

.recommend-card {
  display: inline-block;
  width: 280rpx;
  margin-right: 20rpx;
  padding: 20rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16rpx;
  color: #fff;
}

.recommend-name {
  font-size: 28rpx;
  font-weight: bold;
  margin-bottom: 10rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recommend-salary {
  font-size: 24rpx;
  margin-bottom: 8rpx;
}

.recommend-addr {
  font-size: 22rpx;
  opacity: 0.8;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.grid-body {
  background-color: #fff;
  margin-top: 10rpx;
  padding: 10rpx 0;
}

.grid-item-box {
  flex: 1;
  /* #ifndef APP-NVUE */
  display: flex;
  /* #endif */
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 15px 0;
}

.text {
  text-align: center;
  font-size: 26rpx;
  margin-top: 10rpx;
}

.post-list {
  padding: 10rpx 20rpx 20rpx;
}

.post-card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.post-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
}

.post-salary {
  font-size: 28rpx;
  color: #ff6b00;
  font-weight: bold;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-tags {
  display: flex;
  gap: 12rpx;
}

.post-addr {
  font-size: 24rpx;
  color: #999;
}
</style>
