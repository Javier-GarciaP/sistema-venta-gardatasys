package Reportes;

import ModeloFinal.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Grafico {
    public static void graficar(String fecha){
        Connection conn;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try{
            String consulta = "SELECT cliente, SUM(total) as totalCliente FROM ventas WHERE fecha = ? GROUP BY cliente";
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            while(rs.next()){
                double total = rs.getDouble("totalCliente");
                String cliente = rs.getString("cliente");
                dataSet.setValue(total,  "totalCliente",cliente);
            }
            JFreeChart jf = ChartFactory.createBarChart3D("Ventas del Dia", "Clientes", "Monto", dataSet, PlotOrientation.VERTICAL, true, true, false);
            ChartFrame f = new ChartFrame("Total de Ventas del dia", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
}

/*
/////////////////////////////////////////
           GRAFICO DE PASTEL
/////////////////////////////////////////
public class Grafico {
    public static void graficar(String fecha){
        Connection conn;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try{
            String consulta = "SELECT total FROM ventas WHERE fecha = ?";
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultPieDataset dataSet = new DefaultPieDataset();
            while(rs.next()){
                dataSet.setValue(rs.getString("total"), rs.getDouble("total"));
            }
            JFreeChart jf = ChartFactory.createPieChart("Reporte de Venta", dataSet);
            ChartFrame f = new ChartFrame("Total de Ventas del dia", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
}
*/
