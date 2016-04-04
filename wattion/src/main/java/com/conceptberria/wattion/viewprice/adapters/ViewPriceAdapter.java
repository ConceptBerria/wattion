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


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.conceptberria.wattion.app.EnergyControlApp;
import com.conceptberria.wattion.model.EnergyPrice;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.model.PriceComparator;
import com.conceptberria.wattion.viewprice.R.dimen;
import com.conceptberria.wattion.viewprice.R.drawable;
import com.conceptberria.wattion.viewprice.R.id;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * Created by ConceptBerria on 13/03/2016.
 */
public abstract class ViewPriceAdapter {


    public abstract void render(View vista, EnergyPriceDay precioDia);

    /**
     * Dibuja la gráfica en la vista
     *
     * @param vista
     * @param precioDia
     */
    protected void renderGraph(final View vista, final EnergyPriceDay precioDia) {
        GraphView layout = (GraphView) vista.findViewById(id.graph1);
        if (VERSION.SDK_INT > VERSION_CODES.HONEYCOMB) {
            this.comprobarLandscape(vista, layout);
            this.printGraph(precioDia, layout);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    /**
     * Pinta la información del precioDia en la gráfica
     *
     * @param precioDia precio a pintar
     * @param precioDia layout de la gráfica
     * @param graph
     */
    private void printGraph(final EnergyPriceDay precioDia, final GraphView graph) {

        graph.setTitle("Precio del día");
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(precioDia.getBestEnergyPrice().getPriceDivisa().doubleValue());
        graph.getViewport().setMaxY(precioDia.getWorstEnergyPrice().getPriceDivisa().doubleValue());
        graph.getViewport().setScalable(false);


        DataPoint[] data = new DataPoint[precioDia.getEnergyPricePerHour().size()];
        int i = 0;
        for (EnergyPrice energyPriceFor : precioDia.getEnergyPricePerHour()) {
            data[i] = new DataPoint(i, energyPriceFor.getPriceDivisa().doubleValue());
            i++;

        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        series.setColor(Color.rgb(255, 193, 8));
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(100, 255, 193, 8));
        graph.addSeries(series);

    }

    /**
     * Comprueba la horientación y modifica los parámetros de la gráfica
     *
     * @param vista
     * @param GraphView
     */
    private void comprobarLandscape(final View vista, final GraphView GraphView) {

        if (vista.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GraphView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            GraphView.getLayoutParams().width = vista.getResources().getDimensionPixelSize(dimen.graph_widht);

        }
    }

    /**
     * Retorna la densidad en pixeles.
     *
     * @param dps
     * @return
     */
    protected int returnPixelDesnsity(final int dps) {


        float scale = EnergyControlApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    /**
     * Modifica el texto a un recurso dentro de la vista
     *
     * @param vista   vista en la que se encuentra el recurso
     * @param recurso recurso al que modificar el texto de tipo {@link TextView}
     * @param text    texto
     */
    protected void setText(final View vista, final int recurso, final String text) {
        TextView textView = (TextView) vista.findViewById(recurso);
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * modifica la imagen de fondo de un recurso dentro de una vista
     *
     * @param vista    {@link View} en la que se encuentra el recurso
     * @param recurso  recurso de tipo {@link ImageView}
     * @param drawable drawable al que incluir en el fondo del recuso
     */
    protected void setImage(final View vista, final int recurso, final int drawable) {
        ImageView foto = (ImageView) vista.findViewById(recurso);
        if (foto != null) {
            foto.setBackgroundResource(drawable);
        }
    }

    /**
     * Modifica el texto y la imagen en la comparación de tanto por ciento
     *
     * @param vista       vista en la que se encuentran los recursos
     * @param recursoText recurso texto de tanto por ciento
     * @param recursoImg  recurso imagen de tanto por ciento
     * @param comparador  comparación que pintar
     */
    protected void setTendencies(final View vista, final int recursoText, final int recursoImg, final PriceComparator comparador) {


        ImageView imagen = (ImageView) vista.findViewById(recursoImg);
        TextView textView = (TextView) vista.findViewById(recursoText);
        int color = Color.parseColor("#F44336");
        int img = 0;
        switch (comparador.getComparacion()) {
            case 1:
                color = Color.parseColor("#388E3C");
                img = drawable.ic_trending_down_black_24dp;
                break;
            case -1:
                color = Color.parseColor("#F44336");
                img = drawable.ic_trending_up_black_24dp;
                break;
            case 0:
                color = Color.BLACK;
                img = drawable.ic_trending_flat_black_24dp;
        }

        imagen.setImageResource(img);
        imagen.setColorFilter(color);
        textView.setText(comparador.getPercent());
        textView.setTextColor(color);

    }

    /**
     * Añade a una imagen que se encuentra en una vista la acción en el evento onClick de ir a una url
     *
     * @param vista         vista en la que se encuentra el recurso
     * @param resourceImage recurso de tipo {@link ImageView}
     * @param url           url a la que dirigir
     */
    protected void imageButtonUrl(final View vista, final int resourceImage, final String url) {
        ImageView valorar = (ImageView) vista.findViewById(resourceImage);
        valorar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                vista.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }


        });
    }
}
