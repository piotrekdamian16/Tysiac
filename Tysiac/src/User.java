import java.sql.SQLException;

public class User 
{
	private int idTable;
	private int idUser;
	private String userName;
	private StackCard scHand;
	private StackCard scTable;
	private StackCard scWinnings;
	private int bomb;
	
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

	
	
	public void updateStacks() throws SQLException
	{
		scHand.setCards();
		scTable.setCards();
		scWinnings.setCards();
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
