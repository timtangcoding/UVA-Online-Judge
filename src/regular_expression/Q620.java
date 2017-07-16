/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=742&page=show_problem&problem=561
 * 620 - Cellular Structure
 */
package regular_expression;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q620 {
	
	private static final int MAX  = 1000;
	
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
	
	private static final String SIMPLE = "\\AA\\Z";
	private static final String FULLY_GROWN = "\\A(.+)AB\\Z";
	private static final String MUTAGENIC = "\\AB(.+)A\\Z";
	
	private static final Pattern SIMPLE_PATTERN = Pattern.compile(SIMPLE);
	private static final Pattern FULLY_GROWN_PATTERN = Pattern.compile(FULLY_GROWN);
	private static final Pattern MUTAGENIC_PATTERN = Pattern.compile(MUTAGENIC);
	
	private Matcher simpleMatcher;
	private Matcher fullyGrownMatcher;
	private Matcher mutangenicMatcher;
	
	private static final String SIMPLE_RESULT = "SIMPLE";
	private static final String FULLY_GROWN_RESULT = "FULLY-GROWN";
	private static final String MUTAGENIC_RESULT = "MUTAGENIC";
	private static final String MUTANT_RESULT = "MUTANT";
	
	private Matcher getSimpleMatcher(String input){
		if(simpleMatcher == null){
			simpleMatcher = SIMPLE_PATTERN.matcher(input);
		}else{
			simpleMatcher.reset(input);
		}
		return simpleMatcher;
	}
	
	private Matcher getFullyGrownMatcher(String input){
		if(fullyGrownMatcher == null){
			fullyGrownMatcher = FULLY_GROWN_PATTERN.matcher(input);
		}else{
			fullyGrownMatcher.reset(input);
		}
		return fullyGrownMatcher;
	}
	
	private Matcher getMutangenicMatcher(String input){
		if(mutangenicMatcher == null){
			mutangenicMatcher = MUTAGENIC_PATTERN.matcher(input);
		}else{
			mutangenicMatcher.reset(input);
		}
		return mutangenicMatcher;
	}
	
	private boolean isSimple(String input){
		simpleMatcher = getSimpleMatcher(input);
		return simpleMatcher.matches();
	}
	
	private boolean isFullyGrown(String input){
		boolean result = false;
		fullyGrownMatcher = getFullyGrownMatcher(input);
		if(fullyGrownMatcher.matches()){
			result = isKnown(fullyGrownMatcher.group(1));
		}
		return result;
	}
	
	private boolean isMutangenic(String input){
		boolean result = false;
		mutangenicMatcher = getMutangenicMatcher(input);
		if(mutangenicMatcher.matches()){
			result = isKnown(mutangenicMatcher.group(1));
		}
		
		return result;
	}
	
	private boolean isKnown(String input){
		boolean result = false;
		if(isSimple(input) || isFullyGrown(input) || isMutangenic(input)){
			result = true;
		}
		return result;
	}
	
	public void inputData(String input){
		if(isSimple(input)){
			System.out.println(SIMPLE_RESULT);
		}else if(isFullyGrown(input)){
			System.out.println(FULLY_GROWN_RESULT);
		}else if(isMutangenic(input)){
			System.out.println(MUTAGENIC_RESULT);
		}else{
			System.out.println(MUTANT_RESULT);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q620 solver = new Q620();
		int caseCount = Integer.parseInt(Q620.ReadLn(MAX).trim());
		
		for(int i=0; i<caseCount; i++){
			solver.inputData(Q620.ReadLn(MAX).trim());
		}
		return;
	}

}
