package ModeloFinal;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

public class ProductosDAO {

    Conexion cn = new Conexion();
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrarProductos(Productos prod) {
        String consulta = "INSERT INTO productos (codigo, nombre, medida, cantidad, precio, proveedor) VALUES (?,?,?,?,?,?)";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getMedida());
            ps.setDouble(4, prod.getCantidad());
            ps.setDouble(5, prod.getPrecio());
            ps.setString(6, prod.getProveedor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void consultarProveedor(JComboBox proveedor) {
        String consulta = "SELECT nombre FROM proveedor";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));
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
    }

    public List listarProductos() {
        List<Productos> ListaPROD = new ArrayList();
        String consulta = "SELECT * FROM productos";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos prod = new Productos();
                prod.setId(rs.getInt("id"));
                prod.setCodigo(rs.getString("codigo"));
                prod.setNombre(rs.getString("nombre"));
                prod.setMedida(rs.getString("medida"));

                double cantidad = rs.getDouble("cantidad");
                System.out.println(cantidad);
                prod.setCantidad(cantidad);

                prod.setPrecio(rs.getDouble("precio"));
                prod.setProveedor(rs.getString("proveedor"));
                ListaPROD.add(prod);
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
        return ListaPROD;
    }

    public boolean eliminarProductos(int id) {
        String consulta = "DELETE FROM productos WHERE id = ?";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public boolean actualizarProductos(Productos prod) {
        String consulta = "UPDATE productos SET codigo = ?, nombre = ?, medida = ?, cantidad = ?, precio = ?, proveedor = ? WHERE id = ?";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getMedida());
            ps.setDouble(4, prod.getCantidad());
            ps.setDouble(5, prod.getPrecio());
            ps.setString(6, prod.getProveedor());
            ps.setInt(7, prod.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public Productos buscarProductos(String codigo) {
        Productos producto = new Productos();

        String consulta = "SELECT * FROM productos WHERE codigo = ?";

        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setNombre(rs.getString("nombre"));
                producto.setMedida(rs.getString("medida"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getDouble("cantidad"));
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
        return producto;
    }

    public List buscar(String buscar) {
        List<Productos> ListaPROD = new ArrayList();
        String consulta = "SELECT * FROM productos WHERE LOWER(codigo) LIKE LOWER('%" + buscar + "%') OR LOWER(nombre) LIKE LOWER('%" + buscar + "%')";
        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos prod = new Productos();
                prod.setId(rs.getInt("id"));
                prod.setCodigo(rs.getString("codigo"));
                prod.setNombre(rs.getString("nombre"));
                prod.setMedida(rs.getString("medida"));
                prod.setCantidad(rs.getDouble("cantidad"));
                prod.setPrecio(rs.getLong("precio"));
                prod.setProveedor(rs.getString("proveedor"));
                ListaPROD.add(prod);
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
        return ListaPROD;
    }

    public String[] cargarSugerenciasNombre() {
        List<String> sugerencias = new ArrayList<>();

        String consulta = "SELECT nombre FROM productos";

        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                sugerencias.add(nombreProducto);
            }

            // Convierte la lista de sugerencias en un array de tipo String
            String[] arraySugerencias = sugerencias.toArray(new String[0]);

            return arraySugerencias;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public Productos buscarProductosNombre(String nombre) {
        Productos producto = new Productos();

        String consulta = "SELECT * FROM productos WHERE nombre = ?";

        try {
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setCodigo(rs.getString("codigo"));
                producto.setMedida(rs.getString("medida"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getDouble("cantidad"));
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
        return producto;
    }
    
    public void actualizarStock(String nombre, double cantidad){
        double cantidadActual = 0;
        
        String consulta1 = "SELECT cantidad FROM productos WHERE nombre = ?";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta1);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if(rs.next()){
                cantidadActual = rs.getDouble("cantidad");
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try {
                conn.close();
                ps.close();
                rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
        }
        double cantidadTotal = cantidadActual + cantidad;
        String consulta = "UPDATE productos SET cantidad = ? WHERE nombre = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setDouble(1, cantidadTotal);
            ps.setString(2, nombre);
            ps.execute();
            
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try {
                ps.close();
                rs.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
