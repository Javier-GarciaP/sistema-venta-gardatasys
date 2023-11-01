package ModeloFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    Connection conn;
    public Connection getConnection(){
        
        try{
            
            String myBD = "jdbc:mysql://localhost:3306/sistemaventa?serverTimezone=UTC";
            conn = DriverManager.getConnection(myBD, "root", "");
            return conn;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
