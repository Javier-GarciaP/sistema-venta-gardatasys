package ModeloFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    Connection conn;
    public Connection getConnection(){
        
        try{
            
            String myBD = "jdbc:h2:file:./sistemaventa_db;MODE=MySQL;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './init.sql'";
            conn = DriverManager.getConnection(myBD, "sa", "");
            return conn;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
