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
package com.conceptberria.wattion.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.conceptberria.wattion.model.EnergyPrice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by axterix on 27/04/2014.
 */
public class EnergyPriceDAOImpl extends BaseDAO implements EnergyPriceDAO {

    public static final String TABLA = "ENERGY_PRICE";
    public static final String COLUMNAS[] = {"DIA","TARIFA", "FRANJA", "PRECIO"};

    public EnergyPriceDAOImpl(Context context) {
        super(context);
    }


    @Override
    public void insert(final Date date, final int tarifa, final EnergyPrice energyPrice) {


        ContentValues values = new ContentValues();

       values.put("DIA", formatSQLLiteDate(date));
       values.put("TARIFA", tarifa);
       values.put("FRANJA", formatSQLLiteFranja(energyPrice.getHora()));
       values.put("PRECIO", formatSQLLiteBigDecimal(energyPrice.getPrice()));
        insert(TABLA, values);


    }


    @Override
    public List<EnergyPrice> findByDayTarifa(final Date date, final int tarifa) {
        List<EnergyPrice> preciosEnergia = new ArrayList<EnergyPrice>();
        String queryString=null;
        String[]qs=null;
        queryString="DIA = ? AND TARIFA = ?";
        qs= new String[2];
        qs[0]= formatSQLLiteDate(date);
        qs[1]=String.valueOf(tarifa);
        Cursor cursor = getCursor(TABLA, COLUMNAS, queryString, qs,null,null,"FRANJA ASC");

        // Create return object
        long id=1;
        if (cursor != null && cursor.moveToFirst()) {

            do {
                EnergyPrice energyPricem = new EnergyPrice();
                energyPricem.setId(id);
                energyPricem.setHora(getSQLLiteFranja(cursor.getString(2)));
                energyPricem.setPrice(getSQLLiteBigDecimal(cursor.getString(3)));
                preciosEnergia.add(energyPricem);
                id++;

            } while (cursor.moveToNext());

        }
        return preciosEnergia;
    }
    @Override
    public boolean existDay(final Date date) {

        String[]qs;
        String queryString="DIA = ?";
        qs= new String[1];
        qs[0]= formatSQLLiteDate(date);
        Cursor cursor = getCursor(TABLA, COLUMNAS, queryString, qs,null,null,null);

        // Create return object

        if (cursor != null && cursor.moveToFirst()) {

            do {
               return true;

            } while (cursor.moveToNext());

        }
        return false;
    }



}
