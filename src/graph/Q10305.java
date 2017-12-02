/*
 * https://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=1246
 * 10305 - Ordering Tasks
 */
package graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Q10305 {

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

    static int[] readInput(){
        String input = "";
        StringTokenizer idata;
        int[] result = new int[]{-1, -1};
        while((input = ReadLn(MAX)) != null){
            idata = new StringTokenizer(input);
            if(idata.hasMoreTokens()){
                result[0] = Integer.parseInt(idata.nextToken().trim());
            }
            if(idata.hasMoreTokens()){
                result[1] = Integer.parseInt(idata.nextToken().trim());
            }
            break;
        }

        return result;
    }

    enum VISITED_STATE {
        UNVISITED, VISITED;
    }

    enum RELATIONSHIP {
        NOT_PREREQUISITE, PREREQUISITE;
    }

    private RELATIONSHIP[][] graph;
    private VISITED_STATE[] visited;
    private int[] inDegree;
    private int taskSize;
    private Queue<Integer> taskOrder;

    private void init(int taskSize){
        this.taskSize = taskSize;
        graph = new RELATIONSHIP[taskSize][taskSize];
        visited = new VISITED_STATE[taskSize];
        inDegree = new int[taskSize];

        for(int i=0; i<taskSize; i++){
            inDegree[i] = 0;
            visited[i] = VISITED_STATE.UNVISITED;

            for(int j=0; j<taskSize; j++){
                graph[i][j] = RELATIONSHIP.NOT_PREREQUISITE;

            }
        }

        taskOrder = new LinkedList<>();
    }

    private void readRelations(int[] relation){

        int from = relation[0]-1;
        int to = relation[1]-1;
        if(graph[from][to] == RELATIONSHIP.NOT_PREREQUISITE){
            graph[from][to] = RELATIONSHIP.PREREQUISITE;
            ++inDegree[to];
        }

    }

    private void clear(){
        this.taskSize = 0;
        this.graph = null;
        this.visited = null;
        this.inDegree = null;
        this.taskOrder.clear();
    }

    private void arrangeTask(){
        if(taskOrder.size() == taskSize) return;
        for(int i=0; i<taskSize; i++){
            if(inDegree[i] == 0 && visited[i] == VISITED_STATE.UNVISITED){
                taskOrder.add(i);
                visited[i] = VISITED_STATE.VISITED;
                for(int j=0; j<taskSize; j++){
                    if(graph[i][j] == RELATIONSHIP.PREREQUISITE){
                        graph[i][j] = RELATIONSHIP.NOT_PREREQUISITE;
                        --inDegree[j];
                    }
                }
                arrangeTask();
            }
        }
    }

    public void displayTaskOrder(){
        StringBuffer sb = new StringBuffer();
        while(taskOrder.size() > 0){
            if(sb.length() > 0){
                sb.append(" ");
            }
            sb.append(taskOrder.remove()+1);
        }

        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int numTask = 0;
        int numRelation = 0;
        int[] input;
        Q10305 solver = new Q10305();
        while(true){

            input = readInput();
            if(input[0] <= 0 && input[1] <= 0){
                return;
            }

            numTask = input[0];
            numRelation = input[1];
            solver.init(numTask);
            for(int i=0; i<numRelation; i++){
                input = readInput();
                if(input[0] <= 0 && input[1] <= 0){
                    return;
                }
                solver.readRelations(input);
            }

            solver.arrangeTask();
            solver.displayTaskOrder();
            solver.clear();
        }
    }
}
