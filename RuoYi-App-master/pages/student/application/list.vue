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
          <text class="app-post-name">{{ item.postName || '岗位#' + item.postId }}</text>
          <uni-tag :text="getStatusText(item.applicationStatus)" :type="getStatusType(item.applicationStatus)" size="small" />
        </view>
        <view class="app-info">
          <text class="app-enterprise">{{ item.enterpriseName || '' }}</text>
          <text class="app-time">{{ item.applyTime }}</text>
        </view>
        <!-- 已完成状态显示评价按钮 -->
        <view v-if="item.applicationStatus === '2'" class="app-action">
          <button class="btn-eval" @click="openEvalDialog(item)">去评价</button>
        </view>
      </view>

      <view v-if="appList.length === 0 && !loading" class="empty-state">
        <text>暂无投递记录</text>
      </view>
    </view>

    <!-- 评价弹窗 -->
    <uni-popup ref="evalPopup" type="center">
      <view class="eval-dialog">
        <view class="dialog-title">评价企业</view>
        <view class="dialog-post">{{ evalForm.postName }}</view>
        <view class="dialog-enterprise">{{ evalForm.enterpriseName }}</view>
        <view class="dialog-rate">
          <text class="rate-label">评分：</text>
          <uni-rate v-model="evalForm.score" size="24" />
        </view>
        <view class="dialog-input">
          <textarea v-model="evalForm.content" placeholder="请输入评价内容..." maxlength="500" />
        </view>
        <view class="dialog-actions">
          <button class="btn-cancel" @click="closeEvalDialog">取消</button>
          <button class="btn-submit" @click="submitEval">提交</button>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script>
import { listApplication } from '@/api/student/application'
import { addEvaluation } from '@/api/student/evaluation'

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
      ],
      evalForm: {
        applicationId: '',
        postName: '',
        enterpriseName: '',
        score: 5,
        content: ''
      }
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
        query.applicationStatus = this.currentStatus
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
    },
    openEvalDialog(item) {
      this.evalForm = {
        applicationId: item.applicationId,
        postName: item.postName || '岗位#' + item.postId,
        enterpriseName: item.enterpriseName || '企业',
        score: 5,
        content: ''
      }
      this.$refs.evalPopup.open()
    },
    closeEvalDialog() {
      this.$refs.evalPopup.close()
    },
    submitEval() {
      if (!this.evalForm.content.trim()) {
        uni.showToast({ title: '请输入评价内容', icon: 'none' })
        return
      }
      addEvaluation({
        applicationId: this.evalForm.applicationId,
        score: this.evalForm.score,
        content: this.evalForm.content
      }).then(() => {
        uni.showToast({ title: '评价成功', icon: 'success' })
        this.closeEvalDialog()
      }).catch(err => {
        // 后端防重复：该投递记录已完成学生评价
        uni.showToast({ title: err.msg || '评价失败', icon: 'none' })
      })
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

.app-action {
  margin-top: 20rpx;
  padding-top: 20rpx;
  border-top: 1rpx solid #f0f0f0;
}

.btn-eval {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 32rpx;
  height: 64rpx;
  line-height: 64rpx;
  font-size: 28rpx;
}

.eval-dialog {
  width: 600rpx;
  background-color: #fff;
  border-radius: 24rpx;
  padding: 40rpx;
}

.dialog-title {
  font-size: 32rpx;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20rpx;
}

.dialog-post {
  font-size: 28rpx;
  color: #333;
  text-align: center;
  margin-bottom: 8rpx;
}

.dialog-enterprise {
  font-size: 26rpx;
  color: #666;
  text-align: center;
  margin-bottom: 30rpx;
}

.dialog-rate {
  display: flex;
  align-items: center;
  margin-bottom: 30rpx;
}

.rate-label {
  font-size: 28rpx;
  color: #333;
  margin-right: 16rpx;
}

.dialog-input {
  margin-bottom: 30rpx;
}

.dialog-input textarea {
  width: 100%;
  height: 200rpx;
  border: 1rpx solid #e0e0e0;
  border-radius: 12rpx;
  padding: 20rpx;
  font-size: 28rpx;
  box-sizing: border-box;
}

.dialog-actions {
  display: flex;
  gap: 20rpx;
}

.btn-cancel {
  flex: 1;
  background-color: #f5f5f5;
  color: #666;
  border: none;
  border-radius: 32rpx;
  height: 72rpx;
  line-height: 72rpx;
  font-size: 28rpx;
}

.btn-submit {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 32rpx;
  height: 72rpx;
  line-height: 72rpx;
  font-size: 28rpx;
}

.empty-state {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>
