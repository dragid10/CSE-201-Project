import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Emma Figiel
 *
 */
public class MainPanel extends JPanel {

	public MainPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.RED);
		this.add(new JButton("Test"), BorderLayout.CENTER);
		this.add(new BottomPanel(), BorderLayout.SOUTH);
	}

	
}
