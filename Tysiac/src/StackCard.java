import java.sql.SQLException;

public class StackCard 
{
	private int idTable;
	private int player;
	private char what;
	private int [] cards;
	private int count;
	
	private SQL sql;

	public StackCard(int it, int p, char w)
	{
		idTable = it;
		player = p;
		what = w;
	}

	public void setCards() throws SQLException 
	{
		this.cards = sql.getStackCards(this.idTable, this.player,  this.what);
	}

	public int getIdTable() 
	{
		return idTable;
	}
	
	public int getPlayer() 
	{
		return player;
	}

	public char getWhat() 
	{
		return what;
	}

	public int [] getCards() 
	{
		return cards;
	}

	public int getCount() 
	{
		return count;
	}
	
}
