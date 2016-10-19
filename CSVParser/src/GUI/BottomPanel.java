

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Emma Figiel
 *
 */
public class BottomPanel extends JPanel{
	
	public BottomPanel(){
		//TODO FIX SO BUTTONS ARE CENTERED
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.setBackground(Color.RED);
		this.add(new JButton("Test2"));
		this.add(new JButton("Test3"));
		this.add(new JButton("Test4"));

	}
}
