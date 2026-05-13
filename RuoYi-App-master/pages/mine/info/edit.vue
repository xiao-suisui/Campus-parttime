<template>
  <view class="container">
    <view class="example">
      <view class="section-title">账号信息</view>
      <uni-forms ref="userForm" :model="user" labelWidth="80px">
        <uni-forms-item label="用户昵称" name="nickName">
          <uni-easyinput v-model="user.nickName" placeholder="请输入昵称" />
        </uni-forms-item>
        <uni-forms-item label="手机号码" name="phonenumber">
          <uni-easyinput v-model="user.phonenumber" placeholder="请输入手机号码" />
        </uni-forms-item>
        <uni-forms-item label="邮箱" name="email">
          <uni-easyinput v-model="user.email" placeholder="请输入邮箱" />
        </uni-forms-item>
        <uni-forms-item label="性别" name="sex" required>
          <uni-data-checkbox v-model="user.sex" :localdata="sexs" />
        </uni-forms-item>
      </uni-forms>

      <view class="section-title">学生信息</view>
      <uni-forms ref="studentForm" :model="student" labelWidth="80px">
        <uni-forms-item label="姓名" name="realName">
          <uni-easyinput v-model="student.realName" placeholder="请输入真实姓名" />
        </uni-forms-item>
        <uni-forms-item label="学号" name="studentNo">
          <uni-easyinput v-model="student.studentNo" placeholder="请输入学号" />
        </uni-forms-item>
        <uni-forms-item label="学校" name="schoolName">
          <uni-easyinput v-model="student.schoolName" placeholder="请输入学校名称" />
        </uni-forms-item>
        <uni-forms-item label="专业" name="majorName">
          <uni-easyinput v-model="student.majorName" placeholder="请输入专业名称" />
        </uni-forms-item>
        <uni-forms-item label="年级" name="gradeYear">
          <uni-number-box v-model="student.gradeYear" :min="2020" :max="2030" />
        </uni-forms-item>
        <uni-forms-item label="联系电话" name="phone">
          <uni-easyinput v-model="student.phone" placeholder="请输入联系电话" />
        </uni-forms-item>
      </uni-forms>

      <button type="primary" @click="submit">提交</button>
    </view>
  </view>
</template>

<script>
  import { getUserProfile, updateUserProfile } from "@/api/system/user"
  import { getStudentInfo, addStudentInfo, updateStudentInfo } from "@/api/student/info"

  export default {
    data() {
      return {
        user: {
          nickName: "",
          phonenumber: "",
          email: "",
          sex: ""
        },
        student: {
          studentId: "",
          studentNo: "",
          realName: "",
          gender: "0",
          schoolName: "",
          majorName: "",
          gradeYear: 2024,
          phone: ""
        },
        sexs: [{
          text: '男',
          value: "0"
        }, {
          text: '女',
          value: "1"
        }],
        rules: {
          nickName: {
            rules: [{
              required: true,
              errorMessage: '用户昵称不能为空'
            }]
          },
          phonenumber: {
            rules: [{
              required: true,
              errorMessage: '手机号码不能为空'
            }, {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              errorMessage: '请输入正确的手机号码'
            }]
          },
          email: {
            rules: [{
              required: true,
              errorMessage: '邮箱地址不能为空'
            }, {
              format: 'email',
              errorMessage: '请输入正确的邮箱地址'
            }]
          }
        }
      }
    },
    onLoad() {
      this.getUser()
      this.getStudent()
    },
    onReady() {
      this.$refs.userForm.setRules(this.rules)
    },
    methods: {
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data
        })
      },
      getStudent() {
        getStudentInfo().then(res => {
          if (res.data) {
            Object.assign(this.student, res.data)
          }
        })
      },
      submit() {
        this.$refs.userForm.validate().then(() => {
          const tasks = [updateUserProfile(this.user)]
          if (this.student.studentId) {
            tasks.push(updateStudentInfo(this.student))
          } else if (this.student.realName || this.student.schoolName) {
            tasks.push(addStudentInfo(this.student))
          }
          Promise.all(tasks).then(() => {
            this.$modal.msgSuccess("修改成功")
          })
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #f5f5f5;
  }

  .example {
    padding: 15px;
    background-color: #fff;
  }

  .section-title {
    font-size: 28rpx;
    font-weight: bold;
    color: #333;
    padding: 20rpx 0;
    margin-bottom: 10rpx;
    border-bottom: 1rpx solid #eee;
  }

  .button {
    display: flex;
    align-items: center;
    height: 35px;
    line-height: 35px;
    margin-left: 10px;
  }
</style>
