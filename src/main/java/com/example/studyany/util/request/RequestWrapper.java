package com.example.studyany.util.request;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    /**
     * 생성자 함수
     * 생성과 동시에 private final 변수인 body 에 request 로 부터 inputStream 을 읽어 적재 해 둔다.
     * @param request httpServletRequest
     * @throws IOException stream 처리시 발생 Exception
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }


    /**
     * 생성자로 기 셋팅된 불변의 body byte 배열을 통해 servletInputStream 을 생성하여 반환
     * @return 생성된 stream
     */
    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return inputStream.read();
            }
        };
    }

    /**
     * 생성자로 기 셋팅된 불변의 body byte 배열을 통해 bufferReader 을 생성하여 반환
     * @return 생성된 bufferedReader
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
