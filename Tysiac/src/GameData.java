import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameData 
{
	//===================================== Zmienne ==============================
	protected volatile int idTable; // numer id stołu w bazie danych -> często potrzebne 
	protected volatile String accessCode; // kod dostępu do stołu -> generowane przez Tworzącego stół... potrzebne by dołączyć do stołu... tworzone ze stringu z datą, więc nie powinno być powtórek
	protected volatile int typeTable;
	
	protected String login;
	protected String password;
	
	protected int idUser; //identifikator użytkownika w bazie
	protected int place;  //miejsce przy stole  

	protected User player1; // zmienna przechowująca dane o użytkowniku -> Hostujący stół
	protected User player2; // user zwykły lub bot
	protected User player3;
	protected User player4; // user/ bot lub musek 

	protected int must; // kto ma musek (1-4)
	protected int movement; // kto wykonuje ruch
	protected int trio; // kto ma 3 karty (jeżeli stół na 3 osoby to gracz 4)

	protected int auction; // wartość licytacji
	protected int auctionPlayer; // kto się licytuje i wygrywa (potem kto wygrał jak reszta spasuje)
	protected int auctionSurrender; //czy spasował licytację 
	

	protected Card[] cards = new Card[25];
	protected SQL sql = null;

	
	

	LoginWindow LogW;
	GameWindow GameW;
	JoinCreateTWindow JCWin;
	BOTJoinWindow BOTW;
	Color stol;

	protected volatile int loginClick = 0; // flaga czy był kliknięty guzik Logowania, 
	protected volatile int playClick = 0;
	protected volatile int playClick2 = 0; //0 - nothing, 1 - Create, 2 - JOIN
	
	//int checkL;
	
	//=============================================================================
	public GameData() throws TysiacException
	{
		
		sql = new SQL(this);
		
		cards = sql.getCards();
		
		LogW = new LoginWindow(this);
		JCWin = new JoinCreateTWindow(this);
		BOTW = new BOTJoinWindow(this);
		GameW = new GameWindow(this);
		stol = new Color(0,102,0);
	}
	public void close()
	{
		sql.close(); // kończenie połączenia z bazą danych 
		LogW.closeWindow();
		BOTW.closeWindow();
		GameW.closeWindow();
		JCWin.closeWindow();
	}
	//------------------------------------------------------------------------------
	protected void displayLoginWindow()
	{
		
		LogW.setVisible(true);
		LogW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		LogW.getContentPane().setBackground(stol);
		LogW.setTitle("Tysiąc");
		LogW.setResizable(false);
		
		LogW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(LogW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					LogW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayJoinCreateTWindow()
	{
		JCWin.setVisible(true);
		JCWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JCWin.getContentPane().setBackground(stol);
		JCWin.setTitle("Tysiąc");
		JCWin.setResizable(false);
		
		JCWin.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(JCWin, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					JCWin.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayBOTJoinWindow()
	{
		BOTW.setVisible(true);
		BOTW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		BOTW.getContentPane().setBackground(stol);
		BOTW.setTitle("BOT - Tysiąc");
		BOTW.setResizable(false);
		
		BOTW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(BOTW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					BOTW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayGameWindow()
	{
		GameW.setVisible(true);
		GameW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GameW.getContentPane().setBackground(stol);
		GameW.setTitle("Tysiąc");
		GameW.setResizable(false);
		
		GameW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(GameW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					GameW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	

	

	

	

	//===================================== Funkcje ==============================

	protected int getIdUser() 
	{
		return idUser;
	}
	protected int getIdTable() 
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
	protected String getAccessCode() 
	{
		return accessCode;
	}
	protected int getPlace() 
	{
		return place;
	}
	protected User getPlayer1() 
	{
		return player1;
	}
	protected User getPlayer2() 
	{
		return player2;
	}
	protected User getPlayer3() 
	{
		return player3;
	}
	protected User getPlayer4() 
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
	protected int getPlayClick2() 
	{
		return playClick2;
	}
	protected int getTypeTable() 
	{
		return typeTable;
	}
	
	
	
	

	protected void setIdUser(int idUser) 
	{
		this.idUser = idUser;
	}
	protected void setIdTable(int idTable) 
	{
		this.idTable = idTable;
	}
	protected void setLogin(String l) 
	{
		login = l;
	}
	protected void setPassword(String p) 
	{
		password = p;
	}
	protected void setAccessCode(String accessCode) 
	{
		this.accessCode = accessCode;
	}
	protected void setPlace(int place) 
	{
		this.place = place;
	}
	protected void setPlayer1(User player1) 
	{
		this.player1 = player1;
	}
	protected void setPlayer2(User player2) 
	{
		this.player2 = player2;
	}
	protected void setPlayer3(User player3) 
	{
		this.player3 = player3;
	}
	protected void setPlayer4(User player4) 
	{
		this.player4 = player4;
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
	protected void setPlayClick2(int playClick) 
	{
		this.playClick2 = playClick;
	}
	protected void setTypeTable(int typeTable) 
	{
		this.typeTable = typeTable;
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

	
	protected void SitToTable() throws TysiacException
	{
		int tmp=0;
		do
		{

			this.setPlayClick(0);
			this.displayJoinCreateTWindow();
			while(this.getPlayClick() == 0);
			
			if(this.getPlayClick2() == 1)
			{
				sql.createTable(typeTable, idUser, 1, 2, 4);
				tmp = this.getIdTable();
				while(this.getAccessCode() == null);
				if(tmp <=0 )
				{
					JOptionPane.showMessageDialog(null,"Niepowodzenie przy tworzeniu stołu");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Kod dostępu do stołu to: " + this.getAccessCode());
				}
			}
			else if(this.getPlayClick2() == 2)
			{
				sql.joinTable(idUser, accessCode);
				tmp = this.getIdTable();
				if(tmp <=0 )
				{
					JOptionPane.showMessageDialog(null,"Niepowodzenie przy dołączaniu do stołu");
				}
			}

			this.JCWin.setVisible(false);
		}
		while(tmp<=0);
		

		GameW.setTitle("Tysiąc - " + this.getAccessCode());
	}

	
	protected void BOTSitToTable() throws TysiacException
	{
		int tmp=0;
		do
		{

			this.setPlayClick(0);
			this.displayBOTJoinWindow();
			while(this.getPlayClick() == 0);
			
			sql.BOTjoinTable(accessCode);
			tmp = this.getIdTable();
			if(tmp <=0 )
			{
				JOptionPane.showMessageDialog(null,"Niepowodzenie przy dołączaniu do stołu");
			}
			

			this.BOTW.setVisible(false);
		}
		while(tmp<=0);
		

		GameW.setTitle("BOT - Tysiąc - " + this.getAccessCode());
	}


}
