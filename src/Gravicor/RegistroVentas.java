/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Daniel
 */
public class RegistroVentas extends javax.swing.JFrame {

    /**
     * Creates new form RegistroVentas
     */
    public RegistroVentas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Registrar Ventas");
        
        
        try{
            SpinnerModel model =new SpinnerNumberModel(0, //initial value
                                   0, //min
                                   100, //max
                                   1);
            cantidadS.setModel(model);
            //informacion general (folio venta y fecha)
            String query = "SELECT convert(varchar, getdate(), 106) AS FECHA, MAX(VENTA.VENTAID) AS IDMAX FROM VENTA";
            String[] columnas = {"FECHA","IDMAX"};
            LinkedList<LinkedList<String>> datosGenerales = Globales.baseDatos.select(query,columnas);

            if(datosGenerales == null || datosGenerales.size() <1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                                                            + Globales.baseDatos.getUltimoError());
            }
            
            fechaTB.setText(datosGenerales.get(0).get(0));
            //numeroVentaL.setText(numeroVentaL.getText() + (Integer.parseInt(datosGenerales.get(1).get(0)) + 1));
            
            //llenar combo boxes----
            query = "SELECT C.NOMBRECLIENTE FROM CLIENTE AS C";
            String[] columnasCliente = {"NOMBRECLIENTE"};
            LinkedList<LinkedList<String>> datosClientes = Globales.baseDatos.select(query,columnasCliente);
            if(datosClientes == null || datosClientes.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                                                            + Globales.baseDatos.getUltimoError());
            }
            String[] modeloDescripcion = new String[datosClientes.get(0).size()];
            for(int i = 0; i<datosClientes.get(0).size(); i++){
                modeloDescripcion[i] = datosClientes.get(0).get(i);
            }
            
            DefaultComboBoxModel clientesModel = new DefaultComboBoxModel(modeloDescripcion);
            clienteCB.setModel(clientesModel);
            //--PARTE DE LOS MATERIALES
            query = "SELECT M.DESCRIPCIONMATERIAL FROM MATERIAL AS M";
            String[] columnasMaterial = {"DESCRIPCIONMATERIAL"};
            LinkedList<LinkedList<String>> datosMaterial = Globales.baseDatos.select(query,columnasMaterial);
            if(datosMaterial == null || datosMaterial.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                                                            + Globales.baseDatos.getUltimoError());
            }
            String[] modeloMaterial = new String[datosMaterial.get(0).size()];
            for(int i = 0; i<datosMaterial.get(0).size(); i++){
                modeloMaterial[i] = datosMaterial.get(0).get(i);
            }
            
            DefaultComboBoxModel materialModel = new DefaultComboBoxModel(modeloMaterial);
            materialCB.setModel(materialModel);
            
            //--parte de las plantas
            query = "SELECT P.NOMBREPLANTA FROM PLANTA AS P";
            String[] columnasPlanta = {"NOMBREPLANTA"};
            LinkedList<LinkedList<String>> datosPlanta = Globales.baseDatos.select(query,columnasPlanta);
            if(datosPlanta == null || datosPlanta.size() < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                                                            + Globales.baseDatos.getUltimoError());
            }
            String[] modeloPlanta = new String[datosPlanta.get(0).size()];
            for(int i = 0; i<datosPlanta.get(0).size(); i++){
                modeloPlanta[i] = datosPlanta.get(0).get(i);
            }
            
            DefaultComboBoxModel plantaModel = new DefaultComboBoxModel(modeloPlanta);
            plantaCB.setModel(plantaModel);
            
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    private Float actualizarPrecio(){
        String nombreCliente = (String)clienteCB.getSelectedItem();
        String nombreMaterial = (String)materialCB.getSelectedItem();
        
        if(nombreCliente == null || nombreCliente.equals("")
                || nombreMaterial == null || nombreMaterial.equals("")){
            return (float)-1;
        }
        
        int cantidadMaterial = (Integer) cantidadS.getValue();
        
        Float precio = Globales.baseDatos.obtenerPrecioClienteMaterial(nombreCliente, nombreMaterial);
        Float resultado = precio;
        
        if(precio < 0){
            JOptionPane.showMessageDialog(this, "Error al calcular el precio de la venta: " + Globales.baseDatos.getUltimoError(), 
                    "Error al calcular el precio de la venta", JOptionPane.ERROR_MESSAGE);
            return (float)-1;
        }
        
        precio = precio * cantidadMaterial;
        
        montoTF.setText("$ " + precio.toString());
        return resultado;
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
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        clienteCB = new javax.swing.JComboBox<>();
        pagoCB = new javax.swing.JComboBox<>();
        materialCB = new javax.swing.JComboBox<>();
        folioTransportistaTF = new javax.swing.JTextField();
        nombreChoferTF = new javax.swing.JTextField();
        matri = new javax.swing.JTextField();
        plantaCB = new javax.swing.JComboBox<>();
        continuarB = new javax.swing.JButton();
        numeroVentaL = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        fechaTB = new javax.swing.JTextField();
        cantidadS = new javax.swing.JSpinner();
        montoTF = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        folioPlantaTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(249, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/regresar.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/regresar2.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        clienteCB.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        clienteCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteCBActionPerformed(evt);
            }
        });

        pagoCB.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        pagoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Efectivo" }));
        pagoCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagoCBActionPerformed(evt);
            }
        });

        materialCB.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        materialCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialCBActionPerformed(evt);
            }
        });

        folioTransportistaTF.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        folioTransportistaTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folioTransportistaTFActionPerformed(evt);
            }
        });

        nombreChoferTF.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N

        matri.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N

        plantaCB.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N

        continuarB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/continuar.png"))); // NOI18N
        continuarB.setContentAreaFilled(false);
        continuarB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        continuarB.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/continuar2.png"))); // NOI18N
        continuarB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarBActionPerformed(evt);
            }
        });

        numeroVentaL.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        numeroVentaL.setText("Registrar Venta");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel2.setText("Cliente:");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel3.setText("Pago:");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel4.setText("Monto:");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel5.setText("Material:");

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel7.setText("Cantidad(M3):");

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel8.setText("Folio Transportista:");

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel9.setText("Matrícula camión:");

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel10.setText("Nombre chofer:");

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel11.setText("Planta Productora:");

        fechaTB.setEditable(false);
        fechaTB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cantidadS.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        cantidadS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cantidadSStateChanged(evt);
            }
        });

        montoTF.setEditable(false);
        montoTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        montoTF.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel1.setText("Folio planta productora:");

        folioPlantaTF.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gravicor/AssetsNuevos/encabezadoLogoGravicor.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(folioTransportistaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(materialCB, javax.swing.GroupLayout.Alignment.LEADING, 0, 375, Short.MAX_VALUE)
                                        .addComponent(clienteCB, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(nombreChoferTF, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(folioPlantaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel4)))
                            .addComponent(continuarB, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pagoCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cantidadS)
                            .addComponent(matri)
                            .addComponent(plantaCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(montoTF)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(40, 40, 40))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(229, 229, 229)
                                .addComponent(numeroVentaL, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(fechaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(90, 90, 90))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(fechaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(numeroVentaL)))
                        .addGap(19, 19, 19)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clienteCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(materialCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(cantidadS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(folioTransportistaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(matri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreChoferTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(plantaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(montoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(folioPlantaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(continuarB)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void cantidadSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cantidadSStateChanged
        // TODO add your handling code here:
        actualizarPrecio();
    }//GEN-LAST:event_cantidadSStateChanged

    private void continuarBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarBActionPerformed
        // validar todos los datos para meterlos al sistema
        continuarB.setEnabled(false);
        try{
            Float precioClienteMaterial = (float)0;
            try{
                precioClienteMaterial = actualizarPrecio();
                if(precioClienteMaterial < 0){
                    throw new Exception();
                }
            }
            catch(Exception e){
                throw new NoTypeRequiredException("Error al calcular el precio de la venta");
            }

            int cantidadM3 = (int)cantidadS.getValue();

            if(cantidadM3 <1){
                throw new NoTypeRequiredException("No hay un valor correcto designado para el campo 'Cantidad(M3)'");
            }
            if(folioTransportistaTF.getText().equals("") || folioTransportistaTF.getText().length() < 3 ||
                folioTransportistaTF.getText().length() > 50){
                throw new NoTypeRequiredException("No hay un valor correcto designado para el campo 'Folio Transportista'");
            }
            if(matri.getText().equals("") || matri.getText().length() < 3 || matri.getText().length() > 50){
                throw new NoTypeRequiredException("No hay un valor correcto designado para el campo 'Matrícula Camión'");
            }
            if(nombreChoferTF.getText().equals("") || nombreChoferTF.getText().length() < 4 || nombreChoferTF.getText().length() > 50){
                throw new NoTypeRequiredException("No hay un valor correcto designado para el campo 'Nombre Chofer'");
            }

            Integer idCliente = Globales.baseDatos.obtenerClienteID((String)clienteCB.getSelectedItem());
            //System.out.println(nombreCliente + " = " + idCliente);
            if(idCliente < 0){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                    + Globales.baseDatos.getUltimoError());
            }
            Integer idMaterial = Globales.baseDatos.obtenerMaterialID((String)materialCB.getSelectedItem());
            //System.out.println(nombreMaterial + " = " + idMaterial);

            if(idMaterial < 0){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                    + Globales.baseDatos.getUltimoError());
            }

            Float monto = (float)0;
            try{
                monto = Float.parseFloat(montoTF.getText().replace("$", ""));
                if(monto<0){
                    throw new Exception("El monto calculado fue negativo");
                }
            }
            catch(Exception e){
                throw new NoTypeRequiredException("No se ha calculado correctamente el campo 'Monto': " + e.getMessage());
            }

            Integer idPlanta = Globales.baseDatos.obtenerPlantaId((String)plantaCB.getSelectedItem());
            if(idPlanta <0){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                    + Globales.baseDatos.getUltimoError());
            }
            String tipoPago = (String)pagoCB.getSelectedItem();
            boolean esCredito = true;
            if(tipoPago.equals("Efectivo")){
                esCredito = false;
                //System.out.println("esEfectivo");
            }
            //aqui se terminan de validar los imputs y se realiza el insert

            int validacion = Globales.baseDatos.insertarVenta(idCliente, idMaterial, folioTransportistaTF.getText().toLowerCase(),
                matri.getText().toLowerCase(), nombreChoferTF.getText().toLowerCase(), idPlanta,
                Globales.baseDatos.obtenerUsuarioID(Globales.currentUser), 0, precioClienteMaterial, cantidadM3,
                folioPlantaTF.getText().toLowerCase(),esCredito);

            if(validacion < 1){
                throw new NoConectionDataBaseException("Error al conectar con la base de datos: "
                    + Globales.baseDatos.getUltimoError());
            }

            JOptionPane.showMessageDialog(this,"El registro se ha guardado con éxito","El registro se ha guardado con éxito",JOptionPane.INFORMATION_MESSAGE);

            String nombreImpresora = new ConfiguracionConexionDB().getValueWithHash("NombreImpresora");

            PrintTiket myTiket = new PrintTiket(nombreImpresora,
                (new Integer(validacion)).toString(), PrintTiket.fontType.CONENCABEZADO);

            String textoTicket = myTiket.getTicketRegistroVentas((new Integer(validacion)).toString(),
                fechaTB.getText(), (String)clienteCB.getSelectedItem(), tipoPago,
                (String)materialCB.getSelectedItem(), (new Integer(cantidadM3)).toString(),
                folioTransportistaTF.getText().toLowerCase(), matri.getText().toLowerCase(),
                nombreChoferTF.getText().toLowerCase(), (String)plantaCB.getSelectedItem(),
                montoTF.getText());

            boolean validacionImpresion;

            validacionImpresion = myTiket.printMyTicket(textoTicket);

            if(!validacionImpresion){
                String mensaje ="Error al imprimir el ticket, por favor revisa que la impresora está conectada y  funcionando";
                JOptionPane.showMessageDialog(this, mensaje, "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
            validacionImpresion =  myTiket.printMyTicket(textoTicket);//se imprime doble
            if(!validacionImpresion){
                String mensaje = "Error al imprimir el ticket, por favor revisa que la impresora está conectada y  funcionando";
                JOptionPane.showMessageDialog(this, mensaje, "Error de formato", JOptionPane.ERROR_MESSAGE);
            }

            
            
            RegistroVentas nuevaVenta= new RegistroVentas();
            nuevaVenta.setVisible(true);
            this.dispose();

        }
        catch(NoTypeRequiredException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de formato", JOptionPane.ERROR_MESSAGE);
            continuarB.setEnabled(false);
        }
        catch(NoConectionDataBaseException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de conexión con la base de datos", JOptionPane.ERROR_MESSAGE);
            continuarB.setEnabled(false);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error inesperado del sistema", JOptionPane.ERROR_MESSAGE);
            continuarB.setEnabled(false);
        }

    }//GEN-LAST:event_continuarBActionPerformed

    private void folioTransportistaTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folioTransportistaTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folioTransportistaTFActionPerformed

    private void materialCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialCBActionPerformed
        actualizarPrecio();
    }//GEN-LAST:event_materialCBActionPerformed

    private void pagoCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagoCBActionPerformed

    }//GEN-LAST:event_pagoCBActionPerformed

    private void clienteCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteCBActionPerformed
        // TODO add your handling code here:
        actualizarPrecio();
    }//GEN-LAST:event_clienteCBActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GestionDeVentas ventas= new GestionDeVentas();
        ventas.setVisible(true);
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
            java.util.logging.Logger.getLogger(RegistroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner cantidadS;
    private javax.swing.JComboBox<String> clienteCB;
    private javax.swing.JButton continuarB;
    private javax.swing.JTextField fechaTB;
    private javax.swing.JTextField folioPlantaTF;
    private javax.swing.JTextField folioTransportistaTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JComboBox<String> materialCB;
    private javax.swing.JTextField matri;
    private javax.swing.JFormattedTextField montoTF;
    private javax.swing.JTextField nombreChoferTF;
    private javax.swing.JLabel numeroVentaL;
    private javax.swing.JComboBox<String> pagoCB;
    private javax.swing.JComboBox<String> plantaCB;
    // End of variables declaration//GEN-END:variables
}
