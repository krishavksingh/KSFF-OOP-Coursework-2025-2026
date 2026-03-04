package cityrescue;

import java.util.Arrays;

/**
 * Map Implementation containing rows cols and whether a space is blocked
 */
public class CityMap {  
    private int gridRows;
    private int gridCols;
    private boolean[][] blocked;
    /**
     * Creates an instance of a CityMap object with the number of rows and columns (x, y) desired
     */
    public CityMap(int rows, int cols)
    {
        gridCols = cols;
        gridRows = rows;
        blocked = new boolean[rows][cols];
        for (int i = 0; i < blocked.length; i++) {
            Arrays.fill(blocked[i], false);
        }
        
    }
    /**
     * gets the size
     */
    public int[] getSize()
    {
        return new int[]{gridRows, gridCols};
    }
    /**
     * sets position as blocked
     */
    public void setBlocked(int row, int col)
    {
        blocked[row][col] = true;
    }
    /**
     * sets position as unblocked
     */
    public void setUnblocked(int row, int col)
    {
        blocked[row][col] = false;
    }
    /**
     * checks if position is blocked
     */
    public boolean isBlocked(int row, int col) {
        if (blocked[row][col]){
            return true;
        }
        else return false;
    }
    /**
     * get the number of obstacles
     */
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
