package com.ruoyi.activiti.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/activiti/process")
public class ProcessController extends BaseController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    @GetMapping(value = "/getElements/{processDefinitionId}")
    public AjaxResult getElements(@PathVariable("processDefinitionId") String processDefinitionId){
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        //获取所有任务节点
        List<UserTask> userTaskList = process.findFlowElementsOfType(UserTask.class);
        return AjaxResult.success(userTaskList);
    }
}
