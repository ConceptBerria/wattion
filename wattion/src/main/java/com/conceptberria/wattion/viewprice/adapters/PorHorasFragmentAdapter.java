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
package com.conceptberria.wattion.viewprice.adapters;

import android.content.res.Configuration;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.viewprice.R.id;


/**
 * Created by ConceptBerria on 13/03/2016.
 * Clase adaptador para el layput fragment_view_price_day.xml
 */
public class PorHorasFragmentAdapter extends ViewPriceAdapter {
    @Override
    public void render(final View vista, final EnergyPriceDay precioDia) {

        EnergyPrice worstPrice = precioDia.getWorstEnergyPrice();
        EnergyPrice bestPrice = precioDia.getBestEnergyPrice();

        if (vista.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout contenedor = (LinearLayout) vista.findViewById(id.contenedor_fragment);
            contenedor.setOrientation(LinearLayout.HORIZONTAL);
        }

        this.renderGraph(vista,precioDia);
        ListPriceAdapter adapter = new ListPriceAdapter(vista.getContext(),precioDia.getNextEnergyPrices(true), bestPrice,worstPrice);
        ListView lista = (ListView) vista.findViewById(id.listView);
        lista.setAdapter(adapter);


    }
}
