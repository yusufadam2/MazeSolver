package maze;

public class MultipleEntranceException extends InvalidMazeException
{
	public MultipleEntranceException()
	{
		super("Multiple entrance");
	}
}