package maze;

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