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
      <el-table-column label="审核状态" prop="auditStatus" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.enterprise_audit_status" :value="scope.row.auditStatus" />
        </template>
      </el-table-column>
      <el-table-column label="驳回原因" prop="auditReason" min-width="150" />
      <el-table-column label="操作" min-width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:pass']" @click="handlePass(scope.row)">通过</el-button>
          <el-button type="text" size="mini" v-hasPermi="['admin:enterprise:audit:reject']" @click="handleReject(scope.row)">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
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
      }
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
    handleView(row) {
      getEnterpriseAudit(row.enterpriseId).then((response) => {
        this.$alert(JSON.stringify(response.data, null, 2), '企业详情', { confirmButtonText: '关闭' })
      })
    },
    handlePass(row) {
      this.$modal.confirm('确认通过该企业审核吗？').then(() => {
        return passEnterpriseAudit(row.enterpriseId)
      }).then(() => {
        this.$modal.msgSuccess('审核通过')
        this.getList()
      }).catch(() => {})
    },
    handleReject(row) {
      this.$prompt('请输入驳回原因', '驳回确认', { inputValue: row.auditReason || '' }).then(({ value }) => {
        return rejectEnterpriseAudit(row.enterpriseId, { reason: value })
      }).then(() => {
        this.$modal.msgSuccess('已驳回')
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>

