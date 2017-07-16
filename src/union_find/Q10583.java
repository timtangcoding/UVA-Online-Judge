/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=359&page=show_problem&problem=1524
 * 10583 - Ubiquitous Religions
 */

package union_find;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Q10583 {
	
	public static final int MAX = 1000;
	public static final int MAX_STUDENT = 50001;
	
	private int[] students = new int[MAX_STUDENT];
	
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
	
	private void resetCase(int studentsCount){
		studentsCount += 1;
		students = new int[studentsCount];
		for(int i=0; i<studentsCount; i++){
			this.students[i] = -1;
		}
	}
	
	private int getStudentReligion(int student){
		int studentReligion = this.students[student];
		if(studentReligion == -1){
			return student;
		}
		this.students[student] = getStudentReligion(studentReligion);
		return this.students[student];
	}
	
	private void setSameReligion(int studentA, int studentB){
		int religionA = getStudentReligion(studentA);
		int religionB = getStudentReligion(studentB);
		if(religionA != religionB){
			this.students[religionB] = religionA;
		}
	}
	
	private void getReligionCount(int currentCase, int studentsCount){
//		System.out.println("results: ");
		
		TreeSet<Integer> religionCount = new TreeSet<Integer>();
		for(int i=1; i<=studentsCount; i++){
			int religion = this.getStudentReligion(i);
//			System.out.print(religion + ", ");
//			System.out.print(i + " => "+this.students[i] + ", ");
			religionCount.add(religion);
		}
		int result = religionCount.size();
		System.out.println("Case "+currentCase + ": "+result);
	}
	
	private StringTokenizer readData(StringTokenizer dataToken){
		if(dataToken != null && dataToken.hasMoreTokens()){
			return dataToken;
		}
		String input;
		while((input = Q10583.ReadLn(MAX)) != null){
			if(input.isEmpty()){
				continue;
			}
			dataToken = new StringTokenizer(input.trim());
			return dataToken;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringTokenizer idata = null;
		Q10583 solver = new Q10583();
		int studentsCount;
		int statementCount;
		int caseCount = 0;
		
		idata = solver.readData(idata);
		if(idata == null){
			return;
		}
		studentsCount = Integer.parseInt(idata.nextToken().trim());
		
		idata = solver.readData(idata);
		if(idata == null){
			return;
		}
		statementCount = Integer.parseInt(idata.nextToken().trim());
		
		while(true){
			//TODO: init new case
			caseCount++;
			solver.resetCase(studentsCount);
			int student1, student2;
			for(int j=0; j<statementCount; j++){
				
				idata = solver.readData(idata);
				if(idata == null){
					return;
				}
				student1 = Integer.parseInt(idata.nextToken().trim());
				
				idata = solver.readData(idata);
				if(idata == null){
					return;
				}
				student2 = Integer.parseInt(idata.nextToken().trim());
				
				if(student1 == 0 && student2 == 0){
					return;
				}
				solver.setSameReligion(student1, student2);
				
			}
			solver.getReligionCount(caseCount, studentsCount);
			idata = solver.readData(idata);
			if(idata == null){
				return;
			}
			studentsCount = Integer.parseInt(idata.nextToken().trim());
			
			idata = solver.readData(idata);
			if(idata == null){
				return;
			}
			statementCount = Integer.parseInt(idata.nextToken().trim());
			if(statementCount == 0 && studentsCount == 0){
				return;
			}
		}
	}

}
