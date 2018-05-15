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
import java.awt.Graphics;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
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
    
    public PrintTiket(){
        printerName = "";
    }
    
    public PrintTiket(String printerName){
        this.printerName = printerName;
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
   
   //solo implementa la interfaz de printable pero no se ocupa
   @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException{      
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
            bk.append(new PrintTiket(), job.defaultPage(), 1);      
            // Pass the book to the PrinterJob      
            job.setPageable(bk);      
            // Put up the dialog box      
              // Print the job if the user didn't cancel printing  
            try {job.print();}           
            catch (Exception e) {return false;}   
            return true;
        }
    
}

