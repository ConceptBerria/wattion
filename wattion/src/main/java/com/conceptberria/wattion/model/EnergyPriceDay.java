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

import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.util.MathUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by ConceptBerria on 9/03/14.
 * Precios por hora del día
 */
public class EnergyPriceDay {


    public void setEnergyPerHour(List<EnergyPrice> energyPerHour) {
        this.energyPerHour = energyPerHour;
    }

    private List<EnergyPrice> energyPerHour;

    public EnergyPriceDay(final List<EnergyPrice> energyPerHour) {
        this.energyPerHour = energyPerHour;
    }

    private final Comparator compareDate;

    {
        compareDate = new Comparator<EnergyPrice>() {
            @Override
            public int compare(EnergyPrice price1, EnergyPrice price2) {
                return price1.getHora().compareTo(price2.getHora());
            }
        };
    }

    private final Comparator comparePrice;

    {
        comparePrice = new Comparator<EnergyPrice>() {
            @Override
            public int compare(EnergyPrice price1, EnergyPrice price2) {
                return price1.getPriceDivisa().compareTo(price2.getPriceDivisa());
            }
        };
    }

    private final Comparator comparePriceReserve;

    {
        comparePriceReserve = new Comparator<EnergyPrice>() {
            @Override
            public int compare(EnergyPrice price1, EnergyPrice price2) {
                return price2.getPriceDivisa().compareTo(price1.getPriceDivisa());
            }
        };
    }


    public List<EnergyPrice> getEnergyPricePerHour() {
        Collections.sort(energyPerHour, compareDate);
        return energyPerHour;
    }

    /**
     * Obtiene el precio actual
     * @return
     */
    public EnergyPrice getEnergyPrice() {

        for (EnergyPrice energyPrice : energyPerHour) {

            if (CalendarUtil.getInstance().isSameHour(energyPrice.getHora())) {
                return energyPrice;
            }
        }

        return null;
    }

    /**
     * Devuelve el mejor precio del día
     * @return
     */
    public EnergyPrice getBestEnergyPrice() {

        return this.getBestEnergyPrice(this.energyPerHour);
    }

    /**
     * Devuelve el peor precio del día
     * @return
     */
    public EnergyPrice getWorstEnergyPrice() {
        return getWorstEnergyPrice(this.energyPerHour);
    }

    /**
     * Devuelve el peor precio de la lista
     * @param energyPerHour
     * @return
     */
    public EnergyPrice getWorstEnergyPrice(final List<EnergyPrice> energyPerHour) {

        Collections.sort(energyPerHour, comparePriceReserve);
        return energyPerHour.get(0);
    }

    /**
     * Devuelve el mejor precio de la lista
     * @param energyPerHour
     * @return
     */
    public EnergyPrice getBestEnergyPrice(final List<EnergyPrice> energyPerHour) {
        Collections.sort(energyPerHour, comparePrice);
        return energyPerHour.get(0);
    }

    /**
     * Devuelve la lista de los siguientes precios del día
     * @param withNow indica si se incluye el rango de horas actual
     * @return
     */
    public List<EnergyPrice> getNextEnergyPrices(final boolean withNow) {
        Collections.sort(energyPerHour, compareDate);
        int principio = withNow ? Long.valueOf(getEnergyPrice().getId()).intValue() - 1 : Long.valueOf(getEnergyPrice().getId()).intValue();
        return energyPerHour.subList(principio, energyPerHour.size());

    }

    /**
     * Obtiene la media de todos los precios del día en String formateado
     * @return
     */
    public String getMediaAsString() {

        return MathUtil.getInstance().formatPrecio(getMediaBigDecimal());


    }

    /**
     * Obtiene la medía de todos los precios del día
     * @return
     */
    public BigDecimal getMediaBigDecimal() {
        BigDecimal media = new BigDecimal(0);
        if (energyPerHour.size() != 0) {
            for (EnergyPrice energyPrice : energyPerHour) {
                media = media.add(energyPrice.getPriceDivisa());
            }
            media = media.divide(new BigDecimal(energyPerHour.size()), 5, RoundingMode.HALF_UP);
        }
        return media.setScale(5, RoundingMode.HALF_UP);

    }

    /**
     * Compara en porcentaje dos precios {@link EnergyPrice}
     * @param precio1
     * @param precio2
     * @return
     */
    public PriceComparator comparePercentPrice(final EnergyPrice precio1, final EnergyPrice precio2) {

        return this.comparePercentPrice(precio1.getPriceDivisa(), precio2.getPriceDivisa());
    }

    /**
     * Compara en porcentaje dos precios en {@link BigDecimal}
     * @param precio1
     * @param precio2
     * @return
     */
    public PriceComparator comparePercentPrice(final BigDecimal precio1, final BigDecimal precio2) {

        PriceComparator comparador = new PriceComparator();
        comparador.setComparacion(precio1.compareTo(precio2));
        BigDecimal porcentajeDiferencia;
        switch (comparador.getComparacion()) {
            case 1:
                porcentajeDiferencia = MathUtil.getInstance().getPercentageChange(precio1.subtract(precio2), precio1);

                break;
            case -1:
                porcentajeDiferencia = MathUtil.getInstance().getPercentageChange(precio2.subtract(precio1), precio1);
                break;
            case 0:
                porcentajeDiferencia = new BigDecimal("0");
                break;
            default:
                porcentajeDiferencia = new BigDecimal("0");
        }
        comparador.setPercent(MathUtil.getInstance().formatPercent(porcentajeDiferencia));
        return comparador;
    }

    /**
     * Devuelve el siguiente mejor precio del día
     * @return
     */
    public EnergyPrice getNextBestPrice() {
        return getNextBestPrice(false);
    }

    /**
     * Devuelve el siguiente peor precio del día
     * @return
     */
    public EnergyPrice getNextWorstPrice() {

        return getNextWorstPrice(false);
    }

    /**
     * Devuelve el siguiente mejor precio del día
     * @param withNow contando el rango de hora actual
     * @return
     */
    public EnergyPrice getNextBestPrice(final boolean withNow) {
        List<EnergyPrice> nextEnergyPerHour = getNextEnergyPrices(withNow);
        if (nextEnergyPerHour.isEmpty()) {
            return null;
        }
        return getBestEnergyPrice(nextEnergyPerHour);
    }

    /**
     * Devuelve el siguiente peor precio del día
     * @param withNow contando el rango de hora actual
     * @return
     */
    public EnergyPrice getNextWorstPrice(final boolean withNow) {

        List<EnergyPrice> nextEnergyPerHour = getNextEnergyPrices(withNow);
        if (nextEnergyPerHour.isEmpty()) {
            return null;
        }
        return getWorstEnergyPrice(nextEnergyPerHour);
    }
}




