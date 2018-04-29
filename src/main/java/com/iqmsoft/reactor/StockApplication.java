package com.iqmsoft.reactor;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class StockApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}


	public void intFluxWithBackpressure() {
		Flux<Integer> f = Flux.range(1, 100).map(i -> i * 2).delayElements(Duration.ofMillis(100));
		f.subscribe(new Subscriber<Integer>() {
			Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				subscription.request(1);
				this.subscription = subscription;

			}

			@Override
			public void onNext(Integer integer) {
				this.subscription.request(10);
				System.out.println("integer = " + integer);
			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onComplete() {
				System.out.println("Completed");
			}
		});
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		this.intFluxWithBackpressure();
	}
}


