package cs131.pa1.filter.sequential;


import cs131.pa1.filter.Message;

public class Grep extends SequentialFilter {
	public String subCommand;
	String inputString;
	
	public Grep (String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		
		if(tokens.length == 1) {//checks if there is parameter
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else if(tokens.length > 2) {//checks if there are multiple parameters
			System.out.print(Message.INVALID_PARAMETER.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else {
			this.inputString = tokens[1];
		}
	}
	
	public void process(){
		if(input == null) {//checks for input, if none then return error
			System.out.print(Message.REQUIRES_INPUT.with_parameter(this.subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else {//add to output every line that contains string
			while (!input.isEmpty()){
				String processedLine = processLine(null);
				if (processedLine != null){
					output.add(processedLine);
				}
			}
		}
			
	}
	
	protected String processLine(String line) {
		line = input.remove();
		if(line.contains(this.inputString)) {//checks if line contains the string
			return line;
		}
		return null;
	}
}
