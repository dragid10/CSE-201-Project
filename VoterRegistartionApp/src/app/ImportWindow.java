package app;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Graphics;

import database.CSVParse;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImportWindow extends JFrame {

	private static final long serialVersionUID = 2644876401336569282L;
	private JLabel instruct, error;
	private JTextField directory, logs;
	private JButton dataBrowse, logBrowse,confirm, close;
	private String dataDirectory, logLocation;
	private JPanel panel;
	private Rectangle errRect = new Rectangle(40, 220, 300, 30);

	JFileChooser chooser = new JFileChooser();

	public ImportWindow() {
		setTitle("Import Data");
		setSize(380, 280);
		setLocationRelativeTo(Main.frame);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		panel = new JPanel();
		add(panel);

		panel.setLayout(null);

		instruct = new JLabel("Choose folder you wish to use:");
		panel.add(instruct);
		instruct.setBounds(5, 5, 380, 30);

		error = new JLabel("Set location of error log folder:");
		panel.add(error);
		error.setBounds(5, 70, 380, 30);
		
		directory = new JTextField(200);
		//directory.setEditable(false);
		panel.add(directory);
		directory.setBounds(5, 35, 250, 30);
		
		logs = new JTextField(200);
		panel.add(logs);
		logs.setBounds(5, 105, 250, 30);

		JLabel displayErr = new JLabel("Currently No Errors");
		displayErr.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(displayErr);
		displayErr.setBounds(errRect.x+20, errRect.y-24, errRect.width-40, errRect.height-4);
	
		
		//opens up file explorer
		dataBrowse = new JButton("Browse");
		dataBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataDirectory = openExplorer();
				directory.setText(dataDirectory);
			}
		});
		panel.add(dataBrowse);
		dataBrowse.setBounds(265, 35, 100, 30);
		
		logBrowse = new JButton("Browse");
		logBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logLocation = openExplorer();
				logs.setText(logLocation);
			}
		});
		panel.add(logBrowse);
		logBrowse.setBounds(265, 105, 100, 30);

		//sets the directory path to retrieve data
		confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO if directory is set and no problems that exit but if not inform user with pop up and prompt
				if(directory.getText().equals("")){
					//Error message that pop up if no path is given 
					JOptionPane.showMessageDialog(panel,"You have not selected a directory yet", "Warning", JOptionPane.OK_OPTION);
				}
				if(logs.getText().equals("")){
					JOptionPane.showMessageDialog(panel,"You have not set the destination location for the log directory", "Warning", JOptionPane.OK_OPTION);
				}
				else {
					CSVParse parser = CSVParse.getInstance();
					try {
						parser.loadVoterData();
						parser.setLogDirectory(logLocation);
						parser.parse(dataDirectory);
						Main.counties.setEnabled(true);
						//dispose();
					} catch (FileNotFoundException e1) {
						displayErr.setText("Directory could not be found.");
					}
				}
			}
		});
		panel.add(confirm);
		confirm.setBounds(90, 150, 80, 30);
		
		close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(close);
		close.setBounds(200, 150, 70, 30);
	}

	public String getDirectoryPath() {
		return dataDirectory;
	}

	private void setDirectoryPath(String name) {
		this.dataDirectory = name;
	}

	//opens a file explorer that allows user to set desired directory
	/*private void openExplorer() {
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle(getDirectoryPath());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			setDirectoryPath(dir.getPath());
			directory.setText(getDirectoryPath());
		} else {
			JOptionPane.showMessageDialog(this,"You have not selected a directory yet", "Warning", JOptionPane.OK_OPTION);
		}
	}*/
	private String openExplorer() {
		String path = "";
		chooser.setCurrentDirectory(new File("."));
		//chooser.setDialogTitle("title");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			path = dir.getPath();
		} else {
			//JOptionPane.showMessageDialog(this,"You have not selected a directory yet", "Warning", JOptionPane.OK_OPTION);
		}
		return path;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawRect(errRect.x, errRect.y, errRect.width, errRect.height);
	}
	
	public static void main(String[] args) {
		new ImportWindow();
	}
}
