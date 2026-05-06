<template>
  <div class="app-container">
    <!-- 推荐岗位区域 -->
    <el-card v-if="recommendList.length > 0" class="mb20">
      <div slot="header">
        <span>为你推荐</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="loadRecommend">换一批</el-button>
      </div>
      <el-row :gutter="16">
        <el-col :span="6" v-for="item in recommendList" :key="item.postId" style="margin-bottom: 12px;">
          <el-card shadow="hover" class="recommend-card" @click.native="handleView(item)">
            <div class="recommend-title">{{ item.postName }}</div>
            <div class="recommend-salary">{{ item.salaryMin }}-{{ item.salaryMax }}元</div>
            <div class="recommend-addr">{{ item.workAddress || '不限' }}</div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位名称" prop="postName">
        <el-input v-model="queryParams.postName" placeholder="请输入岗位名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="postList">
      <el-table-column label="岗位ID" prop="postId" width="90" />
      <el-table-column label="岗位名称" prop="postName" min-width="140" />
      <el-table-column label="工作地点" prop="workAddress" min-width="140" />
      <el-table-column label="薪资范围" min-width="180">
        <template slot-scope="scope">
          {{ scope.row.salaryMin }} - {{ scope.row.salaryMax }} / {{ scope.row.salaryUnit }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['student:job:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button type="text" size="mini" v-hasPermi="['student:collection:add']" @click="handleCollect(scope.row)">收藏</el-button>
          <el-button type="text" size="mini" v-hasPermi="['student:application:add']" @click="handleOpenApply(scope.row)">投递</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog title="投递岗位" :visible.sync="applyOpen" width="420px" append-to-body>
      <el-form ref="applyForm" :model="applyForm" :rules="applyRules" label-width="90px">
        <el-form-item label="岗位ID">
          <el-input v-model="applyForm.postId" disabled />
        </el-form-item>
        <el-form-item label="简历ID" prop="resumeId">
          <el-input-number v-model="applyForm.resumeId" :min="1" controls-position="right" style="width: 100%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitApply">确 定</el-button>
        <el-button @click="applyOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStudentPost, getStudentPost } from '@/api/student/post'
import { addStudentCollection } from '@/api/student/collection'
import { addStudentApplication } from '@/api/student/application'
import { getRecommendPosts } from '@/api/student/recommend'

export default {
  name: 'StudentPost',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      postList: [],
      recommendList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postName: undefined
      },
      applyOpen: false,
      applyForm: {
        postId: undefined,
        resumeId: undefined
      },
      applyRules: {
        resumeId: [{ required: true, message: '简历ID不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
    this.loadRecommend()
  },
  methods: {
    loadRecommend() {
      getRecommendPosts(8).then((response) => {
        this.recommendList = response.data || []
      }).catch(() => {})
    },
    getList() {
      this.loading = true
      listStudentPost(this.queryParams).then((response) => {
        this.postList = response.rows
        this.total = response.total
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleView(row) {
      getStudentPost(row.postId).then((response) => {
        this.$alert(JSON.stringify(response.data, null, 2), '岗位详情', { confirmButtonText: '关闭' })
      })
    },
    handleCollect(row) {
      addStudentCollection({ postId: row.postId }).then(() => {
        this.$modal.msgSuccess('收藏操作完成')
      })
    },
    handleOpenApply(row) {
      this.applyForm = { postId: row.postId, resumeId: undefined }
      this.applyOpen = true
    },
    submitApply() {
      this.$refs.applyForm.validate((valid) => {
        if (!valid) {
          return
        }
        addStudentApplication(this.applyForm).then(() => {
          this.$modal.msgSuccess('投递成功')
          this.applyOpen = false
        })
      })
    }
  }
}
</script>

<style scoped>
.mb20 {
  margin-bottom: 20px;
}
.recommend-card {
  cursor: pointer;
  text-align: center;
  padding: 10px;
}
.recommend-card:hover {
  border-color: #409eff;
}
.recommend-title {
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.recommend-salary {
  color: #f56c6c;
  font-size: 13px;
  margin-bottom: 4px;
}
.recommend-addr {
  color: #909399;
  font-size: 12px;
}
</style>
