package com.conceptberria.wattion.viewprice;

import android.content.Context;

import com.conceptberria.wattion.viewprice.R.xml;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * A collection of Google Analytics trackers. Fetch the tracker you need using
 * {@code AnalyticsTrackers.getInstance().get(...)}
 * <p/>
 * This code was generated by Android Studio but can be safely modified by
 * hand at this point.
 * <p/>
 * TODO: Call {@link #initialize(Context)} from an entry point in your app
 * before using this!
 */
public final class AnalyticsTrackers {

  public enum Target {
    APP,
    // Add more trackers here if you need, and update the code in #get(Target) below
  }

  private static AnalyticsTrackers sInstance;

  public static synchronized void initialize(Context context) {
    if (AnalyticsTrackers.sInstance != null) {
      throw new IllegalStateException("Extra call to initialize analytics trackers");
    }

    AnalyticsTrackers.sInstance = new AnalyticsTrackers(context);
  }

  public static synchronized AnalyticsTrackers getInstance() {
    if (AnalyticsTrackers.sInstance == null) {
      throw new IllegalStateException("Call initialize() before getInstance()");
    }

    return AnalyticsTrackers.sInstance;
  }

  private final Map<AnalyticsTrackers.Target, Tracker> mTrackers = new HashMap<AnalyticsTrackers.Target, Tracker>();
  private final Context mContext;

  /**
   * Don't instantiate directly - use {@link #getInstance()} instead.
   */
  private AnalyticsTrackers(Context context) {
    this.mContext = context.getApplicationContext();
  }

  public synchronized Tracker get(AnalyticsTrackers.Target target) {
    if (!this.mTrackers.containsKey(target)) {
      Tracker tracker;
      switch (target) {
        case APP:
          tracker = GoogleAnalytics.getInstance(this.mContext).newTracker(xml.app_tracker);
          break;
        default:
          throw new IllegalArgumentException("Unhandled analytics target " + target);
      }
      this.mTrackers.put(target, tracker);
    }

    return this.mTrackers.get(target);
  }
}
