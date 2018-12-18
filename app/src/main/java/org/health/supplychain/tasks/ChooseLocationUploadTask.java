package org.health.supplychain.tasks;

import android.os.AsyncTask;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Product;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.service.APIServices;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChooseLocationUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;
    private String session, locationId;

    public ChooseLocationUploadTask(String session, String locationId) {
        this.session = session;
        this.locationId = locationId;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            Response<ResponseBody> response = apiServices.chooseLocation(this.session, this.locationId);
            if(response.isSuccessful()){
                boolean isStatusUpdated = true;
                if(isStatusUpdated && !hasUnSyncedRecord) {
//                    super.setSuccess(isStatusUpdated);
                    return String.format(Constants.SYNC_SUCCESS_MESSAGE, "choose location");
                }
                else
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "choose location");
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "choose location");
        }

    }

}
