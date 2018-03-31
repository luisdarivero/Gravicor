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
    }
    
    public boolean conectarBD(String URL){
        try{
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

