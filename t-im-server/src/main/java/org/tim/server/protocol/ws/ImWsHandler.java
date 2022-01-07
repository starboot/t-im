package org.tim.server.protocol.ws;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.packets.Command;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.command.CommandManager;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * Created by DELL(mxd) on 2021/12/23 20:40
 */
public class ImWsHandler implements IWsMsgHandler {

    private static final Logger log = LoggerFactory.getLogger(ImWsHandler.class);

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String username = httpRequest.getParam("username");
        String password = httpRequest.getParam("password");
        log.debug(username + "-" + password);
        Tio.bindUser(channelContext, username);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        Tio.bindGroup(channelContext, "100");
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        log.info("websocket server received bytes");
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(s);
        String cmd = jsonObject.getStr("cmd");
        AbstractCmdHandler command = CommandManager.getCommand(Command.valueOf(Integer.parseInt(cmd)));
        command.handler(convert(s),channelContext);
        return null;
    }

    private WsResponse convert(String data){
        WsResponse wsResponse = new WsResponse();
        wsResponse.setWsOpcode(Opcode.TEXT);
        wsResponse.setBody(data.getBytes());
        wsResponse.setWsBodyText(data);
        return wsResponse;
    }


}
