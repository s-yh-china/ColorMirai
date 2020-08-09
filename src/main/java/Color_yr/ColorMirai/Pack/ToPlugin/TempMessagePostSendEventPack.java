package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
47 [机器人]在群临时会话消息发送后广播（事件）
id：群号
fid：发送到的QQ号
res：是否成功发送
message：消息
error：错误信息
 */
public class TempMessagePostSendEventPack {
    public long id;
    public long fid;
    public boolean res;
    public String message;
    public String error;

    public TempMessagePostSendEventPack(long id, long fid, boolean res, MessageChain message, String error) {
        this.error = error;
        this.fid = fid;
        this.id = id;
        this.message = message.contentToString();
        this.res = res;
    }
}
