import request from '@/utils/request'

export function listJobReview(query) {
  return request({
    url: '/admin/job-review/list',
    method: 'get',
    params: query
  })
}

export function getJobReview(postId) {
  return request({
    url: '/admin/job-review/' + postId,
    method: 'get'
  })
}

export function forceDownJobReview(postId) {
  return request({
    url: '/admin/job-review/' + postId + '/force-down',
    method: 'put'
  })
}
