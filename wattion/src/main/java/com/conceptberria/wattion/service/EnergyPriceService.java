/**
 * Copyright 2016 ConceptBerria
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.conceptberria.wattion.service;

import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;

import java.util.Date;
import java.util.List;

/**
 * Created by ConceptBerria on 9/03/14.
 * Servicio para trabajar con los precios de la energía.
 */
public interface EnergyPriceService {


    int TARIFA_A = 1;
    int TARIFA_DHA = 2;
    int TARIFA_DHS = 3;

    /**
     * getEnergyPriceDay
     *  @return Devuelve el objeto que representa la información del precio del día indicado en  {@link Date)}
     */
    EnergyPriceDay getEnergyPriceDay(final Date fecha);

    /**
     * Devuelve si existe información de los precio del día en la fecha indicada por parámetro
     * @param fecha
     * @return true existe false no existe
     */
    boolean existInfo(final Date fecha);

    /**
     * Persiste informcaión de los precios de la energía del día en la tarifa indicada
     * @param fecha día de la información de precios
     * @param energyPrices precios de las 24 horas del día
     * @param tarifa tarifa a guardar
     * @return true si se ha persistido correctamente
     */
    boolean persistEnergyPriceDay(final Date fecha, final List<EnergyPrice> energyPrices, final int tarifa);

    /**
     * Persiste información de los precios de la energía del día obtenida de la página de esios
     * @param fecha día de la información de precios
     * @param paginaEsiosDto información de la página de esios
     * @return true si se ha persistido correctamente
     */
    boolean persistEnergyPriceDay(final Date fecha, final PaginaEsiosDto paginaEsiosDto);

    /**
     * Persiste información de los precios de la energía del día obtenido del core de  ConceptBerria
     * @param fecha día de la información de precios
     * @param coreEnergyPriceDayDto información del core de ConceptBerria
     * @return true si se ha persistido correctamnete
     */
    boolean persistEnergyPriceDay(final Date fecha, final EnergyPriceDayDto coreEnergyPriceDayDto);

}
