package org.tim.server.protocol.http;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.server.protocol.IMServer;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 14:45
 */
public class HTTPServer extends IMServer {


    private static final Logger log = LoggerFactory.getLogger(HTTPServer.class);

    private static HTTPServer httpServer;

    private HTTPServer() {
    }

    public static HTTPServer getInstance() {
        if (Objects.isNull(httpServer)){
            httpServer = new HTTPServer();
        }
        return httpServer;
    }

    public void start() throws Exception {
        HttpServerInit.init();
    }
}
