import request from '@/utils/request'

// 查询双向互评列表
export function listEvaluation(query) {
  return request({
    url: '/system/evaluation/list',
    method: 'get',
    params: query
  })
}

// 查询双向互评详细
export function getEvaluation(evaluationId) {
  return request({
    url: '/system/evaluation/' + evaluationId,
    method: 'get'
  })
}

// 新增双向互评
export function addEvaluation(data) {
  return request({
    url: '/system/evaluation',
    method: 'post',
    data: data
  })
}

// 修改双向互评
export function updateEvaluation(data) {
  return request({
    url: '/system/evaluation',
    method: 'put',
    data: data
  })
}

// 删除双向互评
export function delEvaluation(evaluationId) {
  return request({
    url: '/system/evaluation/' + evaluationId,
    method: 'delete'
  })
}
