package com.jchs.pos.inventoryscanner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jchs.pos.inventoryscanner.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	private Map<String, Product> productsMap = new HashMap<>();
	
	@Override
	public List<Product> getAllProducts() {
		return new ArrayList<>(productsMap.values());
	}

	@Override
	public Product findByBarcode(String barcode) {
		return productsMap.get(barcode);
	}

    @Override
    public void setProducts(List<Product> products) {
        productsMap.clear();
        for (Product product : products) {
            productsMap.put(product.getBarcode(), product);
        }
    }
	
}
