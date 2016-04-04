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
package com.conceptberria.wattion.dao;

import com.conceptberria.wattion.model.EnergyPrice;

import java.util.Date;
import java.util.List;

/**
 * Created by ConceptBerria on 27/04/2014.
 * Interfaz que define el DAO del precio de la energía
 */
public interface EnergyPriceDAO {
    /**
     * iserta un registro del precio de la energía de la fecha indicada y de la tarifa
     * @param date
     * @param tarifa
     * @param energyPrice
     */
    void insert(Date date, int tarifa, EnergyPrice energyPrice);

    /**
     * Obtiene la información de los precio de la energía de la fecha y tarifa indicada
     * @param date
     * @param tarifa
     * @return
     */
    List<EnergyPrice> findByDayTarifa(Date date, int tarifa);

    /**
     * Comprueba si existe en la base de datos información del precio de la energía del día indicado por parámetro
     * @param date
     * @return
     */
    boolean existDay(Date date);
}
