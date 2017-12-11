package cs131.pa1.filter.sequential;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cs131.pa1.filter.Message;

public class SequentialCommandBuilder {
	
	public static boolean errorCheck = true;//global boolean to check if an error has occurred at any time in the code, works as checkpoint
	public static ArrayList<String> subCommands;
	public static boolean shouldHaveOutput = true;//boolean for checking if something should be sending output
	
	public static List<SequentialFilter> createFiltersFromCommand(String command) {
		shouldHaveOutput = true;
		errorCheck = true; //if error is detected at any point, will cause method to return null
		LinkedList<SequentialFilter> filters = new LinkedList<>();
		subCommands = new ArrayList<>(Arrays.asList(command.split("\\|"))); 
	
		//method to create commands from redirects
		findRedirects(command);
		
		for (String subCommand: subCommands){
			//for loop to create filters and filterlist from subCommands list
			SequentialFilter filter = constructFilterFromSubCommand(subCommand);
			if(filter == null || !errorCheck) {
				return null;
			}
			filters.add(filter);

		}
		
		if (!(filters.getLast() instanceof Redirect)){//if there is no redirect, adds console filter to print to console
			filters.add(new Console());
		}
		
		 if(errorCheck) {//checks if errors has occurred, if not runs method to link filters
			linkFilters(filters);
		    } else {
		    	return null;
		    }
	    
	    
	    if(errorCheck) {//if no errors, return filters
	    	return filters;
	    } else {
	    	return null;
	    }
	}
	
	private static void findRedirects(String command) {
		if(command.contains(">")) {//finds the first instance of redirect
			int indexRedirect = 0;
			int counter = 0;
			boolean foundFirst = false;
			for(String x: subCommands) {
				if(x.contains(">") && !foundFirst) {
					indexRedirect = counter;
					foundFirst = true;
				}
				counter++;
			}
			//splits command with redirect into first command and then redirect command and inserts into list
			int index = subCommands.get(indexRedirect).indexOf(">");
			String sub1 = subCommands.get(indexRedirect).substring(0,index).trim();
			String sub2 = subCommands.get(indexRedirect).substring(index).trim();
			if(sub1.equals("")) {
			} else {
				subCommands.set(indexRedirect, sub1);
				subCommands.add(indexRedirect + 1, sub2);
			}
		}
	}

	private static SequentialFilter constructFilterFromSubCommand(String subCommand) {
		String[] tokens = subCommand.trim().split("\\s+");
		String headToken = tokens[0].toLowerCase();
		
		switch(headToken) {//switch statement that returns filters based on the head token
		case "head": 
			return new Head(tokens, subCommand);		
	    case "grep":
			return new Grep(tokens, subCommand);
		case ">":
			return new Redirect(tokens, subCommand);
		case "ls":
			return new Ls(tokens, subCommand);	
		case "pwd":
			return new Pwd(tokens, subCommand);	
		case "cd":
			return new Cd(tokens, subCommand);	
		case "wc":
			return new Wc(tokens, subCommand);	
		}
		System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
		errorCheck = false;//if head token doesnt match a case, then return null and error
		return null;
		
		// if something is not matched by a switch statement then its an error
		
	}

	private static void linkFilters(List<SequentialFilter> filters){
		SequentialFilter prevFilter = null;
		Iterator<SequentialFilter> filtersIterator = filters.iterator();
		int subCommmandIndex = 0;
		while (filtersIterator.hasNext() && errorCheck){
			SequentialFilter currFilter = filtersIterator.next();
			//go through every filter and link them up
			if((prevFilter instanceof Redirect || prevFilter instanceof Cd) && !(currFilter instanceof Console)) {
				errorCheck = false;//checks if redirect or cd is giving output, if so sends error and prints it
				if(prevFilter instanceof Cd) {
					System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subCommands.get(subCommmandIndex - 1)));
				} else {
					System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subCommands.get(subCommmandIndex)));
				}
				continue;
			}
			if (prevFilter != null){//checks if this is first filter, if not then do the below
				
				if (!((currFilter instanceof Grep) || !(currFilter instanceof Redirect) || !(currFilter instanceof Wc))) {
					//if not any, then we have a problem
					//we have an error, report error and exit out
					errorCheck = false;
					System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(subCommands.get(subCommmandIndex)));;
				}
				
				currFilter.setPrevFilter(prevFilter);
				prevFilter.setNextFilter(currFilter);
				prevFilter = currFilter;
				continue;
			} else {//if first filter, then do below
				if ((currFilter instanceof Grep) || (currFilter instanceof Redirect) || (currFilter instanceof Wc)) {
					//if any of these are first, we have a problem
					//do something to report error and exit
					errorCheck = false;
					System.out.print(Message.REQUIRES_INPUT.with_parameter(subCommands.get(subCommmandIndex)));
				}
				
				prevFilter = currFilter;
			}
			subCommmandIndex++;
		}
	}
}
