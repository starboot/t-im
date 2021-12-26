package org.tim.server.demo;

import cn.hutool.core.lang.Singleton;
import org.tim.common.packets.User;
import org.tim.server.TIMServerStarter;
import org.tim.server.protocol.IMServer;
import org.tio.utils.cache.ICache;
import org.tio.utils.cache.redis.RedisCache;

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

        if (IMServer.isStore) {
            init();
        }

    }

    public static void init() {
        ICache cache = RedisCache.getCache("TIMServer");
        cache.put("User", User.newBuilder().userId("userTest").nick("TIM").build());
        User user =(User) cache.get("User");
        System.out.println(user.getUserId() + "-" + user.getNick());
    }

}
