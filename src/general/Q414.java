/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=355
 * 414 - Machined Surfaces
 */

package general;

import java.io.IOException;

public class Q414 {
	
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
	
	private int[] spaceCount;
	private int dataSize;
	
	public void resetData(int size){
		this.spaceCount = new int[size];
		this.dataSize = size;
	}
	
	public void setSpaceCount(int iteration, int size){
//		System.out.println("iteration: "+iteration + ", "+size);
		this.spaceCount[iteration] = size;
	}
	
	public int getMinSpace(){
		int minSpace = -1;
		for(int i=0; i<this.dataSize; i++){
			if(minSpace < 0 || this.spaceCount[i] < minSpace){
				minSpace = this.spaceCount[i];
			}
		}
//		System.out.println("min: "+minSpace);
		return minSpace;
		
	}
	
	public void getSpace(){
		int netSpace = 0;
		int minSpace = this.getMinSpace();
		for(int i=0; i<this.dataSize; i++){
			if(this.spaceCount[i] > minSpace){
				netSpace += this.spaceCount[i] - minSpace;
			}
		}
		System.out.println(netSpace);
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Q414 solver = new Q414();
		String input;
		int dataSize;
		int wsCount = 0;;
		while((input = Q414.ReadLn(255))!= null){
			dataSize = Integer.parseInt(input.trim());
			if(dataSize == 0){
				break;
			}
			solver.resetData(dataSize);
			for(int i=0; i<dataSize; i++){
				input = Q414.ReadLn(255);
				wsCount = 0;
				for(char c: input.trim().toCharArray()){
					if(c == ' ' || c == 'B'){
						wsCount++;
					}
				}
				solver.setSpaceCount(i, wsCount);

			}
			solver.getSpace();
			
		}

	}

}
