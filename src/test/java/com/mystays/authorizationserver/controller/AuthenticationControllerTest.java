package com.mystays.authorizationserver.controller;

import com.mystays.authorizationserver.config.CustomAuthenticationManager;
import com.mystays.authorizationserver.config.WebSecurityConfig;
import com.mystays.authorizationserver.model.AuthenticationRequest;
import com.mystays.authorizationserver.model.AuthenticationResponse;
import com.mystays.authorizationserver.model.ErrorResponse;
import com.mystays.authorizationserver.repository.UserRepository;
import com.mystays.authorizationserver.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.mystays.authorizationserver.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(WebSecurityConfig.class)
class AuthenticationControllerTest {

    @MockBean
    private CustomAuthenticationManager customAuthenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    void authenticateUserTestSuccess() throws Exception {
        AuthenticationRequest authRequest = TestUtils.authenticationRequest();
        Authentication authentication = TestUtils.authentication();
        when(customAuthenticationManager.authenticate(authentication)).thenReturn(new UsernamePasswordAuthenticationToken("rithin@gmail.com", "password", null));
        MvcResult mvcResult = mockMvc.perform(post("/authenticate").content(parseRequest(authRequest)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        AuthenticationResponse authenticationResponse = parseResponse(mvcResult, AuthenticationResponse.class);
        assertEquals("Authenticated", authenticationResponse.getStatus());
        verify(customAuthenticationManager,times(1)).authenticate(authentication);
    }

    @Test
    void authenticateUserTestFailure() throws Exception {
        AuthenticationRequest authRequest = TestUtils.authenticationRequest();
        Authentication authentication = TestUtils.authentication();
        when(customAuthenticationManager.authenticate(authentication)).thenThrow(new BadCredentialsException("Invalid Credentials"));
        MvcResult mvcResult = mockMvc.perform(post("/authenticate").content(parseRequest(authRequest)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        ErrorResponse errorResponse = parseResponse(mvcResult, ErrorResponse.class);
        assertEquals("Unauthorized", errorResponse.getStatus());
        verify(customAuthenticationManager,times(1)).authenticate(authentication);
    }
}