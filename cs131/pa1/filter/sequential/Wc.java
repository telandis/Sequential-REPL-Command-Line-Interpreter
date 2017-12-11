package cs131.pa1.filter.sequential;

import java.util.Scanner;

import cs131.pa1.filter.Message;

public class Wc extends SequentialFilter {
	public String subCommand;
	String fileName;
	Scanner fileReader;
	int currLineCount;
	int currWordCount;
	int currCharCount;
	
	public Wc (String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		this.currLineCount = 0;
		this.currWordCount = 0;
		this.currCharCount = 0;
		
		if(tokens.length > 1) {//checks for parameter, if so return error
			System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		}
		
	}
	
	public void process(){
		while (!input.isEmpty()){//processes every line and updates word, char, and line counts
			processLine(null);			
		}	
		output.add(this.currLineCount + " " + this.currWordCount + " " + this.currCharCount);//updates output with word, char, line count
	}
	
	protected String processLine(String line) {
		//counts number of words and characters in the line, adds to total and increments line count
		line = input.remove();
		String[] words = line.split("\\s+");
		int spaceCounter = 0;
		for(String j: words) {//checks for any space characters, and increments the spaceCounter every time
			if(j.equals(" ")) {
				spaceCounter++;
			}
		}
		this.currWordCount += words.length - spaceCounter;//counts words, subtracts spaceCounter and adds to total
		this.currCharCount += line.length();//counts chars in this line
		this.currLineCount++;
		return null;
	}
}
