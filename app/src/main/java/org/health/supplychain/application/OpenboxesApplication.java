package org.health.supplychain.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class OpenboxesApplication extends Application {

    private final static String REALM_DATABASE_NAME = "openboxes.realm";
    private final static int VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name(REALM_DATABASE_NAME)
                .schemaVersion(VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
