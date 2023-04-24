package cn.starboot.tim.common.banner;

import cn.starboot.tim.common.ImConfig;

import java.io.PrintStream;

/**
 * Created by DELL(mxd) on 2021/12/24 13:13
 */
public class TimBanner implements Banner {

    private static final String BANNER =
            "  _______       _____  ____    ____  \n" +
            " |__   __|     |_   _||_   \\  /   _| \n" +
            "    | |  ______  | |    |   \\/   |   \n" +
            "    | | |______| | |    | |\\  /| |   \n" +
            "    | |         _| |_  _| |_\\/_| |_  \n" +
            "    |_|        |_____||_____||_____| \n" +
            " ";

    private final ImConfig config;

	public TimBanner(ImConfig config) {
		this.config = config;
	}

	@Override
    public void printBanner(PrintStream printStream) {
        printStream.println(BANNER);
        printStream.println(getTimVersion(this.config.getName(), this.config.getVersion()));
    }

    private String getTimVersion(String name, String version) {
		return "\033[036m :: "+name+" :: \033[0m" + version;
	}

}
