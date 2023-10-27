package com.ruoyi.activiti.service;

import java.util.List;

import com.ruoyi.activiti.domain.ActWorkflowAssignee;
import com.ruoyi.activiti.domain.ActWorkflowProcess;
import com.ruoyi.activiti.domain.ActWorkflowTask;

/**
 * 流程配置Service接口
 * 
 * @author danny
 * @date 2020-11-02
 */
public interface IActWorkflowProcessService
{

    /**
     * 查询流程配置
     *
     * @param procDefId 流程定义ID
     * @return 结果
     */
    public ActWorkflowProcess selectActWorkflowProcess(String procDefId);

    /**
     * 查询流程节点配置
     *
     * @param processId 流程配置ID
     * @param elementId 任务节点ID
     * @return 结果
     */
    public ActWorkflowTask selectActWorkflowTask(String processId, String elementId);

    /**
     * 查询流程节点处理人配置
     *
     * @param taskId 节点配置ID
     * @return 结果
     */
    public List<ActWorkflowAssignee> selectActWorkflowAssignee(String taskId);

    /**
     * 新增流程配置
     *
     * @param actWorkflowProcess 动态单
     * @return 结果
     */
    public int insertActWorkflowProcess(ActWorkflowProcess actWorkflowProcess);

    /**
     * 新增流程节点处理人配置
     *
     * @param actWorkflowAssignee 动态单
     * @return 结果
     */
    public int insertActWorkflowAssignee(List<ActWorkflowAssignee> list);


    /**
     * 修改流程配置
     *
     * @param actWorkflowProcess 动态单
     * @return 结果
     */
    public int updateActWorkflowProcess(ActWorkflowProcess actWorkflowProcess);

    /**
     * 修改流程节点配置
     *
     * @param actWorkflowTask 动态单
     * @return 结果
     */
    public int updateActWorkflowTask(ActWorkflowTask actWorkflowTask);
}
