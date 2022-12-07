package com.shuishu.demo.jpa.common.config.exception;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:28
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：全局异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.get();


    @ResponseBody
    @ExceptionHandler(Exception.class)
    ApiResponse<String> handleException(Exception e) {
        log.info("null", e);
        e.printStackTrace();
        return ApiResponse.of(1, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    ApiResponse<String> handleBusinessException(BusinessException e) {
        e.printStackTrace();
        return ApiResponse.of(e.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    ApiResponse<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        e.printStackTrace();
        return ApiResponse.of(1, e.getParameterName());
    }

    private void printParameterMap() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Map<String, String[]> paramMap = request.getParameterMap();
            Map<String, String> newParamMap = convertRequestParamMap(paramMap);
            log.info(String.format("请求地址：%s", request.getRequestURL()));
            log.info(String.format("请求参数：%s", newParamMap.toString()));
        }
    }

    public static String errorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError error : list) {
                errorMessage.append(",").append(error.getField()).append(" : ").append(error.getDefaultMessage());
            }
        }
        return errorMessage.toString().replaceFirst(",", "");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        printParameterMap();
        return ApiResponse.of(1, errorMessage(e.getBindingResult()));
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    ApiResponse<String> handleBindException(BindException e) {
        printParameterMap();
        return ApiResponse.of(1, errorMessage(e.getBindingResult()));
    }

    public static Map<String, String> convertRequestParamMap(Map<String, String[]> paramMap) {
        Map<String, String> newParamMap = new HashMap<>(10);
        if (paramMap != null) {
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                newParamMap.put(key, values[0]);
            }
        }
        return newParamMap;
    }
}
