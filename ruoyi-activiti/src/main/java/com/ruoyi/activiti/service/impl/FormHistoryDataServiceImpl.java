package com.ruoyi.activiti.service.impl;

import com.ruoyi.activiti.domain.dto.HistoryDataDTO;
import com.ruoyi.activiti.service.IActWorkflowProcessService;
import com.ruoyi.activiti.service.IFormHistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/11/3-10:20
 * 版本   开发者     日期
 * 1.0    Danny    2020/11/3
 */
@Service
public class FormHistoryDataServiceImpl implements IFormHistoryDataService {
    @Autowired
    private IActWorkflowProcessService actWorkflowFormDataService;



    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<HistoryDataDTO> historyDataShow(String businessKey) {

        return null;
    }



}
