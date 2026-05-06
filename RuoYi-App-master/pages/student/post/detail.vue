<template>
  <view class="detail-container">
    <view v-if="post" class="post-detail">
      <!-- 基本信息 -->
      <view class="info-card">
        <view class="post-title">{{ post.postName }}</view>
        <view class="post-salary">{{ post.salaryMin }}-{{ post.salaryMax }}元/{{ post.salaryUnit }}</view>
        <view class="post-tags">
          <uni-tag :text="post.workType" type="primary" size="small" />
          <uni-tag :text="post.workLocationType" type="success" size="small" />
          <uni-tag :text="post.settlementMethod" type="warning" size="small" />
        </view>
      </view>

      <!-- 详细信息 -->
      <view class="info-card">
        <view class="card-title">岗位详情</view>
        <view class="info-row">
          <text class="info-label">招聘人数</text>
          <text class="info-value">{{ post.recruitCount }}人</text>
        </view>
        <view class="info-row">
          <text class="info-label">工作地点</text>
          <text class="info-value">{{ post.workAddress }}</text>
        </view>
        <view class="info-row">
          <text class="info-label">截止时间</text>
          <text class="info-value">{{ post.deadlineTime }}</text>
        </view>
        <view class="info-row">
          <text class="info-label">发布时间</text>
          <text class="info-value">{{ post.createTime }}</text>
        </view>
      </view>

      <!-- 岗位要求 -->
      <view class="info-card">
        <view class="card-title">岗位要求</view>
        <text class="desc-text">{{ post.requirementDesc || '暂无要求' }}</text>
      </view>

      <!-- 岗位描述 -->
      <view class="info-card">
        <view class="card-title">工作描述</view>
        <text class="desc-text">{{ post.postDesc || '暂无描述' }}</text>
      </view>
    </view>

    <!-- 底部操作栏 -->
    <view class="bottom-bar">
      <view class="btn-favorite" @click="toggleCollect">
        <uni-icons :type="isCollected ? 'heart-filled' : 'heart'" :color="isCollected ? '#ff4757' : '#999'" size="24" />
        <text>{{ isCollected ? '已收藏' : '收藏' }}</text>
      </view>
      <button class="btn-apply" @click="handleApply">立即投递</button>
    </view>
  </view>
</template>

<script>
import { getPost } from '@/api/student/post'
import { addCollection, delCollection, listCollection } from '@/api/student/collection'
import { addApplication } from '@/api/student/application'

export default {
  data() {
    return {
      postId: null,
      post: null,
      isCollected: false,
      collectionId: null
    }
  },
  onLoad(options) {
    this.postId = options.id
    this.loadDetail()
    this.checkCollection()
  },
  methods: {
    loadDetail() {
      getPost(this.postId).then(res => {
        this.post = res.data
      })
    },
    checkCollection() {
      listCollection().then(res => {
        const list = res.rows || []
        const found = list.find(item => item.postId == this.postId)
        if (found) {
          this.isCollected = true
          this.collectionId = found.collectionId
        }
      })
    },
    toggleCollect() {
      if (this.isCollected) {
        delCollection(this.postId).then(() => {
          this.isCollected = false
          uni.showToast({ title: '已取消收藏', icon: 'success' })
        })
      } else {
        addCollection({ postId: this.postId }).then(() => {
          this.isCollected = true
          uni.showToast({ title: '收藏成功', icon: 'success' })
        })
      }
    },
    handleApply() {
      uni.showModal({
        title: '确认投递',
        content: '确定要投递该岗位吗？',
        success: (res) => {
          if (res.confirm) {
            addApplication({ postId: this.postId }).then(() => {
              uni.showToast({ title: '投递成功', icon: 'success' })
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.detail-container {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-bottom: 120rpx;
}

.info-card {
  background-color: #fff;
  margin: 20rpx;
  border-radius: 16rpx;
  padding: 30rpx;
}

.post-title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 16rpx;
}

.post-salary {
  font-size: 36rpx;
  color: #ff6b00;
  font-weight: bold;
  margin-bottom: 20rpx;
}

.post-tags {
  display: flex;
  gap: 12rpx;
}

.card-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
  padding-bottom: 16rpx;
  border-bottom: 1rpx solid #eee;
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}

.info-label {
  font-size: 28rpx;
  color: #999;
}

.info-value {
  font-size: 28rpx;
  color: #333;
}

.desc-text {
  font-size: 28rpx;
  color: #666;
  line-height: 1.8;
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20rpx 30rpx;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.btn-favorite {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 40rpx;
  font-size: 22rpx;
  color: #999;
}

.btn-apply {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 44rpx;
  height: 88rpx;
  line-height: 88rpx;
  font-size: 32rpx;
}
</style>
