import request from '@/utils/request'

// 评价列表
export function listEvaluation(query) {
  return request({
    url: '/student/evaluation/list',
    method: 'get',
    params: query
  })
}

// 提交评价
export function addEvaluation(data) {
  return request({
    url: '/student/evaluation',
    method: 'post',
    data: data
  })
}
