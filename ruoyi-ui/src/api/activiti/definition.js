import request from '@/utils/request'


/**
 * ########################################
 *                  流程定义
 * ########################################
 */
// 查询modeler列表
export function listDefinition(query) {
  return request({
    url: '/activiti/process/definition/list',
    method: 'get',
    params: query
  })
}
// 查询流程详细
export function getDefinitionsByInstanceId(instanceId) {
  return request({
    url: '/activiti/process/definition/getDefinitions/' + instanceId,
    method: 'get'
  })
}
// 挂起激活转换
export function suspendOrActiveApply(data) {
  return request({
    url: '/activiti/process/definition/suspendOrActiveApply',
    method: 'post',
    data:data
  })
}
// 删除Modeler
export function delDefinition(id) {
  return request({
    url: '/activiti/process/definition/remove/' + id,
    method: 'delete'
  })
}
export function getDefinitionXML(query) {
  return request({
    url: 'activiti/process/definition/getDefinitionXML',
    method: 'get',
    params: query
  })
}
export function getProcdefByKey(key) {
  return request({
    url: '/activiti/process/definition/getProcdef/' + key,
    method: 'get'
  })
}
/**
 * ########################################
 *                  流程部署
 * ########################################
 */
export function addDeploymentByString(data){
  return request({
    url: '/activiti/process/definition/addDeploymentByString',
    method: 'post',
    data:data
  })
}
export function editDeploymentByString(data){
  return request({
    url: '/activiti/process/definition/updateDeploymentByString',
    method: 'post',
    data:data
  })
}


/**
 * ########################################
 *                  流程配置
 * ########################################
 */
export function getWorkflowProcess(query) {
  return request({
    url: 'activiti/workflow/process/getProcess',
    method: 'get',
    params: query
  })
}
export function getWorkflowTask(query) {
  return request({
    url: 'activiti/workflow/process/getTask',
    method: 'get',
    params: query
  })
}
export function getWorkflowAssignee(query) {
  return request({
    url: 'activiti/workflow/process/getAssignee',
    method: 'get',
    params: query
  })
}
export function addWorkflowProcess(data){
  return request({
    url: '/activiti/workflow/process/addProcess',
    method: 'post',
    data:data
  })
}
export function editWorkflowProcess(data){
  return request({
    url: '/activiti/workflow/process/editProcess',
    method: 'post',
    data:data
  })
}
export function editWorkflowTask(data){
  return request({
    url: '/activiti/workflow/process/editTask',
    method: 'post',
    data:data
  })
}
export function addWorkflowAssignee(data){
  return request({
    url: '/activiti/workflow/process/addAssignee',
    method: 'post',
    data:data
  })
}
