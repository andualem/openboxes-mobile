package org.health.supplychain.customexception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Aworkneh on 7/23/2018.
 */

public class ValidatorException extends RuntimeException {

    public ValidatorException(String message) {
        super(message);
    }


    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected ValidatorException (String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
