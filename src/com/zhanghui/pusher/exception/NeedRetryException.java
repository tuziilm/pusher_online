package com.zhanghui.pusher.exception;

/**
 * 需要重试的异常
 *
 */
public class NeedRetryException extends RuntimeException {
	private static final long serialVersionUID = -8434914169799090165L;

	public NeedRetryException() {
		super();
	}

	public NeedRetryException(String message, Throwable cause) {
		super(message, cause);
	}

	public NeedRetryException(String message) {
		super(message);
	}

	public NeedRetryException(Throwable cause) {
		super(cause);
	}
}
