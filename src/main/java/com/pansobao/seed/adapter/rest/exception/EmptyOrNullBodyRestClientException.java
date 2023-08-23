package com.pansobao.seed.adapter.rest.exception;

import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;

public class EmptyOrNullBodyRestClientException extends GenericException {
    public EmptyOrNullBodyRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
