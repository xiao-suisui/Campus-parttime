<template>
  <view class="work-container">
    <!-- 搜索栏 -->
    <view class="search-bar">
      <uni-search-bar v-model="queryParams.postName" placeholder="搜索岗位" @confirm="onSearch" @cancel="onReset" />
    </view>

    <!-- 筛选条件 -->
    <view class="filter-bar">
      <uni-data-select v-model="queryParams.salaryUnit" :localdata="settlementOptions" placeholder="结算方式" @change="onFilter" clearable />
      <uni-data-select v-model="queryParams.workType" :localdata="workTypeOptions" placeholder="工作类型" @change="onFilter" clearable />
      <uni-data-select v-model="queryParams.workLocationType" :localdata="locationOptions" placeholder="工作地点" @change="onFilter" clearable />
    </view>

    <!-- 推荐区域 -->
    <view v-if="recommendList.length > 0" class="recommend-section">
      <view class="section-title">为你推荐</view>
      <scroll-view scroll-x class="recommend-scroll">
        <view v-for="item in recommendList" :key="item.postId" class="recommend-card" @click="goDetail(item.postId)">
          <view class="recommend-name">{{ item.postName }}</view>
          <view class="recommend-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ formatSalaryUnit(item.salaryUnit) }}</view>
          <view class="recommend-addr">{{ item.workAddress }}</view>
        </view>
      </scroll-view>
    </view>

    <!-- 岗位列表 -->
    <view class="section-title">{{ queryParams.postName ? '搜索结果' : '最新岗位' }}</view>
    <view class="post-list">
      <view v-for="item in postList" :key="item.postId" class="post-card" @click="goDetail(item.postId)">
        <view class="post-header">
          <text class="post-name">{{ item.postName }}</text>
          <text class="post-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ formatSalaryUnit(item.salaryUnit) }}</text>
        </view>
        <view class="post-footer">
          <view class="post-tags">
            <uni-tag :text="formatWorkType(item.workType)" type="primary" size="small" />
            <uni-tag :text="formatLocationType(item.workLocationType)" type="success" size="small" />
          </view>
          <text class="post-addr">{{ item.workAddress }}</text>
        </view>
      </view>

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
      recommendList: [],
      postList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postName: '',
        salaryUnit: '',
        workType: '',
        workLocationType: ''
      },
      settlementOptions: [
        { value: '1', text: '日结' },
        { value: '2', text: '周结' },
        { value: '3', text: '月结' }
      ],
      workTypeOptions: [
        { value: '1', text: '短期' },
        { value: '2', text: '长期' },
        { value: '3', text: '实习' }
      ],
      locationOptions: [
        { value: '1', text: '校内' },
        { value: '2', text: '校外' },
        { value: '3', text: '远程' }
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
    this.loadRecommend()
    this.loadPosts()
  },
  onReachBottom() {
    if (this.hasMore && !this.loading) {
      this.queryParams.pageNum++
      this.loadPosts()
    }
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
    loadRecommend() {
      getRecommendPosts(6).then(res => {
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

.filter-bar {
  display: flex;
  background-color: #fff;
  padding: 10rpx 20rpx;
  gap: 16rpx;
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

.empty-state {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>
