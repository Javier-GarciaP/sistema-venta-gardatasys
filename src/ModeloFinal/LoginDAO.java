package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public Login log(String nombre, String password){
        Login l = new Login();
        String consulta = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setPassword(rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return l;
    }
}
