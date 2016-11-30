package app;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import database.VoterData;

public class TextDisplay extends JFrame {
	JPanel main, countySingle, countyTotal;
	JLabel title, dem, rep, ind, included;
	JTextArea selectedCounties;
	JScrollPane scroll;
	
	public TextDisplay(ArrayList<VoterData> v) {
		JPanel panel = new JPanel();
		setSize(380, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(panel);
		
		String countyNames = "";
		int totalDemoVotes = 0;
		int totalRepubVotes = 0;
		int totalIndieVotes = 0;
		
		for(int i = 0; i < v.size(); i++){
			if(!countyNames.contains(v.get(i).getCounty()))
				countyNames+=v.get(i).getCounty()+"\n";
			totalDemoVotes += v.get(i).getDemVotes();
			totalRepubVotes += v.get(i).getRepVotes();
			totalIndieVotes += v.get(i).getOthVotes();
		}
		
		panel.setLayout(null);
		
		countyTotal = new JPanel();
		panel.add(countyTotal);
		countyTotal.setBounds(0, 0, 380, 199);
		
		countySingle = new JPanel();
		panel.add(countySingle);
		countySingle.setLayout(null);
		countySingle.setBounds(0, 199, 380, 101);
		
		selectedCounties = new JTextArea(5, 30);
		selectedCounties.setText(countyNames);
		selectedCounties.setEditable(false);
		scroll = new JScrollPane(selectedCounties);
		countySingle.add(scroll);
		
		title = new JLabel("Voter Sumamry");
		title.setFont(new Font("Times New Roman", Font.BOLD, 50));
		countyTotal.add(title);
		title.setBounds(10,0, 380, 58);
		
		dem = new JLabel("Democrat: "+totalDemoVotes);
		dem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		countyTotal.add(dem);
		dem.setBounds(20,57, 380, 31);
		
		rep = new JLabel("Republican: "+totalRepubVotes);
		rep.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		countyTotal.add(rep);
		rep.setBounds(20,87, 380, 39);
		
		ind = new JLabel("Independent: "+totalIndieVotes);
		ind.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		countyTotal.add(ind);
		ind.setBounds(20,128, 380, 31);
		
		included = new JLabel("Counties include in Summary:");
		included.setFont(new Font("TImes New Roman", Font.PLAIN, 15));
		countyTotal.add(included);
		included.setBounds(5, 170, 380, 30);
		
		//scroll.setBounds(0, 199, 380, 101);

	}
	
	//For testing purposes only
	public static void main(String[] args){
		ArrayList<VoterData> temp = new ArrayList<>();
		temp.add(new VoterData("Franklin","afsdp", 345, 45, 67));
		temp.add(new VoterData("Bulter","ahidsj", 23, 456, 1));
		temp.add(new VoterData("Delaware","dshld", 212, 382, 20));
		new TextDisplay(temp);
	}
}