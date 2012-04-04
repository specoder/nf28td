package TD4;
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
		xmlText = "";  // clear the xmlText. Important!
		processTree((DefaultMutableTreeNode) getRoot(),0);
		return xmlText;
	}

	public String nbsp(int n)
	{
		String res ="";
		for (int i = 0; i < n*5; i++) {
			res=res+" ";
		}
		return res;
	}


	public void processTree(DefaultMutableTreeNode node,int n){    //visit all the nodes of the tree in depth-first way
		// int n is the indicator of depth
		// There are only two node types in the tree

		Object tempObj = node.getUserObject();

		if (tempObj instanceof String){  //The string node can be leaf or not.

			xmlText = xmlText + nbsp(n) + "<"+ tempObj +">\n";

			for (int i=0; i < node.getChildCount(); i++){  // if the String Node has no children, do nothing,
				// if not, visit recursively all his children
				DefaultMutableTreeNode childNode =(DefaultMutableTreeNode) node.getChildAt(i);
				processTree(childNode,n+1);
			}
			
			xmlText = xmlText + nbsp(n) +"</"+ tempObj +">\n";
		}
		else{	// the Contact Node is always the leaf
			Contact ctt= (Contact) tempObj;
			xmlText = xmlText + nbsp(n) + "<contact>\n";
			xmlText = xmlText + nbsp(n+1) + "<nom>"+ ctt.getNom() +"</nom>\n";
			xmlText = xmlText + nbsp(n+1) + "<mail>"+ ctt.getMail() +"</mail>\n";
			xmlText = xmlText + nbsp(n+1) + "<icone>"+ ctt.getIconURL() +"</icone>\n";
			xmlText = xmlText + nbsp(n) + "</contact>\n";
		}
	}


}
