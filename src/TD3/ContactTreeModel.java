package TD3;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


public class ContactTreeModel extends DefaultTreeModel{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xmlText;

	public ContactTreeModel (TreeNode node){
		super(node);
		xmlText = "";
	}

	public String toXml(){
		processTree((DefaultMutableTreeNode) getRoot());
		return xmlText;
	}


	public void processTree(DefaultMutableTreeNode node){    //visit all the nodes of the tree in depth-first way
		// There are only two node types in the tree

		Object tempObj = node.getUserObject();

		if (tempObj instanceof String){  //The string node can be leaf or not.
			xmlText = xmlText + "<"+ tempObj +">\n";
			for (int i=0; i < node.getChildCount(); i++){  // if the String Node has no children, do nothing,
				// if not, visit recursively all his children
				DefaultMutableTreeNode childNode =(DefaultMutableTreeNode) node.getChildAt(i);
				processTree(childNode);
			}
			xmlText = xmlText + "</"+ tempObj +">\n";
		}
		else{	// the Contact Node is always the leaf
			Contact ctt= (Contact) tempObj;
			xmlText = xmlText + "<contact>\n";
			xmlText = xmlText + "<nom>"+ ctt.getNom() +"</nom>\n";
			xmlText = xmlText + "<mail>"+ ctt.getMail() +"</mail>\n";
			xmlText = xmlText + "<icone>"+ ctt.getIcon() +"</icone>\n";
			xmlText = xmlText + "</contact>\n";
		}
	}
}
