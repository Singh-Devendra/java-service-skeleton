package com.metacube.tms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="spring")
public class DatabaseConfig {
  
  private Datasource datasource = new Datasource();
  
  public static class Datasource {
    private String url;
    private String username;
    private String password;
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
  }

  public Datasource getDatasource() {
    return datasource;
  }

  public void setDatasource(Datasource datasource) {
    this.datasource = datasource;
  }
}
