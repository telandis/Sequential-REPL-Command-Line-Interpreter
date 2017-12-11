package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Head extends SequentialFilter {
	public String subCommand;
	String fileName;
	Scanner fileReader;
	int maxLineCount;
	int currLineCount;

	public Head(String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		String currDir = SequentialREPL.currentWorkingDirectory;
		boolean isInt = true;
		if(tokens.length > 1) { //only runs if there is an input after Head
			for(String i: tokens[1].substring(1).split("\\|")) {			
				if(!Character.isDigit(i.charAt(0))) {
					isInt = false;
				}
			}
		}
		
		if(tokens.length == 1 || (tokens.length == 2 && tokens[1].substring(0, 1).equals("-"))) {
			//if no parameter, prints error
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else if(tokens.length == 3 && (!tokens[1].substring(0, 1).equals("-") || !isInt)) {
			//if number of lines parameter is invalid, prints error
			System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else {
			this.fileName = (tokens.length == 3) ? tokens[2] : tokens[1];//check for 
			this.maxLineCount = (int) ((tokens.length == 3) ? Integer.parseInt(tokens[1].substring(1)) : 10);
			
			this.currLineCount = 0;
			//checks if file exists
			try {
				Scanner fileReader= new Scanner(new File(currDir + FILE_SEPARATOR + fileName));
				this.fileReader = fileReader;
				//if not, catch exception and return error
			} catch (FileNotFoundException e) {
				System.out.print(Message.FILE_NOT_FOUND.with_parameter(this.subCommand));
				SequentialCommandBuilder.errorCheck = false;
			}
		}
		
		
	}
	
	public void process(){
		while (this.fileReader.hasNextLine() && this.currLineCount < this.maxLineCount){//processes the default 10 or w.e user input
			String processedLine = processLine(null);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
		this.fileReader.close();//close the file reader after its finished being used
	}
	
	protected String processLine(String line) {
		line = this.fileReader.nextLine();//returns next line
		this.currLineCount++;		
		return line;
	}
	
	public void setPrevFilter(Filter prevFilter) {//checks for input, if input exists then return error
		System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.subCommand));
		SequentialCommandBuilder.errorCheck = false;
	}
	
}
