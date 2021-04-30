package maze;

/**
	* Class used to find exception error if maze
	is invalid 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class InvalidMazeException extends RuntimeException
{
	public InvalidMazeException()
	{
		super("Invalid maze Exception");
	}

	public InvalidMazeException(String error)
	{
		super(error);
	}
}