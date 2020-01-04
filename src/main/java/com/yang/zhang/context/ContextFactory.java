package com.yang.zhang.context;

/**
 * Created by zhangyang56 on 2020/1/4.
 */
public class ContextFactory {

    private static Context context;

    public static Context getOrCreate() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

}
