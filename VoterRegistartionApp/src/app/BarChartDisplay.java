package app;

import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import database.County;
import database.VoterData;

import java.awt.Color;
import java.awt.Dimension;
//TODO http://www.java2s.com/Code/Java/Chart/JFreeChartBarChartDemo.htm
public class BarChartDisplay extends JFrame {
	private static final long serialVersionUID = 878357737971892631L;
	private JButton close = new JButton("Close");
	private JPanel panel = new JPanel();
	private int totalDemVotes = 0;
	private int totalRepVotes = 0;
	private int totalOthVotes = 0;
	
	public BarChartDisplay(ArrayList<County> c) { //change to VoterData
		super("Bar Chart Vizulization");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(570, 480);
		//add(panel);
		//panel.setLayout(null);
		//panel.setBackground(new Color(0xBBBBDD));
		
		for(int i = 0; i < c.size(); i++){
			totalDemVotes += c.get(i).getDemVotingData();
			totalRepVotes += c.get(i).getRepVotingData();
			totalOthVotes += c.get(i).getOthVotingData();
		}
		
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(570, 450));
		add(chartPanel);
		//chartPanel.setBounds(0, 0, 570, 450);
		
		close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		//add(close);
		//close.setBounds(230, 450, 80, 30);
		
	}

	private CategoryDataset createDataset() {
		final String demo = "Democrat";
		final String rep = "Republican";
		final String ind = "Independent";

		final String category1 = "";

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(totalDemVotes, demo+" = "+totalDemVotes, category1);
		dataset.addValue(totalRepVotes, rep+" = "+totalRepVotes, category1);
		dataset.addValue(totalOthVotes, ind+" = "+totalOthVotes, category1 );
		
		return dataset;
	}

	private JFreeChart createChart(final CategoryDataset data){
		final JFreeChart chart = ChartFactory.createBarChart( 
				"",
				"Party", 
				"Voters", 
				data, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
		
		chart.setBackgroundPaint(new Color(0xBBBBDD));
		
		final CategoryPlot plot = chart.getCategoryPlot();
		
		final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	
		final BarRenderer renderer = (BarRenderer)plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setMaximumBarWidth(0.30);
		
		final GradientPaint gpd = new GradientPaint(
				0.0f, 0.0f, Color.BLUE,
				0.0f, 0.0f, Color.BLUE
		);
		
		final GradientPaint gpr = new GradientPaint(
				0.0f, 0.0f, Color.RED, 
				0.0f, 0.0f, Color.RED
		);
		
		final GradientPaint gpi = new GradientPaint(
				0.0f, 0.0f, Color.GREEN, 
				0.0f, 0.0f, Color.GREEN
		);
		
		renderer.setSeriesPaint(0, gpd);
		renderer.setSeriesPaint(1, gpr);
		renderer.setSeriesPaint(2, gpi);
		
		return chart;
	}
}