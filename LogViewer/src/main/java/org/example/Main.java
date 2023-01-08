package org.example;

import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {

        ReadCSV r = new ReadCSV();
        CreateColumn col = new CreateColumn();
        String file = "test.CSV";
        LinkedList<String[]> array = r.ReadFile(file);
        LinkedList<Column> list = col.Columns(array);

        for(Column c: list){
            c.printColumn();
        }







    }
}