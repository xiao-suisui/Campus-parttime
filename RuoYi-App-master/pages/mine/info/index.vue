<template>
  <view class="container">
    <uni-list>
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'person-filled'}" title="昵称" :rightText="user.nickName" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'phone-filled'}" title="手机号码" :rightText="user.phonenumber" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'email-filled'}" title="邮箱" :rightText="user.email" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'auth-filled'}" title="岗位" :rightText="postGroup" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'staff-filled'}" title="角色" :rightText="roleGroup" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'calendar-filled'}" title="创建日期" :rightText="user.createTime" />
    </uni-list>

    <view class="section-title">学生信息</view>
    <uni-list>
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'person-filled'}" title="姓名" :rightText="student.realName || '未填写'" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'compose'}" title="学号" :rightText="student.studentNo || '未填写'" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'home-filled'}" title="学校" :rightText="student.schoolName || '未填写'" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'list'}" title="专业" :rightText="student.majorName || '未填写'" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'calendar-filled'}" title="年级" :rightText="student.gradeYear ? student.gradeYear + '级' : '未填写'" />
      <uni-list-item showExtraIcon="true" :extraIcon="{type: 'phone-filled'}" title="手机号" :rightText="student.phone || '未填写'" />
    </uni-list>
  </view>
</template>

<script>
  import { getUserProfile } from "@/api/system/user"
  import { getStudentInfo } from "@/api/student/info"

  export default {
    data() {
      return {
        user: {},
        student: {},
        roleGroup: "",
        postGroup: ""
      }
    },
    onLoad() {
      this.getUser()
      this.getStudent()
    },
    methods: {
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data
          this.roleGroup = response.roleGroup
          this.postGroup = response.postGroup
        })
      },
      getStudent() {
        getStudentInfo().then(res => {
          if (res.data) {
            this.student = res.data
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #f5f5f5;
  }

  .section-title {
    font-size: 28rpx;
    color: #999;
    padding: 30rpx 30rpx 10rpx;
  }
</style>
