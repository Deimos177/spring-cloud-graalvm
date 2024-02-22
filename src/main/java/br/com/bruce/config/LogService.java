package br.com.bruce.config;

import org.springframework.http.server.ServerHttpResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface LogService {
	
	void logRequest(HttpServletRequest request, Object body);
	void logResponse(ServerHttpResponse response, Object body);
}