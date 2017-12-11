package cs131.pa1.filter.sequential;


public class Console extends SequentialFilter{
	
	public Console(){
		
	}
	public void process(){//prints input to unix console
		while (!input.isEmpty()){
			String line = input.poll();
			System.out.println(line);
		}	
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
