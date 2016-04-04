package com.conceptberria.wattion.viewprice;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.conceptberria.wattion.viewprice.R.xml;

import java.util.List;


public class SettingsActivity extends AppCompatPreferenceActivity {
    @TargetApi(VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setupActionBar();

    }

    private void setupActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Populate the activity with the top-level headers.
     */

    @TargetApi(VERSION_CODES.HONEYCOMB)
    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        this.loadHeadersFromResource(xml.pref_headers, target);
    }
    @Override
    protected boolean isValidFragment (String fragmentName) {
        return Prefs1Fragment.class.getName().equals(fragmentName) || Prefs2Fragment.class.getName().equals(fragmentName);
    }
    /**
     * This fragment shows the preferences for the first header.
     */
    @TargetApi(VERSION_CODES.HONEYCOMB)
    public static class Prefs1Fragment extends PreferenceFragment {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                int xmlPreferences;
                if (VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN) {
                    xmlPreferences= xml.pref_notification;
                }else{
                    xmlPreferences= xml.pref_notification_compatibility;
                }
                PreferenceManager.setDefaultValues(this.getActivity(),
                        xml.pref_notification, false);
                this.addPreferencesFromResource(xmlPreferences);


            }

    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    public static class Prefs2Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(this.getActivity(),
                    xml.pref_general, false);
            this.addPreferencesFromResource(xml.pref_general);
        }
    }


}

