package br.com.bruce.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogServiceImpl implements LogService {
	
	@Autowired
	ObjectMapper mapper;

	@Override
	public void logRequest(HttpServletRequest request, Object body) {

		try {
			log.info("――――――――――――――――――――――――――request begin――――――――――――――――――――――――――――――――――――――――――――――");
			log.info("Request Headers     : {}", headersMapBuilder(request));
			log.info("Request URI         : {}", request.getRequestURI());
			log.info("Request Method      : {}", request.getMethod());
			if (!request.getMethod().toString().equalsIgnoreCase("get"))
				log.info("Request Body        : \n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			
			if (request.getMethod().toString().equalsIgnoreCase("get"))
				if(!paramsMapBuilder(request).isEmpty())
				  log.info("Request Params      : {}", paramsMapBuilder(request));
			log.info("――――――――――――――――――――――――――request end――――――――――――――――――――――――――――――――――――――――――――――――");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logResponse(ServerHttpResponse response, Object body) {
		
		try {
			log.info("――――――――――――――――――――――――――response begin――――――――――――――――――――――――――――――――――――――――――――――");
			log.info("Request Headers     : {}", response.getHeaders());
			log.info("Response Body       : \n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.info("――――――――――――――――――――――――――response end――――――――――――――――――――――――――――――――――――――――――――――――");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Map<String, String> headersMapBuilder(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headersNames = request.getHeaderNames();
		headersNames.asIterator().forEachRemaining(key -> {
			String value = request.getHeader(key);
			headers.put(key, value);
		});

		return headers;
	}
	
	private Map<String, String> paramsMapBuilder(HttpServletRequest request) {
		Map<String, String> params = new HashMap<>();
		Enumeration<String> paramsNames = request.getParameterNames();
		paramsNames.asIterator().forEachRemaining(key -> {
			String value = request.getParameter(key);
			params.put(key, value);
		});

		return params;
	}
}
