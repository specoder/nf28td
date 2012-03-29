package TD2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.DefaultListModel;

class ListeModel extends DefaultListModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListeModel(){
		super();
	}
}

public class AppModel{
	
	private ListeModel listModel;
	private PropertyChangeSupport pptyChgSpt;
	HashMap<String , String> dicMap = new HashMap<String , String>(); // save the dictionary in the hashdicMap
	
	public PropertyChangeSupport getPptyChgSpt(){
		return pptyChgSpt;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pptyChgSpt.addPropertyChangeListener(listener);
	}

	public AppModel(){
		listModel = new ListeModel();
		pptyChgSpt = new PropertyChangeSupport(this);
	}

	public ListeModel getListModel(){
		return this.listModel;
	}

	public Object lookUp(Object object){
		return dicMap.get(object);
	}

	public void processFile(File dicFile){
		listModel.clear();
		dicMap.clear();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(dicFile));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				String[] wordDefPair = tempString.split("=");
				dicMap.put(wordDefPair[0],wordDefPair[1]);
				listModel.addElement(wordDefPair[0]);
			}
			reader.close();

			/*
			Iterator<Entry<String, String>> iter = dicMap.entrySet().iterator(); 
			String[] wordsStringList = new String[dicMap.size()];
			int i = 0;
			while (iter.hasNext()) { 
				Entry<String, String> entry = (Entry<String, String>) iter.next(); 
				Object key = entry.getKey();
				wordsStringList[i] = key.toString();
				i++;
			} 

			java.util.Arrays.sort(wordsStringList); //ascendant order
			//java.util.Arrays.sort(wordsStringList,Collections.reverseOrder()); // descendant order 

			for (int j = 0; j < wordsStringList.length; j++) {
				System.out.println(wordsStringList[j]);
			}

			wordsList.setListData(wordsStringList);
			 */
			
		}
		catch (IOException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}
}


