import request from '@/utils/request'

// 查询导航菜单列表
export function listMenu(query) {
  return request({
    url: '/business/menu/list',
    method: 'get',
    params: query
  })
}

export function allMenu(query) {
  return request({
    url: '/business/menu/all',
    method: 'get',
    params: query
  })
}

// 查询导航菜单详细
export function getMenu(id) {
  return request({
    url: '/business/menu/' + id,
    method: 'get'
  })
}

// 新增导航菜单
export function addMenu(data) {
  return request({
    url: '/business/menu',
    method: 'post',
    data: data
  })
}

// 修改导航菜单
export function updateMenu(data) {
  return request({
    url: '/business/menu',
    method: 'put',
    data: data
  })
}

// 删除导航菜单
export function delMenu(id) {
  return request({
    url: '/business/menu/' + id,
    method: 'delete'
  })
}
