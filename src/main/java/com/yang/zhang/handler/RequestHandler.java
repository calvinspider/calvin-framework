package com.yang.zhang.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.zhang.context.ContextFactory;
import com.yang.zhang.context.EndpointApi;
import com.yang.zhang.enums.RequestMethod;

/**
 * Created by zhangyang56 on 2020/1/4.
 */
public class RequestHandler {

    public static void dispatchRequest(RequestMethod method, HttpServletRequest request, HttpServletResponse response) {
        EndpointApi api = matchUri(request.getRequestURI());
        if (api == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (!api.getRequestMethod().equals(method)) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        api.invoke(request, response);
    }

    private static EndpointApi matchUri(String requestURI) {
        Map<String, EndpointApi> endpointApiMap = ContextFactory.getOrCreate().getEndpointApiMap();
        return endpointApiMap.get(requestURI);
    }

}
