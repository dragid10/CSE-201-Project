package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import database.CSVParse;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImportWindow extends JFrame {
	JLabel instruct, error;
	JTextField directory;
	JButton browse, confirm;
	String directoryPath;
	JPanel panel;

	JFileChooser chooser = new JFileChooser();

	public ImportWindow() {
		setSize(380, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		panel = new JPanel();
		add(panel);

		panel.setLayout(null);

		instruct = new JLabel("Please give the directory that you wish to use");
		panel.add(instruct);
		instruct.setBounds(5, 5, 380, 30);

		directory = new JTextField(200);
		directory.setEditable(false);
		panel.add(directory);
		directory.setBounds(5, 35, 250, 30);

		browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openExplorer();
				
			}
		});
		panel.add(browse);
		browse.setBounds(265, 35, 100, 30);

		confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(directory.getText().equals("")){
					JOptionPane.showMessageDialog(panel,"You have not selected a directory yet", "Warning", JOptionPane.OK_OPTION);
				}
				else {
					setDirectoryPath((String) directory.getText());
					CSVParse parser = CSVParse.getInstance();
					try {
						parser.parse(getDirectoryPath());
					} catch (FileNotFoundException e1) {
						System.out.println(e1.getMessage());
					}
					Main.counties.setEnabled(true);
					//System.out.println("Directory Path: " + directoryPath);
					dispose();
				}
			}

		});
		panel.add(confirm);
		confirm.setBounds(140, 75, 100, 30);
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	private void setDirectoryPath(String name) {
		this.directoryPath = name;
	}

	public static void main(String[] args) {
		new ImportWindow();

	}

	private void openExplorer() {
		// TODO Auto-generated method stub
		int result;
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle(getDirectoryPath());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			//setDirectoryName(""+chooser.getSelectedFiles());
			File dir = chooser.getSelectedFile();
			setDirectoryPath(dir.getPath());
			directory.setText(getDirectoryPath());
			
			//System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		} else {
			JOptionPane.showMessageDialog(this,"You have not selected a directory yet", "Warning", JOptionPane.OK_OPTION);
			//error = new JLabel("No Selection Made");
			//panel.add(error);
			//error.setBounds(x, y, width, height);
		}

	}
}
