package cn.starboot.tim.common;

public interface ImCallBack {

	void onSuccess();

	void onError(int code, String error);

	default void onProgress (int progress, String status) {}
}
