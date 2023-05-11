package cn.starboot.tim.common.packet;

public interface ImPacketFactory<T> {

	ImPacket createImPacket(T t, byte[] data);
}
