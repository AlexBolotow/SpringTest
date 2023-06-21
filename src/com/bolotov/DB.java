package com.bolotov;

import com.bolotov.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
