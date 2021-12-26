## t-im 一款强大且轻量的IM服务器

[![输开源协议](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg "Apache")](https://www.apache.org/licenses/LICENSE-2.0)
## t-im简介

t-im 是用JAVA语言开发的轻量、高性能、单机支持几十万至百万在线用户IM，主要目标降低即时通讯门槛，快速打造低成本接入在线IM系统，通过极简洁的消息格式就可以实现多端不同协议间的消息发送如内置(Http、Websocket、Tcp自定义IM协议)等，并提供通过http协议的api接口进行消息发送无需关心接收端属于什么协议，一个消息格式搞定一切！  

## 主要特点
        1、高性能(单机可支持几十万至百万人同时在线)
        2、轻量、可扩展性极强
        3、支持集群多机部署
        4、支持SSL/TLS加密传输
        5、消息格式极其简洁(JSON)
        6、一端口支持可插拔多种协议(Socket自定义IM协议、Websocket、Http),各协议可分别独立部署。
        7、内置消息持久化(离线、历史、漫游)，保证消息可靠性，高性能存储
        8、各种丰富的API接口。
        9、零成本部署，一键启动。


## 消息格式

 **1.聊天请求消息结构** 
 ```
{
    "from": "来源ID",
    "to": "目标ID",
    "cmd":"命令码(11)int类型",
    "createTime": "消息创建时间long类型",
    "msgType": "消息类型int类型(0:text、1:image、2:voice、3:vedio、4:music、5:news)",
    "chatType":"聊天类型int类型(0:未知,1:公聊,2:私聊)",
    "groupId":"群组id仅在chatType为(1)时需要,String类型",
    "content": "内容",
    "extras" : "扩展字段,JSON对象格式如：{'扩展字段名称':'扩展字段value'}"
}
```
请求:COMMAND_CHAT_REQ(11) 响应:COMMAND_CHAT_RESP(12)

 **2.鉴权请求消息结构** 
```
{
    "cmd":"命令码(3)int类型",
    "token": "校验码"
}
```
请求:COMMAND_AUTH_REQ(3) 响应:COMMAND_AUTH_RESP(4)

说明:请求:COMMAND_HANDSHAKE_REQ(1) 响应:COMMAND_HANDSHAKE_RESP(2)

 **3.登录请求消息结构** 
```
{
    "cmd":"命令码(5)int类型",
    "userId": "用户账号",
    "password": "密码",
    "token": "校验码(此字段可与userId、password共存,也可只选一种方式)"
}
```
请求:COMMAND_LOGIN_REQ(5) 响应:COMMAND_LOGIN_RESP(6)

 **4.心跳请求消息结构** 
```
{
    "cmd":"命令码(13)int类型",
    "hbbyte":"心跳1个字节"
}
```
请求:COMMAND_HEARTBEAT_REQ(13) 响应:COMMAND_HEARTBEAT_REQ(13)

 **5.关闭、退出请求消息结构** 
```
{
    "cmd":"命令码(14)int类型",
    "userId":"用户ID"
}
```
请求:COMMAND_CLOSE_REQ(14) 响应:无

 **6.获取用户信息请求消息结构** 
```
{
     "cmd":"命令码(17)int类型",
     "userId":"用户id(必填项)",
     "type":"获取类型(0:所有在线用户,1:所有离线线用户,2:所有用户[在线+离线])"
}
```
请求:COMMAND_GET_USER_REQ(17) 响应:COMMAND_GET_USER_RESP(18)

**7.获取持久化聊天消息(离线+历史+漫游)请求结构** 
```
{
     "cmd":"命令码(19)int类型",
     "fromUserId":"消息发送用户id(此字段必须与userId一起使用,获取双方聊天消息),非必填",
     "userId":"当前用户id(必填字段),当只有此字段时,type必须为0，意思是获取当前用户所有离线消息(好友+群组)",
     "groupId":"群组id(此字段必须与userId一起使用,获取当前用户指定群组聊天消息),非必填",
     "beginTime":"消息区间开始时间Date毫秒数double类型,非必填",
     "endTime":"消息区间结束时间Date毫秒数double类型,非必填",
     "offset":"分页偏移量int类型，类似Limit 0,10 中的0,非必填",
     "count":"显示消息数量,类似Limit 0,10 中的10,非必填",
     "type":"消息类型(0:离线消息,1:历史消息)"
}
```
请求:COMMAND_GET_MESSAGE_REQ(19) 响应:COMMAND_GET_MESSAGE_RESP(20)

## 开始使用

### 引入服务器
maven 坐标
```text
<dependency>
    <groupId>io.github.mxd888</groupId>
    <artifactId>t-im-server</artifactId>
    <version>1.0.0.v20211226-RELEASE</version>
</dependency>
```
### 引入客户端
maven 坐标
```text
<dependency>
    <groupId>io.github.mxd888</groupId>
    <artifactId>t-im-client</artifactId>
    <version>1.0.0.v20211226-RELEASE</version>
</dependency>
```

## 联系方式

   官方QQ群号：867691377 
   <a target="_blank"  href="https://jq.qq.com/?_wv=1027&k=Gd6P6BcT">
   <img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="t-im" title="t-im"></a><br>

### 进群前先gitee上star ★