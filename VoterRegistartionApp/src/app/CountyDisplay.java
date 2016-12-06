package app;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import database.County;
import database.VoterData;

public class CountyDisplay extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2457977387854519801L;
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

	private ArrayList<County> userCounties = new ArrayList<>();

	public CountyDisplay(ArrayList<County> counties) { 
		setBackground(Color.WHITE);
		setLayout(null);
		top.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(top);
		add(scroll);
		scroll.setBounds(0,0, 380, 220);
		bottom.setBackground(Color.WHITE);
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
						clearAll.setSelected(false);
						for(County c: counties){
							if(c.getCounty().equals(((JCheckBox) e.getSource()).getText())){
								userCounties.add(c);
							}
						}
					} else {
						((JCheckBox) e.getSource()).setSelected(false);
						checkAll.setSelected(false);
						for(int i = counties.size()-1; i > 0; i--){
							if(counties.get(i).getCounty().equals(((JCheckBox) e.getSource()).getText())){
								userCounties.remove(counties.get(i));
							}
						}
					}
				}
			}
		};
		
		for (int i = 0; i < countyboxes.length; i++) {
			countyboxes[i].setSelected(false);
			countyboxes[i].addItemListener(listener);
			top.add(countyboxes[i]);
			countyboxes[i].setBackground(Color.WHITE);
			
			//countyboxes[i].setForeground(Color.RED);
			//JCheckBox checkBox = new JCheckBox(v.get(i).getCounty());
			//checkBox.setSelected(false);
			//checkBox.addItemListener(listener);
			//top.add(checkBox);
		}

		bottom.setLayout(new GridLayout(1, 2));
		checkAll.setHorizontalAlignment(SwingConstants.CENTER);
		checkAll.setBackground(Color.WHITE);
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
		clearAll.setBackground(Color.WHITE);
		clearAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if (clearAll.isSelected()) {
					for (int i = 0; i < countyboxes.length; i++) {
						((JCheckBox)countyboxes[i]).setSelected(false);	
					}
					userCounties = new ArrayList<County>();
				}
			}
		});
		bottom.add(clearAll);
		
		repaint();
		revalidate();
	}
	
	public ArrayList<County> pickedCounties(){
		return userCounties;
	}
}