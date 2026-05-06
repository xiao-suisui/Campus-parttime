import request from '@/utils/request'

// 收藏岗位
export function addCollection(data) {
  return request({
    url: '/student/collection',
    method: 'post',
    data: data
  })
}

// 我的收藏列表
export function listCollection(query) {
  return request({
    url: '/student/collection/list',
    method: 'get',
    params: query
  })
}

// 取消收藏
export function delCollection(postId) {
  return request({
    url: '/student/collection/' + postId,
    method: 'delete'
  })
}
