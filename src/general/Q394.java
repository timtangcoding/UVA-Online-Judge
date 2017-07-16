/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=330
 * 394 - Mapmaker
 */

package general;

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Q394 {
	
	class ArrayMap{
		private long baseAddress;
		private int[][] dimensionBounds;
		private int numDimen;
		private int byteSize;
		private int[] dimensionSize;
		
		public ArrayMap(long baseAddr, int dimen, int byteSize){
			this.baseAddress = baseAddr;
			this.numDimen = dimen;
			this.byteSize = byteSize;
		}
		
		public void setDimensionSize(){
			this.dimensionSize = new int[this.numDimen];
			for(int k=this.numDimen-1; k>=0; k--){
				if(k+1 < this.numDimen){
					this.dimensionSize[k] = (this.dimensionBounds[k+1][1] - this.dimensionBounds[k+1][0] + 1) * this.dimensionSize[k+1];
				}else{
					this.dimensionSize[k] = 1;
				}
			}
		}
		
		public void setDimenArray(int[][] dimenArr){
			this.dimensionBounds = dimenArr;
			this.setDimensionSize();
		}
		
		public int getDimension(){
			return this.numDimen;
		}
		
		public int getByteSize(){
			return this.byteSize;
		}
		
		public long calculateRequiredAddress(int[] cells){
			long resultAddress = this.baseAddress;
			int numCellUsed = 0;
			
			for(int k=0; k<this.numDimen; k++){
				int cellUsed = cells[k];
				numCellUsed +=  (cellUsed - this.dimensionBounds[k][0]) * this.dimensionSize[k];
			}
			resultAddress += numCellUsed * this.byteSize;
			return resultAddress;
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
	
	private HashMap<String, ArrayMap> arrayMaps;
	
	public Q394(){
		arrayMaps = new HashMap<String, ArrayMap>();
	}
	
	public void addMapping(String mapName, ArrayMap mapObj){
		arrayMaps.put(mapName, mapObj);
	}
	
	public int getDimen(String name){
		ArrayMap mapObj = arrayMaps.get(name);
		return mapObj.getDimension();
	}
	
	public long getRequiredAddress(String name, int[] cells){
		ArrayMap mapObj = arrayMaps.get(name);
		return mapObj.calculateRequiredAddress(cells);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Q394 solver = new Q394();
		String input;
		StringTokenizer idata;
		int numArray, numRefs;
		
		while((input = Q394.ReadLn(255)) != null){
			idata = new StringTokenizer(input);
			numArray = Integer.parseInt(idata.nextToken().trim());
			numRefs = Integer.parseInt(idata.nextToken().trim());
			
			for(int i=0; i<numArray; i++){
				input = Q394.ReadLn(255);
				idata = new StringTokenizer(input);
				String mapName = idata.nextToken().trim();
				long baseAddr = Long.parseLong(idata.nextToken().trim());
				int byteSize = Integer.parseInt(idata.nextToken().trim());
				int dimenNum = Integer.parseInt(idata.nextToken().trim());
				
				ArrayMap mapObj = solver.new ArrayMap(baseAddr, dimenNum, byteSize);
				int[][] arrayDimens = new int[dimenNum][2];
				for(int k=0; k<dimenNum; k++){
					arrayDimens[k][0] = Integer.parseInt(idata.nextToken().trim());
					arrayDimens[k][1] = Integer.parseInt(idata.nextToken().trim());
				}
				mapObj.setDimenArray(arrayDimens);
				solver.addMapping(mapName, mapObj);
			}
			for(int i=0; i<numRefs; i++){
				input = Q394.ReadLn(255);
				idata = new StringTokenizer(input);
				String mapName = idata.nextToken().trim();
				int dimenNum = solver.getDimen(mapName);
				int[] arrayCells = new int[dimenNum];
				StringBuffer sb = new StringBuffer();
				for(int k=0; k<dimenNum; k++){
					arrayCells[k] = Integer.parseInt(idata.nextToken().trim());
					if(sb.length() > 0){
						sb.append(", ");
					}
					sb.append(arrayCells[k]);
				}
				long result = solver.getRequiredAddress(mapName, arrayCells);
				System.out.println(mapName+"["+sb.toString()+"] = "+result);
				
				
			}
			
		}

	}

}

