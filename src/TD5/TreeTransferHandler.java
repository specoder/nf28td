package TD5;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class TreeTransferHandler extends TransferHandler implements Transferable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeTransferHandler(){
		??
	}
	
	public static DataFlavor getNodeFlavor () {
		return NodeTransferable.nodeFlavor;}

	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		if (arg0 == NodeTransferable.nodeFlavor)
			return NodeTransferable.nodeFlavor;
		return null;
	}

	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		DataFlavor[] result = {NodeTransferable.nodeFlavor};
		return result;
	}

	public boolean isDataFlavorSupported(DataFlavor arg0) {
		// TODO Auto-generated method stub
		return Arrays.asList(getTransferDataFlavors()).contains(arg0);
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
				
				System.out.println(t.getTransferData(NodeTransferable.nodeFlavor));
				
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
