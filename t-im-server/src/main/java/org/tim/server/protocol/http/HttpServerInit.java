package org.tim.server.protocol.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ssl.SslUtils;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.server.ServerTioConfig;
import org.tio.utils.jfinal.P;

/**
 * Created by DELL(mxd) on 2021/12/24 15:02
 */
public class HttpServerInit {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(HttpServerInit.class);

    public static HttpConfig httpConfig;

    public static HttpRequestHandler requestHandler;

    public static HttpServerStarter httpServerStarter;

    public static ServerTioConfig serverTioConfig;

    public static void init() throws Exception {

        int port = P.getInt("http.port");//启动端口
        String pageRoot = P.get("http.page");//html/css/js等的根目录，支持classpath:，也支持绝对路径
        httpConfig = new HttpConfig(port, null, null, null);
        httpConfig.setPageRoot(pageRoot);
        httpConfig.setMaxLiveTimeOfStaticRes(P.getInt("http.maxLiveTimeOfStaticRes"));
        httpConfig.setPage404(P.get("http.404"));
        httpConfig.setPage500(P.get("http.500"));
        httpConfig.setUseSession(false);
        httpConfig.setCheckHost(false);

        requestHandler = new DefaultHttpRequestHandler(httpConfig, HTTPServer.class);//第二个参数也可以是数组
        httpServerStarter = new HttpServerStarter(httpConfig, requestHandler);
        serverTioConfig = httpServerStarter.getServerTioConfig();
        httpServerStarter.start(); //启动http服务器
        String protocol = SslUtils.isSsl(serverTioConfig) ? "https" : "http";
        if (log.isInfoEnabled()) {
            log.info("HTTP:访问地址:{}://127.0.0.1:{}/api/index", protocol, port);
            log.info("WebSocket:访问地址:{}://127.0.0.1:{}/api/websocket", protocol, port);
        }
    }

}
