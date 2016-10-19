
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public interface KAppInterface {

	public String getName(); // return the text that you want displayed on the top of your frame
	
	public JPanel getMainPanel(); // return your main JPanel once you construct it
	
	public String[][] getMenus(); // Returns an array of strings representing your menus. 
									// format: menus[menu][item]
									// The action command for the menu item will
									// match the menus text

	public ActionListener getMenuActionListener(); // return the action listener
													// that is handling your
													// menus
	
	public int getWindowWidth();
	public int getWindowHeight();
	
}
