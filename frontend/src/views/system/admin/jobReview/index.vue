<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="岗位名称" prop="postName">
        <el-input v-model="queryParams.postName" placeholder="请输入岗位名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="岗位状态" prop="postStatus">
        <el-select v-model="queryParams.postStatus" placeholder="岗位状态" clearable>
          <el-option v-for="dict in dict.type.job_post_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="jobList">
      <el-table-column label="岗位ID" prop="postId" width="80" />
      <el-table-column label="岗位名称" prop="postName" min-width="120" />
      <el-table-column label="企业名称" prop="enterpriseName" min-width="140" />
      <el-table-column label="薪资" min-width="120">
        <template slot-scope="scope">
          {{ scope.row.salaryMin }} - {{ scope.row.salaryMax }} 元
        </template>
      </el-table-column>
      <el-table-column label="岗位状态" prop="postStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.job_post_status" :value="scope.row.postStatus" />
        </template>
      </el-table-column>
      <el-table-column label="投递量" prop="applyCount" width="80" />
      <el-table-column label="截止时间" prop="deadlineTime" width="160">
        <template slot-scope="scope">
          {{ parseTime(scope.row.deadlineTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['admin:job-review:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button type="text" size="mini" v-hasPermi="['admin:job-review:force-down']"
            v-if="scope.row.postStatus !== '2'" @click="handleForceDown(scope.row)">强制下架</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import {
  listJobReview,
  getJobReview,
  forceDownJobReview
} from '@/api/admin/jobReview'

export default {
  name: 'AdminJobReview',
  dicts: ['job_post_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      jobList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postName: undefined,
        postStatus: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listJobReview(this.queryParams).then((response) => {
        this.jobList = response.rows
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
      getJobReview(row.postId).then((response) => {
        this.$alert(JSON.stringify(response.data, null, 2), '岗位详情', { confirmButtonText: '关闭' })
      })
    },
    handleForceDown(row) {
      this.$modal.confirm('确认强制下架岗位"' + row.postName + '"吗？').then(() => {
        return forceDownJobReview(row.postId)
      }).then(() => {
        this.$modal.msgSuccess('已下架')
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>
