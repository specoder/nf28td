package TD3;
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
	private DefaultMutableTreeNode nodeCatalogue;
	private DefaultMutableTreeNode nodeContact;

	public void addCatalogueNode(String nomCatalogue){
		nodeCatalogue = new DefaultMutableTreeNode ();
		nodeCatalogue.setUserObject(nomCatalogue);
	}

	public void addContactNode(String nom, String mail, String icon){
		Contact ctct = new Contact(nom,mail,icon);
		nodeContact = new DefaultMutableTreeNode ();
		nodeContact.setUserObject(ctct);
	}

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
				nodeContact = new DefaultMutableTreeNode();
				nodeContact.setUserObject(ctt);
				nodeCatalogue.add(nodeContact);
				fieldFlag = 0;
				break;
			default:
				break;
			}
		}

		System.out.println("value = " + buffer);
	}

	public void startElement(String uri,String localName,String qName,Attributes attr){   // attribute is something in the tag.
		if ( localName == "contacts"){  //root node
			root = new DefaultMutableTreeNode();
			root.setUserObject(new String("contacts"));
		}
		else if ( localName == "nom" || localName == "mail" || localName == "icone" || localName == "contact" ){  // contact node 
			// do nothing , because characters(...) will process nom, mail and icone.
			// never process contact tags.
		}
		else { // catalog node
			nodeCatalogue = new DefaultMutableTreeNode();
			nodeCatalogue.setUserObject(localName);
			root.add(nodeCatalogue);
		}
		System.out.println("<"+localName+">");  
	}  

	public void endElement(String uri,String localName,String qName){  
		System.out.println("</"+localName+">");    
	}  

}
