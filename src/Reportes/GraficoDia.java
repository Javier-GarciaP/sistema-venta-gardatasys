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
    public static void graficar(javax.swing.JTable tabla){
        try{
            DefaultPieDataset dataSet = new DefaultPieDataset();
            java.util.HashMap<String, Double> totales = new java.util.HashMap<>();
            
            for (int i = 0; i < tabla.getRowCount(); i++) {
                String tipo = tabla.getValueAt(i, 4).toString();
                double total = Double.parseDouble(tabla.getValueAt(i, 3).toString());
                totales.put(tipo, totales.getOrDefault(tipo, 0.0) + total);
            }
            
            for (String tipo : totales.keySet()) {
                dataSet.setValue(tipo, totales.get(tipo));
            }
            
            JFreeChart jf = ChartFactory.createPieChart("Ganancias Filtradas", dataSet);
            ChartFrame f = new ChartFrame("Total de Ganancias Filtradas", jf);
            f.setSize(800, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
