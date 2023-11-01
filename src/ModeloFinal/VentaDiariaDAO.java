package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaDiariaDAO {
    Connection conn;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarVentaDiaria(VentaDiaria vd){
        String consulta = "INSERT INTO ventasdiarias SET total = ?, tipo = ?, fecha = ?";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setDouble(1, vd.getTotal());
            ps.setString(2, vd.getTipo());
            ps.setString(3, vd.getFecha());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
