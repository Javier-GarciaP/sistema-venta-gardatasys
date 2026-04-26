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
    public static void graficar(javax.swing.JTable tabla){
        try{
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            java.util.HashMap<String, Double> totales = new java.util.HashMap<>();
            
            for (int i = 0; i < tabla.getRowCount(); i++) {
                String cliente = tabla.getValueAt(i, 1).toString();
                double total = Double.parseDouble(tabla.getValueAt(i, 3).toString());
                totales.put(cliente, totales.getOrDefault(cliente, 0.0) + total);
            }
            
            for (String cliente : totales.keySet()) {
                dataSet.setValue(totales.get(cliente), "totalCliente", cliente);
            }
            
            JFreeChart jf = ChartFactory.createBarChart3D("Ventas Filtradas", "Clientes", "Monto", dataSet, PlotOrientation.VERTICAL, true, true, false);
            ChartFrame f = new ChartFrame("Total de Ventas Filtradas", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(Exception e){
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
