package com.project.microservices.currencyexchangeservice;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping(value = "/currency-exchange/from/{from}/to/{to}", produces = MediaType.APPLICATION_JSON)
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);

		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}

}
