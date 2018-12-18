package org.health.supplychain.security.service;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import org.health.supplychain.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aworkneh on 8/14/2018.
 */

public class AutoLogoutService {

    private static AutoLogoutService instance;
    private Timer timer;
    private Activity currentActivity;

    private AutoLogoutService(){};

    public static AutoLogoutService getInstance (Activity currentActivity){
        if (instance == null) {
            instance = new AutoLogoutService();
            instance.currentActivity = currentActivity;
        } else {
            return instance;
        }
        return instance;
    }

    public void startTimer() {
        instance.timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        instance.timer.schedule(logoutTimeTask, Constants.SESSION_LOGOUT_DELAY); //auto logout in 5 minutes
    }

    public void stopTimer() {
        if (instance.timer != null) {
            instance.timer.cancel();
            Log.i("Main", "cancel timer");
            instance.timer = null;
        }
    }

    private class LogOutTimerTask extends TimerTask {
        @Override
        public void run() {
            ActivityCompat.finishAffinity(currentActivity);
        }
    }
























//    public static CountDownTimer timer;
//    private String TAG = AutoLogoutService.class.getSimpleName();
//
//    @Override
//    public void onCreate(){
//        super.onCreate();
//        timer = new CountDownTimer(1 *1 * 1000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                Log.v(TAG, "Service Started");
//            }
//
//            public void onFinish() {
//                Log.v(TAG, "Call Logout by Service");
//                IUserSessionService userSessionService = new UserSessionService();
//                userSessionService.clearUserSessionDetail(getApplicationContext());
//                stopSelf();
//            }
//        };
//    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
}
