
package cn.starboot.tim.common;


public enum ImStatus implements Status {
	
	C10000(10000,"ok",     "SEND SUCCESS"),
	C10001(10001,"offline","USER OFFLINE"),
	C10019(10019,"online", "USER ONLINE"),
	C10002(10002,"failed", "SEND FAILED"),
	C10003(10003,"ok",     "GET USER INFORMATION SUCCESS"),
	C10004(10004,"failed", "GET USER INFORMATION FAILED"),
	C10005(10005,"ok",     "GET ALL USER INFORMATION SUCCESS"),
	C10006(10006,"ok",     "GET ALL USER OFFLINE MESSAGE SUCCESS"),
	C10007(10007,"ok",     "LOGIN SUCCESS"),
	C10008(10008,"failed", "LOGIN FAILED"),
	C10009(10009,"ok",     "AUTH SUCCESS"),
	C10010(10010,"failed", "AUTH FAILED"),
	C10011(10011,"ok",     "JOIN GROUP SUCCESS"),
	C10012(10012,"failed", "JOIN GROUP FAILED"),
	C10013(10013,"failed", "Protocol version number does not match"),
	C10014(10014,"failed", "unsupported cmd command"),
	C10015(10015,"failed", "GET USER INFORMATION FAILED"),
	C10016(10016,"ok",     "GET USER OFFLINE MESSAGE SUCCESS"),
	C10017(10017,"failed", "UNKNOWN CMD COMMAND"),
	C10018(10018,"ok",     "GET USER HISTORY MESSAGE SUCCESS"),
	C10020(10020,"failed", "INVALID VERIFICATION"),
	C10021(10021,"ok",     "CLOSE SUCCESS"),
	C10022(10022,"failed", "YOU ARE NOT START SAVE, MESSAGE SAVE ARE FAILED");
	
	private final int status;
	
	private final String description;
	
	private final String text;

	ImStatus(int status, String description, String text) {
		this.status = status;
		this.description = description;
		this.text = text;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public int getCode() {
		return this.status;
	}

	@Override
	public String getMsg() {
		return this.getDescription()+" "+this.getText();
	}
}
