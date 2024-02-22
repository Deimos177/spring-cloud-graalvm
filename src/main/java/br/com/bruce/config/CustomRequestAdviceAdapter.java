package br.com.bruce.config;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomRequestAdviceAdapter extends RequestBodyAdviceAdapter {
	
	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	LogService logService;
	
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	
	@Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
		String url = httpServletRequest.getRequestURI();
		if(!url.contains("/actuator/") && !url.contains("/swagger") && !url.contains("/api-docs")) {
    		logService.logRequest(httpServletRequest, body);
    	}
		
		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
	}
}