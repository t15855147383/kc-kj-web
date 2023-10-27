package com.ruoyi.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    // 存储引擎配置，以供后面使用
    private static SpringProcessEngineConfiguration springProcessEngineConfiguration;
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        ActivitiConfig.springProcessEngineConfiguration = springProcessEngineConfiguration;
    }
    /**
     * 获取流程引擎配置
     * @return
     */
    public static SpringProcessEngineConfiguration getSpringProcessEngineConfiguration() {
        return springProcessEngineConfiguration;
    }
}
