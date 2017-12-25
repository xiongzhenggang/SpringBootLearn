package com.xzg.cn.expxetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "my class not find")
public class MyClassNotFind extends  RuntimeException {
}
