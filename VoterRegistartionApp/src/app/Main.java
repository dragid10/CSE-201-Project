package app;


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
	static JFrame frame;
	JPanel panel;
	CountyDisplay display;
	JButton getData;
	static JButton counties;
	static JButton pie, text, bar;
	JButton quit;
	JLabel welcome;
	Image welcomePic = Toolkit.getDefaultToolkit().createImage("lifeispain.jpg");
	Image background = Toolkit.getDefaultToolkit().createImage("csegredo.jpg");

	CSVParse parser = CSVParse.getInstance();
	ArrayList<VoterData> temp = new ArrayList<>();
	ArrayList<VoterData> selected = new ArrayList<>();
	
	private int screenWidth = 400, screenHeight = 600;
	private int buttonHeight = 30;
	Rectangle window = new Rectangle(3, 70, screenWidth - 20, screenHeight / 2 + 10);

	public Main() {
		frame = new JFrame("(AL) G.O.E.R Version 1.0");
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

		JLabel back = new JLabel(new ImageIcon(background));
		panel.add(back);
		back.setBounds(0, 0, screenWidth, screenHeight);

		//
		welcome = new JLabel(new ImageIcon(welcomePic));
		panel.add(welcome);
		welcome.setBounds(window);

		//
		getData = new JButton("Import");
		getData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ImportWindow();
				if(display.isVisible() || display != null){
					frame.remove(display);
				}
			}
		});
		panel.add(getData);
		getData.setBounds(screenWidth / 2 - 35, 20, 80, buttonHeight);
		
		counties = new JButton("Check Counties");
		counties.setEnabled(false);
		counties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				welcome.setVisible(false);
				counties.setEnabled(false);
				temp = parser.getData();
				display = new CountyDisplay(temp);
				panel.add(display);
				display.setBounds(window);
				
				pie.setEnabled(true);
				text.setEnabled(true);
				bar.setEnabled(true);
				repaint();	
		}
		});
		panel.add(counties);
		counties.setBounds(screenWidth / 2 - 70, (screenHeight / 3) * 2, screenWidth / 4 + 40, buttonHeight);
	
		
		// displays chosen data in a pie chart format in a new panel
		pie = new JButton("Pie Chart");
		pie.setEnabled(false);
		pie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = display.pickedCounties();
				//selected.add(new VoterData("Delaware", "fish", 234, 12, 23));
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
		pie.setBounds(screenWidth / 4 - 50, (screenHeight / 3) * 2 + 50, screenWidth / 4, buttonHeight);

		// displays chosen in a textual format in a new panel
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
		text.setBounds(screenWidth / 2 - 30, (screenHeight / 3) * 2 + 50, 60, buttonHeight);

		// displays chosen data in a bar chart format in a new panel
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
		bar.setBounds(screenWidth - screenWidth / 4 - 50, (screenHeight / 3) * 2 + 50, screenWidth / 4, buttonHeight);

		// Prompts the user if they want to quit application and does so if
		// "yes" is pressed
		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int quitWindow = JOptionPane.showConfirmDialog(panel, "Are you sure you want to quit?", "Caution",
						JOptionPane.YES_NO_OPTION);
				if (quitWindow == 0) {
					frame.dispose();
					System.exit(0);
				}
			}
		});
		panel.add(quit);
		quit.setBounds(screenWidth / 2 - 30, screenHeight - 100, 60, buttonHeight);

		panel.revalidate();
		panel.repaint();
		}

	public static void main(String[] args) {
		new Main();
	}

}