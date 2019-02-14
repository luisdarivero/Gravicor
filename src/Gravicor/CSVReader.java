/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;


/**
 *
 * @author Daniel
 */
public class CSVReader {
    
    private String pathname = null;
    
    public CSVReader(String pathname){
        this.pathname = pathname;
    }
    
    public LinkedList<String> readFirstColumn(){
        BufferedReader reader;
        LinkedList<String> columna = new LinkedList<String>();
        try {
            reader = new BufferedReader(new FileReader(pathname));
            String line = reader.readLine();
            while (line != null) {
                    //se analiza el archivo
                    String[] columnas = line.split(",");
                    if(!columnas[0].equals("")){
                        columna.add(columnas[0]);
                        
                    }
                    line = reader.readLine();
            }
            reader.close();
            return columna;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
}
