package com.mystays.authorizationserver.config;

import com.mystays.authorizationserver.config.keys.JwksKeys;
import com.mystays.authorizationserver.constants.HostUi;
import com.mystays.authorizationserver.service.OidcUserInfoService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
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
public class AuthorizationServerConfig {

  private final CORSCustomizer corsCustomizer;

  private final RSAConfiguration rsaConfiguration;

  private final String issuerUrl;

  private final String refreshTokenDuration;

  private final String accessTokenDuration;

  private final HostUi hostUi;

  public AuthorizationServerConfig(CORSCustomizer corsCustomizer, RSAConfiguration rsaConfiguration, @Value("${config.issuer}") String issuerUrl, @Value("${access.token.time.minutes}") String refreshTokenDuration, @Value("${access.token.time.minutes}") String accessTokenDuration, HostUi hostUi) {
    this.corsCustomizer = corsCustomizer;
    this.rsaConfiguration = rsaConfiguration;
    this.issuerUrl = issuerUrl;
    this.refreshTokenDuration=refreshTokenDuration;
    this.accessTokenDuration=accessTokenDuration;
    this.hostUi = hostUi;
  }

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain securityASFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    corsCustomizer.corsCustomizer(http);
    return http.formLogin().loginPage(hostUi.uriPath(LOGIN_PAGE)).defaultSuccessUrl(hostUi.uriPath(SUCCESS_URL)).and().build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    RegisteredClient registeredClient = RegisteredClient.withId(REGISTERED_CLIENT_ID)
        .clientId(CLIENT_ID)
        .clientSecret(SECRET_KEY)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(hostUi.uriPath(REDIRECT_URI))
        .scope(OidcScopes.OPENID)
        .clientSettings(ClientSettings.builder()
            .requireAuthorizationConsent(true).build())
        .tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(Long.parseLong(accessTokenDuration)))
            .refreshTokenTimeToLive(Duration.ofHours(Long.parseLong(refreshTokenDuration)))
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
    return ProviderSettings.builder().issuer(issuerUrl) .build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() throws Exception{
    RSAKey rsaKey = JwksKeys.generateRSAKey(rsaConfiguration);
    JWKSet set = new JWKSet(rsaKey);
    return (j, sc) -> j.select(set);
  }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
            OidcUserInfoService userInfoService) {
      return context -> {
        if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
          OidcUserInfo userInfo = userInfoService.loadUser(
                  context.getPrincipal().getName());
          context.getClaims().claims(claims ->
                  claims.putAll(userInfo.getClaims()));
        }
      };
    }

}
