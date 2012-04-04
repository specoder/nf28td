package TD1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConsoleView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int locX = 300;
	static int locY = 300;
	static int width = 300;
	static int height = 150;
	static int locBoutonY = 40;
	static int sliderInitPercent = 50;

	private Console c;

	public void setModel(Console model){
		c = model; 
	}

	public ConsoleView(String title){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);	
		setSize(width,height);
		setLocation(locX,locY);

		//		final JTextField textFreq = new JTextField("40");
		//		textFreq.setSize(width, 30);

		final JSlider jsld = new JSlider();
		jsld.setValue(sliderInitPercent);
		jsld.setSize(width, 30);

		JButton buttonStart = new JButton();
		buttonStart.setSize(width*2/3,70);
		buttonStart.setBackground(Color.cyan);
		buttonStart.setLocation(0, locBoutonY);
		buttonStart.setText("Start");

		JButton buttonStop = new JButton();
		buttonStop.setSize(width/3, 70);
		buttonStop.setBackground(Color.pink);
		buttonStop.setLocation(width*2/3,locBoutonY);
		buttonStop.setText("Stop");

		//this.add(textFreq);
		this.add(jsld);
		this.add(buttonStart);
		this.add(buttonStop);
		
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				c.startTimer();
				System.out.println("Timer start...");
			}
		});

		buttonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				c.stopTimer();
				System.out.println("Timer stop...");
			}
		});

		/*	
		textFreq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				long interv = Long.valueOf(textFreq.getText())* 1000;
				c.setIntervalle(interv);
				System.out.println("interval is being modified to: "+interv);
			}
		});
		
		*/		
		jsld.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				long interv = Long.valueOf(Console.maxTime*jsld.getValue()/100);
				c.setInterval(interv);
				System.out.println("interval is being modified to: "+interv+" miliseconds");
			}
		});
		
		setLayout(null);
		setVisible(true);
	}
}

