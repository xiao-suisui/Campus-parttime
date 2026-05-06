import request from '@/utils/request'

export function getStudentResume() {
  return request({
    url: '/student/resume',
    method: 'get'
  })
}

export function addStudentResume(data) {
  return request({
    url: '/student/resume',
    method: 'post',
    data: data
  })
}

export function updateStudentResume(data) {
  return request({
    url: '/student/resume',
    method: 'put',
    data: data
  })
}
