package com.us.config;



import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import com.us.activiti.MyGroupManager;
import com.us.activiti.MyUserManager;

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class ActivitiConfig {

	@Autowired//(name="dataSource")
	private DataSource dataSource;
	
	@Autowired//(name="transactionManager")
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private MyUserManager userManager;

	@Autowired
	private MyGroupManager groupManager;

	@Bean(name = "processEngineConfiguration")
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration pf = new SpringProcessEngineConfiguration();
		pf.setDataSource(dataSource);
		pf.setTransactionManager(transactionManager);
		pf.setMailServerHost("smtp.qq.com");
		pf.setMailServerPort(465);
		pf.setMailServerUsername("ab1290014555@qq.com");
		pf.setMailServerPassword("xzg_1290014555");
		pf.setJpaHandleTransaction(true);
		pf.setGroupDataManager(groupManager);
		pf.setUserDataManager(userManager);
		pf.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		Resource resource = new ClassPathResource("processes/TestProcess.bpmn20.xml");
		pf.setDeploymentResources(new Resource[]{resource});
//		List<SessionFactory> customSessionFactories = new ArrayList<>();
//		pf.setUserDataManager(userDataManager);
//		pf.setCustomSessionFactories(customSessionFactories);
		return pf;
	}
}
