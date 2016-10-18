package com.lnyp.joke.activities;

import android.app.Application;

import im.fir.sdk.FIR;

public class MyApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        FIR.init(this);
    }
}