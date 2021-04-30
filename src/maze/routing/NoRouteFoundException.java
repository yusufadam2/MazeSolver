package maze.routing;

/**
	* Class used to find exception error if maze
	has no route 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class NoRouteFoundException extends RuntimeException
{
	public NoRouteFoundException()
	{
		super("No route");
	}
}