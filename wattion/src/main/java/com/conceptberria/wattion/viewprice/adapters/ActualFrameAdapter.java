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

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.viewprice.R.drawable;
import com.conceptberria.wattion.viewprice.R.id;

/**
 * Created by ConceptBerria on 13/03/2016.
 * Clase adaptador para el layput fragment_view_price.xml
 */
public class ActualFrameAdapter extends ViewPriceAdapter {
    @Override
    public void render(final View vista, EnergyPriceDay energyPriceDay) {

        EnergyPrice energyPrice = energyPriceDay.getEnergyPrice();
        EnergyPrice worstPrice = energyPriceDay.getWorstEnergyPrice();
        EnergyPrice bestPrice = energyPriceDay.getBestEnergyPrice();

        this.setText(vista, id.hourPrice, energyPrice.getPriceAsString());
        this.setText(vista, id.pretty_day, CalendarUtil.getInstance().getPrettyNow());

        this.setText(vista, id.hora_minimo_value, bestPrice.getHoraAsShortStringRange());
        this.setText(vista, id.valor_mejor_rango, bestPrice.getPriceAsString());
        this.setTendencies(vista, id.percent_mejor_rango, id.img_mejor_rango, energyPriceDay.comparePercentPrice(energyPrice, bestPrice));

        this.setText(vista, id.hora_maximo_value, worstPrice.getHoraAsShortStringRange());
        this.setText(vista, id.valor_peor_rango, worstPrice.getPriceAsString());
        this.setTendencies(vista, id.percent_peor_rango, id.img_peor_rango, energyPriceDay.comparePercentPrice(energyPrice, worstPrice));

        this.setText(vista, id.precio_medio_value, energyPriceDay.getMediaAsString());
        this.setTendencies(vista, id.precio_medio_percent, id.precio_medio_img, energyPriceDay.comparePercentPrice(energyPrice.getPriceDivisa(), energyPriceDay.getMediaBigDecimal()));

        EnergyPrice sgtMejorPrecio = energyPriceDay.getNextBestPrice();
        if (sgtMejorPrecio==null) {
            sgtMejorPrecio = energyPriceDay.getNextWorstPrice(true);
        }
        this.setTendencies(vista, id.siguiente_mejor_rango_percent, id.siguiente_mejor_rango_img, energyPriceDay.comparePercentPrice(energyPrice, sgtMejorPrecio));
        this.setText(vista, id.siguiente_mejor_rango_value, sgtMejorPrecio.getHoraAsShortStringRange());


        EnergyPrice sgtPeorPrecio = energyPriceDay.getNextWorstPrice();
        if (sgtPeorPrecio==null) {
            sgtPeorPrecio = energyPriceDay.getNextWorstPrice(true);
        }
        this.setTendencies(vista, id.siguiente_peor_rango_percent, id.siguiente_peor_rango_img, energyPriceDay.comparePercentPrice(energyPrice, sgtPeorPrecio));
        this.setText(vista, id.siguiente_peor_rango_value, sgtPeorPrecio.getHoraAsShortStringRange());



        //Rellenamos la fotografía
        ImageView foto = (ImageView) vista.findViewById(id.foto);
        ImageView ayuda = (ImageView) vista.findViewById(id.question);
        String texto = null;

        if (CalendarUtil.getInstance().isSameHour(worstPrice.getHora(), energyPrice.getHora())) {
            foto.setBackgroundResource(drawable.light_red_icon);
            texto = "Peor precio del día.";
        } else if (CalendarUtil.getInstance().isSameHour(bestPrice.getHora(), energyPrice.getHora())) {
            foto.setBackgroundResource(drawable.light_green_icon);
            texto = "Mejor precio del día.";
        } else {
            foto.setBackgroundResource(drawable.light_white_icon);
            texto = "Todo gasto de energía cuenta, consume de manera responsable.";
        }
        final String textoAyuda = texto;

        ayuda.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Snackbar.make(vista, textoAyuda, Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();
            }

        });


    }
}

