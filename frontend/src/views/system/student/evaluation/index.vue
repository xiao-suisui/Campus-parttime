<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="投递ID" prop="applicationId">
        <el-input v-model="queryParams.applicationId" placeholder="请输入投递ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openAdd" v-hasPermi="['student:evaluation:add']">新增评价</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="evaluationList">
      <el-table-column label="评价ID" prop="evaluationId" width="90" />
      <el-table-column label="投递ID" prop="applicationId" width="100" />
      <el-table-column label="评分" prop="score" width="80" />
      <el-table-column label="内容" prop="content" min-width="160" />
      <el-table-column label="评价时间" prop="evaluationTime" width="140">
        <template slot-scope="scope">{{ parseTime(scope.row.evaluationTime, '{y}-{m}-{d}') }}</template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="新增评价" :visible.sync="open" width="460px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="投递ID" prop="applicationId">
          <el-input-number v-model="form.applicationId" :min="1" controls-position="right" style="width: 100%" />
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-rate v-model="form.score" :max="5" />
        </el-form-item>
        <el-form-item label="评价内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入评价内容" />
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
import { listStudentEvaluation, addStudentEvaluation } from '@/api/student/evaluation'

export default {
  name: 'StudentEvaluation',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      open: false,
      evaluationList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applicationId: undefined
      },
      form: {
        applicationId: undefined,
        score: 5,
        content: undefined
      },
      rules: {
        applicationId: [{ required: true, message: '投递ID不能为空', trigger: 'blur' }],
        score: [{ required: true, message: '评分不能为空', trigger: 'change' }],
        content: [{ required: true, message: '评价内容不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStudentEvaluation(this.queryParams).then((response) => {
        this.evaluationList = response.rows
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
      this.form = { applicationId: undefined, score: 5, content: undefined }
      this.open = true
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        addStudentEvaluation(this.form).then(() => {
          this.$modal.msgSuccess('评价成功')
          this.open = false
          this.getList()
        })
      })
    }
  }
}
</script>

