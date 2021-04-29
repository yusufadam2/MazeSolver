package maze.routing;

import maze.*;
import java.util.*;
import java.io.Serializable;
import java.io.*;


public class RouteFinder implements Serializable
{
	private Maze maze;
	private Stack<Tile> route= new Stack<Tile>();
	private boolean finished;

	public RouteFinder(Maze m)
	{
		maze= m;
	}

	public Maze getMaze()
	{
		return maze;
	}

	public List<Tile> getRoute()
	{
		return route;
	}

	public boolean isFinished()
	{
		return finished;
	}

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