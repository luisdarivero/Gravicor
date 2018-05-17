/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import java.util.Calendar;
import javax.swing.SwingConstants;
import java.util.Date;
/**
 *
 * @author Mario
 */
public class GenerarPDF {
    
    public void generarPDF(String mes, String anio, String arregloSemanas[], String imgUrl, String pathPDF){
        try{            
            Document document = new Document(PageSize.A7, 36, 36, 10, 10);
            PdfWriter.getInstance(document, new FileOutputStream(pathPDF));
            document.open();
            
            Image imagen = Image.getInstance(imgUrl);
            imagen.scaleAbsolute(15, 15);
            imagen.setAlignment(Element.ALIGN_TOP);
            
            Paragraph titulo = new Paragraph("Ingresos por la Venta de Arenilla " + " (" + mes + ", " + anio + ")");
            titulo.getFont().setColor(BaseColor.RED);
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(5);
            titulo.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph saltoLinea = new Paragraph("\n");
            
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{30,20,25,25});
            
            Paragraph clienteLbl = new Paragraph("CLIENTE");
            clienteLbl.getFont().setStyle(Font.BOLD);
            clienteLbl.getFont().setSize(3);
            tabla.addCell(clienteLbl);
            
            
            Paragraph precioLbl = new Paragraph("PRECIO");
            precioLbl.getFont().setStyle(Font.BOLD);
            precioLbl.getFont().setSize(3);
            tabla.addCell(precioLbl);
            
            Paragraph cantidadLbl = new Paragraph("CANTIDAD");
            cantidadLbl.getFont().setStyle(Font.BOLD);
            cantidadLbl.getFont().setSize(3);
            tabla.addCell(cantidadLbl);
            
            Paragraph totalLbl = new Paragraph("TOTAL");
            totalLbl.getFont().setStyle(Font.BOLD);
            totalLbl.getFont().setSize(3);
            tabla.addCell(totalLbl);
            switch(arregloSemanas.length){
                case 8:
                    Float precioFinalCliente = 0f;
                    Float cantidadFinalCliente = 0f;
                    for(int i=0; i<7; i++){
                        switch(i){
                            case 0:
                                Paragraph semanas = new Paragraph(arregloSemanas[i]);
                                semanas.getFont().setStyle(Font.BOLD);
                                semanas.getFont().setSize(3);
                                PdfPCell celdaAgrup = new PdfPCell(semanas);
                                celdaAgrup.setColspan(5);
                                tabla.addCell(celdaAgrup);
                                String query = arregloSemanas[i+1];
                                String[] Columnas = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados = Globales.bdTemp.select(query, Columnas);
                                String IDCliente = "-1";
                                String nombre = "";
                                String cantidad = "";
                                String precio = "";
                                Float sumaCantidadCliente = 0.0f;
                                Float sumaPrecioCliente = 0.0f;
                                Float promedioPrecio = 0.0f;
                                Float k = 0f;
                                Float precioFinal = 0f;
                                Float cantidadPorSemana = 0f;
                                Float precioPorSemana = 0f;
                                boolean flag = false;
                                for(int j=0; j<datosConsultados.get(0).size(); j++){
                                    if(!IDCliente.equals(datosConsultados.get(1).get(j))){
                                        if(datosConsultados.get(0).size() == 1){
                                            IDCliente = datosConsultados.get(1).get(j);
                                            nombre = datosConsultados.get(0).get(j);
                                            cantidad = datosConsultados.get(4).get(j);
                                            precio = datosConsultados.get(3).get(j);
                                            sumaCantidadCliente += Float.parseFloat(cantidad);
                                            sumaPrecioCliente += Float.parseFloat(precio);
                                            precioFinal = sumaCantidadCliente * sumaPrecioCliente;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(sumaPrecioCliente.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;
                                        }
                                        if(flag == true){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;     
                                        }
                                        IDCliente = datosConsultados.get(1).get(j);
                                        nombre = datosConsultados.get(0).get(j);
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);

                                        k++;
                                        flag = true;
                                        
                                    }
                                    else{
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);
                                        k++;
                                        if(j == datosConsultados.get(0).size()-1){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;
                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;  
                                        }
                                    }                                    
                                }
                                Paragraph finSemana = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre = new Paragraph(" ");
                                Paragraph finSemCantidad = new Paragraph(cantidadPorSemana.toString());
                                Paragraph finSemPrecioFinal = new Paragraph(precioPorSemana.toString());
                                precioFinalCliente += precioPorSemana;
                                cantidadFinalCliente += cantidadPorSemana;
                                finSemana.getFont().setStyle(Font.BOLD);
                                finSemana.getFont().setSize(3);
                                finSemanaPre.getFont().setStyle(Font.BOLD);
                                finSemanaPre.getFont().setSize(3);
                                finSemCantidad.getFont().setStyle(Font.BOLD);
                                finSemCantidad.getFont().setSize(3);
                                finSemPrecioFinal.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal.getFont().setSize(3);
                                tabla.addCell(finSemana);
                                tabla.addCell(finSemanaPre);
                                tabla.addCell(finSemCantidad);
                                tabla.addCell(finSemPrecioFinal);
                                cantidadPorSemana = 0f;
                                precioPorSemana = 0f;
                            break;
                            case 2:
                                Paragraph semanas2 = new Paragraph(arregloSemanas[i]);
                                semanas2.getFont().setStyle(Font.BOLD);
                                semanas2.getFont().setSize(3);
                                PdfPCell celdaAgrup2 = new PdfPCell(semanas2);
                                celdaAgrup2.setColspan(5);
                                tabla.addCell(celdaAgrup2);
                                String query2 = arregloSemanas[i+1];
                                String[] Columnas2 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados2 = Globales.bdTemp.select(query2, Columnas2);
                                String IDCliente2 = "-1";
                                String nombre2 = "";
                                String cantidad2 = "";
                                String precio2 = "";
                                Float sumaCantidadCliente2 = 0.0f;
                                Float sumaPrecioCliente2 = 0.0f;
                                Float promedioPrecio2 = 0.0f;
                                Float k2 = 0f;
                                Float precioFinal2 = 0f;
                                Float cantidadPorSemana2 = 0f;
                                Float precioPorSemana2 = 0f;
                                boolean flag2 = false;
                                for(int j=0; j<datosConsultados2.get(0).size(); j++){
                                    if(!IDCliente2.equals(datosConsultados2.get(1).get(j))){
                                        if(datosConsultados2.get(0).size() == 1){
                                            IDCliente2 = datosConsultados2.get(1).get(j);
                                            nombre2 = datosConsultados2.get(0).get(j);
                                            cantidad2 = datosConsultados2.get(4).get(j);
                                            precio2 = datosConsultados2.get(3).get(j);
                                            sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                            sumaPrecioCliente2 += Float.parseFloat(precio2);
                                            precioFinal2 = sumaCantidadCliente2 * sumaPrecioCliente2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(sumaPrecioCliente2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;
                                        }
                                        if(flag2 == true){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;     
                                        }
                                        IDCliente2 = datosConsultados2.get(1).get(j);
                                        nombre2 = datosConsultados2.get(0).get(j);
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);

                                        k2++;
                                        flag2 = true;
                                        
                                    }
                                    else{
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);
                                        k2++;
                                        if(j == datosConsultados2.get(0).size()-1){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;
                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana2 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre2 = new Paragraph(" ");
                                Paragraph finSemCantidad2 = new Paragraph(cantidadPorSemana2.toString());
                                Paragraph finSemPrecioFinal2 = new Paragraph(precioPorSemana2.toString());
                                precioFinalCliente += precioPorSemana2;
                                cantidadFinalCliente += cantidadPorSemana2;
                                finSemana2.getFont().setStyle(Font.BOLD);
                                finSemana2.getFont().setSize(3);
                                finSemanaPre2.getFont().setStyle(Font.BOLD);
                                finSemanaPre2.getFont().setSize(3);
                                finSemCantidad2.getFont().setStyle(Font.BOLD);
                                finSemCantidad2.getFont().setSize(3);
                                finSemPrecioFinal2.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal2.getFont().setSize(3);
                                tabla.addCell(finSemana2);
                                tabla.addCell(finSemanaPre2);
                                tabla.addCell(finSemCantidad2);
                                tabla.addCell(finSemPrecioFinal2);
                                cantidadPorSemana2 = 0f;
                                precioPorSemana2 = 0f;
                            break;
                            case 4:
                                Paragraph semanas3 = new Paragraph(arregloSemanas[i]);
                                semanas3.getFont().setStyle(Font.BOLD);
                                semanas3.getFont().setSize(3);
                                PdfPCell celdaAgrup3 = new PdfPCell(semanas3);
                                celdaAgrup3.setColspan(5);
                                tabla.addCell(celdaAgrup3);
                                String query3 = arregloSemanas[i+1];
                                String[] Columnas3 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados3 = Globales.bdTemp.select(query3, Columnas3);
                                String IDCliente3 = "-1";
                                String nombre3 = "";
                                String cantidad3 = "";
                                String precio3 = "";
                                Float sumaCantidadCliente3 = 0.0f;
                                Float sumaPrecioCliente3 = 0.0f;
                                Float promedioPrecio3 = 0.0f;
                                Float k3 = 0f;
                                Float precioFinal3 = 0f;
                                Float cantidadPorSemana3 = 0f;
                                Float precioPorSemana3 = 0f;
                                boolean flag3 = false;
                                for(int j=0; j<datosConsultados3.get(0).size(); j++){
                                    if(!IDCliente3.equals(datosConsultados3.get(1).get(j))){
                                        if(datosConsultados3.get(0).size() == 1){
                                            IDCliente3 = datosConsultados3.get(1).get(j);
                                            nombre3 = datosConsultados3.get(0).get(j);
                                            cantidad3 = datosConsultados3.get(4).get(j);
                                            precio3 = datosConsultados3.get(3).get(j);
                                            sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                            sumaPrecioCliente3 += Float.parseFloat(precio3);
                                            precioFinal3 = sumaCantidadCliente3 * sumaPrecioCliente3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(sumaPrecioCliente3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;
                                        }
                                        if(flag3 == true){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;     
                                        }
                                        IDCliente3 = datosConsultados3.get(1).get(j);
                                        nombre3 = datosConsultados3.get(0).get(j);
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);

                                        k3++;
                                        flag3 = true;
                                        
                                    }
                                    else{
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);
                                        k3++;
                                        if(j == datosConsultados3.get(0).size()-1){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;
                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana3 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre3 = new Paragraph(" ");
                                Paragraph finSemCantidad3 = new Paragraph(cantidadPorSemana3.toString());
                                Paragraph finSemPrecioFinal3 = new Paragraph(precioPorSemana3.toString());
                                precioFinalCliente += precioPorSemana3;
                                cantidadFinalCliente += cantidadPorSemana3;
                                finSemana3.getFont().setStyle(Font.BOLD);
                                finSemana3.getFont().setSize(3);
                                finSemanaPre3.getFont().setStyle(Font.BOLD);
                                finSemanaPre3.getFont().setSize(3);
                                finSemCantidad3.getFont().setStyle(Font.BOLD);
                                finSemCantidad3.getFont().setSize(3);
                                finSemPrecioFinal3.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal3.getFont().setSize(3);
                                tabla.addCell(finSemana3);
                                tabla.addCell(finSemanaPre3);
                                tabla.addCell(finSemCantidad3);
                                tabla.addCell(finSemPrecioFinal3);
                                cantidadPorSemana3 = 0f;
                                precioPorSemana3 = 0f;
                            break;
                            case 6:
                                Paragraph semanas4 = new Paragraph(arregloSemanas[i]);
                                semanas4.getFont().setStyle(Font.BOLD);
                                semanas4.getFont().setSize(3);
                                PdfPCell celdaAgrup4 = new PdfPCell(semanas4);
                                celdaAgrup4.setColspan(5);
                                tabla.addCell(celdaAgrup4);
                                String query4 = arregloSemanas[i+1];
                                String[] Columnas4 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados4 = Globales.bdTemp.select(query4, Columnas4);
                                String IDCliente4 = "-1";
                                String nombre4 = "";
                                String cantidad4 = "";
                                String precio4 = "";
                                Float sumaCantidadCliente4 = 0.0f;
                                Float sumaPrecioCliente4 = 0.0f;
                                Float promedioPrecio4 = 0.0f;
                                Float k4 = 0f;
                                Float precioFinal4 = 0f;
                                Float cantidadPorSemana4 = 0f;
                                Float precioPorSemana4 = 0f;
                                boolean flag4 = false;
                                for(int j=0; j<datosConsultados4.get(0).size(); j++){
                                    if(!IDCliente4.equals(datosConsultados4.get(1).get(j))){
                                        if(datosConsultados4.get(0).size() == 1){
                                            IDCliente4 = datosConsultados4.get(1).get(j);
                                            nombre4 = datosConsultados4.get(0).get(j);
                                            cantidad4 = datosConsultados4.get(4).get(j);
                                            precio4 = datosConsultados4.get(3).get(j);
                                            sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                            sumaPrecioCliente4 += Float.parseFloat(precio4);
                                            precioFinal4 = sumaCantidadCliente4 * sumaPrecioCliente4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(sumaPrecioCliente4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;
                                        }
                                        if(flag4 == true){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;     
                                        }
                                        IDCliente4 = datosConsultados4.get(1).get(j);
                                        nombre4 = datosConsultados4.get(0).get(j);
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);

                                        k4++;
                                        flag4 = true;
                                        
                                    }
                                    else{
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);
                                        k4++;
                                        if(j == datosConsultados4.get(0).size()-1){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;
                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana4 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre4 = new Paragraph(" ");
                                Paragraph finSemCantidad4 = new Paragraph(cantidadPorSemana4.toString());
                                Paragraph finSemPrecioFinal4 = new Paragraph(precioPorSemana4.toString());
                                precioFinalCliente += precioPorSemana4;
                                cantidadFinalCliente += cantidadPorSemana4;
                                finSemana4.getFont().setStyle(Font.BOLD);
                                finSemana4.getFont().setSize(3);
                                finSemanaPre4.getFont().setStyle(Font.BOLD);
                                finSemanaPre4.getFont().setSize(3);
                                finSemCantidad4.getFont().setStyle(Font.BOLD);
                                finSemCantidad4.getFont().setSize(3);
                                finSemPrecioFinal4.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal4.getFont().setSize(3);
                                tabla.addCell(finSemana4);
                                tabla.addCell(finSemanaPre4);
                                tabla.addCell(finSemCantidad4);
                                tabla.addCell(finSemPrecioFinal4);
                                cantidadPorSemana4 = 0f;
                                precioPorSemana4 = 0f;
                            break;
                        }
                    }
                    Paragraph totalSemanaui = new Paragraph("CANTIDAD TOTAL");
                    totalSemanaui.getFont().setStyle(Font.BOLD);
                    totalSemanaui.getFont().setSize(3);
                    Paragraph totalSemanaua = new Paragraph(cantidadFinalCliente.toString());
                    totalSemanaua.getFont().setStyle(Font.BOLD);
                    totalSemanaua.getFont().setSize(3);
                    Paragraph totalSemanaue = new Paragraph("INGRESO TOTAL");
                    totalSemanaue.getFont().setStyle(Font.BOLD);
                    totalSemanaue.getFont().setSize(3);
                    Paragraph totalSemanauo = new Paragraph(precioFinalCliente.toString());
                    totalSemanauo.getFont().setStyle(Font.BOLD);
                    totalSemanauo.getFont().setSize(3);
                    PdfPCell cellui = new PdfPCell();
                    PdfPCell cellua = new PdfPCell();
                    PdfPCell cellue = new PdfPCell();
                    PdfPCell celluo = new PdfPCell();
                    cellui.addElement(totalSemanaui);
                    cellui.setBackgroundColor(BaseColor.YELLOW);
                    cellua.addElement(totalSemanaua);
                    cellue.addElement(totalSemanaue);
                    cellue.setBackgroundColor(BaseColor.YELLOW);
                    celluo.addElement(totalSemanauo);
                    tabla.addCell(cellui);
                    tabla.addCell(cellua);
                    tabla.addCell(cellue);
                    tabla.addCell(celluo);
                    break;
                case 10:
                    Float precioFinalCliente2 = 0f;
                    Float cantidadFinalCliente2 = 0f;
                    for(int i=0; i<10; i++){
                        switch(i){
                            case 0:
                                Paragraph semanas = new Paragraph(arregloSemanas[i]);
                                semanas.getFont().setStyle(Font.BOLD);
                                semanas.getFont().setSize(3);
                                PdfPCell celdaAgrup = new PdfPCell(semanas);
                                celdaAgrup.setColspan(5);
                                tabla.addCell(celdaAgrup);
                                String query = arregloSemanas[i+1];
                                String[] Columnas = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados = Globales.bdTemp.select(query, Columnas);
                                String IDCliente = "-1";
                                String nombre = "";
                                String cantidad = "";
                                String precio = "";
                                Float sumaCantidadCliente = 0.0f;
                                Float sumaPrecioCliente = 0.0f;
                                Float promedioPrecio = 0.0f;
                                Float k = 0f;
                                Float precioFinal = 0f;
                                Float cantidadPorSemana = 0f;
                                Float precioPorSemana = 0f;
                                boolean flag = false;
                                for(int j=0; j<datosConsultados.get(0).size(); j++){
                                    if(!IDCliente.equals(datosConsultados.get(1).get(j))){
                                        if(datosConsultados.get(0).size() == 1){
                                            IDCliente = datosConsultados.get(1).get(j);
                                            nombre = datosConsultados.get(0).get(j);
                                            cantidad = datosConsultados.get(4).get(j);
                                            precio = datosConsultados.get(3).get(j);
                                            sumaCantidadCliente += Float.parseFloat(cantidad);
                                            sumaPrecioCliente += Float.parseFloat(precio);
                                            precioFinal = sumaCantidadCliente * sumaPrecioCliente;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(sumaPrecioCliente.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;
                                        }
                                        if(flag == true){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;     
                                        }
                                        IDCliente = datosConsultados.get(1).get(j);
                                        nombre = datosConsultados.get(0).get(j);
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);

                                        k++;
                                        flag = true;
                                        
                                    }
                                    else{
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);
                                        k++;
                                        if(j == datosConsultados.get(0).size()-1){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;
                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre = new Paragraph(" ");
                                Paragraph finSemCantidad = new Paragraph(cantidadPorSemana.toString());
                                Paragraph finSemPrecioFinal = new Paragraph(precioPorSemana.toString());
                                precioFinalCliente2 += precioPorSemana;
                                cantidadFinalCliente2 += cantidadPorSemana;
                                finSemana.getFont().setStyle(Font.BOLD);
                                finSemana.getFont().setSize(3);
                                finSemanaPre.getFont().setStyle(Font.BOLD);
                                finSemanaPre.getFont().setSize(3);
                                finSemCantidad.getFont().setStyle(Font.BOLD);
                                finSemCantidad.getFont().setSize(3);
                                finSemPrecioFinal.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal.getFont().setSize(3);
                                tabla.addCell(finSemana);
                                tabla.addCell(finSemanaPre);
                                tabla.addCell(finSemCantidad);
                                tabla.addCell(finSemPrecioFinal);
                                cantidadPorSemana = 0f;
                                precioPorSemana = 0f;
                            break;
                            case 2:
                                Paragraph semanas2 = new Paragraph(arregloSemanas[i]);
                                semanas2.getFont().setStyle(Font.BOLD);
                                semanas2.getFont().setSize(3);
                                PdfPCell celdaAgrup2 = new PdfPCell(semanas2);
                                celdaAgrup2.setColspan(5);
                                tabla.addCell(celdaAgrup2);
                                String query2 = arregloSemanas[i+1];
                                String[] Columnas2 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados2 = Globales.bdTemp.select(query2, Columnas2);
                                String IDCliente2 = "-1";
                                String nombre2 = "";
                                String cantidad2 = "";
                                String precio2 = "";
                                Float sumaCantidadCliente2 = 0.0f;
                                Float sumaPrecioCliente2 = 0.0f;
                                Float promedioPrecio2 = 0.0f;
                                Float k2 = 0f;
                                Float precioFinal2 = 0f;
                                Float cantidadPorSemana2 = 0f;
                                Float precioPorSemana2 = 0f;
                                boolean flag2 = false;
                                for(int j=0; j<datosConsultados2.get(0).size(); j++){
                                    if(!IDCliente2.equals(datosConsultados2.get(1).get(j))){
                                        if(datosConsultados2.get(0).size() == 1){
                                            IDCliente2 = datosConsultados2.get(1).get(j);
                                            nombre2 = datosConsultados2.get(0).get(j);
                                            cantidad2 = datosConsultados2.get(4).get(j);
                                            precio2 = datosConsultados2.get(3).get(j);
                                            sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                            sumaPrecioCliente2 += Float.parseFloat(precio2);
                                            precioFinal2 = sumaCantidadCliente2 * sumaPrecioCliente2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(sumaPrecioCliente2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;
                                        }
                                        if(flag2 == true){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;     
                                        }
                                        IDCliente2 = datosConsultados2.get(1).get(j);
                                        nombre2 = datosConsultados2.get(0).get(j);
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);

                                        k2++;
                                        flag2 = true;
                                        
                                    }
                                    else{
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);
                                        k2++;
                                        if(j == datosConsultados2.get(0).size()-1){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;
                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana2 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre2 = new Paragraph(" ");
                                Paragraph finSemCantidad2 = new Paragraph(cantidadPorSemana2.toString());
                                Paragraph finSemPrecioFinal2 = new Paragraph(precioPorSemana2.toString());
                                precioFinalCliente2 += precioPorSemana2;
                                cantidadFinalCliente2 += cantidadPorSemana2;
                                finSemana2.getFont().setStyle(Font.BOLD);
                                finSemana2.getFont().setSize(3);
                                finSemanaPre2.getFont().setStyle(Font.BOLD);
                                finSemanaPre2.getFont().setSize(3);
                                finSemCantidad2.getFont().setStyle(Font.BOLD);
                                finSemCantidad2.getFont().setSize(3);
                                finSemPrecioFinal2.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal2.getFont().setSize(3);
                                tabla.addCell(finSemana2);
                                tabla.addCell(finSemanaPre2);
                                tabla.addCell(finSemCantidad2);
                                tabla.addCell(finSemPrecioFinal2);
                                cantidadPorSemana2 = 0f;
                                precioPorSemana2 = 0f;
                            break;
                            case 4:
                                Paragraph semanas3 = new Paragraph(arregloSemanas[i]);
                                semanas3.getFont().setStyle(Font.BOLD);
                                semanas3.getFont().setSize(3);
                                PdfPCell celdaAgrup3 = new PdfPCell(semanas3);
                                celdaAgrup3.setColspan(5);
                                tabla.addCell(celdaAgrup3);
                                String query3 = arregloSemanas[i+1];
                                String[] Columnas3 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados3 = Globales.bdTemp.select(query3, Columnas3);
                                String IDCliente3 = "-1";
                                String nombre3 = "";
                                String cantidad3 = "";
                                String precio3 = "";
                                Float sumaCantidadCliente3 = 0.0f;
                                Float sumaPrecioCliente3 = 0.0f;
                                Float promedioPrecio3 = 0.0f;
                                Float k3 = 0f;
                                Float precioFinal3 = 0f;
                                Float cantidadPorSemana3 = 0f;
                                Float precioPorSemana3 = 0f;
                                boolean flag3 = false;
                                for(int j=0; j<datosConsultados3.get(0).size(); j++){
                                    if(!IDCliente3.equals(datosConsultados3.get(1).get(j))){
                                        if(datosConsultados3.get(0).size() == 1){
                                            IDCliente3 = datosConsultados3.get(1).get(j);
                                            nombre3 = datosConsultados3.get(0).get(j);
                                            cantidad3 = datosConsultados3.get(4).get(j);
                                            precio3 = datosConsultados3.get(3).get(j);
                                            sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                            sumaPrecioCliente3 += Float.parseFloat(precio3);
                                            precioFinal3 = sumaCantidadCliente3 * sumaPrecioCliente3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(sumaPrecioCliente3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;
                                        }
                                        if(flag3 == true){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;     
                                        }
                                        IDCliente3 = datosConsultados3.get(1).get(j);
                                        nombre3 = datosConsultados3.get(0).get(j);
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);

                                        k3++;
                                        flag3 = true;
                                        
                                    }
                                    else{
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);
                                        k3++;
                                        if(j == datosConsultados3.get(0).size()-1){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;
                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana3 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre3 = new Paragraph(" ");
                                Paragraph finSemCantidad3 = new Paragraph(cantidadPorSemana3.toString());
                                Paragraph finSemPrecioFinal3 = new Paragraph(precioPorSemana3.toString());
                                precioFinalCliente2 += precioPorSemana3;
                                cantidadFinalCliente2 += cantidadPorSemana3;
                                finSemana3.getFont().setStyle(Font.BOLD);
                                finSemana3.getFont().setSize(3);
                                finSemanaPre3.getFont().setStyle(Font.BOLD);
                                finSemanaPre3.getFont().setSize(3);
                                finSemCantidad3.getFont().setStyle(Font.BOLD);
                                finSemCantidad3.getFont().setSize(3);
                                finSemPrecioFinal3.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal3.getFont().setSize(3);
                                tabla.addCell(finSemana3);
                                tabla.addCell(finSemanaPre3);
                                tabla.addCell(finSemCantidad3);
                                tabla.addCell(finSemPrecioFinal3);
                                cantidadPorSemana3 = 0f;
                                precioPorSemana3 = 0f;
                            break;
                            case 6:
                                Paragraph semanas4 = new Paragraph(arregloSemanas[i]);
                                semanas4.getFont().setStyle(Font.BOLD);
                                semanas4.getFont().setSize(3);
                                PdfPCell celdaAgrup4 = new PdfPCell(semanas4);
                                celdaAgrup4.setColspan(5);
                                tabla.addCell(celdaAgrup4);
                                String query4 = arregloSemanas[i+1];
                                String[] Columnas4 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados4 = Globales.bdTemp.select(query4, Columnas4);
                                String IDCliente4 = "-1";
                                String nombre4 = "";
                                String cantidad4 = "";
                                String precio4 = "";
                                Float sumaCantidadCliente4 = 0.0f;
                                Float sumaPrecioCliente4 = 0.0f;
                                Float promedioPrecio4 = 0.0f;
                                Float k4 = 0f;
                                Float precioFinal4 = 0f;
                                Float cantidadPorSemana4 = 0f;
                                Float precioPorSemana4 = 0f;
                                boolean flag4 = false;
                                for(int j=0; j<datosConsultados4.get(0).size(); j++){
                                    if(!IDCliente4.equals(datosConsultados4.get(1).get(j))){
                                        if(datosConsultados4.get(0).size() == 1){
                                            IDCliente4 = datosConsultados4.get(1).get(j);
                                            nombre4 = datosConsultados4.get(0).get(j);
                                            cantidad4 = datosConsultados4.get(4).get(j);
                                            precio4 = datosConsultados4.get(3).get(j);
                                            sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                            sumaPrecioCliente4 += Float.parseFloat(precio4);
                                            precioFinal4 = sumaCantidadCliente4 * sumaPrecioCliente4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(sumaPrecioCliente4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;
                                        }
                                        if(flag4 == true){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;     
                                        }
                                        IDCliente4 = datosConsultados4.get(1).get(j);
                                        nombre4 = datosConsultados4.get(0).get(j);
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);

                                        k4++;
                                        flag4 = true;
                                        
                                    }
                                    else{
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);
                                        k4++;
                                        if(j == datosConsultados4.get(0).size()-1){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;
                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana4 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre4 = new Paragraph(" ");
                                Paragraph finSemCantidad4 = new Paragraph(cantidadPorSemana4.toString());
                                Paragraph finSemPrecioFinal4 = new Paragraph(precioPorSemana4.toString());
                                precioFinalCliente2 += precioPorSemana4;
                                cantidadFinalCliente2 += cantidadPorSemana4;
                                finSemana4.getFont().setStyle(Font.BOLD);
                                finSemana4.getFont().setSize(3);
                                finSemanaPre4.getFont().setStyle(Font.BOLD);
                                finSemanaPre4.getFont().setSize(3);
                                finSemCantidad4.getFont().setStyle(Font.BOLD);
                                finSemCantidad4.getFont().setSize(3);
                                finSemPrecioFinal4.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal4.getFont().setSize(3);
                                tabla.addCell(finSemana4);
                                tabla.addCell(finSemanaPre4);
                                tabla.addCell(finSemCantidad4);
                                tabla.addCell(finSemPrecioFinal4);
                                cantidadPorSemana4 = 0f;
                                precioPorSemana4 = 0f;
                            break;
                            case 8:
                                Paragraph semanas5 = new Paragraph(arregloSemanas[i]);
                                semanas5.getFont().setStyle(Font.BOLD);
                                semanas5.getFont().setSize(3);
                                PdfPCell celdaAgrup5 = new PdfPCell(semanas5);
                                celdaAgrup5.setColspan(5);
                                tabla.addCell(celdaAgrup5);
                                String query5 = arregloSemanas[i+1];
                                String[] Columnas5 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados5 = Globales.bdTemp.select(query5, Columnas5);
                                String IDCliente5 = "-1";
                                String nombre5 = "";
                                String cantidad5 = "";
                                String precio5 = "";
                                Float sumaCantidadCliente5 = 0.0f;
                                Float sumaPrecioCliente5 = 0.0f;
                                Float promedioPrecio5 = 0.0f;
                                Float k5 = 0f;
                                Float precioFinal5 = 0f;
                                Float cantidadPorSemana5 = 0f;
                                Float precioPorSemana5 = 0f;
                                boolean flag5 = false;
                                for(int j=0; j<datosConsultados5.get(0).size(); j++){
                                    if(!IDCliente5.equals(datosConsultados5.get(1).get(j))){
                                        if(datosConsultados5.get(0).size() == 1){
                                            IDCliente5 = datosConsultados5.get(1).get(j);
                                            nombre5 = datosConsultados5.get(0).get(j);
                                            cantidad5 = datosConsultados5.get(4).get(j);
                                            precio5 = datosConsultados5.get(3).get(j);
                                            sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                            sumaPrecioCliente5 += Float.parseFloat(precio5);
                                            precioFinal5 = sumaCantidadCliente5 * sumaPrecioCliente5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;

                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(sumaPrecioCliente5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;
                                        }
                                        if(flag5 == true){
                                            promedioPrecio5 = sumaPrecioCliente5 / k5;
                                            precioFinal5 = sumaCantidadCliente5 * promedioPrecio5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;

                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(promedioPrecio5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;     
                                        }
                                        IDCliente5 = datosConsultados5.get(1).get(j);
                                        nombre5 = datosConsultados5.get(0).get(j);
                                        cantidad5 = datosConsultados5.get(4).get(j);
                                        precio5 = datosConsultados5.get(3).get(j);
                                        sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                        sumaPrecioCliente5 += Float.parseFloat(precio5);

                                        k5++;
                                        flag5 = true;
                                        
                                    }
                                    else{
                                        cantidad5 = datosConsultados5.get(4).get(j);
                                        precio5 = datosConsultados5.get(3).get(j);
                                        sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                        sumaPrecioCliente5 += Float.parseFloat(precio5);
                                        k5++;
                                        if(j == datosConsultados5.get(0).size()-1){
                                            promedioPrecio5 = sumaPrecioCliente5 / k5;
                                            precioFinal5 = sumaCantidadCliente5 * promedioPrecio5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;
                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(promedioPrecio5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana5 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre5 = new Paragraph(" ");
                                Paragraph finSemCantidad5 = new Paragraph(cantidadPorSemana5.toString());
                                Paragraph finSemPrecioFinal5 = new Paragraph(precioPorSemana5.toString());
                                precioFinalCliente2 += precioPorSemana5;
                                cantidadFinalCliente2 += cantidadPorSemana5;
                                finSemana5.getFont().setStyle(Font.BOLD);
                                finSemana5.getFont().setSize(3);
                                finSemanaPre5.getFont().setStyle(Font.BOLD);
                                finSemanaPre5.getFont().setSize(3);
                                finSemCantidad5.getFont().setStyle(Font.BOLD);
                                finSemCantidad5.getFont().setSize(3);
                                finSemPrecioFinal5.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal5.getFont().setSize(3);
                                tabla.addCell(finSemana5);
                                tabla.addCell(finSemanaPre5);
                                tabla.addCell(finSemCantidad5);
                                tabla.addCell(finSemPrecioFinal5);
                                cantidadPorSemana5 = 0f;
                                precioPorSemana5 = 0f;
                            break;
                        }
                    }
                    Paragraph totalSemanaui2 = new Paragraph("CANTIDAD TOTAL");
                    totalSemanaui2.getFont().setStyle(Font.BOLD);
                    totalSemanaui2.getFont().setSize(3);
                    Paragraph totalSemanaua2 = new Paragraph(cantidadFinalCliente2.toString());
                    totalSemanaua2.getFont().setStyle(Font.BOLD);
                    totalSemanaua2.getFont().setSize(3);
                    Paragraph totalSemanaue2 = new Paragraph("INGRESO TOTAL");
                    totalSemanaue2.getFont().setStyle(Font.BOLD);
                    totalSemanaue2.getFont().setSize(3);
                    Paragraph totalSemanauo2 = new Paragraph(precioFinalCliente2.toString());
                    totalSemanauo2.getFont().setStyle(Font.BOLD);
                    totalSemanauo2.getFont().setSize(3);
                    PdfPCell cellui2 = new PdfPCell();
                    PdfPCell cellua2 = new PdfPCell();
                    PdfPCell cellue2 = new PdfPCell();
                    PdfPCell celluo2 = new PdfPCell();
                    cellui2.addElement(totalSemanaui2);
                    cellui2.setBackgroundColor(BaseColor.YELLOW);
                    cellua2.addElement(totalSemanaua2);
                    cellue2.addElement(totalSemanaue2);
                    cellue2.setBackgroundColor(BaseColor.YELLOW);
                    celluo2.addElement(totalSemanauo2);
                    tabla.addCell(cellui2);
                    tabla.addCell(cellua2);
                    tabla.addCell(cellue2);
                    tabla.addCell(celluo2);
                    break;
                case 12:
                    Float precioFinalCliente3 = 0f;
                    Float cantidadFinalCliente3 = 0f;
                    for(int i=0; i<10; i++){
                        switch(i){
                            case 0:
                                Paragraph semanas = new Paragraph(arregloSemanas[i]);
                                semanas.getFont().setStyle(Font.BOLD);
                                semanas.getFont().setSize(3);
                                PdfPCell celdaAgrup = new PdfPCell(semanas);
                                celdaAgrup.setColspan(5);
                                tabla.addCell(celdaAgrup);
                                String query = arregloSemanas[i+1];
                                String[] Columnas = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados = Globales.bdTemp.select(query, Columnas);
                                String IDCliente = "-1";
                                String nombre = "";
                                String cantidad = "";
                                String precio = "";
                                Float sumaCantidadCliente = 0.0f;
                                Float sumaPrecioCliente = 0.0f;
                                Float promedioPrecio = 0.0f;
                                Float k = 0f;
                                Float precioFinal = 0f;
                                Float cantidadPorSemana = 0f;
                                Float precioPorSemana = 0f;
                                boolean flag = false;
                                for(int j=0; j<datosConsultados.get(0).size(); j++){
                                    if(!IDCliente.equals(datosConsultados.get(1).get(j))){
                                        if(datosConsultados.get(0).size() == 1){
                                            IDCliente = datosConsultados.get(1).get(j);
                                            nombre = datosConsultados.get(0).get(j);
                                            cantidad = datosConsultados.get(4).get(j);
                                            precio = datosConsultados.get(3).get(j);
                                            sumaCantidadCliente += Float.parseFloat(cantidad);
                                            sumaPrecioCliente += Float.parseFloat(precio);
                                            precioFinal = sumaCantidadCliente * sumaPrecioCliente;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(sumaPrecioCliente.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;
                                        }
                                        if(flag == true){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;

                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;     
                                        }
                                        IDCliente = datosConsultados.get(1).get(j);
                                        nombre = datosConsultados.get(0).get(j);
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);

                                        k++;
                                        flag = true;
                                        
                                    }
                                    else{
                                        cantidad = datosConsultados.get(4).get(j);
                                        precio = datosConsultados.get(3).get(j);
                                        sumaCantidadCliente += Float.parseFloat(cantidad);
                                        sumaPrecioCliente += Float.parseFloat(precio);
                                        k++;
                                        if(j == datosConsultados.get(0).size()-1){
                                            promedioPrecio = sumaPrecioCliente / k;
                                            precioFinal = sumaCantidadCliente * promedioPrecio;
                                            cantidadPorSemana += sumaCantidadCliente;
                                            precioPorSemana += precioFinal;
                                            Paragraph finDeCliente = new Paragraph(nombre);
                                            Paragraph finCantidad = new Paragraph(sumaCantidadCliente.toString());
                                            Paragraph finPrecio = new Paragraph(promedioPrecio.toString());
                                            Paragraph finPrecioFinal = new Paragraph(precioFinal.toString());
                                            finDeCliente.getFont().setStyle(Font.NORMAL);
                                            finDeCliente.getFont().setSize(3);
                                            finCantidad.getFont().setStyle(Font.NORMAL);
                                            finCantidad.getFont().setSize(3);
                                            finPrecio.getFont().setStyle(Font.NORMAL);
                                            finPrecio.getFont().setSize(3);
                                            finPrecioFinal.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal.getFont().setSize(3);
                                            tabla.addCell(finDeCliente);
                                            tabla.addCell(finPrecio);
                                            tabla.addCell(finCantidad);
                                            tabla.addCell(finPrecioFinal);
                                            flag = false;
                                            sumaCantidadCliente = 0f;
                                            sumaPrecioCliente = 0f;
                                            k = 0f;
                                            promedioPrecio = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre = new Paragraph(" ");
                                Paragraph finSemCantidad = new Paragraph(cantidadPorSemana.toString());
                                Paragraph finSemPrecioFinal = new Paragraph(precioPorSemana.toString());
                                precioFinalCliente3 += precioPorSemana;
                                cantidadFinalCliente3 += cantidadPorSemana;
                                finSemana.getFont().setStyle(Font.BOLD);
                                finSemana.getFont().setSize(3);
                                finSemanaPre.getFont().setStyle(Font.BOLD);
                                finSemanaPre.getFont().setSize(3);
                                finSemCantidad.getFont().setStyle(Font.BOLD);
                                finSemCantidad.getFont().setSize(3);
                                finSemPrecioFinal.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal.getFont().setSize(3);
                                tabla.addCell(finSemana);
                                tabla.addCell(finSemanaPre);
                                tabla.addCell(finSemCantidad);
                                tabla.addCell(finSemPrecioFinal);
                                cantidadPorSemana = 0f;
                                precioPorSemana = 0f;
                            break;
                            case 2:
                                Paragraph semanas2 = new Paragraph(arregloSemanas[i]);
                                semanas2.getFont().setStyle(Font.BOLD);
                                semanas2.getFont().setSize(3);
                                PdfPCell celdaAgrup2 = new PdfPCell(semanas2);
                                celdaAgrup2.setColspan(5);
                                tabla.addCell(celdaAgrup2);
                                String query2 = arregloSemanas[i+1];
                                String[] Columnas2 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados2 = Globales.bdTemp.select(query2, Columnas2);
                                String IDCliente2 = "-1";
                                String nombre2 = "";
                                String cantidad2 = "";
                                String precio2 = "";
                                Float sumaCantidadCliente2 = 0.0f;
                                Float sumaPrecioCliente2 = 0.0f;
                                Float promedioPrecio2 = 0.0f;
                                Float k2 = 0f;
                                Float precioFinal2 = 0f;
                                Float cantidadPorSemana2 = 0f;
                                Float precioPorSemana2 = 0f;
                                boolean flag2 = false;
                                for(int j=0; j<datosConsultados2.get(0).size(); j++){
                                    if(!IDCliente2.equals(datosConsultados2.get(1).get(j))){
                                        if(datosConsultados2.get(0).size() == 1){
                                            IDCliente2 = datosConsultados2.get(1).get(j);
                                            nombre2 = datosConsultados2.get(0).get(j);
                                            cantidad2 = datosConsultados2.get(4).get(j);
                                            precio2 = datosConsultados2.get(3).get(j);
                                            sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                            sumaPrecioCliente2 += Float.parseFloat(precio2);
                                            precioFinal2 = sumaCantidadCliente2 * sumaPrecioCliente2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(sumaPrecioCliente2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;
                                        }
                                        if(flag2 == true){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;

                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;     
                                        }
                                        IDCliente2 = datosConsultados2.get(1).get(j);
                                        nombre2 = datosConsultados2.get(0).get(j);
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);

                                        k2++;
                                        flag2 = true;
                                        
                                    }
                                    else{
                                        cantidad2 = datosConsultados2.get(4).get(j);
                                        precio2 = datosConsultados2.get(3).get(j);
                                        sumaCantidadCliente2 += Float.parseFloat(cantidad2);
                                        sumaPrecioCliente2 += Float.parseFloat(precio2);
                                        k2++;
                                        if(j == datosConsultados2.get(0).size()-1){
                                            promedioPrecio2 = sumaPrecioCliente2 / k2;
                                            precioFinal2 = sumaCantidadCliente2 * promedioPrecio2;
                                            cantidadPorSemana2 += sumaCantidadCliente2;
                                            precioPorSemana2 += precioFinal2;
                                            Paragraph finDeCliente2 = new Paragraph(nombre2);
                                            Paragraph finCantidad2 = new Paragraph(sumaCantidadCliente2.toString());
                                            Paragraph finPrecio2 = new Paragraph(promedioPrecio2.toString());
                                            Paragraph finPrecioFinal2 = new Paragraph(precioFinal2.toString());
                                            finDeCliente2.getFont().setStyle(Font.NORMAL);
                                            finDeCliente2.getFont().setSize(3);
                                            finCantidad2.getFont().setStyle(Font.NORMAL);
                                            finCantidad2.getFont().setSize(3);
                                            finPrecio2.getFont().setStyle(Font.NORMAL);
                                            finPrecio2.getFont().setSize(3);
                                            finPrecioFinal2.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal2.getFont().setSize(3);
                                            tabla.addCell(finDeCliente2);
                                            tabla.addCell(finPrecio2);
                                            tabla.addCell(finCantidad2);
                                            tabla.addCell(finPrecioFinal2);
                                            flag2 = false;
                                            sumaCantidadCliente2 = 0f;
                                            sumaPrecioCliente2 = 0f;
                                            k2 = 0f;
                                            promedioPrecio2 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana2 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre2 = new Paragraph(" ");
                                Paragraph finSemCantidad2 = new Paragraph(cantidadPorSemana2.toString());
                                Paragraph finSemPrecioFinal2 = new Paragraph(precioPorSemana2.toString());
                                precioFinalCliente3 += precioPorSemana2;
                                cantidadFinalCliente3 += cantidadPorSemana2;
                                finSemana2.getFont().setStyle(Font.BOLD);
                                finSemana2.getFont().setSize(3);
                                finSemanaPre2.getFont().setStyle(Font.BOLD);
                                finSemanaPre2.getFont().setSize(3);
                                finSemCantidad2.getFont().setStyle(Font.BOLD);
                                finSemCantidad2.getFont().setSize(3);
                                finSemPrecioFinal2.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal2.getFont().setSize(3);
                                tabla.addCell(finSemana2);
                                tabla.addCell(finSemanaPre2);
                                tabla.addCell(finSemCantidad2);
                                tabla.addCell(finSemPrecioFinal2);
                                cantidadPorSemana2 = 0f;
                                precioPorSemana2 = 0f;
                            break;
                            case 4:
                                Paragraph semanas3 = new Paragraph(arregloSemanas[i]);
                                semanas3.getFont().setStyle(Font.BOLD);
                                semanas3.getFont().setSize(3);
                                PdfPCell celdaAgrup3 = new PdfPCell(semanas3);
                                celdaAgrup3.setColspan(5);
                                tabla.addCell(celdaAgrup3);
                                String query3 = arregloSemanas[i+1];
                                String[] Columnas3 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados3 = Globales.bdTemp.select(query3, Columnas3);
                                String IDCliente3 = "-1";
                                String nombre3 = "";
                                String cantidad3 = "";
                                String precio3 = "";
                                Float sumaCantidadCliente3 = 0.0f;
                                Float sumaPrecioCliente3 = 0.0f;
                                Float promedioPrecio3 = 0.0f;
                                Float k3 = 0f;
                                Float precioFinal3 = 0f;
                                Float cantidadPorSemana3 = 0f;
                                Float precioPorSemana3 = 0f;
                                boolean flag3 = false;
                                for(int j=0; j<datosConsultados3.get(0).size(); j++){
                                    if(!IDCliente3.equals(datosConsultados3.get(1).get(j))){
                                        if(datosConsultados3.get(0).size() == 1){
                                            IDCliente3 = datosConsultados3.get(1).get(j);
                                            nombre3 = datosConsultados3.get(0).get(j);
                                            cantidad3 = datosConsultados3.get(4).get(j);
                                            precio3 = datosConsultados3.get(3).get(j);
                                            sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                            sumaPrecioCliente3 += Float.parseFloat(precio3);
                                            precioFinal3 = sumaCantidadCliente3 * sumaPrecioCliente3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(sumaPrecioCliente3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;
                                        }
                                        if(flag3 == true){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;

                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;     
                                        }
                                        IDCliente3 = datosConsultados3.get(1).get(j);
                                        nombre3 = datosConsultados3.get(0).get(j);
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);

                                        k3++;
                                        flag3 = true;
                                        
                                    }
                                    else{
                                        cantidad3 = datosConsultados3.get(4).get(j);
                                        precio3 = datosConsultados3.get(3).get(j);
                                        sumaCantidadCliente3 += Float.parseFloat(cantidad3);
                                        sumaPrecioCliente3 += Float.parseFloat(precio3);
                                        k3++;
                                        if(j == datosConsultados3.get(0).size()-1){
                                            promedioPrecio3 = sumaPrecioCliente3 / k3;
                                            precioFinal3 = sumaCantidadCliente3 * promedioPrecio3;
                                            cantidadPorSemana3 += sumaCantidadCliente3;
                                            precioPorSemana3 += precioFinal3;
                                            Paragraph finDeCliente3 = new Paragraph(nombre3);
                                            Paragraph finCantidad3 = new Paragraph(sumaCantidadCliente3.toString());
                                            Paragraph finPrecio3 = new Paragraph(promedioPrecio3.toString());
                                            Paragraph finPrecioFinal3 = new Paragraph(precioFinal3.toString());
                                            finDeCliente3.getFont().setStyle(Font.NORMAL);
                                            finDeCliente3.getFont().setSize(3);
                                            finCantidad3.getFont().setStyle(Font.NORMAL);
                                            finCantidad3.getFont().setSize(3);
                                            finPrecio3.getFont().setStyle(Font.NORMAL);
                                            finPrecio3.getFont().setSize(3);
                                            finPrecioFinal3.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal3.getFont().setSize(3);
                                            tabla.addCell(finDeCliente3);
                                            tabla.addCell(finPrecio3);
                                            tabla.addCell(finCantidad3);
                                            tabla.addCell(finPrecioFinal3);
                                            flag3 = false;
                                            sumaCantidadCliente3 = 0f;
                                            sumaPrecioCliente3 = 0f;
                                            k3 = 0f;
                                            promedioPrecio3 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana3 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre3 = new Paragraph(" ");
                                Paragraph finSemCantidad3 = new Paragraph(cantidadPorSemana3.toString());
                                Paragraph finSemPrecioFinal3 = new Paragraph(precioPorSemana3.toString());
                                precioFinalCliente3 += precioPorSemana3;
                                cantidadFinalCliente3 += cantidadPorSemana3;
                                finSemana3.getFont().setStyle(Font.BOLD);
                                finSemana3.getFont().setSize(3);
                                finSemanaPre3.getFont().setStyle(Font.BOLD);
                                finSemanaPre3.getFont().setSize(3);
                                finSemCantidad3.getFont().setStyle(Font.BOLD);
                                finSemCantidad3.getFont().setSize(3);
                                finSemPrecioFinal3.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal3.getFont().setSize(3);
                                tabla.addCell(finSemana3);
                                tabla.addCell(finSemanaPre3);
                                tabla.addCell(finSemCantidad3);
                                tabla.addCell(finSemPrecioFinal3);
                                cantidadPorSemana3 = 0f;
                                precioPorSemana3 = 0f;
                            break;
                            case 6:
                                Paragraph semanas4 = new Paragraph(arregloSemanas[i]);
                                semanas4.getFont().setStyle(Font.BOLD);
                                semanas4.getFont().setSize(3);
                                PdfPCell celdaAgrup4 = new PdfPCell(semanas4);
                                celdaAgrup4.setColspan(5);
                                tabla.addCell(celdaAgrup4);
                                String query4 = arregloSemanas[i+1];
                                String[] Columnas4 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados4 = Globales.bdTemp.select(query4, Columnas4);
                                String IDCliente4 = "-1";
                                String nombre4 = "";
                                String cantidad4 = "";
                                String precio4 = "";
                                Float sumaCantidadCliente4 = 0.0f;
                                Float sumaPrecioCliente4 = 0.0f;
                                Float promedioPrecio4 = 0.0f;
                                Float k4 = 0f;
                                Float precioFinal4 = 0f;
                                Float cantidadPorSemana4 = 0f;
                                Float precioPorSemana4 = 0f;
                                boolean flag4 = false;
                                for(int j=0; j<datosConsultados4.get(0).size(); j++){
                                    if(!IDCliente4.equals(datosConsultados4.get(1).get(j))){
                                        if(datosConsultados4.get(0).size() == 1){
                                            IDCliente4 = datosConsultados4.get(1).get(j);
                                            nombre4 = datosConsultados4.get(0).get(j);
                                            cantidad4 = datosConsultados4.get(4).get(j);
                                            precio4 = datosConsultados4.get(3).get(j);
                                            sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                            sumaPrecioCliente4 += Float.parseFloat(precio4);
                                            precioFinal4 = sumaCantidadCliente4 * sumaPrecioCliente4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(sumaPrecioCliente4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;
                                        }
                                        if(flag4 == true){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;

                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;     
                                        }
                                        IDCliente4 = datosConsultados4.get(1).get(j);
                                        nombre4 = datosConsultados4.get(0).get(j);
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);

                                        k4++;
                                        flag4 = true;
                                        
                                    }
                                    else{
                                        cantidad4 = datosConsultados4.get(4).get(j);
                                        precio4 = datosConsultados4.get(3).get(j);
                                        sumaCantidadCliente4 += Float.parseFloat(cantidad4);
                                        sumaPrecioCliente4 += Float.parseFloat(precio4);
                                        k4++;
                                        if(j == datosConsultados4.get(0).size()-1){
                                            promedioPrecio4 = sumaPrecioCliente4 / k4;
                                            precioFinal4 = sumaCantidadCliente4 * promedioPrecio4;
                                            cantidadPorSemana4 += sumaCantidadCliente4;
                                            precioPorSemana4 += precioFinal4;
                                            Paragraph finDeCliente4 = new Paragraph(nombre4);
                                            Paragraph finCantidad4 = new Paragraph(sumaCantidadCliente4.toString());
                                            Paragraph finPrecio4 = new Paragraph(promedioPrecio4.toString());
                                            Paragraph finPrecioFinal4 = new Paragraph(precioFinal4.toString());
                                            finDeCliente4.getFont().setStyle(Font.NORMAL);
                                            finDeCliente4.getFont().setSize(3);
                                            finCantidad4.getFont().setStyle(Font.NORMAL);
                                            finCantidad4.getFont().setSize(3);
                                            finPrecio4.getFont().setStyle(Font.NORMAL);
                                            finPrecio4.getFont().setSize(3);
                                            finPrecioFinal4.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal4.getFont().setSize(3);
                                            tabla.addCell(finDeCliente4);
                                            tabla.addCell(finPrecio4);
                                            tabla.addCell(finCantidad4);
                                            tabla.addCell(finPrecioFinal4);
                                            flag4 = false;
                                            sumaCantidadCliente4 = 0f;
                                            sumaPrecioCliente4 = 0f;
                                            k4 = 0f;
                                            promedioPrecio4 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana4 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre4 = new Paragraph(" ");
                                Paragraph finSemCantidad4 = new Paragraph(cantidadPorSemana4.toString());
                                Paragraph finSemPrecioFinal4 = new Paragraph(precioPorSemana4.toString());
                                precioFinalCliente3 += precioPorSemana4;
                                cantidadFinalCliente3 += cantidadPorSemana4;
                                finSemana4.getFont().setStyle(Font.BOLD);
                                finSemana4.getFont().setSize(3);
                                finSemanaPre4.getFont().setStyle(Font.BOLD);
                                finSemanaPre4.getFont().setSize(3);
                                finSemCantidad4.getFont().setStyle(Font.BOLD);
                                finSemCantidad4.getFont().setSize(3);
                                finSemPrecioFinal4.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal4.getFont().setSize(3);
                                tabla.addCell(finSemana4);
                                tabla.addCell(finSemanaPre4);
                                tabla.addCell(finSemCantidad4);
                                tabla.addCell(finSemPrecioFinal4);
                                cantidadPorSemana4 = 0f;
                                precioPorSemana4 = 0f;
                            break;
                            case 8:
                                Paragraph semanas5 = new Paragraph(arregloSemanas[i]);
                                semanas5.getFont().setStyle(Font.BOLD);
                                semanas5.getFont().setSize(3);
                                PdfPCell celdaAgrup5 = new PdfPCell(semanas5);
                                celdaAgrup5.setColspan(5);
                                tabla.addCell(celdaAgrup5);
                                String query5 = arregloSemanas[i+1];
                                String[] Columnas5 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados5 = Globales.bdTemp.select(query5, Columnas5);
                                String IDCliente5 = "-1";
                                String nombre5 = "";
                                String cantidad5 = "";
                                String precio5 = "";
                                Float sumaCantidadCliente5 = 0.0f;
                                Float sumaPrecioCliente5 = 0.0f;
                                Float promedioPrecio5 = 0.0f;
                                Float k5 = 0f;
                                Float precioFinal5 = 0f;
                                Float cantidadPorSemana5 = 0f;
                                Float precioPorSemana5 = 0f;
                                boolean flag5 = false;
                                for(int j=0; j<datosConsultados5.get(0).size(); j++){
                                    if(!IDCliente5.equals(datosConsultados5.get(1).get(j))){
                                        if(datosConsultados5.get(0).size() == 1){
                                            IDCliente5 = datosConsultados5.get(1).get(j);
                                            nombre5 = datosConsultados5.get(0).get(j);
                                            cantidad5 = datosConsultados5.get(4).get(j);
                                            precio5 = datosConsultados5.get(3).get(j);
                                            sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                            sumaPrecioCliente5 += Float.parseFloat(precio5);
                                            precioFinal5 = sumaCantidadCliente5 * sumaPrecioCliente5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;

                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(sumaPrecioCliente5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;
                                        }
                                        if(flag5 == true){
                                            promedioPrecio5 = sumaPrecioCliente5 / k5;
                                            precioFinal5 = sumaCantidadCliente5 * promedioPrecio5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;

                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(promedioPrecio5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;     
                                        }
                                        IDCliente5 = datosConsultados5.get(1).get(j);
                                        nombre5 = datosConsultados5.get(0).get(j);
                                        cantidad5 = datosConsultados5.get(4).get(j);
                                        precio5 = datosConsultados5.get(3).get(j);
                                        sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                        sumaPrecioCliente5 += Float.parseFloat(precio5);

                                        k5++;
                                        flag5 = true;
                                        
                                    }
                                    else{
                                        cantidad5 = datosConsultados5.get(4).get(j);
                                        precio5 = datosConsultados5.get(3).get(j);
                                        sumaCantidadCliente5 += Float.parseFloat(cantidad5);
                                        sumaPrecioCliente5 += Float.parseFloat(precio5);
                                        k5++;
                                        if(j == datosConsultados5.get(0).size()-1){
                                            promedioPrecio5 = sumaPrecioCliente5 / k5;
                                            precioFinal5 = sumaCantidadCliente5 * promedioPrecio5;
                                            cantidadPorSemana5 += sumaCantidadCliente5;
                                            precioPorSemana5 += precioFinal5;
                                            Paragraph finDeCliente5 = new Paragraph(nombre5);
                                            Paragraph finCantidad5 = new Paragraph(sumaCantidadCliente5.toString());
                                            Paragraph finPrecio5 = new Paragraph(promedioPrecio5.toString());
                                            Paragraph finPrecioFinal5 = new Paragraph(precioFinal5.toString());
                                            finDeCliente5.getFont().setStyle(Font.NORMAL);
                                            finDeCliente5.getFont().setSize(3);
                                            finCantidad5.getFont().setStyle(Font.NORMAL);
                                            finCantidad5.getFont().setSize(3);
                                            finPrecio5.getFont().setStyle(Font.NORMAL);
                                            finPrecio5.getFont().setSize(3);
                                            finPrecioFinal5.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal5.getFont().setSize(3);
                                            tabla.addCell(finDeCliente5);
                                            tabla.addCell(finPrecio5);
                                            tabla.addCell(finCantidad5);
                                            tabla.addCell(finPrecioFinal5);
                                            flag5 = false;
                                            sumaCantidadCliente5 = 0f;
                                            sumaPrecioCliente5 = 0f;
                                            k5 = 0f;
                                            promedioPrecio5 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana5 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre5 = new Paragraph(" ");
                                Paragraph finSemCantidad5 = new Paragraph(cantidadPorSemana5.toString());
                                Paragraph finSemPrecioFinal5 = new Paragraph(precioPorSemana5.toString());
                                precioFinalCliente3 += precioPorSemana5;
                                cantidadFinalCliente3 += cantidadPorSemana5;
                                finSemana5.getFont().setStyle(Font.BOLD);
                                finSemana5.getFont().setSize(3);
                                finSemanaPre5.getFont().setStyle(Font.BOLD);
                                finSemanaPre5.getFont().setSize(3);
                                finSemCantidad5.getFont().setStyle(Font.BOLD);
                                finSemCantidad5.getFont().setSize(3);
                                finSemPrecioFinal5.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal5.getFont().setSize(3);
                                tabla.addCell(finSemana5);
                                tabla.addCell(finSemanaPre5);
                                tabla.addCell(finSemCantidad5);
                                tabla.addCell(finSemPrecioFinal5);
                                cantidadPorSemana5 = 0f;
                                precioPorSemana5 = 0f;
                            break;
                            case 10:
                                Paragraph semanas6 = new Paragraph(arregloSemanas[i]);
                                semanas6.getFont().setStyle(Font.BOLD);
                                semanas6.getFont().setSize(3);
                                PdfPCell celdaAgrup6 = new PdfPCell(semanas6);
                                celdaAgrup6.setColspan(5);
                                tabla.addCell(celdaAgrup6);
                                String query6 = arregloSemanas[i+1];
                                String[] Columnas6 = {"NOMBRECHOFER", "CLIENTEID", "FECHAVENTA", "PRECIOM3", "CANTIDADM3"};
                                LinkedList<LinkedList<String>> datosConsultados6 = Globales.bdTemp.select(query6, Columnas6);
                                String IDCliente6 = "-1";
                                String nombre6 = "";
                                String cantidad6 = "";
                                String precio6 = "";
                                Float sumaCantidadCliente6 = 0.0f;
                                Float sumaPrecioCliente6 = 0.0f;
                                Float promedioPrecio6 = 0.0f;
                                Float k6 = 0f;
                                Float precioFinal6 = 0f;
                                Float cantidadPorSemana6 = 0f;
                                Float precioPorSemana6 = 0f;
                                boolean flag6 = false;
                                for(int j=0; j<datosConsultados6.get(0).size(); j++){
                                    if(!IDCliente6.equals(datosConsultados6.get(1).get(j))){
                                        if(datosConsultados6.get(0).size() == 1){
                                            IDCliente6 = datosConsultados6.get(1).get(j);
                                            nombre6 = datosConsultados6.get(0).get(j);
                                            cantidad6 = datosConsultados6.get(4).get(j);
                                            precio6 = datosConsultados6.get(3).get(j);
                                            sumaCantidadCliente6 += Float.parseFloat(cantidad6);
                                            sumaPrecioCliente6 += Float.parseFloat(precio6);
                                            precioFinal6 = sumaCantidadCliente6 * sumaPrecioCliente6;
                                            cantidadPorSemana6 += sumaCantidadCliente6;
                                            precioPorSemana6 += precioFinal6;

                                            Paragraph finDeCliente6 = new Paragraph(nombre6);
                                            Paragraph finCantidad6 = new Paragraph(sumaCantidadCliente6.toString());
                                            Paragraph finPrecio6 = new Paragraph(sumaPrecioCliente6.toString());
                                            Paragraph finPrecioFinal6 = new Paragraph(precioFinal6.toString());
                                            finDeCliente6.getFont().setStyle(Font.NORMAL);
                                            finDeCliente6.getFont().setSize(3);
                                            finCantidad6.getFont().setStyle(Font.NORMAL);
                                            finCantidad6.getFont().setSize(3);
                                            finPrecio6.getFont().setStyle(Font.NORMAL);
                                            finPrecio6.getFont().setSize(3);
                                            finPrecioFinal6.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal6.getFont().setSize(3);
                                            tabla.addCell(finDeCliente6);
                                            tabla.addCell(finPrecio6);
                                            tabla.addCell(finCantidad6);
                                            tabla.addCell(finPrecioFinal6);
                                            flag6 = false;
                                            sumaCantidadCliente6 = 0f;
                                            sumaPrecioCliente6 = 0f;
                                            k2 = 0f;
                                            promedioPrecio6 = 0f;
                                        }
                                        if(flag6 == true){
                                            promedioPrecio6 = sumaPrecioCliente6 / k6;
                                            precioFinal6 = sumaCantidadCliente6 * promedioPrecio6;
                                            cantidadPorSemana6 += sumaCantidadCliente6;
                                            precioPorSemana6 += precioFinal6;

                                            Paragraph finDeCliente6 = new Paragraph(nombre6);
                                            Paragraph finCantidad6 = new Paragraph(sumaCantidadCliente6.toString());
                                            Paragraph finPrecio6 = new Paragraph(promedioPrecio6.toString());
                                            Paragraph finPrecioFinal6 = new Paragraph(precioFinal6.toString());
                                            finDeCliente6.getFont().setStyle(Font.NORMAL);
                                            finDeCliente6.getFont().setSize(3);
                                            finCantidad6.getFont().setStyle(Font.NORMAL);
                                            finCantidad6.getFont().setSize(3);
                                            finPrecio6.getFont().setStyle(Font.NORMAL);
                                            finPrecio6.getFont().setSize(3);
                                            finPrecioFinal6.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal6.getFont().setSize(3);
                                            tabla.addCell(finDeCliente6);
                                            tabla.addCell(finPrecio6);
                                            tabla.addCell(finCantidad6);
                                            tabla.addCell(finPrecioFinal6);
                                            flag6 = false;
                                            sumaCantidadCliente6 = 0f;
                                            sumaPrecioCliente6 = 0f;
                                            k6 = 0f;
                                            promedioPrecio6 = 0f;     
                                        }
                                        IDCliente6 = datosConsultados6.get(1).get(j);
                                        nombre6 = datosConsultados6.get(0).get(j);
                                        cantidad6 = datosConsultados6.get(4).get(j);
                                        precio6 = datosConsultados6.get(3).get(j);
                                        sumaCantidadCliente6 += Float.parseFloat(cantidad6);
                                        sumaPrecioCliente6 += Float.parseFloat(precio6);

                                        k6++;
                                        flag6 = true;
                                        
                                    }
                                    else{
                                        cantidad6 = datosConsultados6.get(4).get(j);
                                        precio6 = datosConsultados6.get(3).get(j);
                                        sumaCantidadCliente6 += Float.parseFloat(cantidad6);
                                        sumaPrecioCliente6 += Float.parseFloat(precio6);
                                        k6++;
                                        if(j == datosConsultados6.get(0).size()-1){
                                            promedioPrecio6 = sumaPrecioCliente6 / k6;
                                            precioFinal6 = sumaCantidadCliente6 * promedioPrecio6;
                                            cantidadPorSemana6 += sumaCantidadCliente6;
                                            precioPorSemana6 += precioFinal6;
                                            Paragraph finDeCliente6 = new Paragraph(nombre6);
                                            Paragraph finCantidad6 = new Paragraph(sumaCantidadCliente6.toString());
                                            Paragraph finPrecio6 = new Paragraph(promedioPrecio6.toString());
                                            Paragraph finPrecioFinal6 = new Paragraph(precioFinal6.toString());
                                            finDeCliente6.getFont().setStyle(Font.NORMAL);
                                            finDeCliente6.getFont().setSize(3);
                                            finCantidad6.getFont().setStyle(Font.NORMAL);
                                            finCantidad6.getFont().setSize(3);
                                            finPrecio6.getFont().setStyle(Font.NORMAL);
                                            finPrecio6.getFont().setSize(3);
                                            finPrecioFinal6.getFont().setStyle(Font.NORMAL);
                                            finPrecioFinal6.getFont().setSize(3);
                                            tabla.addCell(finDeCliente6);
                                            tabla.addCell(finPrecio6);
                                            tabla.addCell(finCantidad6);
                                            tabla.addCell(finPrecioFinal6);
                                            flag6 = false;
                                            sumaCantidadCliente6 = 0f;
                                            sumaPrecioCliente6 = 0f;
                                            k6 = 0f;
                                            promedioPrecio6 = 0f;  
                                        }
                                    }
                                }
                                Paragraph finSemana6 = new Paragraph("TOTAL POR SEMANA");
                                Paragraph finSemanaPre6 = new Paragraph(" ");
                                Paragraph finSemCantidad6 = new Paragraph(cantidadPorSemana6.toString());
                                Paragraph finSemPrecioFinal6 = new Paragraph(precioPorSemana6.toString());
                                precioFinalCliente3 += precioPorSemana6;
                                cantidadFinalCliente3 += cantidadPorSemana6;
                                finSemana6.getFont().setStyle(Font.BOLD);
                                finSemana6.getFont().setSize(3);
                                finSemanaPre6.getFont().setStyle(Font.BOLD);
                                finSemanaPre6.getFont().setSize(3);
                                finSemCantidad6.getFont().setStyle(Font.BOLD);
                                finSemCantidad6.getFont().setSize(3);
                                finSemPrecioFinal6.getFont().setStyle(Font.BOLD);
                                finSemPrecioFinal6.getFont().setSize(3);
                                tabla.addCell(finSemana6);
                                tabla.addCell(finSemanaPre6);
                                tabla.addCell(finSemCantidad6);
                                tabla.addCell(finSemPrecioFinal6);
                                cantidadPorSemana6 = 0f;
                                precioPorSemana6 = 0f;
                            break;
                        }
                    }
                    Paragraph totalSemanaui3 = new Paragraph("CANTIDAD TOTAL");
                    totalSemanaui3.getFont().setStyle(Font.BOLD);
                    totalSemanaui3.getFont().setSize(3);
                    Paragraph totalSemanaua3 = new Paragraph(cantidadFinalCliente3.toString());
                    totalSemanaua3.getFont().setStyle(Font.BOLD);
                    totalSemanaua3.getFont().setSize(3);
                    Paragraph totalSemanaue3 = new Paragraph("INGRESO TOTAL");
                    totalSemanaue3.getFont().setStyle(Font.BOLD);
                    totalSemanaue3.getFont().setSize(3);
                    Paragraph totalSemanauo3 = new Paragraph(precioFinalCliente3.toString());
                    totalSemanauo3.getFont().setStyle(Font.BOLD);
                    totalSemanauo3.getFont().setSize(3);
                    PdfPCell cellui3 = new PdfPCell();
                    PdfPCell cellua3 = new PdfPCell();
                    PdfPCell cellue3 = new PdfPCell();
                    PdfPCell celluo3 = new PdfPCell();
                    cellui3.addElement(totalSemanaui3);
                    cellui3.setBackgroundColor(BaseColor.YELLOW);
                    cellua3.addElement(totalSemanaua3);
                    cellue3.addElement(totalSemanaue3);
                    cellue3.setBackgroundColor(BaseColor.YELLOW);
                    celluo3.addElement(totalSemanauo3);
                    tabla.addCell(cellui3);
                    tabla.addCell(cellua3);
                    tabla.addCell(cellue3);
                    tabla.addCell(celluo3);
                    break;
            }
            document.add(imagen);
            document.add(titulo);
            document.add(saltoLinea);
            document.add(tabla);
            document.close();  
        }catch(DocumentException | IOException | NumberFormatException e){
            System.err.println("Ocurrio un error al crear el archivo: " + e );
            System.exit(-1);
        }
    }
    
    public void generarPDFProduccion(String mes, String anio, String arregloSemanas[], String imgUrl, String pathPDF){
        try{            
            Document document = new Document(PageSize.A7, 36, 36, 10, 10);
            PdfWriter.getInstance(document, new FileOutputStream(pathPDF));
            document.open();
            
            Image imagen = Image.getInstance(imgUrl);
            imagen.scaleAbsolute(15, 15);
            imagen.setAlignment(Element.ALIGN_TOP);
            
            Paragraph titulo = new Paragraph("Reporte de Producción y Ventas " + " (" + mes + ", " + anio + ")");
            titulo.getFont().setColor(BaseColor.RED);
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(5);
            titulo.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph saltoLinea = new Paragraph("\n");
            
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{15,15,15,15,15,15,10});
            
            for(int i=0; i<arregloSemanas.length; i++){
                String semanas = arregloSemanas[0];
                String queryCamion = arregloSemanas[1];
                String queryCamion2 = arregloSemanas[2];
                String queryVenta1 = arregloSemanas[3];
                String queryVenta2 = arregloSemanas[4];
                String queryVenta3 = arregloSemanas[5];
                String queryInventario = arregloSemanas[6];
                String[] ColumnasCamion = {"FECHA", "TOTAL"};
                String[] ColumnasVenta = {"FECHAVENTA", "SUMA"};
                String[] ColumnasInventario = {"FECHAINVENTARIO", "INVENTARIOPIEDRAGRENA", "INVENTARIOPIEDRAPRODUCIDA"};
                LinkedList<LinkedList<String>> datosConsultadosCamion = Globales.bdTemp.select(queryCamion, ColumnasCamion);
                LinkedList<LinkedList<String>> datosConsultadosCamion2 = Globales.bdTemp.select(queryCamion2, ColumnasCamion);
                LinkedList<LinkedList<String>> datosConsultadosVenta1 = Globales.bdTemp.select(queryVenta1, ColumnasVenta);
                LinkedList<LinkedList<String>> datosConsultadosVenta2 = Globales.bdTemp.select(queryVenta2, ColumnasVenta);
                LinkedList<LinkedList<String>> datosConsultadosVenta3 = Globales.bdTemp.select(queryVenta3, ColumnasVenta);
                LinkedList<LinkedList<String>> datosConsultadosInventario = Globales.bdTemp.select(queryInventario, ColumnasInventario);
                Paragraph semanaDelAl = new Paragraph(semanas);
                semanaDelAl.getFont().setStyle(Font.NORMAL);
                semanaDelAl.getFont().setSize(3);
                PdfPCell cellSemanaDel = new PdfPCell(semanaDelAl);
                cellSemanaDel.setColspan(3);
                tabla.addCell(cellSemanaDel);
                String fecha1;
                String fecha2;
                String fecha3;
                String fecha4;
                String fecha5;
                String fecha6;
                int flag1 = 0;
                int flag2 = 0;
                int flag3 = 0;
                int flag4 = 0;
                int flag5 = 0;
                int flag6 = 0;
                Float viajesTotales;
                Float viajesChicos;
                Float viajesTorton;
                Float m3;
                Float screen;
                Float grava;
                Float suma;
                Float screenDato;
                Float gravaMediaDato;
                Float gravaTresDato;
                Float enGrenaDato;
                Float producidadDato;
                Float rendimiento;
                
                for(int j=0; j<datosConsultadosCamion.get(0).size(); j++){
                    fecha1 = datosConsultadosCamion.get(0).get(0);
                    fecha2 = datosConsultadosCamion2.get(0).get(0);
                    fecha3 = datosConsultadosVenta1.get(0).get(0);
                    fecha4 = datosConsultadosVenta2.get(0).get(0);
                    fecha5 = datosConsultadosVenta3.get(0).get(0);
                    fecha6 = datosConsultadosInventario.get(0).get(0);
                    if(fecha1.equals(fecha2)){
                        flag1 = 1;
                    }
                    if(fecha1.equals(fecha3)){
                        flag2 = 1;
                    }
                    if(fecha1.equals(fecha4)){
                        flag3 = 1;
                    }
                    if(fecha1.equals(fecha5)){
                        flag4 = 1;
                    }
                    if(fecha1.equals(fecha6)){
                        flag5 = 1;
                    }
                    
                    //if(flag1 == flag2 && flag1 == flag3 && flag1 == flag4 && flag1 == flag5){
                        viajesChicos = Float.parseFloat(datosConsultadosCamion.get(1).get(j));
                        viajesTorton = Float.parseFloat(datosConsultadosCamion2.get(1).get(j));
                        viajesTotales = viajesChicos + 2 * viajesTorton;
                        m3 = viajesTotales * 6;
                        screen = m3 * 0.25f;
                        grava = m3 * 0.75f;
                        PdfPCell prodTeoricaCellVacia = new PdfPCell();
                        prodTeoricaCellVacia.setColspan(4);
                        tabla.addCell(prodTeoricaCellVacia);
                        Paragraph prodTeorica  = new Paragraph(" PRODUCCION TEORICA");
                        prodTeorica.getFont().setStyle(Font.BOLD);
                        prodTeorica.getFont().setSize(3);
                        PdfPCell prodTeoricaCell = new PdfPCell(prodTeorica);
                        prodTeoricaCell.setColspan(3);
                        tabla.addCell(prodTeoricaCell);

                        Paragraph fechaLbl = new Paragraph("FECHA");
                        fechaLbl.getFont().setStyle(Font.BOLD);
                        fechaLbl.getFont().setSize(3);
                        tabla.addCell(fechaLbl);

                        Paragraph chicosLbl = new Paragraph("CHICOS");
                        chicosLbl.getFont().setStyle(Font.BOLD);
                        chicosLbl.getFont().setSize(3);
                        tabla.addCell(chicosLbl);

                        Paragraph tortonLbl = new Paragraph("TORTON");
                        tortonLbl.getFont().setStyle(Font.BOLD);
                        tortonLbl.getFont().setSize(3);
                        tabla.addCell(tortonLbl);

                        Paragraph totalLbl = new Paragraph("TOTAL VIAJES REALES");
                        totalLbl.getFont().setStyle(Font.BOLD);
                        totalLbl.getFont().setSize(3);
                        tabla.addCell(totalLbl);

                        Paragraph m3Lbl = new Paragraph("M3");
                        m3Lbl.getFont().setStyle(Font.BOLD);
                        m3Lbl.getFont().setSize(3);
                        tabla.addCell(m3Lbl);

                        Paragraph arenillalLbl = new Paragraph("SCREEN");
                        arenillalLbl.getFont().setStyle(Font.BOLD);
                        arenillalLbl.getFont().setSize(3);
                        tabla.addCell(arenillalLbl);

                        Paragraph gravaLbl = new Paragraph("GRAVA");
                        gravaLbl.getFont().setStyle(Font.BOLD);
                        gravaLbl.getFont().setSize(3);
                        tabla.addCell(gravaLbl);
                        
                        Paragraph fechaa = new Paragraph(datosConsultadosCamion.get(0).get(j));
                        fechaa.getFont().setStyle(Font.NORMAL);
                        fechaa.getFont().setSize(3);
                        Paragraph camionesChicos = new Paragraph(datosConsultadosCamion.get(1).get(j));
                        camionesChicos.getFont().setStyle(Font.NORMAL);
                        camionesChicos.getFont().setSize(3);
                        Paragraph camionesTorton = new Paragraph(datosConsultadosCamion2.get(1).get(j));
                        camionesTorton.getFont().setStyle(Font.NORMAL);
                        camionesTorton.getFont().setSize(3);
                        Paragraph viajesTotalesLbl = new Paragraph(viajesTotales.toString());
                        viajesTotalesLbl.getFont().setStyle(Font.NORMAL);
                        viajesTotalesLbl.getFont().setSize(3);
                        Paragraph m3sLbl = new Paragraph(m3.toString());
                        m3sLbl.getFont().setStyle(Font.NORMAL);
                        m3sLbl.getFont().setSize(3);
                        Paragraph screenLbl = new Paragraph(screen.toString());
                        screenLbl.getFont().setStyle(Font.NORMAL);
                        screenLbl.getFont().setSize(3);
                        Paragraph gravaLbls = new Paragraph(grava.toString());
                        gravaLbls.getFont().setStyle(Font.NORMAL);
                        gravaLbls.getFont().setSize(3);
                        
                        tabla.addCell(fechaa);
                        tabla.addCell(camionesChicos);
                        tabla.addCell(camionesTorton);
                        tabla.addCell(viajesTotalesLbl);
                        tabla.addCell(m3sLbl);
                        tabla.addCell(screenLbl);
                        tabla.addCell(gravaLbls);
                        
                        Paragraph screenLbls = new Paragraph("SCREEN M3");
                        screenLbls.getFont().setStyle(Font.BOLD);
                        screenLbls.getFont().setSize(3);
                        tabla.addCell(screenLbls);
                        Paragraph grava1medioLbl = new Paragraph("GRAVA 1/2 M3");
                        grava1medioLbl.getFont().setStyle(Font.BOLD);
                        grava1medioLbl.getFont().setSize(3);
                        tabla.addCell(grava1medioLbl);
                        Paragraph grava34Lbl = new Paragraph("GRAVA 1/3 M3");
                        grava34Lbl.getFont().setStyle(Font.BOLD);
                        grava34Lbl.getFont().setSize(3);
                        tabla.addCell(grava34Lbl);
                        Paragraph invenProducidaLbl = new Paragraph("INVENTARIO EN GREÑA");
                        invenProducidaLbl.getFont().setStyle(Font.BOLD);
                        invenProducidaLbl.getFont().setSize(3);
                        tabla.addCell(invenProducidaLbl);
                        Paragraph invenGrenaLbl = new Paragraph("INVENTARIO PRODUCIDA");
                        invenGrenaLbl.getFont().setStyle(Font.BOLD);
                        invenGrenaLbl.getFont().setSize(3);
                        tabla.addCell(invenGrenaLbl);
                        Paragraph totalcitoLbl = new Paragraph("TOTAL");
                        totalcitoLbl.getFont().setStyle(Font.BOLD);
                        totalcitoLbl.getFont().setSize(3);
                        tabla.addCell(totalcitoLbl);
                        Paragraph rendimientoLbl = new Paragraph("RENDIMIENTO");
                        rendimientoLbl.getFont().setStyle(Font.BOLD);
                        rendimientoLbl.getFont().setSize(3);
                        tabla.addCell(rendimientoLbl);
                        
                        screenDato = Float.parseFloat(datosConsultadosVenta3.get(1).get(j));
                        gravaMediaDato = Float.parseFloat(datosConsultadosVenta2.get(1).get(j));
                        gravaTresDato = Float.parseFloat(datosConsultadosVenta1.get(1).get(j));
                        enGrenaDato = Float.parseFloat(datosConsultadosInventario.get(2).get(j));
                        producidadDato = Float.parseFloat(datosConsultadosInventario.get(1).get(j));
                        suma = gravaMediaDato + gravaTresDato + enGrenaDato + producidadDato;
                        rendimiento = suma / m3;
                        Paragraph ventasArenilla = new Paragraph(datosConsultadosVenta3.get(1).get(j));
                        ventasArenilla.getFont().setStyle(Font.NORMAL);
                        ventasArenilla.getFont().setSize(3);
                        Paragraph ventasGravamedia = new Paragraph(datosConsultadosVenta2.get(1).get(j));
                        ventasGravamedia.getFont().setStyle(Font.NORMAL);
                        ventasGravamedia.getFont().setSize(3);
                        Paragraph ventasTresCuartos = new Paragraph(datosConsultadosVenta1.get(1).get(j));
                        ventasTresCuartos.getFont().setStyle(Font.NORMAL);
                        ventasTresCuartos.getFont().setSize(3);
                        Paragraph inventarioPiedraProducida = new Paragraph(datosConsultadosInventario.get(1).get(j));
                        inventarioPiedraProducida.getFont().setStyle(Font.NORMAL);
                        inventarioPiedraProducida.getFont().setSize(3);
                        Paragraph inventarioPiedraGrena = new Paragraph(datosConsultadosInventario.get(2).get(j));
                        inventarioPiedraGrena.getFont().setStyle(Font.NORMAL);
                        inventarioPiedraGrena.getFont().setSize(3);
                        Paragraph sumaLbl = new Paragraph(suma.toString());
                        sumaLbl.getFont().setStyle(Font.NORMAL);
                        sumaLbl.getFont().setSize(3);
                        Paragraph rendimientoLbls = new Paragraph(rendimiento.toString());
                        rendimientoLbls.getFont().setStyle(Font.NORMAL);
                        rendimientoLbls.getFont().setSize(3);
                        
                        tabla.addCell(ventasArenilla);
                        tabla.addCell(ventasGravamedia);
                        tabla.addCell(ventasTresCuartos);
                        tabla.addCell(inventarioPiedraProducida);
                        tabla.addCell(inventarioPiedraGrena);
                        tabla.addCell(sumaLbl);
                        tabla.addCell(rendimientoLbls);
                        
                    //}
                }
            }
            
            document.add(imagen);
            document.add(titulo);
            document.add(saltoLinea);
            document.add(tabla);
            document.close();  
        }catch(DocumentException | IOException | NumberFormatException e){
            System.err.println("Ocurrio un error al crear el archivo: " + e );
            System.exit(-1);
        }
        
    }
}


