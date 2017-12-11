package cs131.pa1.filter.sequential;

import java.io.File;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Ls extends SequentialFilter {
	public String subCommand;
	String[] allFiles;
	
	public Ls (String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		if(tokens.length > 1) {//checks for parameters, if so return error
			System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		}
		String currDir = SequentialREPL.currentWorkingDirectory;
		File currentDirFile = new File(currDir);
		allFiles = currentDirFile.list();//creates a list out of all files in the directory
		
		
	}
	
	public void process(){
		for(String file : allFiles) {//iterates all files in folder
			String processedLine = processLine(file);
			output.add(processedLine);//adds each file to output queue
		}
	}
	
	protected String processLine(String line) {
		return line;
	}

	public void setPrevFilter(Filter prevFilter) {//checks for input, if there is input return error
		prevFilter.setNextFilter(this);
		System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.subCommand));
		SequentialCommandBuilder.errorCheck = false;
	}
	
}
