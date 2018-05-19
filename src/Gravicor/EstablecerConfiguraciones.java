/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Daniel
 */
public class EstablecerConfiguraciones extends javax.swing.JFrame {

    /**
     * Creates new form EstablecerConfiguraciones
     */
    public EstablecerConfiguraciones() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Añadir Camion");
        
        try{
            ConfiguracionPrograma config = new ConfiguracionPrograma();
            ConfiguracionConexionDB configString = new ConfiguracionConexionDB();
            SpinnerModel model =
            new SpinnerNumberModel(0, //initial value
                                   0, //min
                                   100, //max
                                   1);  
            SpinnerModel modelDos =
            new SpinnerNumberModel(0, //initial value
                                   0, //min
                                   100, //max
                                   1);  
            SpinnerModel modelTres =
            new SpinnerNumberModel(0, //initial value
                                   0, //min
                                   10, //max
                                   1); 
            spinnerViajes.setModel(model);
            spinnerVentanasEmergentes.setModel(modelDos);
            spinnerImpresionesMax.setModel(modelTres);
            
            spinnerViajes.setValue(config.getValueWithHash("TiempoEsperaSiguienteViaje"));
            spinnerVentanasEmergentes.setValue(config.getValueWithHash("TiempoEsperaConfirmacionViaje"));
            spinnerImpresionesMax.setValue(config.getValueWithHash("ImpresionesMaximasPorOperadorAlDia"));
            nombreImpresoraTF.setText(configString.getValueWithHash("NombreImpresora"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "No fue posible recuperar los datos de configuración", "Error al cargar datos", JOptionPane.WARNING_MESSAGE);
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
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        spinnerViajes = new javax.swing.JSpinner();
        spinnerVentanasEmergentes = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        spinnerImpresionesMax = new javax.swing.JSpinner();
        nombreImpresoraTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel1.setText("Configuraciones");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/cancelar2.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/aceptar2.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel2.setText("Tiempo de espera para realizar un nuevo viaje: ");

        spinnerViajes.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        spinnerVentanasEmergentes.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel3.setText("Duración de ventanas emergentes: ");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel4.setText("Impresión máxima de tickets por operador:");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        jLabel5.setText("Nombre de impresora:");

        spinnerImpresionesMax.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        nombreImpresoraTF.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/encabezadoLogoGravicor.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombreImpresoraTF)
                            .addComponent(spinnerImpresionesMax, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(spinnerVentanasEmergentes)
                            .addComponent(spinnerViajes)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spinnerViajes, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinnerVentanasEmergentes, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinnerImpresionesMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreImpresoraTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 53, Short.MAX_VALUE))
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            if(new Integer((Integer)spinnerViajes.getValue()) > 100 || new Integer((Integer)spinnerViajes.getValue()) < 0){
                throw new NoTypeRequiredException("Los datos ingresados no son validos");
            }
            
            if(new Integer((Integer)spinnerVentanasEmergentes.getValue()) > 100 || new Integer((Integer)spinnerVentanasEmergentes.getValue()) < 0){
                    throw new NoTypeRequiredException("Los datos ingresados no son validos");
                }
            
            if(new Integer((Integer)spinnerImpresionesMax.getValue()) > 10 || 
                    new Integer((Integer)spinnerImpresionesMax.getValue()) < 0){
                throw new NoTypeRequiredException("Los datos ingresados no son validos");
            }
            ConfiguracionPrograma config = new ConfiguracionPrograma();
            ConfiguracionConexionDB configString = new ConfiguracionConexionDB();
            
            String[] keys = {"TiempoEsperaSiguienteViaje","TiempoEsperaConfirmacionViaje", "ImpresionesMaximasPorOperadorAlDia"};
            Integer[] values = {(Integer)spinnerViajes.getValue(),(Integer)spinnerVentanasEmergentes.getValue(), (Integer)spinnerImpresionesMax.getValue()};
            if(!config.modificarConfiguraciones(keys, values)){
                throw new NoTypeRequiredException("Error inesperado al guardar los datos");
            }
            
            String[] keysImpresora = {"NombreImpresora"};
            String[] valuesImpresora = {nombreImpresoraTF.getText()};
            if(!configString.modificarConfiguraciones(keysImpresora, valuesImpresora)){
                throw new NoTypeRequiredException("Error inesperado al guardar los datos");
            }
            
            boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
            if(bandera != false){
                GestionDePiedra pantallaConfiguracion= new GestionDePiedra();
                pantallaConfiguracion.setVisible(true);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos: " + Globales.baseDatos.getUltimoError(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Hubo un error al guardar los ajustes", "Error al guardar el registro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boolean bandera = Globales.baseDatos.conectarBD(Globales.baseDatos.generarURL());
        if(bandera != false){
            GestionDePiedra pantallaConfiguracion= new GestionDePiedra();
            pantallaConfiguracion.setVisible(true);
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
            java.util.logging.Logger.getLogger(EstablecerConfiguraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstablecerConfiguraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstablecerConfiguraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstablecerConfiguraciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EstablecerConfiguraciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreImpresoraTF;
    private javax.swing.JSpinner spinnerImpresionesMax;
    private javax.swing.JSpinner spinnerVentanasEmergentes;
    private javax.swing.JSpinner spinnerViajes;
    // End of variables declaration//GEN-END:variables
}
