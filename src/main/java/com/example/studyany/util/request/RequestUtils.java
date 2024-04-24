package com.example.studyany.util.request;

//import com.hanatour.pkg.apiv2.constants.Guid;
//import hntframe.run.common.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * request 객체를 통해 client 의 실질적인 ip 주소를 취득 한다.
     * @param request http servlet request
     * @return client ip
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (isIpUnknown(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (isIpUnknown(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (isIpUnknown(ip)) {
            ip = request.getRemoteAddr();
        }

        LOGGER.debug("X-Forwarded-For={}, Proxy-Client-IP={}, WL-Proxy-Client-IP={}, HTTP_CLIENT_IP={}, HTTP_X_FORWARDED_FOR={}," +
                        " X-Real-IP={}, X-RealIP={}, REMOTE_ADDR={}, getRemoteAddr={}"
                , request.getHeader("X-Forwarded-For"), request.getHeader("Proxy-Client-IP"), request.getHeader("WL-Proxy-Client-IP"), request.getHeader("HTTP_CLIENT_IP"), request.getHeader("HTTP_X_FORWARDED_FOR"),
                request.getHeader("X-Real-IP"), request.getHeader("X-RealIP"), request.getHeader("REMOTE_ADDR"), request.getRemoteAddr());

        if ((ip != null && !ip.isEmpty()) && ip.contains(",")) {
            String[] token = ip.split(",");
            if (token.length > 0) {
                ip = token[0].trim();
                LOGGER.debug("[fwk] comma delimited, split ip ={}", ip);
            }
        }

        return ip;
    }

    private static boolean isIpUnknown(String ip){
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * reqeust 객체에서 header 정보를 추출 한다.
     *
     * @param request http servlet request
     * @return headersInfo
     */
    public static String getHeadersInfo(HttpServletRequest request){
        StringBuilder headersInfo = new StringBuilder();
        // 헤더 이름들을 Enumeration 형태로 받기
        Enumeration<String> headerNames = request.getHeaderNames();

        // 헤더 이름들을 순회하면서 헤더 값 추출
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersInfo.append(String.format("%s: %s", headerName, headerValue)).append("\n");
        }

        return headersInfo.toString();
    }

    /**
     * 타입별로 request parameter 변환
     *
     * @param request 요청 : requset
     * @return 타입별로 변환한 body
     */
    public static String getRequestBody(HttpServletRequest request){

        // JSON 타입일 때
        if(MediaUtils.isJsonMediaType(request)){
            final StringBuilder stringBuilder = new StringBuilder();

            try{
                stringBuilder.append(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
            }catch (Exception e){
                LOGGER.warn("err :::", e);
            }
            return stringBuilder.toString().replace("\n","");
        }else{
            String contentType = request.getContentType();
            try{
                final StringBuilder body = new StringBuilder();
                try (Reader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        body.append((char) c);
                    }
                }
                return String.format("(ContentType: %s) (Body: %s)", contentType, body);
            }catch(Exception e){
                LOGGER.warn("logging failed :::", e);
                return String.format("(ContentType: %s)", contentType);
            }
        }
    }


    /**
     * keep-alive 비활성화 처리를 위한 헤더
     * @return
     */
    public static HttpHeaders nopeKeepAliveHeaders(){
        final HttpHeaders headers = new HttpHeaders();

//        headers.set(Guid.GUID_NAME, MDC.get(Guid.GUID_NAME));
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONNECTION, "close");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

    /**
     * 디폴트 헤더 세팅
     *
     * @return
     */
    public static HttpHeaders defaultHeaders(){
        final HttpHeaders headers = new HttpHeaders();

//        headers.set(Guid.GUID_NAME, MDC.get(Guid.GUID_NAME));
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
