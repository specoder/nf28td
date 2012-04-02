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

public class DerivedTransferHandler extends TransferHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final DataFlavor nodeFlavor = new DataFlavor(
			DataFlavor.javaJVMLocalObjectMimeType,"ContactNode");

	public static DataFlavor getNodeFlavor () {
		return nodeFlavor;}

	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		if (arg0 == nodeFlavor)
			return nodeFlavor;
		return null;
	}

	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		DataFlavor[] result = {nodeFlavor};
		return result;
	}

	public boolean isDataFlavorSupported(DataFlavor arg0) {
		// TODO Auto-generated method stub
		return Arrays.asList(getTransferDataFlavors()).contains(arg0);
	}

	public int getSourceActions(JComponent c){
		return MOVE; // javax.swing.TransferHandler.MOVE = 2 [0x2]
	}

	public Transferable createTransferable(JComponent c){
		DefaultMutableTreeNode node = 
				(DefaultMutableTreeNode)ContactTreePanel.m_contactTree.getLastSelectedPathComponent();
		return new NodeTransferable(node);
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
