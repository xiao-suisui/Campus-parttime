import request from '@/utils/request'

export function getEnterpriseProfile() {
  return request({
    url: '/enterprise/profile',
    method: 'get'
  })
}

export function addEnterpriseProfile(data) {
  return request({
    url: '/enterprise/profile',
    method: 'post',
    data: data
  })
}

export function updateEnterpriseProfile(data) {
  return request({
    url: '/enterprise/profile',
    method: 'put',
    data: data
  })
}

export function submitEnterpriseProfileAudit() {
  return request({
    url: '/enterprise/profile/submit-audit',
    method: 'post'
  })
}

