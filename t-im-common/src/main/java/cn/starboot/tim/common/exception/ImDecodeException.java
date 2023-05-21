package cn.starboot.tim.common.exception;

import cn.starboot.socket.exception.AioDecoderException;

public class ImDecodeException extends AioDecoderException {

	public ImDecodeException() {
	}

	public ImDecodeException(String message) {
		super(message);
	}

	public ImDecodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImDecodeException(Throwable cause) {
		super(cause);
	}

	public ImDecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
