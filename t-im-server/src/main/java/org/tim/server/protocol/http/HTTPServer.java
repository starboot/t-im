package org.tim.server.protocol.http;


import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.server.protocol.IMServer;


/**
 * Created by DELL(mxd) on 2021/12/24 14:45
 */
public class HTTPServer extends IMServer {


    private static final Logger log = LoggerFactory.getLogger(HTTPServer.class);

    private static HTTPServer httpServer;

    private HTTPServer() {
    }

    public synchronized static HTTPServer getInstance() {
        if (ObjectUtil.isNull(httpServer)){
            httpServer = new HTTPServer();
        }
        return httpServer;
    }

    @Override
    public void start() throws Exception {
        HttpServerInit.init();
    }
}
