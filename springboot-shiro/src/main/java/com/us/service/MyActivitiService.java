package com.us.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MyActivitiService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private HistoryService historyService;
    //启动流程
	@Transactional
    public void startProcess(String processDefinitionKey
    		,String businessKey
    		,Map<String,Object> variables
    		,String currentUserId) {
		  //利用initiator功能，设置一个名称（不是变量而是变量名）到启动事件上，并且在启动流程时调用一些下面的方法
		//这样流程启动之后如果任务流转至”重新修改”节点则会自动把任务分配给启动流程的人。启动节点设置initiaor为applyUserId，调整节点设置于${applyUserId}
        identityService.setAuthenticatedUserId(currentUserId);
        
		runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
    }

	//查询个人任务
	@Transactional
    public List<Task> getTasksByUser(String assignee) {
		//Only select tasks which are assigned to the given user
        return taskService
        		.createTaskQuery()
        		.taskAssignee(assignee)
        		.list();
    }
	/*//根据角色查询已认领的任务
	public List<Task> getTasksByRole(String roleName){
		
		return taskService
				.createTaskQuery()
				.tas
	}*/
	
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
    /**将组任务指定个人任务(拾取任务)
     * 以下为任务认领的几种方式:
	 * 1) taskService.setAssignee(String taskId, String userId);
	 * 2) taskService.claim(String taskId, String userId);
	 * 3) taskService.setOwner(String taskId, String userId);
	 * setAssignee和claim两个的区别是在认领任务时:
	 * claim会检查该任务是否已经被认领，如果被认领则会抛出ActivitiTaskAlreadyClaimedException,而setAssignee不会进行这样的检查，其他方面两个方法效果一致。
	 * setOwner和setAssignee的区别在于
	 * setOwner实在代理任务时使用，代表着任务的归属者，而这时，setAssignee代表的时代理办理者，
	 * 举个例子来说，公司总经理现在有个任务taskA，去核实一下本年度的财务报表，他现在又很忙没时间，于是将该任务委托给其助理进行办理，此时，就应该这么做：
	 * taskService.setOwner(taskA.getId(), 总经理.getId());
	 * taskService.setAssignee/claim(taskA.getId(), 助理.getId());
     * */    
    public void claim(String taskId,String userName){   
    	taskService.claim(taskId, userName);
    } 
    
    /**向组任务中添加成员*/    
    public void addGroupUser(String taskId, String userId){    
    	taskService.addCandidateUser(taskId, userId);    
    }
    /**向组任务中删除成员*/   
    public void deleteGroupUser(String taskId,String userId){    
    	taskService.deleteCandidateUser(taskId, userId);    
    }
    
    //添加批注
    public void addComment(String userName,String taskId,String msg){
    // 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
       Authentication.setAuthenticatedUserId(userName);//批注人的名称  一定要写，不然查看的时候不知道人物信息
        // 添加批注信息
       taskService.addComment(taskId, null, msg);//comment为批注内容
    }
    //获取批注信息
    public List<Comment> getProcessComments(String taskId) {
        List<Comment> historyCommnets = new ArrayList<>();
//         1) 获取流程实例的ID
        Task task = this.taskService
        			.createTaskQuery()
        			.taskId(taskId)
        			.singleResult();
        ProcessInstance pi = runtimeService
        			.createProcessInstanceQuery()
        			.processInstanceId(task.getProcessInstanceId())
        			.singleResult();
//       2）通过流程实例查询所有的(用户任务类型)历史活动   
        List<HistoricActivityInstance> hais = historyService
        		.createHistoricActivityInstanceQuery()
        		.processInstanceId(pi.getId())
        		.activityType("userTask")
        		.list();
//       3）查询每个历史任务的批注
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                historyCommnets.addAll(comments);
            }
        }
//       5）返回
         return historyCommnets;
    }    
}
