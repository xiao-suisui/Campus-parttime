<template>
  <view class="detail-container">
    <view v-if="post" class="post-detail">
      <!-- 基本信息 -->
      <view class="info-card">
        <view class="post-title">{{ post.postName }}</view>
        <view class="post-salary">{{ post.salaryMin }}-{{ post.salaryMax }}元/{{ formatSalaryUnit(post.salaryUnit) }}</view>
        <view class="post-tags">
          <uni-tag :text="formatWorkType(post.workType)" type="primary" size="small" />
          <uni-tag :text="formatLocationType(post.workLocationType)" type="success" size="small" />
          <uni-tag :text="formatSalaryUnit(post.salaryUnit)" type="warning" size="small" />
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

      <!-- 企业评价 -->
      <view class="info-card">
        <view class="card-title">企业评价</view>
        <view v-if="evaluations.length > 0">
          <view class="eval-summary">
            <text class="eval-avg-score">{{ avgScore }}</text>
            <text class="eval-avg-label">分</text>
            <uni-rate :value="Math.round(avgScore)" disabled size="16" />
            <text class="eval-count">共{{ evalTotal }}条评价</text>
          </view>
          <view v-for="item in evaluations" :key="item.evaluationId" class="eval-item">
            <view class="eval-header">
              <uni-rate :value="item.score" disabled size="12" />
              <text class="eval-time">{{ item.evaluationTime }}</text>
            </view>
            <text class="eval-content">{{ item.content || '该学生未填写评价内容' }}</text>
          </view>
        </view>
        <view v-else class="eval-empty">
          <text>暂无评价</text>
        </view>
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
import { getStudentResume } from '@/api/student/resume'
import { listEnterpriseEvaluations } from '@/api/student/evaluation'

export default {
  data() {
    return {
      postId: null,
      post: null,
      isCollected: false,
      collectionId: null,
      evaluations: [],
      evalTotal: 0,
      avgScore: 0
    }
  },
  onLoad(options) {
    this.postId = options.id
    this.loadDetail()
    this.checkCollection()
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
    loadDetail() {
      getPost(this.postId).then(res => {
        this.post = res.data
        if (this.post && this.post.enterpriseId) {
          this.loadEvaluations(this.post.enterpriseId)
        }
      })
    },
    loadEvaluations(enterpriseId) {
      listEnterpriseEvaluations(enterpriseId, { pageNum: 1, pageSize: 3 }).then(res => {
        this.evaluations = res.rows || []
        this.evalTotal = res.total || 0
        if (this.evaluations.length > 0) {
          const sum = this.evaluations.reduce((acc, cur) => acc + (cur.score || 0), 0)
          this.avgScore = (sum / this.evaluations.length).toFixed(1)
        }
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
      getStudentResume().then(res => {
        if (!res.data || !res.data.resumeId) {
          uni.showModal({
            title: '提示',
            content: '您还没有完善简历，请先创建简历后再投递',
            confirmText: '去完善',
            success: (r) => {
              if (r.confirm) {
                uni.navigateTo({ url: '/pages/student/resume/index' })
              }
            }
          })
          return
        }
        const resumeId = res.data.resumeId
        uni.showModal({
          title: '确认投递',
          content: '确定要投递该岗位吗？',
          success: (modalRes) => {
            if (modalRes.confirm) {
              addApplication({ postId: this.postId, resumeId: resumeId }).then(() => {
                uni.showToast({ title: '投递成功', icon: 'success' })
              })
            }
          }
        })
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

.eval-summary {
  display: flex;
  align-items: center;
  margin-bottom: 24rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.eval-avg-score {
  font-size: 48rpx;
  font-weight: bold;
  color: #ff9900;
  margin-right: 6rpx;
}

.eval-avg-label {
  font-size: 24rpx;
  color: #999;
  margin-right: 16rpx;
}

.eval-count {
  font-size: 24rpx;
  color: #999;
  margin-left: 16rpx;
}

.eval-item {
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}

.eval-item:last-child {
  border-bottom: none;
}

.eval-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12rpx;
}

.eval-time {
  font-size: 24rpx;
  color: #ccc;
}

.eval-content {
  font-size: 28rpx;
  color: #666;
  line-height: 1.6;
}

.eval-empty {
  text-align: center;
  padding: 30rpx 0;
  color: #ccc;
  font-size: 28rpx;
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
