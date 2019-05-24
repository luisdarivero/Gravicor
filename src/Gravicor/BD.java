package Gravicor;
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
        
        else{
            
            Object[][] resultado = new Object [lista.size()][lista.get(0).size()];;
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
        //System.out.println(nombreCliente + " = " + idCliente);
        if(idCliente < 0){
            return (float)-1.0;
        }
        Integer idMaterial = obtenerMaterialID(nombreMaterial);
        //System.out.println(nombreMaterial + " = " + idMaterial);
        
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

