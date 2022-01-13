package org.tim.client;

import org.tio.core.Node;

/**
 * Created by DELL(mxd) on 2022/1/6 13:31
 */
public class Options {

    private Node node;

    public Options(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
