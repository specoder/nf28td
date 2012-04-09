package TD6;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class NodeTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public NodeTransferHandler(){	

	}



	// JComponent c is the drag source

	public int getSourceActions(JComponent c){
		return MOVE; // javax.swing.TransferHandler.MOVE = 2 [0x2]
	}


	public Transferable createTransferable(JComponent c){
		DefaultMutableTreeNode node = null;
		if(c instanceof JTextComponent ){
			String text = ((JTextComponent) c).getText();

			return new NodeTransferable(text);
		}
		else{
			node = (DefaultMutableTreeNode)ContactTreePanel.m_contactTree.getLastSelectedPathComponent();
			return new NodeTransferable(node);
		}
	}

	public boolean canImport(TransferHandler.TransferSupport support){
		DataFlavor df[] = support.getTransferable().getTransferDataFlavors();

		for (int i = 0; i < df.length; i++) {
			if (df[i].equals(DataFlavor.javaFileListFlavor))
				System.out.println("file found");
		}

		if(!support.isDrop()) 
			return false;

		if(!support.getTransferable().isDataFlavorSupported(NodeTransferable.nodeFlavor)
				&& support.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)	)
			return false;


		return true;
	}

	public boolean importData(TransferSupport support) {
		//transfer file data: because the JTree covers the ContactTreePanel.
		Transferable tr = support.getTransferable();
		if(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			try {
				File f = null;
				@SuppressWarnings("unchecked")
				List<File> fl = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (int i = 0; i < fl.size(); i++) {
					f = fl.get(i);
				}
				
				AppWindow.m_instance.openFile(f);
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// transfer normal node data
		if (canImport(support)) {
			try {
				Transferable t = support.getTransferable();
				JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
				TreePath tp = dl.getPath();
				if (tp == null) {return false;}

				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) tp.getLastPathComponent();
				if (parent.getUserObject() instanceof Contact) {return false;}

				JTree tree = (JTree)support.getComponent();
				DefaultMutableTreeNode dmt = null;

				if( t.getTransferData(NodeTransferable.nodeFlavor) instanceof DefaultMutableTreeNode){
					dmt = (DefaultMutableTreeNode)t.getTransferData(NodeTransferable.nodeFlavor);
					parent.add(dmt);
				}
				else{
					String txt = (String) t.getTransferData(DataFlavor.stringFlavor);
					System.out.println("text = "+txt);
					Contact ctt = new Contact(txt,"unknown","");
					dmt = new DefaultMutableTreeNode();
					dmt.setUserObject(ctt);
					parent.add(dmt);
				}

				XmlTextPanel.textArea.setText(((ContactTreeModel)tree.getModel()).toXml());
				tree.updateUI();
				return true;
			}
			catch(Exception ex) {ex.printStackTrace();}
		}
		return false;
	}
}

