package maze;

/**
	* Class used to find exception error if maze
	has no entrance
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class NoEntranceException extends InvalidMazeException
{
	public NoEntranceException()
	{
		super("No entrance");
	}
}