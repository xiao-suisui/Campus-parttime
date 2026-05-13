<template>
  <view class="normal-login-container">
    <view class="logo-content align-center justify-center flex">
      <text class="title">学生注册</text>
    </view>
    <view class="login-form-content">
      <!-- 账号信息 -->
      <view class="section-title">账号信息</view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-user icon"></view>
        <input v-model="registerForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="registerForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-password icon"></view>
        <input v-model="registerForm.confirmPassword" type="password" class="input" placeholder="请输入重复密码" maxlength="20" />
      </view>

      <!-- 学生信息 -->
      <view class="section-title">学生信息</view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-user icon"></view>
        <input v-model="registerForm.realName" class="input" type="text" placeholder="请输入真实姓名" maxlength="20" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-edit icon"></view>
        <input v-model="registerForm.studentNo" class="input" type="text" placeholder="请输入学号" maxlength="20" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-home icon"></view>
        <input v-model="registerForm.school" class="input" type="text" placeholder="请输入学校名称" maxlength="50" />
      </view>
      <view class="input-item flex align-center">
        <view class="iconfont icon-edit icon"></view>
        <input v-model="registerForm.major" class="input" type="text" placeholder="请输入专业名称" maxlength="50" />
      </view>

      <view class="input-item flex align-center" style="width: 60%;margin: 0px;" v-if="captchaEnabled">
        <view class="iconfont icon-code icon"></view>
        <input v-model="registerForm.code" type="number" class="input" placeholder="请输入验证码" maxlength="4" />
        <view class="login-code">
          <image :src="codeUrl" @click="getCode" class="login-code-img"></image>
        </view>
      </view>
      <view class="action-btn">
        <button @click="handleRegister()" class="register-btn cu-btn block bg-blue lg round">注册</button>
      </view>
    </view>
    <view class="xieyi text-center">
      <text @click="handleUserLogin" class="text-blue">使用已有账号登录</text>
    </view>
    <view class="xieyi text-center" style="margin-top: 10px;">
      <text class="text-grey1" style="font-size: 12px;">企业用户请前往PC端注册</text>
    </view>
  </view>
</template>

<script>
  import { getCodeImg, register } from '@/api/login'

  export default {
    data() {
      return {
        codeUrl: "",
        captchaEnabled: true,
        globalConfig: getApp().globalData.config,
        registerForm: {
          username: "",
          password: "",
          confirmPassword: "",
          code: "",
          uuid: "",
          userType: "student",
          realName: "",
          studentNo: "",
          school: "",
          major: ""
        }
      }
    },
    created() {
      this.getCode()
    },
    methods: {
      // 用户登录
      handleUserLogin() {
        this.$tab.navigateTo(`/pages/login`)
      },
      // 获取图形验证码
      getCode() {
        getCodeImg().then(res => {
          this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
          if (this.captchaEnabled) {
            this.codeUrl = 'data:image/gif;base64,' + res.img
            this.registerForm.uuid = res.uuid
          }
        })
      },
      // 注册方法
      async handleRegister() {
        if (this.registerForm.username === "") {
          this.$modal.msgError("请输入您的账号")
        } else if (this.registerForm.password === "") {
          this.$modal.msgError("请输入您的密码")
        } else if (this.registerForm.confirmPassword === "") {
          this.$modal.msgError("请再次输入您的密码")
        } else if (this.registerForm.password !== this.registerForm.confirmPassword) {
          this.$modal.msgError("两次输入的密码不一致")
        } else if (this.registerForm.code === "" && this.captchaEnabled) {
          this.$modal.msgError("请输入验证码")
        } else {
          this.$modal.loading("注册中，请耐心等待...")
          this.register()
        }
      },
      // 用户注册
      async register() {
        register(this.registerForm).then(res => {
          this.$modal.closeLoading()
          uni.showModal({
          	title: "系统提示",
          	content: "恭喜你，您的账号 " + this.registerForm.username + " 注册成功！",
          	success: function (res) {
          		if (res.confirm) {
                uni.redirectTo({ url: `/pages/login` });
          		}
          	}
          })
        }).catch(() => {
          if (this.captchaEnabled) {
            this.getCode()
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #ffffff;
  }

  .normal-login-container {
    width: 100%;

    .logo-content {
      width: 100%;
      font-size: 21px;
      text-align: center;
      padding-top: 10%;

      image {
        border-radius: 4px;
      }

      .title {
        margin-left: 10px;
      }
    }

    .login-form-content {
      text-align: center;
      margin: 20px auto;
      margin-top: 5%;
      width: 80%;

      .section-title {
        font-size: 16px;
        font-weight: bold;
        color: #333;
        text-align: left;
        margin: 20px 0 10px 10px;
      }

      .input-item {
        margin: 15px auto;
        background-color: #f5f6f7;
        height: 45px;
        border-radius: 20px;

        .icon {
          font-size: 38rpx;
          margin-left: 10px;
          color: #999;
        }

        .input {
          width: 100%;
          font-size: 14px;
          line-height: 20px;
          text-align: left;
          padding-left: 15px;
        }

      }

      .register-btn {
        margin-top: 30px;
        height: 45px;
      }

      .xieyi {
        color: #333;
        margin-top: 20px;
      }

      .login-code {
        height: 38px;
        float: right;

        .login-code-img {
          height: 38px;
          position: absolute;
          margin-left: 10px;
          width: 200rpx;
        }
      }
    }
  }

</style>
