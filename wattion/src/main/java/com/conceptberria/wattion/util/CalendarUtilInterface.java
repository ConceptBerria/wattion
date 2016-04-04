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
package com.conceptberria.wattion.util;

import java.util.Date;
import java.util.Locale;

/**
 * Created by ConceptBerria on 29/03/2014.
 * Interfaz de utilidades de calendario
 */
public interface CalendarUtilInterface {
     /**
      * Comprueba si es la misma hora de la actual
      * @param fecha hora con la que comparar
      * @return true si es la misma hora que la actual
      */
     boolean isSameHour(final Date fecha);
     /**
      * Obtiene la fecha de hoy
      * @return la fecha de hoy
      */
     Date getNow();
     /**
      * Obtiene la fecha de mañana
      * @return fecha de mañana
      */
     Date getTomorrow();

     /**
      * Comprueba si dos fechas tienen la misma hora
      * @param hora1
      * @param hora2
      * @return true si hora1=hora2
      */
     boolean isSameHour(final Date hora1, final Date hora2);
     /**
      * Obtiene la fecha de ahora en formato
      * @return fecha formateada
      */
     String getPrettyNow();

     /**
      * Añade una hora a la fecha
      * @param priceHour fecha a la que añadir
      * @return fecha con una hora más
      */
     Date addOneHour(final Date priceHour);

     /**
      * Añade un día a la fecha
      * @param priceHour fecha a la que añadir
      * @return fecha con un día más
      */
     Date addOneDay(final Date priceHour);
     /**
      * Obtiene la fecha formateada
      * @param fecha fecha a formatear
      * @return fecha formateada
      */
     String getPrettyDate(final Date fecha);

     /**
      * Comprueba si la hora es mayor a la actual
      * @param hora en enteros
      * @return true si actual >=hora
      */
     boolean isMayorHour(final int hora);
     /**
      * Comprueba si la hora es menor a la actual
      * @param hora en enteros
      * @return true si actual <= hora
      */
     boolean isMinorHour(final int hora);

     /**
      * Parsea la fecha según el patrón
      * @param date fecha a obtener
      * @param format patrón en que está la fecha
      * @param locale local
      * @return fecha parseada, null si error en el parseo
      */
     Date parseDate(final String date, final String format, final Locale locale);
     /**
      * Parsea la fecha según el patrón
      * @param date fecha a obtener
      * @param format patrón en que está la fecha
      * @return fecha parseada
      */
     Date parseDate(final String date, final String format);
     /**
      * Parsea la fecha según el patrón
      * @param fecha fecha a formatear
      * @param pattern patrón en que se devolverá la fecha
      * @return fecha formateada
      */
     String formatDate(final Date fecha, final String pattern);



}
