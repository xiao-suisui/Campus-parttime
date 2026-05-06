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
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openAdd" v-hasPermi="['student:application:add']">新增投递</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="applicationList">
      <el-table-column label="投递ID" prop="applicationId" width="90" />
      <el-table-column label="岗位ID" prop="postId" width="90" />
      <el-table-column label="简历ID" prop="resumeId" width="90" />
      <el-table-column label="状态" prop="applicationStatus" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.job_application_status" :value="scope.row.applicationStatus" />
        </template>
      </el-table-column>
      <el-table-column label="投递时间" prop="applyTime" width="140">
        <template slot-scope="scope">{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</template>
      </el-table-column>
      <el-table-column label="企业备注" prop="enterpriseRemark" min-width="150" />
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['student:application:query']" @click="handleView(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="新增投递" :visible.sync="open" width="420px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="岗位ID" prop="postId">
          <el-input-number v-model="form.postId" :min="1" controls-position="right" style="width: 100%" />
        </el-form-item>
        <el-form-item label="简历ID" prop="resumeId">
          <el-input-number v-model="form.resumeId" :min="1" controls-position="right" style="width: 100%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStudentApplication, getStudentApplication, addStudentApplication } from '@/api/student/application'

export default {
  name: 'StudentApplication',
  dicts: ['job_application_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      open: false,
      applicationList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postId: undefined,
        applicationStatus: undefined
      },
      form: {
        postId: undefined,
        resumeId: undefined
      },
      rules: {
        postId: [{ required: true, message: '岗位ID不能为空', trigger: 'blur' }],
        resumeId: [{ required: true, message: '简历ID不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStudentApplication(this.queryParams).then((response) => {
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
    openAdd() {
      this.form = { postId: undefined, resumeId: undefined }
      this.open = true
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        addStudentApplication(this.form).then(() => {
          this.$modal.msgSuccess('新增成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleView(row) {
      getStudentApplication(row.applicationId).then((response) => {
        this.$alert(JSON.stringify(response.data, null, 2), '投递详情', { confirmButtonText: '关闭' })
      })
    }
  }
}
</script>

