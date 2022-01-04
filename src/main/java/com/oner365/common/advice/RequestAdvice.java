package com.oner365.common.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.oner365.common.config.properties.ClientWhiteProperties;
import com.oner365.util.Cipher;
import com.oner365.util.RequestUtils;
import com.oner365.util.RsaUtils;

/**
 * Controller Advice
 *
 * @author liutao
 */
@ControllerAdvice(basePackages = "com.oner365")
public class RequestAdvice extends RequestBodyAdviceAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAdvice.class);

	@Autowired
	private ClientWhiteProperties clientWhiteProperties;
	
	

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return true;
	}
	

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		HttpServletRequest request = RequestUtils.getHttpRequest();
		String body = RequestUtils.getRequestBody(inputMessage.getBody());
		try {
			
			if(RequestUtils.validateClientWhites(request,clientWhiteProperties.getWhites())){
				if (body instanceof String) {
					String content = body.toString();
			        String sign = inputMessage.getHeaders().get("sign").get(0);
			        String key = RsaUtils.buildRsaDecryptByPrivateKey(sign, clientWhiteProperties.getPrivateKey());
			        String b = Cipher.decodeSMS4toString(Base64.getDecoder().decode(content),
							key.substring(0,16).getBytes());
					return new MappingJacksonInputMessage(
							new ByteArrayInputStream(b.getBytes()),
							inputMessage.getHeaders());
				}
			}
		} catch (Exception e) {
			LOGGER.error("beforeBodyRead ERROR:{}",e);
		}
		return new MappingJacksonInputMessage(
				new ByteArrayInputStream(body.getBytes()),
				inputMessage.getHeaders());
	}






}
