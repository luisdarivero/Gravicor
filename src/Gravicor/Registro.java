/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Daniel
 */
public class Registro extends javax.swing.JFrame {

    /**
     * Creates new form Registro
     */
    public Registro() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        //se inician los datos de la conexion a la base de datos
        try{
            ConfiguracionConexionDB conf = new ConfiguracionConexionDB();
            ipHostTF.setText(conf.getValueWithHash("DireccionIP"));
            usuarioHostTF.setText(conf.getValueWithHash("Usuario"));
            contrasenaHostTF.setText(conf.getValueWithHash("Contrasena"));
        }
        catch(Exception e){
            
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        usuarioTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        contrasenaTextField = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        editarConexionCB = new javax.swing.JCheckBox();
        ipHostTF = new javax.swing.JPasswordField();
        usuarioHostTF = new javax.swing.JPasswordField();
        contrasenaHostTF = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro");
        setName("Registro"); // NOI18N
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(1, 119, 215));
        jPanel1.setToolTipText("");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/logoRegistro.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Registro");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Registro para acceder a plataforma de GRAVICOR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel5)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addComponent(jLabel6)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/logoGavicor.png"))); // NOI18N

        usuarioTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usuarioTextField.setText("luisdaniel");
        usuarioTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Usuario:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Contraseña:");

        contrasenaTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        contrasenaTextField.setText("adminJava");
        contrasenaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenaTextFieldActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/ContinuarAzul.png"))); // NOI18N
        jButton2.setToolTipText("");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/ContinuarAzul2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("IP Host:");

        jLabel8.setText("Usuario:");

        jLabel9.setText("Contraseña");

        editarConexionCB.setBackground(new java.awt.Color(255, 255, 255));
        editarConexionCB.setText("Editar Conexión a base de datos:");
        editarConexionCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarConexionCBActionPerformed(evt);
            }
        });

        ipHostTF.setEnabled(false);

        usuarioHostTF.setEnabled(false);

        contrasenaHostTF.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuarioTextField)
                            .addComponent(contrasenaTextField)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 29, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ipHostTF)
                                    .addComponent(usuarioHostTF)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(editarConexionCB)
                                        .addGap(81, 81, 81))
                                    .addComponent(contrasenaHostTF))))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usuarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(contrasenaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editarConexionCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ipHostTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(usuarioHostTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(contrasenaHostTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usuarioTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioTextFieldActionPerformed
        jButton2ActionPerformed(null);
    }//GEN-LAST:event_usuarioTextFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
            
            if(usuarioTextField.getText().length() > 50 || usuarioTextField.getText().length() < 1
                    || contrasenaTextField.getPassword().length< 1 || contrasenaTextField.getPassword().length > 50){
                throw new NoTypeRequiredException("Fomato de usuario o contraseña invalido, por favor"
                        + " verifica que los datos ingresados sean correctos");
            }
            String textoEncriptadoConMD5=DigestUtils.md5Hex(new String(contrasenaTextField.getPassword()));
            String user = usuarioTextField.getText();
            String ip = new String(ipHostTF.getPassword());
            String usuarioBD = new String(usuarioHostTF.getPassword());
            String contrasenaBD = new String(contrasenaHostTF.getPassword());
            //Globales.baseDatos = new BD("LAPTOP-LVSV7Q0O","1433","GRAVICOR","luisdarivero.s@gmail.com","adminJava");
            Globales.baseDatos = new BD(ip,"1433","GRAVICOR",usuarioBD,contrasenaBD);
            if(Globales.baseDatos.isIsConectado() == true){
                //comprobar el usuario y contraseña
                String[] columnas = {"CONTRASENA"};
                LinkedList<LinkedList<String>> resultado = Globales.baseDatos.select("select CONTRASENA FROM USUARIO WHERE USUARIO.USERNAME = '"+user.toLowerCase()+"'", columnas);
                
                if(resultado.get(0).size() > 0){
                    if(!(resultado.get(0).get(0)).equals(textoEncriptadoConMD5)){
                        throw new NoTypeRequiredException("La contraseña o usuario son incorectos, por favor corrígelos");
                    }
                    //se guardan los cambios en el archivo de configuracion de conexion a BD
                    ConfiguracionConexionDB config = new ConfiguracionConexionDB();
                    String[] keys = {"DireccionIP","Usuario","Contrasena"};
                    String[] values = {ip,usuarioBD,contrasenaBD};
                    if(!config.modificarConfiguraciones(keys, values)){
                        throw new NoTypeRequiredException("Error inesperado al guardar los datos");
                    }
                    //termina el guardado de info
                    Globales.currentUser = user.toLowerCase();
                    
                    MenuPrincipal menu = new MenuPrincipal();
                    
                    this.dispose();
                    menu.setVisible(true);
                }
                else{
                    throw new NoTypeRequiredException("La contraseña o usuario son incorectos, por favor corrígelos");
                }
                
            }
            else{
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                + Globales.baseDatos.getUltimoError());
            }
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void contrasenaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenaTextFieldActionPerformed
        // TODO add your handling code here:
        jButton2ActionPerformed(null);
    }//GEN-LAST:event_contrasenaTextFieldActionPerformed

    private void editarConexionCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarConexionCBActionPerformed
        if(editarConexionCB.isSelected()){
            ipHostTF.setEnabled(true);
            usuarioHostTF.setEnabled(true);
            contrasenaHostTF.setEnabled(true);
        }
        else{
            ipHostTF.setEnabled(false);
            usuarioHostTF.setEnabled(false);
            contrasenaHostTF.setEnabled(false);
        }
    }//GEN-LAST:event_editarConexionCBActionPerformed

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
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro().setVisible(true);
            }
        });
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField contrasenaHostTF;
    private javax.swing.JPasswordField contrasenaTextField;
    private javax.swing.JCheckBox editarConexionCB;
    private javax.swing.JPasswordField ipHostTF;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField usuarioHostTF;
    private javax.swing.JTextField usuarioTextField;
    // End of variables declaration//GEN-END:variables
}
