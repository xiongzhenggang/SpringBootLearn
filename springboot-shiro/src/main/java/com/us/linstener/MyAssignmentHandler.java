package com.us.linstener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyAssignmentHandler implements TaskListener {

	  // @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
	    // Execute custom identity lookups here
		System.out.println("流程监听执行到这里，指定处理任务的代理人及代理角色");
	    // and then for example call following methods:
	    delegateTask.setAssignee("zhangsan");
	    //Adds the given user as a candidate user to this task
	    delegateTask.addCandidateUser("lisi");
	    //Adds the given group as candidate group to this task
	    delegateTask.addCandidateGroup("admin");
	  }

	}
