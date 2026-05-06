<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位ID" prop="postId">
        <el-input v-model="queryParams.postId" placeholder="请输入岗位ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openAdd" v-hasPermi="['student:collection:add']">新增收藏</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="collectionList">
      <el-table-column label="收藏ID" prop="collectionId" width="100" />
      <el-table-column label="岗位ID" prop="postId" width="100" />
      <el-table-column label="收藏时间" prop="collectionTime" width="160">
        <template slot-scope="scope">{{ parseTime(scope.row.collectionTime, '{y}-{m}-{d}') }}</template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="text" size="mini" v-hasPermi="['student:collection:remove']" @click="handleRemove(scope.row)">取消收藏</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="新增收藏" :visible.sync="open" width="420px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="岗位ID" prop="postId">
          <el-input-number v-model="form.postId" :min="1" controls-position="right" style="width: 100%" />
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
import { listStudentCollection, addStudentCollection, removeStudentCollectionByPost } from '@/api/student/collection'

export default {
  name: 'StudentCollection',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      open: false,
      collectionList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postId: undefined
      },
      form: {
        postId: undefined
      },
      rules: {
        postId: [{ required: true, message: '岗位ID不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStudentCollection(this.queryParams).then((response) => {
        this.collectionList = response.rows
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
      this.form = { postId: undefined }
      this.open = true
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        addStudentCollection(this.form).then(() => {
          this.$modal.msgSuccess('收藏成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleRemove(row) {
      this.$modal.confirm('确认取消该收藏吗？').then(() => {
        return removeStudentCollectionByPost(row.postId)
      }).then(() => {
        this.$modal.msgSuccess('取消收藏成功')
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>

