/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.awt.TextField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel
 */
public class HistorialVentasDelDia extends javax.swing.JFrame {

    private MyGregorianCalendar diaHistorial = new MyGregorianCalendar();
    private String minDate = "2-1-2018";
    private String maxDate = "1-1-2018";
    /**
     * Creates new form HistorialVentasDelDia
     */
    public HistorialVentasDelDia() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Historial de ventas del día");
        
        try{
            String queryFecha = "SELECT convert(varchar, getdate(), 105) as FECHA";
            String[] columnasFecha = {"FECHA"};
            LinkedList<LinkedList<String>> fecha = Globales.baseDatos.select(queryFecha, columnasFecha);
            if(fecha == null){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
            }
            if(fecha.size() <1 || fecha.get(0).size() < 1){
                throw new NoTypeRequiredException("No se pudo conectar con la base de datos, error inesperado");
            }
            
            String[] resultadoFecha = fecha.get(0).get(0).split("-");
            GregorianCalendar dia = new GregorianCalendar();
            diaHistorial.set(new Integer(resultadoFecha[2]),new Integer(resultadoFecha[1]) -1,new Integer(resultadoFecha[0]));
            calendario.setMaxSelectableDate(diaHistorial.getTime());
            maxDate = getStringDate(diaHistorial.getTime());
            calendario.setDate(diaHistorial.getTime());
            dia.set(2018,0,1);
            calendario.setMinSelectableDate(dia.getTime());
            
            //actualizarPantalla(fecha.get(0).get(0));
            actualizarTabla(fecha.get(0).get(0));
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        calendario = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 255, 255));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Folio interno", "Folio transportista", "hora", "Cliente", "Material", "Cantidad", "Precio M3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabla);

        calendario.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioPropertyChange(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/eliminarVenta.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/eliminarVenta2.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/Actualizar.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/Actualizar2.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/regresar.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/regresar2.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/encabezadoLogoGravicor.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel2.setText("Historial de ventas del día");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/editarPrecioPorM3.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/editarPrecioPorM32.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(294, 294, 294)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(75, 75, 75)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addGap(77, 77, 77)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addComponent(jButton1))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        //eliminar venta de un día
        if(tabla.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "Por favor selecciona de la tabla la venta que desea eliminar", "Error de selección", JOptionPane.ERROR_MESSAGE);
        }
        else{
            String idVenta = (String)tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
            Object[] options = {"SI",
                    "NO"};
            int n = JOptionPane.showOptionDialog(this, //si = 0, no = 1
                "¿Estás seguro que deseas eliminar este registro?",
                "¿Eliminar registro?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
            if(n == 0){
                String query = "UPDATE VENTA SET ACTIVO = 0 WHERE VENTAID = " + idVenta;
                
                
                try{
                    //se valida si es credito o ya fue pagada en efectivo
                    int esCredito = Globales.baseDatos.esVentaCredito(idVenta);
                    //se valida que la venta no este facturada
                    int esFacturada = Globales.baseDatos.esVentaFacturada(idVenta);
                    if(esFacturada == -1 || esCredito ==-1){
                        throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                    }
                    else if(esCredito == 1 && esFacturada == 1){
                        throw new NoTypeRequiredException("La venta ya se encuentra facturada y no es posible eliminar el registro");
                    }
                    //se procede a modificar el registro
                    
                    boolean bandera = Globales.baseDatos.update(query);
                    if(bandera == false){
                        throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                    }
                    limpiarTabla();
                    actualizarTabla(getStringDate(diaHistorial.getTime()));
                }
                catch(NoConectionDataBaseException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
                }
                catch(NoTypeRequiredException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
                }
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void calendarioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioPropertyChange
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_calendarioPropertyChange

    private String getStringDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = "";
        try{
            fecha = sdf.format(date);
        }
        catch(Exception e){
            return null;
        }
        
        String[] resultadoFecha = fecha.split("-");
        if(resultadoFecha.length != 3){
            return null;
        }
        MyGregorianCalendar dia = new MyGregorianCalendar();
        dia.set(new Integer(resultadoFecha[2]),new Integer(resultadoFecha[1]) -1,new Integer(resultadoFecha[0]));
        
        return fecha;
    }
    
    private boolean validateDate(String fechaInicio, String fechaFinal, String fechaActual){
        if(fechaInicio == null || fechaFinal == null || fechaActual == null){
            
            return false;
        }
        String[] rInicio = fechaInicio.split("-");
        String[] rFinal = fechaFinal.split("-");
        String[] rActual = fechaActual.split("-");
        if(rInicio.length != 3 || rFinal.length != 3 || rActual.length != 3){
            
            return false;
        }
        
        if(Integer.parseInt(rActual[2]) < Integer.parseInt(rInicio[2]) || Integer.parseInt(rActual[2]) > Integer.parseInt(rFinal[2])){
            
            return false;
        }
        
        if(Integer.parseInt(rInicio[2]) == Integer.parseInt(rActual[2]) ){
            if(Integer.parseInt(rInicio[1]) > Integer.parseInt(rActual[1]) ){
                
                return false;
            }
            else if(Integer.parseInt(rInicio[1]) == Integer.parseInt(rActual[1])){
                if(Integer.parseInt(rInicio[0]) > Integer.parseInt(rActual[0])){
                    
                    return false;
                }
            }
        }
        
        if(Integer.parseInt(rFinal[2]) == Integer.parseInt(rActual[2]) ){
            if(Integer.parseInt(rFinal[1]) < Integer.parseInt(rActual[1]) ){
                
                return false;
            }
            else if(Integer.parseInt(rFinal[1]) == Integer.parseInt(rActual[1])){
                if(Integer.parseInt(rFinal[0]) < Integer.parseInt(rActual[0])){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void limpiarTabla(){
        DefaultTableModel dtm = (DefaultTableModel)tabla.getModel(); 
        int numRows = dtm.getRowCount();
        //dtm.removeRow(0);
        dtm.setRowCount(0);
        String[] columnas = {"","","","","","","",""};
        dtm.addRow(columnas);
    }
    
    private void actualizarTabla(String fecha) throws NoConectionDataBaseException, NoTypeRequiredException{
        String query = "SELECT V.VENTAID, V.FOLIOTRANSPORTISTA, convert(varchar, V.HORAVENTA, 108) AS HORA, C.NOMBRECLIENTE, \n" +
                    "M.DESCRIPCIONMATERIAL, V.CANTIDADM3, V.PRECIOM3\n" +
                    "FROM VENTA AS V, CLIENTE AS C, MATERIAL AS M\n" +
                    "WHERE V.CLIENTEID = C.CLIENTEID AND V.MATERIALID = M.MATERIALID AND V.ACTIVO = 1 AND convert(varchar, V.FECHAVENTA, 105) "
                + "= convert(varchar, '"+getStringDate(diaHistorial.getTime())+"', 105) ";
        
        String[] columnas = {"VENTAID","FOLIOTRANSPORTISTA","HORA","NOMBRECLIENTE","DESCRIPCIONMATERIAL","CANTIDADM3","PRECIOM3"};
        boolean validacion = Globales.baseDatos.insertarEnTabla(query, columnas, tabla);
        
        if(validacion == false){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
        }
        
        
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        
        try{
            if(!validateDate(minDate,maxDate, getStringDate(calendario.getDate()))){
                throw new NoTypeRequiredException("La fecha seleccionada contiene un error, por favor corrigela");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = sdf.format(calendario.getDate());
            String[] resultadoFecha = fecha.split("-");
            diaHistorial = new MyGregorianCalendar();
            diaHistorial.set(new Integer(resultadoFecha[2]),new Integer(resultadoFecha[1]) -1,new Integer(resultadoFecha[0]));
            
            limpiarTabla();
           
            actualizarTabla(fecha);
            //System.out.println(getStringDate(diaHistorial.getTime()));
            
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            GestionDeVentas ventas= new GestionDeVentas();
            ventas.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(tabla.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "Por favor selecciona de la tabla la venta que deseas modificar", "Error de selección", JOptionPane.ERROR_MESSAGE);
        }
        else{
            try{
                //se guarda el id de la venta
                String idVenta = (String)tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
                //se valida que la seleccion de la venta sea valida  no sea n null o vacio
                if(idVenta.equals("") || idVenta == null){
                    throw new NoTypeRequiredException("Por favor selecciona un registro válido");
                }
                //se valida si es credito o ya fue pagada en efectivo
                int esCredito = Globales.baseDatos.esVentaCredito(idVenta);
                //se valida que la venta no este facturada
                int esFacturada = Globales.baseDatos.esVentaFacturada(idVenta);
                if(esFacturada == -1 || esCredito ==-1){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                }
                else if(esCredito == 1 && esFacturada == 1){
                    throw new NoTypeRequiredException("La venta ya se encuentra facturada y no es posible editar el registro");
                }
                
                //se confirma que se desea editar el registro
                TextField textF = new TextField();
                int selection = JOptionPane.showOptionDialog(null, textF, "Ingresa el nuevo precio del material", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null);
                if(selection < 0){
                    throw new NoTypeRequiredException("Por favor ingresa un número válido antes de continuar");
                }
                Float nuevoMonto = new Float (0);
                try{
                    nuevoMonto = new Float(textF.getText());
                }
                catch(Exception e){
                    throw new NoTypeRequiredException("Por favor ingresa un número válido antes de continuar");
                }
                
                Object[] options = {"SI",
                    "NO"};
                int n = JOptionPane.showOptionDialog(this, //si = 0, no = 1
                    "¿Estás seguro que deseas cambiar el precio del material a $"+nuevoMonto+" de la venta #"+idVenta+"?",
                    "¿Editar registro?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title
                if(n == 0){
                    //se procede a editar el registro
                    String query = "UPDATE VENTA SET PRECIOM3 = " + nuevoMonto + " WHERE VENTA.VENTAID = " + idVenta;
                    boolean exito = Globales.baseDatos.update(query);
                    if(!exito){
                        throw new NoConectionDataBaseException("Error al conectar con la base de datos: " + Globales.baseDatos.getUltimoError());
                    }
                    limpiarTabla();
                    actualizarTabla(getStringDate(diaHistorial.getTime()));
                    JOptionPane.showMessageDialog(this,"El registro se ha guardado con éxito","El registro se ha guardado con éxito",JOptionPane.INFORMATION_MESSAGE);
                    
                }
                
                
            }
           
            catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
            }
            catch(NoConectionDataBaseException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistorialVentasDelDia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialVentasDelDia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialVentasDelDia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialVentasDelDia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistorialVentasDelDia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser calendario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
