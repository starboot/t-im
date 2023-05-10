package cn.starboot.tim.server.protocol;

import cn.hutool.core.lang.Singleton;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.server.cluster.ClusterHelper;
import cn.starboot.tim.server.cluster.ICluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by DELL(mxd) on 2021/12/24 13:05
 */
public abstract class IMServer {

    private static final Logger log = LoggerFactory.getLogger(IMServer.class);

    static {
        TimBanner timBanner = new TimBanner();
        timBanner.printBanner(System.out);
    }



    public abstract void start() throws Exception;

}
