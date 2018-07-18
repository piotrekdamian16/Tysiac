

public class User 
{
	private int idTable;
	private int idUser;
	private String userName;
	private StackCard scHand;
	private StackCard scTable;
	private StackCard scWinnings;
	private int bomb=0;
	
	public User (int it, int iu, String un, int p)
	{
		idTable = it;
		idUser = iu;
		userName = un;

		scHand = new StackCard(it, p, 'r');
		scTable = new StackCard(it, p, 's');
		scWinnings = new StackCard(it, p, 'z');
	}

	public int getIdTable() 
	{
		return idTable;
	}
	
	public int getIdUser() 
	{
		return idUser;
	}
	
	public String getUserName() 
	{
		return userName;
	}

	public StackCard getScHand() 
	{
		return scHand;
	}

	public StackCard getScTable() 
	{
		return scTable;
	}
	public StackCard getScWinnings() 
	{
		return scWinnings;
	}
	public int getBomb() 
	{
		return bomb;
	}


	public boolean checkReport(char color)
	{
		int counter=0;
		for(int i=0; i<scHand.getCards().length; i++)
		{
			if(color == 'C' && (scHand.getCard(i)==2 || scHand.getCard(i)==3))
			{
				counter++;
			}
			else if(color == 'Z' && (scHand.getCard(i)==8 || scHand.getCard(i)==9))
			{
				counter++;
			}
			else if(color == 'D' && (scHand.getCard(i)==14 || scHand.getCard(i)==15))
			{
				counter++;
			}
			else if(color == 'W' && (scHand.getCard(i)==20 || scHand.getCard(i)==21))
			{
				counter++;
			}
		}
		if(counter == 2) return true;
		
		return false;
	}
	
	
	public void updateStacks(int [] sch, int [] sct, int [] scw) throws TysiacException
	{
		this.scHand.setCards(sch);
		this.scHand.setCount(sch.length);
		
		this.scTable.setCards(sct);
		this.scTable.setCount(sct.length);
		
		this.scWinnings.setCards(scw);
		this.scWinnings.setCount(scw.length);
	}
	
	public void setBomb(int bomb) 
	{
		this.bomb = bomb;
	}
	
	
	public void display()
	{
		System.out.println("Stół: " + idTable + "\tidUser: " + idUser + "\tNazwa: " + userName + "\tBomba: " + bomb);
	}
}
