package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.BindPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/25 15:29
 */
public class BindReqHandler extends AbstractServerCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(BindReqHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_BIND_REQ;
    }

	@Override
    public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
        BindPacketProto.BindPacket packet = BindPacketProto.BindPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(packet)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (StrUtil.isBlank(packet.getBindId())) {
            log.error("bindID is null,{}", imChannelContext);
//            ImStatusPacketProto.ImStatusPacket.Builder builder1 = ImStatusPacketProto.ImStatusPacket.newBuilder();
//            ImStatusPacketProto.ImStatusPacket build1 = builder1.setStatus(ImStatus.C10011.getCode())
//                    .setDescription(ImStatus.C10011.getDescription())
//                    .setText(ImStatus.C10011.getText())
//                    .build();
            imPacket.setTIMCommandType(TIMCommandType.COMMAND_BIND_REQ)
                    .setData(null);
            // build发送出去
//            TIM.send(channelContext, new ImPacket(Command.COMMAND_JOIN_GROUP_RESP, respBody.toByte()));
            return null;
        }
        if (packet.getOptionType() == BindPacketProto.BindPacket.OptionType.BIND) {
            // 进行绑定操作
            if (packet.getBindType() == BindPacketProto.BindPacket.BindType.USER) {
                // 进行用户绑定，一个用户可以登陆多个设备（用户与设备进行一对多绑定）
            } else if (packet.getBindType() == BindPacketProto.BindPacket.BindType.GROUP) {
                // 进行群组绑定，将用户绑定到指定群组
            } else if (packet.getBindType() == BindPacketProto.BindPacket.BindType.TOKEN) {
                // 进行token绑定
            } else {
                log.error("绑定处理器异常：未指明绑定分类");
            }
        } else if (packet.getOptionType() == BindPacketProto.BindPacket.OptionType.UNBIND) {
            // 进行解绑操作
            if (packet.getBindType() == BindPacketProto.BindPacket.BindType.USER) {
                // 进行用户解绑，一个用户可以登陆多个设备（用户与设备进行一对多绑定）
            } else if (packet.getBindType() == BindPacketProto.BindPacket.BindType.GROUP) {
                // 进行群组解绑，将用户绑定到指定群组
            } else if (packet.getBindType() == BindPacketProto.BindPacket.BindType.TOKEN) {
                // 进行token解绑
            } else {
                log.error("绑定处理器异常：未指明解绑分类");
            }
        } else {
            log.error("绑定处理器异常：未指明处理操作类型");
        }
        // 如果加入群聊前有群聊处理器 则在此之星
        //处理完处理器内容后
//        Tio.bindGroup(channelContext, groupId);
        return null;
    }

    private boolean handler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
    	switch (packet.getOptionType()) {
			case BIND: break;
			case UNBIND: break;
			default:
				TIMLogUtil.error(log, "绑定处理器异常：未指明处理操作类型");
		}
    	return true;
	}

	private boolean bindHandler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		// 进行绑定操作
    	switch (packet.getBindType()) {
			case ID: return TIM.bindId(packet.getBindId(), imChannelContext);
			case IP: return TIM.bindId(packet.getBindId(), imChannelContext);
			case BS_ID: break;
			case GROUP: return TIM.bindGroup(packet.getBindId(), imChannelContext);
			case USER: return TIM.bindUser(packet.getBindId(), imChannelContext);
			case TOKEN: break;
			default:
				TIMLogUtil.error(log, "");
		}
	}

	private boolean unbindHandler(BindPacketProto.BindPacket packet) {
		switch (packet.getBindType()) {
			case ID: break;
			case IP: break;
			case BS_ID: break;
			case GROUP: break;
			case USER: break;
			case TOKEN: break;
			default:
				TIMLogUtil.error(log, "");
		}
	}
}
