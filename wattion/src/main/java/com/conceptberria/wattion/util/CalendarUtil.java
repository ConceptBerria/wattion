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
package com.conceptberria.wattion.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ConceptBerria on 29/03/2014.
 * Clase de utilidades para el trabajo con calendario
 */
public class CalendarUtil implements CalendarUtilInterface {

    private static CalendarUtil instance;
    private static final String PRETTY_FORMAT = "HH:mm, dd MMM";

    public static synchronized CalendarUtilInterface getInstance() {
        if (instance == null) {
            instance = new CalendarUtil();
        }
        return instance;
    }


    @Override
    public boolean isSameHour(final Date priceHour) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarPrice = Calendar.getInstance();
        calendar.setTime(priceHour);
        return calendarPrice.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public boolean isMinorHour(final int hora) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) <= hora;
    }


    @Override
    public boolean isMayorHour(final int hora) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) >= hora;
    }


    @Override
    public boolean isSameHour(final Date priceHour, final Date priceHour2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(priceHour);
        calendar2.setTime(priceHour2);
        return calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public Date getNow() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @Override
    public Date getTomorrow() {
        return this.addOneDay(getNow());
    }


    @Override
    public String getPrettyNow() {

        SimpleDateFormat sdf = new SimpleDateFormat(PRETTY_FORMAT);
        return sdf.format(getInstance().getNow());
    }

    @Override
    public String getPrettyDate(final Date fecha) {

        SimpleDateFormat sdf = new SimpleDateFormat(PRETTY_FORMAT);
        return sdf.format(fecha);
    }

    @Override
    public String formatDate(final Date fecha, final String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fecha);
    }

    @Override
    public Date parseDate(final String date, final String format, final Locale locale) {
        SimpleDateFormat sdf = null;
        if (locale == null) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat(format, locale);

        }
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Date parseDate(final String date, final String format) {
        return parseDate(date, format, null);
    }

    public Date addOneHour(final Date priceHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(priceHour);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

    @Override
    public Date addOneDay(final Date priceHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(priceHour);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }


}
