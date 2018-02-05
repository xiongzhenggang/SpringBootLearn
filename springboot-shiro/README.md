## springboot 中集成apache shiro 和 activitie6
### apache shiro集成如下
1. 添加activiti核心包
```xml
<!--shiro相关-->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-ehcache</artifactId>
            <version>1.2.5</version>
        </dependency>
        <dependency>
            <groupId>com.github.theborakompanioni</groupId>
            <artifactId>thymeleaf-extras-shiro</artifactId>
            <version>1.2.1</version>
        </dependency>
```
2. 配置文件
1) dataSource配置mybitis 等配置com.us.config下不在叙述

2）Apache shiro在com.us.shiro包中配置如下
```java
ShiroConfiguration中配置核心主要为：
 @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        filterChainDefinitionManager.put("/logout", "logout");
        //authc 经过认证的请求可访问，否则将会将请求重定向到 ini 配置文件配置的 authc.loginUrl 资源，进行认证操作
//        filterChainDefinitionManager.put("/events/**", "authc,roles[ROLE_ADMIN]");
//      filterChainDefinitionManager.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        //anon 	任何用户发送的请求都能够访问
        filterChainDefinitionManager.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }
```
* 重点需要实现ShiroRealm作为自定义处理
```java
//Realms：用于进行权限信息的验证
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userService;
    @Autowired
    private PermissionDao permissionService;    
  //Authorization(授权)：是授权访问控制，用于对用户进行的操作进行人证授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());
        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(Role userRole:user.getRoles()){
            info.addRole(userRole.getName());
        }
        //赋予权限
        for(Permission permission:permissionService.getByUserId(user.getId())){
                info.addStringPermission(permission.getMethod());
        }

        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
    }
    //Authentication(认证)：是验证用户身份的过程。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());

        User user = userService.getByUserName(token.getUsername());
        if (user != null) {
//            byte[] salt = Encodes.decodeHex(user.getSalt());
//            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        } else {
        	 // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
    }

}
```
3. 登录时使用Apache shiro策略（配置shiroFilter在其他请求也会校验）
```java
{
        //Subject 当前用户的操作
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);
        try {
            subject.login(token);
        }catch (UnknownAccountException e) {
        	 return "{\"Msg\":\"该用户不存在\",\"state\":\"failed\"}";
		}catch (IncorrectCredentialsException e) {
			// TODO: handle exception
        	  return "{\"Msg\":\"您的账号或密码输入错误\",\"state\":\"failed\"}";
		}catch (AuthenticationException e) {
            e.printStackTrace();
//            rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
            return "{\"Msg\":\"您无权登录\",\"state\":\"failed\"}";
        }
        //返回前端相应的数据
        
        return ms.getMenue(1).toJSONString();
//        return "{\"Msg\":\"登陆成功\",\"state\":\"success\"}";
    }
```
* 为了添加自定义用户密码匹配策略可以在ShiroConfiguration添加如下配置，SelfCredentialsMatcher为自定义实现的匹配策略
```
/**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    @Bean(name = "hashedCredentialsMatcher")
    @DependsOn("userService")
    public SelfCredentialsMatcher hashedCredentialsMatcher(@Qualifier("userService") UserService us) {
    	SelfCredentialsMatcher credentialsMatcher = new SelfCredentialsMatcher(us);
        return credentialsMatcher;
    }
```
* 以下为SelfCredentialsMatcher的一个例子
```java
public class SelfCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger LOGGER = Logger.getLogger(SelfCredentialsMatcher.class);
	private UserService us;

	public SelfCredentialsMatcher(UserService us) {
		this.us = us;
	}
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
		String userName = (String) token.getPrincipal();
		UsernamePasswordToken uToken = (UsernamePasswordToken)token;
		String loginPass = String.valueOf(uToken.getPassword());
		User user = us.getByUserName(userName);
		// 连续5次密码错误锁定用户30分钟
		if (!loginPass.equals(user.getUserPass())) {
			LOGGER.info("错误次数：" + user.getErrorCount());
			Result reLoginTime = us.loginTimeOut(user);
			throw new ShiroErrorCountException(reLoginTime.getMessage());
		}
		Result result = us.countError(user);
		if (!result.isSuccess()) {
			throw new ShiroErrorCountException(result.getMessage());
		} else {
			return true;
			// super.doCredentialsMatch(token, info);
		}
		
	}
}
```

### activiti6集成准备如下： 
1. 添加activiti核心包
```xml
<dependency> 
			<groupId>org.activiti</groupId>
			<artifactId>activiti-spring-boot-starter-basic</artifactId>
			<version>${activiti.version}</version>
</dependency>
```
2. 配置文件
1) 添加配置文件
```java
//添加activit配置
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
		pf.setJpaHandleTransaction(true);
		//自定义用户角色
		pf.setGroupDataManager(groupManager);
		pf.setUserDataManager(userManager);
		pf.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		Resource resource = new ClassPathResource("processes/TestProcess.bpmn20.xml");
		pf.setDeploymentResources(new Resource[]{resource});
//		pf.setCustomSessionFactories(customSessionFactories);
		return pf;
	}
}
```

3. 不使用官方的用户角色，自定义实现
* activit6中的用户文档有误，具体可参考https://github.com/Activiti/Activiti/issues/1695
```java
@Component
public class MyGroupManager implements GroupDataManager {

	@Autowired
	private ActivitiGroupDao ago;
	...
	}
```
```java
@Component
public class MyUserManager implements UserDataManager {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ActivitiUserDao ao;
	'''
	}
```
* 详细可参考项目代码
* 将上述自定义实现的部分配置添加到上述的processEngineConfiguration中

4. 配置完成后使用eclipse bpmn插件完成bpmn文件后。可修改后缀名成为bpmn20.xml即可部署启动
--- 
* 以下为部分操作的api具体可参照项目源码MyActivitiService
```java
@Service
public class MyActivitiService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;
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
    
    public void claim(String taskId,String userName){   
    	taskService.claim(taskId, userName);
    }   
}

```
** 说明：**
```
1）在类中使用delegateTask.addCandidateUser(userId);的方式分配组任务的办理人，此时郭靖和黄蓉是下一个任务的办理人。

2）通过processEngine.getTaskService().claim(taskId,userId);将组任务分配给个人任务，也叫认领任务，
即指定某人去办理这个任务，此时有郭靖去办理任务。

注意：认领任务的时候，可以是组任务成员中的人，也可以不是组成员中的人。此时通过type的类型

为participant来指定任务办理人。

3）addCandidateUser()即向组任务添加成员，deleteCandidateUser()即删除组任务成员。

4）在开发中，可以将每一个任务的办理人规定好，例如张三的领导是李四和王五，这样张三

提交任务，由李四或者王五去查询组任务，可以看到对应张三的申请，李四或者王五在通过任务认领（claim）

的方式，由某个人去完成这次任务。

5）将组任务指定个人任务(拾取任务)
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
```
6. 启动项目后进行通过api测试MyTestController具体不在叙述

