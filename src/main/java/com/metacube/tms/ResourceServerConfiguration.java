package com.metacube.tms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${resource.id:spring-boot-application}")
	private String resourceId;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuthRequestedMatcher()).authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated();
	}

	private static class OAuthRequestedMatcher implements RequestMatcher {
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			// Determine if the client request contained an OAuth Authorization
			boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
			boolean haveAccessToken = false;
			if (haveOauth2Token) {
				String[] tokens = auth.split(" ");
				if (tokens.length > 1) {
					haveAccessToken = true;
				}
			}
			return haveAccessToken;
		}
	}

}
