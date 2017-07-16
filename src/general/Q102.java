/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=38
 * 102 - Ecological Bin Packing
 */

package general;

import java.io.IOException;
import java.util.StringTokenizer;


public class Q102 {
	
	static class Bin{
		
		enum BinType{
			G(0), B(1), C(2);
			private int value;
			BinType(int v){
				this.value = v;
			}
		}
		
		private int brownNum;
		private int greenNum;
		private int clearNum;
		public Bin(int bn, int gn, int cn){
			this.brownNum = bn;
			this.greenNum = gn;
			this.clearNum = cn;
		}
		
		public int getMovableNum(BinType v){
			int movableBinNum=0;
			switch(v){
			case G:
				movableBinNum = this.brownNum + this.clearNum;
				break;
			case B:
				movableBinNum = this.greenNum + this.clearNum;
				break;
				
			case C:
				movableBinNum = this.brownNum + this.greenNum;
				break;
			}
			return movableBinNum;
		}
	}
	
	private Bin firstBin, secondBin, thirdBin;
	private Bin.BinType[] allocation;
	private int min;
	
	public void setBins(int first1, int first2, int first3, 
			int second1, int second2, int second3, 
			int third1, int third2, int third3){
		firstBin = new Bin(first1, first2, first3);
		secondBin = new Bin(second1, second2, second3);
		thirdBin = new Bin(third1, third2, third3);
	}
	
	public void reset(){
		this.firstBin = null;
		this.secondBin = null;
		this.thirdBin = null;
		this.min = -1;
		this.allocation = new Bin.BinType[3];
	}
	
	public void getResult(){
		
		for(int f=0; f<3; f++){
			for(int s=0; s<3; s++){
				for(int t=0; t<3; t++){
					if(f!=s && s!=t && f!=t){
						int count = firstBin.getMovableNum(Bin.BinType.values()[f]) 
								+ secondBin.getMovableNum(Bin.BinType.values()[s])
								+ thirdBin.getMovableNum(Bin.BinType.values()[t]);
						if(this.min < 0 || count < this.min){
							this.min = count;
							allocation[0] = Bin.BinType.values()[f];
							allocation[1] = Bin.BinType.values()[s];
							allocation[2] = Bin.BinType.values()[t];
						}else if(this.min == count){
							String current = allocation[0].name()
									+ allocation[1].name()
									+ allocation[2].name();
							
							String newString = Bin.BinType.values()[f].name() 
									+ Bin.BinType.values()[s].name() 
									+ Bin.BinType.values()[t].name();
							
							if(newString.compareTo(current) < 0){
								allocation[0] = Bin.BinType.values()[f];
								allocation[1] = Bin.BinType.values()[s];
								allocation[2] = Bin.BinType.values()[t];
							}
						}
						
					}
				}
			}
		}
		
		for(Bin.BinType b : allocation){
			System.out.print(b.name());
		}
		System.out.println(" "+this.min);
		
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
		StringTokenizer idata;
		
		Q102 solver = new Q102();
		
		while((input = Q102.ReadLn(255)) != null){
			solver.reset();
			idata = new StringTokenizer(input.trim());
			if(idata.countTokens() == 9){
			solver.setBins(Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()), 
						Integer.parseInt(idata.nextToken()));
				
				solver.getResult();
			}else{
				System.out.println("the input data count is incorrect: "+idata.countTokens());
			}
			
		}

	}

}
