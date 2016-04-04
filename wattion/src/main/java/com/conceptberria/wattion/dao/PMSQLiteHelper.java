/*
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
package com.conceptberria.wattion.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Unai on 18/04/14.
 * Clase que implementa el helper de la base de datos del precio de la energía.
 */
public class PMSQLiteHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_ENERGY_PRICE =
            "CREATE TABLE ENERGY_PRICE(" +
                    "DIA 			TEXT DEFAULT \"0000-01-01\"," +
                    "TARIFA			INTEGER DEFAULT 0, " +
                    "FRANJA			TEXT DEFAULT \"00\"," +
                    "PRECIO			TEXT DEFAULT \"0\"" +
                    ");";


    public PMSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENERGY_PRICE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS ENERGY_PRICE;");

    }
}
