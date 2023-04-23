package cn.starboot.tim.common.exception;

/**
 * 制定IM异常管理
 *
 * Created by DELL(mxd) on 2021/12/24 16:56
 */
public class ImException extends Exception {

    private static final long serialVersionUID = -7758277416166675226L;

    public ImException() {
        super();
    }

    public ImException(String message) {
        super(message);
    }

    public ImException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImException(Throwable cause) {
        super(cause);
    }

    protected ImException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
