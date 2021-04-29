package maze;

public class NoExitException extends InvalidMazeException
{
	public NoExitException()
	{
		super("No exit");
	}
}