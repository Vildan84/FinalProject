package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadCSV {

    public LinkedList<String[]> ReadFile(String filename){

        LinkedList<String[]> arr = new LinkedList<>();
        try{
            CSVReader reader = new CSVReaderBuilder(new FileReader(filename)).withSkipLines(5).build();
            String[] str;
            while((str = reader.readNext()) != null){
                arr.add(str);
            }

        }
        catch (IOException | CsvValidationException e){
            System.out.println(e.getMessage());
        }
        return arr;

    }
}
