import request from '@/utils/request'

// 查询页面配置列表
export function listPage(query) {
  return request({
    url: '/business/page/list',
    method: 'get',
    params: query
  })
}
export function allPage(query) {
  return request({
    url: '/business/page/all',
    method: 'get',
    params: query
  })
}

// 查询页面配置详细
export function getPage(id) {
  return request({
    url: '/business/page/' + id,
    method: 'get'
  })
}

// 新增页面配置
export function addPage(data) {
  return request({
    url: '/business/page',
    method: 'post',
    data: data
  })
}

// 修改页面配置
export function updatePage(data) {
  return request({
    url: '/business/page',
    method: 'put',
    data: data
  })
}

// 删除页面配置
export function delPage(id) {
  return request({
    url: '/business/page/' + id,
    method: 'delete'
  })
}
