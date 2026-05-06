import request from '@/utils/request'

// 获取个人信息
export function getStudentInfo() {
  return request({
    url: '/student/info',
    method: 'get'
  })
}

// 新增个人信息
export function addStudentInfo(data) {
  return request({
    url: '/student/info',
    method: 'post',
    data: data
  })
}

// 修改个人信息
export function updateStudentInfo(data) {
  return request({
    url: '/student/info',
    method: 'put',
    data: data
  })
}
