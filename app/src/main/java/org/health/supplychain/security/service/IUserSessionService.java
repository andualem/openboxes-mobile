package org.health.supplychain.security.service;

import android.content.Context;

import org.health.supplychain.entities.Authentication;
import org.health.supplychain.security.entities.UserSession;

/**
 * Created by Aworkneh on 6/7/2018.
 */

public interface IUserSessionService {

    public void createOrUpdateUserSessionDetail(Context context, Authentication authentication);

    public void updateLocationId(Context context, String locationId);

    public UserSession getUserSessionDetail(Context context);

    public void clearUserSessionDetail(Context context);

    public void logoutUser(Context context);
}
