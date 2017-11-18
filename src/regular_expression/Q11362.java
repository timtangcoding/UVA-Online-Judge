/*
 *  (accpeted)
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=748&page=show_problem&problem=2347
 * 11362 - Phone List
 */
package regular_expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Q11362 {
	
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
	 
	
	private static final int MAX = 1000;
	private List<String> phoneNumbers = new ArrayList<String>();
	private static final String CONSISTENT = "YES";
	private static final String NOT_CONSISTENT = "NO";
	
	public void clearCase(){
		this.phoneNumbers.clear();
	}
	
	private void addPhoneNumber(String phoneNumber){
		this.phoneNumbers.add(phoneNumber);
	}
	
	private void sortByLength(){
		Collections.sort(this.phoneNumbers, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				if(o1.length() > o2.length()){
					return 1;
				}else if(o1.length() < o2.length()){
					return -1;
				}
				return 0;
			}
			
		});
	}
	
	private boolean checkIfThereIsPrefix(){
		sortByLength();
		int length = this.phoneNumbers.size();
		for(int i=0; i<length; i++){
			String phoneNumber = this.phoneNumbers.get(i);
			for(int j=i+1; j<length; j++){
				String otherPhone = this.phoneNumbers.get(j);
				if(otherPhone.startsWith(phoneNumber)){
//					System.out.print("other: "+otherPhone + ", this: "+phoneNumber);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void getResult(){
		if(checkIfThereIsPrefix()){
			System.out.println(NOT_CONSISTENT);
		}else{
			System.out.println(CONSISTENT);
		}
	}
	 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int testCases = 0;
		int inputCount = 0;
		String input;
		String phoneNumber;
		Q11362 solver = new Q11362();
		
		while((input = ReadLn(MAX)) != null){
			testCases = Integer.parseInt(input.trim());
			for(int i=0; i<testCases; i++){
				solver.clearCase();
				while((input = ReadLn(MAX)) != null){
					break;
				}
				inputCount = Integer.parseInt(input.trim());
				for(int j=0; j<inputCount; j++){
					while((input = ReadLn(MAX)) != null){
						break;
					}
					
					solver.addPhoneNumber(input.trim());
				}
				solver.getResult();
				
			}
		}
		
		
		

	}

}
