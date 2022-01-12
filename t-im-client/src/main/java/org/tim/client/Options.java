package org.tim.client;

/**
 * Created by DELL(mxd) on 2022/1/6 13:31
 */
public class Options {
    private String ip;
    private int port;

    public Options(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Options() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
