package com.yang.zhang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.zhang.enums.RequestMethod;
import com.yang.zhang.handler.RequestHandler;

/**
 * Created by zhangyang56 on 2020/1/2.
 */
public class DistributeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(RequestMethod.GET, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(RequestMethod.POST, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(RequestMethod.PUT, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchRequest(RequestMethod.DELETE, req, resp);
    }

    private void dispatchRequest(RequestMethod method, HttpServletRequest request, HttpServletResponse response) {
        RequestHandler.dispatchRequest(method, request, response);
    }
}
