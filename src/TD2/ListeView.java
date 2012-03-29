package TD2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
class DefinitionText extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefinitionText(){
		setText("No words choosen");
		setEditable(false);
	}
}

public class ListeView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AppModel m_model;

	public ListeView(String title, AppModel m){
		m_model = m;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setPreferredSize(new Dimension(500,300));
		

		final JFileChooser dirFileChooser = new JFileChooser();

		JMenuBar fileMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		JMenuItem fileOpen = new JMenuItem("Open");

		fileMenuBar.add(fileMenu);
		fileMenu.add(fileOpen);

		final JList wordsList = new JList();
		wordsList.setModel(m_model.getListModel());
		wordsList.setPreferredSize(new Dimension(500,150));
		wordsList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		final DefinitionText defText = new DefinitionText();
		defText.setPreferredSize(new Dimension(500,90));
		defText.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		PropertyChangeListener listener = new PropertyChangeListener(){
			public  void propertyChange(PropertyChangeEvent evt) {
				defText.setText(evt.getNewValue().toString());
				System.out.println("property changed!");
			}
		};

		m_model.addPropertyChangeListener(listener);

		//add(wordsList,BorderLayout.CENTER);
		//add(fileMenuBar);
		//add(defText,BorderLayout.SOUTH);
		//setLayout(null);
		//setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(180);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(wordsList);
		splitPane.setBottomComponent(defText);
		splitPane.setContinuousLayout(true);
		
		
		this.setJMenuBar(fileMenuBar);
		this.add(splitPane);
		setVisible(true);
		this.pack();

		fileOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Select a dictionary file ...");

				dirFileChooser.setCurrentDirectory(new File("."));
				dirFileChooser.setDialogTitle("Import dictionary");
				dirFileChooser.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "*.txt";
					}
				});

				int value = dirFileChooser.showOpenDialog(null);
				if( value == JFileChooser.APPROVE_OPTION ){
					//OK
					System.out.println("OK");
					wordsList.clearSelection(); // important!
					File dicFile = new File(dirFileChooser.getSelectedFile().getPath());
					m_model.processFile(dicFile);
				}
				else if(value == JFileChooser.CANCEL_OPTION){
					//Cancel
					System.out.println("CANCLE");
				}
			}
		});
		
		wordsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(wordsList.getSelectedIndex() != -1)
					m_model.getPptyChgSpt().firePropertyChange("", "", m_model.lookUp(wordsList.getSelectedValue()));
			}
		});
	}
	
}


