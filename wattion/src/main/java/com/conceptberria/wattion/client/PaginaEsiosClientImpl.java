/**
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

import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.exception.PaginaEsiosConnectFailException;
import com.conceptberria.wattion.util.XMLParserUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * Created by axterix on 01/04/2014.
 */
public class PaginaEsiosClientImpl extends RestClient implements PaginaEsiosClient {

    private static PaginaEsiosClientImpl instance;
    public static synchronized PaginaEsiosClient getInstance() {
        if (instance == null) {
            instance = new PaginaEsiosClientImpl();
        }
        return instance;
    }

    public PaginaEsiosDto getEnergyPriceMinisterio(Date fecha)throws PaginaEsiosConnectFailException {
        HttpURLConnection urlConnection = null;

        try
        {


            String urlString = "https://api.esios.ree.es/archives/80/download?date="
                    + getFileName(fecha);

            urlConnection = getUrlBasicConnection(urlString);
            InputStream inputStream = urlConnection.getInputStream();
            int responseCode= urlConnection.getResponseCode();

            if (responseCode!=HttpURLConnection.HTTP_OK){
                throw new PaginaEsiosConnectFailException("Error de conexión");
            }



            return XMLParserUtil.getInstance().parsePricePerHour(inputStream);
        }
        catch (Exception ex)
        {
            throw new PaginaEsiosConnectFailException(ex);
        }
        finally
        {
            urlConnection.disconnect();
        }


    }

}
