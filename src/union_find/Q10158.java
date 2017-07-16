/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1099
 * 10158 - War
 */
package union_find;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.StringTokenizer;

public class Q10158 {
	
private final static int MAX = 1000;
	
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
	
	private int findGroup(int people){
		if(peopleGroup[people] < 0){
			return people;
		}
		return peopleGroup[people] = findGroup(peopleGroup[people]);
	}
	
	private boolean checkAreFriends(int x, int y){
		int xGroup = findGroup(x);
		int yGroup = findGroup(y);
		if(xGroup == yGroup){
			return true;
		}
		return false;
	}
	
	private boolean checkAreEnemies(int x, int y){
		int xGroup = findGroup(x);
		int yGroup = findGroup(y);
		int xAgainstGroup = findGroup(x+this.mNumPeople);
		int yAgainstGroup = findGroup(y+this.mNumPeople);
		if(xGroup == yAgainstGroup || yGroup == xAgainstGroup){
			return true;
		}
		return false;
	}
	
	interface IOperation{
		public void operate(int x, int y);
	}
	
	class SetFriendOperation implements IOperation{

		@Override
		public void operate(int x, int y) {
			// TODO Auto-generated method stub
			if(checkAreEnemies(x, y)){
				System.out.println(-1);
			}else{
				int xGroup = findGroup(x);
				int yGroup = findGroup(y);
				int xAgainstGroup = findGroup(x + mNumPeople);
				int yAgainstGroup = findGroup(y + mNumPeople);
				if(xGroup != yGroup){
					peopleGroup[xGroup] = yGroup;
				}
				if(xAgainstGroup != yAgainstGroup){
					peopleGroup[xAgainstGroup] = yAgainstGroup;
				}
				
			}
		}
		
	}
	
	class SetEnemyOperation implements IOperation{

		@Override
		public void operate(int x, int y) {
			// TODO Auto-generated method stub
			if(checkAreFriends(x, y)){
				System.out.println(-1);
			}else{
				int xGroup = findGroup(x);
				int yGroup = findGroup(y);
				int xAgainstGroup = findGroup(x + mNumPeople);
				int yAgainstGroup = findGroup(y + mNumPeople);
				if(xGroup != yAgainstGroup){
					peopleGroup[xGroup] = yAgainstGroup;
				}
				
				if(xAgainstGroup != yGroup){
					peopleGroup[xAgainstGroup] = yGroup;
				}
				
//				if(yGroup != xAgainstGroup){
//					peopleGroup[yGroup] = xAgainstGroup;
//				}
			}
		}
		
	}
	
	class AreFriendOperation implements IOperation{

		@Override
		public void operate(int x, int y) {
			// TODO Auto-generated method stub
			if(checkAreFriends(x, y)){
				System.out.println(1);
			}else{
				System.out.println(0);
			}
		}
	}
	
	class AreEnemyOperation implements IOperation{

		@Override
		public void operate(int x, int y) {
			// TODO Auto-generated method stub
			if(checkAreEnemies(x, y)){
				System.out.println(1);
			}else{
				System.out.println(0);
			}
			
		}
		
	}
	
	enum Command{
		TERMINATE(0), SET_FRIENDS(1), SET_ENEMIES(2), ARE_FRIENDS(3), ARE_ENEMIES(4);
		private int value;
		Command(int v){
			this.value = v;
		}
	}
	
	
	private StringTokenizer getInputData(StringTokenizer idata){
		String input;
		if(idata != null && idata.hasMoreTokens()){
			return idata;
		}
		while((input = Q10158.ReadLn(MAX)) != null){
			if(input.trim().length() == 0)
				continue;
			idata = new StringTokenizer(input.trim());
			break;
		}
		
		return idata;
	}
	
	private IOperation getOperation(Command command){
		
		IOperation operation = null;
		switch(command){
		
		case ARE_FRIENDS:
			if(areFriendOperation == null)
				areFriendOperation = new AreFriendOperation();
			operation = areFriendOperation;
			break;
		case ARE_ENEMIES:
			if(areEnemyOperation == null)
				areEnemyOperation = new AreEnemyOperation();
			operation = areEnemyOperation;
			break;
		case SET_FRIENDS:
			if(setFriendOperation == null)
				setFriendOperation = new SetFriendOperation();
			operation = setFriendOperation;
			break;
		case SET_ENEMIES:
			if(setEnemyOperation == null)
				setEnemyOperation = new SetEnemyOperation();
			operation = setEnemyOperation;
			break;
		
		}
		
		return operation;
	}
	
	private SetFriendOperation setFriendOperation;
	private SetEnemyOperation setEnemyOperation;
	private AreFriendOperation areFriendOperation;
	private AreEnemyOperation areEnemyOperation;
	
	private int mNumPeople;
	private int[] peopleGroup;
	
	public void setInitState(int numPeople){
		this.mNumPeople = numPeople;
		peopleGroup = new int[mNumPeople*2];
		for(int i=0; i<numPeople*2; i++){
			peopleGroup[i] = -1;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q10158 solver = new Q10158();
		String input;
		StringTokenizer idata = null;
		int numPeople;
		int commandValue = 0;
		Command command;
		IOperation operation;
		int x=0, y=0;
		
		idata = solver.getInputData(idata);
		numPeople = Integer.parseInt(idata.nextToken().trim());
		solver.setInitState(numPeople);
		
		while(true){
			idata = solver.getInputData(idata);
			if(idata != null){
				commandValue = Integer.parseInt(idata.nextToken());
			}
			
			idata = solver.getInputData(idata);
			if(idata != null){
				x = Integer.parseInt(idata.nextToken());
			}
			
			idata = solver.getInputData(idata);
			if(idata != null){
				y = Integer.parseInt(idata.nextToken());
			}
			
			command = Command.values()[commandValue];
			if(command == Command.TERMINATE){
//				System.out.println();
				return;
			}else{
				operation = solver.getOperation(command);
				operation.operate(x, y);
			}
		}
		
	}

}
