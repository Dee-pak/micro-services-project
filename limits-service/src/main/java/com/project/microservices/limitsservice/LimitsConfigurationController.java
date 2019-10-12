package com.project.microservices.limitsservice;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.project.microservices.limitsservice.bean.LimitConfiguration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping(value="/limits", produces = MediaType.APPLICATION_JSON)
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimun());
	}
	
	@GetMapping(value="/fault-tolerance-limits",  produces = MediaType.APPLICATION_JSON)
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveLimitsConfigurations() {
		throw new RuntimeException("Service Down!");
	}
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(9, 999);
	}
}
