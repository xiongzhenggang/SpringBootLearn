package com.us;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.boot.CommandLineRunner;

/**
 * Created by yangyibo on 17/1/17.
 */

@ComponentScan(basePackages ="com.us")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(Application.class, args);
    }
 /* @Bean
  public CommandLineRunner init(final RepositoryService repositoryService,
                                final RuntimeService runtimeService,
                                final TaskService taskService) {

      return new CommandLineRunner() {
          @Override
          public void run(String... strings) throws Exception {
              Map<String, Object> variables = new HashMap<String, Object>();
              variables.put("applicantName", "John Doe");
              variables.put("email", "john.doe@activiti.com");
              variables.put("phoneNumber", "123456789");
              // <process id="hireProcess" name="Developer Hiring" isExecutable="true">
              runtimeService.startProcessInstanceByKey("hireProcess", variables);
          }
      };

  }*/
}
