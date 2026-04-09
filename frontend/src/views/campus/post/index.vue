<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位名称" prop="postName">
        <el-input
          v-model="queryParams.postName"
          placeholder="请输入岗位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作类型" prop="workType">
        <el-select v-model="queryParams.workType" placeholder="请选择工作类型" clearable>
          <el-option
            v-for="dict in job_work_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="截止时间" style="width: 308px">
        <el-date-picker
          v-model="daterangeDeadlineTime"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="岗位状态" prop="postStatus">
        <el-select v-model="queryParams.postStatus" placeholder="请选择岗位状态" clearable>
          <el-option
            v-for="dict in job_post_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['campus:post:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['campus:post:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['campus:post:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['campus:post:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="岗位ID" align="center" prop="postId" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="薪资下限" align="center" prop="salaryMin" />
      <el-table-column label="薪资上限" align="center" prop="salaryMax" />
      <el-table-column label="结算方式" align="center" prop="salaryUnit">
        <template #default="scope">
          <dict-tag :options="job_salary_unit" :value="scope.row.salaryUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="工作地点类型" align="center" prop="workLocationType">
        <template #default="scope">
          <dict-tag :options="job_location_type" :value="scope.row.workLocationType"/>
        </template>
      </el-table-column>
      <el-table-column label="工作地点" align="center" prop="workAddress" />
      <el-table-column label="工作类型" align="center" prop="workType">
        <template #default="scope">
          <dict-tag :options="job_work_type" :value="scope.row.workType"/>
        </template>
      </el-table-column>
      <el-table-column label="工作时间描述" align="center" prop="workTimeDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['campus:post:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['campus:post:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改岗位信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="postRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="企业ID" prop="enterpriseId">
              <el-input v-model="form.enterpriseId" placeholder="请输入企业ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="岗位名称" prop="postName">
              <el-input v-model="form.postName" placeholder="请输入岗位名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="薪资下限" prop="salaryMin">
              <el-input v-model="form.salaryMin" placeholder="请输入薪资下限" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="薪资上限" prop="salaryMax">
              <el-input v-model="form.salaryMax" placeholder="请输入薪资上限" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="结算方式" prop="salaryUnit">
              <el-select v-model="form.salaryUnit" placeholder="请选择结算方式">
                <el-option
                  v-for="dict in job_salary_unit"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作地点类型" prop="workLocationType">
              <el-select v-model="form.workLocationType" placeholder="请选择工作地点类型">
                <el-option
                  v-for="dict in job_location_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作地点" prop="workAddress">
              <el-input v-model="form.workAddress" placeholder="请输入工作地点" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作类型" prop="workType">
              <el-select v-model="form.workType" placeholder="请选择工作类型">
                <el-option
                  v-for="dict in job_work_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="岗位要求" prop="requirementDesc">
              <el-input v-model="form.requirementDesc" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="招聘人数" prop="recruitCount">
              <el-input v-model="form.recruitCount" placeholder="请输入招聘人数" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="截止时间" prop="deadlineTime">
              <el-date-picker clearable
                v-model="form.deadlineTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择截止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="form.contactName" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系方式-电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="请输入联系方式-电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="岗位状态" prop="postStatus">
              <el-radio-group v-model="form.postStatus">
                <el-radio
                  v-for="dict in job_post_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="浏览量" prop="viewCount">
              <el-input v-model="form.viewCount" placeholder="请输入浏览量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收藏量" prop="collectCount">
              <el-input v-model="form.collectCount" placeholder="请输入收藏量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="投递量" prop="applyCount">
              <el-input v-model="form.applyCount" placeholder="请输入投递量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="录用人数" prop="hireCount">
              <el-input v-model="form.hireCount" placeholder="请输入录用人数" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="完成人数" prop="completeCount">
              <el-input v-model="form.completeCount" placeholder="请输入完成人数" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="删除标志" prop="delFlag">
              <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Post">
import { listPost, getPost, delPost, addPost, updatePost } from "@/api/campus/post"

const { proxy } = getCurrentInstance()
const { job_salary_unit, job_post_status, job_work_type, job_location_type } = proxy.useDict('job_salary_unit', 'job_post_status', 'job_work_type', 'job_location_type')

const postList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const daterangeDeadlineTime = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    postName: null,
    workType: null,
    deadlineTime: null,
    postStatus: null,
  },
  rules: {
    enterpriseId: [
      { required: true, message: "企业ID不能为空", trigger: "blur" }
    ],
    postName: [
      { required: true, message: "岗位名称不能为空", trigger: "blur" }
    ],
    salaryMin: [
      { required: true, message: "薪资下限不能为空", trigger: "blur" }
    ],
    salaryMax: [
      { required: true, message: "薪资上限不能为空", trigger: "blur" }
    ],
    salaryUnit: [
      { required: true, message: "结算方式不能为空", trigger: "change" }
    ],
    workLocationType: [
      { required: true, message: "工作地点类型不能为空", trigger: "change" }
    ],
    workAddress: [
      { required: true, message: "工作地点不能为空", trigger: "blur" }
    ],
    workType: [
      { required: true, message: "工作类型不能为空", trigger: "change" }
    ],
    workTimeDesc: [
      { required: true, message: "工作时间描述不能为空", trigger: "change" }
    ],
    postStatus: [
      { required: true, message: "岗位状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询岗位信息列表 */
function getList() {
  loading.value = true
  queryParams.value.params = {}
  if (null != daterangeDeadlineTime.value && '' != daterangeDeadlineTime.value) {
    queryParams.value.params["beginDeadlineTime"] = daterangeDeadlineTime.value[0]
    queryParams.value.params["endDeadlineTime"] = daterangeDeadlineTime.value[1]
  }
  listPost(queryParams.value).then(response => {
    postList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    postId: null,
    enterpriseId: null,
    postName: null,
    salaryMin: null,
    salaryMax: null,
    salaryUnit: null,
    workLocationType: null,
    workAddress: null,
    workType: null,
    workTimeDesc: null,
    requirementDesc: null,
    recruitCount: null,
    deadlineTime: null,
    contactName: null,
    contactPhone: null,
    postStatus: null,
    viewCount: null,
    collectCount: null,
    applyCount: null,
    hireCount: null,
    completeCount: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    delFlag: null
  }
  proxy.resetForm("postRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeDeadlineTime.value = []
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.postId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加岗位信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _postId = row.postId || ids.value
  getPost(_postId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改岗位信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["postRef"].validate(valid => {
    if (valid) {
      if (form.value.postId != null) {
        updatePost(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPost(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _postIds = row.postId || ids.value
  proxy.$modal.confirm('是否确认删除岗位信息编号为"' + _postIds + '"的数据项？').then(function() {
    return delPost(_postIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('campus/post/export', {
    ...queryParams.value
  }, `post_${new Date().getTime()}.xlsx`)
}

getList()
</script>
