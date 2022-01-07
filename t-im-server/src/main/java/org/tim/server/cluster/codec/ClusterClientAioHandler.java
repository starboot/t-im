package org.tim.server.cluster.codec;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.codec.TCPCodec;
import org.tim.common.packets.Command;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 用于服务器连接其他服务器
 * Created by DELL(mxd) on 2021/12/23 20:26
 */
public class ClusterClientAioHandler implements ClientAioHandler {

    private static final Logger log = LoggerFactory.getLogger(ClusterClientAioHandler.class);

    private static final TCPCodec tcpcodec = TCPCodec.getInstance();

    @Override
    public Packet decode(ByteBuffer byteBuffer, int i, int i1, int i2, ChannelContext channelContext) throws TioDecodeException {
        return tcpcodec.decode(byteBuffer, i, i1, i2, channelContext);
    }

    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {
        return tcpcodec.encode(packet, tioConfig, channelContext);
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) {

        // 收到集群消息
        if (packet instanceof ImPacket) {
            ImPacket packet1 = (ImPacket) packet;
            byte[] body = packet1.getBody();
            String s = new String(body, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONUtil.parseObj(s);
            String msg = jsonObject.getStr("msg");
            String token = jsonObject.getStr("token");
            if (StrUtil.isNotEmpty(msg) && StrUtil.isNotEmpty(token)) {
                if (msg.equals("ok 登录成功!")) {
                    log.debug("登录集群服务器成功");
                }else {
                    log.error("登录集群服务器失败");
                }
            }else {
                log.debug("收到集群发来的消息");
                Integer chatType = jsonObject.getInt("chatType");
                if (chatType == 2) {
                    // 私聊
                    String to = jsonObject.getStr("to");
                    TIM.sendToUser(to, packet);
                }else if (chatType == 1) {
                    // 公聊
                    String groupId = jsonObject.getStr("groupId");
                    TIM.sendToGroup(TCPSocketServer.getServerTioConfig(), groupId, packet, null, true);
                }

            }

        }

    }

    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        // 心跳包
        return new ImPacket(Command.COMMAND_HEARTBEAT_REQ, "{\"cmd\":13,\"hbbyte\":\"-127\"}".getBytes());
    }
}
