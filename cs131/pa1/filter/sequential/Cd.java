package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialREPL;

public class Cd extends SequentialFilter {
	public String subCommand;
	String fileName;

	public Cd (String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		if(SequentialCommandBuilder.errorCheck) {
			if(tokens.length == 2 ) {
				this.fileName = tokens[1];
			} else if(tokens.length == 1) {//checks for parameter
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.subCommand));
				SequentialCommandBuilder.errorCheck = false;
			} else if(tokens.length > 2) {//checks for multiple parameters
				System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
				SequentialCommandBuilder.errorCheck = false;
			}
		}
		
		if(SequentialCommandBuilder.errorCheck) {//checks if error has occurred yet, if not proceed
			if(tokens[1].equals(".")) {//does not change directory
				
			} else if(tokens[1].equals("..")) {//changes to previous directory
				int index;
				index = SequentialREPL.currentWorkingDirectory.lastIndexOf(FILE_SEPARATOR);
				SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory.substring(0, index);
			} else if(tokens.length < 3) {
				File currfile = new File(SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR + tokens[1]);
				if(currfile.isDirectory()) {//checks if directory exists
					SequentialREPL.currentWorkingDirectory = currfile.getAbsolutePath();
				} else {//else return error
					System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(this.subCommand));
					SequentialCommandBuilder.errorCheck = false;
				}
			}
		}
				
	}
	
	public void process(){//no process
		
	}
	protected String processLine(String line) {	//no processLine
		return null;
	}
	
	public void setPrevFilter(Filter prevFilter) {//checks if there is input, if yes then error
		prevFilter.setNextFilter(this);
		System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.subCommand));
		SequentialCommandBuilder.errorCheck = false;
	}
	
}