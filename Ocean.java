/* There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean... The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges... The island is partitioned into a grid of square cells... You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c)... The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height... Water can flow from any cell adjacent to an ocean into the ocean... Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans...
   * Eg 1: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]    Output = [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
   * Eg 2: heights = [[1,2,3],[8,9,4],[7,6,5]]                                        Output = [[0,2],[1,0],[1,1],[1,2],[2,0],[2,2],[2,1]]
  */
import java.util.*;
public class Ocean
{
    public List<List<Integer>> OceanDrainage(int heights[][])
    {
        int rows = heights.length, cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];    // Creating Pacific DP Matrix...
        boolean[][] atlantic = new boolean[rows][cols];   // Creating Atlantic DP Matrix...
        for (int col = 0; col < cols; col++){
            DepthFirstSearch(0, col, pacific, heights[0][col], heights);    // Applying DFS to the Upper shore touching Pacific Ocean...
            DepthFirstSearch(rows-1, col, atlantic, heights[rows-1][col], heights);   // Applying DPS to the Lower shore touching Atlantic Ocean...
        }
        for (int row = 0; row < rows; row++){
            DepthFirstSearch(row, 0, pacific, heights[row][0], heights);    // Applying DFS to the Left shore touching the Pacific Ocean...
            DepthFirstSearch(row, cols-1, atlantic, heights[row][cols-1], heights);    // Applying DFS to the Right shore touching Atlantic Ocean...
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                if (pacific[i][j] && atlantic[i][j])  // If the current cell is drained into both Oceans...
                    result.add(Arrays.asList(i,j));   // Adding the Coordinates...
            }
        }
        return result;
    }
    
    private void DepthFirstSearch(int row, int col, boolean[][] visited, int prevHeight, int[][] heights){
        if (row < 0 || row >= heights.length || col < 0 || col >= heights[0].length || visited[row][col] || prevHeight > heights[row][col])
            return;    // If the boundary is crossed or cell is already visited or the previous cell height is more than the current cell height we backtrack...
        visited[row][col]= true;     // Otherwise set the cell state as drain possible...
        DepthFirstSearch(row+1, col, visited, heights[row][col], heights);   // Moving Downwards...
        DepthFirstSearch(row-1, col, visited, heights[row][col], heights);   // Moving Upwards...
        DepthFirstSearch(row, col+1, visited, heights[row][col], heights);   // Moving Rightwards...
        DepthFirstSearch(row, col-1, visited, heights[row][col], heights);   // Moving Leftwards...
        return;
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int m, n;
        System.out.print("Enter the number of rows (n) : ");
        n = sc.nextInt();
        System.out.print("Enter the number of columns (m) : ");
        m = sc.nextInt();
        int matrix[][] = new int[n][m];
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[0].length; j++)
            {
                System.out.print("Enter data of "+(i+1)+" row and "+(j+1)+" column : ");
                matrix[i][j] = sc.nextInt();
            }
        }
        Ocean ocean = new Ocean();    // Object creation...
        List<List<Integer>> list = ocean.OceanDrainage(matrix);     // function calling...
        System.out.println("The Cell Coordinates from which the rain water can flow to both Atlantic and Pacific Ocean ");
        System.out.print("[ ");
        for(int i = 0; i < list.size(); i++)
            System.out.print("["+list.get(i).get(0)+", "+list.get(i).get(1)+"], ");
        System.out.println("]");
        sc.close();
    }
}

// Time Complexity  - O((n+m)^2) time...
// Space Complexity - O(n^m) space...

/* DEDUCTIONS :- 
 * 1. We create two Dynamic Programming matrices, one for each Ocean and check the cells using DFS which can be drained...
 * 2. By the help of Depth First Search we check the next lower cell till not possible in all four cardinal directions...
 * 3. Since the states of many cases are repeated, we backtrack when we find a path has encountered a previous visited cell which can be drained, thus complexity reduced via Dynamic Programming...
 * 4. Now, we check for all cells which can be drained by both the Oceans as the intersection of the coordinates of the two DP Matrices...
*/