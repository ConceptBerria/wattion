package com.conceptberria.wattion.app;

import android.app.Application;
import android.content.Context;

import com.conceptberria.wattion.viewprice.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */

public class EnergyControlApp extends Application {
    private Tracker mTracker;
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */


    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }



    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    public synchronized Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}