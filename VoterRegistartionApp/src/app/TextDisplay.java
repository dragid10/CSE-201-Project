package app;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import database.County;
import database.VoterData;

public class TextDisplay extends JFrame {

	private static final long serialVersionUID = 8732870481739182805L;
	private JPanel top, bottom;
	private JLabel title, dem, rep, ind;
	private JScrollPane scroll;
	
	public TextDisplay(ArrayList<County> c) {
		JPanel panel = new JPanel();
		setSize(570, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		add(panel);
		
		int totalDemoVotes = 0;
		int totalRepubVotes = 0;
		int totalIndieVotes = 0;
		
		for(int i = 0; i < c.size(); i++){
			totalDemoVotes += c.get(i).getDemVotingData();
			totalRepubVotes += c.get(i).getRepVotingData();
			totalIndieVotes += c.get(i).getOthVotingData();
		}
		
		int total = totalDemoVotes + totalRepubVotes + totalIndieVotes;
		
		panel.setLayout(null);
		top = new JPanel();
		bottom = new JPanel();
		
		scroll = new JScrollPane(top);
		panel.add(scroll);
		scroll.setBounds(0, 0, 555, 300);
		
		GridLayout tGrid = new GridLayout((c.size()*4)+c.size(), 1);
		top.setLayout(tGrid);
		tGrid.setVgap(2);
		
		for(County county: c){
			top.add(new JLabel(county.getCounty() + ":"));
			top.add(new JLabel("Democrat: "+ county.getDemVotingData()+
							   " ("+county.getDemVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel("Republican: "+ county.getRepVotingData()+
					  		   " ("+county.getRepVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel("Independent: "+ county.getOthVotingData()+
					           " ("+county.getOthVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel(""));
		}
		
		panel.add(bottom);
		bottom.setBounds(0, 300, 570, 100);
		
		GridLayout bGrid = new GridLayout(4, 1);
		bottom.setLayout(bGrid);
		bGrid.setVgap(0);
		
		title = new JLabel("Total:");
		bottom.add(title);
		//title.setHorizontalAlignment(SwingConstants.CENTER);
		
		dem = new JLabel("Democrat: "+totalDemoVotes+" ("+totalDemoVotes/total*100+"%)");
		bottom.add(dem);
		
		rep = new JLabel("Republican: "+totalRepubVotes+" ("+totalRepubVotes/total*100+"%)");
		bottom.add(rep);
		
		ind = new JLabel("Independent: "+totalIndieVotes+" ("+totalIndieVotes/total*100+"%)");
		bottom.add(ind);
		
		revalidate();
	}
	
	//For testing purposes only
	/*public static void main(String[] args){
		ArrayList<County> temp = new ArrayList<>();
 		temp.add(new County("Delaware", 345, 45, 67));
		new TextDisplay(temp);
	}*/
}