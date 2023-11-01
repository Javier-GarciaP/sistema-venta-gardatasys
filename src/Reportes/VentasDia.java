package Reportes;

import ModeloFinal.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasDia {
    
    double ventasDelDia;
    
    public double obtenerVentasDelDia(String fecha){
        Connection conn;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try{
            String consulta = "SELECT SUM(total) as total_dia FROM ventas WHERE fecha = ? GROUP BY fecha";
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            // Asigna la fecha como un parámetro seguro
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            if(rs.next()){
                ventasDelDia = rs.getDouble("total_dia");
            }
            
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ventasDelDia;
    }
}
