/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=12&page=show_problem&problem=999
 * 10058 - Jimmi's Riddles
 */
package regular_expression;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q10058 {
	
	private static final String TRUE_STATEMENT = "YES I WILL";
	private static final String FALSE_STATEMENT = "NO I WON'T";
	
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
	
	private static final String VERB = "\\b(?>hate|love|know|like)[s]?+\\b";
	private static final String NOUN = "\\b(?>tom|jerry|goofy|mickey|jimmy|dog|cat|mouse)\\b";
	private static final String ARTICLE = "\\b(?>a|the)\\b";
	
	private static final String ACTOR = "(?:"+ARTICLE+" )?"+NOUN + "\\Z";
	
	
	private final Pattern ACTOR_PATTERN = Pattern.compile(ACTOR);
	private final Pattern VERB_PATTERN = Pattern.compile(VERB);
	private Matcher actorMatcher;
	private Matcher verbMatcher;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Q10058 solver = new Q10058();
		String input;
		while((input = Q10058.ReadLn(MAX)) != null){
			solver.readInput(input);
		}
	}
	
	private void readInput(String input){
		boolean result = isStatement(input);
		if(result){
			System.out.println(TRUE_STATEMENT);
		}else{
			System.out.println(FALSE_STATEMENT);
		}
	}
	
	private boolean isStatement(String input){
		boolean result = false;
		String conjunction = ",";
		int conjuctionPos = input.lastIndexOf(conjunction);
		if(conjuctionPos == -1){
			result = isAction(input);
		}else{
			String actionPart = input.substring((conjuctionPos + conjunction.length())).trim();
			if(isAction(actionPart)){
				String statementPart = input.substring(0, conjuctionPos).trim();
				result = isStatement(statementPart);
			}
		}
		return result;
	}
	
	private boolean isAction(String input){
		boolean result = false;
		verbMatcher = getVerbMatcher(input);
		if(verbMatcher.find()){
			String activeList1 = input.substring(0, verbMatcher.start()).trim();
			String activeList2 = input.substring(verbMatcher.end()).trim();
			if(isActiveList(activeList1) && isActiveList(activeList2)){
				result = true;
			}
		}
		return result;
	}
	
	private Matcher getVerbMatcher(String input){
		if(verbMatcher == null){
			verbMatcher = VERB_PATTERN.matcher(input);
		}else{
			verbMatcher.reset(input);
		}
		
		return verbMatcher;
	}
	
	private boolean isActiveList(String inputString){
		String conjunciton = " and ";
		boolean result = false;
		boolean isActor = isActor(inputString);
		if(isActor){
			result = true;
		}else if(actorMatcher.find()){
			int startPoint = actorMatcher.start();
			int activeListEnd = startPoint - conjunciton.length();
			if(activeListEnd > 0){
				if(inputString.substring(activeListEnd, startPoint).equals(conjunciton)){
					result = isActiveList(inputString.substring(0, activeListEnd));
				}	
			}
			
		}
		return result;
	}
	
	private boolean isActor(String inputString){
		actorMatcher = getActorMatcher(inputString);
		return actorMatcher.matches();
	}
	
	private Matcher getActorMatcher(String input){
		if(actorMatcher == null){
			actorMatcher = ACTOR_PATTERN.matcher(input);
		}else{
			actorMatcher.reset(input);
		}
		
		return actorMatcher;
	}

}
