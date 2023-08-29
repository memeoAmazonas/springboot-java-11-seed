package com.pansobao.seed.config.interceptor;

import brave.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class SleuthInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID_NOT_EXIST = "Trace id not exists";
    public static final String SPAND_ID_NOT_EXIST = "Spand id not exists";
    public static final String X_B3_TRACE_ID = "X_B3-TraceId";
    public static final String X_B3_SPAND_ID = "X_B3-SpandId";

    private final Tracer tracer;

    public SleuthInterceptor(final Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final var traceId = Optional.ofNullable((this.tracer.currentSpan()))
                .map(s -> s.context().traceIdString())
                .orElse(TRACE_ID_NOT_EXIST);
        final var spandId = Optional.ofNullable((this.tracer.currentSpan()))
                .map(s -> s.context().spanIdString())
                .orElse(SPAND_ID_NOT_EXIST);
        if (!response.getHeaderNames().contains(X_B3_TRACE_ID)) response.addHeader(X_B3_TRACE_ID, traceId);
        if (!response.getHeaderNames().contains(X_B3_SPAND_ID)) response.addHeader(X_B3_SPAND_ID, spandId);
        return Boolean.TRUE;
    }
}
