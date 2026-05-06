import request from '@/utils/request'

export function getRecommendPosts(limit) {
  return request({
    url: '/student/post/recommend',
    method: 'get',
    params: { limit: limit || 10 }
  })
}
