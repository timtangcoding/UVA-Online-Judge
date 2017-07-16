/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=742&page=show_problem&problem=320
 * 384 - Slurpys
 */
package regular_expression;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q384 {
	
	private static final int MAX = 100;
	
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
	
	private static final String SLUMP = "[DE][F]+G\\Z";
	private static final Pattern SLUMP_PATTERN = Pattern.compile(SLUMP);
	private static final String SLUMP_PREFIX = "[DE][F]+\\Z";
	private static final String GROUP_SLUMP = "GROUPSLUMP";
	private static final String GROUP_SLIMP = "GROUPSLIMP";
	private static final Pattern SLUMP_PREFIX_PATTERN = Pattern.compile(SLUMP_PREFIX);
	private static final String SLIMP = "\\bAH\\Z|\\bAB(?<" + GROUP_SLIMP +">.+)C\\Z|\\bA(?<" + GROUP_SLUMP +">.+)C\\Z";
	private static final String BOUNDARY = "\\B[HC]|[ED]\\B";
	private static final Pattern BOUNDARY_PATTERN  = Pattern.compile(BOUNDARY);
	
	private static final Pattern SLIMP_PATTERN = Pattern.compile(SLIMP);
	private Matcher slumpPrefixMatcher;
	private Matcher slumpMatcher;
	private Matcher slimpMatcher;
	private Matcher boundaryMatcher;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q384 solver = new Q384();
		String input = Q384.ReadLn(MAX);
		
		int testCases = Integer.parseInt(input.trim());
		if(testCases > 0){
			System.out.println("SLURPYS OUTPUT");
		}
		for(int i=0; i<testCases; i++){
			input = Q384.ReadLn(MAX); 
			solver.readData(input);
		}
		
		System.out.println("END OF OUTPUT");
		return;
		
	}
	
	public void readData(String inputString){
		if(isSlurpy(inputString)){
			System.out.println("YES");
		}else{
			System.out.println("NO");
		}
	}
	
	private boolean isSlurpy(String inputString){
		boolean result = false;
		boundaryMatcher = getBoundaryMatcher(inputString);
		while(boundaryMatcher.find()){
			int boundaryPoint = boundaryMatcher.start() + 1;
			String slimpPart = inputString.substring(0, boundaryPoint);
			String slumpPart = inputString.substring(boundaryPoint);
			boolean slimpResult = isSlimp(slimpPart);
			boolean slumpResult = isSlump(slumpPart);
			if(slimpResult && slumpResult){
				return true;
			}
		}
		
		return result;
	}
	
	private Matcher getBoundaryMatcher(String inputString){
		if(boundaryMatcher == null){
			boundaryMatcher = BOUNDARY_PATTERN.matcher(inputString);
		}else{
			boundaryMatcher.reset(inputString);
		}
		
		return boundaryMatcher;
	}
	
	private boolean isSlump(String inputString){
		boolean result = false;
		slumpMatcher = getSlumpMatcher(inputString);
		if(slumpMatcher.matches()){
			result = true;
		}else{
			if(slumpMatcher.find()){
				result = isSlumpPrefix(inputString.substring(0, slumpMatcher.start()));
			}
		}
		return result;
	}
	
	private Matcher getSlumpMatcher(String inputString){
		if(slumpMatcher == null){
			slumpMatcher = SLUMP_PATTERN.matcher(inputString);
		}else{
			slumpMatcher.reset(inputString);
		}
		return slumpMatcher;
	}
	
	private boolean isSlumpPrefix(String inputString){
		boolean result = false;
		slumpPrefixMatcher = getSlumpPrefixMatcher(inputString);
		if(slumpPrefixMatcher.matches()){
			result = true;
		}else{
			if(slumpPrefixMatcher.find()){
				result = isSlumpPrefix(inputString.substring(0, slumpPrefixMatcher.start()));	
			}
		}
		return result;
	}
	
	private Matcher getSlumpPrefixMatcher(String inputString){
		if(slumpPrefixMatcher == null){
			slumpPrefixMatcher = SLUMP_PREFIX_PATTERN.matcher(inputString);
		}else{
			slumpPrefixMatcher.reset(inputString);
		}
		
		return slumpPrefixMatcher;
	}
	
	public boolean isSlimp(String inputString){
		boolean result = false;
		slimpMatcher = getSlimpMatcher(inputString);
		if(slimpMatcher.matches() && slimpMatcher.end() - slimpMatcher.start() == 2){
			result = true;
		}else{
			slimpMatcher.reset();
			if(slimpMatcher.find()){
				String slimpGroup = slimpMatcher.group(GROUP_SLIMP);
				String slumpGroup = slimpMatcher.group(GROUP_SLUMP);
				if(slimpGroup != null){
					result = isSlimp(slimpGroup);
				}else if(slumpGroup != null){
					result = isSlump(slumpGroup);
				}
			}
		}
		
		return result;
	}
	
	private Matcher getSlimpMatcher(String inputString){
		if(slimpMatcher == null){
			slimpMatcher = SLIMP_PATTERN.matcher(inputString);
		}else{
			slimpMatcher.reset(inputString);
		}
		return slimpMatcher;
	}

}
