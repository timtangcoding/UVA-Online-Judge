/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=37
 * 101 - The Blocks Problem
 */
package stack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

class Q101 {
	
	static class Command{
		enum Action{
			MOVE(1), PILE(2);
			private int value;
			Action(int v){
				this.value = v;
			}
		}
		
		enum Operation{
			ONTO(1), OVER(2);
			private int value;
			Operation(int v){
				this.value = v;
			}
		}

		private int src, dst;
		Operation operation;
		Action action;
		
		public void parseCommand(String command){
			String[] commandParts = command.split("\\s+");
			if(commandParts[0].equalsIgnoreCase("pile")){
				this.action = Action.PILE;
			}else if(commandParts[0].equalsIgnoreCase("move")){
				this.action = Action.MOVE;
			}
			
			if(commandParts[2].equalsIgnoreCase("onto")){
				this.operation = Operation.ONTO;
			}else if(commandParts[2].equalsIgnoreCase("over")){
				this.operation = Operation.OVER;
			}
			
			this.src = Integer.parseInt(commandParts[1].trim());
			this.dst = Integer.parseInt(commandParts[3].trim());
			
		}
		
		public int getSrc() {
			return src;
		}
		public void setSrc(int src) {
			this.src = src;
		}
		public int getDst() {
			return dst;
		}
		public void setDst(int dst) {
			this.dst = dst;
		}
		public Action getAction() {
			return action;
		}
		public void setAction(Action action) {
			this.action = action;
		}
		public Operation getOperation() {
			return operation;
		}
		public void setOperation(Operation operation) {
			this.operation = operation;
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

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }
	
	private int boxNum;
	private ArrayList<Stack> blocks;
	private int[] stackPos;
	
	public void setBoxNum(int boxNum){
		this.boxNum = boxNum;
		this.blocks = new ArrayList<Stack>(boxNum);
		this.stackPos = new int[boxNum];
		for(int i=0; i<boxNum; i++){
			this.stackPos[i] = i;
			Stack<Integer> blockStack = new Stack<Integer>();
			blockStack.add(i);
			this.blocks.add(blockStack);
		}
	}
	
	public void takeAction(Command actionCommand){
		List<Integer> movingBlocks;
		int srcBox = actionCommand.getSrc();
		int destBox = actionCommand.getDst();
		if(stackPos[srcBox] == stackPos[destBox]){
			return;
		}
		Stack<Integer> srcStack = blocks.get(stackPos[srcBox]);
		Stack<Integer> destStack = blocks.get(stackPos[destBox]);
		
		switch(actionCommand.getAction()){
		case MOVE:
			movingBlocks = srcStack.subList(srcStack.indexOf(srcBox), srcStack.size());
//			srcStack.removeElement(srcBox);
			for(int block : movingBlocks){
				if(block != srcBox){
					stackPos[block] = block;
					blocks.get(block).push(block);
				}
			}
			movingBlocks.clear();
			
			stackPos[srcBox] = stackPos[destBox];
			switch(actionCommand.getOperation()){
			case OVER:
				
				destStack.push(srcBox);
				break;
			case ONTO:
				List<Integer> topBlocks = destStack.subList(destStack.indexOf(destBox)+1, destStack.size());
				for(int block : topBlocks){
					stackPos[block] = block;
					blocks.get(block).push(block);
				}
				topBlocks.clear();
//				int targetPos = destStack.indexOf(destBox)+1;
//				destStack.add(targetPos, srcBox);
				destStack.push(srcBox);
				
				break;
			}
			break;
		case PILE:
			
			
			switch(actionCommand.getOperation()){
			case OVER:
				movingBlocks = srcStack.subList(srcStack.indexOf(srcBox), srcStack.size());
				for(int block : movingBlocks){
					destStack.push(block);
				}
				for(int block : movingBlocks){
					stackPos[block] = stackPos[destBox];
				}
				movingBlocks.clear();
				break;
			case ONTO:
				List<Integer> topBlocks = destStack.subList(destStack.indexOf(destBox)+1, destStack.size());
				for(int block : topBlocks){
					stackPos[block] = block;
					blocks.get(block).push(block);
				}
				topBlocks.clear();
				movingBlocks = srcStack.subList(srcStack.indexOf(srcBox), srcStack.size());
//				for(int i=movingBlocks.size()-1; i>=0; i--){
				for(int i=0; i<movingBlocks.size(); i++){
					int block = movingBlocks.get(i);
					destStack.push(block);
				}
				for(int block : movingBlocks){
					stackPos[block] = stackPos[destBox];
				}
				movingBlocks.clear();
				
				break;
			}
			
			
			break;
		}
	}
	
	public void logStatus(){
		for(int i=0; i<boxNum; i++){
			System.out.print(i+":");
			Stack<Integer> targetStatck = this.blocks.get(i);
			int lastIndex = targetStatck.size()-1;
			for(int j=0; j<=lastIndex; j++){
				System.out.print(" " + targetStatck.get(j));
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = null;
		StringTokenizer idata;
		int boxNum = 0;
		Q101 solver = new Q101();
		String command;
		
		Command actionCommand = new Command();
		
		while((input = Q101.ReadLn(255)) != null){
			idata = new StringTokenizer(input);
			solver.setBoxNum(Integer.parseInt(idata.nextToken().trim()));
			while((command = Q101.ReadLn(255)) != null){
				if(command.trim().equalsIgnoreCase("quit")){
					break;
				}
				actionCommand.parseCommand(command);
				solver.takeAction(actionCommand);
//				System.out.println(command);
//				solver.logStatus();
//				System.out.println();
			}
			solver.logStatus();
			
		}

	}

}
