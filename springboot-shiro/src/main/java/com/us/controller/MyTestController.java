package com.us.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.us.service.MyService;

@RestController
public class MyTestController {

    @Autowired
    private MyService myService;

    //启动一个流程实例
    @RequestMapping(value="/process", method= {RequestMethod.POST,RequestMethod.GET})
    public void startProcessInstance() {
    	Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userName", "xzg");//所属人
//        variables.put("nextUserName", "zhangsan");//下一个处理者
    	//启动一个流程
        myService.startProcess("testProcess","businessKey1",variables);
    }

    //根据用户名查询任务代办任务
    @RequestMapping(value="/getMytasks", method= {RequestMethod.GET,RequestMethod.POST})
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
    	//根据
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }
    
  //根据任务编号处理任务
    @RequestMapping(value = "/sovelProcess", method = {RequestMethod.GET,RequestMethod.POST})
    public void solveProcess(@RequestParam String taskId,@RequestParam boolean agreen) {
       myService.sovleTask(taskId, agreen);
    }
    
    //根据代办人查询
    @RequestMapping(value = "/getTasksByCandidateUser", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TaskRepresentation>  getCandiateUser(@RequestParam String userName) {
    	List<Task> tasks = myService.getCandiateUser(userName);
    	List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
       return dtos;
    }
    
    //根据角色查询任务执行
    @RequestMapping(value = "/getTaskByCandidateGroup", method = {RequestMethod.GET,RequestMethod.POST})
    public void getCandidateGroup(@RequestParam String roleName) {
       myService.getTaskByCandidateGroup(roleName);
    }
    
    
    //认领任务
    @RequestMapping(value = "/claimUser", method = {RequestMethod.GET,RequestMethod.POST})
    public void  claimUser(@RequestParam String userName,@RequestParam String taskId) {
        myService.claim(taskId,userName);
    }
    
    static class TaskRepresentation {
        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

         public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }

}