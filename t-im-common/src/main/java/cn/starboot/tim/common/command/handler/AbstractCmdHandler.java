package cn.starboot.tim.common.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import com.google.protobuf.ByteString;

/**
 * 抽象命令处理器
 * Created by DELL(mxd) on 2022/9/6 13:04
 */
public abstract class AbstractCmdHandler<C extends ImChannelContext<F>, F extends ImConfig<P>, P extends TIMProcessor> implements CmdHandler<C> {


	protected ImPacket getImPacket(ImChannelContext<F> imChannelContext,
								   TIMCommandType timCommandType) {
		return getImPacket(imChannelContext, timCommandType, null);
	}

	protected ImPacket getImPacket(ImChannelContext<F> imChannelContext,
								   TIMCommandType timCommandType,
								   byte[] data) {
		return imChannelContext.getConfig().getImPacketFactory().createImPacket(timCommandType, data);
	}

	protected RespPacketProto.RespPacket getRespPacket(TIMCommandType timCommandType, RespPacketProto.RespPacket.ImStatus imStatus, String msg) {
		return RespPacketProto.RespPacket.newBuilder().setCode(timCommandType.getCode()).setImStatus(imStatus).setMessage(msg).build();
	}

	protected boolean verify(boolean ...booleans) {
		boolean r = true;
		for (boolean b:
				booleans) {
			r = r && b;
		}
		return r;
	}
}
