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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.util.MathUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Wrapper con las opciones mas comunes para tratar con la Base de Datos
 */
public class BaseDAO {

    protected PMSQLiteHelper sqlLiteHelper;
    private final String BASE_DATOS = "ENERGY_PRICE_BBDD";
    private final Integer VERSION_BASE_DATOS = 1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String FRANJA_FORMAT = "HH";
    public static final String DEFAULT_PRICE_FORMAT = "#.#####";



    /**
     * @param context
     */
    public BaseDAO(Context context) {
        sqlLiteHelper = new PMSQLiteHelper(context, BASE_DATOS, null, VERSION_BASE_DATOS);
    }

    /**
     * Inserta los valores del hash {@link ContentValues} en la tabla pasada como primer parametro
     *
     * @param tabla   Tabla en la que se insertaran los datos
     * @param valores Hash con los valores a insertar
     */
    public long insert(final String tabla, final ContentValues valores) {
        SQLiteDatabase dbw = sqlLiteHelper.getWritableDatabase();
        return dbw.insert(tabla, null, valores);
    }

    /**
     * Obtiene en String una fecha en el formato de la base de datos
     * @param fecha
     * @return
     */
    public String formatSQLLiteDate(final Date fecha){
        return CalendarUtil.getInstance().formatDate(fecha, DATE_FORMAT);
    }

    /**
     * Obtiene la fecha en base al string en el formato de la base de datos
     * @param fecha
     * @return
     */
    public Date getSQLLiteDate(final String fecha){
        return CalendarUtil.getInstance().parseDate(fecha, DATE_FORMAT);
    }


    /**
     * Otiene el BigDecimal del String según el formato de la base de datos
     * @param precio
     * @return
     */
    protected BigDecimal getSQLLiteBigDecimal(final String precio) {
        return MathUtil.getInstance().parseBigDecimal(precio, DEFAULT_PRICE_FORMAT);
    }

    /**
     * Formatea el BigDecimal según el formato de la base de datos
     * @param precio
     * @return
     */
    protected String formatSQLLiteBigDecimal(final BigDecimal precio) {
        return MathUtil.getInstance().formatBigDecimal(precio, DEFAULT_PRICE_FORMAT);
    }

    /**
     * Obtiene la fecha  del String según el formato de la franja horaria en base de datos
     * @param franja
     * @return
     */
    protected Date getSQLLiteFranja(final String franja) {
        return CalendarUtil.getInstance().parseDate(franja, FRANJA_FORMAT);
    }

    /**
     * formatea la fecha según el formato de la franja horaria de la base de datos
     * @param hora
     * @return
     */
    protected String formatSQLLiteFranja(final Date hora) {

        return CalendarUtil.getInstance().formatDate(hora, FRANJA_FORMAT);
    }


    /**
     * Actualiza los registros de la tabla pasada como parametro y que coincidan con la clusula WHERE
     *
     * @param tabla
     * @param valores
     * @param where
     */
    public void update(final String tabla, final ContentValues valores, final String where, final String[] whereArgs) {
        SQLiteDatabase dbw = sqlLiteHelper.getWritableDatabase();
        dbw.update(tabla, valores, where, whereArgs);
    }

    /**
     * Elimina los registros de la tabla pasada como parametro y que coincidan con la clausula WHERE
     *
     * @param tabla
     * @param where
     */
    public void delete(final String tabla, final String where) {
        SQLiteDatabase dbw = sqlLiteHelper.getWritableDatabase();
        dbw.delete(tabla, where, null);
    }

    /**
     * @param tabla     Tabla ha consultar
     * @param columnas  Array de {@link String} con los nombres de las COLUMNAS
     * @param where     Clausula WHERE (pe. id=?, nombre=?) o null si no se quiere usar
     * @param whereArgs Array con los valores de la clausula WHERE que seran sustituidos por ?
     * @param groupBy   Clausula GROUP BY
     * @param having    Clausula HAVING
     * @param orderBy   Clausula ORDER BY
     */
    public Cursor getCursor(final String tabla, final String[] columnas, final String where, final String[] whereArgs, final String groupBy, final String having, final String orderBy) {
        SQLiteDatabase dbr = sqlLiteHelper.getReadableDatabase();

        Cursor cursor = dbr.query(tabla, columnas, where, whereArgs, groupBy, having, orderBy);
        cursor.moveToFirst();

        return cursor;
    }

    /**
     * @param tabla     Tabla ha consultar
     * @param columnas  Array de {@link String} con los nombres de las COLUMNAS
     * @param where     Clausula WHERE (pe. id=?, nombre=?) o null si no se quiere usar
     * @param whereArgs Array con los valores de la clausula WHERE que seran sustituidos por ?
     */
    public Cursor getCursor(final String tabla, final String[] columnas, final String where, final String[] whereArgs) {
        return this.getCursor(tabla, columnas, where, whereArgs, null, null, null);
    }

}
