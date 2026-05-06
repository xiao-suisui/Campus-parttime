<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>个人信息</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" v-loading="loading">
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
            <el-radio label="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学校" prop="schoolName">
          <el-input v-model="form.schoolName" placeholder="请输入学校名称" />
        </el-form-item>
        <el-form-item label="专业" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="入学年份">
          <el-input-number v-model="form.gradeYear" :min="2000" :max="2100" placeholder="请输入入学年份" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" v-hasPermi="['student:info:add', 'student:info:edit']">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
  getStudentInfo,
  addStudentInfo,
  updateStudentInfo
} from '@/api/student/info'

export default {
  name: 'StudentInfo',
  data() {
    return {
      loading: false,
      form: {
        studentId: undefined,
        studentNo: undefined,
        realName: undefined,
        gender: '2',
        schoolName: undefined,
        majorName: undefined,
        gradeYear: undefined,
        phone: undefined
      },
      rules: {
        realName: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
        schoolName: [{ required: true, message: '学校名称不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      this.loading = true
      getStudentInfo().then((response) => {
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
        const request = this.form.studentId ? updateStudentInfo(this.form) : addStudentInfo(this.form)
        request.then(() => {
          this.$modal.msgSuccess('保存成功')
          this.getInfo()
        })
      })
    }
  }
}
</script>
