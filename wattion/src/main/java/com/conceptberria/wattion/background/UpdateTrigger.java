/*
 * Copyright 2016 ConceptBerria
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.conceptberria.wattion.background;

/**
 * Created by ConceptBerria on 12/03/14.
 * Clase que extiende de {@link android.content.BroadcastReceiver} que lanza la ejecución del
 * servicio en background {@link com.conceptberria.wattion.background.MakeNotification} cada hora
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class UpdateTrigger  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);

        Intent newIntent = new Intent(context, MakeNotification.class);
        PendingIntent pintent = PendingIntent.getService(context, 0, newIntent, 0);

        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, pintent);

    }
}


