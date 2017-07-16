/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=359&page=show_problem&problem=2737
 * 11690 - Money Matters
 */
package union_find;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Q11690 {
	
	private static final int MAX = 1000;
	private static final String POSSIBLE_RESULT = "POSSIBLE";
	private static final String IMPOSSIBLE_RESULT = "IMPOSSIBLE";
	
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
	
	private StringTokenizer readData(StringTokenizer dataToken){
		if(dataToken != null && dataToken.hasMoreTokens()){
			return dataToken;
		}
		String input;
		while((input = ReadLn(MAX)) != null){
			if(input.isEmpty()){
				continue;
			}
			dataToken = new StringTokenizer(input.trim());
			return dataToken;
		}
		return null;
	}
	
	private int mPeopleCount;
	private int[] peopleFriendships;
	private int[] circleNetSum;
//	private int[] peopleBalance;
	
	private void init(int peopleCount){
		this.mPeopleCount = peopleCount;
		this.peopleFriendships = new int[this.mPeopleCount];
		this.circleNetSum = new int[this.mPeopleCount];
//		this.peopleBalance = new int[this.mPeopleCount];
		
		for(int i=this.mPeopleCount-1; i>=0; i--){
			this.peopleFriendships[i] = -1;
			this.circleNetSum[i] = 0;
//			this.peopleBalance[i] = 0;
		}
	}
	
	public int getGroup(int people){
		int friend = this.peopleFriendships[people];
		if(friend == -1){
			return people;
		}else{
			return this.peopleFriendships[people] = getGroup(friend);
		}
	}
	
	public void setBalance(int position, int balance){
		this.circleNetSum[position] = balance;
	}
	
	public void setFriendShip(int people1, int people2){
		int group1 = getGroup(people1);
		int group2  = getGroup(people2);
		if(group1 != group2){
			//group2 join to group1
			this.peopleFriendships[group2] = group1;
			this.circleNetSum[group1] += this.circleNetSum[group2];
		}
	}
	
	public void getResult(){
		
		TreeSet<Integer> groupIndex = new TreeSet<Integer>();
		for(int i=this.mPeopleCount-1; i>=0; i--){
			int representative = getGroup(i);
			if(!groupIndex.contains(representative)){
				
				if(this.circleNetSum[representative] != 0){
					System.out.println(IMPOSSIBLE_RESULT);
					return;
				}else{
					groupIndex.add(representative);
				}
			}
		}
		System.out.println(POSSIBLE_RESULT);
	}
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numCase;
		int friendCount;
		int friendShipCount;
		StringTokenizer idata = null;
		Q11690 solver = new Q11690();
		
		idata = solver.readData(idata);
		if(idata == null){
			return;
		}
		numCase = Integer.parseInt(idata.nextToken().trim());
		for(int i=numCase; i>0; i--){
			idata = solver.readData(idata);
			if(idata == null) return;
			friendCount = Integer.parseInt(idata.nextToken().trim());
			
			idata = solver.readData(idata);
			if(idata == null) return;
			friendShipCount = Integer.parseInt(idata.nextToken().trim());
			solver.init(friendCount);
			
			for(int j=0; j<friendCount; j++){
				idata = solver.readData(idata);
				if(idata == null) return;
				int balance = Integer.parseInt(idata.nextToken().trim());
				solver.setBalance(j, balance);
			}
			
			for(int j=0; j<friendShipCount; j++){
				idata = solver.readData(idata);
				if(idata == null) return;
				int people1 = Integer.parseInt(idata.nextToken().trim());
				idata = solver.readData(idata);
				if(idata == null) return;
				int people2 = Integer.parseInt(idata.nextToken().trim());
				solver.setFriendShip(people1, people2);
				
			}
			solver.getResult();
		}
		return;
	}

}
