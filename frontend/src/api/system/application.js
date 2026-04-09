import request from '@/utils/request'

// 查询投递记录列表
export function listApplication(query) {
  return request({
    url: '/system/application/list',
    method: 'get',
    params: query
  })
}

// 查询投递记录详细
export function getApplication(applicationId) {
  return request({
    url: '/system/application/' + applicationId,
    method: 'get'
  })
}

// 新增投递记录
export function addApplication(data) {
  return request({
    url: '/system/application',
    method: 'post',
    data: data
  })
}

// 修改投递记录
export function updateApplication(data) {
  return request({
    url: '/system/application',
    method: 'put',
    data: data
  })
}

// 删除投递记录
export function delApplication(applicationId) {
  return request({
    url: '/system/application/' + applicationId,
    method: 'delete'
  })
}

// 企业录用投递记录
export function hireApplication(applicationId, data) {
  return request({
    url: '/system/application/' + applicationId + '/hire',
    method: 'put',
    data: data
  })
}

// 企业标记完成投递记录
export function completeApplication(applicationId, data) {
  return request({
    url: '/system/application/' + applicationId + '/complete',
    method: 'put',
    data: data
  })
}
