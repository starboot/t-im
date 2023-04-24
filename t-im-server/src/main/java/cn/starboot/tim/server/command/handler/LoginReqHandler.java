package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImStatus;
import cn.starboot.tim.common.entity.Group;
import cn.starboot.tim.common.entity.User;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.UserStatusType;
import cn.starboot.tim.common.packet.proto.ImStatusPacketProto;
import cn.starboot.tim.common.packet.proto.LoginPacketProto;
import cn.starboot.tim.server.command.CommandManager;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import cn.starboot.tim.server.protocol.IMServer;
import cn.starboot.tim.server.protocol.tcp.TCPSocketServer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/25 15:01
 */
public class LoginReqHandler extends ServerAbstractCmdHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginReqHandler.class);

    @Override
    public ReqCommandType command() {
        return ReqCommandType.COMMAND_LOGIN_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
        LoginPacketProto.LoginPacket loginPacket = LoginPacketProto.LoginPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(loginPacket)) {
            log.error("消息包格式化出错");
            return null;
        }
        // 构造登录成功消息包
        ImStatusPacketProto.ImStatusPacket.Builder builder = ImStatusPacketProto.ImStatusPacket.newBuilder();
        ImStatusPacketProto.ImStatusPacket imStatusPacket = builder.setStatus(ImStatus.C10007.getCode())
                .setDescription(ImStatus.C10007.getDescription())
                .setText(ImStatus.C10007.getText())
                .build();
        User user = getUserByProcessor(channelContext, loginPacket, imStatusPacket);
        if (user != null && ObjectUtil.isNotEmpty(user) && user.getUserId() != null) {
//            TIM.bindUser(channelContext, user.getUserId());
            //初始化绑定或者解绑群组;
            initGroup(channelContext, user);
//            LoginRespBody respBody = new LoginRespBody(ImStatus.C10007, user, "t-im token");
//            respBody.setId("1");
//            respBody.setSyn(false);
//            Tio.send(channelContext, new ImPacket(Command.COMMAND_LOGIN_RESP, respBody.toByte()));
            // 用户注册进集群
            if (user.getUserId().contains(":")) {
                // 该登录用户为服务器 不注册进集群
//                TIM.bindGroup(channelContext, "clusterGroup");
                return null;
            }
            if (IMServer.cluster && IMServer.isStore) {
                // 将用户注册进集群中间件
//                IMServer.clusterHelper.pushCluster(Long.decode(user.getUserId()),
//                        IMServer.ip + ":" + IMServer.port);
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
    private User getUserByProcessor(ImChannelContext imChannelContext, LoginPacketProto.LoginPacket loginReqBody, ImStatusPacketProto.ImStatusPacket loginRespBody) {
        if (ObjectUtil.isEmpty(loginRespBody) || loginRespBody.getStatus() != ImStatus.C10007.getCode()) {
            log.error("login failed, userId:{}, password:{}", loginReqBody.getUserId(), loginReqBody.getToken());
            return null;
        }
        return User.newBuilder()
                .userId(loginReqBody.getUserId())
                .status(UserStatusType.ONLINE.getStatus())
                .nick("t-im user")
                .sign("让天下没有难开发的即时通讯---米")
                .addFriend(User.newBuilder()
                        .userId("15511090451")
                        .nick("t-im author")
                        .build())
                .addGroup(Group.newBuilder()
                        .groupId("100")
                        .name("t-im group with organization")
                        .build())
                .build();
    }

    /**
     * 初始化绑定或者解绑群组;
     */
    public void initGroup(ImChannelContext imChannelContext , User user) {
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
//            ImPacket groupPacket = new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, JsonKit.toJsonBytes(group));
            try {
                BindReqHandler bindReqHandler = CommandManager.getCommand(ReqCommandType.COMMAND_BIND_REQ, BindReqHandler.class);
                if (bindReqHandler != null) {
//                    joinGroupReqHandler.handler(groupPacket, imChannelContext);
                }
            } catch (Exception e) {
                log.error("JoinGroupReqHandler is not contain");
            }
        }
    }
}
