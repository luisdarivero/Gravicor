/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel
 */
public class HistorialViajePorDiaCamion extends javax.swing.JFrame {

    /**
     * Creates new form HistorialViajePorDiaCamion
     */
    public HistorialViajePorDiaCamion() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Editar Viajes");
        fechaTB.setText(Globales.ultimaFecha);
        try{
            
            
            String query ="SELECT CAMION.IDCAMION, TIPOCAMION.DESCRIPCION, CAMION.OPERADOR \n" +
                    "FROM CAMION, TIPOCAMION\n" +
                    "WHERE CAMION.IDCAMION = " +Globales.ultimoCamion + " AND CAMION.IDTIPOCAMION = TIPOCAMION.IDTIPOCAMION " ;
            String[] columnasDescripcionGeneral = {"IDCAMION", "DESCRIPCION", "OPERADOR" };
            LinkedList<LinkedList<String>> datosCamion = Globales.bdTemp.select(query, columnasDescripcionGeneral);
            
            if(datosCamion == null){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
            }
            
            if(datosCamion.size() <1){
                throw new NoTypeRequiredException("No se pudo conectar con la base de datos, error inesperado");
            }
            
            camionL.setText(datosCamion.get(0).get(0));
            tipoL.setText(datosCamion.get(1).get(0));
            operadorL.setText(datosCamion.get(2).get(0));
            
            actualizarTabla();
            
            
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
        
        
    }
    
    public void actualizarTabla() throws NoConectionDataBaseException, NoTypeRequiredException{
        String query = "Select  VIAJE.IDVIAJE, convert(varchar, viaje.FECHA, 106) as Fecha,  CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora, TIPOCAMION.CAPACIDAD from VIAJE, CAMION, TIPOCAMION\n" +
                            " WHERE VIAJE.IDCAMION = " +Globales.ultimoCamion + "  and VIAJE.ESACTIVO = 1 and viaje.IDCAMION = camion.IDCAMION and camion.IDTIPOCAMION = TIPOCAMION.IDTIPOCAMION and\n" +
                            "convert(varchar, viaje.FECHA, 105) = '"+Globales.ultimaFecha+"'";
            String[] columnas = {"IDVIAJE","Fecha","Hora", "CAPACIDAD"};
            
            
            boolean  bandera = Globales.bdTemp.insertarEnTabla( query,columnas, tabla);
            if(bandera == false){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
            }
            
            query = "select COUNT(*) AS CONTEOVIAJES FROM VIAJE WHERE VIAJE.IDCAMION = " +Globales.ultimoCamion + " AND convert(varchar, viaje.FECHA, 105) = '"+Globales.ultimaFecha+"' and ESACTIVO = 1";
            String[] columnasConteo = {"CONTEOVIAJES"};
            LinkedList<LinkedList<String>> conteoViajes = Globales.bdTemp.select(query, columnasConteo);
            
            if(conteoViajes == null){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
            }
            
            if(conteoViajes.size() <1){
                throw new NoTypeRequiredException("No se pudo conectar con la base de datos, error inesperado");
            }
            
            viajesL.setText(conteoViajes.get(0).get(0));
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        camionL = new javax.swing.JLabel();
        tipoL = new javax.swing.JLabel();
        operadorL = new javax.swing.JLabel();
        fechaTB = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        viajesL = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID Viaje", "Fecha", "Hora", "Cantidad (M3)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabla);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel1.setText("Editar Viajes");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        jLabel2.setText("Camión:");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        jLabel3.setText("Tipo:");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        jLabel4.setText("Operador:");

        camionL.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        camionL.setText(" ");

        tipoL.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        tipoL.setText(" ");

        operadorL.setFont(new java.awt.Font("Calibri", 0, 21)); // NOI18N
        operadorL.setText(" ");

        fechaTB.setEditable(false);
        fechaTB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fechaTB.setText(" ");
        fechaTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaTBActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        jLabel5.setText("Viajes Totales:");

        viajesL.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        viajesL.setText(" ");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/back-icon.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/eliminarViaje1.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/eliminarViaje2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/anadirViajes1.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/anadirViajes2.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(camionL, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tipoL, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLabel1)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(operadorL, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(fechaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(viajesL, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(98, 98, 98)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(70, 70, 70)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(fechaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(camionL)
                    .addComponent(tipoL)
                    .addComponent(operadorL))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(viajesL))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechaTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaTBActionPerformed
    private void limpiarTabla(){
        DefaultTableModel dtm = (DefaultTableModel)tabla.getModel(); 
        int numRows = dtm.getRowCount();
        //dtm.removeRow(0);
        dtm.setRowCount(0);
        String[] columnas = {"","","","",""};
        dtm.addRow(columnas);
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(tabla.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "Por favor selecciona de la tabla el viaje que desea eliminar", "Error de selección", JOptionPane.ERROR_MESSAGE);
        }
        else{
            String idViaje = (String)tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
            Object[] options = {"SI",
                    "NO"};
            int n = JOptionPane.showOptionDialog(this, //si = 0, no = 1
                "¿Estás seguro que deseas eliminar este regisstro?",
                "¿Eliminar registro?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
            if(n == 0){
                String query = "UPDATE VIAJE SET ESACTIVO = 0 WHERE IDVIAJE = " + idViaje;
                try{
                    boolean bandera = Globales.bdTemp.update(query);
                    if(bandera == false){
                        throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
                    }
                    limpiarTabla();
                    actualizarTabla();
                }
                catch(NoConectionDataBaseException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
                }
                catch(NoTypeRequiredException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
                }
            }
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Object[] possibilities = {1,2,3,4,5,6,7,8,9,10};
        Integer numeroViajes = 0;
        try{
                numeroViajes = (Integer)JOptionPane.showInputDialog(
                            this,
                            "Añadir Viajes:\n"
                            + "\"Selecciona los viajes que deseas añadir para esta fecha\"",
                            "Añadir Viajes",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            possibilities,
                            possibilities[0]);
                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "No se pudo realizar la operación especificada", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        
        if (numeroViajes != null){
            //mandar registro a la base de datos
            try{
                String queryCamionActivo= "SELECT ACTIVO.DESCRIPCION\n" +
                                            "FROM CAMION, ACTIVO\n" +
                                            "WHERE CAMION.ACTIVO = ACTIVO.IDACTIVO AND CAMION.IDCAMION = "+Globales.ultimoCamion+"";

                String[] columnasCamionActivo = {"DESCRIPCION"};

                LinkedList<LinkedList<String>> camionActivo = Globales.bdTemp.select(queryCamionActivo, columnasCamionActivo);

                if(camionActivo == null || camionActivo.get(0).size() == 0){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
                }

                if(!camionActivo.get(0).get(0).equals(new String("ACTIVO"))){
                    throw new NoTypeRequiredException("Este camión está registrado como 'Inactivo', por favor contacta al administrador");
                }
                String query = "SELECT USUARIO.IDUSUARIO FROM USUARIO WHERE USUARIO.USERNAME = '"+Globales.currentUser+"'";
                String[] columnas = {"IDUSUARIO"};


                LinkedList<LinkedList<String>> idUsuario = Globales.bdTemp.select(query, columnas);
                if(idUsuario == null){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
                }
                query = "INSERT INTO VIAJE (IDUSUARIO,IDCAMION,FECHA,HORA,ESACTIVO) VALUES ("+idUsuario.get(0).get(0)+","+Globales.ultimoCamion+", '"+Globales.ultimaFecha+"', "
                        + "'00:01', 1)";
                try{
                    for(int i = 0; i< numeroViajes; i++){
                        boolean confirmacion = Globales.bdTemp.insert(query);
                        if(confirmacion == false){
                            throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
                        }
                    }
                }
                catch(NoConectionDataBaseException e){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
                }
                catch(Exception e){
                    throw new NoTypeRequiredException("Error inesperado al registrar los viajes, por favor contacta al administrador");
                }



            }
            catch(NoConectionDataBaseException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            }
            catch(NoTypeRequiredException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
            }
            //actualizar la tabla
            try{
                limpiarTabla();
                actualizarTabla();
            }
            catch(Exception e){
                //JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        HistorialViajesDelDia viajesDia= new HistorialViajesDelDia();
        viajesDia.setVisible(true);
        this.dispose();
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
            java.util.logging.Logger.getLogger(HistorialViajePorDiaCamion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialViajePorDiaCamion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialViajePorDiaCamion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialViajePorDiaCamion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistorialViajePorDiaCamion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel camionL;
    private javax.swing.JTextField fechaTB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel operadorL;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tipoL;
    private javax.swing.JLabel viajesL;
    // End of variables declaration//GEN-END:variables
}
