package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.ActWorkflowAssignee;
import com.ruoyi.activiti.domain.ActWorkflowProcess;
import com.ruoyi.activiti.domain.ActWorkflowTask;
import com.ruoyi.activiti.service.IActWorkflowProcessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activiti/workflow/process")
public class WorkflowProcessController extends BaseController {
    @Autowired
    private IActWorkflowProcessService actWorkflowProcessService;
    @GetMapping(value = "/getProcess")
    public AjaxResult getProcess(@RequestParam("procDefId") String procDefId){
        return AjaxResult.success(actWorkflowProcessService.selectActWorkflowProcess(procDefId));
    }
    @GetMapping(value = "/getTask")
    public AjaxResult getTask(@RequestParam("processId") String processId,@RequestParam("elementId") String elementId){
        return AjaxResult.success(actWorkflowProcessService.selectActWorkflowTask(processId,elementId));
    }
    @GetMapping(value = "/getAssignee")
    public AjaxResult getAssignee(@RequestParam("taskId") String taskId){
        return AjaxResult.success(actWorkflowProcessService.selectActWorkflowAssignee(taskId));
    }
    @PostMapping(value = "/addProcess")
    public AjaxResult addProcess(@RequestBody ActWorkflowProcess actWorkflowProcess) {
        return toAjax(actWorkflowProcessService.insertActWorkflowProcess(actWorkflowProcess));
    }
    @PostMapping(value = "/editProcess")
    public AjaxResult editProcess(@RequestBody ActWorkflowProcess actWorkflowProcess) {
        return toAjax(actWorkflowProcessService.updateActWorkflowProcess(actWorkflowProcess));
    }
    @PostMapping(value = "/editTask")
    public AjaxResult editTask(@RequestBody ActWorkflowTask actWorkflowTask) {
        return toAjax(actWorkflowProcessService.updateActWorkflowTask(actWorkflowTask));
    }
    @PostMapping(value = "/addAssignee")
    public AjaxResult addAssignee(@RequestBody Map<String,List<ActWorkflowAssignee>> map) {
        List<ActWorkflowAssignee> list = map.get("list");
        return toAjax(actWorkflowProcessService.insertActWorkflowAssignee(list));
    }
}
