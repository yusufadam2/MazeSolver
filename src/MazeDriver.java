import maze.*;

public class MazeDriver {
    public static void main(String args[]) {
    	try{
    		Maze.fromTxt("../resources/mazes/invalid/NoExit.txt");
    	}
    	catch(InvalidMazeException e)
    	{
    		System.out.println(e);
    	}
    }
}
