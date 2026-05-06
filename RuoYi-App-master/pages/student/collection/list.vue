<template>
  <view class="collection-container">
    <view class="collection-list">
      <view v-for="item in collectionList" :key="item.collectionId" class="collection-card">
        <view class="card-body" @click="goDetail(item.postId)">
          <view class="card-header">
            <text class="card-name">{{ item.postName || '岗位' }}</text>
            <text class="card-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元/{{ item.salaryUnit }}</text>
          </view>
          <view class="card-footer">
            <text class="card-addr">{{ item.workAddress }}</text>
            <text class="card-time">{{ item.createTime }}</text>
          </view>
        </view>
        <view class="card-actions">
          <view class="btn-apply" @click.stop="handleApply(item.postId)">投递</view>
          <view class="btn-cancel" @click.stop="handleCancel(item.postId)">取消收藏</view>
        </view>
      </view>

      <view v-if="collectionList.length === 0 && !loading" class="empty-state">
        <text>暂无收藏岗位</text>
      </view>
    </view>
  </view>
</template>

<script>
import { listCollection, delCollection } from '@/api/student/collection'
import { addApplication } from '@/api/student/application'

export default {
  data() {
    return {
      loading: false,
      collectionList: []
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
      listCollection().then(res => {
        this.collectionList = res.rows || []
      }).finally(() => {
        this.loading = false
        uni.stopPullDownRefresh()
      })
    },
    goDetail(postId) {
      uni.navigateTo({ url: '/pages/student/post/detail?id=' + postId })
    },
    handleApply(postId) {
      uni.showModal({
        title: '确认投递',
        content: '确定要投递该岗位吗？',
        success: (res) => {
          if (res.confirm) {
            addApplication({ postId: postId }).then(() => {
              uni.showToast({ title: '投递成功', icon: 'success' })
            })
          }
        }
      })
    },
    handleCancel(postId) {
      uni.showModal({
        title: '取消收藏',
        content: '确定取消收藏该岗位吗？',
        success: (res) => {
          if (res.confirm) {
            delCollection(postId).then(() => {
              uni.showToast({ title: '已取消收藏', icon: 'success' })
              this.loadList()
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.collection-container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.collection-list {
  padding: 20rpx;
}

.collection-card {
  background-color: #fff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
}

.card-body {
  padding: 30rpx;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.card-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
}

.card-salary {
  font-size: 28rpx;
  color: #ff6b00;
  font-weight: bold;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-addr {
  font-size: 24rpx;
  color: #999;
}

.card-time {
  font-size: 22rpx;
  color: #ccc;
}

.card-actions {
  display: flex;
  border-top: 1rpx solid #f0f0f0;
}

.btn-apply {
  flex: 1;
  text-align: center;
  padding: 24rpx 0;
  font-size: 28rpx;
  color: #667eea;
  border-right: 1rpx solid #f0f0f0;
}

.btn-cancel {
  flex: 1;
  text-align: center;
  padding: 24rpx 0;
  font-size: 28rpx;
  color: #999;
}

.empty-state {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>
