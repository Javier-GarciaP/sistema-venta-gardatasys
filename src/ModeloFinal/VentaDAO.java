package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VentaDAO {
    Connection conn;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int respuesta;
    
    
    public int registrarVenta(Venta venta){
        String consulta = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, venta.getCliente());
            ps.setString(2, venta.getVendedor());
            ps.setDouble(3, venta.getTotal());
            ps.setString(4, venta.getFecha());
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
        return respuesta;
    }
    
    public int registrarDetalle(DetalleVenta dv){
        String consulta = "INSERT INTO detalleventa (cod_producto, cantidad, precio, ventaID) VALUES (?,?,?,?)";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, dv.getCodigoProducto());
            ps.setDouble(2, dv.getCantidad());
            ps.setDouble(3, dv.getPrecio());
            ps.setInt(4, dv.getVentaID());
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
        return respuesta;
    }
    
    public int ventaID(){
        int id = 0;
        String consulta = "SELECT MAX(id) FROM ventas";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return id;
    }
    
    public boolean actualizarStock(double cantidad, String codigo){
        String consulta = "UPDATE productos SET cantidad = ? WHERE codigo = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setDouble(1, cantidad);
            ps.setString(2, codigo);
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
    
    public List ListarVenta(){
        List<Venta> listarVenta = new ArrayList();
        String consulta = "SELECT * FROM ventas";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setCliente(rs.getString("cliente"));
                venta.setVendedor(rs.getString("vendedor"));
                venta.setTotal(rs.getDouble("total"));
                venta.setFecha(rs.getString("fecha"));
                listarVenta.add(venta);
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
        return listarVenta;
    }
    
    public List buscar(String fecha){
        List<Venta> listarVenta = new ArrayList();
        String consulta = "SELECT * FROM ventas WHERE fecha = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            while(rs.next()){
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setCliente(rs.getString("cliente"));
                venta.setVendedor(rs.getString("vendedor"));
                venta.setTotal(rs.getDouble("total"));
                venta.setFecha(rs.getString("fecha"));
                listarVenta.add(venta);
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
        return listarVenta;
    }
}
