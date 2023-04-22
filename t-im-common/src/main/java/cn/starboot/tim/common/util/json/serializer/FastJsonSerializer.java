package cn.starboot.tim.common.util.json.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/24 14:03
 */
public class FastJsonSerializer implements IJsonSerializer {
    private static final Logger log = LoggerFactory.getLogger(FastJsonSerializer.class);

    /*
     * 使用fastjson序列化
     * */
    public <T> String toJSON(T t){
        if(t==null){
            return null;
        }
        //加上WriteMapNullValue 使得null值也被序列化
        return JSON.toJSONString(t, SerializerFeature.WriteMapNullValue);
    }

    /*
     * 使用fastjson反序列化
     * */
    public <T> T toObject(String json,Class<T> clazz){
        T t = null;
        try {
            t = JSON.parseObject(json,clazz);
        }catch (Exception e){
            log.error("fastJson转换格式出错");
            //   e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T> T toObject(byte[] bytes, Class<T> clazz) {
        try {
            if (bytes == null) {
                return null;
            }

            return JSON.parseObject(bytes, clazz);
        } catch (Throwable e) {
            log.error("json解析失败:\r\n{}", bytes);
            throw new RuntimeException(e);
        }
    }

    /*
     * tojsonArray
     * */
    public <T> List<T> toArray(String json, Class<T> clazz){
        try {
            return JSON.parseArray(json, clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> byte[] toByte(T t) {
        return toJSON(t).getBytes();
    }
}