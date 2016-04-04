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

import android.os.AsyncTask;

import com.conceptberria.wattion.client.ConceptberriaCoreClientImpl;
import com.conceptberria.wattion.client.PaginaEsiosClientImpl;
import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.exception.ConceptberriaCoreConnectFailException;
import com.conceptberria.wattion.exception.ConceptberriaCoreNoDataException;
import com.conceptberria.wattion.exception.PaginaEsiosConnectFailException;
import com.conceptberria.wattion.service.EnergyPriceServiceImpl;

import java.util.Date;

/**
 * Created by ConceptBerria on 30/09/2014.
 * Tarea asíncrona {@link AsyncTask} para obtener los precios de energía del día.
 */

public class GetPriceTask extends AsyncTask<Date, Void, Long> {

    private IGetPriceListener listener;
    public static final Long OK = (long) 0;
    public static final Long GENERAL_ERROR = (long) 1;
    public static final Long CONNECT_ERROR = (long) 2;
    public static final Long NODATA_ERROR = (long) 3;


    public void setListener(final IGetPriceListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onGetPriceStart();
        }
    }

    @Override
    protected Long doInBackground(Date... date) {
        EnergyPriceDayDto energyPriceDayCore;
        boolean errorCore = false;
        try {
            energyPriceDayCore = ConceptberriaCoreClientImpl.getInstance().getEnergyPriceCore(date[0]);
            EnergyPriceServiceImpl.getInstance().persistEnergyPriceDay(date[0], energyPriceDayCore);
        } catch (ConceptberriaCoreNoDataException e) {
            return NODATA_ERROR;
        } catch (ConceptberriaCoreConnectFailException e) {
            errorCore = true;
        }
        if (errorCore) {
            PaginaEsiosDto paginaEsiosDTO = null;
            try {

                paginaEsiosDTO = PaginaEsiosClientImpl.getInstance().getEnergyPriceMinisterio(date[0]);
                EnergyPriceServiceImpl.getInstance().persistEnergyPriceDay(date[0], paginaEsiosDTO);

            } catch (PaginaEsiosConnectFailException e) {

                return CONNECT_ERROR;
            } catch (Exception e) {

                return GENERAL_ERROR;
            }

        }
        return OK;
    }

    @Override
    protected void onPostExecute(final Long result) {
        if (listener != null) {
            listener.onGetPriceCompleted(result);
        }
    }

    /**
     * Interfaz listener de comienzo y fin de la tarea.
     */
    public interface IGetPriceListener {
        void onGetPriceCompleted(Long i);
        void onGetPriceStart();


    }

}