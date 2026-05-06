import request from '@/utils/request'

// 查询公告列表（带已读状态）
export function listStudentNotice(limit) {
  return request({
    url: '/system/notice/listTop',
    method: 'get',
    params: { limit: limit || 100 }
  })
}
