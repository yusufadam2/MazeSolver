package maze;
import java.io.Serializable;

/**
	* Class providing operations for tile management and creation
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class Tile implements Serializable
{
	/**
		*A Type called type that is used of to determine
		what type the tile is
	*/
	private Type type;

	/**
		*A boolean that determines if the tile has been
		visited or not
	*/
	private boolean isVisited;

	private Tile(Type t)
	{
		this.type= t;

	}

	/**
		*Gets a type tile from a character
		*@param letter used to determine what type of
		tile is being created
		*@return Returns a Tile that has been created 
	*/
	protected static Tile fromChar(char letter)
	{
		Tile tile= new Tile(Type.CORRIDOR);

		if(letter== 'e')
		{
			tile.type= Type.ENTRANCE;
		}
		else if(letter == 'x')
		{
			tile.type= Type.EXIT;
		}
		else if(letter == '.')
		{
			tile.type= Type.CORRIDOR;
		}
		else if(letter == '#')
		{
			tile.type= Type.WALL;
		}

		return tile;
	}

	/**
		*Gets a maze object from a txt file
		*@param file this is the filename that the maze is built from
		*@return Returns a Maze object 
		*@throws InvalidMazeException indicates a problem with the maze
	*/
	public Type getType()
	{
		return type;
	}

	/**
		*Finds out if a tile is navigable or not
		*@return Returns a boolean determining if it is navigable or not 
	*/
	public boolean isNavigable()
	{
		if((type== Type.CORRIDOR) || (type== Type.EXIT) || (type== Type.ENTRANCE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
		*Returns a tile to type String
		*@return Returns a type String  
	*/
	public String toString()
	{
		if(type== Type.CORRIDOR)
		{
			return(".");
		}
		else if(type== Type.ENTRANCE)
		{
			return("e");
		}
		else if(type== Type.EXIT)
		{
			return("x");
		}
		else if(type== Type.WALL)
		{
			return("#");
		}
		else
		{
			return("");
		}
	}

	/**
		*Enumerate class creates different types of type
	*/
	public enum Type
	{
		CORRIDOR,
		ENTRANCE,
		EXIT,
		WALL;
	}

	/**
		*returns the isVisited attribute of tile
		*@return Returns a boolean whether the tile has been visited or not 
	*/
	public boolean getIsVisited()
	{
		return isVisited;
	}

	/**
		*Sets the isvisited attribute to true
		of the tile
	*/
	public void setIsVisited()
	{
		isVisited= true;
	}
}