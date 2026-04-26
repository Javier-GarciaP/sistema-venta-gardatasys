package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreditoDAO {
    Connection conn;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarVentaCredito(Credito cred){
        String consulta = "INSERT INTO credito(cliente, vendedor, total, idVentas, fecha) VALUES(?,?,?,?,?)";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, cred.getCliente());
            ps.setString(2, cred.getVendedor());
            ps.setDouble(3, cred.getTotal());
            ps.setInt(4, cred.getIdVentas());
            ps.setString(5, cred.getFecha());
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
    
    public List ListarVentaCredito(){
        List<Credito> listarCredito = new ArrayList();
        String consulta = "SELECT * FROM credito";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Credito credito = new Credito();
                credito.setId(rs.getInt("id"));
                credito.setCliente(rs.getString("cliente"));
                credito.setVendedor(rs.getString("vendedor"));
                credito.setTotal(rs.getDouble("total"));
                credito.setIdVentas(rs.getInt("idVentas"));
                credito.setFecha(rs.getString("fecha"));
                listarCredito.add(credito);
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
        return listarCredito;
    }
    
    public void abonar(Double cantidad, int id){
        String consulta = "UPDATE credito SET total = ? WHERE id = ?";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setDouble(1, cantidad);
            ps.setInt(2, id);
            ps.execute();
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean eliminar(int id){
        String consulta = "DELETE FROM credito WHERE id = ?";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, id);
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
    
    public double obtenerTotalPorCobrar() {
        double total = 0;
        String consulta = "SELECT SUM(total) FROM credito";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return total;
    }

    public List buscarAvanzado(String valor) {
        List<Credito> listarCredito = new ArrayList();
        String consulta = "SELECT * FROM credito WHERE LOWER(cliente) LIKE LOWER(?) OR idVentas LIKE ?";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, "%" + valor + "%");
            ps.setString(2, "%" + valor + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Credito credito = new Credito();
                credito.setId(rs.getInt("id"));
                credito.setCliente(rs.getString("cliente"));
                credito.setVendedor(rs.getString("vendedor"));
                credito.setTotal(rs.getDouble("total"));
                credito.setIdVentas(rs.getInt("idVentas"));
                credito.setFecha(rs.getString("fecha"));
                listarCredito.add(credito);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return listarCredito;
    }
    public Credito buscarCredito(int id){
        Credito credito = new Credito();
        String consulta = "SELECT * FROM credito WHERE id = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                credito.setId(rs.getInt("id"));
                credito.setCliente(rs.getString("cliente"));
                credito.setVendedor(rs.getString("vendedor"));
                credito.setTotal(rs.getDouble("total"));
                credito.setIdVentas(rs.getInt("idVentas"));
                credito.setFecha(rs.getString("fecha"));
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
        return credito;
    }
}
