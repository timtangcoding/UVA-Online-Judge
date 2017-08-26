/*
 * https://uva.onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&problem=999
 * 10058 - Jimmi's Riddles
 */
package regular_expression;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q10058_2 {
	
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
	private static final String VERB = "\\b(:?hate|love|know|like)[s]*\\b";
	private static final String NOUN = "\\b(?:tom|jerry|goofy|mickey|jimmy|dog|cat|mouse)\\b";
	private static final String ARTICLE = "\\b(?:a|the)\\b";
	private static final String SPACE = "\\s+";
	private static final String CORRECT = "YES I WILL";
	private static final String INCORRECT = "NO I WON'T";
	private static final String CONJUNCTION = " and ";
	private static final Pattern LAST_CONJUNCTION = Pattern.compile("\\band\\b\\w+\\Z");
	private static final Pattern PATTERN_VERB = Pattern.compile(VERB);
	private static final String STATEMENT_CONJUCNTION = "\\s?,\\s?";
	private static final Pattern PATTERN_STATEMENT_CONJUNCTION = Pattern.compile(STATEMENT_CONJUCNTION);
	private static final Pattern LAST_STATEMENT_CONJUNCTION = Pattern.compile("\\s?,\\s?[\\w\\s]+\\Z");
			
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q10058_2 solver = new Q10058_2();
		String input = null;
		while((input = Q10058_2.ReadLn(MAX)) != null){
			solver.getAnswer(input);
		}
		return;
	}
	
	public void getAnswer(String input){
		if(input == null) 
			return;
		
		if(isStatement(input)){
			System.out.println(CORRECT);
		}else{
			System.out.println(INCORRECT);
		}
	}
	
	public boolean isStatement(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			Matcher matcher = LAST_STATEMENT_CONJUNCTION.matcher(input);
			if(matcher.find()){
				String frontPart = input.substring(0, matcher.start());
				String endPart = input.substring(matcher.start());
				
//				System.out.println("the end part is: >>>>>>>>>>>>>>>" + endPart);
				Matcher conjunctionMatcher = PATTERN_STATEMENT_CONJUNCTION.matcher(endPart);
				if(conjunctionMatcher.find()){
					endPart = endPart.substring(conjunctionMatcher.end());
				}
				 
//				System.out.println("the front: "+frontPart);
//				System.out.println("end front: "+endPart);
				result = isAction(endPart) && isStatement(frontPart);
			}else{
				result = isAction(input);
			}
		}
		return result;
	}
	
	public boolean isAction(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			Matcher verbMatcher = PATTERN_VERB.matcher(input);
			if(verbMatcher.find()){
				String part1 = input.substring(0, verbMatcher.start());
				String part2 = input.substring(verbMatcher.end());
				result = isActiveList(part1) && isActiveList(part2);
			}else{
				result = false;
			}
		}
		return result;
		
	}
	
	
	public boolean isActiveList(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			if(isActor(input)){
				return true;
			}else{
				Matcher matcher = LAST_CONJUNCTION.matcher(input);
				if(matcher.find()){
					String trailingPart = input.substring(matcher.start() + CONJUNCTION.length());
					if(isActor(trailingPart)){
						String remaining = input.substring(0, matcher.start());
						return isActiveList(remaining);
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return result;
	}
	
	private boolean isVerb(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			result = input.matches(VERB);
		}
		return result;
	}
	
	private boolean isNoun(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			result = input.matches(NOUN);
		}
		return result;
	}
	
	private boolean isArticle(String input){
		
		boolean result = false;
		if(input != null){
			input = input.trim();
			result = input.matches(ARTICLE);
			
		}
		return result;
	}
	
	private boolean isActor(String input){
		boolean result = false;
		if(input != null){
			input = input.trim();
			String[] words = input.split(SPACE);
			if(words.length == 1){
				result = isNoun(words[0]);
			}else if(words.length > 1){
				result = isArticle(words[0]) && isNoun(words[1]);
			}
		}
		return result;
	}
	


	
	

}
