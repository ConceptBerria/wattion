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
package com.conceptberria.wattion.exception;

/**
 *
 * Created by axterix on 01/04/2014.
 * {@link Exception} cuando existe un fallo de conexión con el core de ConceptBerria
 */
public class ConceptberriaCoreConnectFailException extends Exception {
    public ConceptberriaCoreConnectFailException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public ConceptberriaCoreConnectFailException(String detailMessage) {
        super(detailMessage);
    }

    public ConceptberriaCoreConnectFailException(Throwable throwable) {
        super(throwable);
    }
}
