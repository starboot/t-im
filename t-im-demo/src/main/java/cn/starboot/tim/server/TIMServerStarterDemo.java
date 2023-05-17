package cn.starboot.tim.server;

/**
 * Created by DELL(mxd) on 2021/12/25 16:36
 */
public class TIMServerStarterDemo {

    public static void main(String[] args) {

        TIMServerStarter instance = TIMServerStarter.getInstance();

        instance.start();
    }



}
