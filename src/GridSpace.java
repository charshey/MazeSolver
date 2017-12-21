/*
 * Class: GridSpace
 * Author: Clare Harshey <charshey@indiana.edu>
 * Date Created: 12/20/2017
 *
 * This class represents one space on the maze's grid,
 * and contains information about what directions can
 * be taken from here; whether it is a start or end space;
 * and whether it has a mine.
 *
 * It also holds information about the shortest distance
 * to get here; the previous space in the shortest path;
 * the direction taken to get here in the shortest path;
 * and the number of lives left at this point in the shortest
 * path.
 *
 */

public class GridSpace implements Comparable<GridSpace>
{
    public boolean up, down, left, right, mine, start, end;
    public Integer col, row, distance, lives;
    public GridSpace prev;
    public String dir;
    private static final int UP = 1;
    private static final int DOWN = 4;
    private static final int RIGHT = 2;
    private static final int LEFT = 8;
    private static final int MINE = 64;
    private static final int START = 16;
    private static final int END = 32;

    // Parameters:
    // num: the integer encoding this space
    // c: the integer column of this space in the maze
    // r: the integer row of this space in the maze
    public GridSpace(int num, int c, int r){

        // decode the grid's number with bitwise AND operations against feature codes.
        up = ((num & UP) == UP);
        down = ((num & DOWN) == DOWN);
        right = ((num & RIGHT) == RIGHT);
        left = ((num & LEFT) == LEFT);
        mine = ((num & MINE) == MINE);
        start = ((num & START) == START);
        end = ((num & END) == END);

        // set initial distance very high, default lives to the full number
        distance = 500000000;
        lives = 3;

        // set the column and row for this space.
        col = c;
        row = r;
    }

    // new compareTo method compares spaces by distance, for sorting
    // in Dijkstra's algorithm.
    @Override
    public int compareTo(GridSpace other){
        return Integer.compare(this.distance,other.distance);
    }
}
