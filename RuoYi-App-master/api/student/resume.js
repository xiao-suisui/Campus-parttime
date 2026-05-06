import request from '@/utils/request'

// 获取我的简历
export function getStudentResume() {
  return request({
    url: '/student/resume',
    method: 'get'
  })
}

// 新增简历
export function addStudentResume(data) {
  return request({
    url: '/student/resume',
    method: 'post',
    data: data
  })
}

// 修改简历
export function updateStudentResume(data) {
  return request({
    url: '/student/resume',
    method: 'put',
    data: data
  })
}
