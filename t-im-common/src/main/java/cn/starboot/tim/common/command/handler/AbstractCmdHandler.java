package cn.starboot.tim.common.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.factory.ImPacketFactory;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Objects;

/**
 * 抽象命令处理器
 * Created by DELL(mxd) on 2022/9/6 13:04
 */
public abstract class AbstractCmdHandler implements CmdHandler {

	private static final ImPacketFactory imPacketFactory = (timCommandType, data) -> ImPacket
			.newBuilder()
			.setTIMCommandType(timCommandType)
			.setData(data)
			.build();

	@Override
	public ImPacket handler(ImPacket imPacket, ImChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		byte[] data = imPacket.getData();
		return (Objects.isNull(data) || data.length == 0) ? null : handler(imChannelContext, imPacket);
	}

	protected abstract ImPacket handler(ImChannelContext imChannelContext, ImPacket imPacket) throws ImException, InvalidProtocolBufferException;

	/**
	 * 命令处理器自带发送方法
	 *
	 * @param imChannelContext 用户上下文信息
	 * @param TIMCommandType   协议命令码
	 * @param data             待发送数据
	 */
	protected abstract void send(ImChannelContext imChannelContext, TIMCommandType TIMCommandType, byte[] data);

	protected ImPacket getImPacket(TIMCommandType timCommandType, byte[] data) {
		return imPacketFactory.createImPacket(timCommandType, data);
	}
}
