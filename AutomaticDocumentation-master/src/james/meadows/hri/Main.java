package james.meadows.hri;

import javax.swing.SwingUtilities;

import james.meadows.hri.gui.DocumentationGUI;

public class Main {

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		runDocumentationProgram();
		Long finish = System.currentTimeMillis();
		Long dif = finish - start;
		System.out.println("Finished in " + dif + " milliseconds.");
	}


	public static void runDocumentationProgram() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new DocumentationGUI();
			}
		});

	}

}
