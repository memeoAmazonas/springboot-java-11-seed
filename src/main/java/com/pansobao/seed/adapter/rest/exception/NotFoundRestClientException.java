package com.pansobao.seed.adapter.rest.exception;

import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;

public class NotFoundRestClientException extends GenericException {
    public NotFoundRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
