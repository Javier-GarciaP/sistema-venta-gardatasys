package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaDiariaDAO {
    Connection conn;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarVentaDiaria(VentaDiaria vd){
        String consulta = "INSERT INTO ventasdiarias (venta_id, total, tipo, descripcion, fecha) VALUES (?,?,?,?,?)";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, vd.getVentaID());
            ps.setDouble(2, vd.getTotal());
            ps.setString(3, vd.getTipo());
            ps.setString(4, vd.getDescripcion());
            ps.setString(5, vd.getFecha());
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

    public List listarVentaDiaria(){
        List<VentaDiaria> lista = new ArrayList();
        String consulta = "SELECT * FROM ventasdiarias ORDER BY id DESC";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                VentaDiaria vd = new VentaDiaria();
                vd.setId(rs.getInt("id"));
                vd.setVentaID(rs.getInt("venta_id"));
                vd.setTotal(rs.getDouble("total"));
                vd.setTipo(rs.getString("tipo"));
                vd.setDescripcion(rs.getString("descripcion"));
                vd.setFecha(rs.getString("fecha"));
                lista.add(vd);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return lista;
    }
}
