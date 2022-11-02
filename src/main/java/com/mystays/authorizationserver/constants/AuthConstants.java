package com.mystays.authorizationserver.constants;

import java.util.List;

public class AuthConstants {
    public static final String LOGIN_PAGE = "http://127.0.0.1:3000/signin";
    public static final String LOGIN_PROCESSING_URL = "/perform_login";
    public static final String SUCCESS_URL = "http://127.0.0.1:3000/home";
    public static final String FAILURE_URL = "http://127.0.0.1:3000/signin?error=true";
    public static final String LOGIN_PROCESSING_URL_PATTERNS = LOGIN_PROCESSING_URL+"**";
    public static final String REDIRECT_URI = "http://127.0.0.1:3000/authorized";
    public static final String LOGOUT_URL = "/perform_logout";
    public static final String LOGOUT_URL_PATTERNS = LOGOUT_URL+"**";
    public static final String REGISTERED_CLIENT_ID = "88b6d0cd-4cf9-4e7d-bc5b-fa43dd82538c";
    public static final String CLIENT_ID = "client";
    public static final String SECRET_KEY = "{noop}secret";
    public static final List<String> ALLOWED_ORIGINS = List.of("http://127.0.0.1:3000",
            "http://localhost:3000");
    public static final String COOKIES = "JSESSIONID";
    public static final String refreshTokenTimeToLive= "10";
    public static final String accessTokenTimeToLive= "5";

    private AuthConstants () {
    }

}
