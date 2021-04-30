package maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.Serializable;
/**
	* Class providing operations for maze management and creation
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class Maze implements Serializable
{
	/**
		*A tile that signals the entry point of the maze
	*/
	private Tile entrance;

	/**
		*A tile that signals the exit point of the maze
	*/
	private Tile exit;

	/**
		*A 2D array that consists of two lists which is used to store all
		*of the tiles that exist in the maze
	*/
	private List<List<Tile>> tiles= new ArrayList<List<Tile>>();

	/**
		*This is a constructos method for maze, however it doesn't 
		*do anything
		*No parameters
		*Doesn't return anything
		*Throws nothing
	*/
	private Maze()
	{

	}

	/**
		*Gets a maze object from a txt file
		*@param file this is the filename that the maze is built from
		*@return Returns a Maze object 
		*@throws InvalidMazeException indicates a problem with the maze
	*/
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

	/**
		*Gets the adjacent tile to the given tile
		*@param t,d t is of type Tile that indicates which tile is the one
		*being processed. d is the direction of which the adjacent tile is
		*found from
		*@return Returns a Tile object, i.e. the adjacent tile 
	*/
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

	/**
		*Gets the entrance tile from the maze
		*@return Returns the entrance tile object
	*/
	public Tile getEntrance()
	{
		return this.entrance;
	}

	/**
		*Gets the exit tile from the maze
		*@return Returns the exit tile object
	*/
	public Tile getExit()
	{
		return this.exit;
	}

	/**
		*Gets a tile at a given location in the 2D array
		*@param cin is the coordinates used to find the given tile
		*@return Returns a Tile object that indicates the tile at the
		*location
	*/
	public Tile getTileAtLocation(Coordinate cin)
	{
		int xval= cin.getX();
		int yval= cin.getY();

		yval= tiles.size()-yval -1;

		return tiles.get(yval).get(xval);
	}

	/**
		*Gets the coordinate of a given tile
		*@param tin the tile of which the coordinates are found from 
		*@return returns a Coordinate object 
	*/
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

	/**
		*Gets a 2D array of all of the tiles in the maze
		*@return returns a 2D array of type Tyles
	*/
	public List<List<Tile>> getTiles()
	{
		return this.tiles;
	}

	/**
		*Sets the entrance tile of the maze 
		*@param ent this is the entrance tile that is used
	*/
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

	/**
		*Sets the exit tile in the maze
		*@param ex exit tile that is used
	*/
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

	/**
		*Converts the maze object into a string
		*@return Returns a type String which is the converted maze  
	*/
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

   /**
	* Class providing operations for coordinate management
	and creation
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
	*/
	public class Coordinate
	{	
		/**
		*An integer that is used to represent the x 
		coordinate of the coordinate 
		*/
		private int x;

		/**
		*An integer that is used to represent the y 
		coordinate of the coordinate 
		*/
		private int y;

		/**
		*Builder method used to build the coordinate object
		*@param xin,yin used to build both the x and y parts of the coordinates
		*@return Returns a Coordinate which has been built
		*/
		public Coordinate(int xin, int yin)
		{
			this.x= xin;
			this.y= yin;
		}

		/**
		*Gets the x value of the coordinate
		*@return returns an integer which is the x part of coordinate
		*/
		public int getX()
		{
			return x;
		}

		/**
		*Gets the y value of the coordinate
		*@return returns an integer which is the y part of the coordinate
		*/
		public int getY()
		{
			return y;
		}

		/**
		*Returns the coordinate as a string
		*@return returns a type string of the coordinate
		*/
		public String toString()
		{
			return("("+x +", "+y+")");
		}

	}

	/**
		*Enumerate class that is used to declare the 
		directions  
	*/
	public enum Direction
	{
		NORTH,
		SOUTH,
		EAST,
		WEST;
	}

}