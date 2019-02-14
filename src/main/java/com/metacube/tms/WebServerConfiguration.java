package com.metacube.tms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.filter.state.DefaultStateKeyGenerator;
import org.springframework.security.oauth2.client.filter.state.StateKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.metacube.tms.config.AppConfiguration;

/**
 * @author Manish Sharma
 * @since 4 Nov 2018
 */

@EnableWebSecurity
@Configuration
@EnableOAuth2Sso
public class WebServerConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * Bean creation based on profile env. For Example purpose.
   * 
   */

  @Profile("dev")
  @Bean
  public String devBean() {
    return "dev";
  }

  @Profile("qa")
  @Bean
  public String qaBean() {
    return "qa";
  }

  @Profile("staging")
  @Bean
  public String stagingBean() {
    return "staging";
  }

  @Profile("prod")
  @Bean
  public String prodBean() {
    return "prod";
  }

  @Autowired
  AppConfiguration config;

  @Autowired
  private WRSPrincipalExtractor wrsPrincipalExtractor;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().anyRequest()
        .authenticated().and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedHeaders(Arrays.asList("*"));

    String[] allowedOrigins = config.getSso().getAllowedOrigins().split(",");

    corsConfig.setAllowedOrigins(Arrays.asList(allowedOrigins));
    String[] allowedMethods = config.getSso().getAllowedMethods().split(",");

    corsConfig.setAllowedMethods(Arrays.asList(allowedMethods));
    source.registerCorsConfiguration("/**", corsConfig);
    return new CorsFilter(source);
  }

  @Autowired
  OAuth2ClientContext oauth2ClientContext;

  private Filter ssoFilter() {
    OAuth2ClientAuthenticationProcessingFilter userFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
    OAuth2RestTemplate userTemplate = new OAuth2RestTemplate(user(), oauth2ClientContext);

    AuthorizationCodeAccessTokenProvider authorizationCodeAccessTokenProvider = new AuthorizationCodeAccessTokenProvider() {

      private StateKeyGenerator stateKeyGenerator = new DefaultStateKeyGenerator();

      @Override
      public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
          throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException,
          OAuth2AccessDeniedException {

        AuthorizationCodeResourceDetails resource = (AuthorizationCodeResourceDetails) details;

        if (request.getAuthorizationCode() == null) {
          if (request.getStateKey() == null) {
            throw getRedirectForAuthorization(resource, request);
          }
          obtainAuthorizationCode(resource, request);
        }
        return retrieveToken(request, resource, getParametersForTokenRequest(resource, request),
            getHeadersForTokenRequest(request));

      }

      @Override
      public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource,
          OAuth2RefreshToken refreshToken, AccessTokenRequest request)
          throws UserRedirectRequiredException, OAuth2AccessDeniedException {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("grant_type", "refresh_token");
        form.add("refresh_token", refreshToken.getValue());
        try {
          return retrieveToken(request, resource, form, getHeadersForTokenRequest(request));
        } catch (OAuth2AccessDeniedException e) {
          throw getRedirectForAuthorization((AuthorizationCodeResourceDetails) resource, request);
        }
      }

      private HttpHeaders getHeadersForTokenRequest(AccessTokenRequest request) {
        HttpHeaders headers = new HttpHeaders();
        // No cookie for token request
        return headers;
      }

      private MultiValueMap<String, String> getParametersForTokenRequest(AuthorizationCodeResourceDetails resource,
          AccessTokenRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("grant_type", "authorization_code");
        form.set("code", request.getAuthorizationCode());

        Object preservedState = request.getPreservedState();
        if (request.getStateKey() != null || true) {
          // The token endpoint has no use for the state so we don't send it back, but we
          // are using it
          // for CSRF detection client side...
          if (preservedState == null) {
            throw new InvalidRequestException(
                "Possible CSRF detected - state parameter was required but no state could be found");
          }
        }

        // Extracting the redirect URI from a saved request should ignore the current
        // URI, so it's not simply a call to
        // resource.getRedirectUri()
        String redirectUri = null;
        // Get the redirect uri from the stored state
        if (preservedState instanceof String) {
          // Use the preserved state in preference if it is there
          // TODO: treat redirect URI as a special kind of state (this is a historical
          // mini hack)
          redirectUri = String.valueOf(preservedState);
        } else {
          redirectUri = resource.getRedirectUri(request);
        }

        if (redirectUri != null && !"NONE".equals(redirectUri)) {
          form.set("redirect_uri", redirectUri);
        }

        return form;

      }

      private UserRedirectRequiredException getRedirectForAuthorization(AuthorizationCodeResourceDetails resource,
          AccessTokenRequest request) {

        // we don't have an authorization code yet. So first get that.
        TreeMap<String, String> requestParameters = new TreeMap<String, String>();
        requestParameters.put("response_type", "code"); // oauth2 spec, section 3
        requestParameters.put("client_id", resource.getClientId());
        if (request.get("email") != null && request.get("email").get(0) != null) {
          requestParameters.put("email", request.get("email").get(0));
        }

        // requestParameters.put("login_page", email);

        // Client secret is not required in the initial authorization request

        String redirectUri = resource.getRedirectUri(request);
        if (redirectUri != null) {
          requestParameters.put("redirect_uri", redirectUri);
        }

        if (resource.isScoped()) {

          StringBuilder builder = new StringBuilder();
          List<String> scope = resource.getScope();

          if (scope != null) {
            Iterator<String> scopeIt = scope.iterator();
            while (scopeIt.hasNext()) {
              builder.append(scopeIt.next());
              if (scopeIt.hasNext()) {
                builder.append(' ');
              }
            }
          }

          requestParameters.put("scope", builder.toString());
        }

        UserRedirectRequiredException redirectException = new UserRedirectRequiredException(
            resource.getUserAuthorizationUri(), requestParameters);

        String stateKey = stateKeyGenerator.generateKey(resource);
        redirectException.setStateKey(stateKey);
        request.setStateKey(stateKey);
        redirectException.setStateToPreserve(redirectUri);
        request.setPreservedState(redirectUri);

        return redirectException;

      }

    };

    AccessTokenProviderChain provider = new AccessTokenProviderChain(
        Arrays.asList(authorizationCodeAccessTokenProvider));
    userTemplate.setAccessTokenProvider(provider);

    userFilter.setRestTemplate(userTemplate);
    UserInfoTokenServices tokenServices = new UserInfoTokenServices(userResource().getUserInfoUri(),
        user().getClientId());
    tokenServices.setRestTemplate(userTemplate);
    tokenServices.setPrincipalExtractor(wrsPrincipalExtractor);
    userFilter.setTokenServices(tokenServices);
    return userFilter;
  }

  @Bean
  @ConfigurationProperties("security.oauth2.client")
  public AuthorizationCodeResourceDetails user() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @Primary
  @ConfigurationProperties("security.oauth2.resource")
  public ResourceServerProperties userResource() {
    return new ResourceServerProperties();
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    return registration;
  }
}
