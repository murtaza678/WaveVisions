package com.example.murtaza.wavevisions;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Murtaza on 7/4/2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
