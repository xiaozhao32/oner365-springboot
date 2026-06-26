package com.oner365.data.web.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

/**
 * CustomHttpInputMessage
 *
 * @author zhaoyong
 */
public class CustomHttpInputMessage implements HttpInputMessage {

    private final byte[] body;

    private final HttpHeaders headers;

    public CustomHttpInputMessage(HttpHeaders headers, String body) {
        this.body = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        this.headers = headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(body);
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

}
