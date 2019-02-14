/**
 * 
 */
package com.metacube.tms.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metacube.tms.config.AppConfiguration;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 18-Sep-2018
 */
@RestController
public class AppConfigResource {
	
	@Value("${app.message}")
	private String welcomeMessage;
	
	@Autowired
	private AppConfiguration configuration;

	@GetMapping("/welcome")
	public String retrieveWelcomeMessage() {
		return welcomeMessage;
	}

	@GetMapping("/dynamic-configuration")
	public AppConfiguration dynamicConfiguration() {
		return configuration;
	}

}
