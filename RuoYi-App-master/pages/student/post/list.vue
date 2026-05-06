<template>
  <view class="post-container">
    <!-- 搜索栏 -->
    <view class="search-bar">
      <uni-search-bar v-model="queryParams.postName" placeholder="搜索岗位" @confirm="onSearch" @cancel="onReset" />
    </view>

    <!-- 筛选条件 -->
    <view class="filter-bar">
      <uni-data-select v-model="queryParams.settlementMethod" :localdata="settlementOptions" placeholder="结算方式" @change="onFilter" />
      <uni-data-select v-model="queryParams.workLocationType" :localdata="locationOptions" placeholder="工作地点" @change="onFilter" />
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

    <!-- 岗位列表 -->
    <view class="post-list">
      <view v-for="item in postList" :key="item.postId" class="post-card" @click="goDetail(item.postId)">
        <view class="post-header">
          <text class="post-name">{{ item.postName }}</text>
          <text class="post-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ item.salaryUnit }}</text>
        </view>
        <view class="post-tags">
          <uni-tag :text="item.workType" type="primary" size="small" />
          <uni-tag :text="item.workLocationType" type="success" size="small" />
        </view>
        <view class="post-footer">
          <text class="post-addr">{{ item.workAddress }}</text>
          <text class="post-count">招{{ item.recruitCount }}人</text>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-if="postList.length === 0 && !loading" class="empty-state">
        <text>暂无岗位信息</text>
      </view>
    </view>
  </view>
</template>

<script>
import { listPost, getRecommendPosts } from '@/api/student/post'

export default {
  data() {
    return {
      loading: false,
      postList: [],
      recommendList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postName: '',
        settlementMethod: '',
        workLocationType: ''
      },
      settlementOptions: [
        { value: '日结', text: '日结' },
        { value: '周结', text: '周结' },
        { value: '月结', text: '月结' },
        { value: '完工结算', text: '完工结算' }
      ],
      locationOptions: [
        { value: '线上', text: '线上' },
        { value: '线下', text: '线下' },
        { value: '不限', text: '不限' }
      ],
      hasMore: true
    }
  },
  onLoad() {
    this.loadRecommend()
    this.loadPosts()
  },
  onPullDownRefresh() {
    this.queryParams.pageNum = 1
    this.hasMore = true
    this.postList = []
    this.loadPosts()
    this.loadRecommend()
  },
  onReachBottom() {
    if (this.hasMore && !this.loading) {
      this.queryParams.pageNum++
      this.loadPosts()
    }
  },
  methods: {
    loadRecommend() {
      getRecommendPosts(8).then(res => {
        this.recommendList = res.data || []
      })
    },
    loadPosts() {
      this.loading = true
      listPost(this.queryParams).then(res => {
        const rows = res.rows || []
        if (rows.length < this.queryParams.pageSize) {
          this.hasMore = false
        }
        this.postList = this.queryParams.pageNum === 1 ? rows : this.postList.concat(rows)
      }).finally(() => {
        this.loading = false
        uni.stopPullDownRefresh()
      })
    },
    onSearch() {
      this.queryParams.pageNum = 1
      this.hasMore = true
      this.postList = []
      this.loadPosts()
    },
    onReset() {
      this.queryParams.postName = ''
      this.onSearch()
    },
    onFilter() {
      this.queryParams.pageNum = 1
      this.hasMore = true
      this.postList = []
      this.loadPosts()
    },
    goDetail(postId) {
      uni.navigateTo({ url: '/pages/student/post/detail?id=' + postId })
    }
  }
}
</script>

<style lang="scss" scoped>
.post-container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.search-bar {
  background-color: #fff;
  padding: 10rpx 20rpx;
}

.filter-bar {
  display: flex;
  background-color: #fff;
  padding: 10rpx 20rpx;
  gap: 20rpx;
}

.recommend-section {
  background-color: #fff;
  margin-top: 20rpx;
  padding: 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  margin-bottom: 20rpx;
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

.post-list {
  padding: 20rpx;
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
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.post-salary {
  font-size: 30rpx;
  color: #ff6b00;
  font-weight: bold;
}

.post-tags {
  display: flex;
  gap: 12rpx;
  margin-bottom: 16rpx;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-addr {
  font-size: 24rpx;
  color: #999;
}

.post-count {
  font-size: 24rpx;
  color: #666;
}

.empty-state {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>
