package com.metacube.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */

@ComponentScan("com.metacube.tms*")
@EnableJpaRepositories("com.metacube.tms*")
@EntityScan("com.metacube.tms*")
@Configuration
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // TODO - enable Spring Security later on
public class TrainingManagementSystemApplication  {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(TrainingManagementSystemApplication.class, args);

    for (String name : applicationContext.getBeanDefinitionNames()) {
      System.out.println(name);
    }
  }

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

  
}
