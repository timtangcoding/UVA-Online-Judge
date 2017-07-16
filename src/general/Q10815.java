/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1756
 * 10815 - Andy's First Dictionary
 */

package general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q10815 {
	
	private static final int MAX = 1000;
	
	
	interface WordFilter{
		public ArrayList<String> filterWord(String rawString);
	}
	
	class RegexFilter implements WordFilter{

		private Pattern regPatter;
		
		
		public RegexFilter() {
			// TODO Auto-generated constructor stub
			regPatter = Pattern.compile("[a-zA-Z]+");
		}
		
		@Override
		public ArrayList<String> filterWord(String rawString) {
			// TODO Auto-generated method stub
			Matcher r = this.regPatter.matcher(rawString);
			ArrayList<String> results = new ArrayList<String>();
			while(r.find()){
				results.add(r.group().toLowerCase());
			}
			return results;
		}
	}
	
	class Dictionary{
		private TreeSet<String> dictionary;
		private WordFilter filter;
		
		public Dictionary(){
			this.dictionary = new TreeSet<String>();
			this.filter = new RegexFilter();
		}
		
		public void add(String rawText){
			ArrayList<String> filteredWord = this.filter.filterWord(rawText);
			for(String word : filteredWord){
				this.dictionary.add(word);
			}
		}
		
		public void printAll(){
			Iterator<String> iterator = this.dictionary.iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next());
			}
		}
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
	
	private Dictionary mDictionary;
	
	public Q10815(){
		this.mDictionary = new Dictionary();
	}
	
	public void addWord(String rawText){
		this.mDictionary.add(rawText);
	}
	
	
	public void printAll(){

		this.mDictionary.printAll();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Q10815 problemSolver = new Q10815();
		String input;
		StringTokenizer idata;
		
		while((input = Q10815.ReadLn(MAX)) != null){
			idata = new StringTokenizer(input);
			while(idata.hasMoreTokens()){
				
				problemSolver.addWord(idata.nextToken().trim());
				
			}
		}
		
		problemSolver.printAll();
		

	}

}
