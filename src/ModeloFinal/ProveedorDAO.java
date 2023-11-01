package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProveedorDAO {
    Connection conn;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarProveedor(Proveedor pr){
        String consulta = "INSERT INTO proveedor(rif, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, pr.getRif());
            ps.setString(2, pr.getNombre());
            ps.setLong(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public List listarProveedor(){
        List<Proveedor> listaPR = new ArrayList();
        String consulta = "SELECT * FROM proveedor";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRif(rs.getInt("rif"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getLong("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                listaPR.add(pr);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return listaPR;
    }
    
    public boolean eliminarProveedor(int id){
        String consulta = "DELETE FROM proveedor WHERE id = ?";
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
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public boolean actualizarProveedor(Proveedor pr){
        String consulta = "UPDATE proveedor SET rif = ?, nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, pr.getRif());
            ps.setString(2, pr.getNombre());
            ps.setLong(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setInt(5, pr.getId());
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
    
    public List buscar(String buscar){
        List<Proveedor> listaPR = new ArrayList();
        String consulta = "SELECT * FROM proveedor WHERE LOWER(RIF) LIKE LOWER('%" + buscar + "%') OR LOWER(nombre) LIKE LOWER('%" + buscar + "%')";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRif(rs.getInt("rif"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getLong("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                listaPR.add(pr);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return listaPR;
    }
}
