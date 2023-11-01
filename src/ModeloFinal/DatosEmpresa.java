package ModeloFinal;

public class DatosEmpresa {
    private int id;
    private String rif;
    private String nombreNegocio;
    private String nombrePropietario;
    private long telefono;
    private String municipio;
    private String estado;
    private String direccion;

    public DatosEmpresa() {
    }

    public DatosEmpresa(int id, String rif, String nombreNegocio, String nombrePropietario, long telefono, String municipio, String estado, String direccion) {
        this.id = id;
        this.rif = rif;
        this.nombreNegocio = nombreNegocio;
        this.nombrePropietario = nombrePropietario;
        this.telefono = telefono;
        this.municipio = municipio;
        this.estado = estado;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
