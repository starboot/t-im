package org.tim.common.util.json;

import org.tim.common.packets.Message;
import org.tim.common.util.json.serializer.IJsonSerializer;

/**
 * Created by DELL(mxd) on 2021/12/24 19:10
 */
public class JsonKit {

    private static final IJsonSerializer jsonSerializer= JSONFactory.createSerializer();


    public static String toJSONString(Message message) {
        return jsonSerializer.toJSON(message);
    }

    public static byte[] toJsonBytes(Message message) {
        return jsonSerializer.toByte(message);
    }

    public static byte[] toJsonBytes(Object message) {
        return jsonSerializer.toByte(message);
    }

    public static <T> T toBean(String text, Class<T> chatBodyClass) {
        return jsonSerializer.toObject(text, chatBodyClass);
    }

    public static <T> T toBean(byte[] bytes, Class<T> clazz) {
        return jsonSerializer.toObject(bytes, clazz);
    }
}
