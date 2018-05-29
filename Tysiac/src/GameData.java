import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameData 
{
	//===================================== Zmienne ==============================
	protected static int idTable; // numer id stołu w bazie danych -> często potrzebne 
	protected static String accessCode; // kod dostępu do stołu -> generowane przez Tworzącego stół... potrzebne by dołączyć do stołu... tworzone ze stringu z datą, więc nie powinno być powtórek

	protected String login;
	protected String password;
	
	protected int idUser; //identifikator użytkownika w bazie
	protected static int place;  //miejsce przy stole  

	protected static User player1; // zmienna przechowująca dane o użytkowniku -> Hostujący stół
	protected static User player2; // user zwykły lub bot
	protected static User player3;
	protected static User player4; // user/ bot lub musek 

	protected int must; // kto ma musek (1-4)
	protected int movement; // kto wykonuje ruch
	protected int trio; // kto ma 3 karty (jeżeli stół na 3 osoby to gracz 4)

	protected int auction; // wartość licytacji
	protected int auctionPlayer; // kto się licytuje i wygrywa (potem kto wygrał jak reszta spasuje)
	protected int auctionSurrender; //czy spasował licytację 
	

	protected static Card[] cards = new Card[25];
	protected static SQL sql = null;


	LoginWindow LogW;
	GameWindow GameW;
	JoinCreateTWindow JCWin;
	Color stol;
	

	protected volatile int loginClick = 0;
	private volatile int playClick = 0;
	
	//int checkL;
	
	//=============================================================================
	public GameData() throws TysiacException
	{
		LogW = new LoginWindow(this);
		JCWin = new JoinCreateTWindow(this);
		GameW = new GameWindow();
		stol = new Color(0,102,0);
		sql = new SQL();
	}
	public void close()
	{
		sql.close(); // kończenie połączenia z bazą danych 
		LogW.closeWindow();
		GameW.closeWindow();
		JCWin.closeWindow();
	}
	//------------------------------------------------------------------------------
	protected void displayLoginWindow()
	{
		
		LogW.setVisible(true);
		//LogW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LogW.getContentPane().setBackground(stol);
		LogW.setTitle("Tysiąc");
		LogW.setResizable(false);
	}
	protected void displayJoinCreateTWindow()
	{
		//JOptionPane.showMessageDialog(null,"Zalogowano poprawnie." );
		
		
		JCWin.setVisible(true);
		JCWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCWin.getContentPane().setBackground(stol);
		JCWin.setTitle("Tysiąc");
		JCWin.setResizable(false);
		
		JCWin.setVisible(false);   //niewidoczne okno login window
		//dispose();  //usuwa obiekt login window
	}
	protected void displayGameWindow()
	{
		GameW.setVisible(true);
		GameW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GameW.getContentPane().setBackground(stol);
		GameW.setTitle("Tysiąc");
		GameW.setResizable(false);
	}
	

	

	

	

	//===================================== Funkcje ==============================

	protected int getIdUser() 
	{
		return idUser;
	}
	protected static int getIdTable() 
	{
		return idTable;
	}
	protected String getLogin() 
	{
		return login;
	}
	protected String getPassword() 
	{
		return password;
	}
	protected static String getAccessCode() 
	{
		return accessCode;
	}
	protected static int getPlace() 
	{
		return place;
	}
	protected static User getPlayer1() 
	{
		return player1;
	}
	protected static User getPlayer2() 
	{
		return player2;
	}
	protected static User getPlayer3() 
	{
		return player3;
	}
	protected static User getPlayer4() 
	{
		return player4;
	}
	protected int getMust() 
	{
		return must;
	}
	protected int getMovement() 
	{
		return movement;
	}
	protected int getTrio() 
	{
		return trio;
	}
	protected int getAuction() 
	{
		return auction;
	}
	protected int getAuctionPlayer() 
	{
		return auctionPlayer;
	}
	protected int getAuctionSurrender() 
	{
		return auctionSurrender;
	}
	protected int getLoginClick() 
	{
		return loginClick;
	}
	protected int getPlayClick() 
	{
		return playClick;
	}
	
	

	protected void setIdUser(int idUser) 
	{
		this.idUser = idUser;
	}
	protected static void setIdTable(int idTable) 
	{
		GameData.idTable = idTable;
	}
	protected void setLogin(String l) 
	{
		login = l;
	}
	protected void setPassword(String p) 
	{
		password = p;
	}
	protected static void setAccessCode(String accessCode) 
	{
		GameData.accessCode = accessCode;
	}
	protected static void setPlace(int place) 
	{
		GameData.place = place;
	}
	protected static void setPlayer1(User player1) 
	{
		GameData.player1 = player1;
	}
	protected static void setPlayer2(User player2) 
	{
		GameData.player2 = player2;
	}
	protected static void setPlayer3(User player3) 
	{
		GameData.player3 = player3;
	}
	protected static void setPlayer4(User player4) 
	{
		GameData.player4 = player4;
	}
	protected void setMust(int must) 
	{
		this.must = must;
	}
	protected void setMovement(int movement) 
	{
		this.movement = movement;
	}
	protected void setTrio(int trio) 
	{
		this.trio = trio;
	}
	protected void setAuction(int auction) 
	{
		this.auction = auction;
	}
	protected void setAuctionPlayer(int auctionPlayer) 
	{
		this.auctionPlayer = auctionPlayer;
	}
	protected void setAuctionSurrender(int auctionSurrender) 
	{
		this.auctionSurrender = auctionSurrender;
	}
	protected void setLoginClick(int loginClick) 
	{
		this.loginClick = loginClick;
	}
	protected void setPlayClick(int playClick) 
	{
		this.playClick = playClick;
	}
	
	
	
	
	
	
	
	
	
	
	
	protected void SignIn() throws TysiacException
	{
		int tmp=0;
		do
		{
			this.setLoginClick(0);
			this.displayLoginWindow();
			while(this.getLoginClick() == 0);
			this.LogW.setVisible(false);
			tmp = sql.checkLogin(getLogin());
			if(tmp == -2) 
			{
				JOptionPane.showMessageDialog(null,"Użyto niepoprawnego symbolu w loginie.");
			}
			else if(tmp == -1) 
			{
				JOptionPane.showMessageDialog(null,"Login zbyt krótki, min 5 znaków");
			}
			else if(tmp == 0) 
			{
				JOptionPane.showMessageDialog(null,"Brak podanego loginu w bazie");
			}
			else
			{
				tmp = sql.loginToGame(getLogin(), getPassword());
				if(tmp <= 0)
				{
					JOptionPane.showMessageDialog(null,"Błędny Login lub Hasło");
				}
			}
			this.LogW.resetPasswordField();
			
		}
		while(tmp<=0);
		
		this.setIdUser(tmp);
	}
/*
	protected void SitToTable()
	{
		this.setPlayClick(0);
		this.displayJoinCreateTWindow();
		while(this.getPlayClick() == 0);
	}
*/

}
