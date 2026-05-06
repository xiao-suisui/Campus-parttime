import request from '@/utils/request'

export function listEnterpriseApplication(query) {
  return request({
    url: '/enterprise/application/list',
    method: 'get',
    params: query
  })
}

export function getEnterpriseApplication(applicationId) {
  return request({
    url: '/enterprise/application/' + applicationId,
    method: 'get'
  })
}

export function hireEnterpriseApplication(applicationId, data) {
  return request({
    url: '/enterprise/application/' + applicationId + '/hire',
    method: 'put',
    data: data
  })
}

export function completeEnterpriseApplication(applicationId, data) {
  return request({
    url: '/enterprise/application/' + applicationId + '/complete',
    method: 'put',
    data: data
  })
}

export function rejectEnterpriseApplication(applicationId, data) {
  return request({
    url: '/enterprise/application/' + applicationId + '/reject',
    method: 'put',
    data: data
  })
}
