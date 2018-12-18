package org.health.supplychain.service;

import org.health.supplychain.entities.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductService implements IProductService{
    @Override
    public boolean saveProduct(Product product) {

        Realm realm = Realm.getDefaultInstance();

        Number currentIdNum = realm.where(Product.class).max("id");
        long id = ((currentIdNum == null)? 0L: currentIdNum.longValue()) + 1;
        product.setId(id);

        realm.beginTransaction();
        final Product savedProduct = realm.copyToRealm(product);
        realm.commitTransaction();

        return savedProduct != null;
    }

    @Override
    public List<Product> getAllProduct() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> products = realm.where(Product.class).findAll();
        if(products != null && products.size() > 0)
            return products.subList(0, products.size());
        else
            return null;
    }

    @Override
    public Product getProductByCode(String code) {
        Realm realm = Realm.getDefaultInstance();
        Product product = realm.where(Product.class).equalTo("code", code).findFirst();
        return product;
    }

    @Override
    public boolean updateProduct(Product product) {

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        final Product savedProduct = realm.copyToRealm(product);
        realm.commitTransaction();

        return savedProduct != null;
    }

    @Override
    public boolean updateProduct(Product exitingProduct, Product incomingProduct) {

        return false;
    }
}
