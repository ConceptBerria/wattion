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
package com.conceptberria.wattion.model;

import com.conceptberria.wattion.app.PreferencesProvider;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.util.MathUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ConceptBerria on 9/03/14.
 * Precio de la energía en una hora determinada
 */
public class EnergyPrice {


    private BigDecimal price;
    private Date hora;
    private long id;
    private static final String HORA_FORMAT = "HH:mm";
    private static final String HORA_FORMAT_SHORT = "HH";
    public static final int EURO_MILES_KW = 3;
    public static final int CENTIMO_EURO_KW = 2;
    public static final int EURO_KW = 1;


    public EnergyPrice(Date hora, BigDecimal price) {
        this.hora = hora;
        this.price = price;
    }

    public EnergyPrice(Date hora, BigDecimal price, long id) {
        this.hora = hora;
        this.price = price;
        this.setId(id);
    }

    public EnergyPrice(String hora, String price) {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        try {
            this.hora = sdf.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = new BigDecimal(price);
    }

    public EnergyPrice(String hora, String price, long id) {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        try {
            this.hora = sdf.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = new BigDecimal(price);
        this.setId(id);
    }

    public EnergyPrice() {

    }


    public String getHoraAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        return sdf.format(hora);
    }

    public String getPriceAsString() {

        return MathUtil.getInstance().formatPrecio(getPriceDivisa());


    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public void setHora(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        try {
            this.hora = sdf.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getHora() {
        return hora;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPriceDivisa() {

        switch (PreferencesProvider.getInstance().getDivisa()) {
            case CENTIMO_EURO_KW:
                return price.multiply(new BigDecimal(100));
            case EURO_KW:
                return price;
            case EURO_MILES_KW:
                return price.multiply(new BigDecimal(1000));
            default:
                return price;
        }
    }

    public long getId() {
        return id;
    }


    public String getHoraAsStringRange() {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        return sdf.format(hora) + " - " + sdf.format(CalendarUtil.getInstance().addOneHour(hora));
    }

    public String getHoraAsShortStringRange() {
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT_SHORT);
        return sdf.format(hora) + " a " + sdf.format(CalendarUtil.getInstance().addOneHour(hora)) + " h";
        // return sdf.format(hora) + "h";
    }
}
