package maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.Serializable;

public class Maze implements Serializable
{
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles= new ArrayList<List<Tile>>();

	private Maze()
	{

	}

	public static Maze fromTxt(String file) throws InvalidMazeException
	{
		Maze maze= new Maze();
		int rowLength=0;
		try
		{
			FileReader fr= new FileReader(file);
			BufferedReader br= new BufferedReader(fr);
			String line= br.readLine();
			rowLength= line.length(); 
			int currentRowLength= rowLength;
			while(line!= null)
			{	 
				if (currentRowLength != rowLength)
				{
					throw new RaggedMazeException();
				}

				List<Tile> row= new ArrayList<Tile>();
				
				for (int i=0; i<line.length(); i++)
				{
					char c= line.charAt(i);
					Tile current= Tile.fromChar(c);
					if (c=='e' || c=='x' || c=='#' || c=='.')
					{

						row.add(current);
					}
					else
					{

						throw new InvalidMazeException("Invalid character in maze");
					}
				}
				maze.tiles.add(row);
				line= br.readLine();
				if(line!= null)
				{
					currentRowLength= line.length();
				}
				
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch(IOException e)
		{
			System.out.println("Io Exception");
		}

		for(int i=0; i<maze.tiles.size(); i++)
		{
			for(int j=0; j<maze.tiles.get(i).size(); j++)
			{
				if(maze.tiles.get(i).get(j).toString()== "e")
				{
					maze.setEntrance(maze.tiles.get(i).get(j));
				}
				else if(maze.tiles.get(i).get(j).toString()== "x")
				{
					maze.setExit(maze.tiles.get(i).get(j));
				}
			}
		}
		if(maze.entrance == null)
		{
			throw new NoEntranceException();
		}
		else if(maze.exit == null)
		{
			throw new NoExitException();
		}

		return maze;
	}

	public Tile getAdjacentTile(Tile t, Direction d)
	{
		Coordinate c = getTileLocation(t);
		int xc= c.getX();
		int yc= c.getY();

		Tile tile= null;
		if(d == Direction.NORTH)
		{
			if(yc+1 > tiles.size()-1)
			{
				return null;
			}

			tile= getTileAtLocation(new Coordinate(xc,yc+1));
		}
		else if(d == Direction.SOUTH)
		{
			if(yc-1 < 0)
			{
				return null;
			}

			tile= getTileAtLocation(new Coordinate(xc,yc-1));
		}
		else if(d == Direction.EAST)
		{
			if(xc+1 > tiles.get(0).size()-1)
			{
				return null;
			}

			tile= getTileAtLocation(new Coordinate(xc+1,yc));
		}
		else if(d == Direction.WEST)
		{
			if(xc-1 < 0)
			{
				return null;
			}

			tile= getTileAtLocation(new Coordinate(xc-1,yc));
		}

		return tile;
	}

	public Tile getEntrance()
	{
		return this.entrance;
	}

	public Tile getExit()
	{
		return this.exit;
	}

	public Tile getTileAtLocation(Coordinate cin)
	{
		int xval= cin.getX();
		int yval= cin.getY();

		yval= tiles.size()-yval -1;

		return tiles.get(yval).get(xval);
	}

	public Coordinate getTileLocation(Tile tin)
	{
		Coordinate c = null;
		for(int i=0; i<tiles.size(); i++)
		{
			for(int j=0; j<tiles.get(i).size(); j++)
			{
				if(tiles.get(i).get(j) == tin)
				{
					int yval= tiles.size()-1 -i;
					c= new Coordinate(j, yval);

				}
			}
		}
		return c;
	}

	public List<List<Tile>> getTiles()
	{
		return this.tiles;
	}

	private void setEntrance(Tile ent)
	{
		if (getTileLocation(ent) == null)
		{
			throw new IllegalArgumentException();
		}

		if(entrance == null)
		{
			entrance= ent;
		}
		else
		{
			throw new MultipleEntranceException();
		}
	}

	private void setExit(Tile ex)
	{
		if (getTileLocation(ex) == null)
		{
			throw new IllegalArgumentException();
		}

		if(exit == null)
		{
			exit= ex;
		}
		else
		{
			throw new MultipleExitException();
		}	
	}

	public String toString()
	{
    	String lines ="";
    	for (int i=0; i<tiles.size(); i++)
    	{
      		for (int j=0; j<tiles.get(0).size(); j++)
      		{
       		 	lines += tiles.get(i).get(j).toString();
      		}
      	lines +='\n';
    	}
    	return lines;
    }

	public class Coordinate
	{
		private int x;
		private int y;

		public Coordinate(int xin, int yin)
		{
			this.x= xin;
			this.y= yin;
		}
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		public String toString()
		{
			return("("+x +", "+y+")");
		}

	}

	public enum Direction
	{
		NORTH,
		SOUTH,
		EAST,
		WEST;
	}

}