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
package com.conceptberria.wattion.model;

/**
 * Created by ConceptBerria on 08/03/2016.
 * Comparación entre dos precios
 */
public class PriceComparator {


    public String getPercent() {
        return percent;
    }

    public void setPercent(final String percent) {
        this.percent = percent;
    }

    private String percent;
/**
 * @return  1 > ; -1 < ; 0 =
    *
    **/
    public int getComparacion() {
        return comparacion;
    }

    public void setComparacion(final int comparacion) {
        this.comparacion = comparacion;
    }

    private int comparacion;

}
