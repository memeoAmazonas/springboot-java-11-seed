package com.pansobao.seed.adapter.rest.exception;

import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;

public class NonTargetRestClientException extends GenericException {
    public NonTargetRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
