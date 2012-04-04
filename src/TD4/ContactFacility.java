package TD4;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class ContactFacility {
		
	public static ContactTreeModel parse(String filename)
			throws SAXException, FileNotFoundException, IOException {
		//String xmlReaderClassName = "org.apache.crimson.parser.XMLReaderImpl";
		String xmlReaderClassName = "org.apache.xerces.parsers.SAXParser";
		XMLReader xr = XMLReaderFactory.createXMLReader(xmlReaderClassName);
		ContactHandler handler = new ContactHandler();
		xr.setContentHandler((ContentHandler) handler);
		xr.setErrorHandler((ErrorHandler) handler);
		FileReader r = new FileReader(filename);
		xr.parse(new InputSource(r));	
		return handler.getContactTreeModel();
	}
}


