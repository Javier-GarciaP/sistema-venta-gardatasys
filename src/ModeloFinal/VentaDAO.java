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
        String consulta = "INSERT INTO ventas (cliente, vendedor, total, metodo_pago, monto_pagado, fecha) VALUES (?,?,?,?,?,?)";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, venta.getCliente());
            ps.setString(2, venta.getVendedor());
            ps.setDouble(3, venta.getTotal());
            ps.setString(4, venta.getMetodoPago());
            ps.setDouble(5, venta.getMontoPagado());
            ps.setString(6, venta.getFecha());
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
    
    public boolean actualizarPago(int id, double monto){
        String consulta = "UPDATE ventas SET monto_pagado = monto_pagado + ? WHERE id = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setDouble(1, monto);
            ps.setInt(2, id);
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
                venta.setMetodoPago(rs.getString("metodo_pago"));
                venta.setMontoPagado(rs.getDouble("monto_pagado"));
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
    
    public List buscarVentaAvanzada(String buscar, String inicio, String fin, String estado) {
        List<Venta> listarVenta = new ArrayList();
        String consulta = "SELECT * FROM ventas WHERE (cliente LIKE ? OR vendedor LIKE ?)";
        
        if (inicio != null && fin != null && !inicio.isEmpty() && !fin.isEmpty()) {
            consulta += " AND PARSEDATETIME(fecha, 'dd/MM/yyyy') BETWEEN PARSEDATETIME('" + inicio + "', 'yyyy-MM-dd') AND PARSEDATETIME('" + fin + "', 'yyyy-MM-dd')";
        }
        
        if (estado.equals("Deudores")) {
            consulta += " AND monto_pagado < total";
        }
        
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, "%" + buscar + "%");
            ps.setString(2, "%" + buscar + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setCliente(rs.getString("cliente"));
                venta.setVendedor(rs.getString("vendedor"));
                venta.setTotal(rs.getDouble("total"));
                venta.setMetodoPago(rs.getString("metodo_pago"));
                venta.setMontoPagado(rs.getDouble("monto_pagado"));
                venta.setFecha(rs.getString("fecha"));
                listarVenta.add(venta);
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
                venta.setMetodoPago(rs.getString("metodo_pago"));
                venta.setMontoPagado(rs.getDouble("monto_pagado"));
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

    public List<Object[]> obtenerDetallesVenta(int idVenta){
        List<Object[]> lista = new ArrayList();
        String consulta = "SELECT d.cod_producto, p.nombre, d.cantidad, d.precio FROM detalleventa d LEFT JOIN productos p ON d.cod_producto = p.codigo WHERE d.ventaID = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while(rs.next()){
                Object[] ob = new Object[4];
                ob[0] = rs.getString("cod_producto");
                ob[1] = rs.getString("nombre");
                ob[2] = rs.getDouble("cantidad");
                ob[3] = rs.getDouble("precio");
                lista.add(ob);
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
