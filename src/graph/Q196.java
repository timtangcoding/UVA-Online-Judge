/*
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=132
 * 196 - Spreadsheet
 */

package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Q196 {

    private static final int MAX = 9000000;

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

    static String readInput(){
        String input = null;
        while((input = ReadLn(MAX)) != null) {
            input = input.trim();
            break;
        }

        return input;
    }

    class Cell {
        public int row = 0;
        public int column = 0;
        List<Cell> dependents = new ArrayList<>();
        List<Cell> affectingCells = new ArrayList<>();
        int value = 0;
    }

    private int row = 0;
    private int column = 0;

    private Cell[][] spreadSheetGraph;
    private int[][] spreadSheetValue;
    private boolean[][] visited;

    private static final String FORUMLA_START = "=";
    private static final String FORUMLA_OPERATOR = "+";

    public void readSpreadsheetSize(int row, int column){
        this.column = column;
        this.row = row;

        construct();
    }

    public void updateSpreadSheet(){
        calculate();
        refreshResult();
    }

    private void refreshResult(){
        for(int i=0; i<row; i++){
            StringBuffer sb = new StringBuffer();
            for(int j=0; j<column; j++){
                if(sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(spreadSheetValue[i][j]);
            }

            System.out.println(sb.toString());
        }
    }

    /*public void readRow(int rowIndex, String rowValue){
        int columnIndex = 0;
        StringTokenizer tokenizer = new StringTokenizer(rowValue);
        while(tokenizer.hasMoreTokens()){
            String cellValue = tokenizer.nextToken().trim();
            if(isFormula(cellValue)){
                readFormula(rowIndex, columnIndex, cellValue);
            }else{
                readCell(rowIndex, columnIndex, Integer.parseInt(cellValue));
            }
            columnIndex++;
        }
    }*/

    public void readFormula(int rowIndex, int columnIndex, String forumla){

        Cell currentCell = spreadSheetGraph[rowIndex][columnIndex];
        currentCell.row = rowIndex;
        currentCell.column = columnIndex;

        String[] dependingCellNames = forumla.substring(1).split("\\"+FORUMLA_OPERATOR);
        if(dependingCellNames != null){
            for(String cellName : dependingCellNames){

                int[] cellIndex = cellToRowColumn(cellName);

                Cell dependingCell = spreadSheetGraph[cellIndex[0]][cellIndex[1]];
                currentCell.dependents.add(dependingCell);
                dependingCell.affectingCells.add(currentCell);
            }
        }
    }

    public boolean isFormula(String cellValue){
        return (cellValue.startsWith(FORUMLA_START));
    }


    public static int[] cellToRowColumn(String cellName){
        int[] coor = new int[2];
        try{

            String rowIndexPart = cellName.split("[A-Z]+")[1].trim();
            int rowIndex = Integer.parseInt(rowIndexPart)-1;

            String column = cellName.split("\\d")[0];
            char[] columns =  column.toUpperCase().toCharArray();
            int total = 0;
            int order = columns.length;
            for(int i=order-1; i>=0; i--){
                total += ((int)columns[i] - 64) * Math.pow(26, columns.length-i-1);
            }
            coor[0] = rowIndex;
            coor[1] = total-1;

        }catch(NumberFormatException e){
            System.out.println(e.getMessage());
            System.out.println("cell problem: " + cellName);
        }

        return coor;
    }

    private void calculate(){
        int foundCellCount = 0;
        int oldFoundCellCount = 0;
        int newlyAddedCandidate = 0;
        while(foundCellCount < row*column){
            newlyAddedCandidate = 0;
            for(int i=0; i<row; i++){
                for(int j=0; j<column; j++){
                    Cell currentCell = spreadSheetGraph[i][j];
                    if(currentCell.dependents.size() == 0 && !visited[i][j]){
                        spreadSheetValue[currentCell.row][currentCell.column] = currentCell.value;
                        foundCellCount++;
                        if(currentCell.affectingCells != null){
                            int affectingCellSize = currentCell.affectingCells.size();
                            for(int k=affectingCellSize-1; k>=0; k--){
                                Cell affectingCell = currentCell.affectingCells.get(k);
                                affectingCell.value += currentCell.value;
                                affectingCell.dependents.remove(currentCell);
                                currentCell.affectingCells.remove(k);
                                if(affectingCell.dependents.size() == 0) newlyAddedCandidate++;
                            }
                        }

                        visited[i][j] = true;
                    }
                }
            }
            if(oldFoundCellCount == foundCellCount || newlyAddedCandidate == 0){
                    break;
            }
            oldFoundCellCount = foundCellCount;
        }
    }

    public void readCell(int rowIndex, int columnIndex, int value){
        Cell currentCell = spreadSheetGraph[rowIndex][columnIndex];
        currentCell.value = value;
        spreadSheetValue[rowIndex][columnIndex] = value;
    }

    private void construct(){
        this.spreadSheetValue = new int[row][column];
        this.spreadSheetGraph = new Cell[row][column];
        this.visited = new boolean[row][column];
        init();
    }

    private void init(){
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                Cell cell = new Cell();
                cell.row = i;
                cell.column = j;
                cell.value = 0;
                this.spreadSheetGraph[i][j] = cell;
                this.spreadSheetValue[i][j] = 0;
                this.visited[i][j] = false;
            }
        }
    }

    public void clear() {
        this.row = this.column = 0;
        this.spreadSheetGraph = null;
        this.spreadSheetValue = null;
    }

    public static void main(String[] args) {
        Q196 solver = new Q196();
        int testCase = 0;
        int row=0; int column=0;
        String input = null;
        StringTokenizer idata = null;
        input = readInput();
        if(input == null) return;
        testCase = Integer.parseInt(input);
        int k = 0;
        while(k <= testCase-1){
            row = column = 0;
            input = readInput();
            if(input == null) return;
            if(input.isEmpty()) continue;

            idata = new StringTokenizer(input);
            if(idata.hasMoreTokens()){
                column = Integer.parseInt(idata.nextToken().trim());
            }
            if(idata.hasMoreTokens()){
                row = Integer.parseInt(idata.nextToken().trim());
            }

            if(row == 0 && column == 0) return;
            k++;
            solver.readSpreadsheetSize(row, column);
            int rowCounter = 0;
            int columnCounter = 0;
            String cellValue = "";
            while(rowCounter<=row-1 && columnCounter<=column-1){
                input = readInput();
                if(input == null) return;
                if(input.isEmpty()) continue;
                idata = new StringTokenizer(input);
                while(idata.hasMoreTokens()){
                    cellValue = idata.nextToken().trim();
                    if(solver.isFormula(cellValue)){
                        solver.readFormula(rowCounter, columnCounter, cellValue);
                    }else{
                        solver.readCell(rowCounter, columnCounter, Integer.parseInt(cellValue));
                    }
                    ++columnCounter;
                    if(columnCounter == column){
                        columnCounter = 0;
                        ++rowCounter;
                    }
                }

            }
            solver.updateSpreadSheet();
            solver.clear();
        }
    }

}
