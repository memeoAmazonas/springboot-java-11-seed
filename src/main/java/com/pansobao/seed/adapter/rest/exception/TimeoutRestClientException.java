package com.pansobao.seed.adapter.rest.exception;

import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;

public class TimeoutRestClientException extends GenericException {
    public TimeoutRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
