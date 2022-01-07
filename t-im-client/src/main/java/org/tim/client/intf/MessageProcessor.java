package org.tim.client.intf;

import org.tim.common.packets.ChatBody;
import org.tim.common.packets.UserMessageData;

import java.util.Set;

/**
 * Created by DELL(mxd) on 2022/1/6 13:55
 */
public interface MessageProcessor {

    void afterLogin();

    void OnMessage(ChatBody body);

    void authSuccess(String key);

    void getMessageDataAfter(String data);

    void getOnlineUserIdAfter(String ids);
}
