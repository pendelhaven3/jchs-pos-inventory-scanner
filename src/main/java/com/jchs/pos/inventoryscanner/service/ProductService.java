package com.jchs.pos.inventoryscanner.service;

import java.util.List;

import com.jchs.pos.inventoryscanner.model.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Product findByBarcode(String barcode);

    void setProducts(List<Product> products);
	
}
