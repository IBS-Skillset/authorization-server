package com.example.authserver.config;

import com.example.authserver.service.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig  {

  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;

  private final CORSCustomizer corsCustomizer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    corsCustomizer.corsCustomizer(http);
    return http.formLogin(form -> form
            .loginPage("/login")
            .permitAll())
          .authorizeRequests()
            .anyRequest().authenticated()
          .and().build();
  }
  @Autowired
  public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder
            .authenticationProvider(customAuthenticationProvider);
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    System.out.println("tes->");
//    var u1 = User.withUsername("test").password("12345").authorities("read").build();
//
//    var uds = new InMemoryUserDetailsManager();
//    uds.createUser(u1);
//    return uds;
//  }

//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return NoOpPasswordEncoder.getInstance();
//  }
}
