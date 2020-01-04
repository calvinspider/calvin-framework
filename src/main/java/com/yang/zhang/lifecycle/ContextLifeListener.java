package com.yang.zhang.lifecycle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yang.zhang.context.Context;
import com.yang.zhang.context.ContextFactory;

/**
 * Created by zhangyang56 on 2020/1/4.
 */
public class ContextLifeListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextLifeListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.error("calvin application start init");
        try {
            Context context = ContextFactory.getOrCreate();
            context.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.error("calvin application end init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
