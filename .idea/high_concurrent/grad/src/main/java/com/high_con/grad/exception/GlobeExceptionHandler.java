package com.high_con.grad.exception;


import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobeExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){

        if(e instanceof GlobeException){
            GlobeException ex = (GlobeException) e;
            return Result.error(ex.getCodeMsg());
        }
        else if(e instanceof BindException){
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();

            return Result.error(CodeMsg.BIND_ERR.fillA(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERR);
        }

    }
}
