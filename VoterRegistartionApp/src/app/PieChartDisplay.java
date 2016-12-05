package app;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import database.VoterData;

public class PieChartDisplay extends JFrame {
	private static final long serialVersionUID = 5996167323926060184L;
	private static int totalDemVotes = 0;
	private static int totalRepVotes = 0;
	private static int totalOthVotes = 0;
	
	public PieChartDisplay(ArrayList<VoterData> v) { //change to VoterData
		super("Pie Chart Vizulization");
				
		for(int i = 0; i < v.size(); i++){
			totalDemVotes += v.get(i).getDemVotes();
			totalRepVotes += v.get(i).getRepVotes();
			totalOthVotes += v.get(i).getOthVotes();
		}
		setPreferredSize(new Dimension(570, 450));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(piePanel());
	}

	public static JPanel piePanel() {
		JFreeChart chart = createChart(createDataSet());
		return new ChartPanel(chart);
	}

	private static JFreeChart createChart(PieDataset data) {
		JFreeChart chart = ChartFactory.createPieChart("", data, true, true, false);
		PiePlot plot =(PiePlot) chart.getPlot();
		plot.setNoDataMessage("No data is avaiblable to be shown");
		plot.setCircular(true);
		plot.setLabelGap(0.02);
		return chart;
	}

	private static PieDataset createDataSet() {
		DefaultPieDataset dataSet= new DefaultPieDataset();
		double total = totalDemVotes + totalRepVotes + totalOthVotes;
		String republican = String.format("Republican\n%.2f", (totalRepVotes/total)*100);
		String democrat = String.format("Democrat\n%.2f", (totalDemVotes/total)*100);
		String independent = String.format("Independent\n%.2f", (totalOthVotes/total)*100);
		dataSet.setValue(republican+"%", totalRepVotes/total); 
		dataSet.setValue(democrat+"%", totalDemVotes/total);
		dataSet.setValue(independent+"%", totalOthVotes/total);
 		return dataSet;
	}
}