<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>平台公告</span>
      </div>
      <el-table v-loading="loading" :data="noticeList" @row-click="handleView">
        <el-table-column label="公告标题" prop="noticeTitle" min-width="200" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span :style="{ fontWeight: scope.row.isRead ? 'normal' : 'bold' }">{{ scope.row.noticeTitle }}</span>
          </template>
        </el-table-column>
        <el-table-column label="类型" prop="noticeType" width="100">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType" />
          </template>
        </el-table-column>
        <el-table-column label="发布时间" prop="createTime" width="160">
          <template slot-scope="scope">
            {{ parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :title="detailTitle" :visible.sync="detailOpen" width="600px" append-to-body>
        <div v-html="detailContent" class="notice-content"></div>
        <div slot="footer">
          <el-button @click="detailOpen = false">关闭</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { listStudentNotice } from '@/api/student/notice'
import { markNoticeRead } from '@/api/system/notice'

export default {
  name: 'StudentNotice',
  dicts: ['sys_notice_type'],
  data() {
    return {
      loading: false,
      noticeList: [],
      detailOpen: false,
      detailTitle: '',
      detailContent: ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listStudentNotice(100).then((response) => {
        this.noticeList = response.data || []
      }).finally(() => {
        this.loading = false
      })
    },
    handleView(row) {
      this.detailTitle = row.noticeTitle
      this.detailContent = row.noticeContent
      this.detailOpen = true
      if (!row.isRead) {
        markNoticeRead(row.noticeId).then(() => {
          row.isRead = true
        })
      }
    }
  }
}
</script>

<style scoped>
.notice-content {
  line-height: 1.8;
  word-break: break-word;
}
</style>
