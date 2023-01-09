package csv;

import jfreechart.Chart;
import jfreechart.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.IOException;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) throws IOException {

        ReadCSV r = new ReadCSV();
        CreateColumn col = new CreateColumn();
        XYDataset data = new XYDataset();
        Chart chart = new Chart();
        String file = "test.CSV";
        LinkedList<String[]> array = r.ReadFile(file);
        LinkedList<Column> list = col.Columns(array);

        LinkedList<XYSeriesCollection> dataset = data.createDataset(list);
        chart.chart(dataset, list);









    }
}