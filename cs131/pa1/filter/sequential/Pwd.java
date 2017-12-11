package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Pwd extends SequentialFilter {
	public String subCommand;
	String currDir;
	
	public Pwd (String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		if(tokens.length > 1) {//checks for parameter, if so return error
			System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		}
		currDir = SequentialREPL.currentWorkingDirectory;
	}
	
	public void process(){
		String processedLine = processLine(currDir);//returns currentWorkingDirectory
		output.add(processedLine);
	}
	
	protected String processLine(String line) {
		return line;
	}
	
	public void setPrevFilter(Filter prevFilter) {//checks if input exists, if so return error
		prevFilter.setNextFilter(this);
		System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.subCommand));
		SequentialCommandBuilder.errorCheck = false;
	}
}
