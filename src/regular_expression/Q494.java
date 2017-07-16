/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=743&page=show_problem&problem=435
 * (494 - Kindergarten Counting Game)
 */
package regular_expression;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q494 {
	
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
	
	private static final String WORD = "[a-zA-Z]+";
	private static final int MAX = 1000;
	private Pattern pattern = Pattern.compile(WORD);
	private Matcher matcher;
	
	private Matcher getMatcher(String inputString){
		if(matcher == null){
			matcher = pattern.matcher(inputString);
		}else{
			matcher.reset(inputString);
		}
		return matcher;
	}
	
	public int countWord(String inputString){
		matcher = getMatcher(inputString);
		int counter = 0;
		while(matcher.find()){
			counter++;
		}
		
		return counter;
	}
	
	public static void main(String[] args) {
		
		String inputString;
		Q494 solver = new Q494();
		while((inputString = Q494.ReadLn(MAX)) != null){
			System.out.println(solver.countWord(inputString));
		}
	}

}
