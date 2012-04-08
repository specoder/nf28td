package TD5;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.xml.sax.SAXException;

public class AppWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	System.out.print;

	public static AppWindow m_instance;

	private ContactTreePanel treePanel;
	private JTabbedPane ongletPanel;

	private ContactTreeModel treeModel;
	private String textXML;
	private String contactFile;

	private JMenuItem fileSaveItem;
	private JMenuItem saveAsItem;

	private JMenuItem addContactItem;
	private JMenuItem checkXmlItem;

	// XML Text Panel
	private XmlTextPanel xmlPanel;

	// Contact Edit Panel
	private ContactEditPanel contactPanel;

	public void openFile(File f) throws FileNotFoundException, SAXException,
	IOException {
		ongletPanel.addTab("XML", xmlPanel);
		ongletPanel.addTab("Contact", contactPanel);

		fileSaveItem.setEnabled(true);
		saveAsItem.setEnabled(true);

		addContactItem.setEnabled(true);
		checkXmlItem.setEnabled(true);

		contactFile = f.getPath();
		this.setTitle(f.getName());
		treeModel = ContactFacility.parse(f.getPath());
		treePanel.setContactTreeModel(treeModel); // update model
		textXML = treeModel.toXml();
		xmlPanel.setText(textXML);

	}

	public AppWindow() {
		this.setLocation(300, 300);

		this.setTransferHandler(new FileTransferHandler()); // enable drag

		JMenuBar fileMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");

		JMenuItem fileOpenItem = new JMenuItem("Ouvir");
		fileSaveItem = new JMenuItem("Sauver");
		saveAsItem = new JMenuItem("Sauver sous");
		JMenuItem quitItem = new JMenuItem("Quitter");

		JMenu editMenu = new JMenu("Editer");
		addContactItem = new JMenuItem("Ajouter Contact");
		checkXmlItem = new JMenuItem("Voir XML");

		JSplitPane splitPane;

		// XML Text Panel
		xmlPanel = new XmlTextPanel();

		// Contact Edit Panel
		contactPanel = new ContactEditPanel();

		// Tree Panel
		treePanel = new ContactTreePanel();
		contactPanel.registerXmlTextPanel(xmlPanel); // register the xml panel
		// in the Contact Edit
		// Panel
		contactPanel.registerTreeVue(treePanel.getContactTree()); // register
		// the JTree
		// in the
		// Contact
		// Edit
		// Panel

		// tabs container for XML Text Panel & Tree Panel
		ongletPanel = new JTabbedPane();
		ongletPanel.setPreferredSize(new Dimension(400, 300));

		// Implement menu
		setJMenuBar(fileMenuBar);
		fileMenuBar.add(fileMenu);
		fileMenu.add(fileOpenItem);
		fileMenu.add(fileSaveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(quitItem);

		fileMenuBar.add(editMenu);
		fileSaveItem.setEnabled(false);
		saveAsItem.setEnabled(false);
		addContactItem.setEnabled(false);
		checkXmlItem.setEnabled(false);
		editMenu.add(addContactItem);
		editMenu.add(checkXmlItem);

		// Implement split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treePanel);
		splitPane.setRightComponent(ongletPanel);
		splitPane.setContinuousLayout(true);
		this.getContentPane().add(splitPane);

		// adjust Layout
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		// make this object accessible statically
		m_instance = this;

		// Implement listeners:

		treePanel.addTreeSelLsn(ongletPanel);

		// save file
		fileSaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (contactFile != null && textXML != null) {
					try {
						FileWriter fileWriter = new FileWriter(contactFile);
						PrintWriter out = new PrintWriter(fileWriter, true); // true
						// :
						// autoFlush
						out.println(xmlPanel.getText());
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		// exit app
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		// add contact
		addContactItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Contact ctt = new Contact("Nouveau Contact", "Nouveau Email",
						"");
				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode();
				tempNode.setUserObject(ctt);

				if (treeModel != null) {
					DefaultMutableTreeNode r = (DefaultMutableTreeNode) treeModel
							.getRoot();
					r.add(tempNode);
					xmlPanel.setText(treeModel.toXml());
					ContactTreePanel.m_contactTree.updateUI();
					treePanel.selectLastRow();
					// updateUI must be called from JTree, instead of treePanel,
					// because it is the treeModel who is modified, not the
					// model(if exist) of treePanel.
				}
			}
		});

		// check the XML text
		checkXmlItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ongletPanel.setSelectedIndex(0);
			}
		});

		// open file
		fileOpenItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Select a contact file ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Import contact file...");
				dirFileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".xml")
								|| f.isDirectory();
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.xml";
					}
				});

				int value = dirFileChooser.showOpenDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					// OK
					System.out.println("OK");

					try {

						File tempFile = dirFileChooser.getSelectedFile();
						openFile(tempFile);

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (value == JFileChooser.CANCEL_OPTION) {
					// Cancel
					System.out.println("CANCLE");
				}
			}
		});

		// save file as
		saveAsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Save to file ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Create contact file...");
				dirFileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".xml")
								|| f.isDirectory();
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.xml";
					}
				});

				int value = dirFileChooser.showSaveDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					// OK
					System.out.println("OK");

					FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(dirFileChooser
								.getSelectedFile().getPath() + ".xml");
						PrintWriter out = new PrintWriter(fileWriter, true); // true
						// :
						// autoFlush
						out.println(xmlPanel.getText());
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (value == JFileChooser.CANCEL_OPTION) {
					// Cancel
					System.out.println("CANCLE");
				}
			}
		});

	}
}
