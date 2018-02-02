package com.us.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    
	@Transactional
    public void startProcess(String processDefinitionKey,String businessKey,Map<String,Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
    }

	@Transactional
    public List<Task> getTasks(String assignee) {
		//Only select tasks which are assigned to the given user
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

	//增加任务id完成任务
	@Transactional
	public void sovleTask(String taskId,boolean agreen){
		 Map<String, Object> variables = new HashMap<String, Object>();
	        variables.put("agree", agreen);
//	        List<Task> tasks = taskService
//	        		.createTaskQuery()
//	        		.list();
	    taskService.complete(taskId, variables);
	}
	//查询某个角色的任务列表
	@Transactional
    public List<Task> getRoleTasks(String roleName) {
		//Only select tasks which are assigned to the given user
        return taskService.createTaskQuery().taskCandidateGroup(roleName).list();
    }
	
}
