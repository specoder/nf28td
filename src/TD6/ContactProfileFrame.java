package TD6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;	
import javax.swing.JTextField;

public class ContactProfileFrame  extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static ContactProfileFrame m_instance;

	// Contact info
	private JTextField m_nomText; // name 
	private JTextField m_emailText; // email
	
	private JLabel m_iconLabel; 
	private ImageIcon  m_icon; // image label

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
	
	public void setAll(String nomText,String emailText,String iconUrl) {
		m_nomText.setText(nomText);
		m_emailText.setText(emailText);
		m_icon = new ImageIcon(iconUrl);
		System.out.println("m_icon = "+m_icon);
		m_iconLabel.setIcon(m_icon);
		m_iconLabel.setText(null);
	}
	
	

	public void clear() {
		m_nomText.setText(null);
		m_emailText.setText(null);
		m_iconLabel.setIcon(null);
		m_iconLabel.setText("Aucune image");
	}

	public ContactProfileFrame() {
		m_instance = this;
		this.setTransferHandler(new NodeTransferHandler());
		//pnl.setPreferredSize(new Dimension(400,370));
		
		//this.setTransferHandler(new FileTransferHandler());
		
		// name panel
		JPanel nomPanel = new JPanel();
		JLabel nomLabel = new JLabel ("Nom :");
		nomLabel.setPreferredSize(new Dimension(70,25));
		m_nomText = new JTextField();
		m_nomText.setEditable(false);
		m_nomText.setPreferredSize(new Dimension(300,25));
		nomPanel.add(nomLabel);
		nomPanel.add(m_nomText);

		// email panel		
		JPanel emailPanel = new JPanel();
		JLabel emailLabel = new JLabel ("Email :");
		emailLabel.setPreferredSize(new Dimension(70,25));
		m_emailText = new JTextField();
		m_emailText.setEditable(false);
		m_emailText.setPreferredSize(new Dimension(300,25));
		emailPanel.add(emailLabel);
		emailPanel.add(m_emailText);

		// avatar panel
		m_iconLabel = new JLabel("Aucune Image");

		JPanel avatarPanel = new JPanel();
		avatarPanel.setLayout(new BorderLayout());
		avatarPanel.add(m_iconLabel,BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setSize(400,370);
		this.setLocation(930, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// GridBagLayout
		this.setLayout(new GridBagLayout());		
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
	}
}