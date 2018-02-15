/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class RegistroDeViajeCompletado extends javax.swing.JFrame {

    /**
     * Creates new form NoSirve
     */
    
    String queryViajes = null;
    String[] columnasViajes = null;
    String IDcamion = null;
    String tipoCamion = null;
    String operadorCamion = null;
    String numeroDeViajes = null;
    
    
    
    
    public RegistroDeViajeCompletado() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Registro completo");
        
        try{
            String query = "Select  convert(varchar, viaje.FECHA, 106) as Fecha,  CONVERT(VARCHAR, VIAJE.HORA, 108) as Hora, TIPOCAMION.CAPACIDAD from VIAJE, CAMION, TIPOCAMION\n" +
                            " WHERE VIAJE.IDCAMION = " +Globales.ultimoCamion + "  and VIAJE.ESACTIVO = 1 and viaje.IDCAMION = camion.IDCAMION and camion.IDTIPOCAMION = TIPOCAMION.IDTIPOCAMION and\n" +
                            "convert(varchar, viaje.FECHA, 106) = convert(varchar, GETDATE(), 106)";
            String[] columnas = {"Fecha","Hora", "CAPACIDAD"};
            
            queryViajes = query;
            columnasViajes = columnas;
            
            boolean  bandera = Globales.bdTemp.insertarEnTabla( query,columnas, tabla);
            
            if(!bandera){
                throw new NoConectionDataBaseException("Tu registro fue guardado pero por el momento no se puede mostrar la tabla: " + Globales.bdTemp.getUltimoError());
            }
            
            query ="SELECT CAMION.IDCAMION, TIPOCAMION.DESCRIPCION, CAMION.OPERADOR \n" +
                    "FROM CAMION, TIPOCAMION\n" +
                    "WHERE CAMION.IDCAMION = " +Globales.ultimoCamion + " AND CAMION.IDTIPOCAMION = TIPOCAMION.IDTIPOCAMION " ;
            String[] columnasDescripcionGeneral = {"IDCAMION", "DESCRIPCION", "OPERADOR" };
            LinkedList<LinkedList<String>> datosCamion = Globales.bdTemp.select(query, columnasDescripcionGeneral);
            
            query = "select COUNT(*) AS CONTEOVIAJES FROM VIAJE WHERE VIAJE.IDCAMION = " +Globales.ultimoCamion + " AND convert(varchar, viaje.FECHA, 106) = convert(varchar, GETDATE(), 106)";
            String[] columnasConteo = {"CONTEOVIAJES"};
            LinkedList<LinkedList<String>> conteoViajes = Globales.bdTemp.select(query, columnasConteo);
            
            if(datosCamion == null || conteoViajes==null){
                throw new NoConectionDataBaseException("Tu registro fue guardado pero por el momento no se puede mostrar la información de los registros: " + Globales.bdTemp.getUltimoError());
            }
            else if(datosCamion.size() == 0 || conteoViajes.size() ==0 ||datosCamion.get(0).size() == 0 || conteoViajes.get(0).size()==0){
                throw new NoConectionDataBaseException("Tu registro fue guardado pero por el momento no se puede desplegar la información de los registros");
            }
            
            camionLB.setText(datosCamion.get(0).get(0));
            tipoCamionLB.setText(datosCamion.get(1).get(0));
            operadorCamionLB.setText(datosCamion.get(2).get(0));
            viajesLB.setText(conteoViajes.get(0).get(0));
            
            IDcamion = datosCamion.get(0).get(0);
            tipoCamion = datosCamion.get(1).get(0);
            operadorCamion = datosCamion.get(2).get(0);
            numeroDeViajes = conteoViajes.get(0).get(0);
                  
            
            
            
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        conteoCambioDePantalla();
    }
    static int conteoSiguientePantalla = 11;
    static boolean seguirContando = true;
    public  void conteoCambioDePantalla(){
        
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if(seguirContando){
                    conteoSiguientePantalla--;
                }
                
                //System.out.println("el reloj va: " + Integer.toString(conteoSiguientePantalla));
                if (conteoSiguientePantalla == 0) {
                    continuarBActionPerformed(null);
                    exec.shutdown();
                }
            }
        };

        
        exec.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
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
        camionLB = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        viajesLB = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tipoCamionLB = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        operadorCamionLB = new javax.swing.JLabel();
        continuarB = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Fecha", "Hora", "Cantidad (M3)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setEnabled(false);
        jScrollPane1.setViewportView(tabla);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel1.setText("Tu viaje se ha registrado correctamente");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel2.setText("Camión:");

        camionLB.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        camionLB.setText(" ");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel4.setText("Viajes del día:");

        viajesLB.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        viajesLB.setText(" ");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel6.setText("Tipo:");

        tipoCamionLB.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        tipoCamionLB.setText(" ");

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel10.setText("Operador:");

        operadorCamionLB.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        operadorCamionLB.setText(" ");

        continuarB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/ContinuarAzul.png"))); // NOI18N
        continuarB.setContentAreaFilled(false);
        continuarB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        continuarB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/ContinuarAzul2.png"))); // NOI18N
        continuarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarBActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/terminarJornada.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/Assets/terminarJornada2.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(camionLB, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(90, 90, 90)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tipoCamionLB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(operadorCamionLB, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(continuarB, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(viajesLB, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(camionLB))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(tipoCamionLB)
                        .addComponent(jLabel10)
                        .addComponent(operadorCamionLB)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(viajesLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(continuarB, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void continuarBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarBActionPerformed
        RegistrarViajes nuevoViaje = new RegistrarViajes();
        nuevoViaje.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_continuarBActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        seguirContando = false;
        Object[] options = {"Si",
                    "No"};
        int n = JOptionPane.showOptionDialog(this,
            "¿Deseas terminar la jornada del día?",
            "Terminar jornada",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]); //default button title
        //System.out.println("tu eleccion fue; " + Integer.toString(n));
        if(n == 0){
            ImprimirTicketViaje ticket = new ImprimirTicketViaje(queryViajes,
    columnasViajes,
    IDcamion,
    tipoCamion,
    operadorCamion,
    numeroDeViajes);
            ticket.setVisible(true);
            JOptionPane.showMessageDialog(this, "Felicidades, por favor recoje tu ticket");
            continuarBActionPerformed(null);
        }
        else{
            seguirContando = true;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroDeViajeCompletado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroDeViajeCompletado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroDeViajeCompletado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroDeViajeCompletado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroDeViajeCompletado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel camionLB;
    private javax.swing.JButton continuarB;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel operadorCamionLB;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tipoCamionLB;
    private javax.swing.JLabel viajesLB;
    // End of variables declaration//GEN-END:variables
}