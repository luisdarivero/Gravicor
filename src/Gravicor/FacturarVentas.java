/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.TemporalQueries;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Daniel
 */
public class FacturarVentas extends javax.swing.JFrame {

    /**
     * Creates new form FacturarVentas
     */
    
    private JTable table = null; //se guarda la tabla que revisa la base de datos
    private LinkedList<String> datos = null; //lista de correspondencias a buscar
    private int indexOfDatos = 0; //guarda el index sobre el dato que estas revisando
    //listas que guardan los datos de las ventas o ventas fantasmas o total de la factura
    private String[] ventasID; //lista de ventas ID
    private Float[] precios;//lista de precios por m3
    private Integer[] cantidades;//lista de cantidades de m3
    private Double[] totalFactura;
    
    public FacturarVentas() {
        initComponents();
        
    }
    
    /*
    
    BEGIN TRAN @TransactionName  
    INSERT INTO ValueTable VALUES(1), (2);  
    ROLLBACK TRAN @TransactionName;  
    */
    
    //---------Metodo que se ejecuta desde una clase externa
    
    //---------Termina metodo que se ejecuta desde una clase externa
    public void programInnit(){
        try{
            String[] titulosColumnas = {"Seleccionar","Referencia","Venta ID","Cliente","Planta","Precio M3","Cantidad M3","Precio Final"};
            Integer[] coordenadasTabla = {70,120,800,220};
            this.table= checkBoxTable(titulosColumnas,coordenadasTabla);
            String[] temp = {"hola","hola"};
            insertartarEnTabla(table, temp);
            insertartarEnTabla(table, temp);
            generarDatosTabla(this.indexOfDatos, this.datos, this.table);
            //se inicializan las listas
            this.ventasID = new String[this.datos.size()];
            this.precios = new Float[this.datos.size()];
            this.cantidades = new Integer[this.datos.size()];
            this.totalFactura = new Double[this.datos.size()];
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage() , "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    //-------Metodo que identifica si una cadena es entero
    public boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    //-------termina metodo que identifica si una cadena es entero
    
    //--------Metodo que borra una tabla
    private void limpiarTabla(JTable tabla){
        DefaultTableModel dtm = (DefaultTableModel)tabla.getModel(); 
        dtm.setRowCount(0);
        
    }
    //--------Termina metodo que borra una tabla
    //--------Metodo para generar los datos de las tablas
    public boolean generarDatosTabla(int indexOfDatos, LinkedList<String> datos, JTable tabla){
        String dato = datos.get(indexOfDatos);
        limpiarTabla(tabla);
        String query;
        String[] columnas = {"VENTAID", "NOMBRECLIENTE", "NOMBREPLANTA","PRECIOM3", "CANTIDADM3", "PRECIOFINAL"};
      
        try{
            //se revisa si el dato es un entero
            //Se consulta la base de datos para los ID de ventas 
            if(isNumeric(dato)){
                query = "SELECT V.VENTAID, C.NOMBRECLIENTE, P.NOMBREPLANTA,V.PRECIOM3, V.CANTIDADM3, (V.PRECIOM3 * V.CANTIDADM3) AS PRECIOFINAL\n" +
                                "FROM VENTA AS V, CLIENTE AS C, PLANTA AS P\n" +
                                    "WHERE V.VENTAID = " + dato.toLowerCase() + " AND V.CLIENTEID = C.CLIENTEID AND V.PLANTAID = P.PLANTAID AND V.ACTIVO = 1 AND V.ESFACTURADO = 'FALSE'";
                
                LinkedList<LinkedList<String>> ventasID = Globales.baseDatos.select(query, columnas);
                if(ventasID == null){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                }
                if(ventasID.get(0).size() >= 1){
                    String[] registrosEncontrados = new String[ventasID.size()+1];
                    registrosEncontrados[0] = dato;
                    for(int i = 1; i <= ventasID.size(); i++){
                        registrosEncontrados[i] = ventasID.get(i-1).getFirst();
                    }
                    insertartarEnTabla(tabla, registrosEncontrados);
                }
                
                
            }

            //se consulta la base de datos para los folios de transportista
            query = "SELECT V.VENTAID, C.NOMBRECLIENTE, P.NOMBREPLANTA,V.PRECIOM3, V.CANTIDADM3, (V.PRECIOM3 * V.CANTIDADM3) AS PRECIOFINAL\n" +
                        "FROM VENTA AS V, CLIENTE AS C, PLANTA AS P\n" +
                            "WHERE V.FOLIOTRANSPORTISTA = '"+ dato.toLowerCase() + "' AND V.CLIENTEID = C.CLIENTEID AND V.PLANTAID = P.PLANTAID AND V.ACTIVO = 1 AND V.ESFACTURADO = 'FALSE'";
            
            LinkedList<LinkedList<String>> foliosTransportista = Globales.baseDatos.select(query, columnas);
            if(foliosTransportista == null){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                }
            for(int i = 0; i< foliosTransportista.get(0).size(); i++){
                String[] registrosEncontrados = new String[foliosTransportista.size()+1];
                registrosEncontrados[0] = dato;
                for(int x = 1; x <= foliosTransportista.size(); x++){
                    registrosEncontrados[x] = foliosTransportista.get(x-1).get(i);
                }
                insertartarEnTabla(tabla, registrosEncontrados);
            }
            
            //Se consulta el folio de la planta
            query = "SELECT V.VENTAID, C.NOMBRECLIENTE, P.NOMBREPLANTA,V.PRECIOM3, V.CANTIDADM3, (V.PRECIOM3 * V.CANTIDADM3) AS PRECIOFINAL\n" +
                        "FROM VENTA AS V, CLIENTE AS C, PLANTA AS P\n" +
                            "WHERE V.FOLIOPLANTA = '"+ dato.toLowerCase() + "' AND V.CLIENTEID = C.CLIENTEID AND V.PLANTAID = P.PLANTAID AND V.ACTIVO = 1 AND V.ESFACTURADO = 'FALSE'";
            
            LinkedList<LinkedList<String>> foliosPlantas = Globales.baseDatos.select(query, columnas);
            if(foliosPlantas == null){
                    throw new NoConectionDataBaseException("Error al conectar a la base de datos: " + Globales.baseDatos.getUltimoError());
                }
            for(int i = 0; i< foliosPlantas.get(0).size(); i++){
                String[] registrosEncontrados = new String[foliosPlantas.size()+1];
                registrosEncontrados[0] = dato;
                for(int x = 1; x <= foliosPlantas.size(); x++){
                    registrosEncontrados[x] = foliosPlantas.get(x-1).get(i);
                }
                insertartarEnTabla(tabla, registrosEncontrados);
            }
        }
        catch(NoConectionDataBaseException e){
            limpiarTabla(tabla);
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
        return true;
    }
    //--------Termina metodo para generar los datos de las tablas
   
    //---------Metodo set para almacenar los datos
    public void setDatos(LinkedList<String> datos){
        this.datos = datos;
    }
    //---------Termina metodo set para almacenar los datos
    
    //---------codigo para añadir una nueva fila
    public void insertartarEnTabla(JTable tabla,String[] datos){
        DefaultTableModel model = (DefaultTableModel)tabla.getModel();
        model.addRow(new Object[0]);
        int rowscount = model.getRowCount();
        model.setValueAt(false,rowscount-1,0);
        for(int i = 0; i < datos.length; i++){
            model.setValueAt(datos[i], rowscount-1, i+1);
        }
    }
    //---------termina codigo para añadir una nueva fila
    
    //---------Codigo para identificar las opciones que están seleccionadas
    public Integer[] getSelectedOptions(JTable tabla){
        //se crea la lista que se va a regresar
        Integer[] lista = new Integer[tabla.getRowCount()];
        for(int i=0;i< lista.length; i++){
            lista[i] = 0;
        }
        //Se revisan todas las opciones
        for(int i=0;i<tabla.getRowCount();i++){
            Boolean checked=Boolean.valueOf(tabla.getValueAt(i, 0).toString());
            String col=tabla.getValueAt(i, 1).toString();
            //DISPLAY
            if(checked){
              lista[i] = 1;
            }
          }
        for(Integer x: lista){
            System.out.println(x);
        }
        return lista;
    }
    //---------Codigo para identificar las opciones que están seleccionadas
    
    //---------codigo para hacer una tabla con checkbox al inicio
    public JTable checkBoxTable(String[] titulosColumnas,Integer[] coordenadas){
      
      getContentPane().setLayout(null);
      //ADD SCROLLPANE
      JScrollPane scroll=new JScrollPane();
      scroll.setBounds(coordenadas[0],coordenadas[1],coordenadas[2],coordenadas[3]);//tiene que ser de mínimo 4 datos
      getContentPane().add(scroll);
      //THE TABLE
      JTable table=new JTable();
      scroll.setViewportView(table);
      //THE MODEL OF OUR TABLE
      DefaultTableModel model=new DefaultTableModel()
      {
        public Class<?> getColumnClass(int column)
        {
          switch(column){
          case 0:
            return Boolean.class;
          default:
            return String.class;
          }
        }
        //inicia
        @Override
        public boolean isCellEditable(int row, int col) {
             switch (col) {
                 case 0:
                     return true;
                 default:
                     return false;
              }
        }
        //acaba
      };

      //ASSIGN THE MODEL TO TABLE
      table.setModel(model);
      
      for(String titulo: titulosColumnas){
          model.addColumn(titulo);
      }
     
      return table;
    }
    //---------Termina codigo para hacer una table con checkbox

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        siguienteB = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        registrosBDCB = new javax.swing.JCheckBox();
        registroNuevoCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spinnerPrecio = new javax.swing.JSpinner();
        spinnerCantidad = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        totalNuevoRegistroTB = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalFacturaTB = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Facturar");

        jButton2.setText("Cancelar");

        siguienteB.setText("Siguiente");
        siguienteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteBActionPerformed(evt);
            }
        });

        jButton4.setText("Anterior");
        jButton4.setEnabled(false);

        registrosBDCB.setSelected(true);
        registrosBDCB.setText("Registros de la base de datos");
        registrosBDCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrosBDCBActionPerformed(evt);
            }
        });

        registroNuevoCB.setText("Crear registro nuevo");
        registroNuevoCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroNuevoCBActionPerformed(evt);
            }
        });

        jLabel1.setText("Precio por M3:");

        jLabel2.setText("Cantidad de M3:");

        spinnerPrecio.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10000.0f), Float.valueOf(0.1f)));
        spinnerPrecio.setToolTipText("");
        spinnerPrecio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerPrecioStateChanged(evt);
            }
        });
        spinnerPrecio.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                spinnerPrecioInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        spinnerPrecio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spinnerPrecioPropertyChange(evt);
            }
        });

        spinnerCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        spinnerCantidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCantidadStateChanged(evt);
            }
        });
        spinnerCantidad.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spinnerCantidadPropertyChange(evt);
            }
        });

        jLabel3.setText("Total nuevo registro:");

        totalNuevoRegistroTB.setText("0.0");

        jLabel4.setText("Total de factura:");

        totalFacturaTB.setText("0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(82, 82, 82)
                        .addComponent(jButton2)
                        .addGap(161, 161, 161)
                        .addComponent(jButton1)
                        .addGap(76, 76, 76)
                        .addComponent(siguienteB))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(33, 33, 33)
                                .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addComponent(spinnerCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(totalNuevoRegistroTB, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(136, 136, 136))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registroNuevoCB)
                            .addComponent(registrosBDCB)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(totalFacturaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(totalFacturaTB))
                .addGap(33, 33, 33)
                .addComponent(registrosBDCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(registroNuevoCB)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(spinnerPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalNuevoRegistroTB))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(siguienteB)
                    .addComponent(jButton4))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void siguienteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteBActionPerformed
        // boton que da a siguiente
        try{
            
            if(registrosBDCB.isSelected()){
                //esta seleccionado el cuadro de venta normal
            }
            else if(registroNuevoCB.isSelected()){
                //debe considerar que los datos de ingreso no son correctos
                if((float)spinnerPrecio.getValue() == 0 || (int)spinnerCantidad.getValue() == 0){
                    throw new NoTypeRequiredException("No has especificado el valor de la factura");
                }
                //esta seleccionada la opcion de venta nueva
                this.precios [indexOfDatos] = (float)spinnerPrecio.getValue();
                this.cantidades[indexOfDatos] = (int)spinnerCantidad.getValue();
                this.totalFactura[indexOfDatos] = new Double(((float) spinnerPrecio.getValue() * new Float(((int)spinnerCantidad.getValue()))));
                organizarPantalla(this.table);
                calcularTotalFactura(this.totalFactura);
                //bloquea el botón de siguiente si es el ultimo elemento
                
                if(this.indexOfDatos >= this.datos.size()-2){//se asume que nunca se llegara a un error por que el boton se apaga
                    siguienteB.setEnabled(false);
                }
                
                //se calcula la nueva tabla
                this.indexOfDatos++;
                generarDatosTabla(this.indexOfDatos, this.datos, this.table);
                
            }
            else{
                //no hay un checkbox seleccionado
                JOptionPane.showMessageDialog(this, "Por favor selecciona un elemento para continuar" , "no hay un elemento seleccionado", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, "Debes seleccionar un valores diferentes de cero para el precio por M3 y para la cantidad en M3" , "Datos incompletos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_siguienteBActionPerformed
    
    private void calcularTotalFactura(Double[] lista){
        Double inicio = 0.0;
        for (Double x: lista){
            if(x!=null){
                inicio += x;
            }
        }
        totalFacturaTB.setText(inicio.toString());
    }
    private void organizarPantalla(JTable tabla){
        limpiarTabla(tabla);
        spinnerCantidad.setValue((int)0);
        spinnerPrecio.setValue((float)0);
        totalNuevoRegistroTB.setText("0.0");
        registrosBDCB.setSelected(true);
        registroNuevoCB.setSelected(false);
    }
    private void registrosBDCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrosBDCBActionPerformed
        // TODO add your handling code here:
        if(registrosBDCB.isSelected() == true){
            registroNuevoCB.setSelected(false);
        }
    }//GEN-LAST:event_registrosBDCBActionPerformed

    private void spinnerPrecioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerPrecioStateChanged
        // TODO add your handling code here:
        
        Float total = (float)spinnerPrecio.getValue() * new Float(((int)spinnerCantidad.getValue()));
        totalNuevoRegistroTB.setText(total.toString());
        
        
    }//GEN-LAST:event_spinnerPrecioStateChanged

    private void spinnerCantidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCantidadStateChanged
        // TODO add your handling code here:
        Float total = (float)spinnerPrecio.getValue() * new Float(((int)spinnerCantidad.getValue()));
        totalNuevoRegistroTB.setText(total.toString());
    }//GEN-LAST:event_spinnerCantidadStateChanged

    private void registroNuevoCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroNuevoCBActionPerformed
        // TODO add your handling code here:
        if(registroNuevoCB.isSelected() == true){
            registrosBDCB.setSelected(false);
        }
    }//GEN-LAST:event_registroNuevoCBActionPerformed

    private void spinnerPrecioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spinnerPrecioPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_spinnerPrecioPropertyChange

    private void spinnerCantidadPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spinnerCantidadPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_spinnerCantidadPropertyChange

    private void spinnerPrecioInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_spinnerPrecioInputMethodTextChanged
        // TODO add your handling code here:
        Float total = (float)spinnerPrecio.getValue() * new Float(((int)spinnerCantidad.getValue()));
        totalNuevoRegistroTB.setText(total.toString());
    }//GEN-LAST:event_spinnerPrecioInputMethodTextChanged

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
            java.util.logging.Logger.getLogger(FacturarVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturarVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturarVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturarVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturarVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox registroNuevoCB;
    private javax.swing.JCheckBox registrosBDCB;
    private javax.swing.JButton siguienteB;
    private javax.swing.JSpinner spinnerCantidad;
    private javax.swing.JSpinner spinnerPrecio;
    private javax.swing.JLabel totalFacturaTB;
    private javax.swing.JLabel totalNuevoRegistroTB;
    // End of variables declaration//GEN-END:variables
}
