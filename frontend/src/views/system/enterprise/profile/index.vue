<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>企业资料</span>
        <el-button style="float: right" type="primary" size="mini" @click="submitAudit" v-hasPermi="['enterprise:profile:submit']">提交审核</el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" v-loading="loading">
        <el-form-item label="企业名称" prop="enterpriseName">
          <el-input v-model="form.enterpriseName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="unifiedCode">
          <el-input v-model="form.unifiedCode" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="法人信息" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入法人信息" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="营业执照" prop="licenseUrl">
          <file-upload v-model="form.licenseUrl" />
        </el-form-item>
        <el-form-item label="企业简介" prop="intro">
          <el-input v-model="form.intro" type="textarea" :rows="4" placeholder="请输入企业简介" />
        </el-form-item>
        <el-form-item label="审核状态">
          <dict-tag :options="dict.type.enterprise_audit_status" :value="form.auditStatus" />
        </el-form-item>
        <el-form-item label="驳回原因" v-if="form.auditReason">
          <el-input v-model="form.auditReason" type="textarea" :rows="2" disabled />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" v-hasPermi="['enterprise:profile:add', 'enterprise:profile:edit']">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
  getEnterpriseProfile,
  addEnterpriseProfile,
  updateEnterpriseProfile,
  submitEnterpriseProfileAudit
} from '@/api/enterprise/profile'

export default {
  name: 'EnterpriseProfile',
  dicts: ['enterprise_audit_status'],
  data() {
    return {
      loading: false,
      form: {
        enterpriseId: undefined,
        enterpriseName: undefined,
        unifiedCode: undefined,
        legalPerson: undefined,
        contactName: undefined,
        contactPhone: undefined,
        licenseUrl: undefined,
        intro: undefined,
        auditStatus: '0',
        auditReason: undefined
      },
      rules: {
        enterpriseName: [{ required: true, message: '企业名称不能为空', trigger: 'blur' }],
        unifiedCode: [{ required: true, message: '统一社会信用代码不能为空', trigger: 'blur' }],
        contactName: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
        contactPhone: [{ required: true, message: '联系电话不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getProfile()
  },
  methods: {
    getProfile() {
      this.loading = true
      getEnterpriseProfile().then((response) => {
        this.form = response.data || this.form
      }).finally(() => {
        this.loading = false
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        const request = this.form.enterpriseId ? updateEnterpriseProfile(this.form) : addEnterpriseProfile(this.form)
        request.then(() => {
          this.$modal.msgSuccess('保存成功')
          this.getProfile()
        })
      })
    },
    submitAudit() {
      this.$modal.confirm('确认提交审核吗？').then(() => {
        return submitEnterpriseProfileAudit()
      }).then(() => {
        this.$modal.msgSuccess('提交成功')
        this.getProfile()
      }).catch(() => {})
    }
  }
}
</script>


