import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class MazeSolver {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("mazes.txt"));
            while (input.hasNextLine()) {
                ArrayList<Integer> nums = new ArrayList<Integer>();
                String line = input.nextLine();
                Pattern pat = Pattern.compile("([0-9]+)");
                Matcher match = pat.matcher(line);
                while (match.find()) {
                    int num = Integer.parseInt(match.group());
                    nums.add(num);
                }

                Maze myMaze = new Maze(nums.get(0), nums.get(1));
                myMaze.setMaze(nums);
                myMaze.getSolution();
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

    }
}
