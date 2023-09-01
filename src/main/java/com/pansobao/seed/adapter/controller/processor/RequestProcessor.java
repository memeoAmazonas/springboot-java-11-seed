package com.pansobao.seed.adapter.controller.processor;

import com.pansobao.seed.adapter.controller.model.RestResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Function;

public class RequestProcessor implements Processor {
    @Override
    public <X> RestResponse<X> processRequest(Enriched enriched, Function<Enriched, RestResponse<X>> operation) {
        return null;
    }

    @Override
    public Map<String, String> parseFilter(String filters) {
        return null;
    }

    @Override
    public Map<String, String> buildMetadata(HttpServletRequest request) {
        return null;
    }

    @Override
    public Map<String, String> buildMetadata(HttpServletRequest request, Integer pageNumber, Integer pageSize) {
        return null;
    }
}
