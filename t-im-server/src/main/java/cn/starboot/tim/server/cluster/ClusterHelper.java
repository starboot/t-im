package cn.starboot.tim.server.cluster;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.tio.utils.cache.ICache;
import org.tio.utils.cache.redis.RedisCache;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2022/1/1 19:13
 */
public class ClusterHelper implements ICluster{

    private static final ICache cache;

    private static final RedissonClient redissonClient;

    static {
        cache = RedisCache.getCache("TIMCluster");
        redissonClient = RedisCache.getCache("TIMCluster").getRedisson();
        cache.put("clusterData", ClusterData.getInstance());
    }

    @Override
    public void registerCluster(String ip, int port) {
        // 使用分布式锁进行资源互斥访问
        RLock lock = redissonClient.getLock("clusterData");
        try {
            // 如果某一服务器突然宕机5秒后还可以自动释放资源
            lock.lock(5L, TimeUnit.SECONDS);
            ClusterData data = cache.get("clusterData", ClusterData.class);
            data.list.add(ip + ":" + port);
            cache.put("clusterData", data);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    @Override
    public ClusterData getCluster() {
        return cache.get("clusterData", ClusterData.class);
    }

    @Override
    public void pushCluster(Long userId, String addr) {
        RLock lock = redissonClient.getLock("clusterData");
        try {
            // 如果某一服务器突然宕机5秒后还可以自动释放资源
            lock.lock(5L, TimeUnit.SECONDS);
            ClusterData data = cache.get("clusterData", ClusterData.class);
            data.map.put(userId, addr);
            cache.put("clusterData", data);
        } finally {
            // 释放锁
            lock.unlock();
        }

    }
}
