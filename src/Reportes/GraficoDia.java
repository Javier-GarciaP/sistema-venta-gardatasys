package Reportes;

import ModeloFinal.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class GraficoDia {
    public static void graficar(String fecha){
        Connection conn;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try{
            String consulta = "SELECT tipo, SUM(total)as total_dia FROM ventasdiarias WHERE fecha = ? GROUP BY tipo";
            conn = cn.getConnection();
            ps = conn.prepareStatement(consulta);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultPieDataset dataSet = new DefaultPieDataset();
            while(rs.next()){
                String tipo = rs.getString("tipo");
                Double total = rs.getDouble("total_dia");
                dataSet.setValue(tipo, total);
            }
            JFreeChart jf = ChartFactory.createPieChart("Reporte de Ganancias", dataSet);
            ChartFrame f = new ChartFrame("Total de Ventas del dia", jf);
            f.setSize(800, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }
}
