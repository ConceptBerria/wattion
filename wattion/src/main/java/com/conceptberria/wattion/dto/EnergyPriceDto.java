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
package com.conceptberria.wattion.dto;

import com.conceptberria.wattion.util.CalendarUtil;

import java.util.Date;

/***
 * Objeto Dto representa el json que devuelve el core. No cumple estándares de nomenclatura de variables en java para que funcione el marshall
 * "Dia": "22/03/2016",
 * "Hora": "00-01",
 * "GEN": "97,07",
 * "NOC": "48,29",
 * "VHC": "51,80",
 *
 * @author ConceptBerria
 */
public class EnergyPriceDto {
    String Hora;
    String GEN;
    String NOC;
    private static final String HORA_FORMAT = "HH:mm";
    public String getNOC() {
        return NOC;
    }

    public void setNOC(String NOC) {
        this.NOC = NOC;
    }

    public String getHora() {
        return Hora;
    }
    public Date getHoraAsDate() {
        return CalendarUtil.getInstance().parseDate(caseHora(Hora), HORA_FORMAT);
    }
    public void setHora(String hora) {
        Hora = hora;
    }

    public String getGEN() {
        return GEN;
    }

    public void setGEN(String GEN) {
        this.GEN = GEN;
    }

    public String getVHC() {
        return VHC;
    }

    public void setVHC(String VHC) {
        this.VHC = VHC;
    }

    String VHC;

    private String caseHora(String rango) {
        if ("00-01".equals(rango)) {
            return "00:00";
        } else if ("01-02".equals(rango)) {
            return "01:00";
        } else if ("02-03".equals(rango)) {
            return "02:00";
        } else if ("03-04".equals(rango)) {
            return "03:00";
        } else if ("04-05".equals(rango)) {
            return "04:00";
        } else if ("05-06".equals(rango)) {
            return "05:00";
        } else if ("06-07".equals(rango)) {
            return "06:00";
        } else if ("07-08".equals(rango)) {
            return "07:00";
        } else if ("08-09".equals(rango)) {
            return "08:00";
        } else if ("09-10".equals(rango)) {
            return "09:00";
        } else if ("10-11".equals(rango)) {
            return "10:00";
        } else if ("11-12".equals(rango)) {
            return "11:00";
        } else if ("12-13".equals(rango)) {
            return "12:00";
        } else if ("13-14".equals(rango)) {
            return "13:00";
        } else if ("14-15".equals(rango)) {
            return "14:00";
        } else if ("15-16".equals(rango)) {
            return "15:00";
        } else if ("16-17".equals(rango)) {
            return "16:00";
        } else if ("17-18".equals(rango)) {
            return "17:00";
        } else if ("18-19".equals(rango)) {
            return "18:00";
        } else if ("19-20".equals(rango)) {
            return "19:00";
        } else if ("20-21".equals(rango)) {
            return "20:00";
        } else if ("21-22".equals(rango)) {
            return "21:00";
        } else if ("22-23".equals(rango)) {
            return "22:00";
        } else if ("23-24".equals(rango)) {
            return "23:00";
        }

        return "00:00";
    }
}
