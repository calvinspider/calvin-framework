package com.yang.zhang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yang.zhang.enums.RequestMethod;

/**
 * Created by zhangyang56 on 2020/1/2.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPath {

    String url() default "/";

    RequestMethod method() default RequestMethod.GET;

}
