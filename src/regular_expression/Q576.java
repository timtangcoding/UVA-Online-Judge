/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=743&problem=517&mosmsg=Submission+received+with+ID+18756627
 * 576 - Haiku Review
 */
package regular_expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q576 {
	
	private static final int MAX = 250;
	
	private static final String LINE_SEPARATOR = "/";
	private static final String EOF = "e/o/i";
	private static final String SYLLABLES = "[aeiouy]+";
	private static final Pattern SYLLABLE_PATTERN = Pattern.compile(SYLLABLES);
	
	private static final String WORD = "[a-z]+";
	private static final Pattern WORD_PATTERN = Pattern.compile(WORD);
	
	private final int[] NUM_SYLLABLE_REQUIRED = new int[]{5, 7, 5};
	private static final int NUM_SENTENCE = 3;
	private static final int MIN_WORD_COUNT = 1;
	private static final int SUCCESS = 9;
	
	private List<String> sentences;
	private StringTokenizer tokenizer;
	private Matcher syllableMatcher;
	private Matcher wordMatcher;
	private int[] syllableCounter = new int[NUM_SENTENCE];
	
	public Q576() {
		sentences = new ArrayList<String>();
	}
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input;
		Q576 solver = new Q576();
		while((input = Q576.ReadLn(MAX)) != null){
			if(input.matches(EOF)){
				return;
			}
			solver.readInput(input);
			solver.checkResult();
		}
	}

	public void readInput(String inputString){
		reset();
		separateLines(inputString);
	}
	private void reset(){
		sentences.clear();
		for(int i=syllableCounter.length-1; i>=0; i--){
			syllableCounter[i] = 0;
		}
	}
	
	private void separateLines(String inputString){
		tokenizer = new StringTokenizer(inputString, LINE_SEPARATOR);
		while(tokenizer.hasMoreTokens()){
			sentences.add(tokenizer.nextToken());
		}
	}
	
	private void checkResult(){
		if(checkWordCount()){
			getSyllableCount();
			getResult();
		}
	}
	
	private boolean checkWordCount(){
		for(int i=0; i<NUM_SENTENCE; i++){
			int counter = 0;
			wordMatcher = initWordMatcher(sentences.get(i));
			while(wordMatcher.find()){
				++counter;
				if(counter >= MIN_WORD_COUNT){
					break;
				}
			}
			if(counter < MIN_WORD_COUNT){
				return false;
			}
		}
		return true; 
	}
	private Matcher initWordMatcher(String sentence){
		if(wordMatcher != null){
			wordMatcher.reset(sentence);
		}else{
			wordMatcher = WORD_PATTERN.matcher(sentence);
		}
		return wordMatcher;
	}
	
	private void getSyllableCount(){
		
		for(int i=0; i<NUM_SENTENCE; i++){
			int counter = 0;
			syllableMatcher = initSyllableMatcher(sentences.get(i));
			while(syllableMatcher.find()){
				++counter;
			}
			syllableCounter[i] = counter;
		}
	}
	private Matcher initSyllableMatcher(String sentence){
		if(syllableMatcher != null){
			syllableMatcher.reset(sentence);
		}else{
			syllableMatcher = SYLLABLE_PATTERN.matcher(sentence);
		}
		
		return syllableMatcher;
	}
	
	private void getResult(){
		for(int i = 0; i<syllableCounter.length; i++){
			if(syllableCounter[i] != NUM_SYLLABLE_REQUIRED[i]){
				printResult(i);
				return;
			}
		}
		printResult(SUCCESS);
	}
	
	private void printResult(int i){
		if(i == SUCCESS){
			System.out.println("Y");
		}else{
			System.out.println((i+1));
		}
		
	}
	

}
