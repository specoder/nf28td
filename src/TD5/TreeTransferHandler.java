package TD5;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeTransferHandler(){	
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
		if(!support.isDrop()) 
			return false;

		if(!support.getTransferable().isDataFlavorSupported(NodeTransferable.nodeFlavor)
				&& support.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)	)
			return false;

		return true;
	}

	public boolean importData(TransferSupport support) {
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
					Contact ctt = new Contact(txt,txt,"");
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
