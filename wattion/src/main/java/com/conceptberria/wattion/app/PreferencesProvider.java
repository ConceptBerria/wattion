package com.conceptberria.wattion.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by ConceptBerria on 12/03/2016.
 * Clase para obtener las preferencias de la aplicación
 */
public class PreferencesProvider {

    private static PreferencesProvider instance;

    public static synchronized PreferencesProvider getInstance() {
        if (instance == null) {
            instance = new PreferencesProvider();
        }
        return instance;
    }

    public int getDivisa() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        String divisaString = preferences.getString("preferences_divisa", "1");
        return Integer.valueOf(divisaString);
    }

    public boolean getNotificacionRango() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        return preferences.getBoolean("notificar_horario", true);
    }

    public boolean getNotificacionBestPrice() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        return preferences.getBoolean("notifications_best_price", true);
    }

    public boolean getNotificacionWorstPrice() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        return preferences.getBoolean("notifications_worst_price", true);
    }

    public int getDesdeHoraNotifi() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        return preferences.getInt("desde_hora", 8);
    }

    public int getHastaHoraNotifi() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        return preferences.getInt("hasta_hora", 22);
    }

    public int getTarifa() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnergyControlApp.getContext());
        String tarifaString = preferences.getString("preferences_tarifa", "1");
        return Integer.valueOf(tarifaString);
    }
}
