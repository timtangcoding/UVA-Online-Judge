package regular_expression;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class Q10058_2_Test {
	
	private ArrayList<String> testCases = new ArrayList<String>();
	
	@Before
	public void before(){
		
		testCases.clear();
		testCases.add("the dog and a cat");
	}
	
	@Test
	public void test(){
		
		Q10058_2 tester = new Q10058_2();
		for(String testCase : testCases){
			tester.isActiveList(testCase);
		}
		
	}

}
