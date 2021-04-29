package maze;

public class NoEntranceException extends InvalidMazeException
{
	public NoEntranceException()
	{
		super("No entrance");
	}
}