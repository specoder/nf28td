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
	private ContactTreePanel treePanel;
	private JTabbedPane ongletPanel;

	private ContactTreeModel treeModel;
	private String textXML;
	private String contactFile;

	public AppWindow() {
		final AppWindow thisOne = this;

		JMenuBar fileMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");

		JMenuItem fileOpenItem = new JMenuItem("Ouvir");
		final JMenuItem fileSaveItem = new JMenuItem("Sauver");
		final JMenuItem saveAsItem = new JMenuItem("Sauver sous");
		JMenuItem quitItem = new JMenuItem("Quitter");

		JMenu editMenu = new JMenu("Editer");
		final JMenuItem addContactItem = new JMenuItem("Ajouter Contact");
		final JMenuItem checkXmlItem = new JMenuItem("Voir XML");

		JSplitPane splitPane;

		final XmlTextPanel xmlPanel = new XmlTextPanel();
		final ContactEditPanel contactPanel = new ContactEditPanel();
		treePanel = new ContactTreePanel();
		contactPanel.registreXmlTextPanel(xmlPanel); 
		contactPanel.registreTreeVue(treePanel.getContactTree());

		ongletPanel = new JTabbedPane();
		ongletPanel.setPreferredSize(new Dimension(400,300));

		fileMenuBar.add(fileMenu);
		fileMenu.add(fileOpenItem);
		fileMenu.add(fileSaveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(quitItem);

		fileMenuBar.add(editMenu );
		fileSaveItem.setEnabled(false);
		saveAsItem.setEnabled(false);
		addContactItem.setEnabled(false);
		checkXmlItem.setEnabled(false);
		editMenu.add(addContactItem);
		editMenu.add(checkXmlItem);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treePanel);
		splitPane.setRightComponent(ongletPanel);
		splitPane.setContinuousLayout(true);
		this.getContentPane().add(splitPane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		setJMenuBar(fileMenuBar);

		// Implement listeners:

		treePanel.addTreeSelLsn(ongletPanel);

		fileSaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(contactFile != null && textXML != null){
					try {
						FileWriter fileWriter = new FileWriter(contactFile);
						PrintWriter out = new PrintWriter(fileWriter,true); // true : autoFlush
						out.println(xmlPanel.getText());
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0); 
			}
		});

		addContactItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Contact ctt = new Contact("Nouveau Contact","Nouveau Email","");
				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode();
				tempNode.setUserObject(ctt);

				if ( treeModel != null){
					DefaultMutableTreeNode r = (DefaultMutableTreeNode) treeModel.getRoot();
					r.add(tempNode);
					treeModel.setRoot(r);
				}
				/*else{ 	// when no XML files are imported ---> not necessary
					treeModel = new ContactTreeModel(tempNode);
					treePanel.setContactTreeModel(treeModel) ;
				}*/


				// The notification will be emitted only when one of the Model's setters is called;
			}
		});

		checkXmlItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ongletPanel.setSelectedIndex(0);
			}
		});

		fileOpenItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Select a contact file ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Import contact file...");
				dirFileChooser.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
					}
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.xml";
					}
				});

				int value = dirFileChooser.showOpenDialog(null);
				if( value == JFileChooser.APPROVE_OPTION ){
					//OK
					System.out.println("OK");

					ongletPanel.addTab("XML", xmlPanel);
					ongletPanel.addTab("Contact", contactPanel);
					fileSaveItem.setEnabled(true);
					saveAsItem.setEnabled(true);
					addContactItem.setEnabled(true);
					checkXmlItem.setEnabled(true);

					try {
						File tempFile = dirFileChooser.getSelectedFile();
						contactFile = tempFile.getPath();
						thisOne.setTitle(tempFile.getName());
						treeModel = ContactFacility.parse(dirFileChooser.getSelectedFile().getPath());
						treePanel.setContactTreeModel( treeModel ) ;
						textXML = treeModel.toXml();
						xmlPanel.setText(textXML);

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
				}
				else if(value == JFileChooser.CANCEL_OPTION){
					//Cancel
					System.out.println("CANCLE");
				}
			}
		});
		
		saveAsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Save to file ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Create contact file...");
				dirFileChooser.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
					}
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.xml";
					}
				});

				int value = dirFileChooser.showSaveDialog(null);
				if( value == JFileChooser.APPROVE_OPTION ){
					//OK
					System.out.println("OK");

					FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(dirFileChooser.getSelectedFile().getPath()+".xml");
						PrintWriter out = new PrintWriter(fileWriter,true); // true : autoFlush
						out.println(xmlPanel.getText());
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(value == JFileChooser.CANCEL_OPTION){
					//Cancel
					System.out.println("CANCLE");
				}
			}
		});

	}
}