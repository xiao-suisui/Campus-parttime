<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" @tab-click="handleTabChange">
      <el-tab-pane label="我发出的评价" name="mine">
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
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['enterprise:evaluation:add']">新增评价</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
        </el-row>

        <el-table v-loading="loading" :data="evaluationList">
          <el-table-column label="评价ID" prop="evaluationId" width="80" align="center" />
          <el-table-column label="投递ID" prop="applicationId" width="80" align="center" />
          <el-table-column label="学生ID" prop="studentId" width="80" align="center" />
          <el-table-column label="评分" prop="score" width="150" align="center">
            <template slot-scope="scope">
              <el-rate :value="scope.row.score" disabled show-score text-color="#ff9900" />
            </template>
          </el-table-column>
          <el-table-column label="内容" prop="content" min-width="180" show-overflow-tooltip />
          <el-table-column label="评价时间" prop="evaluationTime" width="160" align="center">
            <template slot-scope="scope">{{ parseTime(scope.row.evaluationTime) }}</template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-tab-pane>

      <el-tab-pane label="收到的评价" name="aboutMe">
        <el-table v-loading="aboutMeLoading" :data="aboutMeList">
          <el-table-column label="评价ID" prop="evaluationId" width="80" align="center" />
          <el-table-column label="学生ID" prop="studentId" width="100" align="center" />
          <el-table-column label="岗位ID" prop="postId" width="100" align="center" />
          <el-table-column label="评分" prop="score" width="150" align="center">
            <template slot-scope="scope">
              <el-rate :value="scope.row.score" disabled show-score text-color="#ff9900" />
            </template>
          </el-table-column>
          <el-table-column label="内容" prop="content" min-width="200" show-overflow-tooltip />
          <el-table-column label="评价时间" prop="evaluationTime" width="160" align="center">
            <template slot-scope="scope">{{ parseTime(scope.row.evaluationTime) }}</template>
          </el-table-column>
        </el-table>

        <pagination v-show="aboutMeTotal > 0" :total="aboutMeTotal" :page.sync="aboutMeParams.pageNum" :limit.sync="aboutMeParams.pageSize" @pagination="getAboutMeList" />
      </el-tab-pane>
    </el-tabs>

    <!-- 新增评价对话框 -->
    <el-dialog title="新增评价" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="投递记录" prop="applicationId">
          <el-select v-model="form.applicationId" placeholder="请选择已标记完成的投递记录" style="width:100%" filterable>
            <el-option
              v-for="item in completedApplications"
              :key="item.applicationId"
              :label="'投递#' + item.applicationId + ' - 学生#' + item.studentId + ' - 岗位#' + item.postId"
              :value="item.applicationId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-rate v-model="form.score" :max="5" show-score text-color="#ff9900" />
        </el-form-item>
        <el-form-item label="评价内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入评价内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnterpriseEvaluation, addEnterpriseEvaluation, listEvaluationsAboutMe } from '@/api/enterprise/evaluation'
import { listEnterpriseApplication } from '@/api/enterprise/application'

export default {
  name: 'EnterpriseEvaluation',
  data() {
    return {
      activeTab: 'mine',
      loading: true,
      aboutMeLoading: true,
      showSearch: true,
      total: 0,
      aboutMeTotal: 0,
      open: false,
      evaluationList: [],
      aboutMeList: [],
      completedApplications: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applicationId: undefined
      },
      aboutMeParams: {
        pageNum: 1,
        pageSize: 10
      },
      form: {},
      rules: {
        applicationId: [{ required: true, message: '请选择投递记录', trigger: 'change' }],
        score: [{ required: true, message: '评分不能为空', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleTabChange(tab) {
      if (tab.name === 'aboutMe' && this.aboutMeList.length === 0) {
        this.getAboutMeList()
      }
    },
    getList() {
      this.loading = true
      listEnterpriseEvaluation(this.queryParams).then((response) => {
        this.evaluationList = response.rows
        this.total = response.total
      }).finally(() => {
        this.loading = false
      })
    },
    getAboutMeList() {
      this.aboutMeLoading = true
      listEvaluationsAboutMe(this.aboutMeParams).then((response) => {
        this.aboutMeList = response.rows
        this.aboutMeTotal = response.total
      }).finally(() => {
        this.aboutMeLoading = false
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
    reset() {
      this.form = {
        applicationId: undefined,
        score: 5,
        content: undefined
      }
      this.resetForm('form')
    },
    cancel() {
      this.open = false
      this.reset()
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.loadCompletedApplications()
    },
    loadCompletedApplications() {
      listEnterpriseApplication({ applicationStatus: '2', pageNum: 1, pageSize: 100 }).then((response) => {
        this.completedApplications = response.rows || []
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        addEnterpriseEvaluation(this.form).then(() => {
          this.$modal.msgSuccess('评价成功')
          this.open = false
          this.getList()
        })
      })
    }
  }
}
</script>
