package ModeloFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatosEmpresaDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public DatosEmpresa buscarDatosEmpresa(){
        DatosEmpresa datosE = new DatosEmpresa();
        String consulta = "SELECT * FROM configuracion";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            if(rs.next()){
                datosE.setId(rs.getInt("id"));
                datosE.setRif(rs.getString("rif"));
                datosE.setNombreNegocio(rs.getString("nombre_negocio"));
                datosE.setNombrePropietario(rs.getString("nombre_propietario"));
                datosE.setTelefono(rs.getLong("telefono"));
                datosE.setMunicipio(rs.getString("municipio"));
                datosE.setEstado(rs.getString("estado"));
                datosE.setDireccion(rs.getString("direccion"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return datosE;
    }
    
    public boolean actualizarDatosEmpresa(DatosEmpresa datosE){
        String consulta = "UPDATE configuracion SET rif = ?, nombre_negocio = ?, nombre_propietario = ?, telefono = ?, municipio = ?, estado = ?, direccion = ? WHERE id = 1";
        
        try{
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, datosE.getRif());
            ps.setString(2, datosE.getNombreNegocio());
            ps.setString(3, datosE.getNombrePropietario());
            ps.setLong(4, datosE.getTelefono());
            ps.setString(5, datosE.getMunicipio());
            ps.setString(6, datosE.getEstado());
            ps.setString(7, datosE.getDireccion());
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
