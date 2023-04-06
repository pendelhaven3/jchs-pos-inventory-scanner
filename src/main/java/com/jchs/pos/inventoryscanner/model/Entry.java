package com.jchs.pos.inventoryscanner.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entry {

	private Product product;
	private String unit;
	private int quantity;
	
}
