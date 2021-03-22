package com.cjt.test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(value = GeneralException.class)
//    public ApiResultVO jsonErrorHandler(GeneralException e) {
//        log.error("{} was thrown", e.getClass(), e);
//        return ApiResult.error(e.getCode(), e.getInfo());
//    }
//
//    @ExceptionHandler(value = org.springframework.validation.BindException.class)
//    public ApiResultVO bindException(org.springframework.validation.BindException e) {
//        final FieldError fieldError = e.getFieldError();
//        final String defaultMessage = fieldError.getDefaultMessage();
//        log.error("{} was thrown", e.getClass(), e);
//        return ApiResult.error("400", defaultMessage);
//    }
//
//    @ExceptionHandler(value = org.springframework.web.bind.MissingServletRequestParameterException.class)
//    public ApiResultVO missingServletRequestParameterException(org.springframework.web.bind.MissingServletRequestParameterException e) {
//        final String parameterName = e.getParameterName();
//        log.error("{} was thrown", e.getClass(), e);
//        return ApiResult.error("400", "缺少参数：" + parameterName);
//    }
//
//    @ExceptionHandler(value = UnauthenticatedException.class)
//    public ApiResultVO unauthenticatedExceptionException(UnauthenticatedException e) {
//        log.error("{} was thrown", e.getClass(), e);
//        return ApiResult.error("401", "用户未登录");
//    }
}
