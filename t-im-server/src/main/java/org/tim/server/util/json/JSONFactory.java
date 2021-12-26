package org.tim.server.util.json;

import org.tim.server.util.json.serializer.FastJsonSerializer;
import org.tim.server.util.json.serializer.IJsonSerializer;

/**
 * Created by DELL(mxd) on 2021/12/24 14:02
 */
public class JSONFactory {

    //创建序列化器
    public static IJsonSerializer createSerializer(){
        return new FastJsonSerializer();
    }
}
