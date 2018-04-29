package com.iqmsoft.reactor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iqmsoft.reactor.Tickers.Ticker;

import lombok.Data;

@Data
public class Tickers {
	@JsonProperty("USD")
	Ticker USD;

	@Data
	class Ticker {
		double last;
	}
}