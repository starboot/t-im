package org.tim.server.command.handler.userInfo;

import org.tim.common.packets.User;
import org.tim.common.packets.UserReqBody;
import org.tio.core.ChannelContext;

/**
 * Created by DELL(mxd) on 2021/12/25 19:57
 */
public interface IUserInfo {

    /**
     * 获取用户信息接口
     * @param userReqBody 请求体
     * @param channelContext 请求来源上下文
     * @return 平台用户状态信息
     */
    User getUserInfo (UserReqBody userReqBody, ChannelContext channelContext);
}
