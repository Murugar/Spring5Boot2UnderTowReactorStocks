package com.iqmsoft.reactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor(staticName = "of")
public class Stock {
	String name;
	double price;
}