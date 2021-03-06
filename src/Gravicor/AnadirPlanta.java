/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class AnadirPlanta extends javax.swing.JFrame {

    /**
     * Creates new form anadirPlanta
     */
    public AnadirPlanta() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Añadir planta");
        
        try{
            String query = "SELECT TIPOPLANTA.DESCRIPCION\n" +
                        "FROM TIPOPLANTA";
            String[] columnasDescripcion = {"DESCRIPCION"};
            
            LinkedList<LinkedList<String>> descripcionPlantas = Globales.baseDatos.select(query, columnasDescripcion);
            if(descripcionPlantas == null || descripcionPlantas.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: " +  Globales.baseDatos.getUltimoError());
            }
            
            String[] modeloDescripcion = new String[descripcionPlantas.get(0).size()];
            for(int i = 0; i<descripcionPlantas.get(0).size(); i++){
                modeloDescripcion[i] = descripcionPlantas.get(0).get(i);
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(modeloDescripcion);
            tipoCB.setModel(model);
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreTF = new javax.swing.JTextField();
        tipoCB = new javax.swing.JComboBox<>();
        cancelarB = new javax.swing.JButton();
        aceptarB = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel2.setText("Tipo de planta:");

        nombreTF.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        tipoCB.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tipoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cancelarB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar.png"))); // NOI18N
        cancelarB.setContentAreaFilled(false);
        cancelarB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelarB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar2.png"))); // NOI18N
        cancelarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBActionPerformed(evt);
            }
        });

        aceptarB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar.png"))); // NOI18N
        aceptarB.setContentAreaFilled(false);
        aceptarB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aceptarB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar2.png"))); // NOI18N
        aceptarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarBActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/encabezadoLogoGravicor.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel4.setText("Añadir planta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(nombreTF)
                            .addComponent(tipoCB, 0, 311, Short.MAX_VALUE))))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelarB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aceptarB)
                .addGap(145, 145, 145))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tipoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aceptarB)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cancelarB)
                        .addGap(28, 28, 28)))
                .addGap(40, 40, 40))
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

    private void cancelarBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBActionPerformed
        // TODO add your handling code here:
        GestionDePlantas plantas = new GestionDePlantas();
        plantas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelarBActionPerformed

    private void aceptarBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarBActionPerformed
        // TODO add your handling code here:
        try{
            if(nombreTF.getText().length() <4 || nombreTF.getText().length() > 50){
                throw new NoTypeRequiredException("El nombre debe contener entre 4 y 50 caracteres");
            }
            String query = "SELECT PLANTAID FROM PLANTA WHERE NOMBREPLANTA = '"+nombreTF.getText().toLowerCase()+"'";
            String[] columnasNombreExistente = {"PLANTAID"};
            LinkedList<LinkedList<String>> nombreExistente = Globales.baseDatos.select(query, columnasNombreExistente);
            
            if(nombreExistente == null){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
            }
            
            if(nombreExistente.get(0).size() > 0){
                throw new NoTypeRequiredException("El nombre de planta ingresado ya existe, por favor verifica este campo");
            }
            
            query = "SELECT TIPOPLANTA.TIPOPLANTAID\n" +
                                "FROM TIPOPLANTA\n" +
                                "WHERE TIPOPLANTA.DESCRIPCION = '"+ (String)tipoCB.getSelectedItem()+"'";
            String[] columnas = {"TIPOPLANTAID"};
            LinkedList<LinkedList<String>> idTipoPlanta = Globales.baseDatos.select(query, columnas);
            if(idTipoPlanta == null || idTipoPlanta.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
            }
            Integer idTipoPlantaInt = Integer.parseInt(idTipoPlanta.get(0).get(0));
            
            query = "INSERT INTO PLANTA VALUES ('"+nombreTF.getText().toLowerCase()+"', "+idTipoPlantaInt+",0)";
            
            boolean res = Globales.baseDatos.insert(query);
            if(res){
                GestionDePlantas gestionPlantas = new GestionDePlantas();
                gestionPlantas.setVisible(true);
                this.dispose();
            }
            else{
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "  + Globales.baseDatos.getUltimoError());
            }
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_aceptarBActionPerformed

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
            java.util.logging.Logger.getLogger(AnadirPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnadirPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnadirPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnadirPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnadirPlanta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarB;
    private javax.swing.JButton cancelarB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreTF;
    private javax.swing.JComboBox<String> tipoCB;
    // End of variables declaration//GEN-END:variables
}
