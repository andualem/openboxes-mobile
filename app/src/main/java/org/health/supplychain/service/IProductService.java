package org.health.supplychain.service;

import org.health.supplychain.entities.Product;

import java.util.List;

public interface IProductService {

    public boolean saveProduct(Product product);

    public List<Product> getAllProduct();

    public Product getProductByCode(String code);

    public boolean updateProduct(Product product);

    public boolean updateProduct(Product exitingProduct, Product incomingProduct);
}
