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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.viewprice.R.drawable;
import com.conceptberria.wattion.viewprice.R.id;
import com.conceptberria.wattion.viewprice.R.layout;

import java.util.List;

/**
 * Created by ConceptBerria on 15/03/14.
 * Clase adaptador {@link BaseAdapter} para el layout list_item_view_day.xml
 */
public class ListPriceAdapter extends BaseAdapter {


    protected Context activity;
    protected List<EnergyPrice> items;
    protected EnergyPrice bestPrice;
    protected EnergyPrice worstPrice;


    public ListPriceAdapter(Context activity, List<EnergyPrice> items, EnergyPrice bestPrice, EnergyPrice worstPricea) {
        this.activity = activity;
        this.items = items;
        this.bestPrice = bestPrice;
        worstPrice = worstPricea;

    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(layout.list_item_view_day, null);
        }

        EnergyPrice energyPrice = this.items.get(position);

        ImageView foto = (ImageView) v.findViewById(id.foto);
        foto.setBackgroundResource(this.getImageResource(energyPrice));

        TextView hora = (TextView) v.findViewById(id.hora);
        hora.setText(energyPrice.getHoraAsStringRange());

        TextView precio = (TextView) v.findViewById(id.precio);
        precio.setText(energyPrice.getPriceAsString());

        return v;
    }

    /**
     * Devuelve la imagen a mostar según el precio
     * @param energyPrice
     * @return
     */
    private int getImageResource(final EnergyPrice energyPrice) {
        if (CalendarUtil.getInstance().isSameHour(this.worstPrice.getHora(), energyPrice.getHora())) {
            return drawable.light_red_icon;
        } else if (CalendarUtil.getInstance().isSameHour(this.bestPrice.getHora(), energyPrice.getHora())) {
            return drawable.light_green_icon;
        } else {
            return drawable.light_white_icon;
        }

    }
}