package JavaSDK;

import java.util.List;

/**
 * 传输底包
 * qq 代表了机器人的qq号
 */
abstract class PackBase {

    public long qq;
}

/**
 * Id:0 [插件]插件开始连接
 * Name：插件名字
 * Reg：注册事件的包id
 * Groups；监听QQ群列表,可以为null
 * QQs：监听QQ号列表,可以为null
 * RunQQ：插件运行的QQ号,可以为null
 */
class StartPack {

    public String Name;
    public List<Integer> Reg;
    public List<Long> Groups;
    public List<Long> QQs;
    public long RunQQ;
}

/**
 * Id:1 [机器人]图片上传前. 可以阻止上传（事件）
 * name：图片ID
 * id：发送给的号码
 */
class BeforeImageUploadPack extends PackBase {

    public String name;
    public long id;
}

/**
 * Id:2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
 * name：机器人的nick
 */
class BotAvatarChangedPack extends PackBase {

    public String name;
}

/**
 * Id:3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
 * name：权限名
 * id：群号
 */
class BotGroupPermissionChangePack extends PackBase {

    public String name;
    public long id;
}

/**
 * Id:4 [机器人]被邀请加入一个群（事件）
 * eventid：事件id
 * id：群号
 * name：邀请人nick
 * fid：邀请人QQ号
 */
class BotInvitedJoinGroupRequestEventPack extends PackBase{

    public long eventid;
    public long id;
    public String name;
    public long fid;
}

/**
 * Id:5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
 * id：群号
 */
class BotJoinGroupEventAPack extends PackBase{

    public long id;
}

/**
 * Id:6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
 * id：群号
 * fid：邀请人QQ
 * name：邀请人nick
 */
class BotJoinGroupEventBPack extends BotJoinGroupEventAPack{

    public String name;
    public long fid;
}

/**
 * Id:7 [机器人]主动退出一个群（事件）
 * id；群号
 */
class BotLeaveEventAPack extends PackBase{

    public long id;
}

/**
 * Id:8 [机器人]被管理员或群主踢出群（事件）
 * id：群号
 * fid：执行人QQ
 * name：执行人nick
 */
class BotLeaveEventBPack extends BotLeaveEventAPack{

    public String name;
    public long fid;
}

/**
 * Id:9 [机器人]被禁言（事件）
 * id：群号
 * name：执行人nick
 * fid：执行人QQ号
 * time：禁言时间
 */
class BotMuteEventPack extends PackBase{

    public long id;
    public String name;
    public long fid;
    public int time;
}

/**
 * Id:10 [机器人]主动离线（事件）
 * Id:12 [机器人]被服务器断开（事件）
 * Id:13 [机器人]因网络问题而掉线（事件）
 * message：离线原因
 *
 * ps: 这三个事件都是用的一个包,但是注册监听的时候还是要分开
 */
class BotOfflineEventAPack extends PackBase{

    public String message;
}

/**
 * Id:11 [机器人]被挤下线（事件）
 * title：标题
 * message：离线原因
 */
class BotOfflineEventBPack extends BotOfflineEventAPack {

    public String title;
}

/**
 * Id:14 [机器人]服务器主动要求更换另一个服务器（事件）
 * id：机器人QQ号
 */
class BotOfflineEventCPack extends PackBase{

}

/**
 * Id:15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
 * id：机器人QQ号
 */
class BotOnlineEventPack extends PackBase{

}

/**
 * Id:16 [机器人]主动或被动重新登录（事件）
 * message：原因消息
 */
class BotReloginEventPack extends PackBase{

    public String message;
}

/**
 * Id:17 [机器人]被取消禁言（事件）
 * id：群号
 * fid：执行人QQ号
 */
class BotUnmuteEventPack extends PackBase{

    public long id;
    public long fid;
}

/**
 * Id:18 [机器人]成功添加了一个新好友（事件）
 * name：好友nick
 * id：好友QQ号
 */
class FriendAddEventPack extends PackBase{

    public String name;
    public long id;
}

/**
 * Id:19 [机器人]好友头像被修改（事件）
 * id：好友QQ号
 * name：好友nick
 * url：图片url
 */
class FriendAvatarChangedEventPack extends PackBase{

    public String name;
    public String url;
    public long id;
}

/**
 * Id:20 [机器人]好友已被删除（事件）
 * name：好友nick
 * id：好友QQ号
 */
class FriendDeleteEventPack extends PackBase{

    public String name;
    public long id;
}

/**
 * Id:21 [机器人]在好友消息发送后广播（事件）
 * message：消息
 * id：好友QQ号
 * name：好友nick
 * res：是否成功发送
 * error：错误消息
 */
class FriendMessagePostSendEventPack extends PackBase{

    public List<String> message;
    public long id;
    public String name;
    public boolean res;
    public String error;
}

/**
 * Id:22 [机器人]在发送好友消息前广播（事件）
 * message：消息
 * id：好友QQ号
 * name：好友nick
 */
class FriendMessagePreSendEventPack extends PackBase{

    public List<String> message;
    public long id;
    public String name;
}

/**
 * 23 [机器人]好友昵称改变（事件）
 * id：好友QQ号
 * old: 旧的昵称
 * name：新的昵称
 */
class FriendRemarkChangeEventPack extends PackBase{

    public long id;
    public String name;
    public String old;
}


/**
 * Id:49 [机器人]收到群消息（事件）
 * id：群号
 * fid：发送人QQ号
 * name：发送人的群名片
 * message：发送的消息
 */
class GroupMessageEventPack extends PackBase {

    public long id;
    public long fid;
    public String name;
    public List<String> message;
}

/**
 * Id:50 [机器人]收到群临时会话消息（事件）
 * id：群号
 * fid：发送人QQ号
 * name：发送人的群名片
 * message：发送的消息
 * time：时间
 */
class TempMessageEventPack extends GroupMessageEventPack {

    public int time;
}

/**
 * Id:51 [机器人]收到朋友消息（事件）
 * id：朋友ID
 * fname：发送人的群名片
 * message：消息
 * time：时间
 */
class FriendMessageEventPack extends PackBase {

    public long id;
    public String name;
    public List<String> message;
    public int time;
}

/**
 * Id:52 [插件]发送群消息
 * id：群号
 * message：消息
 */
class SendGroupMessagePack extends PackBase {

    public long id;
    public List<String> message;
}

/**
 * Id:53 [插件]发送私聊消息
 * id：群号
 * fid：成员QQ号
 * message：消息
 */
class SendGroupPrivateMessagePack extends PackBase {

    public long id;
    public long fid;
    public List<String> message;
}

/**
 * Id:54 [插件]发送好友消息
 * id：好友QQ号
 * message：消息
 */
class SendFriendMessagePack extends PackBase {

    public long id;
    public List<String> message;
}

/**
 * Id:75 [插件]从本地文件加载图片发送到群
 * id: 群号
 * file: 图片文件的路径 注: 图片文件最好用绝对路径
 */
class LoadFileSendToGroupImagePack extends PackBase {

    public String file;
    public long id;
}

/**
 * Id:77 [插件]从本地文件加载图片发送到朋友
 * id: 好友QQ号
 * file: 图片文件的路径 注: 图片文件最好用绝对路径
 */
class LoadFileSendToFriendImagePack extends PackBase{

    public String file;
    public long id;
}

/**
 * Id:78 [插件]从本地文件加载语音发送到群
 * id: 群号
 * file: 语音文件的路径 注: 格式可以是mp3但是有的设备无法播放,格式amr会很糊,不知道什么格式最好
 */
class LoadFileSendToGroupSoundPack extends PackBase{

    public long id;
    public String file;
}