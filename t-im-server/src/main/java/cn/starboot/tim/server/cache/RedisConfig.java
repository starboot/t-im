package cn.starboot.tim.server.cache;

public class RedisConfig {

	private int port;
	private int retryNum;
	private int maxActive;
	private int maxIdle;
	private int maxWait;
	private int timeout;

	private String host;
	private String auth;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getRetryNum() {
		return retryNum;
	}

	public void setRetryNum(int retryNum) {
		this.retryNum = retryNum;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "RedisConfig{" +
				"port=" + port +
				", retryNum=" + retryNum +
				", maxActive=" + maxActive +
				", maxIdle=" + maxIdle +
				", maxWait=" + maxWait +
				", timeout=" + timeout +
				", host='" + host + '\'' +
				", auth='" + auth + '\'' +
				'}';
	}
}
