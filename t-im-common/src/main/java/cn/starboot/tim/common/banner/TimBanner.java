package cn.starboot.tim.common.banner;

import cn.starboot.tim.common.ImConst;

import java.io.PrintStream;

/**
 * Created by DELL(mxd) on 2021/12/24 13:13
 */
public class TimBanner implements Banner, ImConst {

    private static final String BANNER =
            "  _______       _____  ____    ____  \n" +
            " |__   __|     |_   _||_   \\  /   _| \n" +
            "    | |  ______  | |    |   \\/   |   \n" +
            "    | | |______| | |    | |\\  /| |   \n" +
            "    | |         _| |_  _| |_\\/_| |_  \n" +
            "    |_|        |_____||_____||_____| \n" +
            " ";

    private static final String TIM = "\033[036m :: "+ImConst.name+" :: \033[0m";

    @Override
    public void printBanner(PrintStream printStream) {
        printStream.println(BANNER);
        String version  = " (\033[030m" + ImConst.Version + "\033[0m)";
        printStream.println(TIM+version+"\n");
    }

}
