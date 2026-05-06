<template>
  <div class="app-container">
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div slot="header"><span>学生总数</span></div>
          <div class="stat-number">{{ overview.studentCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div slot="header"><span>企业总数</span></div>
          <div class="stat-number">{{ overview.enterpriseCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div slot="header"><span>岗位总数</span></div>
          <div class="stat-number">{{ overview.jobCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div slot="header"><span>投递总数</span></div>
          <div class="stat-number">{{ overview.applicationCount || 0 }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <div slot="header">
        <span>近 30 天趋势</span>
      </div>
      <div ref="trendChart" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getStatOverview, getStatTrend } from '@/api/admin/stat'

export default {
  name: 'AdminStat',
  data() {
    return {
      overview: {},
      chart: null
    }
  },
  created() {
    this.getOverview()
    this.getTrend()
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
    }
  },
  methods: {
    getOverview() {
      getStatOverview().then((response) => {
        this.overview = response.data || {}
      })
    },
    getTrend() {
      getStatTrend({ days: 30 }).then((response) => {
        const data = response.data || {}
        this.updateChart(data)
      })
    },
    initChart() {
      this.chart = echarts.init(this.$refs.trendChart)
      this.chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['学生', '企业', '岗位', '投递'] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', boundaryGap: false, data: [] },
        yAxis: { type: 'value' },
        series: [
          { name: '学生', type: 'line', data: [] },
          { name: '企业', type: 'line', data: [] },
          { name: '岗位', type: 'line', data: [] },
          { name: '投递', type: 'line', data: [] }
        ]
      })
    },
    updateChart(data) {
      if (!this.chart) return
      this.chart.setOption({
        xAxis: { data: data.dates || [] },
        series: [
          { name: '学生', data: data.studentTrend || [] },
          { name: '企业', data: data.enterpriseTrend || [] },
          { name: '岗位', data: data.jobTrend || [] },
          { name: '投递', data: data.applicationTrend || [] }
        ]
      })
    }
  }
}
</script>

<style scoped>
.stat-number {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  color: #409eff;
}
.mb20 {
  margin-bottom: 20px;
}
</style>
