/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Menú principal");
        JButton[] botones = {gestionGrenaB, gestionVentasB, reportesB, cobranzaB, tesoreriaB, gastosg, configuracionCuenta};
        try{
            
            String query = "select GESTIONPIEDRAGRENA, GESTIONVENTAS, REPORTES, COBRANZA, TESORERIA, GESTIONGASTOSGENERALES, CONFIGURACIONCUENTAS from USUARIO where usuario.USERNAME = '"+Globales.currentUser+"'";
            String[] columnas =  {"GESTIONPIEDRAGRENA", "GESTIONVENTAS", "REPORTES", "COBRANZA", "TESORERIA", "GESTIONGASTOSGENERALES", "CONFIGURACIONCUENTAS"};
            LinkedList<LinkedList<String>> permisos = Globales.baseDatos.select(query, columnas);
            
            if(permisos != null){
                
                //System.out.println(permisos);
                
                for(int i = 0; i < botones.length; i++){
                    if((permisos.get(i).get(0)).equals(new String("0"))){
                        botones[i].setEnabled(false);
                    }
                }
                
            }
            else{
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
            }
        }
        catch(NoConectionDataBaseException e){
            
            for(int i = 0; i < botones.length; i++){
                    
                    botones[i].setEnabled(false);
                    
            }
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
        gestionGrenaB = new javax.swing.JButton();
        gestionVentasB = new javax.swing.JButton();
        reportesB = new javax.swing.JButton();
        cobranzaB = new javax.swing.JButton();
        configuracionCuenta = new javax.swing.JButton();
        tesoreriaB = new javax.swing.JButton();
        gastosg = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        gestionGrenaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion piedra en greña 1.png"))); // NOI18N
        gestionGrenaB.setContentAreaFilled(false);
        gestionGrenaB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gestionGrenaB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion piedra en greña 2.png"))); // NOI18N
        gestionGrenaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionGrenaBActionPerformed(evt);
            }
        });

        gestionVentasB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion de ventas1.png"))); // NOI18N
        gestionVentasB.setContentAreaFilled(false);
        gestionVentasB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gestionVentasB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion de ventas2.png"))); // NOI18N
        gestionVentasB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionVentasBActionPerformed(evt);
            }
        });

        reportesB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/reportes y estadisticas 1.png"))); // NOI18N
        reportesB.setContentAreaFilled(false);
        reportesB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportesB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/reportes y estadisticas 2.png"))); // NOI18N
        reportesB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportesBActionPerformed(evt);
            }
        });

        cobranzaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/cobranza 1.png"))); // NOI18N
        cobranzaB.setContentAreaFilled(false);
        cobranzaB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cobranzaB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/cobranza 2.png"))); // NOI18N

        configuracionCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/configuracion de cuentas 1.png"))); // NOI18N
        configuracionCuenta.setContentAreaFilled(false);
        configuracionCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        configuracionCuenta.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/configuracion de cuentas 2.png"))); // NOI18N
        configuracionCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configuracionCuentaActionPerformed(evt);
            }
        });

        tesoreriaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/tesoreria 1.png"))); // NOI18N
        tesoreriaB.setContentAreaFilled(false);
        tesoreriaB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tesoreriaB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/tesoreria 2.png"))); // NOI18N

        gastosg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion gastos generales.png"))); // NOI18N
        gastosg.setContentAreaFilled(false);
        gastosg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gastosg.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/gestion gastos generales2.png"))); // NOI18N
        gastosg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gastosgActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/logoGavicor.png"))); // NOI18N

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/salir logo.png"))); // NOI18N
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(reportesB, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(configuracionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(gestionGrenaB, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gestionVentasB, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(66, 66, 66)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tesoreriaB, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gastosg, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(421, 421, 421)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(421, 421, 421)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cobranzaB, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(gestionGrenaB, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gestionVentasB, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gastosg, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reportesB, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(configuracionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tesoreriaB, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(cobranzaB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Registro registro = new Registro();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void configuracionCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configuracionCuentaActionPerformed
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            ConfiguracionCuentas cuentas = new ConfiguracionCuentas();
            cuentas.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_configuracionCuentaActionPerformed

    private void gestionGrenaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionGrenaBActionPerformed
        //pantalla a gestion de piedra en greña
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            GestionDePiedra piedra= new GestionDePiedra();
            piedra.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_gestionGrenaBActionPerformed

    private void gestionVentasBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionVentasBActionPerformed
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
    }//GEN-LAST:event_gestionVentasBActionPerformed

    private void reportesBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportesBActionPerformed
        // TODO add your handling code here:
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            ReporteEstadisticas reportes = new ReporteEstadisticas();
            reportes.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_reportesBActionPerformed

    private void gastosgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gastosgActionPerformed
        // TODO add your handling code here:
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            GestionGastosGenerales gastosGenerales = new GestionGastosGenerales();
            gastosGenerales.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_gastosgActionPerformed

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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cobranzaB;
    private javax.swing.JButton configuracionCuenta;
    private javax.swing.JButton gastosg;
    private javax.swing.JButton gestionGrenaB;
    private javax.swing.JButton gestionVentasB;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton reportesB;
    private javax.swing.JButton tesoreriaB;
    // End of variables declaration//GEN-END:variables
}
