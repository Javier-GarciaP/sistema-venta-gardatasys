package ModeloFinal;

public class Proveedor {
    private int id;
    private int rif;
    private String nombre;
    private long telefono;
    private String direccion;

    public Proveedor() {
    }

    public Proveedor(int id, int rif, String nombre, long telefono, String direccion) {
        this.id = id;
        this.rif = rif;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRif() {
        return rif;
    }

    public void setRif(int rif) {
        this.rif = rif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
