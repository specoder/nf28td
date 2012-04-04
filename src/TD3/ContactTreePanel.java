package TD3;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTree;


public class ContactTreePanel extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree contactTree;
	
	public ContactTreePanel() {
		contactTree = new JTree();
		contactTree.setModel(null);
		contactTree.setPreferredSize(new Dimension(200,400));
		
		this.add(contactTree);
	}
	
	public void setContactTreeModel(ContactTreeModel model){
		contactTree.setModel(model);
	}

}
