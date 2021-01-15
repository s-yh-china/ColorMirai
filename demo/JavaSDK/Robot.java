package JavaSDK;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


public class Robot extends Thread {

    private final static String ip = "127.0.0.1"; //机器人ip
    private final static int port = 23333; //机器人端口
    private final static long qq = 0; //机器人的qq号

    private static Socket Socket;
    private static Thread ReadThread;
    private static Thread DoThread;
    private static boolean IsRun;
    private static boolean IsConnect;
    private static List<RobotTask> QueueRead;
    private static List<byte[]> QueueSend;
    private static final StartPack PackStart = new StartPack() {{ //插件连接包
        Name = "ColoryrSDK"; //插件名
        Reg = new ArrayList<Integer>() {{ //要监听的事件列表
            add(49);
            add(50);
            add(51);
        }};
    }};

    public void run() {
        QueueRead = new CopyOnWriteArrayList<>();
        QueueSend = new CopyOnWriteArrayList<>();

        DoThread = new Thread(() -> {

            RobotTask task;
            while (IsRun) {
                try {
                    if (!QueueRead.isEmpty()) {
                        task = QueueRead.remove(0);
                        EventDo.Do(task.index,task); //执行操作
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    ServerMain.logError(e);
                }
            }
        }); //包的执行

        ReadThread = new Thread(() -> {
            try {
                while (!IsRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                ServerMain.logError(e);
            }
            DoThread.start();
            byte[] data;
            while (IsRun) {
                try {
                    if (!IsConnect) {
                        reConnect();
                    } else if (Socket.getInputStream().available() > 0) {
                        data = new byte[Socket.getInputStream().available()];
                        Socket.getInputStream().read(data);
                        var type = data[data.length - 1];
                        data[data.length - 1] = 0;
                        QueueRead.add(new RobotTask(type, new String(data)));
                    } else if (Socket.getInputStream().available() < 0) {
                        ServerMain.logOut("机器人连接中断");
                        IsConnect = false;
                    } else if (!QueueSend.isEmpty()) {
                        data = QueueSend.remove(0);
                        Socket.getOutputStream().write(data);
                        Socket.getOutputStream().flush();
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    ServerMain.logError("机器人连接失败");
                    ServerMain.logError(e);
                    IsConnect = false;
                    ServerMain.logError("机器人20秒后重连");
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    ServerMain.logError("机器人重连中");
                }
            }
        }); //包的读取

        ReadThread.start();

        IsRun = true;
        //readTest();
    }

    private static boolean is() {
        try {
            Socket.sendUrgentData(60);
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    private void readTest() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            sendGroupMessage(571239090, new ArrayList<String>() {{
                add(scanner.nextLine());
            }});
        }
    }

    private void reConnect() {
        try {
            if (Socket != null)
                Socket.close();
            Socket = new Socket(ip, port);

            var data = (JSON.toJSON(PackStart) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;

            Socket.getOutputStream().write(data);
            Socket.getOutputStream().flush();

            QueueRead.clear();
            QueueSend.clear();
            ServerMain.logOut("机器人已连接");
            IsConnect = true;
        } catch (Exception e) {
            ServerMain.logError("机器人连接失败");
            ServerMain.logError(e);
        }
    }

    public void sendGroupMessage(long id_, List<String> message_) {
        var data = BuildPack.Build(new SendGroupMessagePack() {{
            qq = Robot.qq;
            id = id_;
            message = message_;
        }}, 52);
        QueueSend.add(data);
    }

    public void sendGroupPrivateMessage(long id_, long fid_, List<String> message_) {
        var data = BuildPack.Build(new SendGroupPrivateMessagePack() {{
            qq = Robot.qq;
            id = id_;
            fid = fid_;
            message = message_;
        }}, 53);
        QueueSend.add(data);
    }

    public void sendFriendMessage(long id_, List<String> message_) {
        var data = BuildPack.Build(new SendFriendMessagePack() {{
            qq = Robot.qq;
            id = id_;
            message = message_;
        }}, 54);
        QueueSend.add(data);
    }

    public void sendGroupImage(long id, String img) {
        var data = BuildPack.BuildImage(Robot.qq, id, 0, img, 61);
        QueueSend.add(data);
    }

    public void sendGroupPrivateImage(long id, long fid, String img) {
        var data = BuildPack.BuildImage(Robot.qq, id, fid, img, 62);
        QueueSend.add(data);
    }

    public void sendFriendImage(long id, String img) {
        var data = BuildPack.BuildImage(Robot.qq, id, 0, img, 63);
        QueueSend.add(data);
    }

    public void end() { //关闭机器人
        ServerMain.logOut("机器人正在断开");
        IsRun = false;
        if (Socket != null) {
            try {
                Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ServerMain.logOut("机器人已断开");
    }
}


class ServerMain {

    public static void logError(Exception e) {
        String a = "[Error]";
        System.out.println(a);
        e.printStackTrace();
    }

    public static void logError(String a) {
        a = "[Error]" + a;
        System.out.println(a);
    }

    public static void logOut(String a) {
        a = "[Info]" + a;
        System.out.println(a);
    }
}

class RobotTask {

    public byte index;
    public String data;

    public RobotTask(byte type, String s) {
        index = type;
        data = s;
    }
}

class EventDo {

    public static void Do(int id, RobotTask task) {

        switch (task.index) { //监听事件的执行
            case 49: //这里是监听事件的包名
                var pack = JSON.parseObject(task.data, GroupMessageEventPack.class); //解析数据
                System.out.println("qq = " + pack.qq);
                System.out.println("id = " + pack.id);
                System.out.println("fid = " + pack.fid);
                System.out.println("name = " + pack.name);
                System.out.println("message = ");
                for (var item : pack.message) {
                    System.out.println(item);
                }
                System.out.println();
                break;
            case 50: //同样的部分
                var pack1 = JSON.parseObject(task.data, TempMessageEventPack.class);
                System.out.println("qq = " + pack1.qq);
                System.out.println("id = " + pack1.id);
                System.out.println("fid = " + pack1.fid);
                System.out.println("name = " + pack1.name);
                System.out.println("message = ");
                for (var item : pack1.message) {
                    System.out.println(item);
                }
                System.out.println();
                break;
            case 51: //一样的
                var pack2 = JSON.parseObject(task.data, FriendMessageEventPack.class);
                System.out.println("qq = " + pack2.qq);
                System.out.println("id = " + pack2.id);
                System.out.println("time = " + pack2.time);
                System.out.println("name = " + pack2.name);
                System.out.println("message = ");
                for (var item : pack2.message) {
                    System.out.println(item);
                }
                System.out.println();
                break;
        }
    }
}