package org.example.csv;

import org.example.csv.Column;

import java.util.LinkedList;

public class CreateColumn {

    public LinkedList<Column> Columns(LinkedList<String[]> arr){
        LinkedList<Column> list = new LinkedList<>();
        int index = 0;
        for(String name: arr.getFirst()){
            if(!name.equals("") && !name.equals("STAMP")){
                LinkedList<Double> temp = new LinkedList<>();
                String value = arr.get(1)[index];

                for(int i = 2; i < arr.size(); i++){
                    if(arr.get(i)[index].matches("-?\\d+(\\.\\d+)?")){
                        temp.add(Double.parseDouble(arr.get(i)[index]));
                    }
                    else{
                        String s = arr.get(i)[index];
                        if(s.contains(" ")){
                            int e = s.indexOf(" ");
                            temp.add(Double.parseDouble(s.substring(0, e)));
                        }
                    }
                }
                list.add(new Column(name, value, temp));
            }
            index++;

        }
        return list;

    }
}
