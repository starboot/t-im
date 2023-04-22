package cn.starboot.tim.server;

import cn.hutool.core.lang.Singleton;
import org.tim.server.TIMServerStarter;


/**
 * Created by DELL(mxd) on 2021/12/25 16:36
 */
public class TIMServerStarterDemo {


    public static void main(String[] args) {
        // 启动方式：推荐使用
        TIMServerStarter timServerStarter = Singleton.get(TIMServerStarter.class);
        // 初始化服务器
        timServerStarter.init();
        // 启动
        timServerStarter.start();

        // 备选
//        TIMServerStarter instance = TIMServerStarter.getInstance();

    }



}
