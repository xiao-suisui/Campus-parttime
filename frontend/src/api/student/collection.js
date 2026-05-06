import request from '@/utils/request'

export function listStudentCollection(query) {
  return request({
    url: '/student/collection/list',
    method: 'get',
    params: query
  })
}

export function addStudentCollection(data) {
  return request({
    url: '/student/collection',
    method: 'post',
    data: data
  })
}

export function removeStudentCollectionByPost(postId) {
  return request({
    url: '/student/collection/' + postId,
    method: 'delete'
  })
}

