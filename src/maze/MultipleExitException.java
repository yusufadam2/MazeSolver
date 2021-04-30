package maze;

/**
	* Class used to find exception error if maze
	has multiple exits 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class MultipleExitException extends InvalidMazeException
{
	public MultipleExitException()
	{
		super("Multiple exit");
	}
}