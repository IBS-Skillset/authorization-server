package com.mystays.authorizationserver.constants;


public class AuthConstants {
    public static final String LOGIN_PAGE = "/signin";
    public static final String LOGIN_PROCESSING_URL = "/perform_login";
    public static final String SUCCESS_URL = "/home";
    public static final String FAILURE_URL = "/signin?error=true";
    public static final String LOGIN_PROCESSING_URL_PATTERNS = LOGIN_PROCESSING_URL+"**";
    public static final String REDIRECT_URI = "/authorized";
    public static final String LOGOUT_URL = "/perform_logout";
    public static final String LOGOUT_URL_PATTERNS = LOGOUT_URL+"**";
    public static final String REGISTERED_CLIENT_ID = "88b6d0cd-4cf9-4e7d-bc5b-fa43dd82538c";
    public static final String CLIENT_ID = "client";
    public static final String SECRET_KEY = "{noop}secret";
    public static final String COOKIES = "JSESSIONID";
    public static final String RSA_UUID = "421903e4-0efd-46ed-b3de-ba27f713457f";

    private AuthConstants () {
    }

}
