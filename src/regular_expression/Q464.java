/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=742&page=show_problem&problem=405
 * 464 - Sentence/Phrase Generator
 */
package regular_expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Q464 {
	
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
	
	private static final String SENTENCE = "sentence";
	private static final String TRANS_SENTENCE = "trans-sentence";
	private static final String INTRANS_SENTENCE = "intrans-sentence";
	private static final String OBJECT = "object";
	private static final String SUBJECT = "subject";
	private static final String NOUN_PHRASE = "noun-phrase";
	private static final String MODIFIED_NOUN = "modified-noun";
	private static final String MODIFIER = "modifier";
	private static final String VERB_PHRASE = "verb-phrase";
	private static final String INTRANS_VERB_PHRASE = "intrans-verb-phrase";
	private static final String PREP_PHRASE = "prep-phrase";
	private static final String NOUN = "noun";
	private static final String TRANS_VERB = "trans-verb";
	private static final String INTRANS_VERB = "intrans-verb";
	private static final String ARTICLE = "article";
	private static final String ADJECTIVE = "adjective";
	private static final String ADVERB = "adverb";
	private static final String PREPOSITION = "preposition";
	private static final String EMPTY = "empty";
	
	
	private static final List<String> EMPTIES;
	private static final List<String> PREPOSITIONS;
	private static final List<String> ADVERBS;
	private static final List<String> ADJECTIVES;
	private static final List<String> ARTICLES;
	private static final List<String> INTRANS_VERBS;
	private static final List<String> TRANS_VERBS;
	private static final List<String> NOUNS;
	private int counter;
	
	static {
		
		String[] empty = {""};
		EMPTIES = Collections.unmodifiableList(Arrays.asList(empty));
		
		String[] prepositions = {"on", "over", "through"};
		PREPOSITIONS = Collections.unmodifiableList(Arrays.asList(prepositions));
		
		String[] adverb = {"nearly", "suddenly", "restlessly"};
		ADVERBS = Collections.unmodifiableList(Arrays.asList(adverb));
		
		String[] adjectives = {"green", "small", "rabid", "quick"};
		ADJECTIVES = Collections.unmodifiableList(Arrays.asList(adjectives));
		
		String[] article = {"the", "a"};
		ARTICLES = Collections.unmodifiableList(Arrays.asList(article));
		
		String[] intrans_verb = {"slept", "jumped", "walked", "swam"};
		INTRANS_VERBS = Collections.unmodifiableList(Arrays.asList(intrans_verb));
		
		String[] trans_verb = {"struck", "saw", "bit", "took"};
		TRANS_VERBS = Collections.unmodifiableList(Arrays.asList(trans_verb));
		
		String[] nouns = {"man", "dog", "fish", "computer", "waves"};
		NOUNS = Collections.unmodifiableList(Arrays.asList(nouns));
		
	}
	
	public Q464(){
		counter = 0;
	}
	
	private String getBasicElement(List<String> basicElements){
		int position = (++counter % basicElements.size());
		return basicElements.get(position);
	}
	
	private String getNoun(){
		 return getBasicElement(NOUNS);
	}
	
	private String getTransVerb(){
		return getBasicElement(TRANS_VERBS);
	}
	
	private String getInTransVerb(){
		return getBasicElement(INTRANS_VERBS); 
	}
	
	private String getArticle(){
		return getBasicElement(ARTICLES);
	}
	
	private String getAdjective(){
		return getBasicElement(ADJECTIVES);
	}
	
	private String getAdverb(){
		return getBasicElement(ADVERBS);
	}
	
	private String getPreposition(){
		return getBasicElement(PREPOSITIONS);
	}
	
	private String getEmpty(){
		return EMPTIES.get(0);
	}
	
	private int getChoice(int choiceNum){
		return (++counter) % choiceNum;
	}
	
	private String getIntransVerbPhrase(){
		StringBuffer sb = new StringBuffer();
		int CHOICE_NUM = 2;
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getInTransVerb());
		}else{
			sb.append(getAdverb());
			sb.append(" ");
			sb.append(getInTransVerb());
		}
		
		return sb.toString();
	}
	
	private String getVerbPhrase(){
		StringBuffer sb = new StringBuffer();
		int CHOICE_NUM = 2;
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getTransVerb());
		}else{
			sb.append(getAdverb());
			sb.append(" ");
			sb.append(getTransVerb());
		}
		
		return sb.toString();
	}
	
	private String getModifier(){
		int CHOICE_NUM = 2;
		StringBuffer sb = new StringBuffer();
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getAdjective());
		}else{
			sb.append(getAdverb());
			sb.append(" ");
			sb.append(getAdjective());
		}
		
		return sb.toString();
	}
	
	private String getModifiedNoun(){
		int CHOICE_NUM = 2;
		StringBuffer sb = new StringBuffer();
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getNoun());
		}else{
			sb.append(getModifier());
			sb.append(" ");
			sb.append(getNoun());
		}
		return sb.toString();
	}
	
	private String getNounPhrase(){
		StringBuffer sb = new StringBuffer();
		sb.append(getArticle());
		sb.append(" ");
		sb.append(getModifiedNoun());
		return sb.toString();
	}
	
	private String getObject(){
		return getNounPhrase();
	}
	
	private String getSubject(){
		return getNounPhrase();
	}
	
	
	private String getPrePhrase(){
		int CHOICE_NUM = 2;
		StringBuffer sb = new StringBuffer();
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getPreposition());
			sb.append(" ");
			sb.append(getNounPhrase());
		}else{
			sb.append(getEmpty());
		}
		return sb.toString();
	}
	
	private String getInTransSentence(){
		StringBuffer sb = new StringBuffer();
		sb.append(getSubject());
		sb.append(" ");
		sb.append(getIntransVerbPhrase());
		sb.append(" ");
		sb.append(getPrePhrase());
		return sb.toString();
	}
	
	private String getTransSentence(){
		StringBuffer sb = new StringBuffer();
		sb.append(getSubject());
		sb.append(" ");
		sb.append(getVerbPhrase());
		sb.append(" ");
		sb.append(getObject());
		sb.append(" ");
		sb.append(getPrePhrase());
		return sb.toString();
	}
	
	private String getSentence(){
		StringBuffer sb = new StringBuffer();
		int CHOICE_NUM = 2;
		int choice = getChoice(CHOICE_NUM);
		if(choice == 0){
			sb.append(getTransSentence());
		}else{
			sb.append(getInTransSentence());
		}
		return sb.toString();
	}
	
	
	
	public void readData(String inputString){
		String output = "";
		if(SENTENCE.equalsIgnoreCase(inputString)){
			output = getSentence();
		}else if(TRANS_SENTENCE.equalsIgnoreCase(inputString)){
			output = getTransSentence();
		}else if(INTRANS_SENTENCE.equalsIgnoreCase(inputString)){
			output = getInTransSentence();
		}else if(SUBJECT.equalsIgnoreCase(inputString)){
			output = getSubject();
		}else if(OBJECT.equalsIgnoreCase(inputString)){
			output = getObject();
		}else if(NOUN_PHRASE.equalsIgnoreCase(inputString)){
			output = getNounPhrase();
		}else if(MODIFIED_NOUN.equalsIgnoreCase(inputString)){
			output = getModifiedNoun();
		}else if(MODIFIER.equalsIgnoreCase(inputString)){
			output = getModifier();
		}else if(VERB_PHRASE.equalsIgnoreCase(inputString)){
			output = getVerbPhrase();
		}else if(INTRANS_VERB_PHRASE.equalsIgnoreCase(inputString)){
			output = getIntransVerbPhrase();
		}else if(PREP_PHRASE.equalsIgnoreCase(inputString)){
			output = getPrePhrase();
		}else if(NOUN.equalsIgnoreCase(inputString)){
			output = getNoun();
		}else if(TRANS_VERB.equalsIgnoreCase(inputString)){
			output = getTransVerb();
		}else if(INTRANS_VERB.equalsIgnoreCase(inputString)){
			output = getInTransVerb();
		}else if(ARTICLE.equalsIgnoreCase(inputString)){
			output = getArticle();
		}else if(ADJECTIVE.equalsIgnoreCase(inputString)){
			output = getAdjective();
		}else if(ADVERB.equalsIgnoreCase(inputString)){
			output = getAdverb();
		}else if(PREPOSITION.equalsIgnoreCase(inputString)){
			output = getPreposition();
		}else if(EMPTY.equalsIgnoreCase(inputString)){
			output = getEmpty();
		}
		
		System.out.println(output.trim());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q464 solver = new Q464();
		String input;
		while((input = Q464.ReadLn(MAX)) != null){
			solver.readData(input.trim());
		}
	}
}
