package cn.starboot.tim.server.protocol;

import cn.hutool.core.lang.Singleton;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.server.cluster.ClusterHelper;
import cn.starboot.tim.server.cluster.ICluster;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

/**
 * Created by DELL(mxd) on 2021/12/24 13:05
 */
public abstract class IMServer {

    private static final Logger log = LoggerFactory.getLogger(IMServer.class);

    /**
     * 心跳超时时间，单位：毫秒
     */
    public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

    /**
     * 开启持久化
     */
    public static final boolean isStore;

    /**
     * 持久化助手
     */
//    public static final TIMCacheHelper cacheHelper;

    /**
     * 集群助手
     */
    public static final ICluster clusterHelper;

    /**
     * 包解析处理器
     */
//    public static final HandlerProcessor handlerProcessor;

    /**
     * 开启SSL
     */
    public static final boolean isUseSSL;

    /**
     * 心跳
     */
    public static final boolean heartbeat;

    /**
     * 开启集群
     */
    public static final boolean cluster;

    /**
     * 本机IP
     */
    public static final String ip;

    /**
     * 本地tcp端口
     */
    public static final int port;

    static {
        TimBanner timBanner = new TimBanner();
        timBanner.printBanner(System.out);
        isStore = false;
        if (isStore) {
            log.info("用户选择开启持久化，redis默认连接http://127.0.0.1:6379");
            try {
//                Config redisConfig = new Config();
//                redisConfig.useSingleServer().setTimeout(1000000).setAddress("redis://127.0.0.1:6379").setDatabase(0);
                /*
                哨兵
                Config config = new Config();
                config.useSentinelServers().addSentinelAddress(
                        "redis://172.29.3.245:26378","redis://172.29.3.245:26379", "redis://172.29.3.245:26380")
                        .setMasterName("mymaster")
                        .setPassword("a123456").setDatabase(0);
                集群
                Config config = new Config();
                config.useClusterServers().addNodeAddress(
                        "redis://172.29.3.245:6375","redis://172.29.3.245:6376", "redis://172.29.3.245:6377",
                        "redis://172.29.3.245:6378","redis://172.29.3.245:6379", "redis://172.29.3.245:6380")
                        .setPassword("a123456").setScanInterval(5000);
                 */
//                RedissonClient redissonClient = Redisson.create(redisConfig);
                // timeToLiveSeconds : 生存时间   timeToIdleSeconds 计时器，  设置一个即可
//                Long aLong = P.getLong("tim.redis.timeout");
                // 注册TIMServer
//                RedisCache.register(redissonClient, "TIMServer", aLong, null);
//                RedisCache.register(redissonClient, "TIMCluster", aLong, null);
//                RedisCache.register(redissonClient, "TIMMessage", aLong, null);
//                RedisCache.register(redissonClient, "TIMOfflineMessage", aLong, null);
//                RedisCache.register(redissonClient, "TIMGroupMessage", aLong, null);
            } catch (RedisConnectionException redisConnectionException) {
                log.error("Redis连接失败，持久化失效，请先启动本地Redis服务");
            }
//            cacheHelper = new RedisTIMCacheHelper();
        }else {
//            cacheHelper = null;
        }
        isUseSSL = false;
        heartbeat = false;
//        handlerProcessor = new TIMHandlerProcessorImpl();
        cluster = false;
        port = 8888;
        if (IMServer.cluster && IMServer.isStore) {
            // 配置集群助手  单例模式
            clusterHelper = Singleton.get(ClusterHelper.class);
        }else {
            clusterHelper = null;
        }
        ip = "127.0.0.1";
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



    public abstract void start() throws Exception;



    private static boolean filterLog(String x){
        return x.contains("---------------------------------------------------------------------------------------");
    }

}
