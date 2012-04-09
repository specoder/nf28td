package TD6;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

public class FileTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean canImport(TransferHandler.TransferSupport support){
		
		
		if(!support.isDrop()) 
			return false;

		if(!support.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor))
			return false;
		
		return true;
	}

	public boolean importData(TransferSupport support) {
		Transferable tr = support.getTransferable();

		if(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			try {
				File f = null;
				@SuppressWarnings("unchecked")
				List<File> fl = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (int i = 0; i < fl.size(); i++) {
					f = fl.get(i);
				}
				
				AppWindow.m_instance.openFile(f);
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
