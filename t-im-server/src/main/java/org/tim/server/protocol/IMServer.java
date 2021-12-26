package org.tim.server.protocol;

import org.tim.common.banner.TimBanner;
import org.tim.server.cache.TIMCacheHelper;
import org.tim.server.command.handler.common.HandlerProcessor;
import org.tim.server.command.handler.common.TIMHandlerProcessorImpl;
import org.tim.server.helper.redis.RedisTIMCacheHelper;
import org.tio.utils.jfinal.P;

import java.io.PrintStream;

/**
 * Created by DELL(mxd) on 2021/12/24 13:05
 */
public class IMServer {

    /**
     * 心跳超时时间，单位：毫秒
     */
    public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

    public static final boolean isStore;

    public static final TIMCacheHelper cacheHelper;

    public static final HandlerProcessor handlerProcessor;

    public static final boolean isUseSSL;

    public static final boolean heartbeat;

    static {
        TimBanner timBanner = new TimBanner();
        timBanner.printBanner(System.out);
        isStore = P.getBoolean("tim.store");
        if (isStore) {
            cacheHelper = new RedisTIMCacheHelper();
        }else {
            cacheHelper = null;
        }
        isUseSSL = P.getBoolean("tim.use.ssl");
        heartbeat = P.getBoolean("tim.use.heartbeat");
        handlerProcessor = new TIMHandlerProcessorImpl();
        PrintStream ps = new PrintStream(System.out){
            @Override
            public void println(String x) {
                if(filterLog(x)){
                    return;
                }
                super.println(x);
            }
            @Override
            public void print(String s) {
                if(filterLog(s)){
                    return;
                }
                super.print(s);
            }
        };
        System.setOut(ps);
    }



    private static boolean filterLog(String x){
        return x.contains("---------------------------------------------------------------------------------------");
    }

}
