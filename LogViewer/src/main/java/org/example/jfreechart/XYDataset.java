package org.example.jfreechart;

import org.example.csv.Column;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Collection;
import java.util.LinkedList;

public class XYDataset {

    public LinkedList<XYSeriesCollection> createDataset(LinkedList<Column> list){

        LinkedList<Integer> xLine = new LinkedList<>();
        LinkedList<XYSeriesCollection> datasetCol = new LinkedList<>();

        Column column = list.get(0);

        for(int i = 0; i < column.getList().size(); i++) {
            xLine.add(i);
        }

        for (Column value : list) {
            String name = value.getName();
            var temp = new XYSeries(name);
            LinkedList<Double> arr = value.getList();
            for (int j = 0; j < arr.size(); j++) {
                temp.add(xLine.get(j), arr.get(j));
            }
            var dataset = new XYSeriesCollection();
            dataset.addSeries(temp);
            datasetCol.add(dataset);
        }
        return datasetCol;
    }
}
