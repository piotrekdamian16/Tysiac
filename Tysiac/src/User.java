

public class User 
{
	private int idTable;
	private int idUser;
	private String userName;
	private StackCard scHand;
	private StackCard scTable;
	private StackCard scWinnings;
	private int bomb=0;

	private int reportC, reportZ, reportD, reportW;
	
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



	public void setReports(char color, int who)  //ustawia kto ma jaki meldunek w reku
	{
		if(color == 'C')
		{
			this.reportC = who;
		}
		else if(color == 'Z')
		{
			this.reportZ = who;
		}
		else if(color == 'D')
		{
			this.reportD = who;
		}
		else if(color == 'W')
		{
			this.reportW = who;
		}
	}
	
	public int [] getReports() // zwraca tablice z wartościami ktory gracz ma dany meldunek 
	{																			// [0] - czerwo
		int [] tabReports = new int[4];											// [1] - zoladz
																				// [2] - dzwonek
		tabReports[0] = reportC;												// [3] - wino
		tabReports[1] = reportZ;
		tabReports[2] = reportD;
		tabReports[3] = reportW;	
		
		return tabReports;																		
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
