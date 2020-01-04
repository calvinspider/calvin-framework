package com.yang.zhang.endpoint;

import com.yang.zhang.annotation.ApiPath;
import com.yang.zhang.annotation.Autowire;
import com.yang.zhang.annotation.Endpoint;
import com.yang.zhang.enums.RequestMethod;

/**
 * Created by zhangyang56 on 2020/1/2.
 */
@Endpoint(url = "/test")
public class TestEndpoint {

    @Autowire
    private TestService testService;

    @ApiPath(url = "/api1", method = RequestMethod.GET)
    public String hello() {
        testService.hello();
        return "hello word";
    }

    @ApiPath(url = "/api2", method = RequestMethod.GET)
    public String hello2() {
        return "hello word2";
    }

    @ApiPath(url = "/api3", method = RequestMethod.GET)
    public String hello3() {
        return "hello word3";
    }

}
