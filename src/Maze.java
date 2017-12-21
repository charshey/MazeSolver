
import java.util.ArrayList;
import java.util.Stack;


public class Maze
{
    private int height, width, startX, startY, endX, endY;
    private ArrayList<ArrayList<GridSpace>> grid = new ArrayList<>();
    private Stack solution;

    public Maze(int h, int w){
        height = h;
        width = w;
    }

    public void setMaze(ArrayList<Integer> nums){
        int index = 2;
        for(int row = 0; row < height; row++){
            ArrayList<GridSpace> newRow = new ArrayList<>();
            for(int col = 0; col < width; col++){
                GridSpace space = new GridSpace(nums.get(index),col,row);
                newRow.add(space);
                if(space.start){
                    startX = col;
                    startY = row;
                }
                if(space.end){
                    endX = col;
                    endY = row;
                }
                index++;
            }
            grid.add(newRow);
        }
        this.solveMaze();
    }

    private void solveMaze() {

        int lives = 3;
        int X = startX;
        int Y = startY;

        ArrayList<GridSpace> settled = new ArrayList<>();
        ArrayList<GridSpace> unsettled = new ArrayList<>();

        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                unsettled.add(grid.get(row).get(col));
            }
        }

        GridSpace square = grid.get(Y).get(X);
        unsettled.add(square);
        square.distance = 0;

        while(!unsettled.isEmpty()){

            GridSpace min = new GridSpace(0,0,0);
            for(GridSpace x:unsettled){
                if(x.distance < min.distance){
                    min = x;
                }
            }

            X = min.X;
            Y = min.Y;
            unsettled.remove(min);


            if(min.up){
                if(!min.mine || lives > 1){
                    if(min.mine) lives -= 1;
                    GridSpace neighbor = grid.get(Y-1).get(X);
                    int alt = min.distance + 1;
                    if(alt < neighbor.distance || neighbor.distance == -1){
                        neighbor.distance = alt;
                        neighbor.prevX = min.X;
                        neighbor.prevY = min.Y;
                    }
                }
            }
            if(min.down){
                if(!min.mine || lives > 1){
                    if(min.mine) lives -= 1;
                    GridSpace neighbor = grid.get(Y+1).get(X);
                    int alt = min.distance + 1;
                    if(alt < neighbor.distance || neighbor.distance == -1){
                        neighbor.distance = alt;
                        neighbor.prevX = min.X;
                        neighbor.prevY = min.Y;
                    }
                }
            }
            if(min.left){
                if(!min.mine || lives > 1){
                    if(min.mine) lives -= 1;
                    GridSpace neighbor = grid.get(Y).get(X-1);
                    int alt = min.distance + 1;
                    if(alt < neighbor.distance || neighbor.distance == -1){
                        neighbor.distance = alt;
                        neighbor.prevX = min.X;
                        neighbor.prevY = min.Y;
                    }
                }
            }
            if(min.right){
                if(!min.mine || lives > 1){
                    if(min.mine) lives -= 1;
                    GridSpace neighbor = grid.get(Y).get(X+1);
                    int alt = min.distance + 1;
                    if(alt < neighbor.distance || neighbor.distance == -1){
                        neighbor.distance = alt;
                        neighbor.prevX = min.X;
                        neighbor.prevY = min.Y;
                    }
                }
            }

            settled.add(min);
        }

        solution = new Stack();
        GridSpace end = grid.get(endY).get(endX);
        while(end.prevX != null){
            GridSpace prev = grid.get(end.prevY).get(end.prevX);
            if(endY > prev.Y){
                solution.push("up");
            }
            else if(endY < prev.Y){
                solution.push("down");
            }
            else if(endX > prev.X){
                solution.push("right");
            }
            else if (endX < prev.X){
                solution.push("left");
            }
        }
    }

    public void getSolution(){
        System.out.print("[ ");
        for(Object dir: solution){
            System.out.print(dir);
            solution.pop();
            if(!solution.isEmpty()){
                System.out.print(", ");
            }
        }
        System.out.print("]\n");
    }

    public void getMaze()
    {
        for(int row = 0; row < height; row++){
            System.out.print("·");
            for(int col = 0; col < width; col++){
                if(!grid.get(row).get(col).up){System.out.print("---·");}
                else{System.out.print("   ·");}
            }
            System.out.print("\n");
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
            System.out.print("|\n");
        }
        System.out.print("·");
        for(int i = 0; i < width; i++){System.out.print("---·");}
        System.out.print("\n\n");
    }
}