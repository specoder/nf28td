package TD5;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodeTransferable implements Transferable{

	protected static final DataFlavor nodeFlavor = new DataFlavor(
			DataFlavor.javaJVMLocalObjectMimeType,"ContactNode");

	private DefaultMutableTreeNode node; 

	public DefaultMutableTreeNode getNode() {
		return node;
	}

	public void setNode(DefaultMutableTreeNode contactNode) {
		this.node = contactNode;
	}

	public NodeTransferable(DefaultMutableTreeNode node){
		setNode(node); 
	}

	public static DataFlavor getNodeFlavor () {
		return nodeFlavor;}

	@Override
	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub

		if (arg0 == nodeFlavor)
			return node;
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		DataFlavor[] result = {nodeFlavor};
		return result;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) {
		// TODO Auto-generated method stub
		return Arrays.asList(getTransferDataFlavors()).contains(arg0);
	}
}
