/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=4331
 * 1556 - Disk Tree
 */
package tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Q1556 {
	
	private static final int MAX = 1000;
	private static final String SEPARATOR = "\\";
	
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
	
	private StringTokenizer readData(StringTokenizer dataToken){
		if(dataToken != null && dataToken.hasMoreTokens()){
			return dataToken;
		}
		String input;
		while((input = Q1556.ReadLn(MAX)) != null){
			if(input.isEmpty()){
				continue;
			}
			dataToken = new StringTokenizer(input.trim());
			return dataToken;
		}
		return null;
	}
		
	private List<DiskDirectory> rootDirectories;
	private HashMap<String, DiskDirectory> directoryMap; 
	
	public void parsePath(String path){
		String[] directories = path.split("\\\\");
		int directoryNum = directories.length;
		DiskDirectory parentDirectory = null;
		DiskDirectory currentDirectory = null;
		
		StringBuffer directoryPath = new StringBuffer();
		for(int i=0; i<directoryNum; i++){
			
			if(!directories[i].isEmpty()){
				
				parentDirectory = currentDirectory;
				if(directoryPath.length() > 0){
					directoryPath.append(SEPARATOR);
				}
				directoryPath.append(directories[i]);
				currentDirectory = directoryMap.get(directoryPath.toString());
				if(currentDirectory == null){
					currentDirectory = new DiskDirectory();
					currentDirectory.setName(directories[i]);
					currentDirectory.setParent(parentDirectory);
					if(parentDirectory != null){
						parentDirectory.getChildren().add(currentDirectory);
					}else{
						rootDirectories.add(currentDirectory);
					}
					directoryMap.put(directoryPath.toString(), currentDirectory);
				}
				
			}
		}
	}
	
	class DiskDirectory implements Comparable<DiskDirectory>{
		private String name;
		private DiskDirectory parent;
		private List<DiskDirectory> children;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public DiskDirectory getParent() {
			return parent;
		}
		public void setParent(DiskDirectory parent) {
			this.parent = parent;
		}
		public List<DiskDirectory> getChildren() {
			return children;
		}
		public void setChildren(List<DiskDirectory> children) {
			this.children = children;
		}
		
		public DiskDirectory(){
			this.name = null;
			this.parent = null;
			this.children = new ArrayList<DiskDirectory>();
		}
		@Override
		public int compareTo(DiskDirectory o) {
			return getName().compareTo(o.getName());
		}
	}
	
	public void sortRootDirectories(){
		Collections.sort(rootDirectories);
	}
	
	public void printDirectory(int level, DiskDirectory directory){
		level++;
		for(int i=0; i<level; i++){
			System.out.print(" ");
		}
		System.out.println(directory.getName());
		List<DiskDirectory> children = directory.getChildren();
		Collections.sort(children);
		for(DiskDirectory child : children){
			printDirectory(level, child);
		}
	}
	
	public void printDirectoryTree(){
		sortRootDirectories();
		int level = -1;
		for(DiskDirectory directory : rootDirectories){
			printDirectory(level, directory);
		}
	}
	
	public void resetData(){
		rootDirectories.clear();
		directoryMap.clear();
	}
	
	public Q1556(){
		rootDirectories = new ArrayList<DiskDirectory>();
		directoryMap = new HashMap<String, DiskDirectory>();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringTokenizer idata = null;
		Q1556 solver = new Q1556();
		int pathNumber;
		String path;
		
		while(true){
			
			idata = solver.readData(idata);
			if(idata == null){
				return;
			}
			pathNumber = Integer.parseInt(idata.nextToken().trim());
			for(int i=0; i<pathNumber; i++){
				idata = solver.readData(idata);
				if(idata == null){
					return;
				}
				path = idata.nextToken().trim().replace("\\", "\\\\");
				solver.parsePath(path);
			}
			solver.printDirectoryTree();
			System.out.println();
			solver.resetData();
			
		}
		
	}

}
