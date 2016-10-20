package GUI;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class KApp {

	public static void launchApp(final KAppInterface kai) {

		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                makeFrame(kai); 
            }
        });
	}
	
	public static void makeFrame(KAppInterface app) {
		
		JFrame frame = new JFrame(app.getName());
		
		MenuBar menuBar = new MenuBar();
		Menu menu;
		MenuItem menuItem;
		
		String[][] menus = app.getMenus();
		
		for(int m=0;m<menus.length;++m){
			menu = new Menu(menus[m][0]);
			for(int mi=1;mi<menus[m].length;++mi) {
				menuItem = new MenuItem(menus[m][mi]);
				menuItem.setActionCommand(menus[m][0]+"|"+menus[m][mi]);
				menuItem.addActionListener(app.getMenuActionListener());
				menu.add(menuItem);
			}
			menuBar.add(menu);
		}
		
		frame.setMenuBar(menuBar);
		frame.add(app.getMainPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		frame.setPreferredSize(new Dimension(app.getWindowWidth(), app.getWindowHeight()));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
