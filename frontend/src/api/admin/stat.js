import request from '@/utils/request'

export function getStatOverview() {
  return request({
    url: '/admin/stat/overview',
    method: 'get'
  })
}

export function getStatTrend(params) {
  return request({
    url: '/admin/stat/trend',
    method: 'get',
    params: params
  })
}
