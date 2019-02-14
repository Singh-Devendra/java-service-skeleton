/**
 * 
 */
package com.metacube.tms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 18-Sep-2018
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

  private SSO sso = new SSO();

  private String name;
  private String message;
  private String organization;
  private String version;

  public static class SSO {
    private String scheme;
    private String host;
    private String path;
    private String allowedOrigins;
    private String allowedMethods;
    
    
    public String getAllowedOrigins() {
      return allowedOrigins;
    }

    public void setAllowedOrigins(String allowedOrigins) {
      this.allowedOrigins = allowedOrigins;
    }

    public String getAllowedMethods() {
      return allowedMethods;
    }

    public void setAllowedMethods(String allowedMethods) {
      this.allowedMethods = allowedMethods;
    }

    public String getScheme() {
      return scheme;
    }

    public void setScheme(String scheme) {
      this.scheme = scheme;
    }

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }
  //End of SSO
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public SSO getSso() {
    return sso;
  }

  public void setSso(SSO sso) {
    this.sso = sso;
  }
}