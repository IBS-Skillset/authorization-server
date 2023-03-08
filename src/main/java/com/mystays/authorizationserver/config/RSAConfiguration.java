package com.mystays.authorizationserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RSAConfiguration {

    @Value("${mod}")
    private String mod;
    @Value("${publicexp}")
    private String publicExp;
    @Value("${privateexp}")
    private String privateExp;
}
