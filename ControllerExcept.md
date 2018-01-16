## spring boot 简化了很多spring操作，但是有时候除了问题也会让人一头雾水。本项目单纯对springboot
* spring 处理异常
```java
@RestController
public class TestContronller {
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Spitter spitterById(@PathVariable long id){
        Spitter spitter = new Spitter(2,"xxx");
        if(null != spitter){
            throw new SpitterNotFound(id);
        }
        return spitter;
    }
}

```
error信息bean
```java
public class Error {
    private long code;
    private String Message;
    public Error(){}
    public Error(long code,String Message){
        this.code = code;
        this.Message = Message;
    }
    public long getCode() {
        return code;
    }
    }
```
自定义异常捕获后调用处理
```java
public class SpitterNotFound extends  RuntimeException {
    private long id;
    public SpitterNotFound(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
```
* 出现异常后使用下面的处理
```java
 @ExceptionHandler(SpitterNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error spnotfound(SpitterNotFound e){
        long id = e.getId();
        return new Error(4,"message");
    }
```