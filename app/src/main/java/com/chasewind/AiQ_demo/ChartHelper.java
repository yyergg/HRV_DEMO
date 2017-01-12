package com.chasewind.AiQ_demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import java.util.ArrayList;

public class ChartHelper {
    Context mContext;

    public ChartHelper(Context c) {
        this.mContext = c;
    }

    public View drawArray(ArrayList<Integer> data) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(3f);

        renderer.setClickEnabled(false);
        renderer.setSelectableBuffer(20);
        renderer.setPanEnabled(true);

        renderer.setXLabelsAlign(Paint.Align.CENTER);
        renderer.setXLabels(0);
        for (int i = 0; i < data.size(); i++) {
            renderer.addXTextLabel(i, Integer.toString(i));
        }

        renderer.setYAxisMin(40);
        renderer.setYAxisMax(150);

        renderer.setXAxisMin(0);
        renderer.setXAxisMax(data.size());

        renderer.setMarginsColor(0x00010101);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //set data
        XYSeries redSeries = new XYSeries("");
        for (int i = 0; i < data.size(); i++) {
            redSeries.add(i, data.get(i));
        }
        XYSeriesRenderer redRenderer = new XYSeriesRenderer();
        redRenderer.setColor(Color.RED);

        dataset.addSeries(redSeries);
        renderer.addSeriesRenderer(redRenderer);

        renderer.setBarWidth(40);

        View view = ChartFactory.getBarChartView(mContext, dataset, renderer, BarChart.Type.DEFAULT);
        return view;
    }
}

