package app;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import database.County;
import database.VoterData;

public class TextDisplay extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8732870481739182805L;
	private JPanel top, bottom;
	private JLabel title, dem, rep, ind;//, included;
	//private JTextArea selectedCounties;
	private JScrollPane scroll;
	
	public TextDisplay(ArrayList<County> c) {
		JPanel panel = new JPanel();
		setSize(380, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		add(panel);
		
		//String countyNames = "";
		int totalDemoVotes = 0;
		int totalRepubVotes = 0;
		int totalIndieVotes = 0;
		
		for(int i = 0; i < c.size(); i++){
			//if(!countyNames.contains(v.get(i).getCounty()))
				//countyNames+=v.get(i).getCounty()+"\n";
			totalDemoVotes += c.get(i).getDemVotingData();
			totalRepubVotes += c.get(i).getRepVotingData();
			totalIndieVotes += c.get(i).getOthVotingData();
		}
		
		int total = totalDemoVotes + totalRepubVotes + totalIndieVotes;
		
		panel.setLayout(null);
		
		top = new JPanel();
		scroll = new JScrollPane(top);
		panel.add(scroll);
		top.setBounds(0, 0, 380, 199);
		
		
		top.setLayout(new GridLayout( (c.size()*4)+c.size(), 1));
		for(County county: c){
			top.add(new JLabel(county.getCounty()));
			top.add(new JLabel("Democrat: "+ county.getDemVotingData()+
							   " ("+county.getDemVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel("Republican: "+ county.getRepVotingData()+
					  		   " ("+county.getRepVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel("Independent: "+ county.getOthVotingData()+
					  " ("+county.getOthVotingData()/county.getTotal()*100+"%)"));
			top.add(new JLabel(""));
		}
		
		bottom = new JPanel();
		panel.add(bottom);
		bottom.setLayout(new GridLayout(4, 1));
		//setVGap(0);
		bottom.setBounds(0, 199, 380, 101);
		
		title = new JLabel("Total:");
		//title.setFont(new Font("Times New Roman", Font.BOLD, 30));
		bottom.add(title);
		
		dem = new JLabel("Democrat: "+totalDemoVotes+" ("+totalDemoVotes/total*100+"%)");
		//dem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		bottom.add(dem);
		
		rep = new JLabel("Republican: "+totalRepubVotes+" ("+totalRepubVotes/total*100+"%)");
		//rep.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		bottom.add(rep);
		
		ind = new JLabel("Independent: "+totalIndieVotes+" ("+totalIndieVotes/total*100+"%)");
		//ind.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		bottom.add(ind);
	}
	
	/*For testing purposes only
	public static void main(String[] args){
		ArrayList<VoterData> temp = new ArrayList<>();
		temp.add(new VoterData("Franklin","afsdp", 345, 45, 67));
		temp.add(new VoterData("Bulter","ahidsj", 23, 456, 1));
		temp.add(new VoterData("Delaware","dshld", 212, 382, 20));
		new TextDisplay(temp);
	}*/
}