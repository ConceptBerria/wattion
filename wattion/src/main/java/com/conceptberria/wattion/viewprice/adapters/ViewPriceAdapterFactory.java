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
package com.conceptberria.wattion.viewprice.adapters;

/**
 * Created by ConceptBerria on 13/03/2016.
 * Factoría de adaptadores
 */

public class ViewPriceAdapterFactory {

    public enum ViewAdapter {
        ACTUAL_FRAME_ADAPTER(ActualFrameAdapter.class),POR_HORAS_FRAME_ADAPTER(PorHorasFragmentAdapter.class),MANANA_FRAME_ADAPTER(MananaFragmentAdapter.class);
        private final Class<?> clazz;

        ViewAdapter( Class<?> clazz) {

            this.clazz = clazz;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }


    }
    public  ViewPriceAdapter getAdapter(ViewPriceAdapterFactory.ViewAdapter adapter){
        ViewPriceAdapter adapterInstante=null;
        try {
            adapterInstante = (ViewPriceAdapter) adapter.getClazz().newInstance();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return adapterInstante;
    }
}
