package ru.itmo.qa.lab2.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FunctionGraph extends JFrame {

  private static final long serialVersionUID = 1L;

  public FunctionGraph(String applicationTitle, String chartTitle, String csvFilePath) {
    super(applicationTitle);
    XYDataset dataset = createDataset(csvFilePath);
    JFreeChart chart = createChart(dataset, chartTitle);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
  }

  public static XYDataset createDataset(String csvFilePath) {
    XYSeries series = new XYSeries("f(x)");
    Path path = Paths.get(csvFilePath);

    try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
      String line;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        if (values.length >= 2) {
          try {
            double x = Double.parseDouble(values[0].trim());
            double y = Double.parseDouble(values[1].trim());
            series.add(x, y);
          } catch (NumberFormatException e) {
            System.err.println("Skipping malformed line: " + line);
          }
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null,
          "Error reading CSV file: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }

    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series);
    return dataset;
  }

  public static JFreeChart createChart(XYDataset dataset, String title) {
    JFreeChart chart = ChartFactory.createXYLineChart(
        title,
        "X Axis",
        "Y Axis",
        dataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false);

    XYPlot plot = chart.getXYPlot();
    customizePlot(plot);
    return chart;
  }

  private static void customizePlot(XYPlot plot) {
    plot.setBackgroundPaint(Color.WHITE);
    plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

    plot.getRenderer().setSeriesPaint(0, Color.BLUE);
    plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
  }

  public static void displayChart(String csvFilePath) {
    FunctionGraph graph = new FunctionGraph(
        "Function Graph Visualization",
        "Graph of f(x)",
        csvFilePath);

    graph.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    graph.pack();
    graph.setLocationRelativeTo(null);
    graph.setVisible(true);
  }
}
