/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.GregorianCalendar;

/**
 *
 * @author Daniel
 */
public class MyGregorianCalendar extends GregorianCalendar{
    public MyGregorianCalendar(){
        super();
    }
    
    public  int getMes(){
        return this.DATE;
    }
    
    public int getDia(){
        return this.DAY_OF_MONTH;
    }
    
    public int getAno(){
        return this.YEAR;
    }
   
    @Override
    public String toString(){
        String cadena = Integer.toString(this.DAY_OF_MONTH) + "-" + Integer.toString(this.DATE) + "-" + Integer.toString(this.YEAR);
        return cadena;
    }
}
