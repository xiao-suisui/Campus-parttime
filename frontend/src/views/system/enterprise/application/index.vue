<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位ID" prop="postId">
        <el-input v-model="queryParams.postId" placeholder="请输入岗位ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="applicationStatus">
        <el-select v-model="queryParams.applicationStatus" placeholder="处理状态" clearable>
          <el-option v-for="dict in dict.type.job_application_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="applicationList">
      <el-table-column label="投递ID" prop="applicationId" width="80" align="center" />
      <el-table-column label="岗位ID" prop="postId" width="80" align="center" />
      <el-table-column label="学生ID" prop="studentId" width="80" align="center" />
      <el-table-column label="状态" prop="applicationStatus" width="100" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.job_application_status" :value="scope.row.applicationStatus" />
        </template>
      </el-table-column>
      <el-table-column label="企业备注" prop="enterpriseRemark" min-width="150" show-overflow-tooltip />
      <el-table-column label="投递时间" prop="applyTime" width="160" align="center">
        <template slot-scope="scope">{{ parseTime(scope.row.applyTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" min-width="200" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['enterprise:application:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button
            v-if="scope.row.applicationStatus == '0'"
            type="text"
            size="mini"
            v-hasPermi="['enterprise:application:hire']"
            @click="handleHire(scope.row)"
          >录用</el-button>
          <el-button
            v-if="scope.row.applicationStatus == '0'"
            type="text"
            size="mini"
            style="color:#F56C6C"
            v-hasPermi="['enterprise:application:reject']"
            @click="handleReject(scope.row)"
          >拒绝</el-button>
          <el-button
            v-if="scope.row.applicationStatus == '1'"
            type="text"
            size="mini"
            v-hasPermi="['enterprise:application:complete']"
            @click="handleComplete(scope.row)"
          >标记完成</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 详情对话框 -->
    <el-dialog title="投递详情" :visible.sync="detailOpen" width="500px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="投递ID">{{ detailForm.applicationId }}</el-descriptions-item>
        <el-descriptions-item label="岗位ID">{{ detailForm.postId }}</el-descriptions-item>
        <el-descriptions-item label="学生ID">{{ detailForm.studentId }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.job_application_status" :value="detailForm.applicationStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="投递时间">{{ parseTime(detailForm.applyTime) }}</el-descriptions-item>
        <el-descriptions-item label="处理时间">{{ parseTime(detailForm.processTime) }}</el-descriptions-item>
        <el-descriptions-item label="完成时间">{{ parseTime(detailForm.completeTime) }}</el-descriptions-item>
        <el-descriptions-item label="企业备注">{{ detailForm.enterpriseRemark }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import {
  listEnterpriseApplication,
  getEnterpriseApplication,
  hireEnterpriseApplication,
  completeEnterpriseApplication,
  rejectEnterpriseApplication
} from '@/api/enterprise/application'

export default {
  name: 'EnterpriseApplication',
  dicts: ['job_application_status'],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      detailOpen: false,
      applicationList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postId: undefined,
        applicationStatus: undefined
      },
      detailForm: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listEnterpriseApplication(this.queryParams).then((response) => {
        this.applicationList = response.rows
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
      getEnterpriseApplication(row.applicationId).then((response) => {
        this.detailForm = response.data
        this.detailOpen = true
      })
    },
    handleHire(row) {
      this.$prompt('请输入企业备注（可选）', '录用确认', { inputValue: row.enterpriseRemark || '', inputType: 'textarea' }).then(({ value }) => {
        return hireEnterpriseApplication(row.applicationId, { enterpriseRemark: value })
      }).then(() => {
        this.$modal.msgSuccess('录用成功')
        this.getList()
      }).catch(() => {})
    },
    handleComplete(row) {
      this.$prompt('请输入企业备注（可选）', '完成确认', { inputValue: row.enterpriseRemark || '', inputType: 'textarea' }).then(({ value }) => {
        return completeEnterpriseApplication(row.applicationId, { enterpriseRemark: value })
      }).then(() => {
        this.$modal.msgSuccess('标记完成成功')
        this.getList()
      }).catch(() => {})
    },
    handleReject(row) {
      this.$prompt('请输入拒绝原因（可选）', '拒绝确认', { inputType: 'textarea' }).then(({ value }) => {
        return rejectEnterpriseApplication(row.applicationId, { enterpriseRemark: value })
      }).then(() => {
        this.$modal.msgSuccess('已拒绝')
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>
