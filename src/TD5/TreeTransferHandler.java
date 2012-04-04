package TD5;

import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
		DefaultMutableTreeNode node = 
				(DefaultMutableTreeNode)ContactTreePanel.m_contactTree.getLastSelectedPathComponent();
		return (Transferable) new NodeTransferable(node);
	}

	public boolean canImport(TransferHandler.TransferSupport support){
		if(!support.isDrop()) 
			return false;

		if(!support.getTransferable().isDataFlavorSupported(NodeTransferable.nodeFlavor))
			return false;

		return true;
	}

	public boolean importData(TransferSupport support) {
		if (canImport(support)) {
			try {
				Transferable t = support.getTransferable();
				DefaultMutableTreeNode dmt = (DefaultMutableTreeNode)
						t.getTransferData(NodeTransferable.nodeFlavor);
				JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
				TreePath tp = dl.getPath();
				if (tp == null) {return false;}
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
						tp.getLastPathComponent();
				if (parent.getUserObject() instanceof Contact) {return false;}
				JTree tree = (JTree)support.getComponent();
				DefaultTreeModel tm = (DefaultTreeModel) tree.getModel();
				parent.add(dmt);
				tm.reload();
				tree.expandPath(tp);
				return true;
			}
			catch(Exception ex) {ex.printStackTrace();}
		}
		return false;
	}
}
