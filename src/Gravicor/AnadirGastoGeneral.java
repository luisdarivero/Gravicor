/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class AnadirGastoGeneral extends javax.swing.JFrame {

    private Integer mesGasto = 1;
    private Integer anoGasto = 2018;
    private int anoSelectedIndexPantallaAnterior = 0;
    private MyGregorianCalendar mesActual = new MyGregorianCalendar();
    /**
     * Creates new form AnadirGastoGeneral
     */
    public AnadirGastoGeneral() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Añadir Gasto General");
        generarLimitesEnFechas();
        anadirFechaCB.setSelected(true);
    }
    
    public void setAnoYMes(Integer ano, Integer mes){
        this.mesGasto = mes;
        this.anoGasto = ano;
        generarLimitesEnFechas();
    }
    
    public void setAnoSelectedIndexPrev(int n){
        this.anoSelectedIndexPantallaAnterior = n;
    }
    
    
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
    
    private boolean validarFechaFactura(){
        try{
            GregorianCalendar diaMinFactura = new GregorianCalendar(); //dia minimo en la que se puede registrar la factura
            diaMinFactura.set(anoGasto, mesGasto -1, 1);
            GregorianCalendar diaMaxFactura = new GregorianCalendar(); //dia minimo en la que se puede registrar la factura
            diaMaxFactura.set(anoGasto, mesGasto -1, mesActual.getActualMaximum(Calendar.DAY_OF_MONTH));
            if(!validateDate(getStringDate(diaMinFactura.getTime()),
                    getStringDate(diaMaxFactura.getTime()), getStringDate(calendarioFechaFactura.getDate()))){
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    
    private boolean validarFolioInterno(){
        try{
            if(!validateDate(getStringDate(calendarioFolioInterno.getMinSelectableDate()),
                    getStringDate(calendarioFolioInterno.getMaxSelectableDate()),getStringDate(calendarioFolioInterno.getDate()))){
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    
    private void generarLimitesEnFechas(){
        try{
            //actualizacion de fecha para el modulo de factura
            mesActual.set(anoGasto,mesGasto -1,1); //hace referencia unicamente al mes actual
            GregorianCalendar diaMinFactura = new GregorianCalendar(); //dia minimo en la que se puede registrar la factura
            diaMinFactura.set(anoGasto, mesGasto -1, 1);
            calendarioFechaFactura.setMinSelectableDate(diaMinFactura.getTime());
            GregorianCalendar diaMaxFactura = new GregorianCalendar(); //dia minimo en la que se puede registrar la factura
            diaMaxFactura.set(anoGasto, mesGasto -1, mesActual.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendarioFechaFactura.setMaxSelectableDate(diaMaxFactura.getTime());
            calendarioFechaFactura.setDate(mesActual.getTime());
            //actualizacion de fechas 
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
            GregorianCalendar diaMaxFolioI = new GregorianCalendar();
            diaMaxFolioI.set(new Integer(resultadoFecha[2]),new Integer(resultadoFecha[1]) -1,new Integer(resultadoFecha[0]));
            calendarioFolioInterno.setMaxSelectableDate(diaMaxFolioI.getTime());
            calendarioFolioInterno.setMinSelectableDate(diaMinFactura.getTime());
            calendarioFolioInterno.setDate(mesActual.getTime());
            
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error al cargar las fechas: "+e.getMessage(), "Error al cargar las fechas", JOptionPane.ERROR_MESSAGE);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        folioInternoTF = new javax.swing.JTextField();
        folioFacturaTB = new javax.swing.JTextField();
        calendarioFolioInterno = new com.toedter.calendar.JDateChooser();
        calendarioFechaFactura = new com.toedter.calendar.JDateChooser();
        descripcionTF = new javax.swing.JTextField();
        equipoTF = new javax.swing.JTextField();
        proveedorTF = new javax.swing.JTextField();
        anadirFechaCB = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        totalTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar2.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel1.setText("Folio interno de pago:");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel2.setText("Fecha de pago de folio interno:");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel3.setText("Folio de factura:");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel4.setText("Fecha de factura:");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel5.setText("Descripción:");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel6.setText("Equipo:");

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel7.setText("Proveedor:");

        folioInternoTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        folioInternoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folioInternoTFActionPerformed(evt);
            }
        });

        folioFacturaTB.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        descripcionTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        equipoTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        proveedorTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        anadirFechaCB.setBackground(new java.awt.Color(255, 255, 255));
        anadirFechaCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        anadirFechaCB.setText("Añadir fecha de pago de folio interno");
        anadirFechaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anadirFechaCBActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel8.setText("Total:");

        totalTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/encabezadoLogoGravicor.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel10.setText("Añadir gasto general");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)))
                            .addComponent(jLabel8))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(anadirFechaCB)
                            .addComponent(folioInternoTF)
                            .addComponent(folioFacturaTB)
                            .addComponent(descripcionTF)
                            .addComponent(equipoTF)
                            .addComponent(proveedorTF)
                            .addComponent(calendarioFolioInterno, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addComponent(calendarioFechaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalTF)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel9)))
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(242, 242, 242))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(folioInternoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(calendarioFolioInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anadirFechaCB)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(folioFacturaTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel4))
                    .addComponent(calendarioFechaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(equipoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proveedorTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void folioInternoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folioInternoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folioInternoTFActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            if(folioInternoTF.getText().length() > 50){
                throw new NoTypeRequiredException("El Folio interno de pago supera los 50 caracteres, por favor ingresa un texto válido");
            }
            if(!validarFechaFactura()){
                throw new NoTypeRequiredException("La fecha de la factura no tiene un formato valido o no está en un rango correcto de fechas");
            }
            if(folioFacturaTB.getText().length() < 2 || folioFacturaTB.getText().length() > 50){
                throw new NoTypeRequiredException("El Folio de la factura debe estár entre 2 y 50 caracteres, por favor ingresa un texto válido");
            }
            if(anadirFechaCB.isSelected()){
                if(!validarFolioInterno()){
                    throw new NoTypeRequiredException("La fecha del pago del folio interno no tiene un formato valido o no está en un rango correcto de fechas");
                }
            }
            
            if(descripcionTF.getText().length() < 4 || descripcionTF.getText().length()> 50){
                throw new NoTypeRequiredException("La descripción debe estár entre 4 y 50 caracteres, por favor ingresa un texto válido");
            }
            Float total = (float)0;
            try{
                total = Float.parseFloat(totalTF.getText());
            }
            catch(Exception e){
                 throw new NoTypeRequiredException("El total designado no es un número válido");
            }
            
            if(equipoTF.getText().length() > 50){
                throw new NoTypeRequiredException("La descripción del equipo los 50 caracteres, por favor ingresa un texto válido");
            }
            if(proveedorTF.getText().length() < 4 || proveedorTF.getText().length() > 50){
                throw new NoTypeRequiredException("La descripción del proveedor debe estár entre 4 y 50 caracteres, por favor ingresa un texto válido");
            }
            
            //generar el query para insertar los datos
            String query = "INSERT INTO GASTOGENERAL\n" +
                            "(IDFOLIOPAGO,FECHAFOLIOPAGO, FOLIOFACTURA, FECHAFACTURA,TOTAL,DESCRIPCION,EQUIPO,PROVEEDOR,ESACTIVO)\n" +
                            "VALUES ";
            String fechaFolioInterno = "null";
            
            if(anadirFechaCB.isSelected()){
                fechaFolioInterno = "'" + getStringDate(calendarioFolioInterno.getDate()) + "'" ;
            }
            String[] arregloValores = {"'" +folioInternoTF.getText().toLowerCase()+"'" , fechaFolioInterno, 
                                        "'" +folioFacturaTB.getText().toLowerCase()+"'" ,"'" +getStringDate(calendarioFechaFactura.getDate())+"'" ,
                                        total.toString(),"'" +descripcionTF.getText().toLowerCase()+"'" , "'" +equipoTF.getText().toLowerCase()+"'" ,
                                        "'" +proveedorTF.getText().toLowerCase()+"'" ,"1"};
            String values = "(" + arregloValores[0];
            for(int i = 1; i< arregloValores.length; i++){
                values += "," + arregloValores[i];
            }
            query += values +  ")";
            //System.out.println(query);
            
            boolean validacion = Globales.baseDatos.insert(query);
            if(!validacion){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
            }
            
            GestionGastosGenerales gestionGastos= new GestionGastosGenerales();
            gestionGastos.actualizarPantallaConFecha(anoSelectedIndexPantallaAnterior, mesGasto-1);
            gestionGastos.setVisible(true);
            this.dispose();
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void anadirFechaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anadirFechaCBActionPerformed
        // TODO add your handling code here:
        if(anadirFechaCB.isSelected()){
            calendarioFolioInterno.setEnabled(true);
        }
        else{
            calendarioFolioInterno.setEnabled(false);
        }
    }//GEN-LAST:event_anadirFechaCBActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            GestionGastosGenerales gestionGastos= new GestionGastosGenerales();
            gestionGastos.actualizarPantallaConFecha(anoSelectedIndexPantallaAnterior, mesGasto-1);
            gestionGastos.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AnadirGastoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnadirGastoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnadirGastoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnadirGastoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnadirGastoGeneral().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox anadirFechaCB;
    private com.toedter.calendar.JDateChooser calendarioFechaFactura;
    private com.toedter.calendar.JDateChooser calendarioFolioInterno;
    private javax.swing.JTextField descripcionTF;
    private javax.swing.JTextField equipoTF;
    private javax.swing.JTextField folioFacturaTB;
    private javax.swing.JTextField folioInternoTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField proveedorTF;
    private javax.swing.JTextField totalTF;
    // End of variables declaration//GEN-END:variables
}
