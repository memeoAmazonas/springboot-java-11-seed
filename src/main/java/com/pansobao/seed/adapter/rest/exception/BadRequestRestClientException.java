package com.pansobao.seed.adapter.rest.exception;

import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;

public class BadRequestRestClientException extends GenericException {
    public BadRequestRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
