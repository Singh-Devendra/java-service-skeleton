/**
 * 
 */
package com.metacube.tms.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
@Configuration
@EnableSwagger2
public class SpringFoxSwaggerConfiguration {

	/**
	 * Custom configuration for Swagger UI API Info section
	 * @return
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfo("TMS", "Training Management System", "1.0.0", "http://www.metacube.com",
				new Contact("Metacube", "http://www.metacube.com", "yash.sharma@metacube.com"), "LICENSE", "LICENSE URL", // TODO add Git urls
				Collections.emptyList()); 
	}

	/**
	 * Create API Docket bean for Swagger
	 * @return
	 */
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.metacube.tms.resource"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}
}
