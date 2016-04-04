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

import com.conceptberria.wattion.dto.PaginaEsiosDto;
import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.service.EnergyPriceService;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ConceptBerria on 02/04/2014.
 * Handler para tratar el xml del precio por horas obtenido de la página de esios
 */
public class PricePerHourXMLHandler extends DefaultHandler {


    /*Variables de procesamientro*/
    boolean isTarifa;
    boolean isPVPC;
    boolean isData;
    EnergyPrice precioActual;
    /*Variables de negocio*/
    PaginaEsiosDto paginaEsiosDTO = new PaginaEsiosDto();
    List<EnergyPrice> tarifaA;
    List<EnergyPrice> tarifaDHA;
    List<EnergyPrice> tarifaDHS;
    int numTarifa = 1;
    int id;


    public PaginaEsiosDto getTarifas() {
        return this.paginaEsiosDTO;
    }


    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        super.characters(ch, start, length);

    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {

        super.endElement(uri, localName, name);


        if ("SeriesTemporales".equals(localName)) {
            this.isTarifa = false;
            this.isPVPC = false;
        }
        if (this.isTarifa && localName.equals("Intervalo")) {
            this.id++;
            this.precioActual.setId((long) this.id);
            switch (this.numTarifa) {
                case EnergyPriceService.TARIFA_A:
                    this.tarifaA.add(this.precioActual);
                    break;
                case EnergyPriceService.TARIFA_DHA:
                    this.tarifaDHA.add(this.precioActual);
                    break;
                case EnergyPriceService.TARIFA_DHS:
                    this.tarifaDHS.add(this.precioActual);
                    break;
            }

        }

    }

    @Override
    public void startDocument() throws SAXException {

        super.startDocument();
        this.isData = false;
        this.tarifaA = new ArrayList<EnergyPrice>();
        this.tarifaDHA = new ArrayList<EnergyPrice>();
        this.tarifaDHS = new ArrayList<EnergyPrice>();


    }

    @Override
    public void startElement(String uri, String localName,
                             String name, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, name, attributes);

        if ("TerminoCosteHorario".equals(localName) && "FEU".equals(attributes.getValue("v"))) {
            this.isPVPC = true;
        }
        if (this.isPVPC && "TipoPrecio".equals(localName)) {
            String value = attributes.getValue("v");
            this.numTarifa = Integer.valueOf(value.substring("ZO".length()));
            if (this.numTarifa == EnergyPriceService.TARIFA_A || this.numTarifa == EnergyPriceService.TARIFA_DHA || this.numTarifa == EnergyPriceService.TARIFA_DHS) {
                this.isTarifa = true;
            }
        }


        if (this.isTarifa) {
            if (localName.equals("Intervalo")) {
                this.precioActual = new EnergyPrice();
            }
            if ("Ctd".equals(localName)) {
                this.precioActual.setPrice(new BigDecimal(attributes.getValue("v")));
            }
            if ("Pos".equals(localName)) {
                this.precioActual.setHora(this.caseHora(attributes.getValue("v")));
            }


        }


    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        this.paginaEsiosDTO = new PaginaEsiosDto();
        this.paginaEsiosDTO.setTarifaA(this.tarifaA);
        this.paginaEsiosDTO.setTarifaDHS(this.tarifaDHS);
        this.paginaEsiosDTO.setTarifaDHA(this.tarifaDHA);
    }

    private String caseHora(String rango) {

        if ("1".equals(rango)) {
            return "00:00";
        } else if ("2".equals(rango)) {
            return "01:00";
        } else if ("3".equals(rango)) {
            return "02:00";
        } else if ("4".equals(rango)) {
            return "03:00";
        } else if ("5".equals(rango)) {
            return "04:00";
        } else if ("6".equals(rango)) {
            return "05:00";
        } else if ("7".equals(rango)) {
            return "06:00";
        } else if ("8".equals(rango)) {
            return "07:00";
        } else if ("9".equals(rango)) {
            return "08:00";
        } else if ("10".equals(rango)) {
            return "09:00";
        } else if ("11".equals(rango)) {
            return "10:00";
        } else if ("12".equals(rango)) {
            return "11:00";
        } else if ("13".equals(rango)) {
            return "12:00";
        } else if ("14".equals(rango)) {
            return "13:00";
        } else if ("15".equals(rango)) {
            return "14:00";
        } else if ("16".equals(rango)) {
            return "15:00";
        } else if ("17".equals(rango)) {
            return "16:00";
        } else if ("18".equals(rango)) {
            return "17:00";
        } else if ("19".equals(rango)) {
            return "18:00";
        } else if ("20".equals(rango)) {
            return "19:00";
        } else if ("21".equals(rango)) {
            return "20:00";
        } else if ("22".equals(rango)) {
            return "21:00";
        } else if ("23".equals(rango)) {
            return "22:00";
        } else if ("24".equals(rango)) {
            return "23:00";
        }

        return "00:00";
    }
}
