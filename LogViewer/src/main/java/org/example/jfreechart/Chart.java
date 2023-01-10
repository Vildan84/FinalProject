package org.example.jfreechart;

import org.example.csv.Column;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Chart {

    public void chart(XYSeriesCollection dataset) throws IOException {

        String name = "name";
        String value = "value";

        JFreeChart ch = ChartFactory.createXYLineChart(name, "Value", value, dataset,
                PlotOrientation.VERTICAL, true, true, false);

//            File file = new File("Chart" + i);
//            ChartUtils.saveChartAsPNG(file, ch, 1300, 800);

        XYPlot plot = ch.getXYPlot();
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(5.0f));

        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(1, new BasicStroke(5.0f));

        renderer.setSeriesPaint(2, Color.BLACK);
        renderer.setSeriesStroke(2, new BasicStroke(5.0f));

        renderer.setSeriesPaint(3, Color.GREEN);
        renderer.setSeriesStroke(3, new BasicStroke(5.0f));

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
