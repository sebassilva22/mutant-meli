package com.meli.mutant.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class GatewayResponse<T> {

    private final T body;
    private final Map<String, String> headers;
    private final int statusCode;

    /**
     * Creates a GatewayResponse object.
     * @param body body of the response
     * @param headers headers of the response
     * @param statusCode status code of the response
     */
    public GatewayResponse(final T body, final Map<String, String> headers, final int statusCode) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }

    public T getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    
}