package maze.routing;

import maze.*;
import java.util.*;
import java.io.Serializable;
import java.io.*;

/**
	* Class providing operations and creation of the RouteFinder 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class RouteFinder implements Serializable
{
	private Maze maze;
	private Stack<Tile> route= new Stack<Tile>();
	private boolean finished;

	/**
		*Setter method to set the maze in the route finder
		*@param m used to input the maze
		*@return Returns a RouteFinder object 
	*/
	public RouteFinder(Maze m)
	{
		maze= m;
	}

	/**
		*Getter method for maze
		*@return Returns the maze attribute of object
		RouteFinder 
	*/
	public Maze getMaze()
	{
		return maze;
	}

	/**
		*Getter method for route
		*@return Returns the route attribute of object
		RouteFinder
	*/
	public List<Tile> getRoute()
	{
		return route;
	}

	/**
		*Getter method for finished
		*@return Returns the finished attribute of object
		RouteFinder, type boolean
	*/
	public boolean isFinished()
	{
		return finished;
	}

	/**
		*Loads route into routefinder
		*@param s which is the file name that is loaded
	*/
	public static RouteFinder load(String s)
	{
		RouteFinder routeFinder = null;
        try(
            FileInputStream f = new FileInputStream(s);
            ObjectInputStream fs = new ObjectInputStream(f);
            )
        {
            routeFinder = (RouteFinder) fs.readObject();
        }
        catch (IOException e)
        {
            if (routeFinder != null){
                System.out.println("error reading the file");
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class not found");
        }
        return routeFinder;
	}

	/**
		*Saves the routefinder object to a file
		*@param s the toString that is being saved
	*/
	public void save(String s)
	{
		try (
            	FileOutputStream f = new FileOutputStream(s);
            	ObjectOutputStream fs = new ObjectOutputStream(f);
            )
        {
            fs.writeObject(this);
        }
        catch (IOException e){
            System.out.println("Error with write");
        }
	}

	/**
		*Method that is used to step through the maze to find
		the solution
		*@return boolean as to whether the maze has been solved or not
	*/
	public boolean step()
	{
		if(isFinished())
		{
			return true;
		}
		else
		{
			if(route.empty())
			{
				Tile entranceTile= maze.getEntrance();
				route.push(entranceTile);
				entranceTile.setIsVisited();
				return false;
			}
			Tile nTile= maze.getAdjacentTile(route.lastElement(), Maze.Direction.NORTH);
			Tile eTile= maze.getAdjacentTile(route.lastElement(), Maze.Direction.EAST);
			Tile sTile= maze.getAdjacentTile(route.lastElement(), Maze.Direction.SOUTH);
			Tile wTile= maze.getAdjacentTile(route.lastElement(), Maze.Direction.WEST);
			
			Tile[] adjTiles = {nTile, eTile, sTile, wTile};
			for(Tile t:adjTiles)
			{
				if (t!= null)
				{
					if(t.getIsVisited())
					{
						continue;
					}
					else
					{
						if(t.getType() != Tile.Type.WALL)
						{
							route.push(t);
							t.setIsVisited();
							if(t.getType()== Tile.Type.EXIT)
							{
								finished= true;
								return true;
							}
							else
							{
								return false;
							}
							
						}

					}
				}
			}
			route.pop();
			if(route.empty())
			{
				throw new NoRouteFoundException();
			}
			return false;
		}
	}

	/**
		*Converts the routefinder tiles into a string
		*@return s which is the file name that is loaded
	*/
	public String toString()
	{
		String lines ="";
		List<List<Tile>> tiles= maze.getTiles();

    	for (int i=0; i<tiles.size(); i++)
    	{
      		for (int j=0; j<tiles.get(0).size(); j++)
      		{
      			Tile ti = tiles.get(i).get(j);
      			if(route.contains(ti))
      			{
      				lines+= "*";
      			}
      			else if (ti.getIsVisited())
      			{
      				lines+="-";
      			}
      			else
      			{
      				lines += ti.toString();
      			}
       		 	
      		}
      	lines +='\n';
    	}
    	return lines;
	}
}