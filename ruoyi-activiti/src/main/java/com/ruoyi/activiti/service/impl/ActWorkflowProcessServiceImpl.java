package com.ruoyi.activiti.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ruoyi.activiti.domain.ActWorkflowAssignee;
import com.ruoyi.activiti.domain.ActWorkflowProcess;
import com.ruoyi.activiti.domain.ActWorkflowTask;
import com.ruoyi.common.utils.uuid.UUID;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.ActWorkflowProcessMapper;
import com.ruoyi.activiti.service.IActWorkflowProcessService;

import javax.annotation.Resource;


/**
 * 流程配置Service业务层处理
 * 
 * @author danny
 * @date 2020-11-02
 */
@Service
public class ActWorkflowProcessServiceImpl implements IActWorkflowProcessService
{
    @Resource
    private ActWorkflowProcessMapper actWorkflowProcessMapper;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public ActWorkflowProcess selectActWorkflowProcess(String procDefId) {
        return actWorkflowProcessMapper.selectActWorkflowProcess(procDefId);
    }

    @Override
    public ActWorkflowTask selectActWorkflowTask(String processId, String elementId) {
        return actWorkflowProcessMapper.selectActWorkflowTask(processId,elementId);
    }

    @Override
    public List<ActWorkflowAssignee> selectActWorkflowAssignee(String taskId) {
        return actWorkflowProcessMapper.selectActWorkflowAssignee(taskId);
    }

    @Override
    public int insertActWorkflowProcess(ActWorkflowProcess actWorkflowProcess) {
        actWorkflowProcess.setId(UUID.randomUUID().toString());
        int row = actWorkflowProcessMapper.insertActWorkflowProcess(actWorkflowProcess);

        BpmnModel bpmnModel = repositoryService.getBpmnModel(actWorkflowProcess.getProcDefId());
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        if(flowElements != null && !flowElements.isEmpty()){
            int sort = 0;
            for(FlowElement flowElement : flowElements){
                System.out.println(flowElement.getClass().getName());
                System.out.println(UserTask.class.getName());
                if(
                        Objects.equals(flowElement.getClass().getName(), UserTask.class.getName())
                        ||
                        Objects.equals(flowElement.getClass().getName(), ManualTask.class.getName())
                ){
                    ActWorkflowTask actWorkflowTask = new ActWorkflowTask();
                    actWorkflowTask.setId(UUID.randomUUID().toString());
                    actWorkflowTask.setProcessId(actWorkflowProcess.getId());
                    actWorkflowTask.setElementId(flowElement.getId());
                    actWorkflowTask.setElementName(flowElement.getName());
                    actWorkflowTask.setSort(String.valueOf(sort));
                    actWorkflowProcessMapper.insertActWorkflowTask(actWorkflowTask);
                    sort ++;
                }
            }
        }
        return row;
    }

    @Override
    public int insertActWorkflowAssignee(List<ActWorkflowAssignee> list) {
        String taskId = list.get(0).getTaskId();
        actWorkflowProcessMapper.deleteActWorkflowAssignee(taskId);
        for(ActWorkflowAssignee actWorkflowAssignee : list){
            actWorkflowProcessMapper.insertActWorkflowAssignee(actWorkflowAssignee);
        }
        return 1;
    }

    @Override
    public int updateActWorkflowProcess(ActWorkflowProcess actWorkflowProcess) {
        return actWorkflowProcessMapper.updateActWorkflowProcess(actWorkflowProcess);
    }

    @Override
    public int updateActWorkflowTask(ActWorkflowTask actWorkflowTask) {
        return actWorkflowProcessMapper.updateActWorkflowTask(actWorkflowTask);
    }
}
