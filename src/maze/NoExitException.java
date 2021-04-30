package maze;

/**
	* Class used to find exception error if maze
	has no exits
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class NoExitException extends InvalidMazeException
{
	public NoExitException()
	{
		super("No exit");
	}
}