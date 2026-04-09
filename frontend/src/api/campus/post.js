import request from '@/utils/request'

// 查询岗位信息列表
export function listPost(query) {
  return request({
    url: '/campus/post/list',
    method: 'get',
    params: query
  })
}

// 查询岗位信息详细
export function getPost(postId) {
  return request({
    url: '/campus/post/' + postId,
    method: 'get'
  })
}

// 新增岗位信息
export function addPost(data) {
  return request({
    url: '/campus/post',
    method: 'post',
    data: data
  })
}

// 修改岗位信息
export function updatePost(data) {
  return request({
    url: '/campus/post',
    method: 'put',
    data: data
  })
}

// 删除岗位信息
export function delPost(postId) {
  return request({
    url: '/campus/post/' + postId,
    method: 'delete'
  })
}
