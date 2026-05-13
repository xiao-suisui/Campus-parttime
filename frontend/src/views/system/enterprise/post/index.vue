<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位名称" prop="postName">
        <el-input v-model="queryParams.postName" placeholder="请输入岗位名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="postStatus">
        <el-select v-model="queryParams.postStatus" placeholder="岗位状态" clearable>
          <el-option v-for="dict in dict.type.job_post_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['enterprise:post:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['enterprise:post:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="岗位ID" prop="postId" width="80" align="center" />
      <el-table-column label="岗位名称" prop="postName" min-width="130" show-overflow-tooltip />
      <el-table-column label="薪资范围" width="130" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.salaryMin != null && scope.row.salaryMax != null">{{ scope.row.salaryMin }}-{{ scope.row.salaryMax }}元/{{ salaryUnitLabel(scope.row.salaryUnit) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工作地点" prop="workAddress" width="120" show-overflow-tooltip />
      <el-table-column label="招聘人数" prop="recruitCount" width="80" align="center" />
      <el-table-column label="状态" prop="postStatus" width="80" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.job_post_status" :value="scope.row.postStatus" />
        </template>
      </el-table-column>
      <el-table-column label="投递量" prop="applyCount" width="70" align="center" />
      <el-table-column label="更新时间" prop="updateTime" width="160" align="center">
        <template slot-scope="scope">{{ parseTime(scope.row.updateTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" min-width="220" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['enterprise:post:query']" @click="handleView(scope.row)">详情</el-button>
          <el-button type="text" size="mini" v-hasPermi="['enterprise:post:edit']" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" v-hasPermi="['enterprise:post:up']" @click="handleUp(scope.row)">上架</el-button>
          <el-button type="text" size="mini" v-hasPermi="['enterprise:post:down']" @click="handleDown(scope.row)">下架</el-button>
          <el-button type="text" size="mini" style="color:#F56C6C" v-hasPermi="['enterprise:post:remove']" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="620px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="岗位名称" prop="postName">
          <el-input v-model="form.postName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="薪资下限" prop="salaryMin">
              <el-input-number v-model="form.salaryMin" :min="0" :precision="0" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资上限" prop="salaryMax">
              <el-input-number v-model="form.salaryMax" :min="0" :precision="0" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="结算方式" prop="salaryUnit">
              <el-select v-model="form.salaryUnit" placeholder="请选择" style="width:100%">
                <el-option label="日结" value="1" />
                <el-option label="周结" value="2" />
                <el-option label="月结" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作类型" prop="workType">
              <el-select v-model="form.workType" placeholder="请选择" style="width:100%">
                <el-option label="短期" value="1" />
                <el-option label="长期" value="2" />
                <el-option label="实习" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="工作地点类型" prop="workLocationType">
              <el-select v-model="form.workLocationType" placeholder="请选择" style="width:100%">
                <el-option label="校内" value="1" />
                <el-option label="校外" value="2" />
                <el-option label="远程" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招聘人数" prop="recruitCount">
              <el-input-number v-model="form.recruitCount" :min="1" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="工作地点" prop="workAddress">
          <el-input v-model="form.workAddress" placeholder="请输入具体工作地点" />
        </el-form-item>
        <el-form-item label="工作时间" prop="workTimeDesc">
          <el-input v-model="form.workTimeDesc" placeholder="如：周一至周五 9:00-17:00" />
        </el-form-item>
        <el-form-item label="岗位要求" prop="requirementDesc">
          <el-input v-model="form.requirementDesc" type="textarea" :rows="4" placeholder="请输入岗位要求" />
        </el-form-item>
        <el-form-item label="工作描述" prop="postDesc">
          <el-input v-model="form.postDesc" type="textarea" :rows="4" placeholder="请输入工作具体内容描述" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="form.contactName" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="截止时间" prop="deadlineTime">
          <el-date-picker v-model="form.deadlineTime" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" style="width:100%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="岗位详情" :visible.sync="detailOpen" width="600px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="岗位名称" :span="2">{{ detailForm.postName }}</el-descriptions-item>
        <el-descriptions-item label="薪资范围">
          <span v-if="detailForm.salaryMin != null">{{ detailForm.salaryMin }}-{{ detailForm.salaryMax }}元/{{ salaryUnitLabel(detailForm.salaryUnit) }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.job_post_status" :value="detailForm.postStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="工作地点类型">{{ locationTypeLabel(detailForm.workLocationType) }}</el-descriptions-item>
        <el-descriptions-item label="工作地点">{{ detailForm.workAddress }}</el-descriptions-item>
        <el-descriptions-item label="工作类型">{{ workTypeLabel(detailForm.workType) }}</el-descriptions-item>
        <el-descriptions-item label="招聘人数">{{ detailForm.recruitCount }}</el-descriptions-item>
        <el-descriptions-item label="工作时间" :span="2">{{ detailForm.workTimeDesc }}</el-descriptions-item>
        <el-descriptions-item label="岗位要求" :span="2">{{ detailForm.requirementDesc }}</el-descriptions-item>
        <el-descriptions-item label="工作描述" :span="2">{{ detailForm.postDesc }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ detailForm.contactName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detailForm.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="截止时间">{{ parseTime(detailForm.deadlineTime) }}</el-descriptions-item>
        <el-descriptions-item label="浏览/收藏/投递">{{ detailForm.viewCount }}/{{ detailForm.collectCount }}/{{ detailForm.applyCount }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ parseTime(detailForm.createTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import {
  listEnterprisePost,
  getEnterprisePost,
  addEnterprisePost,
  updateEnterprisePost,
  delEnterprisePost,
  upEnterprisePost,
  downEnterprisePost
} from '@/api/enterprise/post'

export default {
  name: 'EnterprisePost',
  dicts: ['job_post_status'],
  data() {
    return {
      loading: true,
      showSearch: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      open: false,
      detailOpen: false,
      title: '',
      postList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postName: undefined,
        postStatus: undefined
      },
      form: {},
      detailForm: {},
      rules: {
        postName: [{ required: true, message: '岗位名称不能为空', trigger: 'blur' }],
        recruitCount: [{ required: true, message: '招聘人数不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listEnterprisePost(this.queryParams).then((response) => {
        this.postList = response.rows
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
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    reset() {
      this.form = {
        postId: undefined,
        postName: undefined,
        salaryMin: undefined,
        salaryMax: undefined,
        salaryUnit: undefined,
        workLocationType: undefined,
        workAddress: undefined,
        workType: undefined,
        workTimeDesc: undefined,
        postDesc: undefined,
        requirementDesc: undefined,
        recruitCount: 1,
        deadlineTime: undefined,
        contactName: undefined,
        contactPhone: undefined
      }
      this.resetForm('form')
    },
    cancel() {
      this.open = false
      this.reset()
    },
    handleAdd() {
      this.reset()
      this.title = '新增岗位'
      this.open = true
    },
    handleUpdate(row) {
      this.reset()
      const postId = row.postId || this.ids[0]
      getEnterprisePost(postId).then((response) => {
        this.form = response.data
        this.title = '编辑岗位'
        this.open = true
      })
    },
    handleView(row) {
      getEnterprisePost(row.postId).then((response) => {
        this.detailForm = response.data
        this.detailOpen = true
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        const request = this.form.postId ? updateEnterprisePost(this.form) : addEnterprisePost(this.form)
        request.then(() => {
          this.$modal.msgSuccess('保存成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const postIds = row.postId ? [row.postId] : this.ids
      this.$modal.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(() => {
        return delEnterprisePost(postIds.join(','))
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    handleUp(row) {
      this.$modal.confirm('确认上架该岗位？').then(() => {
        return upEnterprisePost(row.postId)
      }).then(() => {
        this.$modal.msgSuccess('上架成功')
        this.getList()
      }).catch(() => {})
    },
    handleDown(row) {
      this.$modal.confirm('确认下架该岗位？').then(() => {
        return downEnterprisePost(row.postId)
      }).then(() => {
        this.$modal.msgSuccess('下架成功')
        this.getList()
      }).catch(() => {})
    },
    salaryUnitLabel(val) {
      const map = { '1': '日', '2': '周', '3': '月' }
      return map[val] || ''
    },
    locationTypeLabel(val) {
      const map = { '1': '校内', '2': '校外', '3': '远程' }
      return map[val] || ''
    },
    workTypeLabel(val) {
      const map = { '1': '短期', '2': '长期', '3': '实习' }
      return map[val] || ''
    }
  }
}
</script>
