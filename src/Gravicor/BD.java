package Gravicor;
import java.io.FileWriter;
import java.sql.*;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class BD {
    private Connection conexion;
    private String direccionIP;
    private String puerto;
    private String nombreBD;
    private String usuarioBD;
    private String contrasenaBD;
    private boolean isConectado;
    private String ultimoError;

    public BD( String direccionIP, String puerto, String nombreBD, String usuarioBD, String contrasenaBD) {
        this.direccionIP = direccionIP;
        this.puerto = puerto;
        this.nombreBD = nombreBD;
        this.usuarioBD = usuarioBD;
        this.contrasenaBD = contrasenaBD;
        this.isConectado = conectarBD(generarURL());
        
        //https://www.thaicreate.com/java/java-sql-server-transaction.html  //prueba
    }
    
    public boolean conectarBD(String URL){
        try{
            if(this.conexion != null){
                this.conexion.close();
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conexion = DriverManager.getConnection(URL);
        }
        catch(Exception e){
            this.ultimoError = e.getMessage();
            return false;
        }
        
        return true;
    }
    
    public String generarURL(){
        return "jdbc:sqlserver://" + this.direccionIP + ":" + this.puerto + ";databaseName=" + this.nombreBD + ";user=" + this.usuarioBD + ";password=" + this.contrasenaBD + ";";
    }
    
    public LinkedList<LinkedList<String>> select(String query, String[] columnas){
        LinkedList<LinkedList<String>> resultado  = new LinkedList<LinkedList<String>>();
        if(columnas.length <=0){
            return resultado;
        }
        if (!(this.isConectado = conectarBD(generarURL()))){
            return null;
        }
        for(int i=0;i<columnas.length;i++){
            resultado.add(new LinkedList<String>());
        }
        try{
            Statement ejecutor = this.conexion.createStatement();
            ResultSet respuesta = ejecutor.executeQuery(query);
            
            
            while(respuesta.next()){
                //respuesta.getString("IDUsuario") + " " + respuesta.getString("contraseña")  + "\n";
                for(int i=0;i<columnas.length;i++){
                    resultado.get(i).add(respuesta.getString(columnas[i]));
                }
            }
        }
        catch(Exception e){
            this.ultimoError = e.getMessage();
            return null;
        }
        return resultado;
    }
    
    public boolean insertarEnTabla(String query,String[] columnas, JTable tabla){
        LinkedList<LinkedList<String>> lista = select(query, columnas);
        if(lista == null){
            return false;
        }
        if(lista.get(0).size() == 0){
            
            return true;
        }
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        //añade los datos solo de la primera fila
        for(int i=0;i<columnas.length;i++){
            model.setValueAt(lista.get(i).get(0), 0, i);
        }
        //si hay mas se ejecuta
        if(lista.get(0).size() > 1){
            for(int i = 1; i< lista.get(0).size(); i++){
                String[] cadena = new String[columnas.length];
                for(int j = 0; j<lista.size(); j++ ){
                    cadena[j] = lista.get(j).get(i);
                }
                model.addRow(cadena);
            }
        }
        //model.addRow(new Object[]{"Column 1", "Column 2", "Column 3", ""});
        
        return true;
    }
    
    public Object[][] selectO(String query,String[] columnas){
        LinkedList<LinkedList<String>> lista = select(query, columnas);
        
        if(lista == null){
            return null;
        }
        else if(lista.size() == 0 || lista.get(0).size() == 0){
            this.setUltimoError("No se encontraron datos para generar el reporte");
            return null;
        }
        else{
            
            Object[][] resultado = new Object [lista.size()][lista.get(0).size()];
            for(int i = 0; i < lista.size(); i++){
                for(int j = 0; j < lista.get(0).size(); j++){
                    resultado[i][j] = lista.get(i).get(j);
                }
            }
            
            return resultado;
        }
        
        
    }
    
    public boolean insert(String query){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return this.isConectado;
        }
        try{
            
            PreparedStatement declaracion =  conexion.prepareStatement(query);
            int registro = declaracion.executeUpdate();
            if(registro > 0 ){
                return true;
            }
        }
        catch(Exception e){
            this.ultimoError = e.getMessage();
            return false;
        }
        return false;
    }
        

    public boolean update(String query){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return this.isConectado;
        }
        try{
            
            PreparedStatement declaracion = conexion.prepareStatement(query);
            int resultado = declaracion.executeUpdate();
            if(resultado>0){
                return true;
            }
        }
        catch(Exception e){
            this.ultimoError = e.getMessage();
            return false;
        }
        return false;
    }
    
    public boolean editarCliente(int idCliente, float precioGravaMedio, float precioGravaTresCuartos, float precioArenilla ){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return false;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call EDITARCLIENTE(?,?,?,?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setInt(2, idCliente);
            proc_stmt.setFloat(3, precioGravaMedio);
            proc_stmt.setFloat(4, precioGravaTresCuartos);
            proc_stmt.setFloat(5, precioArenilla);  

            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn != -1 && queryReturn != 0 && queryReturn !=1){
             throw new Exception("Error ineserado, valor de retorno invalido de procedimiento 'EDITARCLIENTE'");   
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return false;
        }
        if(queryReturn == -1){
            this.setUltimoError("El ID del cliente especificado no existe");
            return false;
        }
        else if(queryReturn == 0){
            this.setUltimoError("Error inesperado en el procedimiento 'EDITARCLIENTE' al tratar de registrar los datos");
        }
        return true;
    }
    
    public boolean insertarCliente(String nombreCliente, float precioGravaMedio, float precioGravaTresCuartos, float precioArenilla){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return false;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call INSERTARCLIENTE(?,?,?,?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, nombreCliente);
            proc_stmt.setFloat(3, precioGravaMedio);
            proc_stmt.setFloat(4, precioGravaTresCuartos);
            proc_stmt.setFloat(5, precioArenilla);  
            

            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn != -1 && queryReturn != 0 && queryReturn !=1){
             throw new Exception("Error ineserado, valor de retorno invalido de procedimiento 'INSERTARCLIENTE'");   
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return false;
        }
        if(queryReturn == -1){
            this.setUltimoError("El nombre de cliente especificado ya existe, por favor seleccione otro");
            return false;
        }
        else if(queryReturn == 0){
            this.setUltimoError("Error inesperado en el procediiento 'INSERTARCLIENTE' al tratar de registrar los datos");
            return false;
        }
        return true;
    }
    
    public int esVentaCredito(String idVenta){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int  queryReturn;
        
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call ESVENTACREDITO(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, idVenta);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            
            
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -1;
        }
        
        return queryReturn;
    }
    
    public int esVentaFacturada(String idVenta){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int  queryReturn;
        
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call ESVENTAFACTURADA(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, idVenta);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            
            
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -1;
        }
        
        return queryReturn;
    }
    
    public int obtenerClienteID(String nombreCliente){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call OBTENERCLIENTE(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, nombreCliente);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn < -2){
                throw new Exception("Valor de retorno inesperado al ejecutar la instrucción 'OBTENERCLIENTE'");  
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -3;
        }
        if(queryReturn == -1){
            this.setUltimoError("No existe el registro insertado al ejecutar 'OBTENERCLIENTE': " + nombreCliente);
            return -2;
        }
        else if(queryReturn == -2){
            this.setUltimoError("Error inesperado en el procedimiento 'OBTENERCLIENTE' al tratar de buscar un dato");
            return -2;
        }
        return queryReturn;
    }
    
    public int obtenerMaterialID(String nombreMaterial){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call OBTENERMATERIAL(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, nombreMaterial);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn < -2){
                throw new Exception("Valor de retorno inesperado al ejecutar la instrucción 'OBTENERMATERIAL'");  
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -3;
        }
        if(queryReturn == -1){
            this.setUltimoError("No existe el registro insertado al ejecutar 'OBTENERMATERIAL': " + nombreMaterial);
            return -2;
        }
        else if(queryReturn == -2){
            this.setUltimoError("Error inesperado en el procedimiento 'OBTENERMATERIAL' al tratar de buscar un dato");
            return -2;
        }
        return queryReturn;
    }
    
    public int obtenerPlantaId(String nombrePlanta){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call OBTENERPLANTA(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, nombrePlanta);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn < -2){
                throw new Exception("Valor de retorno inesperado al ejecutar la instrucción 'OBTENERPLANTA'");  
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -3;
        }
        if(queryReturn == -1){
            this.setUltimoError("No existe el registro insertado al ejecutar 'OBTENERPLANTA': " + nombrePlanta);
            return -2;
        }
        else if(queryReturn == -2){
            this.setUltimoError("Error inesperado en el procedimiento 'OBTENERPLANTA' al tratar de buscar un dato");
            return -2;
        }
        return queryReturn;
    }
    
    public int obtenerUsuarioID(String username){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call OBTENERUSUARIO(?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setString(2, username);
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn < -2){
                throw new Exception("Valor de retorno inesperado al ejecutar la instrucción 'OBTENERUSUARIO'");  
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -3;
        }
        if(queryReturn == -1){
            this.setUltimoError("No existe el registro insertado al ejecutar 'OBTENERUSUARIO': " + username);
            return -1;
        }
        else if(queryReturn == -2){
            this.setUltimoError("Error inesperado en el procedimiento 'OBTENERUSUARIO' al tratar de buscar un dato");
            return -2;
        }
        return queryReturn;
    }
    
    public Float obtenerPrecioClienteMaterial(String nombreCliente, String nombreMaterial){
        Integer idCliente = obtenerClienteID(nombreCliente);
        
        if(idCliente < 0){
            return (float)-1.0;
        }
        Integer idMaterial = obtenerMaterialID(nombreMaterial);
       
        
        if(idMaterial < 0){
            return (float)-2.0;
        }
        
        String query = "SELECT P.PRECIO FROM PRECIO_CLIENTE_MATERIAL AS P WHERE P.CLIENTEID = "+idCliente+" AND P.MATERIALID = "+idMaterial;
        String[] columnas = {"PRECIO"};
        
        LinkedList<LinkedList<String>> precio = select(query,columnas);
        if(precio == null){
            return (float)-3;
        }
        if(precio.size() < 1){
            this.setUltimoError("Error inesperado al obtener los datos desde el query en el método 'obtenerPrecioClienteMaterial' de Java");
            return (float) -4;
        }
       
        Float resultado = (float)0.0;
        try{
            resultado = Float.parseFloat(precio.get(0).get(0));
        }
        catch(Exception e){
            this.setUltimoError("Error al convertir el tipo de dato a Flotante en el método 'obtenerPrecioClienteMaterial' de Java");
            return (float) -5;
        }
        
        return resultado;
    }
    
    public Integer insertarVenta(int clienteID, int materialID, String folioTransportista, String matriculaCamion, String nombreChofer,
            int plantaID, int usuarioID, float costoOperativoPlanta, float precioM3, float cantidadM3, String folioPlanta,boolean esCredito ){
        if (!(this.isConectado = conectarBD(generarURL()))){
            return -3;
        }
        int queryReturn;
        try{
            //se consulta el stored procedure
            Connection con = this.getConexion();
        
            CallableStatement proc_stmt = con.prepareCall("{? = call INSERTARVENTA(?,?,?,?,?,?,?,?,?,?,?,?) }");
            proc_stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            proc_stmt.setInt(2, clienteID);
            proc_stmt.setInt(3, materialID);
            proc_stmt.setString(4, folioTransportista);
            proc_stmt.setString(5, matriculaCamion);
            proc_stmt.setString(6, nombreChofer);
            proc_stmt.setInt(7, plantaID);
            proc_stmt.setInt(8, usuarioID);
            proc_stmt.setFloat(9, costoOperativoPlanta);
            proc_stmt.setFloat(10, precioM3);
            proc_stmt.setFloat(11, cantidadM3);
            proc_stmt.setString(12, folioPlanta);
            proc_stmt.setBoolean(13, esCredito);
            
            
            boolean rs = proc_stmt.execute();//regresar null si esta variable es falsa por que hay un error
            
            queryReturn = proc_stmt.getInt(1);
            if(queryReturn < -1){
                throw new Exception("Valor de retorno inesperado al ejecutar la instrucción 'OBTENERPLANTA'");  
            }
        }
        catch(Exception e){
            this.setUltimoError(e.getMessage());
            return -2;
        }
        if(queryReturn == -1){
            this.setUltimoError("Hubo un error al registrar la venta");
            return -1;
        }
        
        return queryReturn;
    }
    
    //metodo que regresa un array con los clientes actuales
    public String[] getClientesArray(){
        
        String query = "SELECT C.NOMBRECLIENTE FROM CLIENTE AS C";
        String[] columnasCliente = {"NOMBRECLIENTE"};
        LinkedList<LinkedList<String>> datosClientes = Globales.baseDatos.select(query,columnasCliente);
        if(datosClientes == null || datosClientes.size() < 1){
            return null;
        }
        String[] modeloDescripcion = new String[datosClientes.get(0).size()];
        for(int i = 0; i<datosClientes.get(0).size(); i++){
            modeloDescripcion[i] = datosClientes.get(0).get(i);
        }
        
        
        return modeloDescripcion;
    }
    
    public boolean commitWithRollback(String[] querys){
        Statement st = null;
        boolean execution;
        try{
            this.conexion.setAutoCommit(false);
            st = this.conexion.createStatement();
            
            //se ejecutan todos los querys
            for(String query : querys){
                
                execution = st.execute(query);
                
            }
           
            st.close();
            this.conexion.setAutoCommit(true);
            conectarBD(Globales.baseDatos.generarURL());
        }
        catch(Exception e){
            try{
                st.close();
                this.conexion.rollback();
                this.conexion.setAutoCommit(true);
                this.setUltimoError(e.getMessage());
                return false;
            }
            catch(Exception e2){
                
                this.setUltimoError(e2.getMessage());
                return false;
            }
            
        }
        
        boolean bandera = conectarBD(Globales.baseDatos.generarURL());
        if(bandera == false){
            return bandera;
        }
        return true;
    }
    
    public String generateNewFacturaQuery(String[] valores){
        if(valores.length < 7){
            this.ultimoError = "No se encontraron suficientes argumentos para crear la factura";
            return null;
        }
        String resultado = "('ID',MONTO,FECHA,ESPAGADO,FECHAPAGO,ESACTIVO,CLIENTEID)";
        
        resultado = resultado.replaceFirst("ID", valores[0]);
        resultado = resultado.replaceFirst("MONTO", valores[1]);
        resultado = resultado.replaceFirst("FECHA", valores[2]);
        resultado = resultado.replaceFirst("ESPAGADO", valores[3]);
        resultado = resultado.replaceFirst("FECHAPAGO", valores[4]);
        resultado = resultado.replaceFirst("ESACTIVO", valores[5]);
        resultado = resultado.replaceFirst("CLIENTEID", valores[6]);
        
        resultado = "INSERT INTO FACTURA VALUES " + resultado;
        
        return resultado;
    }
    
    public String generateUptateVentaQuery(String[] valores){
        
        if(valores.length < 2){
            this.ultimoError = "No se encontraron suficientes argumentos para modificar la venta";
            return null;
        }
        
        String resultado = "VENTAID, 'FACTURAID'";
        
        resultado = resultado.replaceFirst("VENTAID", valores[0]);
        resultado = resultado.replaceFirst("FACTURAID", valores[1]);
        
        resultado = "EXEC FACTURARVENTA " + resultado;
        
        
        return resultado;
    }
    
    public String generateNewVentaFantasmaQuery(String[] valores){
        
        if(valores.length < 6){
            this.ultimoError = "No se encontraron suficientes argumentos para crear la venta fantasma";
            return null;
        }
        
        String resultado = "(REFERENCIAVENTA, ESACTIVO, 'IDFACTURA',CANTIDADM3,ESCONSILIADA, PRECIOM3)";
       
        resultado = resultado.replaceFirst("REFERENCIAVENTA", valores[0]);
        resultado = resultado.replaceFirst("ESACTIVO", valores[1]);
        resultado = resultado.replaceFirst("IDFACTURA", valores[2]);
        resultado = resultado.replaceFirst("CANTIDADM3", valores[3]);
        resultado = resultado.replaceFirst("ESCONSILIADA", valores[4]);
        resultado = resultado.replaceFirst("PRECIOM3", valores[5]);
        
        resultado = "INSERT INTO VENTAFANTASMA VALUES" + resultado;
        
        return resultado;
        
        
    }
    
    public boolean deleteFactura(String facturaID){
        String query;
        String[] columnas = {"ID"};
        //Se obtienen los ID de las ventas
        query = "SELECT V.VENTAID  AS ID\n" +
                "FROM FACTURA AS F, VENTA AS V\n" +
                "WHERE V.FACTURAID = F.IDFACTURA AND F.IDFACTURA = '" + facturaID + "'";
        LinkedList<LinkedList<String>> ventas = Globales.baseDatos.select(query,columnas);
        //Se obtienen los ID de las ventas fantasma
        query = "SELECT V.VANTAID AS ID\n" +
                "FROM FACTURA AS F, VENTAFANTASMA AS V\n" +
                "WHERE V.IDFACTURA = F.IDFACTURA AND F.IDFACTURA = '" + facturaID + "'";
        LinkedList<LinkedList<String>> ventasFantasma = Globales.baseDatos.select(query,columnas);
        
        //si hay algun error regresa falso
        if(ventas == null || ventasFantasma == null){
            return false;
        }
            
        
        //se calcula el espacio del array
        int ventasLenght = ventas.get(0).size();
        int ventasFantasmaLenght = ventasFantasma.get(0).size();
        
        //se crea el array de los querys a ejecutar
        String[] querys = new String[1+ventasLenght+ventasFantasmaLenght];
       
        //se crea el query que modifica la factura y se añad al array
        query = "UPDATE FACTURA SET ESACTIVO = 0 WHERE FACTURA.IDFACTURA = '"+ facturaID +"'";
        querys[0] = query;
        
        //se añaden a la lista las actualizaciones de ventas
        for(int i = 1; i < ventasLenght + 1; i++){
            querys[i] = "UPDATE VENTA SET ESFACTURADO = 0, FACTURAID = NULL WHERE VENTAID = "+ventas.get(0).get(i-1)+"";
        }
        //se añaden a la lista las actualizaciones de ventas fantasma
        for(int i = ventasLenght+1; i < ventasLenght+ventasFantasmaLenght+1 ; i++){
            querys[i] = "UPDATE VENTAFANTASMA SET ESACTIVO = 0 WHERE VENTAFANTASMA.VANTAID = "+ventasFantasma.get(0).get(i-1-ventasLenght)+"";
        }
        
        //Se asignan los querys a el array para poder ejecutarse
        return commitWithRollback(querys);
        
    }
    
    public String generateUpdateFacturaPagadaQuery(String idFactura){
        return ("UPDATE FACTURA SET ESPAGADO = 1, FECHAPAGO = GETDATE() WHERE FACTURA.IDFACTURA = '"+idFactura+"'");
    }
    
    public String generateUpdateFacturaNoPagadaQuery(String idFactura){
        return ("UPDATE FACTURA SET ESPAGADO = 0 WHERE FACTURA.IDFACTURA = '"+idFactura+"'");
    }
    
    public String generateReporteCamionesQuery(boolean camionesActivos, boolean camionesInactivos){
        if(!camionesActivos && !camionesInactivos){//no hay nada seleccionado 
            return null;
        }
        
        String queryClause = "SELECT C.IDCAMION, C.OPERADOR AS NombreOperador, T.DESCRIPCION AS DescripcionCamion, C.COLOR AS ColorCamion, A.DESCRIPCION AS EstatusCamion\n";
        queryClause += "FROM CAMION AS C, ACTIVO AS A, TIPOCAMION AS T\n";
        queryClause += "WHERE C.ACTIVO = A.IDACTIVO AND C.IDTIPOCAMION = T.IDTIPOCAMION ";
        
        if(camionesActivos && camionesInactivos){//ambas opciones están seleccionadas
            return queryClause;
        }
        else if(camionesActivos){//camiones activos
            queryClause += "AND C.ACTIVO = 1";
        }
        else{//camiones inactivos
            queryClause += "AND C.ACTIVO = 0";
        }
        
        return queryClause;
    }
    
    public String generateReporteClientesQuery(){
        return "SELECT DISTINCT CLIENTE.CLIENTEID,CLIENTE.NOMBRECLIENTE ,\n" +
                "\n" +
                "(SELECT PRECIO_CLIENTE_MATERIAL.PRECIO \n" +
                "	FROM PRECIO_CLIENTE_MATERIAL \n" +
                "	WHERE  PRECIO_CLIENTE_MATERIAL.CLIENTEID = CLIENTE.CLIENTEID AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 1 ) AS 'GRAVAUNMEDIO',\n" +
                "\n" +
                "(SELECT PRECIO_CLIENTE_MATERIAL.PRECIO \n" +
                "	FROM PRECIO_CLIENTE_MATERIAL \n" +
                "	WHERE  PRECIO_CLIENTE_MATERIAL.CLIENTEID = CLIENTE.CLIENTEID AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 2 ) AS 'GRAVATRESCUARTOS',\n" +
                "\n" +
                "(SELECT PRECIO_CLIENTE_MATERIAL.PRECIO \n" +
                "	FROM PRECIO_CLIENTE_MATERIAL \n" +
                "	WHERE  PRECIO_CLIENTE_MATERIAL.CLIENTEID = CLIENTE.CLIENTEID AND PRECIO_CLIENTE_MATERIAL.MATERIALID = 3 ) AS 'ARENILLA'\n" +
                "\n" +
                "FROM CLIENTE, PRECIO_CLIENTE_MATERIAL \n" +
                "WHERE CLIENTE.CLIENTEID = PRECIO_CLIENTE_MATERIAL.CLIENTEID";
    }
    
    public String generateReporteFacturasQuery(boolean facturasActivas, boolean facturasInactivas, String fechaInicial, String fechaFinal){
        
        if(!facturasActivas && !facturasInactivas){//no hay nada seleccionado 
            return null;
        }
        
        String queryClause = "SELECT F.IDFACTURA,F.MONTOFACTURA,  F.FECHAFACTURA AS FechaFacturaCreada, \n" +
                    "(CASE WHEN F.ESPAGADO = 1 THEN 'TRUE' ELSE 'FALSE' END)  AS EsFacturaPagada, \n" +
                    "F.FECHAPAGO AS FechaFacturaPagada,A.DESCRIPCION  AS EsFacturaActiva, C.NOMBRECLIENTE\n" +
                    "FROM FACTURA AS F, CLIENTE AS C, ACTIVO AS A\n" +
                    "WHERE C.CLIENTEID = F.CLIENTEID AND A.IDACTIVO = F.ESACTIVO ";
        
        String dateQuery = " AND (F.FECHAFACTURA BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"') ";
        queryClause += dateQuery;
        
        if(facturasActivas && facturasInactivas){//ambas opciones están seleccionadas
            
            return queryClause;
        }
        else if(facturasActivas){//facturas activos
            queryClause += " AND F.ESACTIVO = 1";
        }
        else{//facturas inactivos
            queryClause += " AND F.ESACTIVO = 0";
        }
        
        
        return queryClause;
    }
    
    public String generateReporteGastosGeneralesQuery(boolean gastosActivos, boolean gastosInactivos, String fechaInicial, String fechaFinal){
        if(!gastosActivos && !gastosInactivos){//no hay nada seleccionado 
            return null;
        }
        
        String queryClause = "SELECT G.IDFOLIOPAGO, G.FECHAFOLIOPAGO, G.FOLIOFACTURA, G.FECHAFACTURA, G.TOTAL AS MONTOTOTAL, G.DESCRIPCION, G.EQUIPO,\n" +
                        "G.PROVEEDOR, A.DESCRIPCION AS EsGastoGeneralActivo\n" +
                        "FROM GASTOGENERAL AS G, ACTIVO AS A\n" +
                        "WHERE A.IDACTIVO = G.ESACTIVO ";
        
        String dateQuery = " AND (G.FECHAFACTURA BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"') ";
        queryClause += dateQuery;
        
        if(gastosActivos && gastosInactivos){//ambas opciones están seleccionadas
            
            return queryClause;
        }
        else if(gastosActivos){//gastos activos
            queryClause += " AND G.ESACTIVO = 1";
        }
        else{//gastos inactivos
            queryClause += " AND G.ESACTIVO = 0";
        }
        
        
        return queryClause;
    }
    
    public String generateReporteVentasQuery(boolean ventasActivas, boolean ventasInactivas, String fechaInicial, String fechaFinal){
        if(!ventasActivas && !ventasInactivas){//no hay nada seleccionado 
            return null;
        }
        
        String queryClause = "SELECT V.VENTAID,A.DESCRIPCION AS EsVentaActiva, C.NOMBRECLIENTE, P.NOMBREPLANTA,M.DESCRIPCIONMATERIAL, V.PRECIOM3, V.CANTIDADM3, \n" +
                "(V.PRECIOM3*V.CANTIDADM3) AS MontoTotal,  V.FECHAVENTA, V.HORAVENTA,\n" +
                "(CASE WHEN V.ESCREDITO = 1 THEN 'TRUE' ELSE 'FALSE' END)  AS EsVentaCredito,\n" +
                " (CASE WHEN V.ESFACTURADO = 1 THEN 'TRUE' ELSE 'FALSE' END)  AS EsVentaFacturada, V.FACTURAID,\n" +
                " V.FOLIOTRANSPORTISTA, V.FOLIOPLANTA,  V.MATRICULACAMION, V.NOMBRECHOFER\n" +
                "FROM VENTA AS V, CLIENTE AS C, MATERIAL AS M, PLANTA AS P, ACTIVO AS A\n" +
                "WHERE C.CLIENTEID = V.CLIENTEID AND M.MATERIALID = V.MATERIALID AND P.PLANTAID = V.PLANTAID AND A.IDACTIVO = V.ACTIVO ";
        
        String dateQuery = " AND (V.FECHAVENTA BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"') ";
        queryClause += dateQuery;
        
        if(ventasActivas && ventasInactivas){//ambas opciones están seleccionadas
            
            return queryClause;
        }
        else if(ventasActivas){//VENTAS activas
            queryClause += " AND V.ACTIVO = 1";
        }
        else{//gastos inactivos
            queryClause += " AND V.ACTIVO = 0";
        }
        
        return queryClause;
    }
    
    public String generateReporteVentasFantasmaQuery(boolean ventasFActivas, boolean ventasFInactivas){
        if(!ventasFActivas && !ventasFInactivas){//no hay nada seleccionado 
            return null;
        }
        String queryClause = "SELECT VF.VANTAID, A.DESCRIPCION as EsVentaFantasmaActiva, VF.IDFACTURA, VF.CANTIDADM3, VF.PRECIOM3,\n" +
                    "(VF.CANTIDADM3 * VF.PRECIOM3) AS MontoTotalVentaFantasma,\n" +
                    "(CASE WHEN VF.ESCONCILIADA = 1 THEN 'TRUE' ELSE 'FALSE' END)  AS EsVentaFantasmaConciliada, vf.REFERENCIAVENTA as  IDVentaConciliada\n" +
                    "FROM VENTAFANTASMA AS VF, ACTIVO AS A \n" +
                    "WHERE A.IDACTIVO = VF.ESACTIVO ";
        
        if(ventasFActivas && ventasFInactivas){//ambas opciones están seleccionadas
            
            return queryClause;
        }
        else if(ventasFActivas){//VENTAS fantasma activas
            queryClause += " AND VF.ESACTIVO = 1";
        }
        else{//gastos inactivos
            queryClause += " AND VF.ESACTIVO = 0";
        }
        
        
        return queryClause;
    }
    
    public String generateReporteViajesQuery(boolean viajesActivos, boolean viajesInactivos, String fechaInicial, String fechaFinal){
        if(!viajesActivos && !viajesInactivos){//no hay nada seleccionado 
            return null;
        }
        String queryClause = "SELECT V.IDVIAJE, C.IDCAMION, C.OPERADOR AS NombreOperador, T.DESCRIPCION AS DESCRIPCIONCAMION,\n" +
            "T.CAPACIDAD AS CAPACIDADCAMIONM3, V.FECHA AS FechaDeViaje, V.HORA AS HoraDeViaje, A.DESCRIPCION AS EsViajeActivo\n" +
            "FROM VIAJE AS V, CAMION AS C, TIPOCAMION AS T, ACTIVO AS A\n" +
            "WHERE C.IDCAMION = V.IDCAMION AND T.IDTIPOCAMION = C.IDTIPOCAMION AND A.IDACTIVO = V.ESACTIVO ";
        
        String dateQuery = " AND (V.FECHA BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"') ";
        queryClause += dateQuery;
        
        if(viajesActivos && viajesInactivos){//ambas opciones están seleccionadas
            
            return queryClause;
        }
        else if(viajesActivos){//Viajes fantasma activas
            queryClause += " AND V.ESACTIVO = 1";
        }
        else{//gastos inactivos
            queryClause += " AND V.ESACTIVO = 0";
        }
        
        
        return queryClause;
    }
            
    
    public boolean createReportFile(String filePath, String query, String[] columnas){
        Object[][] data = selectO(query, columnas);
        CreateExcelFile excelFile = new CreateExcelFile();
        if(data == null){
            return false;
        }
        //boolean exito = excelFile.createFile(data, filePath, columnas);
        if(!excelFile.createFile(data, filePath,columnas)){
            this.setUltimoError("Error al intentar guardar el archivo");
            return false;
        }
        
        return true;
    }
    
    

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getNombreBD() {
        return nombreBD;
    }

    public void setNombreBD(String nombreBD) {
        this.nombreBD = nombreBD;
    }

    public String getUsuarioBD() {
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public String getContrasenaBD() {
        return contrasenaBD;
    }

    public void setContrasenaBD(String contrasenaBD) {
        this.contrasenaBD = contrasenaBD;
    }

    public boolean isIsConectado() {
        return isConectado;
    }

    public void setIsConectado(boolean isConectado) {
        this.isConectado = isConectado;
    }

    public String getUltimoError() {
        return ultimoError;
    }

    public void setUltimoError(String ultimoError) {
        this.ultimoError = ultimoError;
    }
       
}

