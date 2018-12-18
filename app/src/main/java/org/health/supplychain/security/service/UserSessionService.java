package org.health.supplychain.security.service;

import android.content.Context;
import android.content.SharedPreferences;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Authentication;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.tasks.LogoutTask;
import org.health.supplychain.webservice.service.APIServices;

import java.io.IOException;
import java.util.Set;
import retrofit2.Response;

/**
 * Created by Aworkneh on 6/7/2018.
 */

public class UserSessionService implements IUserSessionService {

    public static final String SESSION_PREFERENCES = "SESSION_PREFERENCES" ;
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String SESSION_ID = "SESSION_ID";
    public static final String LOCATION_ID = "LOCATION_ID";

    public APIServices apiServices;
    SharedPreferences sharedpreferences;


    @Override
    public void createOrUpdateUserSessionDetail(Context context, Authentication authentication) {
        sharedpreferences = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if(authentication != null) {
            editor.putString(USER_NAME, authentication.getUsername());
            editor.putString (PASSWORD, authentication.getPassword());
            editor.putString (SESSION_ID, authentication.getSessionId());
            editor.commit();
        }

    }

    @Override
    public void updateLocationId(Context context, String locationId) {
        sharedpreferences = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if(locationId != null) {
            editor.putString(LOCATION_ID, locationId);
            editor.commit();
        }
    }


    @Override
    public UserSession getUserSessionDetail(Context context) {
        UserSession userSession = new UserSession();
        sharedpreferences = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        userSession.setUsername(sharedpreferences.getString(USER_NAME, ""));
        userSession.setPassword(sharedpreferences.getString(PASSWORD, ""));
        userSession.setSessionId(sharedpreferences.getString(SESSION_ID, ""));
        userSession.setLocationId(sharedpreferences.getString(LOCATION_ID, ""));
        return userSession;
    }

    @Override
    public void clearUserSessionDetail(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void logoutUser(Context context) {
        UserSession currentUserSession = getUserSessionDetail(context);
        if(currentUserSession != null) {
            apiServices = new APIServices();
            LogoutTask logoutTask = new LogoutTask(context, currentUserSession.getSessionId());
            logoutTask.execute();

            //clear local session
//            this.clearUserSessionDetail(context);
        }


    }
}
