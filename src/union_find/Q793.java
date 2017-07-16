/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=359&page=show_problem&problem=734
 * 793 - Network Connections
 */
package union_find;

import java.io.IOException;
import java.util.StringTokenizer;

public class Q793 {
	private final static int MAX = 1000;
	
	private int computerCount;
	private int[] computerConnections;
	private int successCount;
	private int failureCount;
	
	private static int blanklineCount = 0;
	private static boolean last = false;
	
	enum Command{
		C("c"), Q("q");
		private String value;
		
		Command(String strValue){
			this.value = strValue;
		}
		
		public static Command fromString(String strValue){
			for(Command command : Command.values()){
				if(strValue.equalsIgnoreCase(command.value)){
					return command;
				}
			}
			
			return null;
		}
	}
	
	static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        String line = "";

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (line);  // eof
        return (new String (lin, 0, lg));
    }
	
	private int getComputerGroup(int computerNode){
		if(this.computerConnections[computerNode] < 0){
			return computerNode;
		}
		
		return this.computerConnections[computerNode] = getComputerGroup(this.computerConnections[computerNode]);
	}
	
	private boolean checkIfConnected(int node1, int node2){
		int group1 = getComputerGroup(node1);
		int group2 = getComputerGroup(node2);
		return (group1 == group2);
	}
	
	private void connectComputer(int node1, int node2){
		int group1 = getComputerGroup(node1);
		int group2 = getComputerGroup(node2);
		if(group1 != group2){
			this.computerConnections[group1] = group2;
		}
	}
	
	private StringTokenizer getInputData(StringTokenizer idata){
		String input;
		if(idata != null && idata.hasMoreTokens()){
			return idata;
		}
		while((input = Q793.ReadLn(MAX)) != null){
			if(input.trim().length() == 0){
				blanklineCount++;
				if(last)
					break;
				continue;
			}
				
			idata = new StringTokenizer(input.trim());
//			System.out.println(input);
			break;
		}
		
		return idata;
	}
	
	public void setInitialStates(int numComputer){
		this.computerCount = numComputer+1;
		this.successCount = 0;
		this.failureCount = 0;
		this.computerConnections = new int[this.computerCount];
		for(int i=0; i<this.computerCount; i++){
			this.computerConnections[i] = -1;
		}
	}
	
	public void solve(Command command, int node1, int node2){
		switch (command) {
		case C:
			connectComputer(node1, node2);
			break;
			
		case Q:
			if(checkIfConnected(node1, node2)){
				this.successCount++;
			}else{
				this.failureCount++;
			}
			break;

		default:
			break;
		}
	}
	
	public void getResults(){
		System.out.println(this.successCount + "," + this.failureCount);
		if(!last)
			System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringTokenizer idata = null;
		int caseNum;
		Q793 solver = new Q793();
		Command command = null;
		int node1 = 0; 
		int node2 = 0;
		
		boolean isFirst = true;
		
		idata = solver.getInputData(idata);
		caseNum = Integer.parseInt(idata.nextToken().trim());
		for(int i=0; i<caseNum; i++){
			blanklineCount = 0;
			isFirst = true;
			while(true){
				if(isFirst){
					idata = solver.getInputData(idata);
					int computerCount = Integer.parseInt(idata.nextToken().trim());
					solver.setInitialStates(computerCount);
					isFirst = false;
					blanklineCount = 0;
					last = (i == caseNum-1);
				}
				
				idata = solver.getInputData(idata);
				if(blanklineCount > 0){
					solver.getResults();
					break;
				}
				command = Command.fromString(idata.nextToken().trim());
				
				idata = solver.getInputData(idata);
				if(blanklineCount > 0){
					solver.getResults();
					break;
				}
				node1 = Integer.parseInt(idata.nextToken().trim());
				
				idata = solver.getInputData(idata);
				if(blanklineCount > 0){
					solver.getResults();
					break;
				}
				node2 = Integer.parseInt(idata.nextToken().trim());
				
				solver.solve(command, node1, node2);
				
			}
			
		}
		
		return;
		
	}
	

}
