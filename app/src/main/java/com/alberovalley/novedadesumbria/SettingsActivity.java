package com.alberovalley.novedadesumbria;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.alberovalley.novedadesumbria.service.NewsCheckingService;
import com.alberovalley.novedadesumbria.service.scheduler.Scheduler;
import com.alberovalley.novedadesumbria.utils.AlberoLog;

//import com.bugsense.trace.BugSenseHandler;

public class SettingsActivity extends PreferenceActivity {
    // ////////////////////////////////////////////////////////////
    // Life Cycle
    // ////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BugSenseHandler.initAndStartSession(getApplicationContext(), AppConstants.BUGSENSE_API_KEY);
        setContentView(R.layout.settings_activity);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        cancelAlarmService();
        setAlarmService();
    }

    @Override
    protected void onStop() {
        AlberoLog.v(this, ".onStop");
        cancelAlarmService();
        setAlarmService();

        super.onStop();
    }

    // ////////////////////////////////////////////////////////////
    // Methods
    // ////////////////////////////////////////////////////////////

    /**
     * To cancel pre-existing alarms - if any - for this service
     */
    private void cancelAlarmService() {
        AlberoLog.v(this, ".cancelAlarmService");
        Intent serviceIntent = new Intent(getApplicationContext(), NewsCheckingService.class);
        Scheduler.cancelScheduledService(getApplicationContext(), serviceIntent);
    }

    /**
     * To set the alarm to check on the website according to the specified frequency
     */
    private void setAlarmService() {
        AlberoLog.v(this, ".setAlarmService");
        Context context = getApplicationContext();
        Intent serviceIntent = new Intent(getApplicationContext(), NewsCheckingService.class);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String strFreq = sharedPrefs.getString("lpFrecuencia", "60");
        AlberoLog.v(this, ".setAlarmService sharedPrefs: frecuencia >> " + strFreq);
        long frequency = 1000 * 60 * Long.valueOf(strFreq);
        Scheduler.scheduleService(getApplicationContext(),
                serviceIntent,
                frequency
        );

    }

}