import request from '@/utils/request'

export function listEnterprisePost(query) {
  return request({
    url: '/enterprise/post/list',
    method: 'get',
    params: query
  })
}

export function getEnterprisePost(postId) {
  return request({
    url: '/enterprise/post/' + postId,
    method: 'get'
  })
}

export function addEnterprisePost(data) {
  return request({
    url: '/enterprise/post',
    method: 'post',
    data: data
  })
}

export function updateEnterprisePost(data) {
  return request({
    url: '/enterprise/post',
    method: 'put',
    data: data
  })
}

export function delEnterprisePost(postId) {
  return request({
    url: '/enterprise/post/' + postId,
    method: 'delete'
  })
}

export function upEnterprisePost(postId) {
  return request({
    url: '/enterprise/post/' + postId + '/up',
    method: 'put'
  })
}

export function downEnterprisePost(postId) {
  return request({
    url: '/enterprise/post/' + postId + '/down',
    method: 'put'
  })
}
