<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>高校校园兼职信息平台</h2>
        <p>
          本系统是面向高校校园的兼职信息发布与管理平台，为学生、企业和管理员提供便捷的兼职信息交互服务。学生可以浏览和申请兼职岗位，企业可以发布和管理招聘信息，管理员负责平台的整体运营和监管。
        </p>
        <p>
          <b>系统角色:</b>
        </p>
        <ul>
          <li><el-tag>学生</el-tag> 浏览兼职信息、投递简历、管理个人信息</li>
          <li><el-tag type="success">企业</el-tag> 发布兼职岗位、管理招聘信息、查看应聘者</li>
          <li><el-tag type="warning">管理员</el-tag> 平台运营管理、用户管理、数据统计</li>
        </ul>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>项目信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 项目类型：毕业设计
            </p>
            <p>
              <i class="el-icon-user-solid"></i> 开发框架：RuoYi v3.9.2
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 数据库：MySQL
            </p>
            <p>
              <i class="el-icon-money"></i> 缓存：Redis
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.9.2"
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

