package org.health.supplychain.customexception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Aworkneh on 7/23/2018.
 */

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }


    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected CustomException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
