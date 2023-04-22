package cn.starboot.tim.common.packets;

import cn.starboot.tim.common.util.json.JsonKit;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by DELL(mxd) on 2021/12/22 11:57
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -1042446381570777749L;
    /**
     * 消息创建时间
     * new Date().getTime()
     */
    protected Long createTime;
    /**
     * 消息id，全局唯一, 同步消息的唯一标识
     * UUIDSessionIdGenerator.instance.sessionId(null)
     */
    protected String id;
    /**
     * 该消息是否需要同步  true:返回对方ack， false 则为别人ack回复自己的，此时需要id存在， 如果id不存在 该消息为非同步消息
     */
    protected boolean isSyn;
    /**
     * 消息cmd命令码
     */
    protected Integer cmd;
    /**
     * 扩展参数字段
     */
    protected JSONObject extras;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSyn() {
        return isSyn;
    }

    public void setSyn(boolean syn) {
        isSyn = syn;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public JSONObject getExtras() {
        return extras;
    }

    public void setExtras(JSONObject extras) {
        this.extras = extras;
    }

    public String toJsonString() {
        return JsonKit.toJSONString(this);
    }

    public byte[] toByte() {
        return JsonKit.toJsonBytes(this);
    }

    public abstract static class Builder<T extends Message, B extends Builder<T, B>> {
        /**
         * 消息创建时间
         */
        protected Long createTime;
        /**
         * 消息id，全局唯一
         */
        protected String id;
        /**
         * 该消息是否需要同步
         */
        protected boolean isSyn;
        /**
         * 消息cmd命令;
         */
        protected Integer cmd;
        /**
         * 扩展字段;
         */
        protected JSONObject extras;

        private B theBuilder = this.getThis();

        /**
         * 供子类获取自身builder抽象类;
         *
         * @return
         */
        protected abstract B getThis();

        public B setCreateTime(Long createTime) {
            this.createTime = createTime;
            return theBuilder;
        }

        public B setId(String id) {
            this.id = id;
            return theBuilder;
        }

        public B setCmd(Integer cmd) {
            this.cmd = cmd;
            return theBuilder;
        }

        public B addExtra(String key, Object value) {
            if (null == value) {
                return theBuilder;
            } else {
                if (null == extras) {
                    this.extras = new JSONObject();
                }
                this.extras.put(key, value);
                return theBuilder;
            }
        }

        public B setIsSyn(boolean b) {
            this.isSyn = b;
            return theBuilder;
        }

        /**
         * 供子类实现的抽象构建对象
         *
         * @return
         */
        public abstract T build();
    }

    @Override
    public String toString() {
        return "Message{" +
                "createTime=" + createTime +
                ", id='" + id + '\'' +
                ", cmd=" + cmd +
                ", extras=" + extras +
                '}';
    }
}
