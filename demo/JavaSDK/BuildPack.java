package JavaSDK;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public class BuildPack {

    public static byte[] Build(Object data, int index) {

        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }

    public static byte[] BuildImage(long qq, long id, long fid, String img, int index) {

        String temp = "";
        if (id != 0) {
            temp += "id=" + id + "&";
        }
        if (fid != 0) {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&";
        temp += "img=" + img;
        String str = temp + " ";
        byte[] temp1 = str.getBytes(StandardCharsets.UTF_8);
        temp1[temp1.length - 1] = (byte) index;
        return temp1;
    }

    //TODO 语音包
}
