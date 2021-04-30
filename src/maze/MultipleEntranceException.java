package maze;

/**
	* Class used to find exception error if maze
	has multiple entrances 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class MultipleEntranceException extends InvalidMazeException
{
	public MultipleEntranceException()
	{
		super("Multiple entrance");
	}
}