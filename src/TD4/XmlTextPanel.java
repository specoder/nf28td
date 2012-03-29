
package TD4;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class XmlTextPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	
	public XmlTextPanel() {
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setEditable(false);
		//textArea.setLineWrap(true);
		JScrollPane sbrText = new JScrollPane(textArea);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(sbrText,BorderLayout.CENTER);
	}
	
	public void setText(String text){
		textArea.setText(text);
	}
	
	public String getText(){
		return textArea.getText();
	}

}
