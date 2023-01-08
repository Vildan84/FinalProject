package jfreechart;

import csv.Column;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.LinkedList;

public class Chart {

    public void chart(LinkedList<XYSeriesCollection> col, LinkedList<Column> list){

        for(int i = 0; i < col.size(); i++){
            String name = list.get(i + 1).getName();
            String value = list.get(i + 1).getValue();

            JFreeChart ch = ChartFactory.createXYLineChart(name, "Engine RPM", value, col.get(i),
                    PlotOrientation.VERTICAL, true, true, false);

            XYPlot plot = ch.getXYPlot();
            var renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.BLUE);
            renderer.setSeriesStroke(0, new BasicStroke(5.0f));

            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);
            plot.setForegroundAlpha(0.9f);
            plot.setRangeGridlinePaint(Color.red);
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.black);
            plot.setDomainGridlinesVisible(true);

            ch.getLegend().setFrame(BlockBorder.NONE);

            ChartFrame frame1 = new ChartFrame("lines", ch);
            frame1.setVisible(true);
            frame1.setSize(1300, 800);

        }

    }
}
