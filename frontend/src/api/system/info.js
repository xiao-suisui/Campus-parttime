import request from '@/utils/request'

// 查询企业信息列表
export function listInfo(query) {
  return request({
    url: '/system/info/list',
    method: 'get',
    params: query
  })
}

// 查询企业信息详细
export function getInfo(enterpriseId) {
  return request({
    url: '/system/info/' + enterpriseId,
    method: 'get'
  })
}

// 新增企业信息
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}

// 修改企业信息
export function updateInfo(data) {
  return request({
    url: '/system/info',
    method: 'put',
    data: data
  })
}

// 删除企业信息
export function delInfo(enterpriseId) {
  return request({
    url: '/system/info/' + enterpriseId,
    method: 'delete'
  })
}
