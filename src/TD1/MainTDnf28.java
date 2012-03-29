package TD1;

public class MainTDnf28 {
	public static void main(String[] args) {
		td1();				
	}
	
	public static void td1() {
		Console console = new Console();
		
		ImageView imageView = new ImageView("Images");
		console.addObserver(imageView);
		
		ConsoleView consoleView = new ConsoleView("Console");
		consoleView.setModel(console);
		
		System.out.println("program finished");
	}
}
