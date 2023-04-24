package cn.starboot.tim.common;

/**
 * Created by DELL(mxd) on 2022/1/6 17:07
 */
public class ImConfig {

	String name = "TIM";

	String version = "3.0.0.v20221001-RELEASE";

	String charset = "utf-8";

	String authKey = "authKey";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
}
