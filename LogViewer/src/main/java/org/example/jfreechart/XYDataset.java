package org.example.jfreechart;

import org.example.csv.Column;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.LinkedList;

public class XYDataset {

    public LinkedList<XYSeriesCollection> createDataset(LinkedList<Column> list){

        LinkedList<Double> rpm = new LinkedList<>();
        LinkedList<XYSeriesCollection> datasetCol = new LinkedList<>();

        for(Column col: list){
            if(col.getName().equals("Engine RPM")){
                rpm = col.getList();
            }
            else{
                for(int i = 0; i < col.getList().size(); i++){
                    rpm.add(Double.parseDouble(String.valueOf(i)));
                }
            }
        }


        for(int i = 1; i < list.size(); i++){
            String name = list.get(i).getName();
            var temp = new XYSeries(name);
            LinkedList<Double> arr = list.get(i).getList();
            for(int j = 0; j < arr.size(); j++){
                temp.add(rpm.get(j), arr.get(j));
            }
            var dataset = new XYSeriesCollection();
            dataset.addSeries(temp);
            datasetCol.add(dataset);
        }

        return datasetCol;
    }
}
