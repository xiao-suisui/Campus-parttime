<template>
  <view class="resume-container">
    <view class="form-card">
      <view class="card-title">我的简历</view>

      <uni-forms ref="form" :model="form" :rules="rules">
        <uni-forms-item label="期望岗位" name="expectedPosition">
          <uni-easyinput v-model="form.expectedPosition" placeholder="如：前端开发、家教" />
        </uni-forms-item>

        <uni-forms-item label="期望薪资" name="expectedSalary">
          <view class="salary-range">
            <uni-number-box v-model="form.expectedSalaryMin" :min="0" :max="99999" :step="100" />
            <text class="salary-sep">~</text>
            <uni-number-box v-model="form.expectedSalaryMax" :min="0" :max="99999" :step="100" />
            <text class="salary-unit">元/月</text>
          </view>
        </uni-forms-item>

        <uni-forms-item label="教育经历" name="educationExperience">
          <uni-easyinput v-model="form.educationExperience" type="textarea" placeholder="请填写教育经历" maxlength="1000" />
        </uni-forms-item>

        <uni-forms-item label="兼职经历" name="partTimeExperience">
          <uni-easyinput v-model="form.partTimeExperience" type="textarea" placeholder="请填写兼职经历" maxlength="1000" />
        </uni-forms-item>

        <uni-forms-item label="技能标签" name="skillTags">
          <uni-easyinput v-model="form.skillTags" placeholder="多个标签用逗号分隔，如：Python,PS,英语" />
        </uni-forms-item>

        <uni-forms-item label="自我介绍" name="selfIntro">
          <uni-easyinput v-model="form.selfIntro" type="textarea" placeholder="请填写自我介绍" maxlength="500" />
        </uni-forms-item>
      </uni-forms>

      <button class="btn-save" @click="handleSave">保存简历</button>
    </view>
  </view>
</template>

<script>
import { getStudentResume, addStudentResume, updateStudentResume } from '@/api/student/resume'

export default {
  data() {
    return {
      form: {
        resumeId: '',
        expectedPosition: '',
        expectedSalaryMin: 0,
        expectedSalaryMax: 0,
        educationExperience: '',
        partTimeExperience: '',
        skillTags: '',
        selfIntro: ''
      },
      rules: {
        expectedPosition: { rules: [{ required: true, errorMessage: '请输入期望岗位' }] }
      }
    }
  },
  onLoad() {
    this.loadResume()
  },
  methods: {
    loadResume() {
      getStudentResume().then(res => {
        if (res.data) {
          Object.assign(this.form, res.data)
        }
      })
    },
    handleSave() {
      this.$refs.form.validate().then(() => {
        const action = this.form.resumeId ? updateStudentResume : addStudentResume
        action(this.form).then(() => {
          uni.showToast({ title: '保存成功', icon: 'success' })
          this.loadResume()
        })
      }).catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
.resume-container {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding: 20rpx;
}

.form-card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
}

.card-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 30rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #eee;
}

.salary-range {
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.salary-sep {
  font-size: 28rpx;
  color: #999;
}

.salary-unit {
  font-size: 26rpx;
  color: #666;
}

.btn-save {
  margin-top: 40rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 44rpx;
  height: 88rpx;
  line-height: 88rpx;
  font-size: 32rpx;
}
</style>
