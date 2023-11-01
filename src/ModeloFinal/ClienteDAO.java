package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ClienteDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    DefaultTableModel model = new DefaultTableModel();
    
    public boolean registrarCliente(Cliente cl){
        String consulta = "INSERT INTO clientes (dni, nombre, telefono, direccion) VALUES(?,?,?,?)";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setLong(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.execute();
            return true;
        }catch(SQLException e){
            javax.swing.JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try{
                conn.close();
            }catch(SQLException e){
                System.out.println(e.toString());
            }
            
        }
    }
    
    public List listarCliente(){
        List<Cliente> ListaCL = new ArrayList();
        String consulta = "SELECT * FROM clientes";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id"));
                cl.setDni(rs.getInt("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getLong("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                ListaCL.add(cl);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaCL;
    }
    
    public boolean eliminarCliente(int id){
        String consulta = "DELETE FROM clientes WHERE id = ?";
        try{
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
    
    public boolean actualizarCliente(Cliente cl){
        String consulta = "UPDATE clientes SET dni = ?, nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try{
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setLong(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setInt(5, cl.getId());
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
    
    public Cliente buscarCliente(int dni){
        Cliente cl = new Cliente();
        String consulta = "SELECT * FROM clientes WHERE dni = ?";
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, dni);
            rs = ps.executeQuery();
            if(rs.next()){
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getLong("telefono"));
                cl.setDireccion(rs.getString("direccion"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return cl;
    }
    
    public List buscar(String buscar){
        
        List<Cliente> ListaCL = new ArrayList();
        String consulta = "SELECT * FROM clientes WHERE LOWER(nombre) LIKE LOWER('%" + buscar + "%') OR LOWER(dni) LIKE LOWER('%" + buscar + "%')";

        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id"));
                cl.setDni(rs.getInt("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getLong("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                ListaCL.add(cl);
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaCL;
    }
}
