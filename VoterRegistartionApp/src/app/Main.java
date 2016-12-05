package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;

import database.CSVParse;
import database.VoterData;

public class Main extends JPanel {
	private static final long serialVersionUID = -8685330606724958054L;
	static JFrame frame;
	JPanel panel;
	CountyDisplay display;
	static JButton counties, pie, text, bar, all;
	JButton getData, quit;
	JLabel welcome, back;
	Image welcomePic = Toolkit.getDefaultToolkit().getImage("lifeispain.jpg");
	Image background = Toolkit.getDefaultToolkit().getImage("csebgredo_rgb.jpg");

	CSVParse parser = CSVParse.getInstance();
	ArrayList<VoterData> temp = new ArrayList<>();
	ArrayList<VoterData> selected = new ArrayList<>();
	
	private int screenWidth = 400, screenHeight = 600;
	private int buttonHeight = 30;
	Rectangle window = new Rectangle(3, 70, screenWidth - 20, screenHeight / 2 + 10);

	public Main() {
		frame = new JFrame("(AL) G.O.E.R Version 1.375");
		frame.pack();
		frame.setBounds(0, 0, screenWidth, screenHeight);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		mainWindow();
	}

	/**
	 * 
	 */
	public void mainWindow() {
		panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);

		//displays a picture in the space determined by Rectangle window
		welcome = new JLabel(new ImageIcon(welcomePic));
		panel.add(welcome);
		welcome.setBounds(window);
		
		panel.setBackground(Color.WHITE);
		
		/*back = new JLabel(new ImageIcon(background));
		panel.add(back);
		back.setBounds(0, 0, screenWidth, screenHeight);*/
		
		//Brings up new window that gets the directory of data
		getData = new JButton("Import");
		getData.setToolTipText("Add the directory that has the data you wish to view");
		getData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(display != null){
					//switch view to display selectable counties
					display.setVisible(false);
					welcome.setVisible(true);
					
					revalidate();
				}
				new ImportWindow();
			}
		});
		panel.add(getData);
		getData.setBounds(screenWidth / 2 - 35, 20, 80, buttonHeight);
		
		//Brings up panel that shows all the counties that can be selected
		counties = new JButton("Check Counties");
		counties.setEnabled(false);
		counties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				welcome.setVisible(false);
				counties.setEnabled(false); //makes check counties button false
				temp = parser.getData();
				display = new CountyDisplay(temp);
				panel.add(display);
				display.setBounds(window);
				
				//set all display formats to be clickable
				pie.setEnabled(true);
				text.setEnabled(true);
				bar.setEnabled(true);
				all.setEnabled(true);
				
				//enables tooltips for buttons to help the user
				counties.setToolTipText("Click this put to pull up display of counties to display");
				pie.setToolTipText("Display selcted counties in a pie chart format");
				text.setToolTipText("Display selected counties in a text format");
				bar.setToolTipText("Display selected counties in a bar chart format");
				all.setToolTipText("Display bar, text, and pie chart for selected counties");
				
				repaint();	
			}
		});
		panel.add(counties);
		counties.setBounds(screenWidth / 2 - 75, (screenHeight / 3) * 2, screenWidth / 4 + 40, buttonHeight);
		
		
		//displays, in new window, selected counties in a pie chart 
		pie = new JButton("Pie Chart");
		pie.setEnabled(false);
		pie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = display.pickedCounties();
				if (selected.size() > 0) {
					PieChartDisplay d = new PieChartDisplay(selected);
					d.pack();
					RefineryUtilities.centerFrameOnScreen(d);
					d.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(display, "There are no counties selected yet", "Warning", JOptionPane.OK_OPTION);
				}
			}
		});
		panel.add(pie);
		pie.setBounds(screenWidth / 4 - 65, (screenHeight / 3) * 2 + 50, screenWidth / 4, buttonHeight);

		//displays, in new window, selected counties in text
		text = new JButton("Text");
		text.setEnabled(false);
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = display.pickedCounties();
				if (selected.size() > 0) {
					new TextDisplay(selected);
				} else {
					JOptionPane.showMessageDialog(display, "There are no counties selected yet", "Warning", JOptionPane.OK_OPTION);
				}
			}
		});
		panel.add(text);
		text.setBounds(screenWidth / 2 - 65, (screenHeight / 3) * 2 + 50, 60, buttonHeight);

		//displays, in new window, selected counties in a bar chart
		bar = new JButton("Bar Chart");
		bar.setEnabled(false);
		bar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 selected = display.pickedCounties();
				if (selected.size() > 0) {
					final BarChartDisplay b = new BarChartDisplay(selected);
					b.pack();
					RefineryUtilities.centerFrameOnScreen(b);
					b.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(display, "There are no counties selected yet", "Warning", JOptionPane.OK_OPTION);
				}
			}
		});
		panel.add(bar);
		bar.setBounds(screenWidth - screenWidth / 4 - 105, (screenHeight / 3) * 2 + 50, screenWidth / 4, buttonHeight);
		
		//displays, in new windows, the selected counties in every display format
		all = new JButton("All");
		all.setEnabled(false);
		all.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pie.doClick();
				text.doClick();
				bar.doClick();
			}
		});
		panel.add(all);
		all.setBounds(screenWidth - screenWidth / 4-5, (screenHeight / 3) * 2 + 50, screenWidth / 8, buttonHeight);  //TODO change all this stuff later;
		
		//exits the application
		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//asks user if they want to exit, if "yes" the application closes
				int quitWindow = JOptionPane.showConfirmDialog(panel, "Are you sure you want to quit?", "Caution",
						JOptionPane.YES_NO_OPTION);
				if (quitWindow == 0) {
					frame.dispose();
					System.exit(0);
				}
			}
		});
		panel.add(quit);
		quit.setBounds(screenWidth / 2 - buttonHeight, screenHeight - 100, 60, buttonHeight);

		panel.revalidate();
		panel.repaint();
	}
	
	/*@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}*/

	public static void main(String[] args) {
		new Main();
	}

}