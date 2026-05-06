<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>我的简历</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" v-loading="loading">
        <el-form-item label="求职意向" prop="expectedPosition">
          <el-input v-model="form.expectedPosition" placeholder="请输入求职意向" />
        </el-form-item>
        <el-form-item label="期望最低薪资">
          <el-input-number v-model="form.expectedSalaryMin" :min="0" :precision="2" placeholder="元/天" />
        </el-form-item>
        <el-form-item label="期望最高薪资">
          <el-input-number v-model="form.expectedSalaryMax" :min="0" :precision="2" placeholder="元/天" />
        </el-form-item>
        <el-form-item label="教育经历" prop="educationExperience">
          <el-input v-model="form.educationExperience" type="textarea" :rows="4" placeholder="请填写教育经历" />
        </el-form-item>
        <el-form-item label="兼职经历" prop="partTimeExperience">
          <el-input v-model="form.partTimeExperience" type="textarea" :rows="4" placeholder="请填写兼职经历" />
        </el-form-item>
        <el-form-item label="技能特长" prop="skillTags">
          <el-input v-model="form.skillTags" placeholder="请填写技能特长，多个用逗号分隔" />
        </el-form-item>
        <el-form-item label="自我介绍" prop="selfIntro">
          <el-input v-model="form.selfIntro" type="textarea" :rows="4" placeholder="请填写自我介绍" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" v-hasPermi="['student:resume:add', 'student:resume:edit']">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
  getStudentResume,
  addStudentResume,
  updateStudentResume
} from '@/api/student/resume'

export default {
  name: 'StudentResume',
  data() {
    return {
      loading: false,
      form: {
        resumeId: undefined,
        expectedPosition: undefined,
        expectedSalaryMin: undefined,
        expectedSalaryMax: undefined,
        educationExperience: undefined,
        partTimeExperience: undefined,
        skillTags: undefined,
        selfIntro: undefined
      },
      rules: {
        expectedPosition: [{ required: true, message: '求职意向不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getResume()
  },
  methods: {
    getResume() {
      this.loading = true
      getStudentResume().then((response) => {
        if (response.data) {
          this.form = response.data
        }
      }).finally(() => {
        this.loading = false
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        const request = this.form.resumeId ? updateStudentResume(this.form) : addStudentResume(this.form)
        request.then(() => {
          this.$modal.msgSuccess('保存成功')
          this.getResume()
        })
      })
    }
  }
}
</script>
