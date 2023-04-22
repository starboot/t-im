package cn.starboot.tim.common.util.json.serializer;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/24 14:03
 */
public interface IJsonSerializer {
    /*
     * 序列化某个对象
     * */
    <T> String toJSON(T t);

    /*
     * 反序列化
     * */
    <T> T toObject(String json, Class<T> clazz);

    <T> T toObject(byte[] bytes, Class<T> clazz);

    /*
     * 序列化成数组
     * */
    <T> List<T> toArray(String json, Class<T> clazz);

    <T> byte[] toByte(T t);

}
