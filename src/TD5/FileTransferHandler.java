package TD5;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class FileTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*public FileTransferHandler(){	
	}*/


	
	public boolean canImport(TransferSupport support) {

		System.out.println("import1");
		return true;
	}

	public boolean importData(TransferSupport support) {
		Transferable tr = support.getTransferable();
		try {
			
			System.out.println("import3");
			System.out.println(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor));
			//tr.getTransferData(DataFlavor.javaFileListFlavor);
			DataFlavor [] df = tr.getTransferDataFlavors();
			
			for (int i = 0; i < df.length; i++) {
				System.out.println(df[i]);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
