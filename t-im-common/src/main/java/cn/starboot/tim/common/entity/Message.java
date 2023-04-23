package cn.starboot.tim.common.entity;

import cn.starboot.tim.common.util.json.JsonKit;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by DELL(mxd) on 2021/12/22 11:57
 */
public class Message implements Serializable {

    /**
     * 自行添加UID
     */
    private static final long serialVersionUID = -2064012869079283684L;

    /**
     * 扩展参数字段
     */
    protected JSONObject extras;

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

        protected JSONObject extras;

        private final B theBuilder = this.getThis();

        protected abstract B getThis();

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

        public abstract T build();
    }

    @Override
    public String toString() {
        return "Message{" +
                "extras=" + extras +
                '}';
    }
}
