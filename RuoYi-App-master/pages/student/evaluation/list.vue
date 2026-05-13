<template>
  <view class="evaluation-container">
    <!-- Tab 切换 -->
    <view class="tab-bar">
      <uni-segmented-control :current="currentTab" :values="tabs" @clickItem="onClickTab" style-type="button" active-color="#667eea" />
    </view>

    <!-- 我发出的评价 -->
    <view v-if="currentTab === 0" class="eval-list">
      <view v-for="item in evalList" :key="item.evaluationId" class="eval-card">
        <view class="eval-header">
          <text class="eval-post">{{ item.postName || '岗位' }}</text>
          <uni-tag :text="item.status === '0' ? '待评价' : '已评价'" :type="item.status === '0' ? 'warning' : 'success'" size="small" />
        </view>
        <view class="eval-enterprise">{{ item.enterpriseName || '' }}</view>

        <!-- 已评价显示评价内容 -->
        <view v-if="item.status !== '0'" class="eval-content">
          <uni-rate :value="item.score" disabled size="18" />
          <text class="eval-text">{{ item.content }}</text>
        </view>

        <!-- 待评价显示评价入口 -->
        <view v-else class="eval-action">
          <button class="btn-eval" @click="openEvalDialog(item)">去评价</button>
        </view>
      </view>

      <view v-if="evalList.length === 0 && !loading" class="empty-state">
        <text>暂无评价记录</text>
      </view>
    </view>

    <!-- 收到的评价 -->
    <view v-if="currentTab === 1" class="eval-list">
      <view v-for="item in aboutMeList" :key="item.evaluationId" class="eval-card">
        <view class="eval-header">
          <text class="eval-post">{{ item.postName || '岗位#' + item.postId }}</text>
          <uni-tag text="企业评价" type="primary" size="small" />
        </view>
        <view class="eval-enterprise">{{ item.enterpriseName || '企业' }}</view>
        <view class="eval-content">
          <uni-rate :value="item.score" disabled size="18" />
          <text class="eval-text">{{ item.content }}</text>
          <text class="eval-time">{{ item.evaluationTime }}</text>
        </view>
      </view>

      <view v-if="aboutMeList.length === 0 && !aboutMeLoading" class="empty-state">
        <text>暂无收到的评价</text>
      </view>
    </view>

    <!-- 评价弹窗 -->
    <uni-popup ref="evalPopup" type="center">
      <view class="eval-dialog">
        <view class="dialog-title">评价岗位</view>
        <view class="dialog-post">{{ evalForm.postName }}</view>
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
import { listEvaluation, addEvaluation, listEvaluationsAboutMe } from '@/api/student/evaluation'

export default {
  data() {
    return {
      currentTab: 0,
      tabs: ['我发出的', '收到的'],
      loading: false,
      aboutMeLoading: false,
      evalList: [],
      aboutMeList: [],
      evalForm: {
        applicationId: '',
        postName: '',
        score: 5,
        content: ''
      }
    }
  },
  onLoad() {
    this.loadList()
  },
  onPullDownRefresh() {
    if (this.currentTab === 0) {
      this.loadList()
    } else {
      this.loadAboutMeList()
    }
  },
  methods: {
    onClickTab(e) {
      this.currentTab = e.currentIndex
      if (this.currentTab === 1 && this.aboutMeList.length === 0) {
        this.loadAboutMeList()
      }
    },
    loadList() {
      this.loading = true
      listEvaluation().then(res => {
        this.evalList = res.rows || []
      }).finally(() => {
        this.loading = false
        uni.stopPullDownRefresh()
      })
    },
    loadAboutMeList() {
      this.aboutMeLoading = true
      listEvaluationsAboutMe().then(res => {
        this.aboutMeList = res.rows || []
      }).finally(() => {
        this.aboutMeLoading = false
        uni.stopPullDownRefresh()
      })
    },
    openEvalDialog(item) {
      this.evalForm = {
        applicationId: item.applicationId,
        postName: item.postName || '岗位',
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
        this.loadList()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.evaluation-container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.tab-bar {
  padding: 20rpx 30rpx;
  background-color: #fff;
}

.eval-list {
  padding: 20rpx;
}

.eval-card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.eval-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12rpx;
}

.eval-post {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
}

.eval-enterprise {
  font-size: 26rpx;
  color: #666;
  margin-bottom: 16rpx;
}

.eval-content {
  margin-top: 16rpx;
  padding-top: 16rpx;
  border-top: 1rpx solid #f0f0f0;
}

.eval-text {
  font-size: 26rpx;
  color: #666;
  margin-top: 12rpx;
  line-height: 1.6;
}

.eval-time {
  font-size: 24rpx;
  color: #999;
  margin-top: 12rpx;
  display: block;
}

.eval-action {
  margin-top: 16rpx;
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
