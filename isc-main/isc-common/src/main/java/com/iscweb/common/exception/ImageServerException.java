package com.iscweb.common.exception;

import com.iscweb.common.exception.BaseApplicationException;

public class ImageServerException extends BaseApplicationException {

    public ImageServerException() {
        super();
    }

    public ImageServerException(String msg) {
        super(msg);
    }

    public ImageServerException(String msg, Throwable e) {
        super(msg, e);
    }
}
