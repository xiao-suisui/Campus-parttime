<template>
  <div class="app-container">
    <!-- 审核状态提示 -->
    <el-alert
      v-if="form.auditStatus === '0' && !form.enterpriseId"
      title="欢迎注册！请先完善企业信息，然后提交审核。"
      type="info"
      :closable="false"
      show-icon
      style="margin-bottom: 15px;"
    />
    <el-alert
      v-else-if="form.auditStatus === '0' && form.enterpriseId"
      title="您的企业信息正在审核中，请耐心等待管理员审核。"
      type="warning"
      :closable="false"
      show-icon
      style="margin-bottom: 15px;"
    />
    <el-alert
      v-else-if="form.auditStatus === '2'"
      title="您的企业信息审核未通过，请根据驳回原因修改后重新提交。"
      type="error"
      :closable="false"
      show-icon
      style="margin-bottom: 15px;"
    />
    <el-alert
      v-else-if="form.auditStatus === '1'"
      title="您的企业信息已审核通过，可以正常使用所有功能。"
      type="success"
      :closable="false"
      show-icon
      style="margin-bottom: 15px;"
    />

    <el-card>
      <div slot="header" class="clearfix">
        <span>企业资料</span>
        <el-button
          style="float: right"
          type="primary"
          size="mini"
          @click="submitAudit"
          v-hasPermi="['enterprise:profile:submit']"
          :disabled="form.auditStatus === '1'"
        >
          {{ form.auditStatus === '2' ? '重新提交审核' : '提交审核' }}
        </el-button>
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
          <file-upload v-model="form.licenseUrl" :file-type="['png', 'jpg', 'jpeg', 'gif', 'bmp']" :limit="1" />
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
        unifiedCode: [
          { required: true, message: '统一社会信用代码不能为空', trigger: 'blur' },
          { pattern: /^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/, message: '请输入正确的统一社会信用代码', trigger: 'blur' }
        ],
        legalPerson: [{ required: true, message: '法人信息不能为空', trigger: 'blur' }],
        contactName: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
        contactPhone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        licenseUrl: [{ required: true, message: '请上传营业执照', trigger: 'change' }]
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
      // 先验证表单
      this.$refs.form.validate((valid) => {
        if (!valid) {
          this.$modal.msgError('请填写完整的必填信息')
          return
        }
        // 检查是否已保存企业信息
        if (!this.form.enterpriseId) {
          this.$modal.msgError('请先保存企业信息')
          return
        }
        this.$modal.confirm('确认提交审核吗？').then(() => {
          return submitEnterpriseProfileAudit()
        }).then(() => {
          this.$modal.msgSuccess('提交成功')
          this.getProfile()
        }).catch(() => {})
      })
    }
  }
}
</script>


