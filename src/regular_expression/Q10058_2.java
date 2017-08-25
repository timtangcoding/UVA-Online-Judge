/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=999
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
	private static final String VERB = "(:?hate|love|know|like)[s]?";
	private static final String NOUN = "tom|jerry|goofy|mickey|jimmy|dog|cat|mouse";
	private static final String ARTICLE = "a|the";
	private static final String SPACE = "\\s+";
	
//	private static final Pattern PATTERN_VREB = Pattern.compile(VERB);
//	private static final Pattern PATTERN_NOUN = Pattern.compile(NOUN);
//	private static final Pattern PATTERN_ARTICLE = Pattern.compile(ARTICLE);
	
//	private Matcher matcherVerb = null;
//	private Matcher matcherNoun = null;
//	private Matcher matcherArticle = null;
	
	private boolean isVerb(String input){
		boolean result = false;
		if(input != null){
			result = input.trim().matches(VERB);
		}
		return result;
	}
	
	private boolean isNoun(String input){
		boolean result = false;
		if(input != null){
			result = input.trim().matches(NOUN);
		}
		return result;
	}
	
	private boolean isArticle(String input){
		boolean result = false;
		if(input != null){
			result = input.trim().matches(ARTICLE);
			
		}
		return result;
	}
	
	private boolean isActor(String input){
		boolean result = false;
		if(input != null){
			String[] words = input.split(SPACE);
			if(words.length == 1){
				result = isNoun(words[0]);
			}else if(words.length > 1){
				result = isArticle(words[0]) && isNoun(words[1]);
			}
			
		}
		return result;
	}
	
	
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q10058_2 solver = new Q10058_2();
		String input = null;
		while((input = Q10058_2.ReadLn(MAX)) != null){
			System.out.println(input);
		}

	}
	

}
