package com.fitness.centrale.mobile;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FireBaseEventLogger {

    public enum Events {
        NEW_ACTIVITY("NEW_ACTIVITY");

        final String value;

        Events(String value) {
            this.value = value;
        }
    }

    private FirebaseAnalytics analytics;
    private static FireBaseEventLogger logger;

    private FireBaseEventLogger(Context ctx) {
        analytics = FirebaseAnalytics.getInstance(ctx);
    }

    public static FireBaseEventLogger getLogger(Context ctx){
        if (logger == null){

            logger = new FireBaseEventLogger(ctx);
        }
        return logger;
    }

    public void logEvent(Events event, String message){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, message);
        logEvent(event, bundle);
    }

    public void logEvent(Events event, Bundle params){
        analytics.logEvent(event.value, params);
    }



}
