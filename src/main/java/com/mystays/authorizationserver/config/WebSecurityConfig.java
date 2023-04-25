package com.mystays.authorizationserver.config;

import com.mystays.authorizationserver.constants.HostUi;
import com.mystays.authorizationserver.service.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.mystays.authorizationserver.constants.AuthConstants.*;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private CustomAuthenticationProvider customAuthenticationProvider;

    private final CORSCustomizer corsCustomizer;

    private final HostUi hostUi;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        corsCustomizer.corsCustomizer(http);
        return http.formLogin(form -> form
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
                        .loginPage(hostUi.uriPath(LOGIN_PAGE))
                        .defaultSuccessUrl(hostUi.uriPath(SUCCESS_URL))
                        .failureUrl(hostUi.uriPath(FAILURE_URL))
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL)
                        .logoutSuccessUrl(hostUi.uriPath(SUCCESS_URL))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(COOKIES).permitAll())
                .authorizeRequests().antMatchers(LOGIN_PROCESSING_URL_PATTERNS, LOGOUT_URL_PATTERNS,"/register")
                .permitAll().anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().csrf().ignoringAntMatchers(LOGIN_PROCESSING_URL_PATTERNS, LOGOUT_URL_PATTERNS).and()
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

}
