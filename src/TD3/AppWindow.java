package TD3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

import org.xml.sax.SAXException;



public class AppWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContactTreePanel treePanel;
	private XmlTextPanel textPanel;

	public AppWindow() {
		//this.setTitle("contacts.")
		final AppWindow thisOne = this;
		
		JMenuBar fileMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		JMenuItem fileOpen = new JMenuItem("Open");

		JSplitPane splitPane;

		textPanel = new XmlTextPanel();
		treePanel = new ContactTreePanel();

		fileMenuBar.add(fileMenu);
		fileMenu.add(fileOpen);
		setJMenuBar(fileMenuBar);
		fileOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Select a dictionary file ...");
				JFileChooser dirFileChooser = new JFileChooser();
				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Import dictionary");
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
					try {
						thisOne.setTitle(dirFileChooser.getSelectedFile().getName());
						ContactTreeModel tempModel = ContactFacility.parse(dirFileChooser.getSelectedFile().getPath());
						treePanel.setContactTreeModel( tempModel ) ;
						textPanel.setText(tempModel.toXml());
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

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treePanel);
		splitPane.setRightComponent(textPanel);
		this.getContentPane().add(splitPane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}