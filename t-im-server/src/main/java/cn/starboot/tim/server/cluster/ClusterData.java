package cn.starboot.tim.server.cluster;

import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL(mxd) on 2022/1/1 18:18
 */
public class ClusterData implements Serializable {

    private static final long serialVersionUID = 4789578536900173799L;

    private static ClusterData cluster;

    // 集群序列
    public ArrayList<String> list;

    // 用户所在机器IP
    public Map<Long, String> map;


    public synchronized static ClusterData getInstance() {
        if (ObjectUtil.isEmpty(cluster)) {
            cluster = new ClusterData();
        }
        return cluster;
    }


    private ClusterData() {
        super();
        list = new ArrayList<>();
        map = new HashMap<>();
    }
}
