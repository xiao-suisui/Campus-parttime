import request from '@/utils/request'

export function listEnterpriseEvaluation(query) {
  return request({
    url: '/enterprise/evaluation/list',
    method: 'get',
    params: query
  })
}

export function addEnterpriseEvaluation(data) {
  return request({
    url: '/enterprise/evaluation',
    method: 'post',
    data: data
  })
}

export function listEvaluationsAboutMe(query) {
  return request({
    url: '/enterprise/evaluation/about-me',
    method: 'get',
    params: query
  })
}

