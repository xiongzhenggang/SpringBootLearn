package com.us.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestProcessRestController {

    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;
    
//    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/start-test-process", method = RequestMethod.POST)
    public void startHireProcess(@RequestBody Map<String, String> data) {
    	
//        Map<String, Object> vars = Collections.<String, Object>singletonMap("applicant", applicant);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userName", "xx");
        // 开始流程  ,processDefinitionKey：testProcess流程文件key。 businessKey1作为业务主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testProcess", "businessKey1",variables);
       
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .taskCandidateGroup("dev-managers")
                .singleResult();
        System.out.println("task.getName:"+task.getName());
    }
    
//    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/firstStepProcess", method = RequestMethod.POST)
    public void firstStepProcess() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("agree", true);
        List<Task> tasks = taskService
        		.createTaskQuery()
        		.list();
        //所有的完成任务
        for (Task task : tasks) {
        	taskService.complete(task.getId(), variables);
        }
    }
//    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/backStepProcess", method = RequestMethod.POST)
    public void backStepProcess() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("agree", true);
        List<Task> tasks = taskService
        		.createTaskQuery()
        		.list();
        //所有的完成任务
        for (Task task : tasks) {
        	taskService.complete(task.getId(), variables);
        }
//      taskService.deleteTask(taskId,true); // 删除task（包括历史数据和子任务）  
      //根据任务ID和用户ID，然后绑定到一块
//        void addCandidateUser(String taskId, String userId);
//        //把任务ID和用户组绑定到一块
//         void addCandidateGroup(Strinvg taskId, String groupId);
//        //不仅绑定到一块，并设置绑定的类型
//         void addUserIdentityLink(String taskId, String userId, String identityLinkType);
//         void addGroupIdentityLink(String taskId, String groupId, String identityLinkType);
//      //根据任务id和用户id设置任务受理人
//        void setAssignee(String taskId, String userId);
//        //根据用户id和任务id设置任务持有人
//         void setOwner(String taskId, String userId);
    }
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/selectProcess", method = RequestMethod.POST)
    @ResponseBody
    public  void selectProcess() {
        taskService
        		.createTaskQuery()
        		.count();
        //所有的完成任务
        // 查询已完成的流程  
        List<HistoricProcessInstance> datas = historyService  
        		.createHistoricProcessInstanceQuery()
        		.finished()
        		.list(); 
        System.out.println("使用finish方法： " + datas.size()); 
     // 根据流程定义ID查询  
        /*
         *查询执行流  
                Execution exe1 = runtimeService.createExecutionQuery()  
                        .processInstanceId(processInstance.getId()).singleResult(); 
           // 根据流程定义ID查询 
         * datas = historyService.createHistoricProcessInstanceQuery()  
                .processDefinitionId(ProcessInstance的id).list(); 
           //根据流程定义key（流程描述文件的process节点id属性）查询
          datas = historyService.createHistoricProcessInstanceQuery()  
                        .processDefinitionKey(define.getKey()).list();     
       */
     // 根据业务主键查询  
        datas = historyService.createHistoricProcessInstanceQuery()  
                .processInstanceBusinessKey("businessKey1")
                .list();
        System.out.println("使用processInstanceBusinessKey方法： " + datas.size()); 
     // 查询没有完成的流程实例  
       historyService.createHistoricProcessInstanceQuery()
       			.unfinished()
       			.list();  
        System.out.println("使用unfinished方法： " + datas.size()); 
    } 
    @RequestMapping(value = "/testMyself", method = RequestMethod.POST)
    public  void testMyself() {
    	Group group = identityService.newGroup("user");
    	group.setName("users");
    	group.setType("security-role");
    	identityService.saveGroup(group);
    	User admin = identityService.newUser("admin");
    	admin.setPassword("admin");
    	identityService.saveUser(admin);
    } 
    @RequestMapping(value = "/findPersonalTaskList", method = RequestMethod.POST)  
    public void findPersonalTaskList(@RequestBody Map<String, String> data){  
        //根据任务办理人查询  
        String assignee = data.get("userName");  
        List<Task> list = taskService  
                        .createTaskQuery()//  
                        .taskAssignee(assignee)//个人任务的查询  
                        .list();
        if(list!=null && list.size()>0){
            for(Task task:list){  
                System.out.println("任务ID："+task.getId());  
                System.out.println("任务的办理人："+task.getAssignee());  
                System.out.println("任务名称："+task.getName());  
                System.out.println("任务的创建时间："+task.getCreateTime());  
                System.out.println("流程实例ID："+task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        } 
        
    }
}