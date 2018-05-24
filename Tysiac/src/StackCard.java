

public class StackCard 
{
	private int idTable;
	private int player;
	private char what;
	private int [] cards;
	private int count;

	public StackCard(int it, int p, char w)
	{
		idTable = it;
		player = p;
		what = w;
	}

	public void setCards(int [] c)
	{
		this.cards = c;
	}
	public void setCount(int c)
	{
		this.count = c;
	}

	public int getIdTable() 
	{;
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
	public int getCard(int i) 
	{
		return cards[i];
	}

	public int getCount() 
	{
		return count;
	}
	
}
