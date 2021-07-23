package com.meli.mutant.handler;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.meli.mutant.model.Statistics;

@JsonAutoDetect
public class GatewayResponseMetadataStats {
	private Statistics body;
	private Map<String, String> headers;
    private int statusCode;
	
    
	public Statistics getBody() {
		return body;
	}
	public void setBody(Statistics body) {
		this.body = body;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
    
    
}
