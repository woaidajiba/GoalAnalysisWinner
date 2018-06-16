package com.goalanalysis.inter;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import com.goalanalysis.inter.GoalAnalysisWinnerApplication;
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GoalAnalysisWinnerApplication.class);
	}

}
