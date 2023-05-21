package cn.starboot.tim.common.exception;

import cn.starboot.socket.exception.AioEncoderException;

public class ImEncodeException extends AioEncoderException {

	public ImEncodeException() {
	}

	public ImEncodeException(String message) {
		super(message);
	}

	public ImEncodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImEncodeException(Throwable cause) {
		super(cause);
	}

	public ImEncodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
