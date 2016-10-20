package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 */

/**
 * @author Emma Figiel
 *
 */
public class GUIMain implements KAppInterface, ActionListener{

	private static GUIMain app;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		KApp.launchApp(app = new GUIMain());
	}

	@Override
	public String getName() {
		return "Ohio Voting CSE 201 Project";
	}

	@Override
	public JPanel getMainPanel() {
		return new MainPanel();
	}

	@Override
	public String[][] getMenus() {
		String[][] menu = {{"File","Exit"}};
		return menu;
	}

	@Override
	public ActionListener getMenuActionListener() {
		return this;
	}

	@Override
	public int getWindowWidth() {
		return 600;
	}

	@Override
	public int getWindowHeight() {
		return 600;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch(event.getActionCommand()){
		
		case "File|Exit":
			System.exit(0);
			break;
		}
		
	}

}
