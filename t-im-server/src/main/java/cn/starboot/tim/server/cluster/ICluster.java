package cn.starboot.tim.server.cluster;

/**
 * Created by DELL(mxd) on 2022/1/1 17:00
 */
public interface ICluster {

    /**
     * 注册进集群
     * @param ip 宿主IP
     */
    void registerCluster(String ip, int port);

    /**
     * 获取集群信息
     * @return 集群信息类
     */
    ClusterData getCluster();

    void pushCluster(Long userId, String addr);


}
