package TD1;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class ImageView extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private int locX = ConsoleView.locX+ConsoleView.width;
	static private int locY = ConsoleView.locY;
	static private int width = 250;
	static private int height = 200;

	public JLabel lable; 

	public ImageView(String title){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setSize(width,height);
		setLocation(locX,locY);

		ImageIcon icon = new ImageIcon("image5.jpg","moom");
		//icon.setImage(icon.getImage().getScaledInstance(width,width,Image.SCALE_DEFAULT));

		lable = new JLabel(icon);
		lable.setSize(width, width);
		this.add(lable);
		setVisible(true);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof ImageIcon){
			ImageIcon ImgIcn = (ImageIcon) arg1;
			lable.setIcon((Icon)ImgIcn);
		}
	}
}

