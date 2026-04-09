import request from '@/utils/request'

// 查询学生简历列表
export function listResume(query) {
  return request({
    url: '/system/resume/list',
    method: 'get',
    params: query
  })
}

// 查询学生简历详细
export function getResume(resumeId) {
  return request({
    url: '/system/resume/' + resumeId,
    method: 'get'
  })
}

// 新增学生简历
export function addResume(data) {
  return request({
    url: '/system/resume',
    method: 'post',
    data: data
  })
}

// 修改学生简历
export function updateResume(data) {
  return request({
    url: '/system/resume',
    method: 'put',
    data: data
  })
}

// 删除学生简历
export function delResume(resumeId) {
  return request({
    url: '/system/resume/' + resumeId,
    method: 'delete'
  })
}
