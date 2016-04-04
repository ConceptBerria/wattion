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
package com.conceptberria.wattion.dto;

import com.conceptberria.wattion.model.EnergyPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ConceptBerria on 05/04/2014.
 * Dto con la información devuelta en el cliente de esios
 */
public class PaginaEsiosDto {


    private List<EnergyPrice> tarifaA =new ArrayList<EnergyPrice>();
    private List<EnergyPrice> tarifaDHA=new ArrayList<EnergyPrice>();
    private List<EnergyPrice> tarifaDHS=new ArrayList<EnergyPrice>();

    public List<EnergyPrice> getTarifaA() {
        return tarifaA;
    }

    public void setTarifaA(final List<EnergyPrice> tarifaA) {
        this.tarifaA = tarifaA;
    }

    public List<EnergyPrice> getTarifaDHA() {
        return tarifaDHA;
    }

    public void setTarifaDHA(final List<EnergyPrice> tarifaDHA) {
        this.tarifaDHA = tarifaDHA;
    }

    public List<EnergyPrice> getTarifaDHS() {
        return tarifaDHS;
    }

    public void setTarifaDHS(final List<EnergyPrice> tarifaDHS) {
        this.tarifaDHS = tarifaDHS;
    }


}

