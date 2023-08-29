package com.pansobao.seed.config.handler;

import brave.Tracer;
import com.pansobao.seed.adapter.rest.exception.*;
import com.pansobao.seed.config.ErrorCode;
import com.pansobao.seed.config.exception.GenericException;
import com.pansobao.seed.config.interceptor.SleuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String DEVELOP_PROFILE = "Develop";

    private final HttpServletRequest request;
    private final Tracer tracer;

    @Value("${spring.profiles.active:}")
    private String profile;

    public ErrorHandler(final HttpServletRequest request, final Tracer tracer) {
        this.request = request;
        this.tracer = tracer;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e,ErrorCode.INTERNAL_ERROR);
    }
    @ExceptionHandler({NonTargetRestClientException.class, RestClientGenericException.class})
    public ResponseEntity<ErrorResponse> handle(NonTargetRestClientException e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e,e.getCode());
    }
    @ExceptionHandler({NotFoundRestClientException.class, EmptyOrNullBodyRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(GenericException e) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), e);
        return buildError(HttpStatus.NOT_FOUND,e,e.getCode());
    }
    @ExceptionHandler({BadRequestRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(BadRequestRestClientException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST,e,e.getCode());
    }

@ExceptionHandler({TimeoutRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(TimeoutRestClientException e) {
        log.error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), e);
        return buildError(HttpStatus.REQUEST_TIMEOUT,e,e.getCode());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus httpStatus, Throwable e, ErrorCode code) {
        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : "";
        final var traceId = Optional.ofNullable((this.tracer.currentSpan()))
                .map(s -> s.context().traceIdString())
                .orElse(SleuthInterceptor.TRACE_ID_NOT_EXIST);
        final var spandId = Optional.ofNullable((this.tracer.currentSpan()))
                .map(s -> s.context().spanIdString())
                .orElse(SleuthInterceptor.SPAND_ID_NOT_EXIST);
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                SleuthInterceptor.X_B3_TRACE_ID, traceId,
                SleuthInterceptor.X_B3_SPAND_ID,spandId,
                "query_string", queryString,
                "stack_trace", isDebugMessage);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .name(httpStatus.getReasonPhrase())
                        .detail(String.format("%s: %s", e.getClass().getCanonicalName(), e.getMessage()))
                        .status(httpStatus.value())
                        .code(code.getValue())
                        .resource(request.getRequestURI())
                        .metadata(metaData)
                        .build(), httpStatus);

    }
}
