package org.health.supplychain.service;


import org.health.supplychain.customexception.CustomException;
import org.health.supplychain.entities.Authentication;
import org.health.supplychain.entities.Facility;

import java.io.IOException;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by Aworkneh on 6/4/2018.
 */

public interface IAuthenticationService {

    public WeakHashMap<String, String> authenticateUser(String userName, String password) throws IOException;

    public Authentication getCurrentAuthentication();

    public Authentication getCurrentAuthentication(String sessionId);

    public List<String> getAuthenticatedUsernameList();


}
