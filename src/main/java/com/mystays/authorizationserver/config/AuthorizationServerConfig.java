package com.mystays.authorizationserver.config;

import com.mystays.authorizationserver.config.keys.JwksKeys;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

import static com.mystays.authorizationserver.constants.AuthConstants.*;

@Configuration
@AllArgsConstructor
public class AuthorizationServerConfig {

  private final CORSCustomizer corsCustomizer;

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain securityASFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    corsCustomizer.corsCustomizer(http);
    return http.formLogin().loginPage(LOGIN_PAGE).defaultSuccessUrl(SUCCESS_URL).and().build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    RegisteredClient registeredClient = RegisteredClient.withId(REGISTERED_CLIENT_ID)
        .clientId(CLIENT_ID)
        .clientSecret(SECRET_KEY)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(REDIRECT_URI)
        .scope(OidcScopes.OPENID)
        .clientSettings(ClientSettings.builder()
            .requireAuthorizationConsent(true).build())
        .tokenSettings(TokenSettings.builder()
            .refreshTokenTimeToLive(Duration.ofHours(10))
            .build())
        .build();

    JdbcRegisteredClientRepository registeredClientRepository =
            new JdbcRegisteredClientRepository(jdbcTemplate);
    registeredClientRepository.save(registeredClient);

    return registeredClientRepository;
  }

  @Bean
  public JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository){
    return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
  }

  @Bean
  public ProviderSettings providerSettings() {
    return ProviderSettings.builder().issuer("http://localhost:9000").build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = JwksKeys.generateRSAKey();
    JWKSet set = new JWKSet(rsaKey);
    return (j, sc) -> j.select(set);
  }
}
