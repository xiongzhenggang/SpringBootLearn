package com.us.activiti;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;

public class MyGroupManagerMybitis extends MybatisGroupDataManager{

	public MyGroupManagerMybitis(ProcessEngineConfigurationImpl processEngineConfiguration) {
		super(processEngineConfiguration);
		// TODO Auto-generated constructor stub
	}

}
