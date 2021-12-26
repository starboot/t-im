package org.tim.common.banner;

import org.tim.common.ImConst;

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

    private static final String TIM = " :: "+ImConst.name+" :: ";

    @Override
    public void printBanner(PrintStream printStream) {
        printStream.println(BANNER);
        String version  = " (" + ImConst.Version + ")";
        printStream.println(TIM+version+"\n");
    }

}
