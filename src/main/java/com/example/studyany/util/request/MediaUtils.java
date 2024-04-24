package com.example.studyany.util.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;

//import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaUtils {

    public static final String TALA_JSON = "talaria/json";
    public static final String PULS_JSON = "application/*+json";
    public static final String APPL_JSON = "application/json";

    public static final MediaType TALARIA_JSON = MediaType.valueOf(TALA_JSON);
    public static final MediaType JSON_PLUS = MediaType.valueOf(PULS_JSON);

    public static boolean isJsonMediaType(HttpServletRequest request){
        if(request == null) return false;

        String contentType = request.getContentType();
        if(contentType == null) return false;

        MediaType mediaType = toMediaType(contentType);
        if(mediaType == null) return false;

        return (MediaType.APPLICATION_JSON.includes(mediaType)
                || TALARIA_JSON.includes(mediaType)
                || JSON_PLUS.includes(mediaType));
    }

    private static MediaType toMediaType(String contentType) {
        if(contentType == null) return null;

        try {
            return MediaType.valueOf(contentType);
        } catch (InvalidMediaTypeException e) {
            // ignore
        }
        return null;
    }
}
