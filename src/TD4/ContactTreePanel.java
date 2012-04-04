package TD4;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class ContactTreePanel extends JPanel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JTree m_contactTree;

	public JTree getContactTree() {
		return m_contactTree;
	}
	
	public ContactTreePanel() {
		//cttEditPanel = newPanel;
		setLayout(new BorderLayout());
		m_contactTree = new JTree();
		m_contactTree.setModel(null);
		m_contactTree.setPreferredSize(new Dimension(200,300));
		m_contactTree.setBorder(BorderFactory.createTitledBorder("Contacts"));

		this.add(m_contactTree,BorderLayout.CENTER);
	}
	
	
	public void addTreeSelLsn(final JTabbedPane tabbedPane){
		
		m_contactTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)m_contactTree.getLastSelectedPathComponent();
				System.out.println("selection... = " + node);
				
				if (node == null) return; // very important!!!
				/*
				 * When adding a contact to the root "contacts", we should call the function setRoot(...),
				 * and then, it will notify the view to refresh (clear all the selection), 
				 * meanwhile, the listener's member function will also be called, it will try to get the
				 * item which is selected, but meanwhile, we have nothing selected.
				 * Thus, node = null -> null pointer problem will occur in the line below.
				 * 
				 * To simplify, the function can be called in many cases. What we need is to check if we have
				 * something selected in the tree, if not, we do nothing.
				 */
			
				Object nodeInfo = node.getUserObject(); 
				ContactEditPanel tabEdit = (ContactEditPanel) (tabbedPane.getComponentAt(1));
				
				if(nodeInfo instanceof Contact){
					
					//cttEditPanel.registreTreeVue(m_contactTree); // Only when a Contact is selected, the JTree can be refreshed
					// by valid button (the contactEditPanel needs the JTree) 
					Contact person = (Contact) nodeInfo;
					
					tabEdit.setVisible(true);
					tabEdit.setNomText(person.getNom());	
					tabEdit.setEmailText(person.getMail());
					tabEdit.setIconUrl(person.getIconURL());
					tabbedPane.setSelectedIndex(1);
				}
				if(nodeInfo instanceof String){
					//cttEditPanel.registreTreeVue(null);
					tabEdit.clear();
//					tabEdit.setVisible(false);
					tabbedPane.setSelectedIndex(0);
					tabEdit.setEnabled(false);
				}
			}
		});
	}
	
	public void clearSelection(){
		m_contactTree.clearSelection();
	}
	
	public void setContactTreeModel(ContactTreeModel model){
		m_contactTree.setModel(model);
	}

}
