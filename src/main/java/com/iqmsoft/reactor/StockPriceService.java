package com.iqmsoft.reactor;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Flux;

@Service
public class StockPriceService {

	RestTemplate restTemplate = new RestTemplate();

	double bitcoinPrice() {
		Tickers tickers= restTemplate.getForObject("https://blockchain.info/ticker",Tickers.class);
		return tickers.USD.last;
	}

	Flux<Long> rtLong() {
		return Flux
				.generate(
						() -> 1L,
						(l, sink) -> {
							sink.next(l);
							if (l > 100) sink.complete();
							return l + l;
						});
	}

	Flux<Stock> rtStockPrice(String stockName) {
		return Flux.<Stock,Stock>generate(
				() -> Stock.of(stockName, 12.2),
				(stock, sink) -> {
				double step = ThreadLocalRandom.current().nextInt(10) / 10d;
					if (stock.getPrice() > 14d) {
						sink.complete();
					}
					sink.next(Stock.of(stock.getName(), stock.getPrice() + step));
					return Stock.of(stock.getName(), stock.getPrice() + step);
				});
	}

	Flux<Double> rtBitcoinPrice() {
		return Flux.interval(Duration.ofSeconds(1))
				.map(count -> bitcoinPrice()).distinct();
	}
}