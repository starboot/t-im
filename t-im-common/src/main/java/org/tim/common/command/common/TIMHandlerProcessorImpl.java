package org.tim.common.command.common;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.util.json.JsonKit;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpRequest;
import org.tio.websocket.common.WsResponse;


/**
 * Created by DELL(mxd) on 2021/12/25 20:41
 */
public class TIMHandlerProcessorImpl implements HandlerProcessor {

    private static final Logger log = LoggerFactory.getLogger(TIMHandlerProcessorImpl.class);

    @Override
    public <T> T instanceofHandler(Packet packet, Class<T> clazz) {
        T t = null;
        if (packet instanceof ImPacket) {
            // TCP 包
            ImPacket imPacket = (ImPacket) packet;
            t = JsonKit.toBean(imPacket.getBody(), clazz);
        } else if (packet instanceof WsResponse) {
            // WS 包
            WsResponse imPacket = (WsResponse) packet;
            t = JsonKit.toBean(imPacket.getBody(), clazz);
        }else if (packet instanceof HttpRequest) {
            // HTTP 包
            HttpRequest request = (HttpRequest) packet;
            log.debug(request.getBodyString());
        }
        if (ObjectUtil.isEmpty(t)) {
            return null;
        }
        return t;
    }
}
