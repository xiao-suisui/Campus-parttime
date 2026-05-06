import request from '@/utils/request'

// 岗位列表
export function listPost(query) {
  return request({
    url: '/student/post/list',
    method: 'get',
    params: query
  })
}

// 岗位详情
export function getPost(postId) {
  return request({
    url: '/student/post/' + postId,
    method: 'get'
  })
}

// 岗位推荐
export function getRecommendPosts(limit) {
  return request({
    url: '/student/post/recommend',
    method: 'get',
    params: { limit: limit || 8 }
  })
}
