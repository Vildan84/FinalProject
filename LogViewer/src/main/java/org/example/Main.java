package org.example;

import org.example.jfreechart.Chart;
import org.example.jfreechart.XYDataset;
import org.example.csv.Column;
import org.example.csv.CreateColumn;
import org.example.csv.ReadCSV;
import org.example.telegram.MyBot;
import org.jfree.data.xy.XYSeriesCollection;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.io.IOException;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {

//        ReadCSV r = new ReadCSV();
//        CreateColumn col = new CreateColumn();
//        XYDataset data = new XYDataset();
//        Chart chart = new Chart();
//        String file = "test.CSV";
//        LinkedList<String[]> array = r.ReadFile(file);
//        LinkedList<Column> list = col.Columns(array);
//
//        LinkedList<XYSeriesCollection> dataset = data.createDataset(list);
//        chart.chart(dataset, list);

        MyBot bot = new MyBot();

        TelegramBotsApi tb = new TelegramBotsApi(DefaultBotSession.class);
        tb.registerBot(bot);


    }
}