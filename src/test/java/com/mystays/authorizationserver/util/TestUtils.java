package com.mystays.authorizationserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public final class TestUtils {
    private TestUtils() {
    }

    public static <T> T parseResponse(MvcResult mvcResult, Class<T> responseClass) throws JsonProcessingException, UnsupportedEncodingException {
        String responseContent = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readValue(responseContent, responseClass);
    }

    public static String parseRequest(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static Authentication authentication() {
        return new UsernamePasswordAuthenticationToken("rithin@gmail.com", "password");
    }
}
