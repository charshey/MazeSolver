/*
 * Class: Maze
 * Author: Clare Harshey <charshey@indiana.edu>
 * Date Created: 12/20/2017
 *
 * This class represents the maze as a whole. It contains
 * a 2D ArrayList of GridSpaces as a representation of their
 * adjacency to one another. It also contians information on
 * the maze's height, width, start and end locations; a stack
 * representing the directions to take in the solution path;
 * and an integer number of lives left after the solution path
 * is taken.
 *
 * The maze solving method here is a modified version of Dijkstra's
 * algorithm. This algorithm is fast for finding the shortest path,
 * especially when prioritizing squares by minimum distance in the
 * unsettled list, and player lives can be tracked concurrently with
 * min distance values to know when risking a mine square is safe.
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class Maze
{
    private int height, width, startcol, startrow, endcol, endrow, lives;
    private ArrayList<ArrayList<GridSpace>> grid = new ArrayList<>();
    private Stack<String> solution;

    // Parameters:
    // nums: an ArrayList of integers representing the height, width, and cells
    // of the maze.
    public Maze(ArrayList<Integer> nums){

        // set height and width from the first two values of the list
        height = nums.get(0);
        width = nums.get(1);

        // starting from the third value, initialize the 2D ArrayList
        // based on height and width
        int index = 2;
        for(int row = 0; row < height; row++){
            // create a new row for each row in the height of the maze
            ArrayList<GridSpace> newRow = new ArrayList<>();
            for(int col = 0; col < width; col++){
                // for each column in the width of the maze, initialize
                // a new GridSpace with the next number from the list
                // and the current row and column values
                GridSpace space = new GridSpace(nums.get(index),col,row);
                // add to the new row
                newRow.add(space);
                // if this new space is start or end, save that information
                // in the maze's start/end coordinates
                if(space.start){
                    startcol = col;
                    startrow = row;
                }
                if(space.end){
                    endcol = col;
                    endrow = row;
                }
                // move to the next number in the list.
                index++;
            }
            // add each completed row to the grid.
            grid.add(newRow);
        }

        // once the maze is initialized, go ahead and solve it.
        this.solveMaze();
    }

    // solveMaze() solves the maze for the shortest path avoiding too
    // many mines using a modified version of Dijkstra's Algorithm.
    // This solution is stored as a stack of directions, as strings,
    // from start to end.
    private void solveMaze() {

        // create lists to keep track of the settled and unsettled
        // spaces in the maze.
        ArrayList<GridSpace> settled = new ArrayList<>();
        ArrayList<GridSpace> unsettled = new ArrayList<>();

        // add all of the spaces to unsettled to begin with.
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                unsettled.add(grid.get(i).get(j));
            }
        }

        // start the algorithm from the start square, based on
        // the saved coordinates. Set the start square's distance
        // to zero. Note for later that this is the beginning of
        // the algorithm.
        GridSpace square = grid.get(startrow).get(startcol);
        square.distance = 0;
        boolean beginning = true;

        // loop through this process as long as there are unsettled
        // spaces left in the list.
        while(!unsettled.isEmpty()){
            // look for the minimum distance square in the unsettled list
            GridSpace min;
            // if this is the first iteration, set the beginning square as the minimum
            if(beginning){
                min = square;
                beginning = false;
            }
            // if this is not the first iteration, sort the unsettled list by distance
            // and get the square with the smallest distance.
            else{
                Collections.sort(unsettled);
                min = unsettled.get(0);
            }

            // set the column and row to the current column and row of the square at hand.
            int col = min.col;
            int row = min.row;

            // if it's possible to move up from the square, evaluate the neighbor above
            if(min.up){
                // set the neighbor to be the square above
                GridSpace neighbor = grid.get(row-1).get(col);
                // the new distance is the min. distance of the previous square, plus 1 to
                // the neighbor square.
                int alt = min.distance + 1;
                // see if it's safe to move here; if the player doesn't have enough lives
                // and there's a mine there, it's not safe.
                boolean safe;
                safe = !neighbor.mine || min.lives > 1;
                // if it's safe and the new distance is lower than the neighbor's previous
                // distance, replace it.
                if(alt < neighbor.distance && safe){
                    // set the new distance
                    neighbor.distance = alt;
                    // set the neighbor's previous node on the shortest path as the one
                    // we were in.
                    neighbor.prev = min;
                    // set the direction on the shortest path as "up" since that's how we
                    // got here.
                    neighbor.dir = "up";
                    // if the neighbor has a mine, the neighbor's lives should be one less
                    // than the lives we had before. Otherwise it's the same number of lives.
                    if(neighbor.mine){
                        neighbor.lives = min.lives - 1;
                    }
                    else{
                        neighbor.lives = min.lives;
                    }
                }
            }
            // if it's possible to move down from the square, evaluate the neighbor below.
            if(min.down){
                // set the neighbor to be the square below
                GridSpace neighbor = grid.get(row+1).get(col);
                // the new distance is the min. distance of the previous square, plus 1 to
                // the neighbor square.
                int alt = min.distance + 1;
                // see if it's safe to move here; if the player doesn't have enough lives
                // and there's a mine there, it's not safe.
                boolean safe;
                safe = !neighbor.mine || min.lives > 1;
                // if it's safe and the new distance is lower than the neighbor's previous
                // distance, replace it.
                if(alt < neighbor.distance && safe){
                    // set the new distance
                    neighbor.distance = alt;
                    // set the neighbor's previous node on the shortest path as the one
                    // we were in.
                    neighbor.prev = min;
                    // set the direction on the shortest path as "up" since that's how we
                    // got here.
                    neighbor.dir = "down";
                    // if the neighbor has a mine, the neighbor's lives should be one less
                    // than the lives we had before. Otherwise it's the same number of lives.
                    if(neighbor.mine){
                        neighbor.lives = min.lives - 1;
                    }
                    else{
                        neighbor.lives = min.lives;
                    }
                }
            }
            // if it's possible to move left, evaluate the neighbor to the left
            if(min.left){
                // set the neighbor to be the square to the left
                GridSpace neighbor = grid.get(row).get(col-1);
                // the new distance is the min. distance of the previous square, plus 1 to
                // the neighbor square.
                int alt = min.distance + 1;
                // see if it's safe to move here; if the player doesn't have enough lives
                // and there's a mine there, it's not safe.
                boolean safe;
                safe = !neighbor.mine || min.lives > 1;
                // if it's safe and the new distance is lower than the neighbor's previous
                // distance, replace it.
                if(alt < neighbor.distance && safe){
                    // set the new distance
                    neighbor.distance = alt;
                    // set the neighbor's previous node on the shortest path as the one
                    // we were in.
                    neighbor.prev = min;
                    // set the direction on the shortest path as "up" since that's how we
                    // got here.
                    neighbor.dir = "left";
                    // if the neighbor has a mine, the neighbor's lives should be one less
                    // than the lives we had before. Otherwise it's the same number of lives.
                    if(neighbor.mine){
                        neighbor.lives = min.lives - 1;
                    }
                    else{
                        neighbor.lives = min.lives;
                    }
                }
            }
            // if it's possible to move to the right, evaluate the neighbor to the right
            if(min.right){
                // set the neighbor to be the square to the right
                GridSpace neighbor = grid.get(row).get(col+1);
                // the new distance is the min. distance of the previous square, plus 1 to
                // the neighbor square.
                int alt = min.distance + 1;
                // see if it's safe to move here; if the player doesn't have enough lives
                // and there's a mine there, it's not safe.
                boolean safe;
                safe = !neighbor.mine || min.lives > 1;
                // if it's safe and the new distance is lower than the neighbor's previous
                // distance, replace it.
                if(alt < neighbor.distance && safe){
                    // set the new distance
                    neighbor.distance = alt;
                    // set the neighbor's previous node on the shortest path as the one
                    // we were in.
                    neighbor.prev = min;
                    // set the direction on the shortest path as "up" since that's how we
                    // got here.
                    neighbor.dir = "right";
                    // if the neighbor has a mine, the neighbor's lives should be one less
                    // than the lives we had before. Otherwise it's the same number of lives.
                    if(neighbor.mine){
                        neighbor.lives = min.lives - 1;
                    }
                    else{
                        neighbor.lives = min.lives;
                    }
                }

            }
            // once this node's neighbors have been checked out, remove it from unsettled and add to settled.
            unsettled.remove(min);
            settled.add(min);
        }

        // once all nodes have been settled, the shortest path has been found.
        // new string stack will hold directions for this path.
        solution = new Stack<>();
        // start with the end square
        GridSpace end = grid.get(endrow).get(endcol);
        // set the lives to be the number left on the end square
        lives = end.lives;
        // for each previous square in the chain, add the direction to get to the end
        // from that square to the stack, then set the previous square as the new end
        while(end.prev != null){
            solution.push(end.dir);
            end = end.prev;
        }
    }

    // getSolution() prints the solution stored as directions from start to end.
    public void getSolution(){
        // the directions are enclosed in brackets and single quotes.
        System.out.print("[");
        // get the size of the stack
        int num = solution.size();
        // for each thing in the stack, print it and pop it out.
        for(int i = 0; i < num; i++){
            System.out.print("'" + solution.pop() + "'");
            if(!solution.isEmpty()){
                System.out.print(", ");
            }
        }
        // closing bracket for the directions.
        System.out.print("]\n\n");
    }

    // getLives() prints to the user the number of lives left after taking the shortest path.
    public void getLives(){
        System.out.print("Lives left: " + this.lives + "\n\n\n");
    }

    // getMaze() prints the maze graphically.
    public void getMaze()
    {
        // print dots and dashes of "ceiling" of each row where appropriate
        for(int row = 0; row < height; row++){
            System.out.print("·");
            for(int col = 0; col < width; col++){
                if(!grid.get(row).get(col).up){System.out.print("---·");}
                else{System.out.print("   ·");}
            }
            // move to the body of the row
            System.out.print("\n");
            // for each square in the row, print its walls
            // and contents dependent on the GridSpace's properties
            for(int col = 0; col < width; col++){
                GridSpace square = grid.get(row).get(col);

                if(!square.left){
                    System.out.print("|");
                }
                else{
                    System.out.print(" ");
                }

                if(square.start){
                    System.out.print(" S ");
                }
                else if(square.end){
                    System.out.print(" E ");
                }
                else if(square.mine){
                    System.out.print(" * ");
                }
                else{
                    System.out.print("   ");
                }
            }
            // right side wall of the maze for each row.
            System.out.print("|\n");
        }
        // print the floor of the maze in dots and dashes.
        System.out.print("·");
        for(int i = 0; i < width; i++){System.out.print("---·");}
        System.out.print("\n\n");
    }
}