package com.alberovalley.novedadesumbria.service.broadcastreceivers;

/**
 * Created by frank on 10/02/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alberovalley.novedadesumbria.service.NewsCheckingService;
import com.alberovalley.novedadesumbria.service.scheduler.Scheduler;
import com.alberovalley.novedadesumbria.utils.AlberoLog;
import com.alberovalley.novedadesumbria.utils.AppConstants;

public class BootReceiver extends BroadcastReceiver {
    // ////////////////////////////////////////////////////////////
    // Life Cycle
    // ////////////////////////////////////////////////////////////
    @Override
    public void onReceive(Context context, Intent intent) {
        AlberoLog.v(this, ".onReceive");
        Context ctx = context.getApplicationContext();
        Intent serviceIntent = new Intent(ctx, NewsCheckingService.class);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String strFreq = sharedPrefs.getString("lpFrecuencia", AppConstants.DEFAULT_FREQ);
        AlberoLog.v(this, ".onReceive sharedPrefs: frecuencia >> " + strFreq);
        long interval = 1000 * 60 * Long.valueOf(strFreq);

        if (Scheduler.cancelScheduledService(ctx, serviceIntent)) {
            AlberoLog.v(this, ".onReceive cancelado posible servicio anterior");
            if (Scheduler.scheduleService(ctx, serviceIntent, interval)) {
                AlberoLog.v(this, ".onReceive arrancado el servicio");
            } else {
                AlberoLog.w(this, ".onReceive ¿por qué falla el iniciar servicio? ");
            }
        } else {
            AlberoLog.w(this, ".onReceive ¿por qué falla el cancelar servicio? ");
        }

    }
}