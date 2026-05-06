import request from '@/utils/request'

export function listStudentPost(query) {
  return request({
    url: '/student/post/list',
    method: 'get',
    params: query
  })
}

export function getStudentPost(postId) {
  return request({
    url: '/student/post/' + postId,
    method: 'get'
  })
}

