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

public class GraficoProductosBajoStock {
    public static void graficar(){
        Connection conn;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try{
            String consulta = "SELECT nombre, cantidad FROM productos WHERE cantidad <= 5";
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            while(rs.next()){
                String nombre = rs.getString("nombre");
                Double cantidad = rs.getDouble("cantidad");
                dataSet.setValue(cantidad,  "Bajo Stock",nombre);
            }
            JFreeChart jf = ChartFactory.createBarChart3D("Productos bajo stock", "Nombre", "Cantidad", dataSet, PlotOrientation.VERTICAL, true, true, false);
            ChartFrame f = new ChartFrame("Productos bajo stock", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
}
