package org.example;

import java.util.LinkedList;

public class Column {

    String name, value;
    LinkedList<Double> numbers;

    Column(String n, String v, LinkedList<Double> num){
        this.name = n;
        this.value = v;
        this.numbers = num;
    }
    public String getName(){
        return this.name;
    }
    public String getValue(){
        return this.value;
    }

    public LinkedList<Double> getList(){
        return this.numbers;
    }

    public void printColumn(){
        System.out.printf("%s\n%s\n", this.name, this.value);
        for(Double d: this.numbers){
            System.out.println(d);
        }
    }

}
