package com.yang.zhang.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.yang.zhang.annotation.ApiPath;
import com.yang.zhang.annotation.Autowire;
import com.yang.zhang.annotation.Endpoint;
import com.yang.zhang.annotation.Service;
import com.yang.zhang.enums.RequestMethod;

/**
 * Created by zhangyang56 on 2020/1/2.
 */
public class Context {

    private Logger logger = LogManager.getLogger(Context.class);
    private Map<String, EndpointApi> endpointApiMap = new HashMap<>();
    private Map<String, Object> serviceMap = new HashMap<>();

    private Reflections reflections =
            new Reflections("com.yang.zhang", new TypeAnnotationsScanner(), new FieldAnnotationsScanner(),
                    new MethodAnnotationsScanner(), new SubTypesScanner());

    public void init() throws Exception {
        initService();
        initEndpoint();
        doInject();
    }

    private void initService() {
        logger.error("start init service");
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        try {
            for (Class<?> kclass : services) {
                Object service = kclass.newInstance();
                Service annotation = kclass.getAnnotation(Service.class);
                if (annotation != null) {
                    String serviceName = annotation.name();
                    serviceMap.put(serviceName, service);
                    logger.error("parse service {} {}", serviceName, kclass.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.error("end init service");
    }

    private void initEndpoint() throws Exception {
        Set<Class<?>> endpoints = reflections.getTypesAnnotatedWith(Endpoint.class);
        logger.error("start parse endpoint");
        for (Class<?> kclass : endpoints) {
            String prefixUrl = "/";
            Annotation annotation = kclass.getAnnotation(Endpoint.class);
            if (annotation != null) {
                prefixUrl = ((Endpoint) annotation).url();
            }
            Method[] methods = kclass.getMethods();
            for (Method method : methods) {
                annotation = method.getAnnotation(ApiPath.class);
                if (annotation != null) {
                    String suffixUrl = ((ApiPath) annotation).url();
                    RequestMethod requestMethod = ((ApiPath) annotation).method();
                    EndpointApi endpointApi =
                            new EndpointApi(prefixUrl, suffixUrl, requestMethod, kclass.newInstance(), method);
                    endpointApiMap.put(prefixUrl + suffixUrl, endpointApi);
                    logger.error("parse endpoint {} class {} method {}", prefixUrl + suffixUrl, kclass.getName(),
                            method.getName());
                }
            }
        }
        logger.error("end parse endpoint");
    }

    private void doInject() throws Exception {
        logger.error("start do inject");
        List<EndpointApi> endpoints = new ArrayList<>(endpointApiMap.values());
        List<Object> services = new ArrayList<>(serviceMap.values());
        for (EndpointApi api : endpoints) {
            Field[] fields = api.getOriginClass().getClass().getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            for (Field item : fieldList) {
                Annotation autowire = item.getAnnotation(Autowire.class);
                if (autowire != null) {
                    for (Object object : services) {
                        if (item.getType() == object.getClass()) {
                            item.setAccessible(true);
                            item.set(api.getOriginClass(), object);
                            logger.error("inject field {} {}", item.getName(), object);
                        }
                    }
                }
            }
        }
        logger.error("end do inject");
    }

    public Map<String, EndpointApi> getEndpointApiMap() {
        return endpointApiMap;
    }
}
