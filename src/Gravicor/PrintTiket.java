/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

/**
 *
 * @author Gravicor
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
 
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Gravicor
 */
public class PrintTiket implements Printable{
    private String printerName;
    private fontType myFont= fontType.NORMAL;
    private String encabezado = "";
    
    
    public PrintTiket(){
        printerName = "";
    }
    
    public enum fontType {
        NORMAL,
        CONENCABEZADO
    }

    
    public PrintTiket(String printerName){
        this.printerName = printerName;
    }
    
    public PrintTiket(String printerName, String encabezado, fontType nuevo){
        this.printerName = printerName;
        setMyFont(encabezado, nuevo);
    }
    //metodo que te regresa las impresoras disponibles
   public List<String> getPrinters(){
		
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                        flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
                printerList.add( printerService.getName());
        }

        return printerList;
    }
   
   public String getTicketRegistroVentas(String folioVenta, String fecha, String cliente,
                                            String tipoPago, String material, String cantidad,
                                            String folioTransportista, String matricula, String nombreChofer,
                                            String plantaProductora, String precio){
       String resultado = "";
       
       resultado += "Folio de venta: {{folioVenta}}\n";
       resultado += "Fecha: {{fecha}}\n";
       
       resultado += "========================================\n";
       
       resultado += "Cliente: {{cliente}}\n";
       resultado += "Tipo de pago: {{tipoPago}}\n";
       resultado += "Material: {{material}}\n";
       resultado += "Cantidad en metros cubicos: {{cantidad}}\n";
       resultado += "Folio transportista: {{folioTransportista}}\n";
       resultado += "Matricula de camion: {{matricula}}\n";
       resultado += "Nombre de chofer: {{nombreChofer}}\n";
       resultado += "Planta productora: {{plantaProductora}}\n";
       
       resultado += "========================================\n";
       
       resultado += "Precio total: {{precio}}\n";
       
       resultado = resultado.replace("{{folioVenta}}", folioVenta);
       resultado = resultado.replace("{{fecha}}", fecha);
       resultado = resultado.replace("{{cliente}}", cliente);
       resultado = resultado.replace("{{tipoPago}}", tipoPago);
       resultado = resultado.replace("{{material}}", material);
       resultado = resultado.replace("{{cantidad}}", cantidad);
       resultado = resultado.replace("{{folioTransportista}}", folioTransportista);
       resultado = resultado.replace("{{matricula}}", matricula);
       resultado = resultado.replace("{{nombreChofer}}", nombreChofer);
       resultado = resultado.replace("{{plantaProductora}}", plantaProductora);
       resultado = resultado.replace("{{precio}}", precio);
       
       return resultado;
   }
   
   public String getTicketHistorialViajesDia(String fecha, String m3, String toneladas,
                                                LinkedList<LinkedList<String>> viajes){
       
       String resultado = "";
       
       resultado += "Historial de viajes del dia - {{fecha}}\n";
       resultado += "Metros cuadrados totales: {{m3}}\n";
       resultado += "Toneladas totales: {{toneladas}}\n";
       
       resultado += "========================================\n";
       
       for(int i = 0 ; i < viajes.get(0).size(); i++){
           resultado += "Camion #" + viajes.get(0).get(i) + " (" + viajes.get(1).get(i) + "):\n";
           resultado += "    Viajes: " + viajes.get(3).get(i) + ", M3: ";
           int numViajes = Integer.parseInt(viajes.get(3).get(i));
           int capacidad = Integer.parseInt(viajes.get(2).get(i));
           Integer metrosC = numViajes * capacidad;
           resultado += metrosC.toString() + ", Toneladas: " + Integer.parseInt(viajes.get(4).get(i)) 
                    + "\n";
       }
       
       resultado = resultado.replace("{{fecha}}", fecha);
       resultado = resultado.replace("{{m3}}", m3);
       resultado = resultado.replace("{{toneladas}}", toneladas);
       
       return resultado;
   }
   
   public String getTicketRegistroViajeCmpletado(String dia, String camion, String tipoCamion,
                                                    String operador, String[] viajes,
                                                    String conteoViajes, String m3, String toneladas){
       String resultado = "";
       
       resultado += "Viajes completados - {{dia}}\n";
       resultado += "Camion: {{camion}}\n";
       resultado += "Tipo de camion: {{tipoCamion}}\n";
       resultado += "Operador: {{operador}}\n";
       resultado += "========================================\n";
       
       for(int i = 0; i < viajes.length ; i++){
           resultado += "viaje #" + (new Integer(i+1)) + ": " + viajes[i] + "\n";
       }
       
       resultado += "========================================\n";
       resultado += "Conteo de viajes: {{conteoViajes}}\n";
       resultado += "Metros cubicos totales: {{m3}}\n";
       resultado += "Toneladas totales: {{toneladas}}\n";
       resultado += "\n\n";
       
       //reemplazar datos
       resultado = resultado.replace("{{dia}}", dia);
       resultado = resultado.replace("{{camion}}", camion);
       resultado = resultado.replace("{{tipoCamion}}", tipoCamion);
       resultado = resultado.replace("{{operador}}", operador);
       resultado = resultado.replace("{{conteoViajes}}", conteoViajes);
       resultado = resultado.replace("{{m3}}", m3);
       resultado = resultado.replace("{{toneladas}}", toneladas);
       
       return resultado;
   }
   public void setMyFont(String encabezado, fontType nuevo){
       this.encabezado = encabezado;
       this.myFont = nuevo;
   }
   
   
   
   //implementa la interfaz de printable
   @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException{   
        
        if(this.myFont == fontType.CONENCABEZADO){
            Graphics2D g2d = (Graphics2D) g;
            Font titleFont = new Font("Serif", Font.PLAIN, 38);
            g2d.setFont(titleFont);
            g2d.translate(10.0, 10.0);
            
            //System.out.println(pf.getImageableX());
            //System.out.println(pf.getImageableY());
            g2d.drawString(this.encabezado, 40, 22);
            
        }
        
        return Printable.PAGE_EXISTS;    
    } 
    
    public void printString(String printerName, String text) {
        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                        flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

                byte[] bytes;

                // important for umlaut chars
                bytes = text.getBytes("CP437");

                Doc doc = new SimpleDoc(bytes, flavor, null);


                job.print(doc, null);

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
 
	}
    
    public void printBytes(String printerName, byte[] bytes) {
		
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                        flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

                Doc doc = new SimpleDoc(bytes, flavor, null);

                job.print(doc, null);

        } catch (Exception e) {
                e.printStackTrace();
        }
    }
	
    private PrintService findPrintService(String printerName,
                    PrintService[] services) {
        for (PrintService service : services) {
                if (service.getName().equalsIgnoreCase(printerName)) {
                        return service;
                }
        }

        return null;
    }
    
    public boolean printMyVentaTicket(String folioVenta){
        
        
        try{
            printString(this.printerName, folioVenta);
            cutPaper();
        }
        catch(Exception e){
            return false;
        }
        
        
        return true;
    }
    
    public boolean printMyTicket(String ticketInfo){
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                        flavor, pras);
        PrintService service = findPrintService(printerName, printService);
        if(service == null){
            return false;
        }
        
        boolean status;
        
        status = printPage();
        if(!status){
            return status;
        }
        
        try{
            printString(this.printerName, ticketInfo);
            cutPaper();
        }
        catch(Exception e){
            return false;
        }
        
        return true;
    }
    
    private boolean cutPaper(){
        try{
            byte[] cutP = {27, 100, 3};// new byte[] { 0x30, 'V', 1 };
            printBytes(this.printerName, cutP);
        }
        catch(Exception e){
            return false;
        }
        
        return true;
    }
    
    private boolean printPage(){
            PrintService printService[] = PrinterJob.lookupPrintServices();
            boolean validarImpresora = false;
            for(int i = 0 ; i< printService.length; i++){
                if(printService[i].toString().contains(this.printerName)){
                    validarImpresora = true;
                }
            }
            if(!validarImpresora){
                return false;
            }
            
            
            PrintService service = findPrintService(this.printerName, printService);
            PrinterJob job = PrinterJob.getPrinterJob();
            try{
                job.setPrintService(service);
            }
            catch(Exception e){
                return false;
            }
            
            Book bk = new Book();   
            //bk.
            //bk.append(new PrintTiket(this.printerName, this.encabezado, this.myFont), job.defaultPage(), 1);   
            bk.append(new PrintTiket(this.printerName, this.encabezado, this.myFont), getMinimumMarginPageFormat(job), 1);   
            // Pass the book to the PrinterJob      
            job.setPageable(bk);      
            // Put up the dialog box      
              // Print the job if the user didn't cancel printing  
            try {job.print();}           
            catch (Exception e) {return false;}   
            return true;
        }
    
    private PageFormat getMinimumMarginPageFormat(PrinterJob printJob) {
        PageFormat pf0 = printJob.defaultPage();
        PageFormat pf1 = (PageFormat) pf0.clone();
        Paper p = pf0.getPaper();
        p.setImageableArea(0, 0,pf0.getWidth(), pf0.getHeight());
        pf1.setPaper(p);
        PageFormat pf2 = printJob.validatePage(pf1);
        return pf2;     
    }
    
}

