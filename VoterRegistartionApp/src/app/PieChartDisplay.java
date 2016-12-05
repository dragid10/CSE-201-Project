package app;

import database.VoterData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PieChartDisplay extends JFrame {
	static int totalDemVotes = 0;
	static int totalRepVotes = 0;
	static int totalOthVotes = 0;
	
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
	
	//For testing purposes only
	/*public static void main(String[] args){
		PieChartDisplay d = new PieChartDisplay();
		d.pack();
		RefineryUtilities.centerFrameOnScreen(d);
		d.setVisible(true);
		
	}*/
}