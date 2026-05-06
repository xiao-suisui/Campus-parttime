import request from '@/utils/request'

export function listEnterpriseAudit(query) {
  return request({
    url: '/admin/audit/enterprise/list',
    method: 'get',
    params: query
  })
}

export function getEnterpriseAudit(enterpriseId) {
  return request({
    url: '/admin/audit/enterprise/' + enterpriseId,
    method: 'get'
  })
}

export function passEnterpriseAudit(enterpriseId) {
  return request({
    url: '/admin/audit/enterprise/' + enterpriseId + '/pass',
    method: 'put'
  })
}

export function rejectEnterpriseAudit(enterpriseId, data) {
  return request({
    url: '/admin/audit/enterprise/' + enterpriseId + '/reject',
    method: 'put',
    data: data
  })
}

