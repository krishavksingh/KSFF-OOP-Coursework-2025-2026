package cityrescue;

import java.util.Arrays;

public class CityMap { // Map Implementation containing rows cols and whether a space is blocked
    private int gridRows;
    private int gridCols;
    private boolean[][] blocked;
    public CityMap(int rows, int cols)
    {
        gridCols = cols;
        gridRows = rows;
        blocked = new boolean[rows][cols];
        for (int i = 0; i < blocked.length; i++) {
            Arrays.fill(blocked[i], false);
        }
        
    }
    public int[] getSize()
    {
        return new int[]{gridRows, gridCols};
    }
    public void setBlocked(int row, int col)
    {
        blocked[row][col] = true;
    }
    public void setUnblocked(int row, int col)
    {
        blocked[row][col] = false;
    }
    public boolean isBlocked(int row, int col) {
        if (blocked[row][col]){
            return true;
        }
        else return false;
    }
    public int getObstacleCount() {
        int count=0;
        for (int i=0; i<gridRows; i++){
            for (int j=0; j< gridCols; j++){
                if (blocked[i][j]){
                    count++;
                }
            }
        }
        return count;
    }
    
}
