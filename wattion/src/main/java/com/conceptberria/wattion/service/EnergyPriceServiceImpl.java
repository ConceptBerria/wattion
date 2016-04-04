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
package com.conceptberria.wattion.service;

import com.conceptberria.wattion.app.EnergyControlApp;
import com.conceptberria.wattion.app.PreferencesProvider;
import com.conceptberria.wattion.dao.EnergyPriceDAO;
import com.conceptberria.wattion.dao.EnergyPriceDAOImpl;
import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.dto.EnergyPriceDto;
import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.util.MathUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ConceptBerria on 12/03/2016.
 * Implementación del servicio {@link EnergyPriceService} accediendo a base de datos local
 */
public class EnergyPriceServiceImpl implements EnergyPriceService {
    private final EnergyPriceDAO priceDAO;
    private static EnergyPriceServiceImpl instance;

    public static synchronized EnergyPriceService getInstance() {
        if (instance == null) {
            instance = new EnergyPriceServiceImpl();
        }
        return instance;
    }

    public EnergyPriceServiceImpl() {

        priceDAO = new EnergyPriceDAOImpl(EnergyControlApp.getContext());

    }

    @Override
    public EnergyPriceDay getEnergyPriceDay(final Date fecha) {

        return new EnergyPriceDay(priceDAO.findByDayTarifa(fecha, PreferencesProvider.getInstance().getTarifa()));
    }

    @Override
    public boolean existInfo(final Date fecha) {
        return priceDAO.existDay(fecha);
    }

    @Override
    public synchronized boolean persistEnergyPriceDay(final Date fecha, final List<EnergyPrice> energyPrices, final int tarifa) {

        for (EnergyPrice energyPrice : energyPrices) {
            priceDAO.insert(fecha, tarifa, energyPrice);
        }
        return true;

    }

    @Override
    public synchronized boolean persistEnergyPriceDay(final Date fecha, final PaginaEsiosDto paginaEsiosDto) {

        this.persistEnergyPriceDay(fecha, paginaEsiosDto.getTarifaA(), EnergyPriceService.TARIFA_A);
        this.persistEnergyPriceDay(fecha, paginaEsiosDto.getTarifaDHS(), EnergyPriceService.TARIFA_DHS);
        this.persistEnergyPriceDay(fecha, paginaEsiosDto.getTarifaDHA(), EnergyPriceService.TARIFA_DHA);
        return true;
    }

    @Override
    public synchronized boolean persistEnergyPriceDay(final Date fecha, final EnergyPriceDayDto coreEnergyPriceDayDto) {

        for (EnergyPriceDto energyPriceDto : coreEnergyPriceDayDto.getPVPC()) {
            insertSingularPrice(fecha, energyPriceDto.getHoraAsDate(), EnergyPriceService.TARIFA_A, energyPriceDto.getGEN());
            insertSingularPrice(fecha, energyPriceDto.getHoraAsDate(), EnergyPriceService.TARIFA_DHA, energyPriceDto.getNOC());
            insertSingularPrice(fecha, energyPriceDto.getHoraAsDate(), EnergyPriceService.TARIFA_DHS, energyPriceDto.getVHC());
        }
        return true;
    }

    /**
     * Guarda la información de un precio en una franja horaria concreta para una fecha y una tarifa
     * @param fecha día del que se persiste el precio
     * @param rango franja horaria de la que se persiste el precio
     * @param tarifa tarifa concreta
     * @param precioString precio en eMw/h
     */
    private void insertSingularPrice(final Date fecha, final Date rango, final int tarifa, final String precioString) {
        EnergyPrice energyPrice;
        BigDecimal precio;
        energyPrice = new EnergyPrice();
        energyPrice.setHora(rango);
        precio = MathUtil.getInstance().parseBigDecimal(precioString, MathUtil.DEFAULT_PRICE_FORMAT_ME);
        precio= precio.divide(new BigDecimal(1000));
        energyPrice.setPrice(precio);
        priceDAO.insert(fecha, tarifa, energyPrice);
    }
}
