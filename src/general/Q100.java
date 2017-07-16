/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=36
 * 100 - The 3n + 1 problem
 */

package general;

import java.io.IOException;
import java.util.StringTokenizer;

class Q100 {
	
	private long stepCount = 0;
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
	
	
	
	public long iteration(int number){
		this.stepCount++;
		if(number == 1){
			return this.stepCount;
		}
		else if(number%2 == 0){
			iteration(number/2);
		}else{
			iteration(3*number + 1);
		}
		return this.stepCount;
	}
	
	public void restart(){
		this.stepCount = 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q100 solver = new Q100();
		
		String input = null;
		StringTokenizer idata;
		int lower, higher;
		int first, second;
		
		while((input = Q100.ReadLn(255)) != null){
			idata = new StringTokenizer(input.trim());
			first = lower = Integer.parseInt(idata.nextToken());
			second = higher = Integer.parseInt(idata.nextToken());
			if(lower > higher){
				int temp = lower;
				lower = higher;
				higher = temp;
			}
			long maxCount = 0;
			for(int i=lower; i<=higher; i++){
				solver.restart();
				long count = solver.iteration(i);
				if(count > maxCount){
					maxCount = count;
				}
			}
			
			System.out.println(first + " " + second + " " + maxCount);
		}
		
	}

}
