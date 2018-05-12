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
    //guarda la informacion de la base de datos que hace todas las peticiones
    public static BD baseDatos = null;
    //guarda el usuario que está dentro del sistema
    public static String currentUser = "luisdaniel";
    
    //funcion que verifica si una cadena de texto es un string valido
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
