package maze;
import java.io.Serializable;

public class Tile implements Serializable
{
	private Type type;
	private boolean isVisited;

	private Tile(Type t)
	{
		this.type= t;

	}

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

	public Type getType()
	{
		return type;
	}

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

	public enum Type
	{
		CORRIDOR,
		ENTRANCE,
		EXIT,
		WALL;
	}

	public boolean getIsVisited()
	{
		return isVisited;
	}

	public void setIsVisited()
	{
		isVisited= true;
	}
}