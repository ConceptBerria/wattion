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

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.conceptberria.wattion.app.PreferencesProvider;
import com.conceptberria.wattion.client.ConceptberriaCoreClientImpl;
import com.conceptberria.wattion.client.PaginaEsiosClientImpl;
import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.exception.ConceptberriaCoreConnectFailException;
import com.conceptberria.wattion.exception.ConceptberriaCoreNoDataException;
import com.conceptberria.wattion.exception.PaginaEsiosConnectFailException;
import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.service.EnergyPriceServiceImpl;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.viewprice.R;
import com.conceptberria.wattion.viewprice.R.drawable;
import com.conceptberria.wattion.viewprice.ViewPrice;

import java.util.Date;

/**
 * Created by ConceptBerria on 30/09/2014.
 * Clase hija de  {@link IntentService} notifica del peor o mejor precio según configuración y actualiza la información del precio de la energía.
 *
 */
public class MakeNotification extends IntentService {

    private static final int NOTIF_ALERT = 123456788;
    private static final int HOUR = 20;


    public MakeNotification() {
        super("MakeNotification");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (EnergyPriceServiceImpl.getInstance().existInfo(CalendarUtil.getInstance().getNow())) {
            process();
        } else {
            Date now = CalendarUtil.getInstance().getNow();
            updateEnergyPrice(now);
        }

        Date tomorrow = CalendarUtil.getInstance().getTomorrow();
        if (!EnergyPriceServiceImpl.getInstance().existInfo(tomorrow) && CalendarUtil.getInstance().isMayorHour(HOUR)) {
            updateEnergyPrice(tomorrow);
        }

    }

    /**
     * Actualiza la información del precio de la energía de la fecha.
     * @param fecha
     */
    private void updateEnergyPrice(final Date fecha) {
        EnergyPriceDayDto energyPriceDayCore;
        boolean errorCore = false;
        try {
            energyPriceDayCore = ConceptberriaCoreClientImpl.getInstance().getEnergyPriceCore(fecha);
            EnergyPriceServiceImpl.getInstance().persistEnergyPriceDay(fecha, energyPriceDayCore);
        } catch (ConceptberriaCoreNoDataException e) {
            //donothng
        } catch (ConceptberriaCoreConnectFailException e) {
            errorCore = true;
        }
        try {
            if (errorCore) {
                PaginaEsiosDto paginaEsiosDTO = PaginaEsiosClientImpl.getInstance().getEnergyPriceMinisterio(fecha);
                EnergyPriceServiceImpl.getInstance().persistEnergyPriceDay(fecha, paginaEsiosDTO);
            }

        } catch (PaginaEsiosConnectFailException e) {
            //donothng
        }

    }

    /**
     * Procesa la generación de la notificación
     */
    private void process() {

        boolean notificacionRango = PreferencesProvider.getInstance().getNotificacionRango();
        boolean notificacionBestPrice = PreferencesProvider.getInstance().getNotificacionBestPrice();
        boolean notificacionWorstPrice = PreferencesProvider.getInstance().getNotificacionWorstPrice();
        int desdeHora = PreferencesProvider.getInstance().getDesdeHoraNotifi();
        int hastaHora = PreferencesProvider.getInstance().getHastaHoraNotifi();
        hastaHora = hastaHora != 0 ? hastaHora : 24;

        EnergyPriceDay energyService = EnergyPriceServiceImpl.getInstance().getEnergyPriceDay(CalendarUtil.getInstance().getNow());
        EnergyPrice bestPrice = energyService.getBestEnergyPrice();
        EnergyPrice worstPrice = energyService.getWorstEnergyPrice();
        NotificationCompat.Builder mBuilder = null;
        boolean notify = false;

        if (CalendarUtil.getInstance().isSameHour(bestPrice.getHora()) && notificacionBestPrice) {
            mBuilder = getBestNotification(bestPrice);
            notify = isNotify(notificacionRango, desdeHora, hastaHora);

        } else if (CalendarUtil.getInstance().isSameHour(worstPrice.getHora()) && notificacionWorstPrice) {

            mBuilder = getWorstNotification(worstPrice);
            notify = isNotify(notificacionRango, desdeHora, hastaHora);
        }

        if (notify) {
            notify(mBuilder);
        }

    }

    /**
     * Devuelve si es necesario notificar según la configuración de la aplicación
     * @param notificacionRango
     * @param desdeHora
     * @param hastaHora
     * @return
     */
    private boolean isNotify(final boolean notificacionRango, final int desdeHora, final int hastaHora) {
        if (notificacionRango) {
            return CalendarUtil.getInstance().isMayorHour(desdeHora) && CalendarUtil.getInstance().isMinorHour(hastaHora);
        } else {
            return true;
        }


    }

    /**
     * Notifica la notificación generada
     * @param mBuilder
     */
    private void notify(final NotificationCompat.Builder mBuilder) {
        PendingIntent resultPendingIntent;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            resultPendingIntent = getPendingIntent();
        } else {
            resultPendingIntent = getPendingIntentWithStackBuilder();
        }

        mBuilder.setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ALERT, mBuilder.build());
    }

    /**
     * Para versiones mejores q Jelly Bean genera el intent directamente
     * @return
     */
    private PendingIntent getPendingIntent() {

        Intent resultIntent = new Intent(this, ViewPrice.class);
        return PendingIntent.getActivity(this,
                0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

    /**
     * Para versiones mas alta que JellyBean genera un intent añadiendo en la pila de tareas la actividad {@link ViewPrice}
     * @return el intent
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private PendingIntent getPendingIntentWithStackBuilder() {
        Intent resultIntent = new Intent(this, ViewPrice.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ViewPrice.class);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    /**
     * Genera una notificación de peor precio
     * @param precio información a mostrar en la notificación
     * @return
     */
    private NotificationCompat.Builder getWorstNotification(final EnergyPrice precio) {
        return
                new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_watti_off)
                        .setLargeIcon(((BitmapDrawable) this.getResources()
                                .getDrawable(drawable.ic_launcher)).getBitmap())
                        .setContentTitle(getString(R.string.notif_worst_header)+ precio.getHoraAsShortStringRange())
                        .setContentText(getString(R.string.notif_worst_body) + precio.getPriceAsString())
                        .setAutoCancel(true);
    }

    /**
     * Genera una notificación de mejor precio
     * @param precio información a mostrar en la notificación
     * @return
     */
    private NotificationCompat.Builder getBestNotification(final EnergyPrice precio) {
        return new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_watti_on)
                .setLargeIcon(((BitmapDrawable) this.getResources()
                        .getDrawable(drawable.ic_launcher)).getBitmap())
                .setContentTitle(getString(R.string.notif_best_header) + precio.getHoraAsShortStringRange())
                .setContentText(getString(R.string.notif_best_body)+ precio.getPriceAsString())
                .setAutoCancel(true);
    }


}
