package org.health.supplychain.service;

import android.content.Context;


import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Authentication;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.utility.MD5Encryption;
import org.health.supplychain.utility.Utils;
import org.health.supplychain.webservice.entities.ServerTimeResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Headers;
import retrofit2.Response;

/**
 * Created by Aworkneh on 6/4/2018.
 */

public class AuthenticationService implements IAuthenticationService {

    private APIServices apiServices;
    private Authentication authentication;
    private IOfficerService officerService;
    private Context context;
    private IUserSessionService userSessionService;

    public AuthenticationService(){

    }

    public AuthenticationService(Context context){
        this.context = context;
    }


    @Override
    public WeakHashMap<String, String> authenticateUser(String userName, String password) throws IOException {
        boolean isAuthenticated = false;
//        String encryptedPassword = MD5Encryption.MD5(password);
        WeakHashMap<String, String> authStatusMap = null;

        //TODO: offline authentication may not be valid since cookie expires after some time
//        authStatusMap = offlineAuthentication(userName, password);
        if(authStatusMap != null)
            return authStatusMap;
        else { //if(offlineAuthenticationStatus == null) {
            // then check for online authentication
            apiServices = new APIServices();
            authStatusMap = new WeakHashMap<>();

//            if (checkServerTimeMistmatch(authStatusMap)) return authStatusMap;



            Response response = apiServices.authenticateUser(userName, password);
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                String cookieHeader = "", sessionId = "";


                if (headers != null && headers.get(Constants.HEADER_COOKIE) != null) {
                    cookieHeader = headers.get(Constants.HEADER_COOKIE);
                    System.out.println("cookiHeader = " + cookieHeader);
                    sessionId = getSessionId(cookieHeader);

                    boolean isSaved = saveAuthentication(sessionId, userName, password);
                    authStatusMap.put(Constants.AUTHENTICATION_STATUS, Constants.AUTHENTICATION_SUCCESS);
                    authStatusMap.put(Constants.HEADER_JSESSION, sessionId);
                    return authStatusMap;

                } else {
                    authStatusMap.put(Constants.AUTHENTICATION_STATUS, Constants.AUTHENTICATION_FAILURE);
                    return authStatusMap;
                }
            }
            return null;
        }
    }

    private String getSessionId(String cookieHeader){
        String[] cookiHeaderParts = cookieHeader.split(";");
        String sessionId = "";
        for(int i=0; i < cookiHeaderParts.length; i++){

            if(cookiHeaderParts[i].toLowerCase().contains(Constants.HEADER_JSESSION.toLowerCase())){
                sessionId = cookiHeaderParts[i];
                break;
            }
        }

//        String[] sessionIdParts = sessionId.split("=");
//        return sessionIdParts.length == 2? sessionIdParts[1]:"";
        return sessionId.trim();
    }

    private boolean checkServerTimeMistmatch(WeakHashMap<String, String> authStatusMap) throws IOException {
        Response<ServerTimeResponse> timeResponse = apiServices.getServerTime();
        if(timeResponse.isSuccessful()){
            ServerTimeResponse serverTimeResponse = timeResponse.body();
            long serverTime = serverTimeResponse.getCurrentDateTime();
            long localTime = new Date().getTime();
            if(localTime < serverTime - Constants.TIME_SYNC_TOLERANCE_MINUTE
                    || localTime > serverTime + Constants.TIME_SYNC_TOLERANCE_MINUTE){
                authStatusMap.put(Constants.TIME_SYNC_STATUS, Constants.TIME_SYNC_FAILURE);
                authStatusMap.put(Constants.TIME_SYNC_SERVER_TIME, Utils.convertDateToString(serverTime, Constants.DATE_TIME_FORMAT));
                return true;
            }
        }
        return false;
    }

    public boolean saveAuthentication(String sessionId, String username, String password){
        authentication = new Authentication();
        authentication.setUsername(username);
        authentication.setPassword(password);
        authentication.setSessionId(sessionId);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Authentication savedAuthentication = realm.copyToRealm(authentication);
        realm.commitTransaction();

        return savedAuthentication != null;
    }

    public Authentication getCurrentAuthentication() {
        return authentication;
    }

    @Override
    public Authentication getCurrentAuthentication(String sessionId) {
        Realm realm = Realm.getDefaultInstance();
        final Authentication authentication= realm.where(Authentication.class).equalTo("sessionId", sessionId).findFirst();
        return authentication;
    }


    @Override
    public List<String> getAuthenticatedUsernameList() {
        List<String> usernameList = null;
        List<Authentication> authenticationList = null;
//                = Authentication.find(Authentication.class,
//                null, null, null, "id desc", null);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Authentication> authentications =
                realm.where(Authentication.class).findAll();
        if(authentications != null) {
            authenticationList = authentications.subList(0, authentications.size());
        }


        if(authenticationList != null && !authenticationList.isEmpty()) {
            usernameList = new ArrayList<>();
            for(Authentication authentication: authenticationList){
                usernameList.add(authentication.getUsername());
            }
        }
        return usernameList;
    }


    private WeakHashMap<String, String> offlineAuthentication(String username, String password){
        boolean isAuthenticated = false;
        List<Authentication> authenticationList = null;
        username = username.toLowerCase();


//        String whereClause = "lower(username) = ?";
//        String[] whereArgs = {username.toLowerCase()};
//        List<Authentication> authenticationList = Authentication.find(Authentication.class,
//                whereClause, whereArgs, null, "id desc", null);


        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Authentication> authentications =
                realm.where(Authentication.class).equalTo("username", username).findAll();
        if(authentications != null && !authentications.isEmpty()) {
            authenticationList = authentications.subList(0, authentications.size());
        }


        if(authenticationList != null && !authenticationList.isEmpty()) {
            authentication = authenticationList.get(0);
            WeakHashMap<String, String> authStatusMap = new WeakHashMap<>();
            if(authentication.getPassword().equals(password)) {
                authStatusMap.put(Constants.AUTHENTICATION_STATUS, Constants.AUTHENTICATION_SUCCESS);
                authStatusMap.put(Constants.HEADER_JSESSION, authentication.getSessionId());
                return authStatusMap;
            }
            else {
                authStatusMap.put(Constants.AUTHENTICATION_STATUS, Constants.AUTHENTICATION_FAILURE);
                return authStatusMap;
            }
        }

        return null;
    }
}
