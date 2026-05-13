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

// 查看企业收到的评价
export function listEnterpriseEvaluations(enterpriseId, query) {
  return request({
    url: '/student/evaluation/enterprise/' + enterpriseId,
    method: 'get',
    params: query
  })
}

// 查看收到的评价（企业对我的评价）
export function listEvaluationsAboutMe(query) {
  return request({
    url: '/student/evaluation/about-me',
    method: 'get',
    params: query
  })
}
