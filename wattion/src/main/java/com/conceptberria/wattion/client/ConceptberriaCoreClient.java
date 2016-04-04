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
package com.conceptberria.wattion.client;

import com.conceptberria.wattion.dto.EnergyPriceDayDto;
import com.conceptberria.wattion.exception.ConceptberriaCoreConnectFailException;
import com.conceptberria.wattion.exception.ConceptberriaCoreNoDataException;

import java.util.Date;

/**
 * Created by Conceptberria on 01/04/2014.
 * Interface del cliente del core ConceptBerria
 */
public interface ConceptberriaCoreClient {
    EnergyPriceDayDto getEnergyPriceCore(final Date fecha)throws ConceptberriaCoreConnectFailException,ConceptberriaCoreNoDataException;

}
