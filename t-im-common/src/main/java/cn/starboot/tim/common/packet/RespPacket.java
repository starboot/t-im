package cn.starboot.tim.common.packet;

import cn.starboot.tim.common.Status;

public class RespPacket extends ImPacket{

    private static final long serialVersionUID = -4381928468879751442L;

    /**
     * 响应状态码;
     */
    protected Integer code;

    /**
     * 响应状态信息提示;
     */
    protected String msg;

    /**
     * 消息创建时间
     */
    protected Long createTime;

    public RespPacket(CommandType commandType, byte[] data, Status status) {
        super(commandType, data);
        this.msg = status.getMsg();
        this.code = status.getCode();
        this.createTime = System.currentTimeMillis();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Long getCreateTime() {
        return createTime;
    }
}
