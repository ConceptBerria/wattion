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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 *  Created by ConceptBerria on 20/03/2016
 *  Implementa las funciones básicas de un cliente Rest
 */
public class RestClient {
    private static final String HORA_FORMAT = "yyyy-MM-dd";

    /**
     * Genera una conexión con los parámetros básicos
     * @param urlString
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    protected HttpURLConnection getUrlBasicConnection(final String urlString)
            throws IOException, NoSuchAlgorithmException {

        HttpsURLConnection.setDefaultHostnameVerifier(new NoHostVerify());
        HttpsURLConnection urlConnection;
        URL url = new URL(urlString);
        urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000); // en milisegundos
        urlConnection.setConnectTimeout(10000);// en milisegundos
        urlConnection.setRequestMethod("GET");
        return urlConnection;
    }

    /**
     * Obtiene la fecha en el formato del cliente
     * @param date
     * @return
     */
    protected String getFileName(final Date date) {

        String day = "";
        //+2015-03-04
        SimpleDateFormat sdf = new SimpleDateFormat(HORA_FORMAT);
        day = sdf.format(date);

        return day;
    }


}
