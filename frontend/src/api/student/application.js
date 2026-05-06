import request from '@/utils/request'

export function listStudentApplication(query) {
  return request({
    url: '/student/application/list',
    method: 'get',
    params: query
  })
}

export function getStudentApplication(applicationId) {
  return request({
    url: '/student/application/' + applicationId,
    method: 'get'
  })
}

export function addStudentApplication(data) {
  return request({
    url: '/student/application',
    method: 'post',
    data: data
  })
}

