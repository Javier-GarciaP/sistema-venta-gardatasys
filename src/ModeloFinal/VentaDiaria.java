package ModeloFinal;

public class VentaDiaria {
    private int id;
    private double total;
    private int ventaID;
    private String tipo;
    private String fecha;

    public VentaDiaria() {
    }

    public VentaDiaria(int id, double total, int ventaID, String tipo, String fecha) {
        this.id = id;
        this.total = total;
        this.ventaID = ventaID;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getVentaID() {
        return ventaID;
    }

    public void setVentaID(int ventaID) {
        this.ventaID = ventaID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
