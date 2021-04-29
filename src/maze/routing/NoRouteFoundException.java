package maze.routing;

public class NoRouteFoundException extends RuntimeException
{
	public NoRouteFoundException()
	{
		super("No route");
	}
}