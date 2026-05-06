import request from '@/utils/request'

// 投递岗位
export function addApplication(data) {
  return request({
    url: '/student/application',
    method: 'post',
    data: data
  })
}

// 我的投递列表
export function listApplication(query) {
  return request({
    url: '/student/application/list',
    method: 'get',
    params: query
  })
}
