package cityrescue;

import java.util.Arrays;
import cityrescue.exceptions.*;
import cityrescue.exceptions.InvalidLocationException;

public class CityMap { // Map Implementation containing rows cols and whether a space is blocked
    private int gridRows;
    private int gridCols;
    private boolean[][] blocked;
    public CityMap(int rows, int cols)
    {
        gridCols = cols;
        gridRows = rows;
        blocked = new boolean[rows][cols];
        Arrays.fill(blocked, false);
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

    
}
