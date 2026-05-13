<template>
  <view class="mine-container" :style="{height: `${windowHeight}px`}">
    <!--顶部个人信息栏-->
    <view class="header-section">
      <view class="flex padding justify-between">
        <view class="flex align-center">
          <view v-if="!avatar" class="cu-avatar xl round bg-white">
            <view class="iconfont icon-people text-gray icon"></view>
          </view>
          <image v-if="avatar" @click="handleToAvatar" :src="avatar" class="cu-avatar xl round" mode="widthFix">
          </image>
          <view v-if="!name" @click="handleToLogin" class="login-tip">
            点击登录
          </view>
          <view v-if="name" @click="handleToInfo" class="user-info">
            <view class="u_title">
              用户名：{{ name }}
            </view>
            <view v-if="studentInfo.schoolName" class="u_school">
              {{ studentInfo.schoolName }} · {{ studentInfo.majorName || '' }}
            </view>
          </view>
        </view>
        <view @click="handleToInfo" class="flex align-center">
          <text>个人信息</text>
          <view class="iconfont icon-right"></view>
        </view>
      </view>
    </view>

    <view class="content-section">
      <!-- 学生信息卡片 -->
      <view v-if="name && studentInfo.schoolName" class="student-card" @click="handleToInfo">
        <view class="card-row">
          <view class="card-item">
            <text class="card-label">学校</text>
            <text class="card-value">{{ studentInfo.schoolName }}</text>
          </view>
          <view class="card-item">
            <text class="card-label">专业</text>
            <text class="card-value">{{ studentInfo.majorName || '未填写' }}</text>
          </view>
        </view>
        <view class="card-row">
          <view class="card-item">
            <text class="card-label">学号</text>
            <text class="card-value">{{ studentInfo.studentNo || '未填写' }}</text>
          </view>
          <view class="card-item">
            <text class="card-label">年级</text>
            <text class="card-value">{{ studentInfo.gradeYear ? studentInfo.gradeYear + '级' : '未填写' }}</text>
          </view>
        </view>
      </view>

      <view class="mine-actions grid col-4 text-center">
        <view class="action-item" @click="handleToApplication">
          <view class="iconfont icon-community text-blue icon"></view>
          <text class="text">我的投递</text>
        </view>
        <view class="action-item" @click="handleToCollection">
          <view class="iconfont icon-aixin text-red icon"></view>
          <text class="text">我的收藏</text>
        </view>
        <view class="action-item" @click="handleToResume">
          <view class="iconfont icon-edit text-green icon"></view>
          <text class="text">我的简历</text>
        </view>
        <view class="action-item" @click="handleToEvaluation">
          <view class="iconfont icon-dianzan text-orange icon"></view>
          <text class="text">互评记录</text>
        </view>
      </view>

      <view class="menu-list">
        <view class="list-cell list-cell-arrow" @click="handleToEditInfo">
          <view class="menu-item-box">
            <view class="iconfont icon-user menu-icon"></view>
            <view>编辑资料</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleToPwd">
          <view class="menu-item-box">
            <view class="iconfont icon-password menu-icon"></view>
            <view>修改密码</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleAbout">
          <view class="menu-item-box">
            <view class="iconfont icon-aixin menu-icon"></view>
            <view>关于我们</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleToSetting">
          <view class="menu-item-box">
            <view class="iconfont icon-setting menu-icon"></view>
            <view>应用设置</view>
          </view>
        </view>
      </view>

    </view>
  </view>
</template>

<script>
  import { getStudentInfo } from '@/api/student/info'

  export default {
    data() {
      return {
        name: this.$store.state.user.name,
        studentInfo: {}
      }
    },
    computed: {
      avatar() {
        return this.$store.state.user.avatar
      },
      windowHeight() {
        return uni.getSystemInfoSync().windowHeight - 50
      }
    },
    onShow() {
      if (this.name) {
        this.loadStudentInfo()
      }
    },
    methods: {
      loadStudentInfo() {
        getStudentInfo().then(res => {
          if (res.data) {
            this.studentInfo = res.data
          }
        })
      },
      handleToInfo() {
        this.$tab.navigateTo('/pages/mine/info/index')
      },
      handleToEditInfo() {
        this.$tab.navigateTo('/pages/mine/info/edit')
      },
      handleToSetting() {
        this.$tab.navigateTo('/pages/mine/setting/index')
      },
      handleToLogin() {
        this.$tab.reLaunch('/pages/login')
      },
      handleToAvatar() {
        this.$tab.navigateTo('/pages/mine/avatar/index')
      },
      handleToPwd() {
        this.$tab.navigateTo('/pages/mine/pwd/index')
      },
      handleAbout() {
        this.$tab.navigateTo('/pages/mine/about/index')
      },
      handleToApplication() {
        this.$tab.navigateTo('/pages/student/application/list')
      },
      handleToCollection() {
        this.$tab.navigateTo('/pages/student/collection/list')
      },
      handleToResume() {
        this.$tab.navigateTo('/pages/student/resume/index')
      },
      handleToEvaluation() {
        this.$tab.navigateTo('/pages/student/evaluation/list')
      }
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #f5f6f7;
  }

  .mine-container {
    width: 100%;
    height: 100%;


    .header-section {
      padding: 15px 15px 45px 15px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;

      .login-tip {
        font-size: 18px;
        margin-left: 10px;
      }

      .cu-avatar {
        border: 2px solid #eaeaea;

        .icon {
          font-size: 40px;
        }
      }

      .user-info {
        margin-left: 15px;

        .u_title {
          font-size: 18px;
          line-height: 30px;
        }

        .u_school {
          font-size: 13px;
          opacity: 0.85;
          margin-top: 2px;
        }
      }
    }

    .content-section {
      position: relative;
      top: -50px;

      .student-card {
        margin: 0 15px 10px;
        padding: 20px;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

        .card-row {
          display: flex;
          margin-bottom: 12px;

          &:last-child {
            margin-bottom: 0;
          }
        }

        .card-item {
          flex: 1;

          .card-label {
            font-size: 12px;
            color: #999;
            display: block;
            margin-bottom: 4px;
          }

          .card-value {
            font-size: 14px;
            color: #333;
            font-weight: 500;
          }
        }
      }

      .mine-actions {
        margin: 15px 15px;
        padding: 20px 0px;
        border-radius: 8px;
        background-color: white;

        .action-item {
          .icon {
            font-size: 28px;
          }

          .text {
            display: block;
            font-size: 13px;
            margin: 8px 0px;
          }
        }
      }
    }
  }
</style>
