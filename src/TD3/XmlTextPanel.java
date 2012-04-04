package TD3;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class XmlTextPanel extends JPanel{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	
	public XmlTextPanel() {
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(400,400));
		textArea.setEditable(false);
		
		this.add(textArea);
	}
	
	public void setText(String text){
		textArea.setText(text);
	}

}
