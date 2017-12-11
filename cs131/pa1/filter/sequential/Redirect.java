package cs131.pa1.filter.sequential;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PrintStream;
import cs131.pa1.filter.Message;

public class Redirect extends SequentialFilter {
	public String subCommand;
	String fileName;
	PrintStream finalFile;
	File f = null;
	
	public Redirect(String[] tokens, String subCommand) {
		this.subCommand = subCommand;
		if(tokens.length == 1) {//check for parameter, if none return error
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else if(tokens.length > 2) {//check for multiple parameter, return error if so
			System.out.print(Message.INVALID_PARAMETER.with_parameter(subCommand));
			SequentialCommandBuilder.errorCheck = false;
		} else {
			this.fileName = tokens[1];
			try {
				
				f = new File (SequentialREPL.currentWorkingDirectory, fileName);//create new file object
				
				if (f.exists()) {//if file exists already delete it
					f.delete();
					f.createNewFile();
				} else {
					f.createNewFile();//else create the new file
				}
				
				finalFile = new PrintStream (f);//open up printstream
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//java requires catching exception
			} catch (IOException e) {
				//java requires catching exception
			}
		}
	}
	
	public void process(){
		while (!input.isEmpty()){//if there is input continue
			String line = input.remove();
			if (!line.equals(f.getName())) {//since the file is created beforehand, don't count it if its read and returned
				finalFile.println(line);
			}
		}	
		finalFile.close();//close printstream
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
