package TD5;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;	
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ContactEditPanel  extends JPanel{

	private static final long serialVersionUID = 1L;

	private JTextField m_nomText; 
	private JTextField m_emailText;
	private String  m_iconUrl;

	private JLabel m_iconLabel; 
	private ImageIcon  m_icon;

	private JTree treeVue; // for refreshing JTree
	private XmlTextPanel xmlTextPanel; // for refreshing Xml text

	public void registreTreeVue(JTree treeVue) {
		this.treeVue = treeVue;
	}

	public void registreXmlTextPanel(XmlTextPanel xmlPanel) {
		this.xmlTextPanel = xmlPanel;
	}

	public void setNomText(String nomText) {
		m_nomText.setText(nomText);
	}

	public void setEmailText(String emailText) {
		m_emailText.setText(emailText);
	}

	public void setIconUrl(String iconUrl) {
		m_icon = new ImageIcon(iconUrl);
		m_iconLabel.setIcon(m_icon);
		m_iconLabel.setText(null);
	}

	public void clear() {
		m_nomText.setText(null);
		m_emailText.setText(null);
		m_iconLabel.setIcon(null);
		m_iconLabel.setText("Aucune image");
	}

	public ContactEditPanel() {

		// name panel
		JPanel nomPanel = new JPanel();
		JLabel nomLabel = new JLabel ("Nom :");
		nomLabel.setPreferredSize(new Dimension(70,25));
		m_nomText = new JTextField();
		m_nomText.setPreferredSize(new Dimension(300,25));
		nomPanel.add(nomLabel);
		nomPanel.add(m_nomText);

		// email panel		
		JPanel emailPanel = new JPanel();
		JLabel emailLabel = new JLabel ("Email :");
		emailLabel.setPreferredSize(new Dimension(70,25));
		m_emailText = new JTextField();
		m_emailText.setPreferredSize(new Dimension(300,25));
		emailPanel.add(emailLabel);
		emailPanel.add(m_emailText);

		// avatar panel
		m_iconLabel = new JLabel("Aucune Image");
		JButton imageButton = new JButton("Image");

		JPanel avatarPanel = new JPanel();
		avatarPanel.setLayout(new BorderLayout());
		avatarPanel.add(m_iconLabel,BorderLayout.CENTER);
		avatarPanel.add(imageButton,BorderLayout.SOUTH);

		// valid button panel
		JButton valideButton = new JButton("Valider");
		valideButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		/*this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.add(nomPanel);
		this.add(emailPanel);
		this.add(avatarPanel);
		this.add(valideButton);*/

		// GridBagLayout
		setLayout(new GridBagLayout());		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.weightx = 0.1; // at least one "weightx" should be set to put extra space between the grid.
		c1.gridwidth = 1;
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 0;
		this.add(nomPanel,c1);

		GridBagConstraints c2 = new GridBagConstraints();
		//c2.weightx = 0.1; // at least one "weightx" should be set to put extra space between the grid.
		c2.gridwidth = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 1;
		this.add(emailPanel,c2);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.weighty = 1;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		//c3.fill = GridBagConstraints.HORIZONTAL;
		c3.gridx = 0;
		c3.gridy = 2;
		this.add(avatarPanel,c3);

		GridBagConstraints c4 = new GridBagConstraints();
		c4.weighty = 1;
		c4.gridwidth = 1;
		//c4.insets = new Insets(0,0,10,0);  //bottom padding
		//c4.anchor = GridBagConstraints.PAGE_END; 
		c4.gridx = 0;
		c4.gridy = 3;
		this.add(valideButton,c4);

		// Listener

		valideButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeVue.getLastSelectedPathComponent();
				if(node == null){
					System.out.println("No item is selected");
					return;
				}
				Object nodeInfo = node.getUserObject(); 
				if(nodeInfo instanceof Contact){

					((Contact)nodeInfo).setNom(m_nomText.getText());
					((Contact)nodeInfo).setMail(m_emailText.getText());
					((Contact)nodeInfo).setIcon(m_iconUrl);

					
					treeVue.updateUI();
					xmlTextPanel.setText(((ContactTreeModel) (treeVue.getModel())).toXml());  // refresh XML text
					((JTabbedPane )(xmlTextPanel.getParent())).setSelectedIndex(0);
					// switch to xmlTextPanel
				}
				else{
					// disable valid
					System.out.println("No contact item is selected.");
					return;
				}
			}
		});

		imageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub


				DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeVue.getLastSelectedPathComponent();
				if(node == null){
					System.out.println("No item is selected");
					return;
				}
				Object nodeInfo = node.getUserObject(); 
				if(nodeInfo instanceof String){
					System.out.println("No contact item is selected.");
					return;
				}

				System.out.println("Select a contact picture ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Import contact pitcture...");
				dirFileChooser.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".jpg") || f.isDirectory();
					}
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.jpg";
					}
				});

				int value = dirFileChooser.showOpenDialog(null);
				if( value == JFileChooser.APPROVE_OPTION ){
					//OK
					System.out.println("OK");
					m_iconUrl = dirFileChooser.getSelectedFile().getPath();
					System.out.println("m_iconUrl = " + m_iconUrl);
					m_icon =  new ImageIcon (m_iconUrl);
					m_iconLabel.setIcon(m_icon);
				}
				else if(value == JFileChooser.CANCEL_OPTION){
					//Cancel
					System.out.println("CANCLE");
				}
			}
		});
	}
}