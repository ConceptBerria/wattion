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
package com.conceptberria.wattion.util;

import java.math.BigDecimal;

/**
 * Created by ConceptBerria on 10/03/2016.
 * Interfaz de clase de utilidades con operaciones matemáticas
 */
public interface MathUtilInterface {
    String DEFAULT_PERCENT_FORMAT = "#.#";
    String DEFAULT_PRICE_FORMAT_E = "#.#####";
    String DEFAULT_PRICE_FORMAT_CE = "#.###";
    String DEFAULT_PRICE_FORMAT_ME= "##.##";
    int EURO_MILES_KW = 3;
    int CENTIMO_EURO_KW = 2;
    int EURO_KW = 1;

    /**
     * formatea el decimal con patrón de tanto por ciento
     * @param decimal decimal a formatear
     * @return
     */
    String formatPercent(final BigDecimal decimal);

    /**
     * Obtiene el porcentaje de la diferencia entre dos decimales
     * @param numero1
     * @param numero2
     * @return porcentaje de numero1 sobre numero2
     */
    BigDecimal getPercentageChange(final BigDecimal numero1, final BigDecimal numero2);

    /**
     * formatea el decimal como precio dependiendo de la divisa configurada
     * @param decimal
     * @return
     */
    String formatPrecio(final BigDecimal decimal);

    /**
     * Parsea el decimal según el patrón
     * @param decimal número a parsear en {@String}
     * @param pattern patrón
     * @return devuelve el decimal, null si error en el parseo
     */
    BigDecimal parseBigDecimal(final String decimal, final String pattern);

    /**
     * Formatea el decimal según el patrón
     * @param decimal decimal a formatear
     * @param pattern patrón
     * @return
     */
    String formatBigDecimal(final BigDecimal decimal, final String pattern);
}
