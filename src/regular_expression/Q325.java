/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=743&page=show_problem&problem=261
 * 325 - Identifying Legal Pascal Real Constants
 */
package regular_expression;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q325 {
	
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
	private static final String EOF = "\\A\\s*\\*\\s*\\Z";
	private static final String VALID_CONSTANTS = "\\A\\s*[+-]?\\d+((?>\\.\\d++)|(?>[eE][+-]?\\d++)|(?>\\.\\d++)(?>[eE][+-]?\\d++))\\s*\\Z";
	private Pattern validConstantsPattern = Pattern.compile(VALID_CONSTANTS);
	private Matcher matcher;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input;
		Q325 solver = new Q325();
		while((input = Q325.ReadLn(MAX)) != null){
			if(solver.isEOF(input)){
				return;
			}
			
			solver.checkValidConstants(input);
		}
	}
	
	public boolean isEOF(String inputString){
		return inputString.matches(EOF);
	}
	
	public void checkValidConstants(String inputString){
		inputString = inputString.trim();
		if(isValid(inputString)){
			System.out.println(inputString + " is legal.");
		}else{
			System.out.println(inputString + " is illegal.");
		}
	}
	
	private boolean isValid(String inputString){
		
		return getMatcher(inputString).matches();
	}
	
	private Matcher getMatcher(String inputString){
		if(matcher == null){
			matcher = validConstantsPattern.matcher(inputString);
		}else{
			matcher.reset(inputString);
		}
		
		return matcher;
	}

	
}
