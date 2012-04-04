package TD5;
import javax.swing.tree.DefaultMutableTreeNode;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class ContactHandler extends DefaultHandler {

	private int fieldFlag;
	private String buffer;

	private String contactNom;
	private String contactMail;
	private String contactIcon;

	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode tempNode;
	private DefaultMutableTreeNode currentNode;

	public ContactTreeModel getContactTreeModel() {
		return new ContactTreeModel(root);
	}

	public ContactHandler(){
		super();
		fieldFlag = 0;
	}

	public void characters(char ch[], int start, int length){  // get the text between two tags
		buffer= new String(ch, start, length).trim(); // delete all the space leading and following
		if (buffer.length() != 0){
			switch (fieldFlag){
			case 0 :
				contactNom = buffer;
				fieldFlag++;
				break;
			case 1 :
				contactMail = buffer;
				fieldFlag++;
				break;
			case 2:
				contactIcon = buffer;
				Contact ctt = new Contact(contactNom,contactMail,contactIcon);
				tempNode = new DefaultMutableTreeNode();
				tempNode.setUserObject(ctt);
				currentNode.add(tempNode);
				fieldFlag = 0;
				break;
			default:
				break;
			}
		}

	}

	public void startElement(String uri,String localName,String qName,Attributes attr){   // attribute is something in the tag.
		if ( localName == "contacts"){  //root node
			root = new DefaultMutableTreeNode();
			root.setUserObject(localName);
			currentNode = root;
		}
		else if ( localName != "nom" && localName != "mail" && localName != "icone" && localName != "contact" ){  // contact node 
			tempNode = new DefaultMutableTreeNode();
			tempNode.setUserObject(localName);
			currentNode.add(tempNode);
			currentNode = tempNode;   // go down
		}
		
	}  

	public void endElement(String uri,String localName,String qName){  
		if ( localName != "nom" && localName != "mail" && localName != "icone" && localName != "contact" ){
			currentNode = (DefaultMutableTreeNode) currentNode.getParent();  // go up
		}
		
	}  

}
