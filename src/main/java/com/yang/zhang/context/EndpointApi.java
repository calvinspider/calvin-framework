package com.yang.zhang.context;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yang.zhang.enums.RequestMethod;

/**
 * Created by zhangyang56 on 2020/1/3.
 */
public class EndpointApi {

    private String prefixUrl;
    private String suffixUrl;
    private RequestMethod requestMethod;
    private Object originClass;
    private Method targetMethod;

    public EndpointApi(String prefixUrl, String suffixUrl, RequestMethod requestMethod, Object originClass,
                       Method targetMethod) {
        this.prefixUrl = prefixUrl;
        this.suffixUrl = suffixUrl;
        this.requestMethod = requestMethod;
        this.originClass = originClass;
        this.targetMethod = targetMethod;
    }

    public void invoke(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object object = this.getTargetMethod().invoke(this.getOriginClass());
            response.getWriter().write(JSON.toJSONString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl) {
        this.prefixUrl = prefixUrl;
    }

    public String getSuffixUrl() {
        return suffixUrl;
    }

    public void setSuffixUrl(String suffixUrl) {
        this.suffixUrl = suffixUrl;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Object getOriginClass() {
        return originClass;
    }

    public void setOriginClass(Object originClass) {
        this.originClass = originClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }
}
