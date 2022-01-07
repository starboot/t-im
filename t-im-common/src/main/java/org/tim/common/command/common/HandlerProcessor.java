package org.tim.common.command.common;

import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/25 20:40
 */
public interface HandlerProcessor {

    <T> T instanceofHandler(Packet packet, Class<T> clazz);
}
