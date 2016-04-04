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

import com.conceptberria.wattion.app.PreferencesProvider;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by ConceptBerria on 10/03/2016.
 * Clase de utilidades de operaciones matemáticas
 */
public class MathUtil implements MathUtilInterface {

    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private static final int DECIMALS = 1;
    private static MathUtil instance;

    public static synchronized MathUtilInterface getInstance() {
        if (instance == null) {
            instance = new MathUtil();
        }
        return instance;
    }

    @Override
    public String formatPercent(final BigDecimal decimal) {
        return this.formatBigDecimal(decimal, MathUtilInterface.DEFAULT_PERCENT_FORMAT)+ "%";

    }
    @Override
    public String formatPrecio(final BigDecimal decimal) {
        switch (PreferencesProvider.getInstance().getDivisa()) {
            case MathUtilInterface.CENTIMO_EURO_KW:
                return getInstance().formatBigDecimal(decimal, MathUtilInterface.DEFAULT_PRICE_FORMAT_CE)+" c€/kWh";
            case MathUtilInterface.EURO_KW:
                return getInstance().formatBigDecimal(decimal, MathUtilInterface.DEFAULT_PRICE_FORMAT_E)+" €/kWh";
            case MathUtilInterface.EURO_MILES_KW:
                return getInstance().formatBigDecimal(decimal, MathUtilInterface.DEFAULT_PRICE_FORMAT_ME)+" €/MWh";
            default:
                return getInstance().formatBigDecimal(decimal, MathUtilInterface.DEFAULT_PRICE_FORMAT_E)+" €/kWh";
        }


    }
    @Override
    public String formatBigDecimal(final BigDecimal decimal, final String pattern) {
        DecimalFormat format = new DecimalFormat(pattern, getSymbols());
        return format.format(decimal);
    }
    @Override
    public BigDecimal parseBigDecimal(final String decimal, final String pattern) {

        DecimalFormat decimalFormat = new DecimalFormat(pattern, getSymbols());
        decimalFormat.setParseBigDecimal(true);

        BigDecimal bigDecimal = null;
        try {
            bigDecimal = (BigDecimal) decimalFormat.parse(decimal);
        } catch (ParseException e) {
            return null;
        }

        return bigDecimal;
    }
    @Override
    public BigDecimal getPercentageChange(final BigDecimal numero1, final BigDecimal numero2) {
        BigDecimal fractionalChange = numero1.divide(
                numero2, ROUNDING_MODE
        );
        return rounded(fractionalChange.multiply(new BigDecimal("100")));
    }

    /**
     * redondea el número
     * @param aNumber
     * @return
     */
    private BigDecimal rounded(final BigDecimal aNumber) {
        return aNumber.setScale(DECIMALS, ROUNDING_MODE);
    }

    /**
     * obtiene los simbolos por defecto
     * @return
     */
    private DecimalFormatSymbols getSymbols() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        return symbols;

    }


}
