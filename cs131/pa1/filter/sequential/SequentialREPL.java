package cs131.pa1.filter.sequential;
import java.util.*;

import cs131.pa1.filter.Message;

public class SequentialREPL {
	
	static String currentWorkingDirectory;
	
	public static void main(String[] args) {
		System.out.print(Message.WELCOME);//print welcome message
		Scanner console = new Scanner(System.in);
		boolean check = true;
		String userInput;
		currentWorkingDirectory = System.getProperty("user.dir");//reset working directory to default directory
		do {//dowhile loop for the REPL loop
			System.out.print(Message.NEWCOMMAND);
			userInput = console.nextLine();
			if(userInput.equals("exit")) {
				check = false;
				System.out.print(Message.GOODBYE);
			} else if(userInput.equals("")) {
				continue;
			} else {
				 List<SequentialFilter> filters = SequentialCommandBuilder.createFiltersFromCommand(userInput);
				//if no filters created or error found, moves to next loop
				 if(filters == null || !SequentialCommandBuilder.errorCheck) {
					 continue;
				 }
				 for (SequentialFilter filter: filters){
					 filter.process();
				 }
			}
		} while(check);//dowhile ends when user inputs exit
		console.close();
	}
}
