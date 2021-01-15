package JavaSDK;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    //只能在Java10+使用

    public static void main(String[] args) {

        Robot robot = new Robot();

        robot.start(); //启动插件的Socket连接

        Scanner input = new Scanner(System.in);

        while (true) { //控制台打字发送包
            robot.sendGroupMessage(571239090, new ArrayList<String>() {{
                add(input.nextLine());
            }});
        }
    }
}
