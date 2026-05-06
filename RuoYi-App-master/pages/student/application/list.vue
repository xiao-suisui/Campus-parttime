<template>
  <view class="application-container">
    <!-- 状态筛选 -->
    <view class="status-tabs">
      <view v-for="tab in statusTabs" :key="tab.value" :class="['tab-item', { active: currentStatus === tab.value }]" @click="switchStatus(tab.value)">
        {{ tab.text }}
      </view>
    </view>

    <!-- 投递列表 -->
    <view class="app-list">
      <view v-for="item in appList" :key="item.applicationId" class="app-card">
        <view class="app-header">
          <text class="app-post-name">{{ item.postName || '岗位' }}</text>
          <uni-tag :text="getStatusText(item.status)" :type="getStatusType(item.status)" size="small" />
        </view>
        <view class="app-info">
          <text class="app-enterprise">{{ item.enterpriseName || '' }}</text>
          <text class="app-time">{{ item.createTime }}</text>
        </view>
      </view>

      <view v-if="appList.length === 0 && !loading" class="empty-state">
        <text>暂无投递记录</text>
      </view>
    </view>
  </view>
</template>

<script>
import { listApplication } from '@/api/student/application'

export default {
  data() {
    return {
      loading: false,
      appList: [],
      currentStatus: '',
      statusTabs: [
        { value: '', text: '全部' },
        { value: '0', text: '待查看' },
        { value: '1', text: '已录用' },
        { value: '2', text: '已完成' },
        { value: '3', text: '已拒绝' }
      ]
    }
  },
  onLoad() {
    this.loadList()
  },
  onPullDownRefresh() {
    this.loadList()
  },
  methods: {
    loadList() {
      this.loading = true
      const query = { pageNum: 1, pageSize: 50 }
      if (this.currentStatus) {
        query.status = this.currentStatus
      }
      listApplication(query).then(res => {
        this.appList = res.rows || []
      }).finally(() => {
        this.loading = false
        uni.stopPullDownRefresh()
      })
    },
    switchStatus(status) {
      this.currentStatus = status
      this.loadList()
    },
    getStatusText(status) {
      const map = { '0': '待查看', '1': '已录用', '2': '已完成', '3': '已拒绝' }
      return map[status] || '未知'
    },
    getStatusType(status) {
      const map = { '0': 'warning', '1': 'success', '2': 'primary', '3': 'danger' }
      return map[status] || 'info'
    }
  }
}
</script>

<style lang="scss" scoped>
.application-container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.status-tabs {
  display: flex;
  background-color: #fff;
  padding: 20rpx;
  gap: 16rpx;
  flex-wrap: wrap;
}

.tab-item {
  padding: 12rpx 28rpx;
  border-radius: 32rpx;
  font-size: 26rpx;
  color: #666;
  background-color: #f5f5f5;
}

.tab-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.app-list {
  padding: 20rpx;
}

.app-card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.app-post-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
}

.app-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.app-enterprise {
  font-size: 26rpx;
  color: #666;
}

.app-time {
  font-size: 24rpx;
  color: #999;
}

.empty-state {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>
