package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Product;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.entities.ProductListResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class SingleProductDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private Long officerId, organizationId;
    private String sessionId;
    private IProductService productService;
    private Context context;
    private String productId;

    public SingleProductDownloadTask(Context context, Long officerId, Long organizationId, String sessionId,
                                     String productId, ImageView imageView){
        this.officerId = officerId;
        this.organizationId = organizationId;
        this.sessionId = sessionId;
        this.productId = productId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Response<org.health.supplychain.webservice.entities.Product> response = apiServices.getProducts(sessionId, this.productId); ;

            if(response.isSuccessful()){
                org.health.supplychain.webservice.entities.Product incomingProduct = response.body();
                List<org.health.supplychain.webservice.entities.Product> incomingProductList;
                incomingProductList = new ArrayList<>();
                incomingProductList.add(incomingProduct);
                List<Product> productList = getMappedProductList(incomingProductList);
                if(productList != null && !productList.isEmpty()) {
                    boolean isCommoditySaved = saveProductList(productList);
//                    super.setSuccess(isCommoditySaved);
                    if(isCommoditySaved)
                        return String.format(Constants.SYNC_SUCCESS_MESSAGE, "input");
                    else
                        return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                } else {
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                }
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
        }

    }

    private boolean saveProductList(List<Product> productsList) {
        boolean isSaved = true;
        productService = new ProductService();
        Product existingProduct;
        for(Product product: productsList){
            existingProduct = productService.getProductByCode(product.getCode());
            if(existingProduct != null){
//                if(existingProduct.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= productService.updateProduct(existingProduct, product);
            } else {
                isSaved &=  productService.saveProduct(product);
            }
        }
        return isSaved;
    }

    private List<Product> getMappedProductList(List<org.health.supplychain.webservice.entities.Product> incomingProductList){
        List<Product> productList;
        if(incomingProductList != null){
            productList = new ArrayList<>();
            Product product;
            ProductCategory productCategory;
            for(org.health.supplychain.webservice.entities.Product productResponse: incomingProductList){
                product = new Product();
                product.setProductId(productResponse.getId());
                product.setName(productResponse.getName());
                product.setCode(productResponse.getProductCode());
                product.setDescription(productResponse.getDescription());
                productCategory = productResponse.getCategory();
                if(productCategory != null) {
                    product.setCategoryId(productCategory.getId());
                    product.setCategoryName(productCategory.getName());
                }

                product.setDateCreated(productResponse.getDateCreated());
                product.setLastUpdated(productResponse.getLastUpdated());
                productList.add(product);
            }
            return productList;
        }
        return null;
    }

}