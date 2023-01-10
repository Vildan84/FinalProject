package org.example.jfreechart;

import org.example.csv.Column;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Collection;
import java.util.LinkedList;

public class XYDataset {

    public XYSeriesCollection createDataset(LinkedList<Column> list){

        LinkedList<String> nameList = new LinkedList<>();
//        nameList.add("Engine RPM");
//        nameList.add("Charge air pressure: specified value");
        nameList.add("Charge pressure before throttle valve: calculated actual value");

        LinkedList<Integer> xLine = new LinkedList<>();
        LinkedList<XYSeriesCollection> datasetCol = new LinkedList<>();
        var dataset = new XYSeriesCollection();

        Column column = list.get(0);

        for(int i = 0; i < column.getList().size(); i++) {
            xLine.add(i);
        }

        for(Column col: list) {
            if (nameList.contains(col.getName())) {

                String name = col.getName();
                var temp = new XYSeries(name);
                LinkedList<Double> arr = col.getList();

                if(col.getName().equals("Engine RPM")){
                    for (int j = 0; j < arr.size(); j++) {
                        Double number = arr.get(j) / 1000;
                        temp.add(xLine.get(j), number);
                    }
                }
                else{
                    for (int j = 0; j < arr.size(); j++) {
                        temp.add(xLine.get(j), arr.get(j));
                    }
                }
                dataset.addSeries(temp);
            }
        }
        return dataset;
    }
}
