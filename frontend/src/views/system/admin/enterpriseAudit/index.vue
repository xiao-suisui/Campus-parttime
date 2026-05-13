<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="企业名称" prop="enterpriseName">
        <el-input v-model="queryParams.enterpriseName" placeholder="请输入企业名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="审核状态" clearable>
          <el-option v-for="dict in dict.type.enterprise_audit_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="auditList">
      <el-table-column label="企业ID" prop="enterpriseId" width="90" />
      <el-table-column label="企业名称" prop="enterpriseName" min-width="140" />
      <el-table-column label="统一社会信用代码" prop="unifiedCode" min-width="170" />
      <el-table-column label="联系人" prop="contactName" width="100" />
      <el-table-column label="联系电话" prop="contactPhone" width="120" />
      <el-table-column label="审核状态" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.enterprise_audit_status" :value="scope.row.auditStatus" />
        </template>
      </el-table-column>
      <el-table-column label="提交时间" prop="createTime" width="160" />
      <el-table-column label="操作" min-width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:pass']" @click="handlePass(scope.row)" v-if="scope.row.auditStatus !== '1'">通过</el-button>
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:reject']" @click="handleReject(scope.row)" v-if="scope.row.auditStatus !== '2'">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 企业详情对话框 -->
    <el-dialog :title="'企业详情 - ' + (detailForm.enterpriseName || '')" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="企业ID">{{ detailForm.enterpriseId }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <dict-tag :options="dict.type.enterprise_audit_status" :value="detailForm.auditStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="企业名称" :span="2">{{ detailForm.enterpriseName }}</el-descriptions-item>
        <el-descriptions-item label="统一社会信用代码" :span="2">{{ detailForm.unifiedCode }}</el-descriptions-item>
        <el-descriptions-item label="法人信息">{{ detailForm.legalPerson }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ detailForm.contactName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detailForm.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ detailForm.createTime }}</el-descriptions-item>
        <el-descriptions-item label="企业简介" :span="2">{{ detailForm.intro || '暂无' }}</el-descriptions-item>
        <el-descriptions-item label="营业执照" :span="2">
          <el-image
            v-if="detailForm.licenseUrl"
            :src="getImageUrl(detailForm.licenseUrl)"
            :preview-src-list="[getImageUrl(detailForm.licenseUrl)]"
            style="max-width: 300px; max-height: 200px;"
            fit="contain"
          />
          <span v-else>暂未上传</span>
        </el-descriptions-item>
        <el-descriptions-item label="审核时间" v-if="detailForm.auditTime">{{ detailForm.auditTime }}</el-descriptions-item>
        <el-descriptions-item label="审核人" v-if="detailForm.auditBy">{{ detailForm.auditBy }}</el-descriptions-item>
        <el-descriptions-item label="驳回原因" :span="2" v-if="detailForm.auditReason">
          <span style="color: #f56c6c;">{{ detailForm.auditReason }}</span>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="handlePass(detailForm)" v-if="detailForm.auditStatus !== '1'" v-hasPermi="['admin:enterprise:audit:pass']">审核通过</el-button>
        <el-button type="danger" @click="handleReject(detailForm)" v-if="detailForm.auditStatus !== '2'" v-hasPermi="['admin:enterprise:audit:reject']">审核驳回</el-button>
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listEnterpriseAudit,
  getEnterpriseAudit,
  passEnterpriseAudit,
  rejectEnterpriseAudit
} from '@/api/admin/audit/enterprise'

export default {
  name: 'AdminEnterpriseAudit',
  dicts: ['enterprise_audit_status'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      auditList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enterpriseName: undefined,
        auditStatus: undefined
      },
      // 详情相关
      detailOpen: false,
      detailForm: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listEnterpriseAudit(this.queryParams).then((response) => {
        this.auditList = response.rows
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
    getImageUrl(url) {
      if (!url) return ''
      if (url.startsWith('http')) return url
      return process.env.VUE_APP_BASE_API + url
    },
    handleView(row) {
      getEnterpriseAudit(row.enterpriseId).then((response) => {
        this.detailForm = response.data
        this.detailOpen = true
      })
    },
    handlePass(row) {
      this.$modal.confirm('确认通过该企业审核吗？').then(() => {
        return passEnterpriseAudit(row.enterpriseId)
      }).then(() => {
        this.$modal.msgSuccess('审核通过')
        this.detailOpen = false
        this.getList()
      }).catch(() => {})
    },
    handleReject(row) {
      this.$prompt('请输入驳回原因', '驳回确认', {
        inputValue: row.auditReason || '',
        inputErrorMessage: '驳回原因不能为空',
        inputValidator: (value) => { return !!value && !!value.trim() }
      }).then(({ value }) => {
        return rejectEnterpriseAudit(row.enterpriseId, { reason: value.trim() })
      }).then(() => {
        this.$modal.msgSuccess('已驳回')
        this.detailOpen = false
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>

