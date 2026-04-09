import request from '@/utils/request'

// 查询岗位收藏列表
export function listCollection(query) {
  return request({
    url: '/system/collection/list',
    method: 'get',
    params: query
  })
}

// 查询岗位收藏详细
export function getCollection(collectionId) {
  return request({
    url: '/system/collection/' + collectionId,
    method: 'get'
  })
}

// 新增岗位收藏
export function addCollection(data) {
  return request({
    url: '/system/collection',
    method: 'post',
    data: data
  })
}

// 修改岗位收藏
export function updateCollection(data) {
  return request({
    url: '/system/collection',
    method: 'put',
    data: data
  })
}

// 删除岗位收藏
export function delCollection(collectionId) {
  return request({
    url: '/system/collection/' + collectionId,
    method: 'delete'
  })
}
