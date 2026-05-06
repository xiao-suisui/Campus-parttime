<template>
  <view class="info-container">
    <view class="form-card">
      <view class="card-title">个人信息</view>

      <uni-forms ref="form" :model="form" :rules="rules">
        <uni-forms-item label="学号" name="studentNo">
          <uni-easyinput v-model="form.studentNo" placeholder="请输入学号" />
        </uni-forms-item>

        <uni-forms-item label="姓名" name="realName">
          <uni-easyinput v-model="form.realName" placeholder="请输入姓名" />
        </uni-forms-item>

        <uni-forms-item label="性别" name="gender">
          <uni-data-checkbox v-model="form.gender" :localdata="genderOptions" />
        </uni-forms-item>

        <uni-forms-item label="学校" name="schoolName">
          <uni-easyinput v-model="form.schoolName" placeholder="请输入学校名称" />
        </uni-forms-item>

        <uni-forms-item label="专业" name="majorName">
          <uni-easyinput v-model="form.majorName" placeholder="请输入专业名称" />
        </uni-forms-item>

        <uni-forms-item label="年级" name="gradeYear">
          <uni-number-box v-model="form.gradeYear" :min="2020" :max="2030" />
        </uni-forms-item>

        <uni-forms-item label="手机号" name="phone">
          <uni-easyinput v-model="form.phone" placeholder="请输入手机号" />
        </uni-forms-item>
      </uni-forms>

      <button class="btn-save" @click="handleSave">保存</button>
    </view>
  </view>
</template>

<script>
import { getStudentInfo, addStudentInfo, updateStudentInfo } from '@/api/student/info'

export default {
  data() {
    return {
      form: {
        studentId: '',
        studentNo: '',
        realName: '',
        gender: '0',
        schoolName: '',
        majorName: '',
        gradeYear: 2024,
        phone: ''
      },
      genderOptions: [
        { value: '0', text: '男' },
        { value: '1', text: '女' }
      ],
      rules: {
        realName: { rules: [{ required: true, errorMessage: '请输入姓名' }] }
      }
    }
  },
  onLoad() {
    this.loadInfo()
  },
  methods: {
    loadInfo() {
      getStudentInfo().then(res => {
        if (res.data) {
          Object.assign(this.form, res.data)
        }
      })
    },
    handleSave() {
      this.$refs.form.validate().then(() => {
        const action = this.form.studentId ? updateStudentInfo : addStudentInfo
        action(this.form).then(() => {
          uni.showToast({ title: '保存成功', icon: 'success' })
          this.loadInfo()
        })
      }).catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
.info-container {
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
