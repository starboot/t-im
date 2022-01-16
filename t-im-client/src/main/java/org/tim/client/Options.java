package org.tim.client;

import org.tio.core.Node;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * TIMClient 配置类
 *
 * Created by DELL(mxd) on 2022/1/6 13:31
 */
public class Options {

    /**
     * 服务器的IP与端口，使用：new Node("IP", "port");
     */
    private Node node;

    /**
     * 同步消息等待时间, 默认8秒
     */
    private long timeout = 8000L;

    /**
     * 线程池
     */
    private ThreadPoolExecutor executor;

    /**
     * 同步线程池
     */
    private SynThreadPoolExecutor synExecutor;

    public Options(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public SynThreadPoolExecutor getSynExecutor() {
        return synExecutor;
    }

    public void setSynExecutor(SynThreadPoolExecutor synExecutor) {
        this.synExecutor = synExecutor;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
