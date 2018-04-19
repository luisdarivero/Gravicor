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
public class EditarPlanta extends javax.swing.JFrame {

    private Integer plantaAEditar = 1;
   
    
    public void setPlantaAEditar(Integer planta){
        this.plantaAEditar = planta;
        cargarDatosPlanta();
    }
    
    private void cargarDatosPlanta(){
        try{
            String query = "SELECT P.PLANTAID, P.NOMBREPLANTA, T.DESCRIPCION FROM PLANTA AS P, TIPOPLANTA AS T \n" +
                            "WHERE P.PLANTAID = "+this.plantaAEditar+" AND P.TIPOPLANTAID = T.TIPOPLANTAID";
            String[] columnas = {"PLANTAID", "NOMBREPLANTA", "DESCRIPCION"};
            LinkedList<LinkedList<String>> infoPlanta = Globales.bdTemp.select(query, columnas);
            
            if(infoPlanta == null || infoPlanta.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: " +  Globales.bdTemp.getUltimoError());
            }
            
            idPlantaTB.setText(infoPlanta.get(0).get(0));
            nombreTF.setText(infoPlanta.get(1).get(0));
            tipoCB.setSelectedItem(infoPlanta.get(2).get(0));
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    /**
     * Creates new form anadirPlanta
     */
    public EditarPlanta() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Editar planta");
        
        try{
            //empieza carga del box de opciones - tipos de planta
            String query = "SELECT TIPOPLANTA.DESCRIPCION\n" +
                        "FROM TIPOPLANTA";
            String[] columnasDescripcion = {"DESCRIPCION"};
            
            LinkedList<LinkedList<String>> descripcionPlantas = Globales.bdTemp.select(query, columnasDescripcion);
            if(descripcionPlantas == null || descripcionPlantas.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: " +  Globales.bdTemp.getUltimoError());
            }
            
            String[] modeloDescripcion = new String[descripcionPlantas.get(0).size()];
            for(int i = 0; i<descripcionPlantas.get(0).size(); i++){
                modeloDescripcion[i] = descripcionPlantas.get(0).get(i);
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(modeloDescripcion);
            tipoCB.setModel(model);
            //termina la carga de tipos de planta
            cargarDatosPlanta();
            
            //empieza la carga de información de la planta
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
        idPlantaTB = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nombre:");

        jLabel2.setText("Tipo de planta:");

        tipoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cancelarB.setText("Cancelar");
        cancelarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBActionPerformed(evt);
            }
        });

        aceptarB.setText("Aceptar");
        aceptarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarBActionPerformed(evt);
            }
        });

        jLabel3.setText("ID planta:");

        idPlantaTB.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombreTF)
                            .addComponent(tipoCB, 0, 180, Short.MAX_VALUE)
                            .addComponent(idPlantaTB)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(cancelarB)
                        .addGap(137, 137, 137)
                        .addComponent(aceptarB)))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(idPlantaTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tipoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarB)
                    .addComponent(aceptarB))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            if(idPlantaTB.getText().length() < 1){
                throw new NoTypeRequiredException("No hay un ID de planta seleccionado");
            }
            if(nombreTF.getText().length() <4 || nombreTF.getText().length() > 50){
                throw new NoTypeRequiredException("El nombre debe contener entre 4 y 50 caracteres");
            }
            
            
            String query = "SELECT TIPOPLANTA.TIPOPLANTAID\n" +
                                "FROM TIPOPLANTA\n" +
                                "WHERE TIPOPLANTA.DESCRIPCION = '"+ (String)tipoCB.getSelectedItem()+"'";
            String[] columnas = {"TIPOPLANTAID"};
            LinkedList<LinkedList<String>> idTipoPlanta = Globales.bdTemp.select(query, columnas);
            if(idTipoPlanta == null || idTipoPlanta.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.bdTemp.getUltimoError());
            }
            Integer idTipoPlantaInt = Integer.parseInt(idTipoPlanta.get(0).get(0));
            
            query = "UPDATE PLANTA\n" +
                    "SET NOMBREPLANTA = '"+nombreTF.getText().toLowerCase()+"', TIPOPLANTAID = "+idTipoPlantaInt+"\n" +
                    "WHERE PLANTAID = "+this.plantaAEditar+" ";
            
            boolean res = Globales.bdTemp.update(query);
            if(res){
                GestionDePlantas gestionPlantas = new GestionDePlantas();
                gestionPlantas.setVisible(true);
                this.dispose();
            }
            else{
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "  + Globales.bdTemp.getUltimoError());
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
            java.util.logging.Logger.getLogger(EditarPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarPlanta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarB;
    private javax.swing.JButton cancelarB;
    private javax.swing.JTextField idPlantaTB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreTF;
    private javax.swing.JComboBox<String> tipoCB;
    // End of variables declaration//GEN-END:variables
}
