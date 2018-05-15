/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Daniel
 */
public class ConfiguracionPrograma {
    public Hashtable<String, Integer> configuraciones = new Hashtable<String, Integer>();
    public final String FICHERO = "./Configuraciones/Configuraciones.config";
    
    public ConfiguracionPrograma(){
        //configura valores por default, solo valores enteros
        this.configuraciones.put("TiempoEsperaSiguienteViaje", 20);
        this.configuraciones.put("TiempoEsperaConfirmacionViaje", 12);
        this.configuraciones.put("ImpresionesMaximasPorOperadorAlDia", 3);
        if(!recuperarDatos()){
            escribirDatos();
        }
        
    }
    
    public Integer getValueWithHash(String hash){
        return configuraciones.get(hash);
    }
    
    public boolean modificarConfiguraciones(String[] keys, Integer[] values){
        for(int i = 0; i< keys.length; i++){
            if(configuraciones.containsKey(keys[i])){
                configuraciones.put(keys[i], values[i]);
            }
        }
        
        return escribirDatos();
    }
    
    public boolean escribirDatos(){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(FICHERO);
            pw = new PrintWriter(fichero);
            
            Set entrySet = configuraciones.entrySet();
            Iterator it = entrySet.iterator();
            while(it.hasNext()){
                //System.out.println(it.next());
                pw.println(it.next());
            }
            
                

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        return true;
    }
    
    public boolean recuperarDatos(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           archivo = new File (FICHERO);
           //System.out.println ("Directorio actual: " + archivo.getCanonicalPath());
           if(archivo.isFile()){
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);
                // Lectura del fichero
                String linea;
                while((linea=br.readLine())!=null){
                    //System.out.println(linea);
                   String[] resultado = linea.split("=");
                   if(resultado.length == 2 && configuraciones.containsKey(resultado[0])){
                       configuraciones.put(resultado[0], new Integer(resultado[1]));
                   }
                }
                
           }
           else{
               //System.out.println("llego aqui");
               return false;
           }
           
        }
        catch(Exception e){
           //e.printStackTrace();
           return false;
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              //e2.printStackTrace();
           }
        }
        return true;
    }
}
