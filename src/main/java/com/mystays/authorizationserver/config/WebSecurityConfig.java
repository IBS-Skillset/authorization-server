package com.mystays.authorizationserver.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig  {

  private final CORSCustomizer corsCustomizer;


  private static final String LOGIN_PAGE_URL = "http://localhost:3000/logins";


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    corsCustomizer.corsCustomizer(http);
    return http.formLogin()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().build();
  }



  @Bean
  public UserDetailsService userDetailsService() {
    var u1 = User.withUsername("test").password("12345").authorities("read").build();

    var uds = new InMemoryUserDetailsManager();
    uds.createUser(u1);
    return uds;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
