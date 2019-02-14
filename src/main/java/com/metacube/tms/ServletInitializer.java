package com.metacube.tms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TrainingManagementSystemApplication.class);
	}

}
