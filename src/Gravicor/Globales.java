/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel
 */
public class Globales {
    public static BD baseDatos = null;//new BD("LAPTOP-LVSV7Q0O","1433","GRAVICOR","luisdarivero.s@gmail.com","adminJava");// null;
    //esta variable temporal de base de datos solo es para pruebas
    public static BD bdTemp =  new BD("LAPTOP-LVSV7Q0O","1433","GRAVICOR","luisdarivero.s@gmail.com","adminJava");
    
    public static String currentUser = "luisdaniel";
    
    public static String temp = "1";
    
    public static String ultimoCamion = "2";
    
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
    }
    
    
}
