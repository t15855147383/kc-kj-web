import request from "@/utils/request";

export function roleTypeTreeSelect() {
  return request({
    url: '/system/roleType/tree',
    method: 'get'
  })
}
export function addRoleType(data) {
  return request({
    url: '/system/roleType',
    method: 'post',
    data: data
  })
}
export function updateRoleType(data) {
  return request({
    url: '/system/roleType',
    method: 'put',
    data: data
  })
}
export function delRoleType(id) {
  return request({
    url: '/system/roleType/' + id,
    method: 'delete'
  })
}
