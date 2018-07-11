package com.fundoonotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration 
{
	@Bean
	public Docket getDocket()
	{
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		ApiSelectorBuilder apiSelectorBuilder = docket.select();
		apiSelectorBuilder = apiSelectorBuilder.apis(RequestHandlerSelectors.any());
		apiSelectorBuilder = apiSelectorBuilder.paths(PathSelectors.any());
		docket = apiSelectorBuilder.build();
		return docket;
	}
}