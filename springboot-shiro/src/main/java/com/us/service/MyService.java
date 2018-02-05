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

    //启动流程
	@Transactional
    public void startProcess(String processDefinitionKey,String businessKey,Map<String,Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
    }

	//查询个人任务
	@Transactional
    public List<Task> getTasks(String assignee) {
		//Only select tasks which are assigned to the given user
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
	//增加任务id完成任务
	@Transactional
	public void sovleTask(String taskId,boolean agreen){
		 Map<String, Object> variables = new HashMap<String, Object>();
		 //是否同意
	        variables.put("agree", agreen);
	        variables.put("reApply", agreen);
//	        List<Task> tasks = taskService
//	        		.createTaskQuery()
//	        		.list();
	    taskService.complete(taskId, variables);
	}
	//查询某个角色的任务列表
    public List<Task> getTaskByCandidateGroup(String roleName) {
		//Only select tasks which are assigned to the given user
        return taskService
        		.createTaskQuery()
        		.taskCandidateGroup(roleName)
        		.list();
    }
	//查询组用户代办任务
    public List<Task> getCandiateUser(String userName){
    	  List<Task> list = taskService    
                  .createTaskQuery()//    
                  .taskCandidateUser(userName)//参与者，组任务查询    
                  .list(); 
    	return list;  
    }
	
    //查询组任务
    public List<Task> getCandiateGroup(String candidateGroup){
  	  List<Task> list = taskService    
                .createTaskQuery()//    
                .taskCandidateGroup(candidateGroup)//参与组任务查询    
                .list(); 
  	return list;  
  }
    //查询组任务
    public List<Task> getTaskByListIds(List<String> assigneeListIds){
  	  List<Task> list = taskService    
                .createTaskQuery()//
                .taskAssigneeIds(assigneeListIds)
                .list();
  	return list;  
  }
    /**将组任务指定个人任务(拾取任务)*/    
    public void claim(String taskId,String userName){   
    	taskService.claim(taskId, userName);
    	/*
    	 * 一下都可以认领任务
    	 * taskService.setAssignee(String taskId, String userId);
    	taskService.claim(String taskId, String userId);
    	taskService.setOwner(String taskId, String userId);
    	
    	setAssignee和claim两个的区别是在认领任务时，
		claim会检查该任务是否已经被认领，如果被认领则会抛出ActivitiTaskAlreadyClaimedException 
    	*/
    }  
    /**向组任务中添加成员*/    
    public void addGroupUser(String taskId, String userId){    
    	taskService.addCandidateUser(taskId, userId);    
    }
    /**向组任务中删除成员*/   
    public void deleteGroupUser(String taskId,String userId){    
    	taskService.deleteCandidateUser(taskId, userId);    
    }
    
}
