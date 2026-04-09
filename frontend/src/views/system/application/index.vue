<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位ID" prop="postId">
        <el-input
          v-model="queryParams.postId"
          placeholder="请输入岗位ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生ID" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学生ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投递时间">
        <el-date-picker
          v-model="daterangeApplyTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="处理状态" prop="applicationStatus">
        <el-select v-model="queryParams.applicationStatus" placeholder="请选择处理状态" clearable>
          <el-option
            v-for="dict in dict.type.job_application_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:application:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:application:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:application:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:application:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="投递ID" align="center" prop="applicationId" />
      <el-table-column label="岗位ID" align="center" prop="postId" />
      <el-table-column label="学生ID" align="center" prop="studentId" />
      <el-table-column label="简历ID" align="center" prop="resumeId" />
      <el-table-column label="投递时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="applicationStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.job_application_status" :value="scope.row.applicationStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="最近处理时间" align="center" prop="processTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.processTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="企业备注" align="center" prop="enterpriseRemark" />
      <el-table-column label="企业标记完成时间" align="center" prop="completeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleHire(scope.row)"
            v-if="scope.row.applicationStatus === '0'"
            v-hasPermi="['system:application:edit']"
          >录用</el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleComplete(scope.row)"
            v-if="scope.row.applicationStatus === '1'"
            v-hasPermi="['system:application:edit']"
          >标记完成</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:application:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:application:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改投递记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="岗位ID" prop="postId">
              <el-input v-model="form.postId" placeholder="请输入岗位ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简历ID" prop="resumeId">
              <el-input v-model="form.resumeId" placeholder="请输入简历ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listApplication, getApplication, delApplication, addApplication, updateApplication, hireApplication, completeApplication } from "@/api/system/application"

export default {
  name: "Application",
  dicts: ['job_application_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 投递记录表格数据
      applicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 删除标志时间范围
      daterangeApplyTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postId: null,
        studentId: null,
        applyTime: null,
        applicationStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        postId: [
          { required: true, message: "岗位ID不能为空", trigger: "blur" }
        ],
        resumeId: [
          { required: true, message: "简历ID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询投递记录列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeApplyTime && '' != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0]
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1]
      }
      listApplication(this.queryParams).then(response => {
        this.applicationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        applicationId: null,
        postId: null,
        studentId: null,
        resumeId: null,
        applyTime: null,
        applicationStatus: null,
        processTime: null,
        enterpriseRemark: null,
        completeTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeApplyTime = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.applicationId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加投递记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const applicationId = row.applicationId || this.ids
      getApplication(applicationId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改投递记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.applicationId != null) {
            updateApplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addApplication(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const applicationIds = row.applicationId || this.ids
      this.$modal.confirm('是否确认删除投递记录编号为"' + applicationIds + '"的数据项？').then(function() {
        return delApplication(applicationIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/application/export', {
        ...this.queryParams
      }, `application_${new Date().getTime()}.xlsx`)
    },
    /** 录用按钮操作 */
    handleHire(row) {
      const applicationId = row.applicationId
      this.$modal.confirm('确认录用该投递记录吗？').then(() => {
        return hireApplication(applicationId, { enterpriseRemark: row.enterpriseRemark })
      }).then(() => {
        this.$modal.msgSuccess("录用成功")
        this.getList()
      }).catch(() => {})
    },
    /** 标记完成按钮操作 */
    handleComplete(row) {
      const applicationId = row.applicationId
      this.$modal.confirm('确认标记该投递记录为完成吗？').then(() => {
        return completeApplication(applicationId, { enterpriseRemark: row.enterpriseRemark })
      }).then(() => {
        this.$modal.msgSuccess("标记完成成功")
        this.getList()
      }).catch(() => {})
    },
  }
}
</script>
