package com.bin.yang.exception;

import com.bin.yang.constant.ObjectRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/22 16:28
 * @Description:  异常处理类
 */
@ControllerAdvice()
@SuppressWarnings("rawtypes")
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    @ResponseBody
    private ObjectRestResponse illegalPostParamsExceptionHandler(BindException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuffer sb = new StringBuffer();
        errors.forEach(item -> sb.append(item.getDefaultMessage()).append(","));
        log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
        return new ObjectRestResponse().rel(false).msg(sb.substring(0, sb.length() - 1));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    private ObjectRestResponse illegalPostParamsExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuffer sb = new StringBuffer();
        errors.forEach(item -> sb.append(item.getDefaultMessage()).append(","));
        log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
        return new ObjectRestResponse().rel(false).msg(sb.substring(0, sb.length() - 1));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    private ObjectRestResponse illegalGetParamsExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        StringBuffer sb = new StringBuffer();
        set.forEach(item -> sb.append(item.getMessageTemplate()).append(","));
        log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
        return new ObjectRestResponse().rel(false).msg(sb.substring(0, sb.length() - 1));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    private ObjectRestResponse ExceptionHandlerAll(Exception e) {
        e.printStackTrace();
        return new ObjectRestResponse().rel(false).msg("服务器内部异常");
    }
}
