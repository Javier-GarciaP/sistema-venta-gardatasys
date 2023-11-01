package Sistema;

import ModeloFinal.Cliente;
import ModeloFinal.DineroCajaDAO;
import ModeloFinal.Productos;
import ModeloFinal.ClienteDAO;
import ModeloFinal.Login;
import ModeloFinal.Proveedor;
import ModeloFinal.Credito;
import ModeloFinal.Venta;
import ModeloFinal.DatosEmpresa;
import ModeloFinal.DatosEmpresaDAO;
import ModeloFinal.VentaDiaria;
import ModeloFinal.VentaDAO;
import ModeloFinal.ProveedorDAO;
import ModeloFinal.DetalleVenta;
import ModeloFinal.VentaDiariaDAO;
import ModeloFinal.ProductosDAO;
import ModeloFinal.CreditoDAO;
import Reportes.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ModeloFinal.Eventos;
import java.net.URL;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.mxrck.autocompleter.TextAutoCompleter;

/**
 *
 * @author PC
 */
public class Sistema extends javax.swing.JFrame {

    private TextAutoCompleter ac;

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);

    Login login = new Login();
    Cliente cl = new Cliente();
    ClienteDAO cliente = new ClienteDAO();
    Proveedor pr = new Proveedor();
    ProveedorDAO proveedor = new ProveedorDAO();
    Productos prod = new Productos();
    ProductosDAO productos = new ProductosDAO();
    Venta v = new Venta();
    VentaDAO venta = new VentaDAO();
    DetalleVenta dv = new DetalleVenta();
    DatosEmpresa datosEmpresa = new DatosEmpresa();
    DatosEmpresaDAO datosEmpresaDAO = new DatosEmpresaDAO();
    VentasDia ventasDia = new VentasDia();
    DineroCajaDAO dineroCajaDAO = new DineroCajaDAO();
    VentaDiaria vd = new VentaDiaria();
    VentaDiariaDAO ventaDiaria = new VentaDiariaDAO();
    Credito cred = new Credito();
    CreditoDAO credito = new CreditoDAO();

    Eventos event = new Eventos();

    AgregarStock agregarStock;

    DefaultTableModel tmp = new DefaultTableModel();
    DefaultTableModel modelo = new DefaultTableModel();
    String vendedor;
    int item;
    int check1;
    double dineroCaja;
    double totalPagar;
    int activarNumDecimal, activarNumEntero;
    String tipoVenta;
    int checkBuscarGrafico;

    /**
     * Creates new form Sistema
     */
    public Sistema(String nombre) {
        initComponents();

        ac = new TextAutoCompleter(jt_descripcion_ventas);
        cargarSugerenciasNombre();

        iniciarComponentesJPVentas();
        iniciarComponentesJPClientes();
        iniciarComponentesJPProvedor();
        iniciarComponentesJPProductos();
        iniciarComponentesJPConfig();
        ocultarJTextField();
        listarDetalleEmpresa();
        estilosBotones();
        iniciarComponentesJPInicio();
        consultarDineroCajaBD();

        vendedor = nombre;
        jl_bienvenida.putClientProperty("FlatLaf.styleClass", "h3");
        jl_bienvenida.setText("Hola, bienvenid@ " + vendedor);
        jl_bienvenida.setForeground(Color.BLACK);

        AutoCompleteDecorator.decorate(jcb_proveedor_productos);

        jl_header_fecha.putClientProperty("FlatLaf.styleClass", "h1");
        jl_header_fecha.setText(fechaActual);
        jl_header_fecha.setForeground(Color.BLACK);
    }

    public void consultarDineroCajaBD() {
        double dineroTemp = dineroCajaDAO.consultarDineroCaja();
        dineroCaja = dineroTemp;
        jl_ventas_vender.setText(String.valueOf(dineroCaja));
    }

    public void ocultarJTextField() {
        jt_id_clientes.setVisible(false);
        jt_id_proveedor.setVisible(false);
        jt_id_productos.setVisible(false);
        jt_telefonoClientes_ventas.setVisible(false);
        jt_direccionClientes_ventas.setVisible(false);
        jt_id_config.setVisible(false);
        jt_id_ventas.setVisible(false);
    }

    public void estilosBotones() {
        jb_guardar_clientes.setBackground(Color.WHITE);
        jb_borrar_clientes.setBackground(Color.WHITE);
        jb_nuevo_clientes.setBackground(Color.WHITE);
        jb_actualizar_clientes.setBackground(Color.WHITE);
        jb_guardar_productos.setBackground(Color.WHITE);
        jb_borrar_productos.setBackground(Color.WHITE);
        jb_nuevo_productos.setBackground(Color.WHITE);
        jb_actualizar_productos.setBackground(Color.WHITE);
        jb_guardar_provedor.setBackground(Color.WHITE);
        jb_borrar_provedor.setBackground(Color.WHITE);
        jb_nuevo_provedor.setBackground(Color.WHITE);
        jb_actualizar_provedor.setBackground(Color.WHITE);
        jb_actualizar_config.setBackground(Color.WHITE);
        jb_pdf_ventas.setBackground(Color.WHITE);
        jb_reporte_ventas.setBackground(Color.WHITE);
        jb_ventasDia_ventas.setBackground(Color.WHITE);
        jb_exportar_productos.setBackground(Color.WHITE);
        jb_productosMenosStock_ventas.setBackground(Color.WHITE);

        jb_guardar_clientes.setForeground(Color.BLACK);
        jb_borrar_clientes.setForeground(Color.BLACK);
        jb_nuevo_clientes.setForeground(Color.BLACK);
        jb_actualizar_clientes.setForeground(Color.BLACK);
        jb_guardar_productos.setForeground(Color.BLACK);
        jb_borrar_productos.setForeground(Color.BLACK);
        jb_nuevo_productos.setForeground(Color.BLACK);
        jb_actualizar_productos.setForeground(Color.BLACK);
        jb_guardar_provedor.setForeground(Color.BLACK);
        jb_borrar_provedor.setForeground(Color.BLACK);
        jb_nuevo_provedor.setForeground(Color.BLACK);
        jb_actualizar_provedor.setForeground(Color.BLACK);
        jb_actualizar_config.setForeground(Color.BLACK);
        jb_pdf_ventas.setForeground(Color.BLACK);
        jb_reporte_ventas.setForeground(Color.BLACK);
        jb_ventasDia_ventas.setForeground(Color.BLACK);
        jb_exportar_productos.setForeground(Color.BLACK);
        jb_productosMenosStock_ventas.setForeground(Color.BLACK);
    }

    public void dineroCaja() {
        dineroCaja = dineroCaja + totalPagar;
        jl_ventas_vender.setText(String.valueOf(dineroCaja));
        dineroCajaDAO.guardarDinero(dineroCaja);
        double dineroTemp = dineroCajaDAO.consultarDineroCaja();
        jl_ventas_vender.setText(String.valueOf(dineroTemp));
    }

    public void listarCliente() {
        @SuppressWarnings("unchecked")
        List<Cliente> listarCL = cliente.listarCliente();
        modelo = (DefaultTableModel) jt_clientes.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarCL.size(); i++) {
            ob[0] = listarCL.get(i).getId();
            ob[1] = listarCL.get(i).getDni();
            ob[2] = listarCL.get(i).getNombre();
            ob[3] = listarCL.get(i).getTelefono();
            ob[4] = listarCL.get(i).getDireccion();
            modelo.addRow(ob);
        }
        jt_clientes.setModel(modelo);
    }

    public void listarProveedor() {
        @SuppressWarnings("unchecked")
        List<Proveedor> listarPR = proveedor.listarProveedor();
        modelo = (DefaultTableModel) jt_provedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarPR.size(); i++) {
            ob[0] = listarPR.get(i).getId();
            ob[1] = listarPR.get(i).getRif();
            ob[2] = listarPR.get(i).getNombre();
            ob[3] = listarPR.get(i).getTelefono();
            ob[4] = listarPR.get(i).getDireccion();
            modelo.addRow(ob);
        }
        jt_provedor.setModel(modelo);
    }

    public void listarProductos() {
        @SuppressWarnings("unchecked")
        List<Productos> listarPROD = productos.listarProductos();
        modelo = (DefaultTableModel) jt_productos.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < listarPROD.size(); i++) {
            ob[0] = listarPROD.get(i).getId();
            ob[1] = listarPROD.get(i).getCodigo();
            ob[2] = listarPROD.get(i).getNombre();
            ob[3] = listarPROD.get(i).getMedida();
            ob[4] = listarPROD.get(i).getCantidad();
            ob[5] = listarPROD.get(i).getPrecio();
            ob[6] = listarPROD.get(i).getProveedor();
            modelo.addRow(ob);
        }

        jt_productos.setModel(modelo);
    }

    public void listarDetalleEmpresa() {
        datosEmpresa = datosEmpresaDAO.buscarDatosEmpresa();
        jt_id_config.setText("" + datosEmpresa.getId());
        jt_rif_config.setText("" + datosEmpresa.getRif());
        jt_nombreNegocio_config.setText("" + datosEmpresa.getNombreNegocio());
        jt_nombrePropietario_config.setText("" + datosEmpresa.getNombrePropietario());
        jt_telefono_config.setText("" + datosEmpresa.getTelefono());
        jt_municipio_config.setText("" + datosEmpresa.getMunicipio());
        jt_estado_config.setText("" + datosEmpresa.getEstado());
        jt_direccion_config.setText("" + datosEmpresa.getDireccion());
    }

    public void listarVenta() {
        @SuppressWarnings("unchecked")
        List<Venta> listarVentas = venta.ListarVenta();
        modelo = (DefaultTableModel) jt_ventas.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarVentas.size(); i++) {
            ob[0] = listarVentas.get(i).getId();
            ob[1] = listarVentas.get(i).getCliente();
            ob[2] = listarVentas.get(i).getVendedor();
            ob[3] = listarVentas.get(i).getTotal();
            ob[4] = listarVentas.get(i).getFecha();
            modelo.addRow(ob);
        }
        jt_ventas.setModel(modelo);
    }

    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void iniciarComponentesJPInicio() {
        jt_seleccionar_ventas.setForeground(Color.BLACK);
        jt_dinero_inicio.setBackground(Color.WHITE);
        jl_dinero_inicio.setForeground(Color.BLACK);
        jt_dinero_inicio.setForeground(Color.BLACK);
        jl_dinero_inicio.putClientProperty("FlatLaf.styleClass", "h4");

        /*
        COMPONENTES DE CREDITO
         */
        jt_monto_credito.setVisible(false);
        jt_id_credito.setVisible(false);
        jt_abonar_credito.setForeground(Color.BLACK);
        jl_abonar_credito.setForeground(Color.BLACK);
        jl_abonar_credito.putClientProperty("FlatLaf.styleClass", "h4");
        jl_nombre_abonar.setForeground(Color.BLACK);
        jl_text_abonar.setForeground(Color.BLACK);
        jt_abonar_credito.setBackground(Color.WHITE);
    }

    public void iniciarComponentesJPVentas() {
        jt_cod_ventas.setBackground(Color.WHITE);
        jt_descripcion_ventas.setBackground(Color.WHITE);
        jt_cantidad_ventas.setBackground(Color.WHITE);
        jt_precio_ventas.setBackground(Color.WHITE);
        jt_stock_ventas.setBackground(Color.WHITE);
        jt_cedula_ventas.setBackground(Color.WHITE);
        jt_nombreClientes_ventas.setBackground(Color.WHITE);
        jt_medidas_ventas.setBackground(Color.WHITE);

        jt_cod_ventas.setForeground(Color.BLACK);
        jt_descripcion_ventas.setForeground(Color.BLACK);
        jt_cantidad_ventas.setForeground(Color.BLACK);
        jt_precio_ventas.setForeground(Color.BLACK);
        jt_stock_ventas.setForeground(Color.BLACK);
        jt_cedula_ventas.setForeground(Color.BLACK);
        jt_nombreClientes_ventas.setForeground(Color.BLACK);
        jt_medidas_ventas.setForeground(Color.BLACK);

        jl_cod_ventas.setForeground(Color.BLACK);
        jl_descripcion_ventas.setForeground(Color.BLACK);
        jl_cantidad_ventas.setForeground(Color.BLACK);
        jl_precio_ventas.setForeground(Color.BLACK);
        jl_stock_ventas.setForeground(Color.BLACK);
        jl_cedula_ventas.setForeground(Color.BLACK);
        jl_nombreClientes_ventas.setForeground(Color.BLACK);
        jl_totalPagar_ventas.setForeground(Color.BLACK);
        jl_mostrarTotal_ventas.setForeground(Color.BLACK);
        jl_medidas_ventas.setForeground(Color.BLACK);
    }

    public void iniciarComponentesJPClientes() {
        jl_rif_prevedor.setForeground(Color.BLACK);
        jl_nombre_provedor.setForeground(Color.BLACK);
        jl_telefono_provedor.setForeground(Color.BLACK);
        jl_direccion_provedor.setForeground(Color.BLACK);
        jl_buscar_provedor.setForeground(Color.BLACK);

        jt_rif_provedor.setBackground(Color.WHITE);
        jt_nombre_provedor.setBackground(Color.WHITE);
        jt_telefono_provedor.setBackground(Color.WHITE);
        jt_direccion_provedor.setBackground(Color.WHITE);
        jt_buscar_provedor.setBackground(Color.WHITE);

        jt_rif_provedor.setForeground(Color.BLACK);
        jt_nombre_provedor.setForeground(Color.BLACK);
        jt_telefono_provedor.setForeground(Color.BLACK);
        jt_direccion_provedor.setForeground(Color.BLACK);
        jt_buscar_provedor.setForeground(Color.BLACK);
    }

    public void iniciarComponentesJPProvedor() {
        jl_dni_clientes.setForeground(Color.BLACK);
        jl_nombre_clientes.setForeground(Color.BLACK);
        jl_telefono_clientes.setForeground(Color.BLACK);
        jl_direccion_clientes.setForeground(Color.BLACK);
        jl_buscar_clientes.setForeground(Color.BLACK);

        jt_dni_clientes.setBackground(Color.WHITE);
        jt_nombre_clientes.setBackground(Color.WHITE);
        jt_telefono_clientes.setBackground(Color.WHITE);
        jt_direccion_clientes.setBackground(Color.WHITE);
        jt_buscar_clientes.setBackground(Color.WHITE);

        jt_dni_clientes.setForeground(Color.BLACK);
        jt_nombre_clientes.setForeground(Color.BLACK);
        jt_telefono_clientes.setForeground(Color.BLACK);
        jt_direccion_clientes.setForeground(Color.BLACK);
        jt_buscar_clientes.setForeground(Color.BLACK);
    }

    public void iniciarComponentesJPProductos() {
        jl_codigo_productos.setForeground(Color.BLACK);
        jl_nombre_productos.setForeground(Color.BLACK);
        jl_stock_productos.setForeground(Color.BLACK);
        jl_precio_productos.setForeground(Color.BLACK);
        jl_proveedor_productos.setForeground(Color.BLACK);
        jl_buscar_productos.setForeground(Color.BLACK);
        jl_medidas_productos.setForeground(Color.BLACK);

        jt_codigo_productos.setBackground(Color.WHITE);
        jt_nombre_productos.setBackground(Color.WHITE);
        jt_stock_productos.setBackground(Color.WHITE);
        jt_precio_productos.setBackground(Color.WHITE);
        jt_buscar_productos.setBackground(Color.WHITE);

        jt_codigo_productos.setForeground(Color.BLACK);
        jt_nombre_productos.setForeground(Color.BLACK);
        jt_stock_productos.setForeground(Color.BLACK);
        jt_precio_productos.setForeground(Color.BLACK);
        jt_buscar_productos.setForeground(Color.BLACK);

        jcb_proveedor_productos.setBackground(Color.WHITE);
        jcb_proveedor_productos.setForeground(Color.BLACK);
        jcb_medidas_productos.setBackground(Color.WHITE);
        jcb_medidas_productos.setForeground(Color.BLACK);
        jb_agregar_productos.setBackground(Color.WHITE);
    }

    public void iniciarComponentesJPConfig() {
        jl_rif_config.setForeground(Color.BLACK);
        jl_nombreNegocio_config.setForeground(Color.BLACK);
        jl_nombrePropietario_config.setForeground(Color.BLACK);
        jl_telefono_config.setForeground(Color.BLACK);
        jl_municipio_config.setForeground(Color.BLACK);
        jl_estado_config.setForeground(Color.BLACK);
        jl_direccion_config.setForeground(Color.BLACK);
        jl_datosNegocio_config.setForeground(Color.BLACK);
        jl_datosNegocio_config.putClientProperty("FlatLaf.styleClass", "h1");

        jt_rif_config.setBackground(Color.WHITE);
        jt_nombreNegocio_config.setBackground(Color.WHITE);
        jt_nombrePropietario_config.setBackground(Color.WHITE);
        jt_telefono_config.setBackground(Color.WHITE);
        jt_municipio_config.setBackground(Color.WHITE);
        jt_estado_config.setBackground(Color.WHITE);
        jt_direccion_config.setBackground(Color.WHITE);

        jt_rif_config.setForeground(Color.BLACK);
        jt_nombreNegocio_config.setForeground(Color.BLACK);
        jt_nombrePropietario_config.setForeground(Color.BLACK);
        jt_telefono_config.setForeground(Color.BLACK);
        jt_municipio_config.setForeground(Color.BLACK);
        jt_estado_config.setForeground(Color.BLACK);
        jt_direccion_config.setForeground(Color.BLACK);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jb_vender_panel = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jl_header_fecha = new javax.swing.JLabel();
        jl_bienvenida = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jp_inicio = new javax.swing.JPanel();
        jl_ventas_vender = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jb_depositar_inicio = new javax.swing.JButton();
        jb_retirar_inicio = new javax.swing.JButton();
        jl_dinero_inicio = new javax.swing.JLabel();
        jt_dinero_inicio = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        jp_vender = new javax.swing.JPanel();
        jl_cod_ventas = new javax.swing.JLabel();
        jl_descripcion_ventas = new javax.swing.JLabel();
        jl_cantidad_ventas = new javax.swing.JLabel();
        jl_precio_ventas = new javax.swing.JLabel();
        jl_stock_ventas = new javax.swing.JLabel();
        jb_eliminar_ventas = new javax.swing.JButton();
        jt_descripcion_ventas = new javax.swing.JTextField();
        jt_cantidad_ventas = new javax.swing.JTextField();
        jt_precio_ventas = new javax.swing.JTextField();
        jt_stock_ventas = new javax.swing.JTextField();
        jt_cod_ventas = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_vender = new javax.swing.JTable();
        jb_credito_ventas = new javax.swing.JButton();
        jl_totalPagar_ventas = new javax.swing.JLabel();
        jl_mostrarTotal_ventas = new javax.swing.JLabel();
        jl_cedula_ventas = new javax.swing.JLabel();
        jl_nombreClientes_ventas = new javax.swing.JLabel();
        jt_cedula_ventas = new javax.swing.JTextField();
        jt_nombreClientes_ventas = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jb_guardar_ventas1 = new javax.swing.JButton();
        jt_telefonoClientes_ventas = new javax.swing.JTextField();
        jt_direccionClientes_ventas = new javax.swing.JTextField();
        jl_medidas_ventas = new javax.swing.JLabel();
        jt_medidas_ventas = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        jp_clientes = new javax.swing.JPanel();
        jl_dni_clientes = new javax.swing.JLabel();
        jl_nombre_clientes = new javax.swing.JLabel();
        jl_telefono_clientes = new javax.swing.JLabel();
        jl_direccion_clientes = new javax.swing.JLabel();
        jt_dni_clientes = new javax.swing.JTextField();
        jt_nombre_clientes = new javax.swing.JTextField();
        jt_telefono_clientes = new javax.swing.JTextField();
        jt_direccion_clientes = new javax.swing.JTextField();
        jb_guardar_clientes = new javax.swing.JButton();
        jb_borrar_clientes = new javax.swing.JButton();
        jb_actualizar_clientes = new javax.swing.JButton();
        jb_nuevo_clientes = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jt_clientes = new javax.swing.JTable();
        jl_buscar_clientes = new javax.swing.JLabel();
        jt_buscar_clientes = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jt_id_clientes = new javax.swing.JTextField();
        jp_proveedor = new javax.swing.JPanel();
        jt_telefono_provedor = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jt_direccion_provedor = new javax.swing.JTextField();
        jb_guardar_provedor = new javax.swing.JButton();
        jb_borrar_provedor = new javax.swing.JButton();
        jl_rif_prevedor = new javax.swing.JLabel();
        jb_actualizar_provedor = new javax.swing.JButton();
        jl_nombre_provedor = new javax.swing.JLabel();
        jb_nuevo_provedor = new javax.swing.JButton();
        jl_telefono_provedor = new javax.swing.JLabel();
        jl_direccion_provedor = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jt_provedor = new javax.swing.JTable();
        jt_rif_provedor = new javax.swing.JTextField();
        jl_buscar_provedor = new javax.swing.JLabel();
        jt_nombre_provedor = new javax.swing.JTextField();
        jt_buscar_provedor = new javax.swing.JTextField();
        jt_id_proveedor = new javax.swing.JTextField();
        jp_productos = new javax.swing.JPanel();
        jt_precio_productos = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jb_guardar_productos = new javax.swing.JButton();
        jb_exportar_productos = new javax.swing.JButton();
        jl_nombre_productos = new javax.swing.JLabel();
        jb_actualizar_productos = new javax.swing.JButton();
        jl_stock_productos = new javax.swing.JLabel();
        jb_nuevo_productos = new javax.swing.JButton();
        jl_precio_productos = new javax.swing.JLabel();
        jl_proveedor_productos = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jt_productos = new javax.swing.JTable();
        jt_nombre_productos = new javax.swing.JTextField();
        jl_buscar_productos = new javax.swing.JLabel();
        jt_stock_productos = new javax.swing.JTextField();
        jt_buscar_productos = new javax.swing.JTextField();
        jl_codigo_productos = new javax.swing.JLabel();
        jt_codigo_productos = new javax.swing.JTextField();
        jb_borrar_productos = new javax.swing.JButton();
        jcb_proveedor_productos = new javax.swing.JComboBox<>();
        jt_id_productos = new javax.swing.JTextField();
        jl_medidas_productos = new javax.swing.JLabel();
        jcb_medidas_productos = new javax.swing.JComboBox<>();
        jb_agregar_productos = new javax.swing.JButton();
        jp_ventas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jt_ventas = new javax.swing.JTable();
        jb_pdf_ventas = new javax.swing.JButton();
        jt_id_ventas = new javax.swing.JTextField();
        calendario_ventas = new com.toedter.calendar.JDateChooser();
        jb_reporte_ventas = new javax.swing.JButton();
        jt_seleccionar_ventas = new javax.swing.JLabel();
        jb_ventasDia_ventas = new javax.swing.JButton();
        jb_productosMenosStock_ventas = new javax.swing.JButton();
        jp_credito = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jt_credito = new javax.swing.JTable();
        jl_buscar_credito = new javax.swing.JLabel();
        jt_buscar_credito = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        jt_abonar_credito = new javax.swing.JTextField();
        jb_abonar_credito = new javax.swing.JButton();
        jb_eliminar_credito = new javax.swing.JButton();
        jl_abonar_credito = new javax.swing.JLabel();
        jt_id_credito = new javax.swing.JTextField();
        jt_monto_credito = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        jl_text_abonar = new javax.swing.JLabel();
        jl_nombre_abonar = new javax.swing.JLabel();
        jp_config = new javax.swing.JPanel();
        jl_datosNegocio_config = new javax.swing.JLabel();
        jl_rif_config = new javax.swing.JLabel();
        jl_nombreNegocio_config = new javax.swing.JLabel();
        jl_telefono_config = new javax.swing.JLabel();
        jl_estado_config = new javax.swing.JLabel();
        jl_municipio_config = new javax.swing.JLabel();
        jl_nombrePropietario_config = new javax.swing.JLabel();
        jl_direccion_config = new javax.swing.JLabel();
        jb_actualizar_config = new javax.swing.JButton();
        jt_rif_config = new javax.swing.JTextField();
        jt_nombreNegocio_config = new javax.swing.JTextField();
        jt_nombrePropietario_config = new javax.swing.JTextField();
        jt_telefono_config = new javax.swing.JTextField();
        jt_municipio_config = new javax.swing.JTextField();
        jt_estado_config = new javax.swing.JTextField();
        jt_direccion_config = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jt_id_config = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(245, 255, 255));

        jButton4.setText("INICIO");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jb_vender_panel.setText("NUEVA VENTA");
        jb_vender_panel.setBorder(null);
        jb_vender_panel.setBorderPainted(false);
        jb_vender_panel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_vender_panelActionPerformed(evt);
            }
        });

        jButton6.setText("CLIENTES");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("PROVEEDOR");
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("PRODUCTOS");
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("VENTAS");
        jButton9.setBorder(null);
        jButton9.setBorderPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("CREDITO");
        jButton10.setBorder(null);
        jButton10.setBorderPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton3.setText("CONFIG");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Imagen4-removebg-preview.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jb_vender_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jb_vender_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 550));

        jl_header_fecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/calendario.png"))); // NOI18N
        jl_header_fecha.setText("Fecha Actual:");
        jPanel11.add(jl_header_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        jl_bienvenida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/perfil.png"))); // NOI18N
        jl_bienvenida.setText("Hola, bienvenido");
        jl_bienvenida.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jl_bienvenida.setIconTextGap(10);
        jPanel11.add(jl_bienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/header.png"))); // NOI18N
        jPanel11.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 810, 100));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jp_inicio.setBackground(new java.awt.Color(255, 255, 255));
        jp_inicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jl_ventas_vender.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jl_ventas_vender.setForeground(new java.awt.Color(153, 255, 153));
        jl_ventas_vender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jl_ventas_vender.setText("0.00");
        jp_inicio.add(jl_ventas_vender, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 100, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/cajaregistradora.png"))); // NOI18N
        jp_inicio.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 14, 540, 426));

        jb_depositar_inicio.setText("DEPOSITAR");
        jb_depositar_inicio.setBorder(null);
        jb_depositar_inicio.setBorderPainted(false);
        jb_depositar_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_depositar_inicioActionPerformed(evt);
            }
        });
        jp_inicio.add(jb_depositar_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 130, 50));

        jb_retirar_inicio.setText("RETIRAR");
        jb_retirar_inicio.setBorder(null);
        jb_retirar_inicio.setBorderPainted(false);
        jb_retirar_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_retirar_inicioActionPerformed(evt);
            }
        });
        jp_inicio.add(jb_retirar_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 130, 50));

        jl_dinero_inicio.setText("Ingrese el dinero");
        jp_inicio.add(jl_dinero_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, -1, -1));

        jt_dinero_inicio.setBorder(null);
        jp_inicio.add(jt_dinero_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 150, -1));
        jp_inicio.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 150, 10));

        jTabbedPane1.addTab("tab1", jp_inicio);

        jp_vender.setBackground(new java.awt.Color(255, 255, 255));
        jp_vender.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jl_cod_ventas.setText("COD");
        jp_vender.add(jl_cod_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 27, -1, -1));

        jl_descripcion_ventas.setText("DESCRIPCIÓN");
        jp_vender.add(jl_descripcion_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 27, -1, -1));

        jl_cantidad_ventas.setText("CANTIDAD");
        jp_vender.add(jl_cantidad_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 27, -1, -1));

        jl_precio_ventas.setText("PRECIO");
        jp_vender.add(jl_precio_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 27, -1, -1));

        jl_stock_ventas.setText("STOCK");
        jp_vender.add(jl_stock_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 27, -1, -1));

        jb_eliminar_ventas.setText("ELIMINAR");
        jb_eliminar_ventas.setBorder(null);
        jb_eliminar_ventas.setBorderPainted(false);
        jb_eliminar_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_eliminar_ventasActionPerformed(evt);
            }
        });
        jp_vender.add(jb_eliminar_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 34, 66, 35));

        jt_descripcion_ventas.setBorder(null);
        jt_descripcion_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_descripcion_ventasActionPerformed(evt);
            }
        });
        jt_descripcion_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_descripcion_ventasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jt_descripcion_ventasKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_descripcion_ventasKeyTyped(evt);
            }
        });
        jp_vender.add(jt_descripcion_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 49, 215, -1));

        jt_cantidad_ventas.setBorder(null);
        jt_cantidad_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_cantidad_ventasActionPerformed(evt);
            }
        });
        jt_cantidad_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cantidad_ventasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_cantidad_ventasKeyTyped(evt);
            }
        });
        jp_vender.add(jt_cantidad_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 49, 71, -1));

        jt_precio_ventas.setEditable(false);
        jt_precio_ventas.setBorder(null);
        jt_precio_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_precio_ventasActionPerformed(evt);
            }
        });
        jp_vender.add(jt_precio_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 49, 71, -1));

        jt_stock_ventas.setEditable(false);
        jt_stock_ventas.setBorder(null);
        jp_vender.add(jt_stock_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 49, 71, -1));

        jt_cod_ventas.setBorder(null);
        jt_cod_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_cod_ventasActionPerformed(evt);
            }
        });
        jt_cod_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cod_ventasKeyPressed(evt);
            }
        });
        jp_vender.add(jt_cod_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 49, 50, -1));
        jp_vender.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 71, 50, 13));
        jp_vender.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 71, 215, 10));
        jp_vender.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 71, 71, 10));
        jp_vender.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 71, 71, 10));
        jp_vender.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 71, 71, 10));

        jt_vender.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCIÓN", "CANTIDAD", "U. MEDIDA", "PRECIO", "TOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jt_vender);
        if (jt_vender.getColumnModel().getColumnCount() > 0) {
            jt_vender.getColumnModel().getColumn(0).setPreferredWidth(30);
            jt_vender.getColumnModel().getColumn(1).setPreferredWidth(100);
            jt_vender.getColumnModel().getColumn(2).setPreferredWidth(30);
            jt_vender.getColumnModel().getColumn(3).setPreferredWidth(50);
            jt_vender.getColumnModel().getColumn(4).setPreferredWidth(30);
            jt_vender.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        jp_vender.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 109, 719, 254));

        jb_credito_ventas.setText("CREDITO");
        jb_credito_ventas.setBorder(null);
        jb_credito_ventas.setBorderPainted(false);
        jb_credito_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_credito_ventasActionPerformed(evt);
            }
        });
        jp_vender.add(jb_credito_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 391, 79, 38));

        jl_totalPagar_ventas.setText("TOTAL A PAGAR");
        jp_vender.add(jl_totalPagar_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(606, 402, -1, -1));

        jl_mostrarTotal_ventas.setText("---");
        jp_vender.add(jl_mostrarTotal_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 402, 51, -1));

        jl_cedula_ventas.setText("CEDULA");
        jp_vender.add(jl_cedula_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 377, -1, -1));

        jl_nombreClientes_ventas.setText("NOMBRE CLIENTE");
        jp_vender.add(jl_nombreClientes_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 377, -1, -1));

        jt_cedula_ventas.setBorder(null);
        jt_cedula_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_cedula_ventasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_cedula_ventasKeyTyped(evt);
            }
        });
        jp_vender.add(jt_cedula_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 399, 128, -1));

        jt_nombreClientes_ventas.setBorder(null);
        jp_vender.add(jt_nombreClientes_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 399, 173, -1));
        jp_vender.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 421, 128, 10));
        jp_vender.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 421, 173, 10));

        jb_guardar_ventas1.setText("DEBITO");
        jb_guardar_ventas1.setBorder(null);
        jb_guardar_ventas1.setBorderPainted(false);
        jb_guardar_ventas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_guardar_ventas1ActionPerformed(evt);
            }
        });
        jp_vender.add(jb_guardar_ventas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 391, 79, 38));

        jt_telefonoClientes_ventas.setText("jTextField1");
        jp_vender.add(jt_telefonoClientes_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 437, 71, -1));

        jt_direccionClientes_ventas.setText("jTextField1");
        jp_vender.add(jt_direccionClientes_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 437, 71, -1));

        jl_medidas_ventas.setText("U. Medida");
        jp_vender.add(jl_medidas_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 27, -1, -1));

        jt_medidas_ventas.setEditable(false);
        jt_medidas_ventas.setBorder(null);
        jt_medidas_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_medidas_ventasActionPerformed(evt);
            }
        });
        jt_medidas_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_medidas_ventasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_medidas_ventasKeyTyped(evt);
            }
        });
        jp_vender.add(jt_medidas_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 49, 71, -1));
        jp_vender.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 71, 71, 10));

        jTabbedPane1.addTab("tab2", jp_vender);

        jp_clientes.setBackground(new java.awt.Color(255, 255, 255));
        jp_clientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jl_dni_clientes.setText("DNI/RIF:");
        jp_clientes.add(jl_dni_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 90, -1, -1));

        jl_nombre_clientes.setText("NOMBRE:");
        jp_clientes.add(jl_nombre_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 140, -1, -1));

        jl_telefono_clientes.setText("TELEFONO:");
        jp_clientes.add(jl_telefono_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 190, -1, -1));

        jl_direccion_clientes.setText("DIRECCIÓN:");
        jp_clientes.add(jl_direccion_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jt_dni_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_dni_clientesKeyTyped(evt);
            }
        });
        jp_clientes.add(jt_dni_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 90, 170, -1));

        jt_nombre_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_nombre_clientesKeyTyped(evt);
            }
        });
        jp_clientes.add(jt_nombre_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 140, 170, -1));

        jt_telefono_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_telefono_clientesKeyTyped(evt);
            }
        });
        jp_clientes.add(jt_telefono_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 190, 170, -1));
        jp_clientes.add(jt_direccion_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 170, -1));

        jb_guardar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Guardar.png"))); // NOI18N
        jb_guardar_clientes.setText("GUARDAR");
        jb_guardar_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_guardar_clientes.setBorderPainted(false);
        jb_guardar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_guardar_clientes.setIconTextGap(10);
        jb_guardar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_guardar_clientesActionPerformed(evt);
            }
        });
        jp_clientes.add(jb_guardar_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 321, -1, -1));

        jb_borrar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Eliminar.png"))); // NOI18N
        jb_borrar_clientes.setText("ELIMINAR");
        jb_borrar_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_borrar_clientes.setBorderPainted(false);
        jb_borrar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_borrar_clientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_borrar_clientes.setIconTextGap(10);
        jb_borrar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_borrar_clientesActionPerformed(evt);
            }
        });
        jp_clientes.add(jb_borrar_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 384, -1, -1));

        jb_actualizar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Editar.png"))); // NOI18N
        jb_actualizar_clientes.setText("EDITAR");
        jb_actualizar_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_actualizar_clientes.setBorderPainted(false);
        jb_actualizar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_actualizar_clientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_actualizar_clientes.setIconTextGap(10);
        jb_actualizar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_actualizar_clientesActionPerformed(evt);
            }
        });
        jp_clientes.add(jb_actualizar_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 321, 100, -1));

        jb_nuevo_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Nuevo.png"))); // NOI18N
        jb_nuevo_clientes.setText("NUEVO");
        jb_nuevo_clientes.setBorder(null);
        jb_nuevo_clientes.setBorderPainted(false);
        jb_nuevo_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_nuevo_clientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_nuevo_clientes.setIconTextGap(10);
        jb_nuevo_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_nuevo_clientesActionPerformed(evt);
            }
        });
        jp_clientes.add(jb_nuevo_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 384, 100, -1));

        jt_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RIF", "NOMBRE", "TELEFONO", "DIRECCIÓN"
            }
        ));
        jt_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_clientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jt_clientes);
        if (jt_clientes.getColumnModel().getColumnCount() > 0) {
            jt_clientes.getColumnModel().getColumn(0).setPreferredWidth(5);
            jt_clientes.getColumnModel().getColumn(1).setPreferredWidth(20);
            jt_clientes.getColumnModel().getColumn(2).setPreferredWidth(110);
            jt_clientes.getColumnModel().getColumn(3).setPreferredWidth(10);
            jt_clientes.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jp_clientes.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 90, 525, 360));

        jl_buscar_clientes.setText("BUSCAR:");
        jp_clientes.add(jl_buscar_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 59, -1));

        jt_buscar_clientes.setBorder(null);
        jt_buscar_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_buscar_clientesKeyPressed(evt);
            }
        });
        jp_clientes.add(jt_buscar_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 34, 265, -1));
        jp_clientes.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 59, 265, 10));
        jp_clientes.add(jt_id_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 31, 20, -1));

        jTabbedPane1.addTab("tab3", jp_clientes);

        jp_proveedor.setBackground(new java.awt.Color(255, 255, 255));
        jp_proveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jt_telefono_provedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_telefono_provedorKeyTyped(evt);
            }
        });
        jp_proveedor.add(jt_telefono_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 188, 160, -1));
        jp_proveedor.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 260, 10));
        jp_proveedor.add(jt_direccion_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 238, 160, -1));

        jb_guardar_provedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Guardar.png"))); // NOI18N
        jb_guardar_provedor.setText("GUARDAR");
        jb_guardar_provedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_guardar_provedor.setBorderPainted(false);
        jb_guardar_provedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_guardar_provedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_guardar_provedor.setIconTextGap(10);
        jb_guardar_provedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_guardar_provedorActionPerformed(evt);
            }
        });
        jp_proveedor.add(jb_guardar_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jb_borrar_provedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Eliminar.png"))); // NOI18N
        jb_borrar_provedor.setText("ELIMINAR");
        jb_borrar_provedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_borrar_provedor.setBorderPainted(false);
        jb_borrar_provedor.setContentAreaFilled(false);
        jb_borrar_provedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_borrar_provedor.setHideActionText(true);
        jb_borrar_provedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_borrar_provedor.setIconTextGap(10);
        jb_borrar_provedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_borrar_provedorActionPerformed(evt);
            }
        });
        jp_proveedor.add(jb_borrar_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jl_rif_prevedor.setText("RIF:");
        jp_proveedor.add(jl_rif_prevedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 88, 22, -1));

        jb_actualizar_provedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Editar.png"))); // NOI18N
        jb_actualizar_provedor.setText("EDITAR");
        jb_actualizar_provedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_actualizar_provedor.setBorderPainted(false);
        jb_actualizar_provedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_actualizar_provedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_actualizar_provedor.setIconTextGap(10);
        jb_actualizar_provedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_actualizar_provedorActionPerformed(evt);
            }
        });
        jp_proveedor.add(jb_actualizar_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 104, -1));

        jl_nombre_provedor.setText("NOMBRE:");
        jp_proveedor.add(jl_nombre_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 138, 54, -1));

        jb_nuevo_provedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Nuevo.png"))); // NOI18N
        jb_nuevo_provedor.setText("NUEVO");
        jb_nuevo_provedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_nuevo_provedor.setBorderPainted(false);
        jb_nuevo_provedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_nuevo_provedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_nuevo_provedor.setIconTextGap(10);
        jb_nuevo_provedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_nuevo_provedorActionPerformed(evt);
            }
        });
        jp_proveedor.add(jb_nuevo_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 104, -1));

        jl_telefono_provedor.setText("TELEFONO:");
        jp_proveedor.add(jl_telefono_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 188, 62, -1));

        jl_direccion_provedor.setText("DIRECCIÓN:");
        jp_proveedor.add(jl_direccion_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 238, 67, -1));

        jt_provedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RIF", "NOMBRE", "TELEFONO", "DIRECCIÓN"
            }
        ));
        jt_provedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_provedorMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jt_provedor);
        if (jt_provedor.getColumnModel().getColumnCount() > 0) {
            jt_provedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            jt_provedor.getColumnModel().getColumn(1).setPreferredWidth(20);
            jt_provedor.getColumnModel().getColumn(2).setPreferredWidth(110);
            jt_provedor.getColumnModel().getColumn(3).setPreferredWidth(10);
            jt_provedor.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jp_proveedor.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 88, 525, 365));

        jt_rif_provedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_rif_provedorKeyTyped(evt);
            }
        });
        jp_proveedor.add(jt_rif_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 88, 160, -1));

        jl_buscar_provedor.setText("BUSCAR:");
        jp_proveedor.add(jl_buscar_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 40, 59, -1));

        jt_nombre_provedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_nombre_provedorKeyTyped(evt);
            }
        });
        jp_proveedor.add(jt_nombre_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(101, 138, 160, -1));

        jt_buscar_provedor.setBorder(null);
        jt_buscar_provedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_buscar_provedorKeyPressed(evt);
            }
        });
        jp_proveedor.add(jt_buscar_provedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 40, 265, -1));
        jp_proveedor.add(jt_id_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 61, -1));

        jTabbedPane1.addTab("tab4", jp_proveedor);

        jp_productos.setBackground(new java.awt.Color(255, 255, 255));
        jp_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_productosMouseClicked(evt);
            }
        });
        jp_productos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jt_precio_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_precio_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jt_precio_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 138, -1));
        jp_productos.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 62, 234, 10));

        jb_guardar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Guardar.png"))); // NOI18N
        jb_guardar_productos.setText("GUARDAR");
        jb_guardar_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_guardar_productos.setBorderPainted(false);
        jb_guardar_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_guardar_productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_guardar_productos.setIconTextGap(10);
        jb_guardar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_guardar_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_guardar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 294, -1, -1));

        jb_exportar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Exel.png"))); // NOI18N
        jb_exportar_productos.setText("EXPORTAR");
        jb_exportar_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_exportar_productos.setBorderPainted(false);
        jb_exportar_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_exportar_productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_exportar_productos.setIconTextGap(10);
        jb_exportar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_exportar_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_exportar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 398, -1, 40));

        jl_nombre_productos.setText("NOMBRE:");
        jp_productos.add(jl_nombre_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jb_actualizar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Editar.png"))); // NOI18N
        jb_actualizar_productos.setText("EDITAR");
        jb_actualizar_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_actualizar_productos.setBorderPainted(false);
        jb_actualizar_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_actualizar_productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_actualizar_productos.setIconTextGap(10);
        jb_actualizar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_actualizar_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_actualizar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 294, 98, -1));

        jl_stock_productos.setText("CANTIDAD:");
        jp_productos.add(jl_stock_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jb_nuevo_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Nuevo.png"))); // NOI18N
        jb_nuevo_productos.setText("NUEVO");
        jb_nuevo_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_nuevo_productos.setBorderPainted(false);
        jb_nuevo_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_nuevo_productos.setHideActionText(true);
        jb_nuevo_productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_nuevo_productos.setIconTextGap(10);
        jb_nuevo_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_nuevo_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_nuevo_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 346, 98, -1));

        jl_precio_productos.setText("PRECIO:");
        jp_productos.add(jl_precio_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jl_proveedor_productos.setText("PROVEEDOR:");
        jp_productos.add(jl_proveedor_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 253, -1, -1));

        jt_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "NOMBRE", "U. MEDIDA", "CANTIDAD", "PRECIO", "PROVEEDOR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jt_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_productosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jt_productosMouseEntered(evt);
            }
        });
        jScrollPane5.setViewportView(jt_productos);
        if (jt_productos.getColumnModel().getColumnCount() > 0) {
            jt_productos.getColumnModel().getColumn(0).setPreferredWidth(5);
            jt_productos.getColumnModel().getColumn(1).setPreferredWidth(5);
            jt_productos.getColumnModel().getColumn(2).setPreferredWidth(100);
            jt_productos.getColumnModel().getColumn(3).setPreferredWidth(10);
            jt_productos.getColumnModel().getColumn(4).setPreferredWidth(10);
            jt_productos.getColumnModel().getColumn(5).setPreferredWidth(50);
            jt_productos.getColumnModel().getColumn(6).setPreferredWidth(70);
        }

        jp_productos.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 556, 362));

        jt_nombre_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_nombre_productosKeyTyped(evt);
            }
        });
        jp_productos.add(jt_nombre_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 138, -1));

        jl_buscar_productos.setText("BUSCAR:");
        jp_productos.add(jl_buscar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 40, 59, -1));

        jt_stock_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jt_stock_productosKeyTyped(evt);
            }
        });
        jp_productos.add(jt_stock_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 138, -1));

        jt_buscar_productos.setBorder(null);
        jt_buscar_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_buscar_productosKeyPressed(evt);
            }
        });
        jp_productos.add(jt_buscar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 40, 234, -1));

        jl_codigo_productos.setText("CÓDIGO:");
        jp_productos.add(jl_codigo_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
        jp_productos.add(jt_codigo_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 138, -1));

        jb_borrar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Eliminar.png"))); // NOI18N
        jb_borrar_productos.setText("ELIMINAR");
        jb_borrar_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_borrar_productos.setBorderPainted(false);
        jb_borrar_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_borrar_productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_borrar_productos.setIconTextGap(10);
        jb_borrar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_borrar_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_borrar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 346, -1, -1));

        jcb_proveedor_productos.setEditable(true);
        jp_productos.add(jcb_proveedor_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 250, 133, -1));

        jt_id_productos.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jt_id_productosInputMethodTextChanged(evt);
            }
        });
        jp_productos.add(jt_id_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 71, -1));

        jl_medidas_productos.setText("MEDIDA:");
        jp_productos.add(jl_medidas_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jcb_medidas_productos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidades", "Kg", "L" }));
        jcb_medidas_productos.setSelectedItem(null);
        jp_productos.add(jcb_medidas_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 138, -1));

        jb_agregar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/inventario.png"))); // NOI18N
        jb_agregar_productos.setText("Agregar Stock");
        jb_agregar_productos.setBorder(null);
        jb_agregar_productos.setBorderPainted(false);
        jb_agregar_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_agregar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_agregar_productosActionPerformed(evt);
            }
        });
        jp_productos.add(jb_agregar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, 40));

        jTabbedPane1.addTab("tab5", jp_productos);

        jp_ventas.setBackground(new java.awt.Color(255, 255, 255));
        jp_ventas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jt_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA"
            }
        ));
        jt_ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_ventasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jt_ventas);
        if (jt_ventas.getColumnModel().getColumnCount() > 0) {
            jt_ventas.getColumnModel().getColumn(0).setPreferredWidth(10);
            jt_ventas.getColumnModel().getColumn(1).setPreferredWidth(100);
            jt_ventas.getColumnModel().getColumn(2).setPreferredWidth(100);
            jt_ventas.getColumnModel().getColumn(3).setPreferredWidth(50);
            jt_ventas.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jp_ventas.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 119, 744, 320));

        jb_pdf_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/pdf.png"))); // NOI18N
        jb_pdf_ventas.setText("PDF");
        jb_pdf_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_pdf_ventas.setBorderPainted(false);
        jb_pdf_ventas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_pdf_ventas.setIconTextGap(10);
        jb_pdf_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pdf_ventasActionPerformed(evt);
            }
        });
        jp_ventas.add(jb_pdf_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 73, 71, -1));
        jp_ventas.add(jt_id_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 12, 71, -1));

        calendario_ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendario_ventasMouseClicked(evt);
            }
        });
        calendario_ventas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendario_ventasPropertyChange(evt);
            }
        });
        calendario_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                calendario_ventasKeyPressed(evt);
            }
        });
        jp_ventas.add(calendario_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 54, 143, -1));

        jb_reporte_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Grafica.png"))); // NOI18N
        jb_reporte_ventas.setText("GRAFICA");
        jb_reporte_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_reporte_ventas.setBorderPainted(false);
        jb_reporte_ventas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_reporte_ventas.setIconTextGap(10);
        jb_reporte_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_reporte_ventasActionPerformed(evt);
            }
        });
        jp_ventas.add(jb_reporte_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 103, -1));

        jt_seleccionar_ventas.setText("Seleccionar");
        jp_ventas.add(jt_seleccionar_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 26, -1, -1));

        jb_ventasDia_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Ganancias.png"))); // NOI18N
        jb_ventasDia_ventas.setText("GANANCIAS");
        jb_ventasDia_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_ventasDia_ventas.setBorderPainted(false);
        jb_ventasDia_ventas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_ventasDia_ventas.setIconTextGap(10);
        jb_ventasDia_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_ventasDia_ventasActionPerformed(evt);
            }
        });
        jp_ventas.add(jb_ventasDia_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, -1, 38));

        jb_productosMenosStock_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/productosBajoStock.png"))); // NOI18N
        jb_productosMenosStock_ventas.setText("PRODUCTOS");
        jb_productosMenosStock_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_productosMenosStock_ventas.setBorderPainted(false);
        jb_productosMenosStock_ventas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_productosMenosStock_ventas.setIconTextGap(10);
        jb_productosMenosStock_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_productosMenosStock_ventasActionPerformed(evt);
            }
        });
        jp_ventas.add(jb_productosMenosStock_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, 38));

        jTabbedPane1.addTab("tab6", jp_ventas);

        jp_credito.setBackground(new java.awt.Color(255, 255, 255));
        jp_credito.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jt_credito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "VENDEDOR", "DEUDA", "FECHA", "ID VENTA"
            }
        ));
        jt_credito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jt_creditoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jt_credito);
        if (jt_credito.getColumnModel().getColumnCount() > 0) {
            jt_credito.getColumnModel().getColumn(0).setPreferredWidth(5);
            jt_credito.getColumnModel().getColumn(1).setPreferredWidth(100);
            jt_credito.getColumnModel().getColumn(2).setPreferredWidth(100);
            jt_credito.getColumnModel().getColumn(3).setPreferredWidth(40);
            jt_credito.getColumnModel().getColumn(4).setPreferredWidth(60);
            jt_credito.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

        jp_credito.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 73, 780, 290));

        jl_buscar_credito.setText("BUSCAR:");
        jp_credito.add(jl_buscar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));

        jt_buscar_credito.setBorder(null);
        jt_buscar_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jt_buscar_creditoKeyPressed(evt);
            }
        });
        jp_credito.add(jt_buscar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 200, -1));
        jp_credito.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 200, 10));

        jt_abonar_credito.setBorder(null);
        jp_credito.add(jt_abonar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 170, -1));

        jb_abonar_credito.setText("ABONAR");
        jb_abonar_credito.setBorder(null);
        jb_abonar_credito.setBorderPainted(false);
        jb_abonar_credito.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_abonar_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_abonar_creditoActionPerformed(evt);
            }
        });
        jp_credito.add(jb_abonar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 116, 40));

        jb_eliminar_credito.setText("ELIMINAR");
        jb_eliminar_credito.setBorder(null);
        jb_eliminar_credito.setBorderPainted(false);
        jb_eliminar_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_eliminar_creditoActionPerformed(evt);
            }
        });
        jp_credito.add(jb_eliminar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 116, 40));

        jl_abonar_credito.setText("Ingrese la cantidad a abonar");
        jp_credito.add(jl_abonar_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 166, -1));

        jt_id_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_id_creditoActionPerformed(evt);
            }
        });
        jp_credito.add(jt_id_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 8, 60, -1));
        jp_credito.add(jt_monto_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 8, 60, -1));
        jp_credito.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 170, 10));

        jl_text_abonar.setText("Le esta abonando a:");
        jp_credito.add(jl_text_abonar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 400, -1, -1));

        jl_nombre_abonar.setText("User");
        jp_credito.add(jl_nombre_abonar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, 180, -1));

        jTabbedPane1.addTab("tab7", jp_credito);

        jp_config.setBackground(new java.awt.Color(255, 255, 255));
        jp_config.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jl_datosNegocio_config.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_datosNegocio_config.setText("DATOS DEL NEGOCIO");
        jp_config.add(jl_datosNegocio_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 24, 810, -1));

        jl_rif_config.setText("RIF");
        jp_config.add(jl_rif_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 95, -1, -1));

        jl_nombreNegocio_config.setText("NOMBRE DEL NEGOCIO");
        jp_config.add(jl_nombreNegocio_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 95, -1, -1));

        jl_telefono_config.setText("TELEFONO");
        jp_config.add(jl_telefono_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 203, -1, -1));

        jl_estado_config.setText("ESTADO");
        jp_config.add(jl_estado_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 203, -1, -1));

        jl_municipio_config.setText("MUNICIPIO");
        jp_config.add(jl_municipio_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 203, -1, -1));

        jl_nombrePropietario_config.setText("NOMBRE PROPIETARIO");
        jp_config.add(jl_nombrePropietario_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 95, -1, -1));

        jl_direccion_config.setText("DIRECCIÓN");
        jp_config.add(jl_direccion_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 312, -1, -1));

        jb_actualizar_config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagine/Editar.png"))); // NOI18N
        jb_actualizar_config.setText("GUARDAR");
        jb_actualizar_config.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jb_actualizar_config.setBorderPainted(false);
        jb_actualizar_config.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jb_actualizar_config.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jb_actualizar_config.setIconTextGap(10);
        jb_actualizar_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_actualizar_configActionPerformed(evt);
            }
        });
        jp_config.add(jb_actualizar_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, -1));

        jt_rif_config.setBorder(null);
        jp_config.add(jt_rif_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 129, 180, -1));

        jt_nombreNegocio_config.setBorder(null);
        jt_nombreNegocio_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt_nombreNegocio_configActionPerformed(evt);
            }
        });
        jp_config.add(jt_nombreNegocio_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 129, 180, -1));

        jt_nombrePropietario_config.setBorder(null);
        jp_config.add(jt_nombrePropietario_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 129, 180, -1));

        jt_telefono_config.setBorder(null);
        jp_config.add(jt_telefono_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 237, 180, -1));

        jt_municipio_config.setBorder(null);
        jp_config.add(jt_municipio_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 237, 180, -1));

        jt_estado_config.setBorder(null);
        jp_config.add(jt_estado_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 237, 180, -1));

        jt_direccion_config.setBorder(null);
        jp_config.add(jt_direccion_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 346, 180, -1));
        jp_config.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 151, 180, 10));
        jp_config.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 151, 180, 10));
        jp_config.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 151, 180, 10));
        jp_config.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 259, 180, 10));
        jp_config.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(316, 259, 180, 10));
        jp_config.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 259, 180, 10));
        jp_config.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 368, 180, 10));
        jp_config.add(jt_id_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 419, 71, -1));

        jTabbedPane1.addTab("tab8", jp_config);

        jPanel11.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 810, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jt_cod_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_cod_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_cod_ventasActionPerformed

    private void jt_precio_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_precio_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_precio_ventasActionPerformed

    private void jt_descripcion_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_descripcion_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_descripcion_ventasActionPerformed

    private void jb_eliminar_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_eliminar_ventasActionPerformed
        // TODO add your handling code here:
        if (jt_vender.getSelectedRow() != -1) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de eliminar la venta?");
            if (temp == 0) {
                modelo = (DefaultTableModel) jt_vender.getModel();
                modelo.removeRow(jt_vender.getSelectedRow());
                totalPagar();
                jt_cod_ventas.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para eliminar");
        }


    }//GEN-LAST:event_jb_eliminar_ventasActionPerformed

    private void jb_pdf_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pdf_ventasActionPerformed
        try {
            // TODO add your handling code here:
            int id = Integer.parseInt(jt_id_ventas.getText());
            String home = System.getProperty("user.home");
            File file = new File(home + "/Documents/pdf/venta" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_jb_pdf_ventasActionPerformed

    private void jt_nombreNegocio_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_nombreNegocio_configActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_nombreNegocio_configActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        limpiarTabla();
        listarCliente();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jb_vender_panelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_vender_panelActionPerformed
        // TODO add your handling code here:
        cargarSugerenciasNombre();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jb_vender_panelActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
        limpiarTabla();
        listarProveedor();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        listarProductos();
        removeItemByIndex();
        jcb_proveedor_productos.setSelectedItem(null);
        jcb_medidas_productos.setSelectedItem(null);
        jTabbedPane1.setSelectedIndex(4);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        listarVenta();
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        listarVentaCredito();
        jb_abonar_credito.setVisible(true);
        jb_eliminar_credito.setVisible(true);
        jTabbedPane1.setSelectedIndex(6);
        jl_nombre_abonar.setText("User");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jb_guardar_provedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_guardar_provedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_rif_provedor.getText()) || !"".equals(jt_nombre_provedor.getText()) || !"".equals(jt_telefono_provedor.getText()) || !"".equals(jt_direccion_provedor.getText())) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de registrar al proveedor?");
            if (temp == 0) {
                pr.setRif(Integer.parseInt(jt_rif_provedor.getText()));
                pr.setNombre(jt_nombre_provedor.getText());
                pr.setTelefono(Long.parseLong(jt_telefono_provedor.getText()));
                pr.setDireccion(jt_direccion_provedor.getText());
                proveedor.registrarProveedor(pr);
                check1 = 0;
                limpiarTabla();
                listarProveedor();
                limpiarProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor registrado con exito.");
            } else {
                limpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingresa datos en todos los campos");
        }

    }//GEN-LAST:event_jb_guardar_provedorActionPerformed

    private void jt_provedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_provedorMouseClicked
        // TODO add your handling code here:
        int fila = jt_provedor.rowAtPoint(evt.getPoint());
        jt_id_proveedor.setText(jt_provedor.getValueAt(fila, 0).toString());
        jt_rif_provedor.setText(jt_provedor.getValueAt(fila, 1).toString());
        jt_nombre_provedor.setText(jt_provedor.getValueAt(fila, 2).toString());
        jt_telefono_provedor.setText(jt_provedor.getValueAt(fila, 3).toString());
        jt_direccion_provedor.setText(jt_provedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_jt_provedorMouseClicked

    private void jb_borrar_provedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_borrar_provedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(jt_id_proveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de eliminar el proveedor?");
            if (pregunta == 0) {
                int id = Integer.parseInt(jt_id_proveedor.getText());
                proveedor.eliminarProveedor(id);
                check1 = 0;
                limpiarTabla();
                listarProveedor();
                limpiarProveedor();
            }
        }
    }//GEN-LAST:event_jb_borrar_provedorActionPerformed

    private void jb_nuevo_provedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_nuevo_provedorActionPerformed
        // TODO add your handling code here:
        limpiarProveedor();
    }//GEN-LAST:event_jb_nuevo_provedorActionPerformed

    private void jb_actualizar_provedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_actualizar_provedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(jt_id_proveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");

        } else {
            if (!"".equals(jt_rif_provedor.getText()) && !"".equals(jt_nombre_provedor.getText()) && !"".equals(jt_telefono_provedor.getText()) && !"".equals(jt_direccion_provedor.getText())) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de modificar el proveedor");
                if (temp == 0) {
                    pr.setId(Integer.parseInt(jt_id_proveedor.getText()));
                    pr.setRif(Integer.parseInt(jt_rif_provedor.getText()));
                    pr.setNombre(jt_nombre_provedor.getText());
                    pr.setTelefono(Long.parseLong(jt_telefono_provedor.getText()));
                    pr.setDireccion(jt_direccion_provedor.getText());
                    proveedor.actualizarProveedor(pr);
                    check1 = 0;
                    limpiarTabla();
                    listarProveedor();
                    limpiarProveedor();
                } else {
                    limpiarProveedor();
                }
            }
        }
    }//GEN-LAST:event_jb_actualizar_provedorActionPerformed

    private void jb_guardar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_guardar_productosActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_codigo_productos.getText()) && !"".equals(jt_nombre_productos.getText()) && !"".equals(jt_stock_productos.getText()) && !"".equals(jt_precio_productos.getText()) && !"".equals(jcb_proveedor_productos.getSelectedItem().toString()) && !"".equals(jcb_medidas_productos.getSelectedItem().toString())) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de registrar el producto?");
            if (temp == 0) {
                prod.setCodigo(jt_codigo_productos.getText());
                prod.setNombre(jt_nombre_productos.getText());
                prod.setMedida(jcb_medidas_productos.getSelectedItem().toString());
                prod.setCantidad(Double.parseDouble(jt_stock_productos.getText()));
                prod.setPrecio(Double.parseDouble(jt_precio_productos.getText()));
                prod.setProveedor(jcb_proveedor_productos.getSelectedItem().toString());
                productos.registrarProductos(prod);
                limpiarTabla();
                listarProductos();
                limpiarProductos();
                JOptionPane.showMessageDialog(null, "Producto registrado con exito.");
            } else {
                limpiarProductos();
            }

        } else
            JOptionPane.showMessageDialog(null, "Rellena todos los campos.");
    }//GEN-LAST:event_jb_guardar_productosActionPerformed

    private void jt_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_productosMouseClicked
        // TODO add your handling code here:
        int fila = jt_productos.rowAtPoint(evt.getPoint());
        jt_id_productos.setText(jt_productos.getValueAt(fila, 0).toString());
        jt_codigo_productos.setText(jt_productos.getValueAt(fila, 1).toString());
        jt_nombre_productos.setText(jt_productos.getValueAt(fila, 2).toString());
        jcb_medidas_productos.setSelectedItem(jt_productos.getValueAt(fila, 3).toString());
        jt_stock_productos.setText(jt_productos.getValueAt(fila, 4).toString());
        jt_precio_productos.setText(jt_productos.getValueAt(fila, 5).toString());
        jcb_proveedor_productos.setSelectedItem(jt_productos.getValueAt(fila, 6).toString());
        int sele = jt_productos.getSelectedRowCount();

    }//GEN-LAST:event_jt_productosMouseClicked

    private void jb_borrar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_borrar_productosActionPerformed
        // TODO add your handling code here:
        if ("".equals(jt_id_productos.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de eliminar el producto?");
            if (pregunta == 0) {
                int id = Integer.parseInt(jt_id_productos.getText());
                productos.eliminarProductos(id);
                limpiarTabla();
                listarProductos();
                limpiarProductos();
            }
        }
    }//GEN-LAST:event_jb_borrar_productosActionPerformed

    private void jb_actualizar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_actualizar_productosActionPerformed
        // TODO add your handling code here:
        if ("".equals(jt_id_productos.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");

        } else {
            if (!"".equals(jt_codigo_productos.getText()) && !"".equals(jt_nombre_productos.getText()) && !"".equals(jt_stock_productos.getText()) && !"".equals(jt_precio_productos.getText()) && !"".equals(jcb_proveedor_productos.getSelectedItem().toString()) && !"".equals(jcb_medidas_productos.getSelectedItem().toString())) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de modificar el producto");
                if (temp == 0) {
                    prod.setId(Integer.parseInt(jt_id_productos.getText()));
                    prod.setCodigo(jt_codigo_productos.getText());
                    prod.setNombre(jt_nombre_productos.getText());
                    prod.setMedida(jcb_medidas_productos.getSelectedItem().toString());
                    prod.setCantidad(Double.parseDouble(jt_stock_productos.getText()));
                    prod.setPrecio(Double.parseDouble(jt_precio_productos.getText()));
                    prod.setProveedor(jcb_proveedor_productos.getSelectedItem().toString());
                    productos.actualizarProductos(prod);
                    limpiarTabla();
                    listarProductos();
                    limpiarProductos();
                } else {
                    limpiarProductos();
                }
            }
        }
    }//GEN-LAST:event_jb_actualizar_productosActionPerformed

    private void jb_exportar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_exportar_productosActionPerformed
        // TODO add your handling code here:
        Excel.reporte();
    }//GEN-LAST:event_jb_exportar_productosActionPerformed

    private void jt_cod_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cod_ventasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jt_cod_ventas.getText())) {
                String cod = jt_cod_ventas.getText();
                prod = productos.buscarProductos(cod);
                if (prod.getNombre() != null) {
                    jt_descripcion_ventas.setText("" + prod.getNombre());
                    jt_precio_ventas.setText("" + prod.getPrecio());
                    jt_stock_ventas.setText("" + prod.getCantidad());
                    jt_medidas_ventas.setText("" + prod.getMedida());
                    if ("Kg".equals(jt_medidas_ventas.getText()) || "L".equals(jt_medidas_ventas.getText())) {
                        activarNumDecimal = 0;
                        activarNumEntero = 1;
                    }
                    if ("Unidades".equals(jt_medidas_ventas.getText())) {
                        activarNumEntero = 0;
                        activarNumDecimal = 1;
                    }

                    jt_cantidad_ventas.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no existente");
                    limpiarVenta();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un codigo");
            }
        }
    }//GEN-LAST:event_jt_cod_ventasKeyPressed

    private void jt_cantidad_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cantidad_ventasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jt_cantidad_ventas.getText())) {
                String cod = jt_cod_ventas.getText();
                String nombre = jt_descripcion_ventas.getText();
                double stock = Double.parseDouble(jt_stock_ventas.getText());
                double cantidad = Double.parseDouble(jt_cantidad_ventas.getText());
                String medida = jt_medidas_ventas.getText();
                double precio = Double.parseDouble(jt_precio_ventas.getText());

                if (cantidad <= stock) {
                    double total = (cantidad * precio);
                    item = item + 1;
                    tmp = (DefaultTableModel) jt_vender.getModel();
                    for (int i = 0; i < jt_vender.getRowCount(); i++) {
                        if (jt_vender.getValueAt(i, 1).equals(jt_descripcion_ventas.getText())) {
                            JOptionPane.showMessageDialog(null, "Producto ya esta agregado");
                            limpiarVenta();
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(nombre);
                    lista.add(cantidad);
                    lista.add(medida);
                    lista.add(precio);
                    lista.add(total);
                    Object[] ob = new Object[6];
                    ob[0] = lista.get(1);
                    ob[1] = lista.get(2);
                    ob[2] = lista.get(3);
                    ob[3] = lista.get(4);
                    ob[4] = lista.get(5);
                    ob[5] = lista.get(6);
                    tmp.addRow(ob);
                    jt_vender.setModel(tmp);
                    totalPagar();
                    limpiarVenta();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                    limpiarTablaVenta();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad");
            }
        }
    }//GEN-LAST:event_jt_cantidad_ventasKeyPressed

    private void jt_cantidad_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_cantidad_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_cantidad_ventasActionPerformed

    private void jb_nuevo_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_nuevo_productosActionPerformed
        // TODO add your handling code here:
        limpiarProductos();
    }//GEN-LAST:event_jb_nuevo_productosActionPerformed

    private void jt_cedula_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cedula_ventasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jt_cedula_ventas.getText())) {
                int dni = Integer.parseInt(jt_cedula_ventas.getText());
                cl = cliente.buscarCliente(dni);
                if (cl.getNombre() != null) {
                    jt_nombreClientes_ventas.setText("" + cl.getNombre());
                    jt_telefonoClientes_ventas.setText("" + cl.getTelefono());
                    jt_direccionClientes_ventas.setText("" + cl.getDireccion());
                } else {
                    jt_cedula_ventas.setText("");
                    JOptionPane.showMessageDialog(null, "Cliente no existe");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese una cedula");
            }
        }
    }//GEN-LAST:event_jt_cedula_ventasKeyPressed

    private void jb_guardar_ventas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_guardar_ventas1ActionPerformed
        // TODO add your handling code here:
        if (jt_vender.getRowCount() > 0) {
            if (!"".equals(jt_nombreClientes_ventas.getText())) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de guardar la venta?");
                if (temp == 0) {
                    tipoVenta = "Debito";
                    registrarVenta();
                    registrarDetalleVenta();
                    actualizarStock();
                    dineroCaja();
                    pdf();

                    limpiarTablaVenta();
                    limpiarTabla();
                    //////////////////////
                    tmp = (DefaultTableModel) jt_vender.getModel();
                    int filas = jt_vender.getRowCount();

                    for (int i = 0; i < filas; i++) {
                        tmp.removeRow(0);
                    }
                    ////////////////////////
                    JOptionPane.showMessageDialog(null, "Venta exitosa");
                } else {
                    limpiarTablaVenta();
                    limpiarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un cliente");
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingrese productos");
    }//GEN-LAST:event_jb_guardar_ventas1ActionPerformed

    private void jt_cantidad_ventasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cantidad_ventasKeyTyped
        // TODO add your handling code here:
        if (activarNumDecimal == 0) {
            event.numberDecimalKeyPress(evt, jt_cantidad_ventas);
        }

        if (activarNumEntero == 0) {
            event.numberKeyPress(evt);
        }

    }//GEN-LAST:event_jt_cantidad_ventasKeyTyped

    private void jt_descripcion_ventasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_descripcion_ventasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_descripcion_ventasKeyTyped

    private void jt_cedula_ventasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_cedula_ventasKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_jt_cedula_ventasKeyTyped

    private void jt_rif_provedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_rif_provedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_rif_provedorKeyTyped

    private void jt_nombre_provedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_nombre_provedorKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_jt_nombre_provedorKeyTyped

    private void jt_telefono_provedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_telefono_provedorKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_jt_telefono_provedorKeyTyped

    private void jt_nombre_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_nombre_productosKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jt_nombre_productosKeyTyped

    private void jt_stock_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_stock_productosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_stock_productosKeyTyped

    private void jb_actualizar_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_actualizar_configActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_rif_config.getText()) && !"".equals(jt_nombreNegocio_config.getText()) && !"".equals(jt_nombrePropietario_config.getText()) && !"".equals(jt_telefono_config.getText()) && !"".equals(jt_municipio_config.getText()) && !"".equals(jt_estado_config.getText()) && !"".equals(jt_direccion_config.getText())) {
            DatosEmpresa datosE = new DatosEmpresa();
            datosE.setRif(jt_rif_config.getText());
            datosE.setNombreNegocio(jt_nombreNegocio_config.getText());
            datosE.setNombrePropietario(jt_nombrePropietario_config.getText());
            datosE.setTelefono(Long.parseLong(jt_telefono_config.getText()));
            datosE.setMunicipio(jt_municipio_config.getText());
            datosE.setEstado(jt_estado_config.getText());
            datosE.setDireccion(jt_direccion_config.getText());
            datosEmpresaDAO.actualizarDatosEmpresa(datosE);
            JOptionPane.showMessageDialog(null, "Datos actualizados con exito.");
            listarDetalleEmpresa();
        } else
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
    }//GEN-LAST:event_jb_actualizar_configActionPerformed

    private void jt_ventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_ventasMouseClicked
        // TODO add your handling code here:
        int fila = jt_ventas.rowAtPoint(evt.getPoint());
        jt_id_ventas.setText(jt_ventas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_jt_ventasMouseClicked

    private void jb_reporte_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_reporte_ventasActionPerformed
        // TODO add your handling code here:
        if (checkBuscarGrafico == 1) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = calendario_ventas.getDate();
            String fechaFormateada = formato.format(fecha);
            Grafico.graficar(fechaFormateada);
            /*double ventas = ventasDia.obtenerVentasDelDia(fechaFormateada);
            jt_ventasDia_ventas.setText("" + ventas);*/
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha");
        }

    }//GEN-LAST:event_jb_reporte_ventasActionPerformed

    private void jt_buscar_provedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_buscar_provedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            limpiarTabla();
            buscarProveedor();
        }
    }//GEN-LAST:event_jt_buscar_provedorKeyPressed

    private void jt_buscar_productosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_buscar_productosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            limpiarTabla();
            buscarProductos();
        }
    }//GEN-LAST:event_jt_buscar_productosKeyPressed

    private void calendario_ventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendario_ventasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_calendario_ventasMouseClicked

    private void jb_ventasDia_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_ventasDia_ventasActionPerformed
        // TODO add your handling code here:
        if (checkBuscarGrafico == 1) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = calendario_ventas.getDate();
            String fechaFormateada = formato.format(fecha);
            GraficoDia.graficar(fechaFormateada);
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un fecha");
        }
    }//GEN-LAST:event_jb_ventasDia_ventasActionPerformed

    private void jb_depositar_inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_depositar_inicioActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_dinero_inicio.getText())) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de depositar el dinero?");
            if (temp == 0) {
                double depositar = Double.parseDouble(jt_dinero_inicio.getText());
                dineroCaja = dineroCaja + depositar;
                dineroCajaDAO.guardarDinero(dineroCaja);
                jt_dinero_inicio.setText("");
                JOptionPane.showMessageDialog(null, "Dinero agregado");
                double dineroTemp = dineroCajaDAO.consultarDineroCaja();
                jl_ventas_vender.setText(String.valueOf(dineroTemp));
                jt_dinero_inicio.setText("");
            } else {
                jt_dinero_inicio.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad al campo");
        }

    }//GEN-LAST:event_jb_depositar_inicioActionPerformed

    private void jb_retirar_inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_retirar_inicioActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_dinero_inicio.getText())) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de retirar el dinero?");
            if (temp == 0) {
                double retirar = Double.parseDouble(jt_dinero_inicio.getText());
                if (retirar > dineroCaja) {
                    JOptionPane.showMessageDialog(null, "No hay esa cantidad en la caja para retirar");
                } else {
                    dineroCaja = dineroCaja - retirar;
                    dineroCajaDAO.guardarDinero(dineroCaja);
                    jt_dinero_inicio.setText("");
                    JOptionPane.showMessageDialog(null, "Dinero retirado");
                    double dineroTemp = dineroCajaDAO.consultarDineroCaja();
                    jl_ventas_vender.setText(String.valueOf(dineroTemp));
                    jt_dinero_inicio.setText("");
                }
            } else {
                jt_dinero_inicio.setText("");
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad al campo");
    }//GEN-LAST:event_jb_retirar_inicioActionPerformed

    private void jt_precio_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_precio_productosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_precio_productosActionPerformed

    private void jt_medidas_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_medidas_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_medidas_ventasActionPerformed

    private void jt_medidas_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_medidas_ventasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_medidas_ventasKeyPressed

    private void jt_medidas_ventasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_medidas_ventasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_medidas_ventasKeyTyped

    private void jt_buscar_clientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_buscar_clientesKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            limpiarTabla();
            buscarClientes();
        }
    }//GEN-LAST:event_jt_buscar_clientesKeyPressed

    private void jt_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_clientesMouseClicked
        // TODO add your handling code here:
        int fila = jt_clientes.rowAtPoint(evt.getPoint());
        jt_id_clientes.setText(jt_clientes.getValueAt(fila, 0).toString());
        jt_dni_clientes.setText(jt_clientes.getValueAt(fila, 1).toString());
        jt_nombre_clientes.setText(jt_clientes.getValueAt(fila, 2).toString());
        jt_telefono_clientes.setText(jt_clientes.getValueAt(fila, 3).toString());
        jt_direccion_clientes.setText(jt_clientes.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_jt_clientesMouseClicked

    private void jb_nuevo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_nuevo_clientesActionPerformed
        // TODO add your handling code here:
        limpiarClientes();
    }//GEN-LAST:event_jb_nuevo_clientesActionPerformed

    private void jb_actualizar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_actualizar_clientesActionPerformed
        // TODO add your handling code here:
        if ("".equals(jt_id_clientes.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");
        } else {
            if (!"".equals(jt_dni_clientes.getText()) && !"".equals(jt_nombre_clientes.getText()) && !"".equals(jt_telefono_clientes.getText()) && !"".equals(jt_direccion_clientes.getText())) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de modificar el cliente?");
                if (temp == 0) {
                    cl.setId(Integer.parseInt(jt_id_clientes.getText()));
                    cl.setDni(Integer.parseInt(jt_dni_clientes.getText()));
                    cl.setNombre(jt_nombre_clientes.getText());
                    cl.setTelefono(Long.parseLong(jt_telefono_clientes.getText()));
                    cl.setDireccion(jt_direccion_clientes.getText());
                    cliente.actualizarCliente(cl);
                    limpiarTabla();
                    listarCliente();
                    limpiarClientes();
                } else {
                    limpiarClientes();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingresa datos en todos los campos.");
            }
        }
    }//GEN-LAST:event_jb_actualizar_clientesActionPerformed

    private void jb_borrar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_borrar_clientesActionPerformed
        // TODO add your handling code here:
        if (!"".equals(jt_id_clientes.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de eliminar el cliente?");
            if (pregunta == 0) {
                int id = Integer.parseInt(jt_id_clientes.getText());
                cliente.eliminarCliente(id);
                limpiarTabla();
                listarCliente();
                limpiarClientes();
            } else {
                limpiarClientes();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro.");
        }
    }//GEN-LAST:event_jb_borrar_clientesActionPerformed

    private void jb_guardar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_guardar_clientesActionPerformed
        if (!"".equals(jt_dni_clientes.getText()) || !"".equals(jt_nombre_clientes.getText()) || !"".equals(jt_telefono_clientes.getText()) || !"".equals(jt_direccion_clientes.getText())) {
            int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de registrar el cliente?");
            if (temp == 0) {
                cl.setDni(Integer.parseInt(jt_dni_clientes.getText()));
                cl.setNombre(jt_nombre_clientes.getText());
                cl.setTelefono(Long.parseLong(jt_telefono_clientes.getText()));
                cl.setDireccion(jt_direccion_clientes.getText());
                cliente.registrarCliente(cl);
                limpiarTabla();
                listarCliente();
                limpiarClientes();
                JOptionPane.showMessageDialog(null, "Cliente registrado con exito");
            } else {
                limpiarClientes();
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingresa datos en todos los campos");
    }//GEN-LAST:event_jb_guardar_clientesActionPerformed

    private void jt_telefono_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_telefono_clientesKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_jt_telefono_clientesKeyTyped

    private void jt_nombre_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_nombre_clientesKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_jt_nombre_clientesKeyTyped

    private void jt_dni_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_dni_clientesKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_jt_dni_clientesKeyTyped

    private void jb_credito_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_credito_ventasActionPerformed
        // TODO add your handling code here:
        if (jt_vender.getRowCount() > 0) {
            if (!"".equals(jt_nombreClientes_ventas.getText())) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de registrar como credito?");
                if (temp == 0) {
                    tipoVenta = "Credito";
                    registrarVenta();
                    registrarDetalleVenta();
                    actualizarStock();
                    pdf();
                    registrarVentaCredito();

                    limpiarTablaVenta();
                    limpiarTabla();
                    //////////
                    tmp = (DefaultTableModel) jt_vender.getModel();
                    int filas = jt_vender.getRowCount();

                    for (int i = 0; i < filas; i++) {
                        tmp.removeRow(0);
                    }
                    //////////
                    JOptionPane.showMessageDialog(null, "Se agrego a ventas credito");
                } else {
                    limpiarTablaVenta();
                    limpiarTabla();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un cliente");
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingrese productos");
    }//GEN-LAST:event_jb_credito_ventasActionPerformed

    private void jt_creditoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_creditoMouseClicked
        // TODO add your handling code here:
        int fila = jt_credito.rowAtPoint(evt.getPoint());
        jt_id_credito.setText(jt_credito.getValueAt(fila, 0).toString());
        jt_monto_credito.setText(jt_credito.getValueAt(fila, 3).toString());
        double montoJTCredito = Double.parseDouble(jt_monto_credito.getText());
        if (montoJTCredito == 0) {
            jb_eliminar_credito.setVisible(true);
            jb_abonar_credito.setVisible(false);
        }
        if (montoJTCredito > 0) {
            jb_eliminar_credito.setVisible(false);
            jb_abonar_credito.setVisible(true);
        }
        jl_nombre_abonar.setText(jt_credito.getValueAt(fila, 1).toString());
    }//GEN-LAST:event_jt_creditoMouseClicked

    private void jb_eliminar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_eliminar_creditoActionPerformed
        // TODO add your handling code here:
        int temp = JOptionPane.showConfirmDialog(null, "¿Esta seguro de continuar?");
        if (temp == 0) {
            int id = Integer.parseInt(jt_id_credito.getText());
            credito.eliminar(id);
            limpiarTabla();
            listarVentaCredito();
        }
    }//GEN-LAST:event_jb_eliminar_creditoActionPerformed

    private void jb_abonar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_abonar_creditoActionPerformed
        // TODO add your handling code here:
        if (jt_credito.getSelectedRow() != -1) {
            double total = Double.parseDouble(jt_monto_credito.getText());
            double montoAbonar = Double.parseDouble(jt_abonar_credito.getText());
            int id = Integer.parseInt(jt_id_credito.getText());
            if (montoAbonar <= total) {
                int temp = JOptionPane.showConfirmDialog(null, "¿Esta segur@ de abonar ese monto?");
                if (temp == 0) {
                    double montoAbonarCredito = total - montoAbonar;
                    credito.abonar(montoAbonarCredito, id);
                    vd.setTotal(montoAbonar);
                    vd.setTipo("Credito");
                    vd.setFecha(fechaActual);
                    ventaDiaria.registrarVentaDiaria(vd);

                    dineroCaja = dineroCaja + montoAbonar;
                    dineroCajaDAO.guardarDinero(dineroCaja);
                    double dineroTemp = dineroCajaDAO.consultarDineroCaja();
                    jl_ventas_vender.setText(String.valueOf(dineroTemp));

                    jt_abonar_credito.setText("");

                    limpiarTabla();
                    listarVentaCredito();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El monto ingresado supera la deuda");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro");
        }

    }//GEN-LAST:event_jb_abonar_creditoActionPerformed

    private void jt_buscar_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_buscar_creditoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            limpiarTabla();
            buscarVentaCredito();
        }
    }//GEN-LAST:event_jt_buscar_creditoKeyPressed

    private void calendario_ventasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendario_ventasPropertyChange
        // TODO add your handling code here:
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = calendario_ventas.getDate();
        if (fecha != null) {
            String fechaFormateada = formato.format(fecha);
            limpiarTabla();
            buscarVenta(fechaFormateada);
            checkBuscarGrafico = 1;
        }
    }//GEN-LAST:event_calendario_ventasPropertyChange

    private void calendario_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calendario_ventasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_calendario_ventasKeyPressed

    private void jt_descripcion_ventasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_descripcion_ventasKeyPressed

    }//GEN-LAST:event_jt_descripcion_ventasKeyPressed

    private void jb_productosMenosStock_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_productosMenosStock_ventasActionPerformed
        // TODO add your handling code here:
        GraficoProductosBajoStock.graficar();
    }//GEN-LAST:event_jb_productosMenosStock_ventasActionPerformed

    private void jt_id_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt_id_creditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_id_creditoActionPerformed

    private void jb_agregar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_agregar_productosActionPerformed
        // TODO add your handling code here:
        agregarStock = new AgregarStock();
        agregarStock.setVisible(true);
        agregarStock.setLocationRelativeTo(null);
    }//GEN-LAST:event_jb_agregar_productosActionPerformed

    private void jp_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_productosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jp_productosMouseClicked

    private void jt_productosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jt_productosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jt_productosMouseEntered

    private void jt_id_productosInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jt_id_productosInputMethodTextChanged

    }//GEN-LAST:event_jt_id_productosInputMethodTextChanged

    private void jt_descripcion_ventasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jt_descripcion_ventasKeyReleased
        // TODO add your handling code here
        
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jt_descripcion_ventas.getText())) {
                //////
                String nombre = jt_descripcion_ventas.getText();
                prod = productos.buscarProductosNombre(nombre);
                if (prod.getCodigo() != null) {
                    jt_cod_ventas.setText("" + prod.getCodigo());
                    jt_precio_ventas.setText("" + prod.getPrecio());
                    jt_stock_ventas.setText("" + prod.getCantidad());
                    jt_medidas_ventas.setText("" + prod.getMedida());
                    if ("Kg".equals(jt_medidas_ventas.getText()) || "L".equals(jt_medidas_ventas.getText())) {
                        activarNumDecimal = 0;
                        activarNumEntero = 1;
                    }
                    if ("Unidades".equals(jt_medidas_ventas.getText())) {
                        activarNumEntero = 0;
                        activarNumDecimal = 1;
                    }

                    jt_cantidad_ventas.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no existente");
                    limpiarVenta();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un nombre");
            }
        }
    }//GEN-LAST:event_jt_descripcion_ventasKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser calendario_ventas;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jb_abonar_credito;
    private javax.swing.JButton jb_actualizar_clientes;
    private javax.swing.JButton jb_actualizar_config;
    private javax.swing.JButton jb_actualizar_productos;
    private javax.swing.JButton jb_actualizar_provedor;
    private javax.swing.JButton jb_agregar_productos;
    private javax.swing.JButton jb_borrar_clientes;
    private javax.swing.JButton jb_borrar_productos;
    private javax.swing.JButton jb_borrar_provedor;
    private javax.swing.JButton jb_credito_ventas;
    private javax.swing.JButton jb_depositar_inicio;
    private javax.swing.JButton jb_eliminar_credito;
    private javax.swing.JButton jb_eliminar_ventas;
    private javax.swing.JButton jb_exportar_productos;
    private javax.swing.JButton jb_guardar_clientes;
    private javax.swing.JButton jb_guardar_productos;
    private javax.swing.JButton jb_guardar_provedor;
    private javax.swing.JButton jb_guardar_ventas1;
    private javax.swing.JButton jb_nuevo_clientes;
    private javax.swing.JButton jb_nuevo_productos;
    private javax.swing.JButton jb_nuevo_provedor;
    private javax.swing.JButton jb_pdf_ventas;
    private javax.swing.JButton jb_productosMenosStock_ventas;
    private javax.swing.JButton jb_reporte_ventas;
    private javax.swing.JButton jb_retirar_inicio;
    private javax.swing.JButton jb_vender_panel;
    private javax.swing.JButton jb_ventasDia_ventas;
    private javax.swing.JComboBox<String> jcb_medidas_productos;
    private javax.swing.JComboBox<String> jcb_proveedor_productos;
    private javax.swing.JLabel jl_abonar_credito;
    private javax.swing.JLabel jl_bienvenida;
    private javax.swing.JLabel jl_buscar_clientes;
    private javax.swing.JLabel jl_buscar_credito;
    private javax.swing.JLabel jl_buscar_productos;
    private javax.swing.JLabel jl_buscar_provedor;
    private javax.swing.JLabel jl_cantidad_ventas;
    private javax.swing.JLabel jl_cedula_ventas;
    private javax.swing.JLabel jl_cod_ventas;
    private javax.swing.JLabel jl_codigo_productos;
    private javax.swing.JLabel jl_datosNegocio_config;
    private javax.swing.JLabel jl_descripcion_ventas;
    private javax.swing.JLabel jl_dinero_inicio;
    private javax.swing.JLabel jl_direccion_clientes;
    private javax.swing.JLabel jl_direccion_config;
    private javax.swing.JLabel jl_direccion_provedor;
    private javax.swing.JLabel jl_dni_clientes;
    private javax.swing.JLabel jl_estado_config;
    private javax.swing.JLabel jl_header_fecha;
    private javax.swing.JLabel jl_medidas_productos;
    private javax.swing.JLabel jl_medidas_ventas;
    private javax.swing.JLabel jl_mostrarTotal_ventas;
    private javax.swing.JLabel jl_municipio_config;
    private javax.swing.JLabel jl_nombreClientes_ventas;
    private javax.swing.JLabel jl_nombreNegocio_config;
    private javax.swing.JLabel jl_nombrePropietario_config;
    private javax.swing.JLabel jl_nombre_abonar;
    private javax.swing.JLabel jl_nombre_clientes;
    private javax.swing.JLabel jl_nombre_productos;
    private javax.swing.JLabel jl_nombre_provedor;
    private javax.swing.JLabel jl_precio_productos;
    private javax.swing.JLabel jl_precio_ventas;
    private javax.swing.JLabel jl_proveedor_productos;
    private javax.swing.JLabel jl_rif_config;
    private javax.swing.JLabel jl_rif_prevedor;
    private javax.swing.JLabel jl_stock_productos;
    private javax.swing.JLabel jl_stock_ventas;
    private javax.swing.JLabel jl_telefono_clientes;
    private javax.swing.JLabel jl_telefono_config;
    private javax.swing.JLabel jl_telefono_provedor;
    private javax.swing.JLabel jl_text_abonar;
    private javax.swing.JLabel jl_totalPagar_ventas;
    private javax.swing.JLabel jl_ventas_vender;
    private javax.swing.JPanel jp_clientes;
    private javax.swing.JPanel jp_config;
    private javax.swing.JPanel jp_credito;
    private javax.swing.JPanel jp_inicio;
    private javax.swing.JPanel jp_productos;
    private javax.swing.JPanel jp_proveedor;
    private javax.swing.JPanel jp_vender;
    private javax.swing.JPanel jp_ventas;
    private javax.swing.JTextField jt_abonar_credito;
    private javax.swing.JTextField jt_buscar_clientes;
    private javax.swing.JTextField jt_buscar_credito;
    private javax.swing.JTextField jt_buscar_productos;
    private javax.swing.JTextField jt_buscar_provedor;
    private javax.swing.JTextField jt_cantidad_ventas;
    private javax.swing.JTextField jt_cedula_ventas;
    private javax.swing.JTable jt_clientes;
    private javax.swing.JTextField jt_cod_ventas;
    private javax.swing.JTextField jt_codigo_productos;
    private javax.swing.JTable jt_credito;
    private javax.swing.JTextField jt_descripcion_ventas;
    private javax.swing.JTextField jt_dinero_inicio;
    private javax.swing.JTextField jt_direccionClientes_ventas;
    private javax.swing.JTextField jt_direccion_clientes;
    private javax.swing.JTextField jt_direccion_config;
    private javax.swing.JTextField jt_direccion_provedor;
    private javax.swing.JTextField jt_dni_clientes;
    private javax.swing.JTextField jt_estado_config;
    private javax.swing.JTextField jt_id_clientes;
    private javax.swing.JTextField jt_id_config;
    private javax.swing.JTextField jt_id_credito;
    private javax.swing.JTextField jt_id_productos;
    private javax.swing.JTextField jt_id_proveedor;
    private javax.swing.JTextField jt_id_ventas;
    private javax.swing.JTextField jt_medidas_ventas;
    private javax.swing.JTextField jt_monto_credito;
    private javax.swing.JTextField jt_municipio_config;
    private javax.swing.JTextField jt_nombreClientes_ventas;
    private javax.swing.JTextField jt_nombreNegocio_config;
    private javax.swing.JTextField jt_nombrePropietario_config;
    private javax.swing.JTextField jt_nombre_clientes;
    private javax.swing.JTextField jt_nombre_productos;
    private javax.swing.JTextField jt_nombre_provedor;
    private javax.swing.JTextField jt_precio_productos;
    private javax.swing.JTextField jt_precio_ventas;
    private javax.swing.JTable jt_productos;
    private javax.swing.JTable jt_provedor;
    private javax.swing.JTextField jt_rif_config;
    private javax.swing.JTextField jt_rif_provedor;
    private javax.swing.JLabel jt_seleccionar_ventas;
    private javax.swing.JTextField jt_stock_productos;
    private javax.swing.JTextField jt_stock_ventas;
    private javax.swing.JTextField jt_telefonoClientes_ventas;
    private javax.swing.JTextField jt_telefono_clientes;
    private javax.swing.JTextField jt_telefono_config;
    private javax.swing.JTextField jt_telefono_provedor;
    private javax.swing.JTable jt_vender;
    private javax.swing.JTable jt_ventas;
    // End of variables declaration//GEN-END:variables
    private void limpiarClientes() {
        jt_id_clientes.setText("");
        jt_dni_clientes.setText("");
        jt_nombre_clientes.setText("");
        jt_telefono_clientes.setText("");
        jt_direccion_clientes.setText("");
    }

    private void limpiarProveedor() {
        jt_id_proveedor.setText("");
        jt_rif_provedor.setText("");
        jt_nombre_provedor.setText("");
        jt_telefono_provedor.setText("");
        jt_direccion_provedor.setText("");
    }

    private void limpiarProductos() {
        jt_id_productos.setText("");
        jt_codigo_productos.setText("");
        jt_nombre_productos.setText("");
        jt_stock_productos.setText("");
        jt_precio_productos.setText("");
        jcb_proveedor_productos.setSelectedItem(null);
        jcb_medidas_productos.setSelectedItem(null);

    }

    private void totalPagar() {
        totalPagar = 0.00;
        int numFila = jt_vender.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double calcular = Double.parseDouble(String.valueOf(jt_vender.getModel().getValueAt(i, 5)));
            totalPagar = totalPagar + calcular;
        }
        jl_mostrarTotal_ventas.setText(String.format("%.2f", totalPagar));
    }

    private void limpiarVenta() {
        jt_cod_ventas.setText("");
        jt_descripcion_ventas.setText("");
        jt_cantidad_ventas.setText("");
        jt_medidas_ventas.setText("");
        jt_stock_ventas.setText("");
        jt_precio_ventas.setText("");
        jt_cod_ventas.requestFocus();
    }

    private void registrarVenta() {
        String cliente = jt_nombreClientes_ventas.getText();
        String vended = vendedor;
        double monto = totalPagar;
        v.setCliente(cliente);
        v.setVendedor(vended);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        venta.registrarVenta(v);
        if (tipoVenta.equals("Debito")) {
            vd.setTotal(monto);
            vd.setTipo(tipoVenta);
            vd.setFecha(fechaActual);
            ventaDiaria.registrarVentaDiaria(vd);
        }
    }

    private void registrarDetalleVenta() {
        int id = venta.ventaID();
        for (int i = 0; i < jt_vender.getRowCount(); i++) {
            String cod = jt_vender.getValueAt(i, 0).toString();
            double cantidad = Double.parseDouble(jt_vender.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(jt_vender.getValueAt(i, 4).toString());
            dv.setCodigoProducto(cod);
            dv.setCantidad(cantidad);
            dv.setPrecio(precio);
            dv.setVentaID(id);
            venta.registrarDetalle(dv);
            System.out.println(id);
        }
    }

    private void actualizarStock() {
        for (int i = 0; i < jt_vender.getRowCount(); i++) {
            String codigo = jt_vender.getValueAt(i, 0).toString();
            double cantidad = Double.parseDouble(jt_vender.getValueAt(i, 2).toString());
            prod = productos.buscarProductos(codigo);
            double stockActual = prod.getCantidad() - cantidad;
            venta.actualizarStock(stockActual, codigo);
        }
    }

    private void limpiarTablaVenta() {
        jt_cod_ventas.setText("");
        jt_descripcion_ventas.setText("");
        jt_cantidad_ventas.setText("");
        jt_medidas_ventas.setText("");
        jt_precio_ventas.setText("");
        jt_stock_ventas.setText("");
        jt_nombreClientes_ventas.setText("");
        jt_cedula_ventas.setText("");
        jt_telefonoClientes_ventas.setText("");
        jt_direccionClientes_ventas.setText("");
        jl_mostrarTotal_ventas.setText("---");
        jt_cod_ventas.requestFocus();

        /*tmp = (DefaultTableModel) jt_vender.getModel();
        int filas = jt_vender.getRowCount();

        for (int i = 0; i < filas; i++) {
            tmp.removeRow(0);
        }*/
    }

    private void pdf() {
        try {
            int id = venta.ventaID();
            FileOutputStream archivo;
            String home = System.getProperty("user.home");

            File file = new File(home + "/Documents/pdf/venta" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            URL rutaImagen = getClass().getResource("/imagine/seniat.png");
            Image img = Image.getInstance(rutaImagen);

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: " + id + "\n" + "Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado = new float[]{
                20f, 30f, 70f, 40f
            };
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            encabezado.addCell(img);

            String rif = jt_rif_config.getText();
            String nombreNegocio = jt_nombreNegocio_config.getText();
            String nombrePropietario = jt_nombrePropietario_config.getText();
            String telefono = jt_telefono_config.getText();
            String municipio = jt_municipio_config.getText();
            String estado = jt_estado_config.getText();
            String direccion = jt_direccion_config.getText();

            encabezado.addCell("");
            encabezado.addCell("Propietario: " + nombrePropietario + "\n" + nombreNegocio + "\nRif: " + rif + "\n" + "Cel: " + telefono + "\n" + direccion + "\n" + municipio + " - " + estado);
            encabezado.addCell(fecha);
            doc.add(encabezado);

            Paragraph clientes = new Paragraph();
            clientes.add(Chunk.NEWLINE);
            clientes.add("Datos de los clientes" + "\n\n");
            doc.add(clientes);

            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            float[] columnaCliente = new float[]{
                20f, 50f, 30f, 40f
            };
            tablaCliente.setWidths(columnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliente1 = new PdfPCell(new Phrase("Cedula", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell cliente4 = new PdfPCell(new Phrase("Dirección", negrita));
            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            cliente4.setBorder(0);
            tablaCliente.addCell(cliente1);
            tablaCliente.addCell(cliente2);
            tablaCliente.addCell(cliente3);
            tablaCliente.addCell(cliente4);
            tablaCliente.addCell(jt_cedula_ventas.getText());
            tablaCliente.addCell(jt_nombreClientes_ventas.getText());
            tablaCliente.addCell(jt_telefonoClientes_ventas.getText());
            tablaCliente.addCell(jt_direccionClientes_ventas.getText());
            tablaCliente.addCell("\n");
            tablaCliente.addCell("\n");
            tablaCliente.addCell("\n");
            tablaCliente.addCell("\n");

            doc.add(tablaCliente);

            //Productos
            PdfPTable tablaProductos = new PdfPTable(5);
            tablaProductos.setWidthPercentage(100);
            tablaProductos.getDefaultCell().setBorder(0);
            float[] columnaProductos = new float[]{
                15f, 15f, 50f, 15f, 20f
            };
            tablaProductos.setWidths(columnaProductos);
            tablaProductos.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell producto1 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell producto2 = new PdfPCell(new Phrase("U. Medida", negrita));
            PdfPCell producto3 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell producto4 = new PdfPCell(new Phrase("Precio Unit", negrita));
            PdfPCell producto5 = new PdfPCell(new Phrase("Precio Total", negrita));
            producto1.setBorder(0);
            producto2.setBorder(0);
            producto3.setBorder(0);
            producto4.setBorder(0);
            producto5.setBorder(0);

            producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            producto5.setBackgroundColor(BaseColor.LIGHT_GRAY);

            tablaProductos.addCell(producto1);
            tablaProductos.addCell(producto2);
            tablaProductos.addCell(producto3);
            tablaProductos.addCell(producto4);
            tablaProductos.addCell(producto5);

            for (int i = 0; i < jt_vender.getRowCount(); i++) {
                String producto = jt_vender.getValueAt(i, 1).toString();
                String cantidad = jt_vender.getValueAt(i, 2).toString();
                String medidad = jt_vender.getValueAt(i, 3).toString();
                String precio = jt_vender.getValueAt(i, 4).toString();
                String total = jt_vender.getValueAt(i, 5).toString();

                tablaProductos.addCell(cantidad);
                tablaProductos.addCell(medidad);
                tablaProductos.addCell(producto);
                tablaProductos.addCell(precio);
                tablaProductos.addCell(total);
            }
            doc.add(tablaProductos);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: " + totalPagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion y firma\n\n");
            firma.add("-----------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra");
            mensaje.setAlignment(Element.ALIGN_RIGHT);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            System.out.println("Ruta del archivo: " + file.getAbsolutePath());
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }

    }

    public void buscarClientes() {
        @SuppressWarnings("unchecked")
        List<Cliente> listarCL = cliente.buscar(jt_buscar_clientes.getText());
        modelo = (DefaultTableModel) jt_clientes.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarCL.size(); i++) {
            ob[0] = listarCL.get(i).getId();
            ob[1] = listarCL.get(i).getDni();
            ob[2] = listarCL.get(i).getNombre();
            ob[3] = listarCL.get(i).getTelefono();
            ob[4] = listarCL.get(i).getDireccion();
            modelo.addRow(ob);
        }
        jt_clientes.setModel(modelo);
    }

    public void buscarProveedor() {
        @SuppressWarnings("unchecked")
        List<Proveedor> listarPR = proveedor.buscar(jt_buscar_provedor.getText());
        modelo = (DefaultTableModel) jt_provedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarPR.size(); i++) {
            ob[0] = listarPR.get(i).getId();
            ob[1] = listarPR.get(i).getRif();
            ob[2] = listarPR.get(i).getNombre();
            ob[3] = listarPR.get(i).getTelefono();
            ob[4] = listarPR.get(i).getDireccion();
            modelo.addRow(ob);
        }
        jt_provedor.setModel(modelo);
    }

    public void buscarProductos() {
        @SuppressWarnings("unchecked")
        List<Productos> listarPROD = productos.buscar(jt_buscar_productos.getText());
        modelo = (DefaultTableModel) jt_productos.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < listarPROD.size(); i++) {
            ob[0] = listarPROD.get(i).getId();
            ob[1] = listarPROD.get(i).getCodigo();
            ob[2] = listarPROD.get(i).getNombre();
            ob[3] = listarPROD.get(i).getMedida();
            ob[4] = listarPROD.get(i).getCantidad();
            ob[5] = listarPROD.get(i).getPrecio();
            ob[6] = listarPROD.get(i).getProveedor();
            modelo.addRow(ob);
        }
        jt_productos.setModel(modelo);
    }

    public void removeItemByIndex() {
        if (check1 == 0) {
            JComboBox<String> jcb_proveedor_producto = jcb_proveedor_productos;
            jcb_proveedor_producto.removeAllItems();

            productos.consultarProveedor(jcb_proveedor_producto);

            check1 = 1;
        }
    }

    public void registrarVentaCredito() {
        String cliente = jt_nombreClientes_ventas.getText();
        String vended = vendedor;
        double monto = totalPagar;
        cred.setCliente(cliente);
        cred.setVendedor(vended);
        cred.setTotal(monto);
        cred.setIdVentas(venta.ventaID());
        cred.setFecha(fechaActual);
        credito.registrarVentaCredito(cred);
    }

    public void listarVentaCredito() {
        @SuppressWarnings("unchecked")
        List<Credito> listarCredito = credito.ListarVentaCredito();
        modelo = (DefaultTableModel) jt_credito.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarCredito.size(); i++) {
            ob[0] = listarCredito.get(i).getId();
            ob[1] = listarCredito.get(i).getCliente();
            ob[2] = listarCredito.get(i).getVendedor();
            ob[3] = listarCredito.get(i).getTotal();
            ob[4] = listarCredito.get(i).getFecha();
            ob[5] = listarCredito.get(i).getIdVentas();
            modelo.addRow(ob);
        }
        jt_credito.setModel(modelo);
    }

    public void buscarVentaCredito() {
        String cliente = jt_buscar_credito.getText();

        @SuppressWarnings("unchecked")
        List<Credito> listarCredito = credito.buscar(cliente);
        modelo = (DefaultTableModel) jt_credito.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarCredito.size(); i++) {
            ob[0] = listarCredito.get(i).getId();
            ob[1] = listarCredito.get(i).getCliente();
            ob[2] = listarCredito.get(i).getVendedor();
            ob[3] = listarCredito.get(i).getTotal();
            ob[4] = listarCredito.get(i).getFecha();
            ob[5] = listarCredito.get(i).getIdVentas();
            modelo.addRow(ob);
        }
        jt_credito.setModel(modelo);
    }

    public void buscarVenta(String fecha) {
        @SuppressWarnings("unchecked")
        List<Venta> listarVentas = venta.buscar(fecha);
        modelo = (DefaultTableModel) jt_ventas.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listarVentas.size(); i++) {
            ob[0] = listarVentas.get(i).getId();
            ob[1] = listarVentas.get(i).getCliente();
            ob[2] = listarVentas.get(i).getVendedor();
            ob[3] = listarVentas.get(i).getTotal();
            ob[4] = listarVentas.get(i).getFecha();
            modelo.addRow(ob);
        }
        jt_ventas.setModel(modelo);
    }

    private void cargarSugerenciasNombre() {

        String[] tempNombre = productos.cargarSugerenciasNombre();
        ac.removeAllItems();
        for (int i = 0; i < tempNombre.length; i++) {
            ac.addItem(tempNombre[i]);
        }
    }
}