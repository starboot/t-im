## t-im 一款强大且轻量的IM服务器

[![输开源协议](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg "Apache")](https://www.apache.org/licenses/LICENSE-2.0)
## t-im简介

t-im通讯内核采用tio，t-im的设计思想(一套业务逻辑代码解决所有协议的业务逻辑)，
采用了t-io的websocket生态,服务器处理多协议是监听了多端口，与监听相同端口再做协议判别其实是殊途同归，
采用JAVA语言开发的轻量、高性能的IM服务器，Linux OS一核心CPU、2G内存可支持30万用户在线，
主要目标降低即时通讯门槛，快速打造低成本接入在线IM系统，
通过统一的的消息格式就可以实现不同协议间的消息发送，如内置(Http、Websocket、Tcp自定义IM协议)，
并提供通过http协议的api接口进行消息发送无需关心接收端属于什么协议，一套后端业务逻辑处理器搞定一切！  

## 主要特点
        1、高性能服务器，采用异步非阻塞AIO通讯
        2、轻量、可水平扩展
        3、支持集群多机部署
        4、支持SSL/TLS加密传输
        5、消息格式为(JSON)
        6、支持可插拔多种协议(Socket自定义IM协议、Websocket、Http)，各协议可分别独立部署。
        7、内置消息持久化(离线、历史、漫游)，保证消息可靠性，高性能存储
        8、提供springboot依赖，方便使用spring生态的便利性。
        9、部署简单，一键轻松启动。


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

 **6.获取用户在线信息请求消息结构** 
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
     "type":"消息类型(0:离线消息,1:历史消息)"
     "timelineId":"消息时间，如：2022-01-12，则为获取当天的离线、历史消息"
}
```
请求:COMMAND_GET_MESSAGE_REQ(19) 响应:COMMAND_GET_MESSAGE_RESP(20)

## 开始使用

### 引入服务器
maven 坐标，普通开发引入它即可
```text
<dependency>
    <groupId>io.github.mxd888</groupId>
    <artifactId>t-im-server</artifactId>
    <version>2.1.7.v20220120-RELEASE</version>
</dependency>
```
SpringBoot 开发：只需要引入springboot依赖，在启动类上添加注解@EnableIM，并将t-im-server-demo下的tim.properties文件拷贝至springboot项目的resources目录下
```text
<dependency>
    <groupId>io.github.mxd888</groupId>
    <artifactId>t-im-server-spring-boot-starter</artifactId>
    <version>2.1.7.v20220120-RELEASE</version>
</dependency>
```

### 引入客户端
maven 坐标
```text
<dependency>
    <groupId>io.github.mxd888</groupId>
    <artifactId>t-im-client</artifactId>
    <version>2.1.7.v20220120-RELEASE</version>
</dependency>
```

## 本项目提供的UI
### Android 
以开源，请访问： https://gitee.com/mxd_2022/we-chat
![Image text](https://gitee.com/mxd_2022/we-chat/raw/master/images/1.jpg)
![Image text](https://gitee.com/mxd_2022/we-chat/raw/master/images/4.jpg)
![Image text](https://gitee.com/mxd_2022/we-chat/raw/master/images/7.png)
### 扫描下载APP
![Image text](https://gitee.com/mxd_2022/we-chat/raw/master/images/APP_Download.png)
### 本APP测试扫一扫功能专用二维码
![Image text](https://gitee.com/mxd_2022/we-chat/raw/master/images/测试扫一扫专用二维码.png)
## 联系方式

   官方QQ群号：867691377 
   <a target="_blank"  href="https://jq.qq.com/?_wv=1027&k=Gd6P6BcT">
   <img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="t-im" title="t-im"></a><br>

### 进群前先gitee上star ★
   请看完再进群，进群是为了相互交流技术，共同学习进步，设置进群问题是防止有某云服务器销售人员，或其他乱发广告的进入；

## 重要说明 ☆☆☆

   第一本项目完全开源免费，可以拥有其任何使用权，但不代表可以做非法乱纪的事情，本项目以质朴质简思想开发，意在打造最易读懂源码为主，并不是以高深的设计哲学为主，
   可用作学习或不重要的作业使用，切勿商用，商用出现后果概不负责！！！非常感谢配合

## 附上相关开源项目链接
   
   t-io：稳如泰山，性能炸裂的Java通讯框架，官网：  https://www.tiocloud.com/tio/  <br>  
   Hutool：一个小而全的Java工具类库，官网：  https://www.hutool.cn/   <br>  
   smart-socket：高性能Aio通讯，官网： https://smartboot.gitee.io/book/smart-socket/    <br>  
   J-IM：高性能IM，官网（暂时打不来，或许作者正在开发中，尽情期待）：  http://www.j-im.cn/ <br>  
   本项目如有侵犯到任何个人或组织的权益请联系邮箱：1191998028@qq.com （如有侵权请联系删除）