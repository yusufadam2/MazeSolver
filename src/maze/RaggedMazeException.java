package maze;

/**
	* Class used to find exception error if maze
	is ragged
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class RaggedMazeException extends InvalidMazeException
{
	public RaggedMazeException()
	{
		super("Ragged maze");
	}
}