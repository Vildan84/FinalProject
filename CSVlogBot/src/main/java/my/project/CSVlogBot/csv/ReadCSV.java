package my.project.CSVlogBot.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
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
            log.error("Error occurred: " + e.getMessage());
        }
        return arr;

    }
}
