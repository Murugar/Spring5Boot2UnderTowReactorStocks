package com.iqmsoft.reactor;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class StockController {

	@Autowired
	StockPriceService stockPriceService;

	@RequestMapping(value = "/hello",produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<String> hello() {
		return Mono.just("Hello @" + new Date());
	}

	@RequestMapping(value = "/bitcoin",method = RequestMethod.GET,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Double> getBitcoinPrice() {
		return stockPriceService.rtBitcoinPrice();
	}

	@RequestMapping(value = "/long",method = RequestMethod.GET,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Long> getLong() {
		return stockPriceService.rtLong().delayElements(Duration.ofSeconds(1));
	}

	@RequestMapping(value = "/stocks/{stock}", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Stock> getStockPrice(@PathVariable("stock") String stockName) {
		return stockPriceService.rtStockPrice(stockName).delayElements(Duration.ofMillis(200));
	}
}