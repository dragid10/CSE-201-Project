package app;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import database.VoterData;

public class CountyDisplay extends JPanel {

	JPanel top = new JPanel();
	JPanel bottom = new JPanel();

	JCheckBox adams = new JCheckBox("Adams"), allen = new JCheckBox("Allen"), butler = new JCheckBox("Butler"),
			carroll = new JCheckBox("Carroll"), clark = new JCheckBox("Clark"), delaware = new JCheckBox("Delaware"),
			logan = new JCheckBox("Logan"), erie = new JCheckBox("Erie"), fairfield = new JCheckBox("Fairfield"),
			greene = new JCheckBox("Greene"), hamilton = new JCheckBox("Hamilton"),
			jefferson = new JCheckBox("Jefferson"), madison = new JCheckBox("Madison"),
			ottawa = new JCheckBox("Ottawa"), perry = new JCheckBox("Perry"), ross = new JCheckBox("Ross"),
			sandusky = new JCheckBox("Sandusky"), stark = new JCheckBox("Stark"), checkAll = new JCheckBox("check All"),
			clearAll = new JCheckBox("Clear All");
	JCheckBox[] countyboxes = { adams, allen, butler, carroll, clark, delaware, erie, fairfield, greene,
			hamilton, jefferson, logan, madison, ottawa, perry, ross, sandusky, stark };
	//ArrayList<JCheckBox> countyBoxes = new ArrayList<>();
	ArrayList<VoterData> userCounties = new ArrayList<>();


	public CountyDisplay(ArrayList<VoterData> v) {
		setLayout(null);
		JScrollPane scroll = new JScrollPane(top);
		add(scroll);
		scroll.setBounds(0,0, 380, 220); //scroll.setBounds(0, 0, 190, 220);
		add(bottom);
		bottom.setBounds(0, 220, 380, 40);
		
		GridLayout grid = new GridLayout(countyboxes.length, 1);
		top.setLayout(grid);
		grid.setVgap(5);
		
		ItemListener listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() instanceof JCheckBox) {
					if (((JCheckBox) e.getSource()).isSelected()){ 
						((JCheckBox) e.getSource()).setSelected(true);
						for(VoterData voter: v){
							if(voter.getCounty().equals(((JCheckBox) e.getSource()).getText())){
								userCounties.add(voter);
								//System.out.println("Added " + ((JCheckBox) e.getSource()).getText());
							}
						}
					}
					else {
						((JCheckBox) e.getSource()).setSelected(false);
						checkAll.setSelected(false);
						for(int i = v.size()-1; i > 0; i--){
							if(v.get(i).getCounty().equals(((JCheckBox) e.getSource()).getText())){
								userCounties.remove(v.get(i));
								//System.out.println("Removed " + ((JCheckBox) e.getSource()).getText());
							}
						}
					}
				}
			}
		};

		for (int i = 0; i < countyboxes.length; i++) {
			//JCheckBox checkBox = new JCheckBox(v.get(i).getCounty());
			countyboxes[i].setSelected(false);
			countyboxes[i].addItemListener(listener);
			countyboxes[i].setSelected(false);
			top.add(countyboxes[i]);
			
			//checkBox.setSelected(false);
			//checkBox.addItemListener(listener);
			//countyBoxes[i].setSelected(false);
			//countyBoxes[i].addItemListener(listener);
			//top.add(checkBox);
		}

		bottom.setLayout(new GridLayout(1, 2));
		checkAll.setHorizontalAlignment(SwingConstants.CENTER);
		checkAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if (checkAll.isSelected()) {
					clearAll.setSelected(false);
					for (int i = 0; i < countyboxes.length; i++) {
						((JCheckBox)countyboxes[i]).setSelected(true);
					}
				}
			}
		});
		bottom.add(checkAll);
		
		clearAll.setHorizontalAlignment(SwingConstants.CENTER);
		clearAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if (clearAll.isSelected()) {
					for (int i = 0; i < countyboxes.length; i++) {
						((JCheckBox)countyboxes[i]).setSelected(false);	
					}
					userCounties = new ArrayList<VoterData>();
				}
			}
		});
		bottom.add(clearAll);
		
		repaint();
		revalidate();
	}
	
	public ArrayList<VoterData> pickedCounties(){
		return userCounties;
	}
	
	//For testing purposes only
	/*public static void main(String[] args){
		ArrayList<VoterData> temp = new ArrayList<>();
		//temp.add(new VoterData("Franklin", "Good", 345, 45, 67));
		//temp.add(new VoterData("Butler", "Fresh", 23, 456, 1));
		//temp.add(new VoterData("Delaware", " Apple", 212, 382, 20));
		
		JFrame frame = new JFrame("County Selector");
		JPanel panel = new CountyDisplay(temp);
		frame.pack();
		frame.setBounds(0, 0, 380, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//frame.setResizable(false);
		frame.add(panel);
	}*/
	
	
}