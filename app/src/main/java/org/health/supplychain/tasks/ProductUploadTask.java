package org.health.supplychain.tasks;

import android.os.AsyncTask;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Product;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.service.APIServices;

import java.util.Date;

import retrofit2.Response;

public class ProductUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private String session;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;

    public ProductUploadTask(String session, Product product) {
        this.session = session;
        this.product = product;
    }

    @Override
    protected String doInBackground(Void... voids) {

        org.health.supplychain.webservice.entities.Product product = getMappedProduct(this.product);
        if(product == null) {
//            this.setNoDataToUpload(Constants.NO_DATA_TO_UPLOAD);
            return Constants.NO_CONTENT_TO_SYNC;
        }

        try {
            Response<org.health.supplychain.webservice.entities.Product> response = apiServices.pushProduct(session, product);
            if(response.isSuccessful()){
                boolean isStatusUpdated = updateSyncStatus(response.body());
                if(isStatusUpdated && !hasUnSyncedRecord) {
//                    super.setSuccess(isStatusUpdated);
                    return String.format(Constants.SYNC_SUCCESS_MESSAGE, "product");
                }
                else
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "product");
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "product");
        }

    }


    private org.health.supplychain.webservice.entities.Product getMappedProduct(Product product){
        org.health.supplychain.webservice.entities.Product productRequest;
        if(product != null){
            productRequest = new org.health.supplychain.webservice.entities.Product();
            productRequest.setId(product.getProductId());
            productRequest.setProductCode(product.getCode());
            productRequest.setName(product.getName());
            productRequest.setDescription(product.getDescription());
            productRequest.setCategory(getMappedProductCategory(product.getCategoryId(), product.getCategoryName()));
            productRequest.setDateCreated(product.getDateCreated());
            productRequest.setLastUpdated(product.getLastUpdated());
            return productRequest;
        }
        return null;
    }


    private ProductCategory getMappedProductCategory(String categoryId, String categoryName){
        ProductCategory category = new ProductCategory();
        category.setId(categoryId);
        category.setName(categoryName);
        return category;
    }


    private boolean updateSyncStatus(org.health.supplychain.webservice.entities.Product product){
        boolean isUpdated = true;
        IProductService productService = new ProductService();
        if(product != null){

            if(product.getName() != null && product.getName().equals(this.product.getName())){
                this.product.setSyncStatus(Constants.SYNC_SUCCESS);
                this.product.setSyncDate(new Date().getTime());
                isUpdated = productService.updateProduct(this.product);
            } else {
                this.product.setSyncStatus(Constants.SYNC_FAILURE);
                isUpdated = productService.updateProduct(this.product);
            }
            return isUpdated;
        }
        return false;
    }
}
