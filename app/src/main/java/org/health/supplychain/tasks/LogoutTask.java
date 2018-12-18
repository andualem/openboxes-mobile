package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.webservice.entities.ProductListResponse;
import org.health.supplychain.webservice.service.APIServices;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class LogoutTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private Context context;
    private String sessionId;
    private IUserSessionService userSessionService;

    public LogoutTask (Context context, String sessionId){
        this.context = context;
        this.sessionId = sessionId;
    }


    @Override
    protected String doInBackground(Void... voids) {


        try{
            Response<ResponseBody> response = apiServices.logoutUser(sessionId);
            if(response.isSuccessful()){
                userSessionService = new UserSessionService();
                userSessionService.clearUserSessionDetail(context);
            }
        } catch (Exception e) {

        }


        return null;
    }
}
