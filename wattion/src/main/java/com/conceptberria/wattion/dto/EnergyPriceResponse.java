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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * Objeto Dto representa el JSON del core
 */

public class EnergyPriceResponse {

	public static final int OK = 0;
	public static final int OK_WITH_ERROR = 1;
	public static final int NO_DATA = 2;
	public static final int ERROR = 3;
	public static final int PARAMETROS_NOVALIDOS = 4;
	
	
	public EnergyPriceDayDto energyPrice;
	public int codigoResponse;
	public String msgResponse="";

	public int getCodigoResponse() {
		return codigoResponse;
	}

	public void setCodigoResponse(int codigoResponse) {
		this.codigoResponse = codigoResponse;
	}

	public EnergyPriceDayDto getEnergyPrice() {
		return energyPrice;
	}

	public void setEnergyPrice(EnergyPriceDayDto energyPrice) {
		this.energyPrice = energyPrice;
	}

	public void setEnergyPriceJson(String energyPrice) {
		Gson gson = new GsonBuilder().create();
		EnergyPriceDayDto p = gson.fromJson(energyPrice, EnergyPriceDayDto.class);

		this.energyPrice = p;
	}

	public String getMsgResponse() {
		return msgResponse;
	}

	public void setMsgResponse(String msgResponse) {
		this.msgResponse = msgResponse;
	}

	/**
	 * Añade un String al mensaje de respuesta
	 * @param msgResponse
	 */
	public void appendMsgResponse(String msgResponse) {
		StringBuilder sb= new StringBuilder(this.msgResponse);
		sb.append(" - ");
		sb.append(msgResponse);
		this.msgResponse = sb.toString();
	}

}

