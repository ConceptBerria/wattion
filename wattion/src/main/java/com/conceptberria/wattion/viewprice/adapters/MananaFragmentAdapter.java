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

import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.service.EnergyPriceServiceImpl;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.viewprice.R.id;
import com.jjoe64.graphview.GraphView;

/**
 * Created by ConceptBerria on 13/03/2016.
 * Clase adaptador para el layput fragment_view_price_tomorrow.xml
 */
public class MananaFragmentAdapter extends ViewPriceAdapter {
    @Override
    public void render(final View vista, final EnergyPriceDay precioDia) {

        if (EnergyPriceServiceImpl.getInstance().existInfo(CalendarUtil.getInstance().getTomorrow())) {

            EnergyPrice worstPriceTomorrow = precioDia.getWorstEnergyPrice();
            EnergyPrice bestPriceTomorrow = precioDia.getBestEnergyPrice();
            CardView noInfo = (CardView) vista.findViewById(id.no_info);
            noInfo.setVisibility(View.GONE);

            if (vista.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LinearLayout contenedor = (LinearLayout) vista.findViewById(id.contenedor_fragment);
                contenedor.setOrientation(LinearLayout.HORIZONTAL);
            }

            this.renderGraph(vista, precioDia);
            ListPriceAdapter adapterTomorrow = new ListPriceAdapter(vista.getContext(), precioDia.getEnergyPricePerHour(), bestPriceTomorrow, worstPriceTomorrow);
            ListView listaTomorrow = (ListView) vista.findViewById(id.listView);
            listaTomorrow.setAdapter(adapterTomorrow);
        } else {
            ListView listaTomorrow = (ListView) vista.findViewById(id.listView);
            listaTomorrow.setVisibility(View.GONE);
            GraphView layout = (GraphView) vista.findViewById(id.graph1);
            layout.setVisibility(View.GONE);
            CardView noInfo = (CardView) vista.findViewById(id.no_info);
            noInfo.setVisibility(View.VISIBLE);
            CardView tip = (CardView) vista.findViewById(id.tip);
            tip.setVisibility(View.VISIBLE);
            this.imageButtonUrl(vista, id.marquet, "market://details?id=com.conceptberria.wattion");
            this.imageButtonUrl(vista, id.facebook, "https://www.facebook.com/WattiOn-1697527513823353/");
            this.imageButtonUrl(vista, id.twitter, "http://twitter.com/watti_on");
            this.imageButtonUrl(vista, id.google, "https://plus.google.com/107969918183257136741");

        }
    }
}

