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
import com.conceptberria.wattion.exception.ParseEsiosXMLException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ConceptBerria on 02/04/2014.
 */
public class XMLParserUtil implements XMLParserUtilInterface {
    private static XMLParserUtil instance;

    public static synchronized XMLParserUtilInterface getInstance() {
        if (XMLParserUtil.instance == null) {
            XMLParserUtil.instance = new XMLParserUtil();
        }
        return XMLParserUtil.instance;
    }

    public PaginaEsiosDto parsePricePerHour(InputStream is) throws ParseEsiosXMLException {
        try {

            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            PricePerHourXMLHandler xmlHandler = new PricePerHourXMLHandler();
            xmlReader.setContentHandler(xmlHandler);
            InputSource isource = new InputSource(is);
            isource.setEncoding("ISO-8859-1");
            xmlReader.parse(isource);
            return xmlHandler.getTarifas();


        } catch (ParserConfigurationException pce) {
            throw new ParseEsiosXMLException(pce);

        } catch (SAXException se) {
            throw new ParseEsiosXMLException(se);
        } catch (IOException e) {
            throw new ParseEsiosXMLException(e);
        }


    }

}
