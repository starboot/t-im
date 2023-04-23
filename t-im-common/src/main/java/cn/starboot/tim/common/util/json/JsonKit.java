package cn.starboot.tim.common.util.json;

import cn.starboot.tim.common.util.json.serializer.IJsonSerializer;

/**
 * Created by DELL(mxd) on 2021/12/24 19:10
 */
public class JsonKit {

    private static final IJsonSerializer jsonSerializer = JSONFactory.createSerializer();

    public static String toJSONString(Object object) {
        return jsonSerializer.toJSON(object);
    }

    public static byte[] toJsonBytes(Object object) {
        return jsonSerializer.toByte(object);
    }

    public static <T> T toBean(String text, Class<T> chatBodyClass) {
        return jsonSerializer.toObject(text, chatBodyClass);
    }

    public static <T> T toBean(byte[] bytes, Class<T> clazz) {
        return jsonSerializer.toObject(bytes, clazz);
    }
}
