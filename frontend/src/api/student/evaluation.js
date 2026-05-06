import request from '@/utils/request'

export function listStudentEvaluation(query) {
  return request({
    url: '/student/evaluation/list',
    method: 'get',
    params: query
  })
}

export function addStudentEvaluation(data) {
  return request({
    url: '/student/evaluation',
    method: 'post',
    data: data
  })
}

