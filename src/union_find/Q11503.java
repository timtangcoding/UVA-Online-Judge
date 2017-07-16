/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=359&page=show_problem&problem=2498
 * 11503 - Virtual Friends
 */
package union_find;

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Q11503 {
	
	private HashMap<String, String> networks = new HashMap<String, String>();
	private HashMap<String, Integer> friendsCount = new HashMap<>();
	private static final int MAX = 1000;
	
	
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

	private StringTokenizer getData(StringTokenizer idata){
		if(idata != null && idata.hasMoreTokens()){
			return idata;
		}
		String input;
		while((input = ReadLn(MAX)) != null){
			idata = new StringTokenizer(input.trim());
			break;
		}
		
		return idata;
		
	}
	
	private String getNetworkGroup(String personName){
		String friend = networks.get(personName);
//		System.out.println(personName + " >>> "+friend); 
		
		if(friend == null){
			return personName;
		}
		else{
			String representative = getNetworkGroup(friend);
			networks.put(personName, representative);
			
			return representative;
		}
	}
	
	private void addFriend(String person1, String person2){
		String group1 = getNetworkGroup(person1);
		String group2 = getNetworkGroup(person2);
		Integer friendsCount1 = friendsCount.get(group1);
		if(friendsCount1 == null){
			friendsCount1 = 1;
			friendsCount.put(group1, friendsCount1);
		}
		Integer friendsCount2 = friendsCount.get(group2);
		if(friendsCount2 == null){
			friendsCount2 = 1;
			friendsCount.put(group2, friendsCount2);
		}
		
		if(!person1.equalsIgnoreCase(person2)){
			if(!group1.equalsIgnoreCase(group2)){
				
				networks.put(group1, group2);
				friendsCount2 += friendsCount1;
				friendsCount.put(group2, friendsCount2);
			}
		}
		
		System.out.println(friendsCount2);
		
		
	}
	
	private void reset(){
		this.networks.clear();
		this.friendsCount.clear();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringTokenizer idata = null;
		Q11503 solver = new Q11503();
		int caseNumber; 
		int connectionNumber;
		idata =  solver.getData(idata);
		if(idata == null){
			return;
		}
		caseNumber = Integer.parseInt(idata.nextToken().trim());
		for(int i=0; i<caseNumber; i++){
			connectionNumber = 0;
			solver.reset();
			idata = solver.getData(idata);
			if(idata == null){
				return;
			}
			connectionNumber = Integer.parseInt(idata.nextToken().trim());
			for(int j=0; j<connectionNumber; j++){
				idata = solver.getData(idata);
				if(idata == null){
					return;
				}
				String person1 = idata.nextToken();
				String person2 = idata.nextToken();
				solver.addFriend(person1, person2);
			}
		}

	}

}
