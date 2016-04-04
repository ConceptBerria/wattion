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
package com.conceptberria.wattion.client;

import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.dto.EnergyPriceResponse;
import com.conceptberria.wattion.exception.ConceptberriaCoreConnectFailException;
import com.conceptberria.wattion.exception.ConceptberriaCoreNoDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * Created by ConceptBerria on 01/04/2014.
 * Clase FAKE!! que exitende de {@link RestClient} e implementa {@link ConceptberriaCoreClient}
 */
public class ConceptberriaCoreClientImpl extends RestClient implements ConceptberriaCoreClient {

    private static ConceptberriaCoreClientImpl instance;
    public static synchronized ConceptberriaCoreClient getInstance() {
        if (instance == null) {
            instance = new ConceptberriaCoreClientImpl();
        }
        return instance;
    }

    /**
     * Obtiene la información del precio la energía de la fecha pasada por parámetro.
     * @param fecha
     * @return dto de la energía del precio
     * @throws ConceptberriaCoreConnectFailException
     * @throws ConceptberriaCoreNoDataException
     */
    public EnergyPriceDayDto getEnergyPriceCore(final Date fecha)throws ConceptberriaCoreConnectFailException,ConceptberriaCoreNoDataException {
         try
        {

            Gson gson = new GsonBuilder().create();
            String xmlGetPrice = "{\"PVPC\":[{\"Hora\":\"00-01\",\"GEN\":\"90,00\",\"NOC\":\"90,00\",\"VHC\":\"91,00\"},{\"Hora\":\"01-02\",\"GEN\":\"92,69\",\"NOC\":\"44,16\",\"VHC\":\"41,41\"},{\"Hora\":\"02-03\",\"GEN\":\"90,13\",\"NOC\":\"41,80\",\"VHC\":\"39,11\"},{\"Hora\":\"03-04\",\"GEN\":\"88,28\",\"NOC\":\"40,11\",\"VHC\":\"37,53\"},{\"Hora\":\"04-05\",\"GEN\":\"88,23\",\"NOC\":\"40,12\",\"VHC\":\"37,56\"},{\"Hora\":\"05-06\",\"GEN\":\"88,10\",\"NOC\":\"40,15\",\"VHC\":\"37,65\"},{\"Hora\":\"06-07\",\"GEN\":\"90,13\",\"NOC\":\"42,43\",\"VHC\":\"40,03\"},{\"Hora\":\"07-08\",\"GEN\":\"93,59\",\"NOC\":\"45,86\",\"VHC\":\"48,52\"},{\"Hora\":\"08-09\",\"GEN\":\"93,24\",\"NOC\":\"45,32\",\"VHC\":\"48,11\"},{\"Hora\":\"09-10\",\"GEN\":\"88,93\",\"NOC\":\"41,97\",\"VHC\":\"43,98\"},{\"Hora\":\"10-11\",\"GEN\":\"89,02\",\"NOC\":\"42,20\",\"VHC\":\"44,11\"},{\"Hora\":\"11-12\",\"GEN\":\"90,32\",\"NOC\":\"43,43\",\"VHC\":\"45,41\"},{\"Hora\":\"12-13\",\"GEN\":\"89,71\",\"NOC\":\"108,10\",\"VHC\":\"44,76\"},{\"Hora\":\"13-14\",\"GEN\":\"91,09\",\"NOC\":\"109,61\",\"VHC\":\"109,61\"},{\"Hora\":\"14-15\",\"GEN\":\"91,32\",\"NOC\":\"109,91\",\"VHC\":\"109,91\"},{\"Hora\":\"15-16\",\"GEN\":\"90,10\",\"NOC\":\"108,68\",\"VHC\":\"108,68\"},{\"Hora\":\"16-17\",\"GEN\":\"88,54\",\"NOC\":\"107,07\",\"VHC\":\"107,07\"},{\"Hora\":\"17-18\",\"GEN\":\"90,46\",\"NOC\":\"108,93\",\"VHC\":\"108,93\"},{\"Hora\":\"18-19\",\"GEN\":\"91,58\",\"NOC\":\"110,02\",\"VHC\":\"110,02\"},{\"Hora\":\"19-20\",\"GEN\":\"96,05\",\"NOC\":\"114,56\",\"VHC\":\"114,56\"},{\"Hora\":\"20-21\",\"GEN\":\"96,97\",\"NOC\":\"115,57\",\"VHC\":\"115,57\"},{\"Hora\":\"21-22\",\"GEN\":\"97,75\",\"NOC\":\"116,48\",\"VHC\":\"116,48\"},{\"Hora\":\"22-23\",\"GEN\":\"98,84\",\"NOC\":\"49,74\",\"VHC\":\"117,65\"},{\"Hora\":\"23-24\",\"GEN\":\"90,00\",\"NOC\":\"47,65\",\"VHC\":\"51,42\"}]}";

            EnergyPriceDayDto conceptBerriaResponse= gson.fromJson(xmlGetPrice, EnergyPriceDayDto.class);
            int codigo=0;
            if(codigo==EnergyPriceResponse.OK||codigo==EnergyPriceResponse.OK_WITH_ERROR){
                return conceptBerriaResponse;
            }else if (codigo==EnergyPriceResponse.NO_DATA){
                throw new ConceptberriaCoreNoDataException("No hay datos");
            }else{
                throw new ConceptberriaCoreConnectFailException("Error en la información obtenida");
            }

        }
        catch (Exception ex)
        {
            throw new ConceptberriaCoreConnectFailException(ex);
        }



    }

}
