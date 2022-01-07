package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.exception.ImException;
import org.tim.common.packets.*;
import org.tim.common.util.json.JsonKit;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.command.CommandManager;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

import java.util.List;
import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/25 15:01
 */
public class LoginReqHandler extends AbstractCmdHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginReqHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_LOGIN_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) throws ImException {
        LoginReqBody loginReqBody = IMServer.handlerProcessor.instanceofHandler(packet, LoginReqBody.class);
        if (ObjectUtil.isEmpty(loginReqBody)) {
            log.error("消息包格式化出错");
            return null;
        }
        LoginRespBody loginRespBody = LoginRespBody.success();
        User user = getUserByProcessor(channelContext, loginReqBody, loginRespBody);
        if (Objects.nonNull(user)){
            TIM.bindUser(channelContext, user.getUserId());
            //初始化绑定或者解绑群组;
            initGroup(channelContext, user);
            LoginRespBody respBody = new LoginRespBody(ImStatus.C10007, user, "t-im token");
            respBody.setId("1");
            Tio.send(channelContext, new ImPacket(Command.COMMAND_LOGIN_RESP, respBody.toByte()));
            // 用户注册进集群
            if (user.getUserId().contains(":")) {
                // 该登录用户为服务器 不注册进集群
                TIM.bindGroup(channelContext, "clusterGroup");
                return null;
            }
            if (IMServer.cluster && IMServer.isStore) {
                // 将用户注册进集群中间件
                IMServer.clusterHelper.pushCluster(Long.decode(user.getUserId()),
                        IMServer.ip + ":" + IMServer.port);
            }

        }

        return null;
    }

    /**
     * 根据用户配置的自定义登录处理器获取业务组装的User信息
     * @param imChannelContext 通道上下文
     * @param loginReqBody 登录请求体
     * @param loginRespBody 登录响应体
     * @return 用户组装的User信息
     */
    private User getUserByProcessor(ChannelContext imChannelContext, LoginReqBody loginReqBody, LoginRespBody loginRespBody) {
        if (Objects.isNull(loginRespBody) || loginRespBody.getCode() != ImStatus.C10007.getCode()) {
            log.error("login failed, userId:{}, password:{}", loginReqBody.getUserId(), loginReqBody.getPassword());
            return null;
        }
        return User.newBuilder()
                .userId(loginReqBody.getUserId())
                .status(UserStatusType.ONLINE.getStatus())
                .nick("t-im user")
                .sign("让天下没有难开发的即时通讯---米")
                .addFriend(User.newBuilder()
                        .userId("1551109")
                        .nick("tcp端")
                        .build())
                .addGroup(Group.newBuilder()
                        .groupId("100")
                        .name("t-im 官方群")
                        .build())
                .build();
    }

    /**
     * 初始化绑定或者解绑群组;
     */
    public void initGroup(ChannelContext imChannelContext , User user) {
        String userId = user.getUserId();
        List<Group> groups = user.getGroups();
        if(groups == null || groups.isEmpty()) {
            return;
        }
        boolean isStore = TCPSocketServer.isStore;
        List<String> groupIds = null;
        if(isStore){
            log.debug("从存储介质中读取groupIds");
        }
        //绑定群组
        for(Group group : groups){
            ImPacket groupPacket = new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, JsonKit.toJsonBytes(group));
            try {
                JoinGroupReqHandler joinGroupReqHandler = CommandManager.getCommand(Command.COMMAND_JOIN_GROUP_REQ, JoinGroupReqHandler.class);
                assert joinGroupReqHandler != null;
                joinGroupReqHandler.handler(groupPacket, imChannelContext);
            } catch (Exception e) {
                log.error("JoinGroupReqHandler is not contain");
            }
        }
    }
}
