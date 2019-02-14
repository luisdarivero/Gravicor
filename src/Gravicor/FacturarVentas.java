/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
    private JTable tableVentaFantasma = null; //se guarda la tabla que equivale a ventas fantasmas
    private LinkedList<String> datos = null; //lista de correspondencias a buscar
    private int indexOfDatos = 0; //guarda el index sobre el dato que estas revisando
    
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
        String[] titulosColumnas = {"Seleccionar","Referencia","Venta ID","Cliente","Planta","Precio M3","Cantidad M3","Precio Final"};
        Integer[] coordenadasTabla = {70,80,800,200};
        this.table= checkBoxTable(titulosColumnas,coordenadasTabla);
        String[] temp = {"hola","hola"};
        insertartarEnTabla(table, temp);
        insertartarEnTabla(table, temp);
        generarDatosTabla(this.indexOfDatos, this.datos, this.table);
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
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Facturar");

        jButton2.setText("Cancelar");

        jButton3.setText("Siguiente");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(287, 287, 287))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // boton que da a siguiente
        getSelectedOptions(table);
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}
