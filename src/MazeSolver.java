/*
 * Program: MazeSolver
 * Author: Clare Harshey <charshey@indiana.edu>
 * Date Created: 12/20/2017
 *
 * This program was created as a solution to the
 * Software Coding Challenge issued as part of the
 * SparkCognition interview process.
 *
 * MazeSolver, Maze and GridSpace together solve mazes encoded
 * in a local text file, finding the shortest path that also
 * doesn't kill the player by traversing over (too many) mines.
 *
 * The maze file, named "mazes.txt", should be located in the
 * working directory.
 *
 * The output for each maze in the file is a bracketed list of
 * directions to take that shortest path through the maze from
 * start to finish.
 *
 * The user may add command line flag -extra to also get in the
 * output graphical representation of the maze and the number of
 * lives left after the path is traversed (starting with 3;
 * stepping on a mine with 1 life kills the player).
 *
 */

import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class MazeSolver {

    public static void main(String[] args) {

        // initiazlize command line flag parser to see whether user wants "extra" output.
        CommandLineArgs command = new CommandLineArgs();
        boolean extra = command.flagset(args);

        try {
            // open file; pathname is stored here.
            Scanner input = new Scanner(new File("mazes.txt"));

            // for each line in the file, decode the maze and solve it.
            while (input.hasNextLine()) {

                // get list of numbers by pattern matching only integers.
                ArrayList<Integer> nums = new ArrayList<>();
                String line = input.nextLine();
                Pattern pat = Pattern.compile("([0-9]+)");
                Matcher match = pat.matcher(line);
                while (match.find()) {
                    int num = Integer.parseInt(match.group());
                    nums.add(num);
                }

                // initialize the maze by passing the list in.
                Maze myMaze = new Maze(nums);
                // if the user wants extra output, display the maze.
                if(extra) {
                    myMaze.getMaze();
                }
                // display maze solution.
                myMaze.getSolution();
                // if the user wants extra output, display the lives left after solution.
                if(extra){
                    myMaze.getLives();
                }
            }
        }
        // check for file not found.
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
