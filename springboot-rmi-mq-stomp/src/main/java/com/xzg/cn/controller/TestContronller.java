package com.xzg.cn.controller;

import com.xzg.cn.entity.Spitter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ExceptionHandler(SpitterNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error spnotfound(SpitterNotFound e){
        long id = e.getId();
        return new Error(4,"message");
    }
}
