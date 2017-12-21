/*
 * Class: CommandLineArgs
 * Author: Clare Harshey <charshey@indiana.edu>
 * Date Created: 12/20/2017
 *
 * This class parses command line arguments for this program.
 * The user can use flag -extra to request extra output beyond
 * just the solution to the mazes.
 *
 */

public class CommandLineArgs {

    // Parameters:
    // args: string array of command line arguments,
    // passed here from main function of the program.
    //
    // Returns:
    // boolean, true if -extra flag was specified.
    public boolean flagset(String[] args){
        // if there are any arguments, check each to see if it matches
        // the extra flag, and if so, return true.
        if(args.length != 0){
            for(String argument: args){
                if(argument.equals("-extra")){
                    return true;
                }
            }
        }
        // otherwise return false.
        return false;
    }
}
