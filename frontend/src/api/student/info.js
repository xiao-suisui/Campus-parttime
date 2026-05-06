import request from '@/utils/request'

export function getStudentInfo() {
  return request({
    url: '/student/info',
    method: 'get'
  })
}

export function addStudentInfo(data) {
  return request({
    url: '/student/info',
    method: 'post',
    data: data
  })
}

export function updateStudentInfo(data) {
  return request({
    url: '/student/info',
    method: 'put',
    data: data
  })
}
